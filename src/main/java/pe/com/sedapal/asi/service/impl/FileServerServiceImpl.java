package pe.com.sedapal.asi.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import pe.com.sedapal.asi.model.response_objects.UploadFileResponse;
import pe.com.sedapal.asi.service.IFileServerService;
import pe.com.sedapal.asi.util.AppConstants;
import pe.com.sedapal.asi.util.FileUtil;
import pe.com.sedmail.cliente.bean.ArchivoAdjunto;

@Service
public class FileServerServiceImpl implements IFileServerService {
	private Logger logger = LoggerFactory.getLogger(FileServerServiceImpl.class);

	@Override
	public ArchivoAdjunto downloadFile(String fileUrl) {
		ArchivoAdjunto archivoAdjunto = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HttpURLConnection httpConn = null;

		try {
			logger.info("File url: " + fileUrl);
			
			URL url = new URL(fileUrl);
			httpConn = (HttpURLConnection) url.openConnection();
			int responseCode = httpConn.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				logger.info("Iniciando descarga del fichero " + fileUrl);
				
				String fileName = "";
				String disposition = httpConn.getHeaderField("Content-Disposition");
				String contentType = httpConn.getContentType();
				int contentLength = httpConn.getContentLength();

				if (disposition != null) {
					// extracts file name from header field
					int index = disposition.indexOf("filename=");
					if (index > 0) {
						fileName = disposition.substring(index + 10, disposition.length() - 1);
					}
				} else {
					// extracts file name from URL
					fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
				}

				logger.info("Content-Type = " + contentType);
				logger.info("Content-Disposition = " + disposition);
				logger.info("Content-Length = " + contentLength);
				logger.info("fileName = " + fileName);

				// opens input stream from the HTTP connection
				InputStream inputStream = httpConn.getInputStream();

				int bytesRead = -1;
				byte[] buffer = new byte[1024];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				archivoAdjunto = new ArchivoAdjunto();
				archivoAdjunto.setBytesArchivo(outputStream.toByteArray());
				archivoAdjunto.setSizeArchivo(contentLength);
				archivoAdjunto.setNombreArchivo(fileName);
				archivoAdjunto.setExtension(FileUtil.getExtension(fileName));

				outputStream.close();
				inputStream.close();

				httpConn.disconnect();
				
				logger.info("Descarga exitosa del fichero!");
			} else {
				logger.warn("===> File Server: No file to download. Server replied HTTP code: " + responseCode);
			}
		} catch (MalformedURLException e) {
			logger.error("File Server: Error URL mal formado.");
		} catch (IOException e) {
			logger.error("File Server: Error en descargar fichero desde " + fileUrl);
		}

		return archivoAdjunto;
	}

	@Override
	public UploadFileResponse uploadFile(ArchivoAdjunto archivoAdjunto, String url) {
		UploadFileResponse uploadFileResponse = new UploadFileResponse();

		try {
			// Request Body
			MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();

			// todo replace tempFile with a real file
			Path tempFile = Files.createTempFile(archivoAdjunto.getNombreArchivo(), "." + archivoAdjunto.getExtension());
			Files.write(tempFile, archivoAdjunto.getBytesArchivo());
			File file = tempFile.toFile();
			// to upload in-memory bytes use ByteArrayResource instead
			Resource binayFile = new FileSystemResource(file);

			bodyMap.add(AppConstants.FILE_FIELD_NAME, binayFile);

			// Request Header
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			// Request Entity
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<UploadFileResponse> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
					new ParameterizedTypeReference<UploadFileResponse>() {});

			uploadFileResponse = response.getBody();

			if (!response.getStatusCode().is2xxSuccessful()) {
				logger.error("Error al cargar archivo al file server");
				logger.warn("Status Code:" + response.getStatusCodeValue());
				uploadFileResponse.setCargado(false);
			} else {
				logger.info("Se cargo exitosamente archivo al file server.");
				logger.info("response status: " + response.getStatusCode());
				logger.info("response body:extension = " + response.getBody().getExtension());
				logger.info("response body:mensaje = " + response.getBody().getMensaje());
				logger.info("response body:nombre = " + response.getBody().getNombre());
				logger.info("response body:nombrereal = " + response.getBody().getNombreReal());
				logger.info("response body:ubicacion = " + response.getBody().getUbicacion());
				logger.info("response body:url = " + response.getBody().getUrl());
				uploadFileResponse.setCargado(true);
			}
		} catch (RestClientException | IOException e) {
			e.printStackTrace();
		}

		return uploadFileResponse;
	}

	@Override
	public void deleteFile(String urlFile) {
		logger.info("Eliminando fichero: " + urlFile);

		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.ALL));

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(urlFile, HttpMethod.HEAD, requestEntity, String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			logger.info(response.getBody());
		} else {
			logger.info("Response: " + response.getBody());
		}
	}

	@Override
	public void getFileInfo(String urlFileServer, String realFileName) {
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		bodyMap.add(AppConstants.FILE_FIELD_REAL_NAME, realFileName);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.ALL);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(urlFileServer, HttpMethod.HEAD, requestEntity,
				String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			logger.info(response.getBody());
		} else {
			logger.info("File info: " + response.getBody());
		}
	}
}
