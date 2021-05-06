package pe.com.sedapal.asi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import pe.com.sedapal.asi.dao.IBandejaDAO;
import pe.com.sedapal.asi.dao.IParametrosDAO;
import pe.com.sedapal.asi.dao.IRevisorDAO;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.DetalleRequerimiento;
import pe.com.sedapal.asi.model.Revisor;
import pe.com.sedapal.asi.model.request_objects.BandejaRequest;
import pe.com.sedapal.asi.service.ICorreoBandejaService;
import pe.com.sedapal.asi.service.IFileServerService;
import pe.com.sedapal.asi.util.AppConstants;
import pe.com.sedapal.asi.util.MailConstants;
import pe.com.sedapal.asi.util.MustacheUtil;
import pe.com.sedmail.cliente.bean.ArchivoAdjunto;
import pe.com.sedmail.cliente.bean.ParametrosCorreo;
import pe.com.sedmail.cliente.ws.SedmailClienteWs;

@Component
public class CorreoAprobacionServiceImpl implements ICorreoBandejaService{
	
	private Logger logger = LoggerFactory.getLogger(CorreoAprobacionServiceImpl.class);
	
	@Autowired
	private IBandejaDAO bandejaDao;
	
	@Autowired
	private IParametrosDAO parametroDao;
	
	@Autowired
	private IRevisorDAO revisorDao;
	
	@Autowired
	private IFileServerService fileServerService;
	
	@Async("threadPoolTaskExecutor")
	@Override
	public void enviarCorreoRevision(List<DetalleRequerimiento> listaRequerimientos,
			Map<Integer, String> parametrosEnvioCorreo) throws Exception {
		Map<Integer, String> formatosCorreo = obtenerFormatosCorreo();
		List<DetalleRequerimiento> listReqAprob = new ArrayList<DetalleRequerimiento>(); 
		List<DetalleRequerimiento> listReqPendAprob = new ArrayList<DetalleRequerimiento>();
		List<DetalleRequerimiento> listReqDesaprob = new ArrayList<DetalleRequerimiento>();
		
		DetalleRequerimiento objReq = null;
		for(DetalleRequerimiento requerimiento: listaRequerimientos) {		
			objReq = new DetalleRequerimiento();
			BandejaRequest request = new BandejaRequest();
			request.setIdSolicitud(requerimiento.getN_id_soli());
			request.setIdDetalleSolicitud(requerimiento.getN_det_soli());
			request.setCategoriaRequerimiento(requerimiento.getN_id_cat_par().getIdcategoria());
			// Obtendo detalle del requerimiento revisado
			objReq = bandejaDao.obtenerDetalleRequerimiento(request);
			if(objReq != null) {
				if(objReq.getN_estado_par().intValue() == AppConstants.PARAM_DT_ESTD_REQ_APROB.intValue()) {
					listReqAprob.add(objReq);												
				}else if(objReq.getN_estado_par().intValue() == AppConstants.PARAM_DT_ESTD_REQ_PEND_APROB.intValue() &&
						objReq.getTipoOrdenAprobacion().equalsIgnoreCase(AppConstants.TIPO_APROBACION_CON_ORDEN)) {
					listReqPendAprob.add(objReq);
				}else if(objReq.getN_estado_par().intValue() == AppConstants.PARAM_DT_ESTD_REQ_DESAPROB.intValue()) {
					listReqDesaprob.add(objReq);
				}
			}											
		}		
		if(listReqAprob.size() > 0) { // Envio correo de los requerimientos Aprobados
			enviarCorreoSolicitante(listReqAprob, AppConstants.PARAM_DT_ESTD_REQ_APROB, formatosCorreo, parametrosEnvioCorreo);
			enviarCorreoContratista(listReqAprob, formatosCorreo, parametrosEnvioCorreo);
		}		
		if(listReqPendAprob.size() > 0) { // Envio correo de los requerimientos Pendientes
			enviarCorreoAprobador(listReqPendAprob, formatosCorreo, parametrosEnvioCorreo);
		}		
		if(listReqDesaprob.size() > 0) { // Envio correo de los requerimientos Desaprobados
			enviarCorreoSolicitante(listReqDesaprob, AppConstants.PARAM_DT_ESTD_REQ_DESAPROB, formatosCorreo, parametrosEnvioCorreo);	
		}
	}
	
	private void enviarCorreoSolicitante(List<DetalleRequerimiento> listaReq, Integer estadoReq,
			Map<Integer, String> formatos, Map<Integer, String> parametrosEnvioCorreo) {
		SedmailClienteWs sedmailClienteWs = new SedmailClienteWs();
		Set<Long> listaUnicaNumSoli = listaUnicaSolicitudes(listaReq);
		List<DetalleRequerimiento> listAgrupReqPorSoli = null;
		for (Long numSoli : listaUnicaNumSoli) {
			listAgrupReqPorSoli = new ArrayList<DetalleRequerimiento>();
			for (DetalleRequerimiento requerimiento : listaReq) {
				if (numSoli.equals(requerimiento.getN_id_soli())) {
					listAgrupReqPorSoli.add(requerimiento);
				}
			}
			try {
				ParametrosCorreo parametrosCorreo = this.buildParametrosCorreoSolicitante(listAgrupReqPorSoli, formatos,
						estadoReq, parametrosEnvioCorreo);
				logger.info("Inicia envio del correo al solicitante." + parametrosCorreo.getDestinatario());
				sedmailClienteWs.enviarCorreo(parametrosCorreo,
						parametrosEnvioCorreo.get(MailConstants.PARAM_DT_URL_WS_CORREO),
						parametrosEnvioCorreo.get(MailConstants.PARAM_DT_CORREO_USUARIO_SEGURIDAD),
						parametrosEnvioCorreo.get(MailConstants.PARAM_DT_CORREO_CLAVE_USUARIO_SEGURIDAD));
			} catch (Exception e) {
				logger.error("Error al enviar correo al solicitante con solicitud " + numSoli, e.getCause());
			}
		}
	}
	
	private ParametrosCorreo buildParametrosCorreoSolicitante(List<DetalleRequerimiento> listaReq,
			Map<Integer, String> formatos, Integer estadoReq, Map<Integer, String> parametrosEnvioCorreo) {
		Integer mensajeInicialCorreo = null;
		Integer asuntoCorreo = null;
		String flagCopiarCorreoContratista = null;
		
		if (estadoReq == AppConstants.PARAM_DT_ESTD_REQ_APROB) {
			mensajeInicialCorreo = AppConstants.PARAM_DT_MSJ_INI_SOLIC_APROB;
			asuntoCorreo = AppConstants.PARAM_DT_ASUNTO_SOLIC_POR_APROB;
			
			flagCopiarCorreoContratista = parametrosEnvioCorreo.get(MailConstants.PARAM_DET_FLAG_COPIAR_CONTRATISTA);
		} else if (estadoReq == AppConstants.PARAM_DT_ESTD_REQ_DESAPROB) {
			mensajeInicialCorreo = AppConstants.PARAM_DT_MSJ_INI_SOLIC_DESAPROB;
			asuntoCorreo = AppConstants.PARAM_DT_ASUNTO_SOLIC_POR_DESAPROB;
		}
		
		// Genero Html que tendra el contenido del correo
		StringBuilder cuerpoCorreo = new StringBuilder()
				.append(generarCabCorreo(listaReq.get(0), 
						formatos.get(mensajeInicialCorreo),
						formatos.get(AppConstants.PARAM_DT_FORMATO_CORREO_CAB),
						parametrosEnvioCorreo.get(AppConstants.PARAM_IND_VISU_NRO_SOLI),
						null,
						MailConstants.USUARIO_SOLICITANTE,
						null))
				.append(generarDetCorreo(listaReq, formatos))
				.append(formatos.get(AppConstants.PARAM_DT_MSJ_FINAL_CORREO));
		// Construyo parametro usado para el envio de correo
		ParametrosCorreo parametroCorreo = new ParametrosCorreo();
		parametroCorreo.setIdTipoEnvio(Integer.parseInt(parametrosEnvioCorreo.get(MailConstants.PARAM_DT_TIPO_ENVIO_CORREO)));
		parametroCorreo.setRemitente(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_NOMBRE_REMITENTE_CORREO));
		parametroCorreo.setRemitenteCorreo(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CORREO_REMITENTE));
		parametroCorreo.setRemitenteClave(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CLAVE_CORREO_REMITENTE));
//		parametroCorreo.setDestinatario(Arrays.asList(listaReq.get(0).getUsuarioSolicitante().getCorreo()));
		parametroCorreo.setDestinatario(this.obtenerDestinatarios(listaReq.get(0).getUsuarioSolicitante().getCorreo()));
		
		if (AppConstants.FLAG_SI.equalsIgnoreCase(flagCopiarCorreoContratista)) {
			parametroCorreo.setCopia(Arrays.asList(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CORREO_CONTRATISTA)));
		}
		
		parametroCorreo.setAsunto(formatos.get(asuntoCorreo));
		parametroCorreo.setCuerpoHTML(cuerpoCorreo.toString());
		parametroCorreo.setArchivosAdjuntos(new ArrayList<ArchivoAdjunto>());
		parametroCorreo.setUserOffice365(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CORREO_REMITENTE));
		parametroCorreo.setPassOffice365(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CLAVE_CORREO_REMITENTE));
		
		return parametroCorreo;
	}		
			
	private void enviarCorreoContratista(List<DetalleRequerimiento> listaReq, Map<Integer, String> formatos,
			Map<Integer, String> parametrosEnvioCorreo) {
		SedmailClienteWs sedmailClienteWs = new SedmailClienteWs();
		for (DetalleRequerimiento requerimiento : listaReq) {
			if (parametrosEnvioCorreo.get(AppConstants.PARAM_IND_ENV_NOTIF_CONTR).equalsIgnoreCase(AppConstants.FLAG_SI)
					/*&& requerimiento.getV_id_ticket() != null*/) {
				try {
					ParametrosCorreo parametrosCorreo = this.buildParametrosCorreoContratista(requerimiento, formatos,
							parametrosEnvioCorreo);
					logger.info("Inicia envio del correo al contratista." + parametrosCorreo.getDestinatario());
					sedmailClienteWs.enviarCorreo(parametrosCorreo,
							parametrosEnvioCorreo.get(MailConstants.PARAM_DT_URL_WS_CORREO),
							parametrosEnvioCorreo.get(MailConstants.PARAM_DT_CORREO_USUARIO_SEGURIDAD),
							parametrosEnvioCorreo.get(MailConstants.PARAM_DT_CORREO_CLAVE_USUARIO_SEGURIDAD));
				} catch (Exception e) {
					logger.error(
							"Error al enviar correo al Contratista con requerimiento " + requerimiento.getN_det_soli(),
							e.getCause());
				}
			}
		}
	}
	
	private ParametrosCorreo buildParametrosCorreoContratista(DetalleRequerimiento requerimiento,
			Map<Integer, String> formatos, Map<Integer, String> parametrosEnvioCorreo) {
		
		List<DetalleRequerimiento> listaReq = new ArrayList<>();
		listaReq.add(requerimiento);
		
		Long idSolicitud = requerimiento.getN_id_soli();
		List<Revisor> revisores = revisorDao.obtenerRevisoresPorSolicitud(idSolicitud);
		
		// Genero Html que tendra el contenido del correo
		StringBuilder cuerpoCorreo = new StringBuilder()
				.append(generarCabCorreo(requerimiento,
						formatos.get(AppConstants.PARAM_DT_MSJ_INI_CONTRATISTA),
						formatos.get(AppConstants.PARAM_DT_FORMATO_CORREO_CAB_CONTRAT),
						parametrosEnvioCorreo.get(AppConstants.PARAM_IND_VISU_NRO_SOLI),
						null,
						MailConstants.USUARIO_CONTRATISTA,
						revisores))
				.append(generarDetCorreo(listaReq, formatos))
				.append(formatos.get(AppConstants.PARAM_DT_MSJ_FINAL_CORREO));
		
		// Construyo parametro usado para el envio de correo
		ParametrosCorreo parametroCorreo = new ParametrosCorreo();
		parametroCorreo.setIdTipoEnvio(Integer.parseInt(parametrosEnvioCorreo.get(MailConstants.PARAM_DT_TIPO_ENVIO_CORREO)));
		parametroCorreo.setRemitente(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_NOMBRE_REMITENTE_CORREO));
		parametroCorreo.setRemitenteCorreo(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CORREO_REMITENTE));
		parametroCorreo.setRemitenteClave(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CLAVE_CORREO_REMITENTE));
//		parametroCorreo.setDestinatario(Arrays.asList(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CORREO_CONTRATISTA)));
		parametroCorreo.setDestinatario(this.obtenerDestinatarios(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CORREO_CONTRATISTA)));
		parametroCorreo.setAsunto(formatos.get(AppConstants.PARAM_DT_ASUNTO_CONTRATISTA) + (requerimiento.getV_id_ticket() != null ? " " + requerimiento.getV_id_ticket() : "") );
		parametroCorreo.setCuerpoHTML(cuerpoCorreo.toString());
		
		if (parametrosEnvioCorreo.get(MailConstants.PARAM_DT_FLAG_ADJUNTAR_ARCH_NOTIFICACION)
				.equalsIgnoreCase(AppConstants.FLAG_SI)) {
			parametroCorreo.setArchivosAdjuntos(obtenerArchivoAdjuntos(requerimiento,
					parametrosEnvioCorreo.get(MailConstants.PARAM_DT_CARPETA_FILE_SERVER)));
		} else {
			parametroCorreo.setArchivosAdjuntos(new ArrayList<ArchivoAdjunto>());
		}
		parametroCorreo.setUserOffice365(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CORREO_REMITENTE));
		parametroCorreo.setPassOffice365(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CLAVE_CORREO_REMITENTE));
		return parametroCorreo;
	}	
		
	private void enviarCorreoAprobador(List<DetalleRequerimiento> listaReq, Map<Integer, String> formatos,
			Map<Integer, String> parametrosEnvioCorreo) {
		SedmailClienteWs sedmailClienteWs = new SedmailClienteWs();
		Set<Long> listaUnicaNumSoli = listaUnicaSolicitudes(listaReq);
		List<DetalleRequerimiento> listAgrupReqPorSoli = null;
		for (Long numSoli : listaUnicaNumSoli) {
			listAgrupReqPorSoli = new ArrayList<DetalleRequerimiento>();
			for (DetalleRequerimiento requerimiento : listaReq) {
				if (numSoli.equals(requerimiento.getN_id_soli())) {
					listAgrupReqPorSoli.add(requerimiento);
				}
			}
			try {
				ParametrosCorreo parametrosCorreo = this.buildParametrosCorreoAprobador(listAgrupReqPorSoli, formatos,
						parametrosEnvioCorreo);
				logger.info("Inicia envio del correo al aprobador." + parametrosCorreo.getDestinatario());
				sedmailClienteWs.enviarCorreo(parametrosCorreo,
						parametrosEnvioCorreo.get(MailConstants.PARAM_DT_URL_WS_CORREO),
						parametrosEnvioCorreo.get(MailConstants.PARAM_DT_CORREO_USUARIO_SEGURIDAD),
						parametrosEnvioCorreo.get(MailConstants.PARAM_DT_CORREO_CLAVE_USUARIO_SEGURIDAD));
			} catch (Exception e) {
				logger.error("Error al enviar correo al Aprobador con solicitud " + numSoli, e.getCause());
			}
		}
	}
	
	private ParametrosCorreo buildParametrosCorreoAprobador(List<DetalleRequerimiento> listaReq,
			Map<Integer, String> formatos, Map<Integer, String> parametrosEnvioCorreo) {
		// Genero Html que tendra el contenido del correo
		StringBuilder cuerpoCorreo = new StringBuilder()
				.append(generarCabCorreo(listaReq.get(0), 
						formatos.get(AppConstants.PARAM_DT_MSJ_INI_REVISOR),
						formatos.get(AppConstants.PARAM_DT_FORMATO_CORREO_CAB),
						parametrosEnvioCorreo.get(AppConstants.PARAM_IND_VISU_NRO_SOLI),
						listaReq.get(0).getRevisor(),
						MailConstants.USUARIO_REVISOR, 
						null))
				.append(generarDetCorreo(listaReq, formatos))
				.append(formatos.get(AppConstants.PARAM_DT_MSJ_FINAL_CORREO));
		// Construyo parametro usado para el envio de correo
		ParametrosCorreo parametroCorreo = new ParametrosCorreo();
		parametroCorreo.setIdTipoEnvio(Integer.parseInt(parametrosEnvioCorreo.get(MailConstants.PARAM_DT_TIPO_ENVIO_CORREO)));
		parametroCorreo.setRemitente(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_NOMBRE_REMITENTE_CORREO));
		parametroCorreo.setRemitenteCorreo(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CORREO_REMITENTE));
		parametroCorreo.setRemitenteClave(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CLAVE_CORREO_REMITENTE));
		// Inicio - mejora para enviar correo al siguiente aprobador
//		parametroCorreo.setDestinatario(Arrays.asList(listaReq.get(0).getCorreoSiguienteRevisor()));
//		parametroCorreo.setDestinatario(this.obtenerDestinatarios(listaReq.get(0).getCorreoSiguienteRevisor()));
		parametroCorreo.setDestinatario(this.obtenerDestinatarios(listaReq.get(0).getRevisor().getCorreo()));
		// Fin - mejora para enviar correo al siguiente aprobador
		
		parametroCorreo.setAsunto(formatos.get(AppConstants.PARAM_DT_ASUNTO_APROBADOR));
		parametroCorreo.setCuerpoHTML(cuerpoCorreo.toString());
		parametroCorreo.setArchivosAdjuntos(new ArrayList<ArchivoAdjunto>());
		parametroCorreo.setUserOffice365(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CORREO_REMITENTE));
		parametroCorreo.setPassOffice365(parametrosEnvioCorreo.get(MailConstants.MAIL_DET_CLAVE_CORREO_REMITENTE));
		return parametroCorreo;
	}
	
	private String generarCabCorreo(DetalleRequerimiento detalle, String msjIntroduccion, String formato,
			String indVisuSoli, Revisor revisor, String tipoUsuarioEnvio, List<Revisor> revisores) {
		
		Map<String, Object> datosCabeceraCorreo = new HashMap<>();
		if(tipoUsuarioEnvio.equals(MailConstants.USUARIO_SOLICITANTE)) {			
			datosCabeceraCorreo.put("nombres", detalle.getUsuarioSolicitante().getNomUsuario() != null ? detalle.getUsuarioSolicitante().getNomUsuario() : "");
			datosCabeceraCorreo.put("usuario", detalle.getUsuarioSolicitante().getCodUsuario() != null ? detalle.getUsuarioSolicitante().getCodUsuario() : "");
			
			List<String> aprobadores = Arrays.asList(detalle.getAprobadores().split("\\s*,\\s*"));
			
			datosCabeceraCorreo.put("listaaprobadores", aprobadores);
		} else if(tipoUsuarioEnvio.equals(MailConstants.USUARIO_CONTRATISTA)) {			
			msjIntroduccion = msjIntroduccion.replace("{{nroticket}}", (detalle.getV_id_ticket() != null ? detalle.getV_id_ticket() : ""));	
			
			List<String> aprobadores = new ArrayList<>();
			
			if (revisores != null && !revisores.isEmpty()) {
				aprobadores = this.obtenerOrdenAprobadoresAreas(revisores);
			}
			
			if (aprobadores != null && aprobadores.size() > 0) {
				Collections.sort(aprobadores);
				String aprobadoPor = String.join("<br/>", aprobadores);
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_APROBADO_POR, aprobadoPor);
			} else {
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_APROBADO_POR, "");
			}
		} else if(tipoUsuarioEnvio.equals(MailConstants.USUARIO_REVISOR)) {			
			datosCabeceraCorreo.put("nombres", revisor.getApPaterno()+" "+revisor.getApMaterno()+", "+revisor.getNombres());
			datosCabeceraCorreo.put("usuario", AppConstants.PREFIX_ASI+revisor.getNficha());
			List<String> aprobadores = Arrays.asList(detalle.getAprobadores().split("\\s*,\\s*"));			
			datosCabeceraCorreo.put("listaaprobadores", aprobadores);
		}
		datosCabeceraCorreo.put("msjintroduccion", msjIntroduccion != null ? msjIntroduccion : "");
		datosCabeceraCorreo.put("mostrarsolicitud", indVisuSoli.equalsIgnoreCase(AppConstants.FLAG_SI) ? true : false);
		datosCabeceraCorreo.put("numerosolicitud", detalle.getN_id_soli());
		datosCabeceraCorreo.put("fechacreacionreq", detalle.getFechaSolicitud() != null ? detalle.getFechaSolicitud() : "");
		datosCabeceraCorreo.put("usuariosolic", detalle.getUsuarioSolicitante().getCodUsuario() != null ? detalle.getUsuarioSolicitante().getCodUsuario() : "");
		datosCabeceraCorreo.put("nombressolic", detalle.getUsuarioSolicitante().getNomUsuario() != null ? detalle.getUsuarioSolicitante().getNomUsuario() : "");
		datosCabeceraCorreo.put("cargosolic", detalle.getUsuarioSolicitante().getDescSituacion() != null ? detalle.getUsuarioSolicitante().getDescSituacion() : "");
		datosCabeceraCorreo.put("sede", detalle.getUsuarioSolicitante().getSede() != null ? detalle.getUsuarioSolicitante().getSede() : "");
		datosCabeceraCorreo.put("nomgerencia", detalle.getUsuarioSolicitante().getDescGerencia() != null ? detalle.getUsuarioSolicitante().getDescGerencia() : "");
		datosCabeceraCorreo.put("nomequipo", detalle.getUsuarioSolicitante().getDescEquipo() != null ? detalle.getUsuarioSolicitante().getDescEquipo() : "");
		datosCabeceraCorreo.put("correo", detalle.getUsuarioSolicitante().getCorreo() != null ? detalle.getUsuarioSolicitante().getCorreo() : "");
		datosCabeceraCorreo.put("fichadni", detalle.getUsuarioSolicitante().getDni() != null ? detalle.getUsuarioSolicitante().getDni() : "");
		datosCabeceraCorreo.put("telefonoanexo", detalle.getUsuarioSolicitante().getTelefono() != null ? detalle.getUsuarioSolicitante().getTelefono() : "");
		
		String result = MustacheUtil.procesarCabeceraCorreo(formato, datosCabeceraCorreo);
		return result;
	}
	
	private List<String> obtenerOrdenAprobadoresAreas(List<Revisor> revisores) {
		boolean conOrden = false;
		String formatoConOrden = "%s %s (%s/%s)";
		String formatoSinOrden = "%s (%s/%s)";
		List<String> aprobadores = new ArrayList<>();
		
		for(Revisor reviewer : revisores) {
			if(reviewer.getOrden() != null && reviewer.getOrden().longValue() > 0) {
				conOrden = true;
				break;
			}
		}
		
		String orderAprobadorArea = null;
		for(Revisor aprobador : revisores) {
			if (conOrden) {
				orderAprobadorArea 
					= String.format(formatoConOrden, 
							aprobador.getOrden(), 
							aprobador.getNombreCompleto(),
							aprobador.getAbrevAreaSuperior(),
							aprobador.getAbrevArea());
				aprobadores.add(orderAprobadorArea);
			} else {
				orderAprobadorArea 
					= String.format(formatoSinOrden,  
							aprobador.getNombreCompleto(),
							aprobador.getAbrevAreaSuperior(),
							aprobador.getAbrevArea());
				aprobadores.add(orderAprobadorArea);
			}
		}
		
		return aprobadores;
	}
	
	private String generarDetCorreo(List<DetalleRequerimiento> listaReq, Map<Integer, String> formatos) {
		List<DetalleRequerimiento> listaReqRecTIC = new ArrayList<>();
		List<DetalleRequerimiento> listaReqAccApl = new ArrayList<>();
		List<Map<String, Object>> solicitudDt = null;
		StringBuilder respuesta = new StringBuilder(); 
		
		for(DetalleRequerimiento req: listaReq) {
			if(req.getN_id_cat_par().getIdcategoria().intValue() == AppConstants.PARAM_DT_CATEGORIA_ACCESO_RECURSOSTIC) {
				listaReqRecTIC.add(req);
			}else if(req.getN_id_cat_par().getIdcategoria().intValue() == AppConstants.PARAM_DT_CATEGORIA_ACCESO_APLICACIONES) {
				listaReqAccApl.add(req);
			}
		}		
		Map<String, Object> mapDatosDetalleCorreo = null;
		
		if(listaReqRecTIC.size() > 0) {
			solicitudDt = new ArrayList<>();			
			for(DetalleRequerimiento req: listaReqRecTIC) {
				mapDatosDetalleCorreo = new HashMap<>();
				mapDatosDetalleCorreo.put("ticket", req.getV_id_ticket() != null ? req.getV_id_ticket() : "");
				mapDatosDetalleCorreo.put("usuarioafectado", req.getN_idtrabafec().getNomUsuario() != null ? req.getN_idtrabafec().getNomUsuario() : "");
				mapDatosDetalleCorreo.put("uficha", req.getN_idtrabafec().getDni() != null ? req.getN_idtrabafec().getDni() : "");
				mapDatosDetalleCorreo.put("usituacion", req.getN_idtrabafec().getDescSituacion() != null ? req.getN_idtrabafec().getDescSituacion() : "");
				mapDatosDetalleCorreo.put("uequipo", req.getN_idtrabafec().getDescEquipo() != null ? req.getN_idtrabafec().getDescEquipo() : "");
				mapDatosDetalleCorreo.put("utelefono", req.getN_idtrabafec().getTelefono() != null ? req.getN_idtrabafec().getTelefono() : "");
				mapDatosDetalleCorreo.put("recursotic", req.getCategoriaRequerimiento().getNomcategoria() != null ? req.getCategoriaRequerimiento().getNomcategoria() : "");
				mapDatosDetalleCorreo.put("accion", req.getN_accion_par().getNombreaccion() != null ? req.getN_accion_par().getNombreaccion() : "");
				mapDatosDetalleCorreo.put("cuentared", req.getV_cuenta_red() != null ? req.getV_cuenta_red() : "");
				mapDatosDetalleCorreo.put("codigoinventario", req.getV_cod_inven() != null ? req.getV_cod_inven() : "");
				mapDatosDetalleCorreo.put("fechafinvigencia", req.getFechafinVigencia() != null ? req.getFechafinVigencia() : "");
				mapDatosDetalleCorreo.put("observaciones", req.getV_desc_obse() != null ? req.getV_desc_obse() : "");
				mapDatosDetalleCorreo.put("archivoadjunto", req.getN_idarchadju().getNombreAdjunto() != null ? req.getN_idarchadju().getNombreAdjunto() : "");
				solicitudDt.add(mapDatosDetalleCorreo);
			}					
			String result = MustacheUtil.procesarDetallesCorreo(formatos.get(AppConstants.PARAM_DT_FORMATO_CORREO_DET_REC_TIC), solicitudDt);			
			respuesta.append(result);			
		}					
		if(listaReqAccApl.size() > 0) {
			solicitudDt = new ArrayList<>();
			for(DetalleRequerimiento req: listaReqAccApl) {
				mapDatosDetalleCorreo = new HashMap<>();				
				mapDatosDetalleCorreo.put("ticket", req.getV_id_ticket() != null ? req.getV_id_ticket() : "");
				mapDatosDetalleCorreo.put("usuarioafectado", req.getN_idtrabafec().getNomUsuario() != null ? req.getN_idtrabafec().getNomUsuario() : "");
				mapDatosDetalleCorreo.put("uficha", req.getN_idtrabafec().getDni() != null ? req.getN_idtrabafec().getDni() : "");
				mapDatosDetalleCorreo.put("usituacion", req.getN_idtrabafec().getDescSituacion() != null ? req.getN_idtrabafec().getDescSituacion() : "");
				mapDatosDetalleCorreo.put("uequipo", req.getN_idtrabafec().getDescEquipo() != null ? req.getN_idtrabafec().getDescEquipo() : "");
				mapDatosDetalleCorreo.put("utelefono", req.getN_idtrabafec().getTelefono() != null ? req.getN_idtrabafec().getTelefono() : "");
				mapDatosDetalleCorreo.put("nombresistema", req.getCategoriaRequerimiento().getNomcategoria() != null ? req.getCategoriaRequerimiento().getNomcategoria() : "");
				mapDatosDetalleCorreo.put("perfiltransaccion", req.getV_perfil_tran() != null ? req.getV_perfil_tran() : req.getV_perfil_tran());
				mapDatosDetalleCorreo.put("accion", req.getN_accion_par().getNombreaccion() != null ? req.getN_accion_par().getNombreaccion() : "");
				mapDatosDetalleCorreo.put("sustento", req.getN_idarchadju().getNombreAdjunto() != null ? req.getN_idarchadju().getNombreAdjunto() : "");
				mapDatosDetalleCorreo.put("fechafinvigencia", req.getFechafinVigencia() != null ? req.getFechafinVigencia() : "");
				mapDatosDetalleCorreo.put("observaciones", req.getV_desc_obse() != null ? req.getV_desc_obse() : "");
				solicitudDt.add(mapDatosDetalleCorreo);
			}			
			String result = MustacheUtil.procesarDetallesCorreo(formatos.get(AppConstants.PARAM_DT_FORMATO_CORREO_DET_APLIC), solicitudDt);
			respuesta.append(result);
		}		
		return respuesta.toString();
	}
	
	private List<ArchivoAdjunto> obtenerArchivoAdjuntos(DetalleRequerimiento requerimiento, String rutaFileServer) {
		List<ArchivoAdjunto> archivosAdjuntos = new ArrayList<>();
		try {
			ArchivoAdjunto archivoAdjunto = null;
			if (requerimiento.getN_idarchadju() != null && requerimiento.getN_idarchadju().getUrlAdjunto() != null) {
				archivoAdjunto = fileServerService
						.downloadFile(rutaFileServer + "/" + requerimiento.getN_idarchadju().getUrlAdjunto());
				if (archivoAdjunto != null) {
					archivoAdjunto.setNombreArchivo(requerimiento.getN_idarchadju().getNombreAdjunto());
					archivosAdjuntos.add(archivoAdjunto);
				}
			}
		} catch (Exception e) {
			logger.error("Error al tratar de obtener archivos adjuntos.", e.getCause());
		}
		return archivosAdjuntos;
	}
		
	private Map<Integer, String> obtenerFormatosCorreo(){
		List<DetalleParametro> listaFormatosOut = new ArrayList<DetalleParametro>();		
		List<DetalleParametro> asuntosCorreo = parametroDao.obtenerDetallesPorParametro(AppConstants.PARAM_ASUNTO_CORREO);
		List<DetalleParametro> mensajesInicialCorreo = parametroDao.obtenerDetallesPorParametro(AppConstants.PARAM_MSJ_INI_CORREO);
		List<DetalleParametro> formatosCorreoCab = parametroDao.obtenerDetallesPorParametro(AppConstants.PARAM_FORMATO_CORREO_CAB);
		List<DetalleParametro> formatosCorreoDet = parametroDao.obtenerDetallesPorParametro(AppConstants.PARAM_FORMATO_CORREO_DET);
		List<DetalleParametro> mensajeFinCorreo = parametroDao.obtenerDetallesPorParametro(AppConstants.PARAM_MSJ_FIN_CORREO);
		listaFormatosOut.addAll(asuntosCorreo);
		listaFormatosOut.addAll(mensajesInicialCorreo);
		listaFormatosOut.addAll(formatosCorreoCab);
		listaFormatosOut.addAll(formatosCorreoDet);
		listaFormatosOut.addAll(mensajeFinCorreo);	
		Map<Integer, String> map = new HashMap<>();
		for (DetalleParametro param : listaFormatosOut) {
	        map.put(param.getIdDetallePara(), param.getValorPara1());
	    }		
		return map;
	}
	
	private Set<Long> listaUnicaSolicitudes(List<DetalleRequerimiento> listaInput){
		Set<Long> listaOuput = null;
		List<Long> listNumSoli = new ArrayList<Long>();
		for(DetalleRequerimiento reqAprob: listaInput) {
			listNumSoli.add(reqAprob.getN_id_soli());
		}
		listaOuput = new HashSet<Long>(listNumSoli);
		return listaOuput;
	}

	private List<String> obtenerDestinatarios(String... correos) {
		if (correos != null) {
			for(int i = 0; i < correos.length; i++) {
				logger.info("correo " + i + " = " + correos[i]);
			}
		}
//		return Arrays.asList("pbobadilla@canvia.com", "jpsanchezc@canvia.com", "gjaramillo@canvia.com", "emamani@canvia.com"/*, "vdelossantosr@sedapal.com.pe", "marevalo@sedapal.com.pe"*/);// Para pruebas
		return Arrays.asList(correos);
	}
}
