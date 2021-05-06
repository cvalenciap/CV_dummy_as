package pe.com.sedapal.asi.service;

import java.util.List;

import pe.com.sedapal.asi.model.Contacto;
import pe.com.sedapal.asi.model.response_objects.Error;

public interface IContactoService {
	List<Contacto> obtenerListaContacto(Long codigo);
	Error getError();
}
