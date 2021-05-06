package pe.com.sedapal.asi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.asi.model.InformacionSistema;
import pe.com.sedapal.asi.service.ISistemaService;

@RestController
@RequestMapping("/api")
public class RootApi {

	@Autowired
	private ISistemaService service;

	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InformacionSistema> obtenerParametrosSistema(){
		try {
			InformacionSistema informacionSistema = service.obtenerInformacionSistema();
			return new ResponseEntity<InformacionSistema>(informacionSistema,HttpStatus.OK);
		} catch (Exception e) {
			return null;
		}
	}
}
