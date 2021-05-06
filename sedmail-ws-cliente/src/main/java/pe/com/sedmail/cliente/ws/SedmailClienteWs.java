package pe.com.sedmail.cliente.ws;

import static java.text.MessageFormat.format;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pe.com.sedmail.cliente.bean.ArchivoAdjunto;
import pe.com.sedmail.cliente.bean.ParametrosCorreo;
import pe.com.sedmail.cliente.bean.ResponseBean;
import pe.com.sedmail.cliente.util.ConstantesCliente;
import pe.com.sedmail.cliente.util.JsonUtil;

public class SedmailClienteWs {
	private Logger logger = LoggerFactory.getLogger(SedmailClienteWs.class);
	
	private ResponseBean respuestaBean = new ResponseBean();
		
	private RestTemplate restTemplate = new RestTemplate();
	
	public RestTemplate getRestTemplate(){
		return restTemplate;
	}
	
	public void setRestTemplate(RestTemplate restTemplate){
		this.restTemplate = restTemplate;
	}
	
	public ResponseBean enviarCorreo(ParametrosCorreo parametros, String urlWs, String usuario, String password) throws Exception{
		logger.info("Envio sin multipart...");
		
		String urlMethod = "/correo/enviarCorreo";
		
		try{
			String paramValue = JsonUtil.convertirObjetoACadenaJson(parametros);
			/*set configuration header*/
			HttpHeaders headers = new HttpHeaders();
			headers.add(ConstantesCliente.AUTHORIZATION, setCredentialsAuth(usuario, password));
	        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);	
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
	        /**/
	        
	        logger.info("Enviando correos...");
	        
	        restTemplate = new RestTemplate();
			HttpEntity<String> request = new HttpEntity<String>(paramValue, headers);
			ResponseEntity<ResponseBean> response = restTemplate.exchange(urlWs + urlMethod, HttpMethod.POST, request, ResponseBean.class);
			respuestaBean = response.getBody();
			
			logger.info("Estado: " + respuestaBean.getEstadoRespuesta());
			logger.info("Mensaje: " + respuestaBean.getMensajeRespuesta());
		}catch(Exception exception){
			exception.printStackTrace();
			respuestaBean.setMensajeRespuesta(format(ConstantesCliente.MENSAJE_ERROR, exception.getMessage()));
			respuestaBean.setEstadoRespuesta(ConstantesCliente.RESULTADO_ERROR);
		}
		return respuestaBean;
	}
	
	public ResponseBean enviarCorreoMultiPart(ParametrosCorreo parametros, String urlWs, String usuario, String password) throws Exception{
		logger.info("Envio multipart...");
		
		String urlMethod = "/correo/enviarCorreoMultiPart";
		List<ArchivoAdjunto> ltaAux = new ArrayList<ArchivoAdjunto>();
		try{
			/*set configuration header*/
			HttpHeaders headers = new HttpHeaders();
			headers.add(ConstantesCliente.AUTHORIZATION, setCredentialsAuth(usuario, password));
			
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
			headers.setAccept(acceptableMediaTypes);
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			
			/*multipartValueContent*/
	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        
	        /*set content file attachment*/
	        if(parametros.getArchivosAdjuntos() != null && parametros.getArchivosAdjuntos().size() > 0){
	        	parametros.setArchivosAdjuntos(saveTempFile(parametros.getArchivosAdjuntos()));
	        	
	        	for(ArchivoAdjunto archivo : parametros.getArchivosAdjuntos()){
	        		body.add("fileString", new FileSystemResource((new File(archivo.getUrlArchivo()))));
		        }
	        }
	        
	        /*empty attachtment file value*/
	        ltaAux = parametros.getArchivosAdjuntos();
	        parametros.setArchivosAdjuntos(null);
	        String paramValue = JsonUtil.convertirObjetoACadenaJson(parametros);
			
	        body.add("map", paramValue);
	        
	        logger.info("Enviando correos...");
	        
	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
			ResponseEntity<ResponseBean> response = restTemplate.postForEntity(urlWs + urlMethod, requestEntity, ResponseBean.class);
			respuestaBean = response.getBody();
			
			logger.info("Estado: " + respuestaBean.getEstadoRespuesta());
			logger.info("Mensaje: " + respuestaBean.getMensajeRespuesta());
			
			/*delete content file attachment*/
			deleteTempFile(ltaAux);
		}catch(Exception exception){
			deleteTempFile(ltaAux);
			respuestaBean.setMensajeRespuesta(format(ConstantesCliente.MENSAJE_ERROR, exception.getMessage()));
			respuestaBean.setEstadoRespuesta(ConstantesCliente.RESULTADO_ERROR);
		}
		return respuestaBean;
	}
	
	private String setCredentialsAuth(String usuario, String password) throws Exception{
		String authorization = "";
		try{
			String plainCreds = usuario + ":" + password;
			byte[] plainCredsBytes = plainCreds.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
			authorization = ConstantesCliente.BASIC + new String(base64CredsBytes);
		}catch(Exception exception){
			throw new Exception(exception);
		}
		return authorization;
	}
	
	private List<ArchivoAdjunto> saveTempFile(List<ArchivoAdjunto> listaArchivos) throws Exception{
		try{
			for(ArchivoAdjunto archivo : listaArchivos){
				String ruta = "";
				int index = archivo.getNombreArchivo().lastIndexOf('.');
				String extension = archivo.getNombreArchivo().substring(index + 1);
				String nombreArchivoInterno = generarNombreArchivo(archivo.getNombreArchivo().substring(0, index), extension);
				ruta = System.getProperty("java.io.tmpdir") + "\\" + nombreArchivoInterno;
//				ruta = System.getProperty("java.io.tmpdir") + "\\" + archivo.getNombreArchivo();
				FileOutputStream fileOutputStream = new FileOutputStream(ruta);
				fileOutputStream.write(archivo.getBytesArchivo());
				fileOutputStream.close();
				archivo.setUrlArchivo(ruta);
			}
		}catch(Exception exception){
			throw new Exception(exception);
		}
		return listaArchivos;
	}
	
	private String generarNombreArchivo(String nombre, String extension){
		
		Calendar now = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmssSSS");
		
		String nombreArchivo = nombre + "." + sdf.format(now.getTime()) + "." + extension;
		
		return nombreArchivo;
	}
	
	private void deleteTempFile(List<ArchivoAdjunto> listaAdjuntos) throws Exception {
		try{
			if(listaAdjuntos != null && listaAdjuntos.size() > 0){
				for(ArchivoAdjunto adjunto : listaAdjuntos){
					File currentFile = new File(adjunto.getUrlArchivo());
					currentFile.delete();
				}
			}
		}catch(Exception exception){
			throw new Exception(exception);
		}
	}

}
