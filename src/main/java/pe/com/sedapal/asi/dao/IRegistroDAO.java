package pe.com.sedapal.asi.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.asi.model.AdjuntoMensaje;
import pe.com.sedapal.asi.model.DetalleSolicitud;
import pe.com.sedapal.asi.model.Solicitud;
import pe.com.sedapal.asi.model.response_objects.Error;

public interface IRegistroDAO {
	public Integer generarSolicitud(Solicitud cabecera, List<DetalleSolicitud> detalle, Long n_cod_area) throws SQLException;
	public List<AdjuntoMensaje> guardarAdjuntos(List<AdjuntoMensaje> adjuntos) throws SQLException;
	public Error getError();
	public Map<String, Object> getRequerimientos();
}
