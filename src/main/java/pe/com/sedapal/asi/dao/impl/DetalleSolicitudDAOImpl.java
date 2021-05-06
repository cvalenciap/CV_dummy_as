package pe.com.sedapal.asi.dao.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.com.sedapal.asi.dao.IDetalleSolicitudDAO;
import pe.com.sedapal.asi.mapper.DetalleSolicitudRowMapper;
import pe.com.sedapal.asi.model.DetalleSolicitud;

@Repository
public class DetalleSolicitudDAOImpl implements IDetalleSolicitudDAO {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<DetalleSolicitud> obtenerDetallesPorIds(List<Long> idsDetallesSolicitud) throws SQLException {
		String query = "SELECT N_DET_SOLI, N_ID_SOLI, N_ID_CAT_PAR, N_IDTRABAFEC FROM ARS.ARS_SOLICITUD_DT WHERE N_DET_SOLI IN (:idsDetallesSolicitud)";
		
		Map<String, List<Long>> namedParameters = Collections.singletonMap("idsDetallesSolicitud", idsDetallesSolicitud);
		
		return jdbcTemplate.query(query, namedParameters, new DetalleSolicitudRowMapper());
	}
}
