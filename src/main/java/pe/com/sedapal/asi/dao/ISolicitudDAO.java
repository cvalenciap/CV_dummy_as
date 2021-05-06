package pe.com.sedapal.asi.dao;

import java.sql.SQLException;

import pe.com.sedapal.asi.model.Solicitud;

public interface ISolicitudDAO {
	Solicitud obtenerSolicitudPorId(Long idSolicitud) throws SQLException;
}
