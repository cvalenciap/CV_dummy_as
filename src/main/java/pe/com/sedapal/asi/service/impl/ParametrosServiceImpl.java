package pe.com.sedapal.asi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.com.sedapal.asi.dao.IParametrosDAO;
import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.Parametro;
import pe.com.sedapal.asi.model.ParametroStorage;
import pe.com.sedapal.asi.model.UsuarioAfectado;
import pe.com.sedapal.asi.model.enums.TipoParametro;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.request_objects.RequestUsuarioAfectado;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Paginacion;
import pe.com.sedapal.asi.service.IParametrosService;

@Service
@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true)
public class ParametrosServiceImpl implements IParametrosService{

	
	@Autowired 
	private IParametrosDAO dao;
	
	public Paginacion getPaginacion() {
		return this.dao.getPaginacion();
	}
	
	public Error getError() {
		return this.dao.getError();
	}
	
	@Override
	public List<UsuarioAfectado> obtenerUsuariosAfectados(Long nficha, PageRequest pageRequest, RequestUsuarioAfectado requestUsuarioAfectado) {
		return dao.obtenerUsuariosAfectados(nficha, pageRequest, requestUsuarioAfectado);
	}
	
	@Override
	public List<Area> obtenerAreas() {
		return dao.obtenerAreas();
	}

	@Override
	public ParametroStorage obtenerParametroStorage(Long n_codarea) {
		return dao.obtenerParametroStorage(n_codarea);
	}
	
	@Override
	public List<Parametro> obtenerParametrosPorTipo(String codTipoParam, PageRequest pageRequest) {	
		String tipoParam = null;
		if (TipoParametro.LISTADO_VALORES.getCodigo().equals(codTipoParam)) {
			tipoParam = TipoParametro.LISTADO_VALORES.getValor();
		} else if(TipoParametro.VALOR_UNICO.getCodigo().equals(codTipoParam)) {
			tipoParam = TipoParametro.VALOR_UNICO.getValor();
		}
		
		return dao.obtenerParametrosPorTipo(tipoParam, pageRequest);
	}
	
	@Override
	public List<DetalleParametro> obtenerDetallesParametro(Integer idParametro) {
		return dao.obtenerDetallesPorParametro(idParametro);
	}
	
	@Override
	public Parametro obtenerParametrosPorId(Integer idParametro) {
		return dao.obtenerParametroPorId(idParametro);
	}
	
	@Override
	public Integer registrarListaValores(DetalleParametro dtParametro) {
		return dao.registrarListaValores(dtParametro);
	}
}
