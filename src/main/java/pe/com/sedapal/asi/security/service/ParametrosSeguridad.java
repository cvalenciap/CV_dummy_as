package pe.com.sedapal.asi.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.sedapal.asi.dao.IParametrosDAO;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.util.AppConstants;

@Component
public class ParametrosSeguridad {
	private static final Logger logger = LoggerFactory.getLogger(ParametrosSeguridad.class);

	@Autowired
	private IParametrosDAO parametrosDAO;
	
	private String urlWs;
	
	public String getUrlWsSeguridad() {
		try {
			logger.info("Obteniendo url de servicio web de seguridad...");
			List<DetalleParametro> paramUrlSeguridad = parametrosDAO.obtenerDetallesPorParametro(AppConstants.PARAM_URL_WS_SEGURIDAD);
			
			if (paramUrlSeguridad != null && paramUrlSeguridad.size() > 0) {
				urlWs = paramUrlSeguridad.get(0).getValorPara1();
			} else {
				logger.warn("No se obtuvo la URL de Seguridad.");
			}
		} catch (Exception e) {
			logger.error("Error al obtener url del servicio web de seguridad!", e.getCause());
		}
		
		return urlWs;
	}
}
