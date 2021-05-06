package pe.com.sedapal.asi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pe.com.sedapal.asi.model.ConfiguracionRevision;

public class ConfiguracionRevisionRowMapper implements RowMapper<ConfiguracionRevision> {

	@Override
	public ConfiguracionRevision mapRow(ResultSet rs, int rowNum) throws SQLException {
		ConfiguracionRevision confiRevi = new ConfiguracionRevision();
		
		confiRevi.setIdConfRev(rs.getLong("N_ID_CONFREV"));
		confiRevi.setEquipo(rs.getLong("N_EQUIPO"));
		confiRevi.setIdTrabConf(rs.getLong("N_IDTRABCONF"));
		confiRevi.setOrdenConf(rs.getInt("N_ORDEN_CONF"));
		confiRevi.setEstado(Integer.valueOf("" + rs.getLong("N_ESTADO")));
		
		return confiRevi;
	}

}
