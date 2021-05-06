package pe.com.sedapal.asi.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.asi.model.AdjuntoMensaje;
import pe.com.sedapal.asi.model.SolicitudPackage;
import pe.com.sedapal.asi.model.response_objects.Estado;
import pe.com.sedapal.asi.model.response_objects.ResponseObject;
import pe.com.sedapal.asi.service.IRegistroService;
import pe.com.sedapal.asi.util.AppConstants;

@RestController
@RequestMapping("/api")
public class RegistroApi {
	private Logger logger = LoggerFactory.getLogger(RegistroApi.class);
	
	@Autowired
	private IRegistroService service;
	
	@PostMapping(path = "/registro", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseObject> generarSolicitud(@RequestBody SolicitudPackage solicitudPackage, @RequestParam("n_cod_area") Long n_cod_area) {
		ResponseObject response = new ResponseObject();
		
		try {
			// respuesta = { "resultado": (0|1), "tickets": [123,21312]}
			Map<String, Object> respuesta = this.service.generarSolicitud(solicitudPackage.getCabecera(), solicitudPackage.getDetalle(), n_cod_area);
			Integer resultado = (respuesta.get("resultado") != null ? (Integer)respuesta.get("resultado") : null);
			if (AppConstants.RESULTADO_OK.equals(resultado)) {
				logger.info("La grabación se ha realizado satisfactoriamente.");
				response.setResultado(respuesta);
				response.setEstado(Estado.OK);
				response.setError(0, "Grabación Exitosa", "La grabación se ha realizado satisfactoriamente.");
			} else {
				logger.error("La grabación no se realizó, intente de nuevo.");
				response.setResultado(respuesta);
				response.setEstado(Estado.ERROR);
				response.setError(1, "Grabación Fallida", "La grabación no se realizó, intente de nuevo.");
			}
			
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch(BadRequestException e) {
			e.printStackTrace();
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch(InternalServerErrorException e) {
			e.printStackTrace();
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			e.printStackTrace();
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

	@PostMapping(path = "/adjuntos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> guardarAdjuntos(@RequestBody List<AdjuntoMensaje> adjuntos) {
		ResponseObject response = new ResponseObject();
		try {
			response.setResultado(this.service.guardarAdjuntos(adjuntos));
			response.setEstado(Estado.OK);
			response.setError(0, "Grabación Exitosa", "La grabación de Adjuntos se ha realizado Satisfactoriamente");
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch(BadRequestException e) {
			logger.error(e.getMessage(), e.getCause());
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch(InternalServerErrorException e) {
			logger.error(e.getMessage(), e.getCause());
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			logger.error(e.getMessage(), e.getCause());
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	
}
