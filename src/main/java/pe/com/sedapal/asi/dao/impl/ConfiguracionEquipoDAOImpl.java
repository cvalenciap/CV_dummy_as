package pe.com.sedapal.asi.dao.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.com.sedapal.asi.dao.IConfiguracionEquipoDAO;
import pe.com.sedapal.asi.mapper.ConfiguracionEquipoRowMapper;
import pe.com.sedapal.asi.model.ConfiguracionEquipo;

@Repository
public class ConfiguracionEquipoDAOImpl implements IConfiguracionEquipoDAO {
	private Logger logger = LoggerFactory.getLogger(ConfiguracionEquipoDAOImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public ConfiguracionEquipo obtenerConfEquipoPorId(Long codEquipo) throws SQLException {
		String query = "SELECT N_EQUIPO, V_TIPO_ORDEN, N_ESTADO FROM ARS.ARS_CONF_EQUIPO WHERE N_EQUIPO = :codEquipo";
		Map<String, Long> namedParameters = Collections.singletonMap("codEquipo", codEquipo);
		
		ConfiguracionEquipo confEquipo = null;
		
		try {
			confEquipo = jdbcTemplate.queryForObject(query, namedParameters, new ConfiguracionEquipoRowMapper());
		} catch(Exception ex) {
			logger.error("No hay configuracion para el equipo " + codEquipo);
		}
		
		return confEquipo;
	}

}
