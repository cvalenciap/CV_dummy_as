package pe.com.sedapal.asi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sedapal.asi.model.ConfiguracionEquipo;

public class ConfiguracionEquipoRowMapper implements RowMapper<ConfiguracionEquipo> {

	@Override
	public ConfiguracionEquipo mapRow(ResultSet rs, int rowNum) throws SQLException {
		ConfiguracionEquipo revisor = new ConfiguracionEquipo();
		revisor.setEquipo(rs.getLong("N_EQUIPO"));
		revisor.setTipoOrden(rs.getString("V_TIPO_ORDEN"));
		revisor.setEstado(rs.getLong("N_ESTADO"));
		
		return revisor;
	}

}
