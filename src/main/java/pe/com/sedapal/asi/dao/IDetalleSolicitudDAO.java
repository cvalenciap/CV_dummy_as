package pe.com.sedapal.asi.dao;

import java.sql.SQLException;
import java.util.List;

import pe.com.sedapal.asi.model.DetalleSolicitud;

public interface IDetalleSolicitudDAO {

	List<DetalleSolicitud> obtenerDetallesPorIds(List<Long> idsDetallesSolicitud) throws SQLException;
}
