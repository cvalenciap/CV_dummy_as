package pe.com.sedapal.asi.service;

import java.util.List;

import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.response_objects.Error;

public interface IAreasService {
	List<Area> obtenerAreasEquipos(Long codigo);
	Error getError();
}
