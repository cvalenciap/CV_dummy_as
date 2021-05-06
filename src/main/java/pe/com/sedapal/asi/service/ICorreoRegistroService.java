package pe.com.sedapal.asi.service;

import java.util.List;

import pe.com.sedapal.asi.model.DetalleRequerimiento;
import pe.com.sedapal.asi.model.Solicitud;

public interface ICorreoRegistroService {
	
	public void aprobacionEnviarCorreo(Solicitud solicitud, List<DetalleRequerimiento> requerimientos, boolean requiereAprobacion) throws Exception;
}
