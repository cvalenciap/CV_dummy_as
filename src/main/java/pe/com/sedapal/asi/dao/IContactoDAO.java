package pe.com.sedapal.asi.dao;

import java.util.List;

import pe.com.sedapal.asi.model.Contacto;
import pe.com.sedapal.asi.model.response_objects.Error;

public interface IContactoDAO {
	List<Contacto> obtenerListaContacto(Long codigo);
	Error getError();
}
