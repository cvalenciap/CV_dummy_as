package pe.com.sedapal.asi.fileserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pe.com.sedapal.asi.model.response_objects.UploadFileResponse;
import pe.com.sedapal.asi.util.AppConstants;

public class UploadFileTest {
	
	@Test
	public void testUploadFile() throws IOException {
	      MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
	      bodyMap.add(AppConstants.FILE_FIELD_NAME, getUserFileResource());
	      HttpHeaders headers = new HttpHeaders();
	      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

	      // Method 1
//	      RestTemplate restTemplate = new RestTemplate();
//	      ResponseEntity<String> response = restTemplate.exchange("http://sedapal.test:8080/fileserver/asi",
//	              HttpMethod.PUT, requestEntity, String.class);
//	      System.out.println("response status: " + response.getStatusCode());
//	      System.out.println("response body: " + response.getBody());
	      
	      // Method 2
	      RestTemplate restTemplate = new RestTemplate();
	      ResponseEntity<UploadFileResponse> response = restTemplate.exchange("http://sedapal.test:8080/fileserver/asi",
	              HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<UploadFileResponse>() {});
	      System.out.println("response status: " + response.getStatusCode());
	      System.out.println("response body:extension = " + response.getBody().getExtension());
	      System.out.println("response body:mensaje = " + response.getBody().getMensaje());
	      System.out.println("response body:nombre = " + response.getBody().getNombre());
	      System.out.println("response body:nombrereal = " + response.getBody().getNombreReal());
	      System.out.println("response body:ubicacion = " + response.getBody().getUbicacion());
	      System.out.println("response body:url = " + response.getBody().getUrl());
	  }

	  public static Resource getUserFileResource() throws IOException {
	      //todo replace tempFile with a real file
	      Path tempFile = Files.createTempFile("upload-test-file", ".txt");
	      Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
	      System.out.println("uploading: " + tempFile);
	      File file = tempFile.toFile();
	      //to upload in-memory bytes use ByteArrayResource instead
	      return new FileSystemResource(file);
	  }
}
