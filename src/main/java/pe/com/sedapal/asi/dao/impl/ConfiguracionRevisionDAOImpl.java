package pe.com.sedapal.asi.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.com.sedapal.asi.dao.IConfiguracionRevisionDAO;
import pe.com.sedapal.asi.mapper.ConfiguracionRevisionRowMapper;
import pe.com.sedapal.asi.model.ConfiguracionRevision;
import pe.com.sedapal.asi.util.AppConstants;

@Repository
public class ConfiguracionRevisionDAOImpl implements IConfiguracionRevisionDAO {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<ConfiguracionRevision> obtenerAprobadores(Long codEquipo) throws SQLException {
		String query = "SELECT N_ID_CONFREV, N_EQUIPO, N_IDTRABCONF, N_ORDEN_CONF, N_ESTADO  FROM ARS.ARS_CONF_REVI WHERE N_EQUIPO = :codEquipo and N_ESTADO = :estado";
		
		Map<String, Long> namedParameters = new HashMap<>();
		namedParameters.put("codEquipo", codEquipo);
		namedParameters.put("estado", AppConstants.ESTADO_ACTIVO);
		
		return jdbcTemplate.query(query, namedParameters, new ConfiguracionRevisionRowMapper());
	}

}
