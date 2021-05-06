package pe.com.sedapal.asi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.com.sedapal.asi.dao.IAreasDAO;
import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Nivel;
import pe.com.sedapal.asi.model.response_objects.Paginacion;
import pe.com.sedapal.asi.service.IAreasService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AreasServiceImpl implements IAreasService {
	@Autowired
	private IAreasDAO dao;
	
	public Paginacion getPaginacion() {
		return this.dao.getPaginacion();
	}
	
	public Error getError() {
		return this.dao.getError();
	}
	
	@Override
	public List<Area> obtenerAreasEquipos(Long codigo) {
		List<Area> listaAreas = new ArrayList<>();
		listaAreas = this.dao.obtenerAreasEquipos(codigo);
		if(this.dao.getError() != null) {
			if (this.dao.getError().getNivel() == Nivel.REQUEST) {
				throw new BadRequestException();
			} else {
				throw new InternalServerErrorException();
			}
		}
		return listaAreas;
	}
}
