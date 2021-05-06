package pe.com.sedapal.asi.dao;

import java.util.List;

import pe.com.sedapal.asi.model.Tipo;

public interface ITipoDAO {
	List<Tipo> consultarTipos(String grupo);
}
