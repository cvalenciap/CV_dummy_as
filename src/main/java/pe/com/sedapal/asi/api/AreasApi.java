package pe.com.sedapal.asi.api;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.response_objects.Estado;
import pe.com.sedapal.asi.model.response_objects.ResponseObject;
import pe.com.sedapal.asi.service.IAreasService;

@RestController
@RequestMapping("/api")
public class AreasApi {
	@Autowired
	private IAreasService service;

	@GetMapping(path = "/areas/gerencia-equipos/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarAreasEquipos(@PathVariable("codigo") Long codigo) {
		ResponseObject response = new ResponseObject();
		try {
			List<Area> lista = this.service.obtenerAreasEquipos(codigo);
			response.setResultado(lista);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (BadRequestException e) {
			response.setError(this.service.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch (InternalServerErrorException e) {
			response.setError(this.service.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
