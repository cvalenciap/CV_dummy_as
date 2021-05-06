package pe.com.sedapal.asi.api;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.Parametro;
import pe.com.sedapal.asi.model.ParametroStorage;
import pe.com.sedapal.asi.model.SolicitudPackage;
import pe.com.sedapal.asi.model.UsuarioAfectado;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.request_objects.RequestUsuarioAfectado;
import pe.com.sedapal.asi.model.response_objects.Estado;
import pe.com.sedapal.asi.model.response_objects.ResponseObject;
import pe.com.sedapal.asi.service.IParametrosService;
import pe.com.sedapal.asi.util.AppConstants;

@RestController
@RequestMapping("/api")
public class ParametrosApi {
	Logger logger = LoggerFactory.getLogger(ParametrosApi.class);
	
	@Autowired
	private IParametrosService parametrosService;

	@GetMapping(path = "/parametros/parametro-storage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> obtenerParametroStorage(@RequestParam("n_codarea") Long n_codarea) {
		ResponseObject response = new ResponseObject();
		try {
			logger.info("" + n_codarea);
			ParametroStorage parametro = this.parametrosService.obtenerParametroStorage(n_codarea);
			if (this.parametrosService.getError() == null) {
				response.setResultado(parametro);
				response.setEstado(Estado.OK);
				response.setPaginacion(null);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setError(this.parametrosService.getError() );
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/parametros/usuarios-afectados", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarUsuariosAfectados(@RequestParam("nficha") Long nficha, PageRequest pageRequest, RequestUsuarioAfectado requestUsuarioAfectado) {
		ResponseObject response = new ResponseObject();
		try {
			List<UsuarioAfectado> lista = this.parametrosService.obtenerUsuariosAfectados(nficha, pageRequest, requestUsuarioAfectado);
			if (this.parametrosService.getError() == null) {
				response.setResultado(lista);
				response.setEstado(Estado.OK);
				response.setPaginacion(this.parametrosService.getPaginacion());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setError(this.parametrosService.getError() );
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/parametros/areas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarAreas(){
		ResponseObject response = new ResponseObject();
		try {
			List<Area> lista = this.parametrosService.obtenerAreas();
			if (this.parametrosService.getError() == null) {
				response.setResultado(lista);
				response.setEstado(Estado.OK);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setError(this.parametrosService.getError());
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/parametros/listado", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarParametrosPorTipo(@RequestParam("tipoParam") String tipoParametro, PageRequest page) {
		ResponseObject response = new ResponseObject();
		
		try {
			List<Parametro> lista = this.parametrosService.obtenerParametrosPorTipo(tipoParametro, page);
			if (this.parametrosService.getError() == null) {
				response.setResultado(lista);
				response.setEstado(Estado.OK);
				response.setPaginacion(this.parametrosService.getPaginacion());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setError(this.parametrosService.getError() );
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(BadRequestException e) {
			response.setError(this.parametrosService.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch(InternalServerErrorException e) {
			response.setError(this.parametrosService.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/parametros/{idPara}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarParametroPorId(@PathVariable("idPara") Integer idParametro) {
		ResponseObject response = new ResponseObject();
		
		try {
			Parametro parametro = this.parametrosService.obtenerParametrosPorId(idParametro);
			response.setResultado(parametro);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch(BadRequestException e) {
			response.setError(this.parametrosService.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch(InternalServerErrorException e) {
			response.setError(this.parametrosService.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/parametros/detalle", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarParametrosPorTipo(@RequestParam("idParam") Integer idParametro) {
		ResponseObject response = new ResponseObject();
		
		try {
			logger.info("Detalles del parametro " + idParametro);
			List<DetalleParametro> detallesParametro = this.parametrosService.obtenerDetallesParametro(idParametro);
			if (this.parametrosService.getError() == null) {
				response.setResultado(detallesParametro);
				response.setEstado(Estado.OK);
				response.setPaginacion(this.parametrosService.getPaginacion());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			} else {
				response.setError(this.parametrosService.getError() );
				response.setEstado(Estado.ERROR);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(BadRequestException e) {
			response.setError(this.parametrosService.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch(InternalServerErrorException e) {
			response.setError(this.parametrosService.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/parametros/registraListaValores", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registrarListaValores(@RequestBody DetalleParametro dtParametro) {
		ResponseObject response = new ResponseObject();
		try {
			Integer resultado = this.parametrosService.registrarListaValores(dtParametro);
			
			if (AppConstants.RESULTADO_OK.equals(resultado)) {
				logger.info("La grabación se ha realizado satisfactoriamente.");
				response.setResultado(resultado);
				response.setEstado(Estado.OK);
				response.setError(0, "Grabación Exitosa", "La grabación se ha realizado satisfactoriamente.");
			} else {
				logger.error("La grabación no se realizó, intente de nuevo.");
				response.setResultado(resultado);
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
}
