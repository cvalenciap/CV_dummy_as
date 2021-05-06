package pe.com.sedapal.asi.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.asi.dao.IParametrosDAO;
import pe.com.sedapal.asi.dao.ITrabajadoresDAO;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.Trabajador;
import pe.com.sedapal.asi.model.request_objects.TrabajadorRequest;
import pe.com.sedapal.asi.service.IAprobacionSolicitudService;
import pe.com.sedapal.asi.util.MailConstants;

@Service
public class AprobacionSolicitudServiceImpl implements IAprobacionSolicitudService {
	
	@Autowired
	private IParametrosDAO parametrosDAO;
	
	@Autowired
	private ITrabajadoresDAO trabajadoresDAO;

	@Override
	public boolean requireAprobacion(String codTrabajador) throws Exception {
		
		if (codTrabajador == null || codTrabajador.trim().isEmpty()) {
			return true;
		}
		
		TrabajadorRequest trabajadorRequest = new TrabajadorRequest();
		trabajadorRequest.setCodTrabajador(Long.parseLong(codTrabajador));
		trabajadorRequest.setNumeroFicha(0l);
		
		List<Trabajador> trabajadores = trabajadoresDAO.obtenerTrabajadores(trabajadorRequest);
		Trabajador trabajador = null;
		
		if (trabajadores != null && !trabajadores.isEmpty()) {
			trabajador = trabajadores.get(0);
		}
		
		if (trabajador != null && trabajador.getRevisor() > 0) {
			return false;
		}
		
		Set<Integer> parametros = new HashSet<>();
		parametros.add(MailConstants.PARAM_CARGO_JEFE);
		parametros.add(MailConstants.PARAM_CARGO_GERENTE);
		parametros.add(MailConstants.PARAM_CARGO_SECRETARIA_EQUIPOS);
		parametros.add(MailConstants.PARAM_CARGO_SECRETARIA_GERENCIA_GENERAL);
		parametros.add(MailConstants.PARAM_CARGO_SECRETARIA_GERENCIAS);
		parametros.add(MailConstants.PARAM_CARGO_GERENTE_GENERAL);
		
		List<DetalleParametro> usuariosConCargo = parametrosDAO.obtenerDetallesPorParametros(parametros);
		
		if (usuariosConCargo != null && usuariosConCargo.size() > 0 && trabajador != null) {
			for(DetalleParametro usuarioConCargo : usuariosConCargo) {
				if (usuarioConCargo.getValorPara1() != null 
						&& usuarioConCargo.getValorPara1().equals(trabajador.getficha().toString())) {
					return false;
				}
			}
		}
		
		return true;
	}

}
