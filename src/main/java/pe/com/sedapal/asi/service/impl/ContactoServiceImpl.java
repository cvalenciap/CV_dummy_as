package pe.com.sedapal.asi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.com.sedapal.asi.dao.IContactoDAO;
import pe.com.sedapal.asi.model.Contacto;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Nivel;
import pe.com.sedapal.asi.service.IContactoService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ContactoServiceImpl implements IContactoService {
	
	@Autowired
	private IContactoDAO dao;
	
	public Error getError() {
		return this.dao.getError();
	}
	
	@Override
	public List<Contacto> obtenerListaContacto(Long codigo) {
		List<Contacto> listaContactos = new ArrayList<>();
		listaContactos = this.dao.obtenerListaContacto(codigo);
		if(this.dao.getError() != null) {
			if (this.dao.getError().getNivel() == Nivel.REQUEST) {
				throw new BadRequestException();
			} else {
				throw new InternalServerErrorException();
			}
		}
		return listaContactos;
	}
}
