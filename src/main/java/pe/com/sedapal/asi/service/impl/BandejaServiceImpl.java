package pe.com.sedapal.asi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.sedapal.asi.dao.IBandejaDAO;
import pe.com.sedapal.asi.dao.IParametrosDAO;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.DetalleRequerimiento;
import pe.com.sedapal.asi.model.request_objects.BandejaRequest;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.request_objects.TicketContratistaRequest;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Paginacion;
import pe.com.sedapal.asi.model.response_objects.TicketContratistaResponse;
import pe.com.sedapal.asi.service.IBandejaService;
import pe.com.sedapal.asi.service.ICorreoBandejaService;
import pe.com.sedapal.asi.service.ITicketService;
import pe.com.sedapal.asi.util.AppConstants;
import pe.com.sedapal.asi.util.MailConstants;

@Service
public class BandejaServiceImpl implements IBandejaService{
	
	private Logger logger = LoggerFactory.getLogger(BandejaServiceImpl.class);
	
	@Autowired
	private IBandejaDAO dao;
	
	@Autowired
	private IParametrosDAO daoParametro;
	
	@Autowired
	private ITicketService ticketService;
	
	@Autowired
	private ICorreoBandejaService correoService;

	@Override
	public List<DetalleRequerimiento> obtenerRequerimientos(BandejaRequest bandejaRequest, PageRequest pageRequest) {		
		return dao.obtenerRequerimientos(bandejaRequest, pageRequest);
	}
	
	@Override
	public Integer revisarSolicitud(List<DetalleRequerimiento> listDetalleSolicitud) {
		Integer respuesta = 0;
		try {
			Map<Integer, String> parametrosEnvioCorreo = obtenerParametrosEnvioCorreo();
			if (parametrosEnvioCorreo.get(AppConstants.PARAM_IND_SERV_WEB_INTEG)
					.equalsIgnoreCase(AppConstants.FLAG_SI)) {
				for (DetalleRequerimiento detalle : listDetalleSolicitud) {
					if (detalle.getIndAprobacion().equals(new Long(1))
							&& detalle.getIndUltRev().equalsIgnoreCase(AppConstants.FLAG_SI)) {
						detalle.setV_id_envio(AppConstants.FLAG_IND_ENVIO);
						detalle.setV_id_ticket(generarTicket(detalle, parametrosEnvioCorreo));
					}
				}
			}
			respuesta = dao.revisarSoliciutd(listDetalleSolicitud);
			if (dao.getError() == null) {
				correoService.enviarCorreoRevision(listDetalleSolicitud, parametrosEnvioCorreo);
			} else {
				logger.warn("Error al revisar solicitud, por tanto no se enviará correo.", dao.getError());
			}
		} catch (Exception e) {
			logger.error("Error al revisar solicitud");
			e.printStackTrace();
		}
		return respuesta;
	}
	
	private String generarTicket(DetalleRequerimiento detalle, Map<Integer, String> parametros) {
		TicketContratistaRequest ticketRequest = new TicketContratistaRequest();;
		TicketContratistaResponse ticketResponse = null;
		
		ticketRequest.setResumen(detalle.getN_id_cat_par().getNomcategoria());
		ticketRequest.setDescripcion("");
		ticketRequest.setEstadoSolicitud(parametros.get(MailConstants.PARAM_DT_TICKET_ESTADO_REQ));
		ticketRequest.setCategoria(detalle.getCategoriaRequerimiento().getNomcategoria());
		ticketRequest.setObservaciones(detalle.getV_desc_obse());
		ticketRequest.setUsuarioSolicitante(detalle.getUsuarioSolicitante().getNomUsuario());
		ticketRequest.setUsuarioAfectado(detalle.getN_idtrabafec().getNomUsuario());
		ticketRequest.setPrioridad(parametros.get(MailConstants.PARAM_DT_TICKET_PRIORIDAD_REQ) == null ? "" : parametros.get(MailConstants.PARAM_DT_TICKET_PRIORIDAD_REQ));
		ticketRequest.setImpacto(parametros.get(MailConstants.PARAM_DT_TICKET_IMPACTO) == null ? "" : parametros.get(MailConstants.PARAM_DT_TICKET_IMPACTO));
		ticketRequest.setUrgencia(parametros.get(MailConstants.PARAM_DT_TICKET_URGENCIA));
		ticketRequest.setTipo(parametros.get(MailConstants.PARAM_DT_TICKET_TIPO_REQ));
		ticketRequest.setSede(detalle.getUsuarioSolicitante().getSede());
		ticketRequest.setOrigen(parametros.get(MailConstants.PARAM_DT_TICKET_ORIGEN));
		ticketRequest.setProducto(parametros.get(MailConstants.PARAM_DT_TICKET_PRODUCTO_MICROINFORM));
		ticketRequest.setTicket("");
		try {
			ticketResponse = this.ticketService.createTicket(ticketRequest, 
					parametros.get(MailConstants.PARAM_DT_URL_WS_INTEG_CONTRATISTA));
		}catch(Exception e) {
			logger.warn("No se genero ticket para el requerimiento " + detalle.getN_det_soli(), e.getCause());
			return null;
		}		
		
		if (ticketResponse != null && ticketResponse.getEstado().equalsIgnoreCase(AppConstants.ESTADO_ERROR)) {
			logger.warn("No se genero ticket para el requerimiento " + detalle.getN_det_soli());
			return null;
		}
		return ticketResponse.getResultado().getTicket();
	}		
	
	private Map<Integer, String> obtenerParametrosEnvioCorreo(){
		List<DetalleParametro> claveCorreo = daoParametro.obtenerDetallesPorParametro(MailConstants.MAIL_DET_CLAVE_CORREO_REMITENTE);
		List<DetalleParametro> correoRemitente = daoParametro.obtenerDetallesPorParametro(MailConstants.MAIL_DET_CORREO_REMITENTE);
		List<DetalleParametro> correoContratista = daoParametro.obtenerDetallesPorParametro(MailConstants.MAIL_DET_CORREO_CONTRATISTA);
		List<DetalleParametro> nombreRemitente = daoParametro.obtenerDetallesPorParametro(MailConstants.MAIL_DET_NOMBRE_REMITENTE_CORREO);
		List<DetalleParametro> nombreContratista = daoParametro.obtenerDetallesPorParametro(MailConstants.MAIL_DET_NOMBRE_CONTRATISTA_CORREO);
		List<DetalleParametro> indVisuNroSolicitud = daoParametro.obtenerDetallesPorParametro(AppConstants.PARAM_IND_VISU_NRO_SOLI);		
		List<DetalleParametro> indServWebInteg = daoParametro.obtenerDetallesPorParametro(AppConstants.PARAM_IND_SERV_WEB_INTEG);
		List<DetalleParametro> indEnvNotifContr = daoParametro.obtenerDetallesPorParametro(AppConstants.PARAM_IND_ENV_NOTIF_CONTR);
		List<DetalleParametro> indAdjArchNoti = daoParametro.obtenerDetallesPorParametro(MailConstants.PARAM_FLAG_ADJUNTAR_ARCH_NOTIFICACION);
		List<DetalleParametro> rutaFileServer = daoParametro.obtenerDetallesPorParametro(MailConstants.PARAM_CARPETA_FILE_SERVER);
		List<DetalleParametro> usuarioSeguridad = daoParametro.obtenerDetallesPorParametro(MailConstants.PARAM_CORREO_USUARIO_SEGURIDAD);
		List<DetalleParametro> urlServicioCorreo = daoParametro.obtenerDetallesPorParametro(MailConstants.PARAM_URL_WS_CORREO);
		List<DetalleParametro> claveSeguridad = daoParametro.obtenerDetallesPorParametro(MailConstants.PARAM_CORREO_CLAVE_USUARIO_SEGURIDAD);
		List<DetalleParametro> ulrServicioWebContratista = daoParametro.obtenerDetallesPorParametro(MailConstants.PARAM_FLAG_URL_WS_INTEG_CONTRATISTA);
		List<DetalleParametro> valoresGeneracionTicket = daoParametro.obtenerDetallesPorParametro(MailConstants.PARAM_TICKET);
		List<DetalleParametro> tipoEnvioCorreo = daoParametro.obtenerDetallesPorParametro(MailConstants.PARAM_TIPO_ENVIO_CORREO);
		List<DetalleParametro> indEnviarCorreoCopiaContrat = daoParametro.obtenerDetallesPorParametro(MailConstants.PARAM_FLAG_COPIAR_CONTRATISTA);
				
		Map<Integer, String> map = new HashMap<>();
		map.put(claveCorreo.get(0).getIdPara(), claveCorreo.get(0).getValorPara1());
		map.put(correoRemitente.get(0).getIdPara(), correoRemitente.get(0).getValorPara1());
		map.put(correoContratista.get(0).getIdPara(), correoContratista.get(0).getValorPara1());
		map.put(nombreRemitente.get(0).getIdPara(), nombreRemitente.get(0).getValorPara1());
		map.put(nombreContratista.get(0).getIdPara(), nombreContratista.get(0).getValorPara1());
		map.put(indVisuNroSolicitud.get(0).getIdPara(), indVisuNroSolicitud.get(0).getValorPara1());
		map.put(indServWebInteg.get(0).getIdPara(), indServWebInteg.get(0).getValorPara1());
		map.put(indEnvNotifContr.get(0).getIdPara(), indEnvNotifContr.get(0).getValorPara1());
		map.put(indAdjArchNoti.get(0).getIdDetallePara(), indAdjArchNoti.get(0).getValorPara1());
		map.put(rutaFileServer.get(0).getIdDetallePara(), rutaFileServer.get(0).getValorPara1());
		map.put(usuarioSeguridad.get(0).getIdDetallePara(), usuarioSeguridad.get(0).getValorPara1());
		map.put(claveSeguridad.get(0).getIdDetallePara(), claveSeguridad.get(0).getValorPara1());
		map.put(urlServicioCorreo.get(0).getIdDetallePara(), urlServicioCorreo.get(0).getValorPara1());
		map.put(tipoEnvioCorreo.get(0).getIdDetallePara(), tipoEnvioCorreo.get(0).getValorPara1());		
		map.put(ulrServicioWebContratista.get(0).getIdDetallePara(), ulrServicioWebContratista.get(0).getValorPara1());
		map.put(indEnviarCorreoCopiaContrat.get(0).getIdDetallePara(), indEnviarCorreoCopiaContrat.get(0).getValorPara1());
		
		valoresGeneracionTicket.stream().forEach((p) -> {
			map.put(p.getIdDetallePara(), p.getValorPara1());
		});
		
		return map;
	}					
		
	@Override
	public DetalleRequerimiento obtenerDetalleRequerimiento(BandejaRequest bandejaRequest) {
		return dao.obtenerDetalleRequerimiento(bandejaRequest);
	}

	@Override
	public Paginacion getPaginacion() {		
		return this.dao.getPaginacion();
	}

	@Override
	public Error getError() {		
		return this.dao.getError();
	}	

}