package pe.com.sedapal.asi.service;

import java.util.List;

import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.Parametro;
import pe.com.sedapal.asi.model.ParametroStorage;
import pe.com.sedapal.asi.model.UsuarioAfectado;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.request_objects.RequestUsuarioAfectado;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Paginacion;

public interface IParametrosService {
	public List<UsuarioAfectado> obtenerUsuariosAfectados(Long nficha, PageRequest pageRequest,
			RequestUsuarioAfectado requestUsuarioAfectado);

	public List<Area> obtenerAreas();

	public ParametroStorage obtenerParametroStorage(Long n_codarea);

	Paginacion getPaginacion();

	Error getError();

	List<Parametro> obtenerParametrosPorTipo(String tipoParametro, PageRequest pageRequest);

	List<DetalleParametro> obtenerDetallesParametro(Integer idParametro);

	Parametro obtenerParametrosPorId(Integer idParametro);

	Integer registrarListaValores(DetalleParametro dtParametro);
}
