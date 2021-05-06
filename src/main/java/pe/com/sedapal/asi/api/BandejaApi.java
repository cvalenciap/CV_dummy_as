package pe.com.sedapal.asi.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.asi.model.DetalleRequerimiento;
import pe.com.sedapal.asi.model.request_objects.BandejaRequest;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.response_objects.Estado;
import pe.com.sedapal.asi.model.response_objects.ResponseObject;
import pe.com.sedapal.asi.service.IBandejaService;

@RestController
@RequestMapping("/api")
public class BandejaApi {
	
	@Autowired
	private IBandejaService service;
	
	@GetMapping(path = "/requerimientos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarRequerimientos(BandejaRequest bandejaRequest, PageRequest pageRequest) {
		ResponseObject response = new ResponseObject();
		try {
			List<DetalleRequerimiento> lista = this.service.obtenerRequerimientos(bandejaRequest, pageRequest);
			if(service.getError() == null) {
				response.setEstado(Estado.OK);
				response.setResultado(lista);				
				response.setPaginacion(this.service.getPaginacion());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setEstado(Estado.ERROR);
				response.setError(this.service.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/requerimientos/aprobar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> revisarSoliciutd(@RequestBody List<DetalleRequerimiento> listDetalleSolicitud){
		ResponseObject response = new ResponseObject();
		try {
			Integer resultado = this.service.revisarSolicitud(listDetalleSolicitud);
			if(service.getError() == null) {
				response.setEstado(Estado.OK);
				response.setResultado(resultado);				
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setEstado(Estado.ERROR);
				response.setError(this.service.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/requerimientos/detalle", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarDetalleRequerimiento(BandejaRequest bandejaRequest) {
		ResponseObject response = new ResponseObject();
		try {
			DetalleRequerimiento detalleRequerimiento = this.service.obtenerDetalleRequerimiento(bandejaRequest);
			if(service.getError() == null) {
				response.setEstado(Estado.OK);
				response.setResultado(detalleRequerimiento);				
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setEstado(Estado.ERROR);
				response.setError(this.service.getError());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	
}
