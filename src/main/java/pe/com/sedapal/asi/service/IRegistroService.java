package pe.com.sedapal.asi.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.asi.model.AdjuntoMensaje;
import pe.com.sedapal.asi.model.DetalleSolicitud;
import pe.com.sedapal.asi.model.Solicitud;

public interface IRegistroService {
	public Map<String, Object> generarSolicitud(Solicitud cabecera, List<DetalleSolicitud> detalle, Long n_cod_area) throws SQLException;
	public List<AdjuntoMensaje> guardarAdjuntos(List<AdjuntoMensaje> adjuntos) throws SQLException;
}
