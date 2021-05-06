package pe.com.sedapal.asi.service;

import java.util.List;

import pe.com.sedapal.asi.model.DetalleRequerimiento;
import pe.com.sedapal.asi.model.request_objects.BandejaRequest;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Paginacion;

public interface IBandejaService {
	List<DetalleRequerimiento> obtenerRequerimientos(BandejaRequest bandejaRequest, PageRequest pageRequest);
	Integer revisarSolicitud(List<DetalleRequerimiento> listDetalleSolicitud);
	DetalleRequerimiento obtenerDetalleRequerimiento(BandejaRequest bandejaRequest);
	Paginacion getPaginacion();
	Error getError();
}
