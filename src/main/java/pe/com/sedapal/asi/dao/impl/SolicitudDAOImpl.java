package pe.com.sedapal.asi.dao.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.com.sedapal.asi.dao.ISolicitudDAO;
import pe.com.sedapal.asi.mapper.SolicitudRowMapper;
import pe.com.sedapal.asi.model.Solicitud;

@Repository
public class SolicitudDAOImpl implements ISolicitudDAO {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Solicitud obtenerSolicitudPorId(Long idSolicitud) throws SQLException {
		String query = "SELECT N_ID_SOLI, N_IDTRABSOLI, D_FECHA_SOLI, N_IND_ALTA, N_IDARCHADJU, N_ESTADO  FROM ARS.ARS_SOLICITUD WHERE N_ID_SOLI = :idSolicitud";
		
		Map<String, Long> namedParameters = Collections.singletonMap("idSolicitud", idSolicitud);
		
		return jdbcTemplate.queryForObject(query, namedParameters, new SolicitudRowMapper());
	}
}
