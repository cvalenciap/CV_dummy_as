package pe.com.sedapal.asi.dao;

import java.sql.SQLException;

import pe.com.sedapal.asi.model.ConfiguracionEquipo;

public interface IConfiguracionEquipoDAO {
	
	ConfiguracionEquipo obtenerConfEquipoPorId(Long codEquipo) throws SQLException;
	
}
