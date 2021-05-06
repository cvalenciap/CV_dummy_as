package pe.com.sedapal.asi.service;

import java.util.List;
import java.util.Map;

import pe.com.sedapal.asi.model.DetalleRequerimiento;

public interface ICorreoBandejaService {
	public void enviarCorreoRevision(List<DetalleRequerimiento> listaRequerimientos,
			Map<Integer, String> parametrosEnvioCorreo) throws Exception;
}
