package pe.com.sedapal.asi.dao;

import java.sql.SQLException;
import java.util.List;

import pe.com.sedapal.asi.model.ConfiguracionRevision;

public interface IConfiguracionRevisionDAO {

	List<ConfiguracionRevision> obtenerAprobadores(Long codEquipo) throws SQLException;
}
