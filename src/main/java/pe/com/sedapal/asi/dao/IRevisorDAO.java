package pe.com.sedapal.asi.dao;

import java.util.List;

import pe.com.sedapal.asi.model.Revisor;

public interface IRevisorDAO {

	List<Revisor> obtenerRevisoresPorSolicitud(Long idSolicitud);
	
}
