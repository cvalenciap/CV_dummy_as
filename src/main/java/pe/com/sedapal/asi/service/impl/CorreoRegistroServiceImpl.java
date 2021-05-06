package pe.com.sedapal.asi.service.impl;

import java.sql.SQLException;
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

import pe.com.sedapal.asi.dao.IConfiguracionEquipoDAO;
import pe.com.sedapal.asi.dao.IConfiguracionRevisionDAO;
import pe.com.sedapal.asi.dao.IParametrosDAO;
import pe.com.sedapal.asi.dao.ITrabajadoresDAO;
import pe.com.sedapal.asi.model.ConfiguracionEquipo;
import pe.com.sedapal.asi.model.ConfiguracionRevision;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.DetalleRequerimiento;
import pe.com.sedapal.asi.model.Solicitud;
import pe.com.sedapal.asi.model.Trabajador;
import pe.com.sedapal.asi.model.UsuarioAfectado;
import pe.com.sedapal.asi.model.request_objects.TrabajadorRequest;
import pe.com.sedapal.asi.service.ICorreoRegistroService;
import pe.com.sedapal.asi.service.IFileServerService;
import pe.com.sedapal.asi.util.AppConstants;
import pe.com.sedapal.asi.util.MailConstants;
import pe.com.sedapal.asi.util.MustacheUtil;
import pe.com.sedmail.cliente.bean.ArchivoAdjunto;
import pe.com.sedmail.cliente.bean.ParametrosCorreo;
import pe.com.sedmail.cliente.bean.ResponseBean;
import pe.com.sedmail.cliente.ws.SedmailClienteWs;

@Component
public class CorreoRegistroServiceImpl implements ICorreoRegistroService {
	private Logger logger = LoggerFactory.getLogger(CorreoRegistroServiceImpl.class);

	@Autowired
	private IParametrosDAO parametrosDAO;

	@Autowired
	private ITrabajadoresDAO trabajadorDAO;

	@Autowired
	private IConfiguracionEquipoDAO configuracionEquipoDAO;

	@Autowired
	private IConfiguracionRevisionDAO configuracionRevisionDAO;

	@Autowired
	private IFileServerService fileServerService;
	
	@Async("threadPoolTaskExecutor")
	@Override
	public void aprobacionEnviarCorreo(Solicitud solicitud, List<DetalleRequerimiento> requerimientos, boolean requiereAprobacion) throws Exception {

		Map<Integer, String> mapaParamValor = new HashMap<>();

		// 0) Parametros a considerar
		Set<Integer> parametros = new HashSet<>();
		parametros.add(MailConstants.PARAM_URL_WS_CORREO);
		parametros.add(MailConstants.PARAM_CORREO_REMITENTE);
		parametros.add(MailConstants.PARAM_CLAVE_CORREO_REMITENTE);
		parametros.add(MailConstants.PARAM_NOMBRE_REMITENTE);
		parametros.add(MailConstants.PARAM_FORMATO_CORREO_CAB);
		parametros.add(MailConstants.PARAM_FORMATO_CORREO_DET);
		parametros.add(MailConstants.PARAM_MSJ_INTRO);
		parametros.add(MailConstants.PARAM_ASUNTO);
		parametros.add(MailConstants.PARAM_TICKET);
		parametros.add(MailConstants.PARAM_URL_WS_CONTRATISTA);
		parametros.add(MailConstants.PARAM_FLAG_ENVIO_NOTIF_CONTRATISTA);
		parametros.add(MailConstants.PARAM_FLAG_WS_INTEG_CONTRATISTA);
		parametros.add(MailConstants.PARAM_CORREO_CONTRATISTA);
		parametros.add(MailConstants.PARAM_SALUDO);
		parametros.add(MailConstants.PARAM_IND_ADJUNTAR_ARCHIVO);
		parametros.add(MailConstants.PARAM_CARPETA_FILE_SERVER);
		parametros.add(MailConstants.PARAM_IND_VISUAL_NRO_SOLICITUD);
		parametros.add(MailConstants.PARAM_CORREO_USUARIO_SEGURIDAD);
		parametros.add(MailConstants.PARAM_CORREO_CLAVE_USUARIO_SEGURIDAD);
		parametros.add(MailConstants.PARAM_FLAG_ADJUNTAR_ARCH_NOTIFICACION);
		parametros.add(MailConstants.PARAM_TIPO_ENVIO_CORREO);
		parametros.add(MailConstants.PARAM_FLAG_COPIAR_CONTRATISTA);
		parametros.add(MailConstants.PARAM_CORREO_RECEPTOR_CONTRATISTA);
		parametros.add(MailConstants.PARAM_URL_ASI_WEBAPP);

		// 1) Obtenemos los parametros
		mapaParamValor = this.cargarParametros(parametros, mapaParamValor);

		// 2) Agrupamos las requerimientos por categoria
		Map<Integer, List<DetalleRequerimiento>> requerimientosPorCategoria =

				this.clasificarRequerimientosPorCategoria(requerimientos);

		// 3) Verificamos si requiere aprobacion

		// 4) Enviamos los correos
		if (requiereAprobacion) 
		{			
			this.enviarCorreoRevisores(solicitud, requerimientosPorCategoria, mapaParamValor);
		} 
		else 
		{
			String flagEnvioNotiContratista = this.getValorParametro(MailConstants.PARAM_DT_FLAG_ENVIO_NOTIF_CONTRATISTA, mapaParamValor);

			if (AppConstants.FLAG_SI.equalsIgnoreCase(flagEnvioNotiContratista)) 
			{
				this.enviarCorreoContratista(solicitud, requerimientosPorCategoria,	mapaParamValor);
			} else {
				logger.info("Flag de envio de notificacion de correo al Contratista esta desactivado, no se enviara correo al contratista!");
			}
		}
		
		this.enviarCorreoSolicitante(solicitud, requerimientosPorCategoria, mapaParamValor, requiereAprobacion);
	}

	private ParametrosCorreo adjuntarFicheroSolicitud(ParametrosCorreo parametrosCorreo, Solicitud solicitud,
			Map<Integer, String> mapaParamValor) {
		try {
			if (parametrosCorreo.getArchivosAdjuntos() == null) {
				List<ArchivoAdjunto> archivosAdjuntos = new ArrayList<>();
				parametrosCorreo.setArchivosAdjuntos(archivosAdjuntos);
			}
			
			String flagAdjuntarFicheros = getValorParametro(MailConstants.PARAM_DT_FLAG_ADJUNTAR_ARCH_NOTIFICACION, mapaParamValor);

			if (solicitud.getN_idarchadju() != null 
					&& solicitud.getN_idarchadju().getUrlAdjunto() != null 
					&& !solicitud.getN_idarchadju().getUrlAdjunto().trim().isEmpty()
					&& AppConstants.FLAG_SI.equalsIgnoreCase(flagAdjuntarFicheros)) {
				ArchivoAdjunto archivoAdjunto = fileServerService.downloadFile(this.getValorParametro

				(MailConstants.PARAM_DT_CARPETA_FILE_SERVER, mapaParamValor) + solicitud.getN_idarchadju().getUrlAdjunto());

				if (archivoAdjunto != null) {
					archivoAdjunto.setNombreArchivo(solicitud.getN_idarchadju().getNombreAdjunto());
					parametrosCorreo.getArchivosAdjuntos().add(archivoAdjunto);
				}
			}
		} catch (Exception e) {
			logger.error("Error al adjuntar fichero de la solicitud!", e.getCause());
		}

		return parametrosCorreo;
	}

	private ParametrosCorreo adjuntarFichero(ParametrosCorreo parametrosCorreo, DetalleRequerimiento requerimiento,
			Map<Integer, String> mapaParamValor) {
		try {

			if (parametrosCorreo.getArchivosAdjuntos() == null) {
				List<ArchivoAdjunto> archivosAdjuntos = new ArrayList<>();
				parametrosCorreo.setArchivosAdjuntos(archivosAdjuntos);
			}

			ArchivoAdjunto archivoAdjunto = null;
			String flagAdjuntarFicheros = getValorParametro(MailConstants.PARAM_DT_FLAG_ADJUNTAR_ARCH_NOTIFICACION, mapaParamValor);

			if (requerimiento.getN_idarchadju() != null 
					&& requerimiento.getN_idarchadju().getUrlAdjunto() != null
					&& !requerimiento.getN_idarchadju().getUrlAdjunto().trim().isEmpty()
					&& AppConstants.FLAG_SI.equalsIgnoreCase(flagAdjuntarFicheros)) {
				
				archivoAdjunto = fileServerService.downloadFile(this.getValorParametro
				(MailConstants.PARAM_DT_CARPETA_FILE_SERVER, mapaParamValor) + requerimiento.getN_idarchadju().getUrlAdjunto());

				if (archivoAdjunto != null) {
					archivoAdjunto.setNombreArchivo(requerimiento.getN_idarchadju().getNombreAdjunto());
					parametrosCorreo.getArchivosAdjuntos().add(archivoAdjunto);
				}
			}
		} catch (Exception e) {
			logger.error("Error al tratar de adjuntar el fichero.");
		}

		return parametrosCorreo;
	}

	private ParametrosCorreo adjuntarFicheros(ParametrosCorreo parametrosCorreo,
			Map<Integer, List<DetalleRequerimiento>> mapRequerimientos, Map<Integer, String> mapaParamValor) {
		for (Map.Entry<Integer, List<DetalleRequerimiento>> entry : mapRequerimientos.entrySet()) {
			List<DetalleRequerimiento> requerimientos = entry.getValue();

			for (DetalleRequerimiento requerimiento : requerimientos) {
				parametrosCorreo = adjuntarFichero(parametrosCorreo, requerimiento, mapaParamValor);
			}
		}

		return parametrosCorreo;
	}

	private void enviarCorreoSolicitante(Solicitud solicitud,
			Map<Integer, List<DetalleRequerimiento>> mapRequerimientos, Map<Integer, String> mapaParamValor, boolean requiereAprobacion)
			throws Exception {
		String htmlContenidoCabecera = this.prepararCabeceraCorreo(mapRequerimientos,
				MailConstants.PARAM_DT_MSJ_INTRO_PARA_SOLICITANTE, MailConstants.PARAM_DT_FORMATO_CORREO_CAB,
				AppConstants.USUARIO_TIPO_SOLICITANTE, mapaParamValor, false, requiereAprobacion);

		htmlContenidoCabecera = agregarMensajeAlta(solicitud, htmlContenidoCabecera);

		String htmlContenidoDetalle = this.prepararDetalleCorreo(solicitud, mapRequerimientos, mapaParamValor);
		String saludo = this.getValorParametro(MailConstants.PARAM_DT_SALUDO, mapaParamValor);

		String cuerpoHTML = htmlContenidoCabecera + htmlContenidoDetalle + "<br/>" + saludo;
		
		String correoSolicitante = null;
		for (Map.Entry<Integer, List<DetalleRequerimiento>> entry : mapRequerimientos.entrySet()) {
			List<DetalleRequerimiento> requerimientos = entry.getValue();
			correoSolicitante = requerimientos.get(0).getUsuarioSolicitante().getCorreo();
			break;
		}

		ParametrosCorreo parametrosCorreo = this.prepararParametrosCorreo(mapaParamValor);
		parametrosCorreo.setCuerpoHTML(cuerpoHTML);
		parametrosCorreo.setAsunto(this.getValorParametro(MailConstants.PARAM_DT_ASUNTO_SOLICITANTE, mapaParamValor));
		parametrosCorreo.setDestinatario(this.obtenerDestinatarios(correoSolicitante));
		
		String flagCopiarCorreoContratista = this.getValorParametro(MailConstants.PARAM_DET_FLAG_COPIAR_CONTRATISTA, mapaParamValor);
		
		if (AppConstants.FLAG_SI.equalsIgnoreCase(flagCopiarCorreoContratista)) {
			parametrosCorreo.setCopia(Arrays.asList(this.getValorParametro(MailConstants.PARAM_DT_CORREO_RECEPTOR_CONTRATISTA, mapaParamValor)));
			logger.info("Correo dirigido al solicitante con copia CC: " + parametrosCorreo.getCopia());
		}

		parametrosCorreo = adjuntarFicheros(parametrosCorreo, mapRequerimientos, mapaParamValor);
		parametrosCorreo = adjuntarFicheroSolicitud(parametrosCorreo, solicitud, mapaParamValor);

		logger.info("Inicia envio del correo al solicitante." + parametrosCorreo.getDestinatario());
		this.enviarCorreo(parametrosCorreo, mapaParamValor);
	}

	private String agregarMensajeAlta(Solicitud solicitud, String htmlContenidoCabecera) {
		if (AppConstants.FLAG_IND_ALTA_SI.equals(solicitud.getN_ind_alta())) {
			htmlContenidoCabecera += "<p style='font-family:Tahoma;font-size:13px;'>"
					+ MailConstants.MAIL_ALTA_SECRETARIA
					+ (solicitud.getN_idarchadju() != null ? solicitud.getN_idarchadju

					().getNombreAdjunto() : "") + "</p><br/>";
		}

		return htmlContenidoCabecera;
	}

	private void enviarCorreoRevisores(Solicitud solicitud, Map<Integer, List<DetalleRequerimiento>> mapRequerimientos,
			Map<Integer, String> mapaParamValor) throws Exception {
		
		UsuarioAfectado solicitante = null;
		for (Map.Entry<Integer, List<DetalleRequerimiento>> entry : mapRequerimientos.entrySet()) {
			List<DetalleRequerimiento> requerimientos = entry.getValue();
			solicitante = requerimientos.get(0).getUsuarioSolicitante();
			break;
		}
		
		// Verificar tipo de orden: SO o CO
		ConfiguracionEquipo configuracionEquipo = configuracionEquipoDAO.obtenerConfEquipoPorId(solicitante.getNcodarea());
		List<ConfiguracionRevision> revisores = configuracionRevisionDAO.obtenerAprobadores(solicitante.getNcodarea());
		Map<String, Object> mapaAprobadores = new HashMap<>();

		if (configuracionEquipo != null && AppConstants.CON_ORDEN.equalsIgnoreCase(configuracionEquipo.getTipoOrden())) {
			mapaAprobadores = this.obtenerRevisores(revisores, AppConstants.CON_ORDEN);
		} else {
			mapaAprobadores = this.obtenerRevisores(revisores, AppConstants.SIN_ORDEN);
		}
		
		List<Trabajador> listaAprobadores = (List<Trabajador>)mapaAprobadores.get("listaAprobadores");
		
		if (listaAprobadores == null || listaAprobadores.isEmpty()) {
			logger.warn("No hay revisores para el area " + solicitante.getNcodarea() + ", no se enviara correo a los revisores.");
			return;
		}
		
		boolean mostrarUrlRequerimiento = true;

		for (Trabajador trabajador : listaAprobadores) {
			String htmlContenidoCabecera = this.prepararCabeceraCorreo(mapRequerimientos,
					MailConstants.PARAM_DT_MSJ_INTRO_PARA_REVISOR, MailConstants.PARAM_DT_FORMATO_CORREO_CAB,
					AppConstants.USUARIO_TIPO_REVISOR, mapaParamValor, mostrarUrlRequerimiento, true);
			String htmlContenidoDetalle = this.prepararDetalleCorreo(solicitud, mapRequerimientos,

					mapaParamValor);
			String saludo = this.getValorParametro(MailConstants.PARAM_DT_SALUDO, mapaParamValor);

			String cuerpoHTML = htmlContenidoCabecera + htmlContenidoDetalle + "<br/>" + saludo;

			cuerpoHTML = cuerpoHTML.replace("{{nombres}}", trabajador.getNombreCompleto());
			cuerpoHTML = cuerpoHTML.replace("{{usuario}}", AppConstants.PREFIX_ASI + trabajador.getficha());

			ParametrosCorreo parametrosCorreo = this.prepararParametrosCorreo(mapaParamValor);
			parametrosCorreo.setCuerpoHTML(cuerpoHTML);
			parametrosCorreo.setAsunto(this.getValorParametro(MailConstants.PARAM_DT_ASUNTO_APROBADOR,

					mapaParamValor));
			parametrosCorreo.setDestinatario(this.obtenerDestinatarios(trabajador.getcorreo()));

			parametrosCorreo = adjuntarFicheros(parametrosCorreo, mapRequerimientos, mapaParamValor);
			parametrosCorreo = adjuntarFicheroSolicitud(parametrosCorreo, solicitud, mapaParamValor);

			logger.info("Revisor: " + trabajador.getNombreCompleto());
			logger.info("Inicia envio del correo al revisor." + parametrosCorreo.getDestinatario());
			this.enviarCorreo(parametrosCorreo, mapaParamValor);
		}
	}

	private void enviarCorreoContratista(Solicitud solicitud,
			Map<Integer, List<DetalleRequerimiento>> mapRequerimientos, Map<Integer, String> mapaParamValor)
			throws Exception {
		String cuerpoHTML = "";
		Map<String, Object> mapaRequerimiento = null;
		String htmlContenidoDetalle = "";
		ParametrosCorreo parametrosCorreo = null;
		List<Map<String, Object>> datosDetallesCorreo = null;
		String textoAsunto = "";
		String saludo = this.getValorParametro(MailConstants.PARAM_DT_SALUDO, mapaParamValor);
		String correoContratista = this.getValorParametro(MailConstants.PARAM_DT_CORREO_CONTRATISTA, mapaParamValor);
		
		if (correoContratista == null || correoContratista.trim().isEmpty()) {
			logger.warn("No hay correo del contratista, no se enviara correo al contratista.");
			return;
		}

		String htmlContenidoCabecera = this.prepararCabeceraCorreo(mapRequerimientos,
				MailConstants.PARAM_DT_MSJ_INTRO_PARA_CONTRATISTA,
				MailConstants.PARAM_DT_FORMATO_CORREO_CAB_CONTRATISTA, AppConstants.USUARIO_TIPO_CONTRATISTA,
				mapaParamValor, false, false);

		htmlContenidoCabecera = agregarMensajeAlta(solicitud, htmlContenidoCabecera);

		for (Map.Entry<Integer, List<DetalleRequerimiento>> entry : mapRequerimientos.entrySet()) {
			Integer idDetParaFormatoDet = getIdFormatoDetalleReq(entry.getKey());
			String formatoDetalleCorreoPorCat = this.getValorParametro(idDetParaFormatoDet, mapaParamValor);
			List<DetalleRequerimiento> requerimientos = entry.getValue();

			for (DetalleRequerimiento requerimiento : requerimientos) {
				
				if (requerimiento.getV_id_ticket() == null) {
					requerimiento.setV_id_ticket("");
				}
				
				datosDetallesCorreo = new ArrayList<>();
				mapaRequerimiento = this.adicionarMapRequerimientos(Long.valueOf("" + entry.getKey()), requerimiento, datosDetallesCorreo);
				datosDetallesCorreo.add(mapaRequerimiento);

				htmlContenidoDetalle = MustacheUtil.procesarDetallesCorreo

				(formatoDetalleCorreoPorCat, datosDetallesCorreo);
				cuerpoHTML = htmlContenidoCabecera + htmlContenidoDetalle + "<br/>" + saludo;

				cuerpoHTML = cuerpoHTML.replace("{{nroticket}}", (requerimiento.getV_id_ticket()

						!= null ? requerimiento.getV_id_ticket() : ""));

				parametrosCorreo = this.prepararParametrosCorreo(mapaParamValor);
				parametrosCorreo.setCuerpoHTML(cuerpoHTML);

				textoAsunto = this.getValorParametro(MailConstants.PARAM_DT_ASUNTO_CONTRATISTA,

						mapaParamValor) + " " + requerimiento.getV_id_ticket();

				parametrosCorreo.setAsunto(textoAsunto);
				parametrosCorreo.setDestinatario(this.obtenerDestinatarios(correoContratista));

				parametrosCorreo = adjuntarFichero(parametrosCorreo, requerimiento,

						mapaParamValor);
				parametrosCorreo = adjuntarFicheroSolicitud(parametrosCorreo, solicitud,

						mapaParamValor);

				logger.info("Inicia envio del correo al contratista." + parametrosCorreo.getDestinatario());
				this.enviarCorreo(parametrosCorreo, mapaParamValor);
			}
		} // end for
	}

	private Map<String, Object> adicionarMapRequerimientos(Long categoriaReq, DetalleRequerimiento req,	List<Map<String, Object>> datosDetallesCorreo) {
		
		UsuarioAfectado usuarioAfectado = req.getN_idtrabafec();
		Map<String, Object> mapValues = new HashMap<>();
		mapValues.put(MailConstants.MAIL_DET_TICKET, (req.getV_id_ticket() != null ? req.getV_id_ticket() : ""));

		if (req.getN_id_cat_par().getIdcategoria().intValue() == AppConstants.PARAM_DT_CATEGORIA_ACCESO_APLICACIONES.intValue()) {
			mapValues.put(MailConstants.MAIL_DET_NOMSISTEMA, req.getCategoriaRequerimiento().getNomcategoria() != null ? req.getCategoriaRequerimiento().getNomcategoria() : "");
			mapValues.put(MailConstants.MAIL_DET_PERFILTRANS, req.getV_perfil_tran() != null ? req.getV_perfil_tran() : req.getV_perfil_tran());
			mapValues.put(MailConstants.MAIL_DET_SUSTENTO, req.getN_idarchadju().getNombreAdjunto() != null ? req.getN_idarchadju().getNombreAdjunto() : "");
			mapValues.put(MailConstants.MAIL_DET_FECHAFINVIGEN, req.getFechafinVigencia() != null ?	req.getFechafinVigencia() : "");
		} else if (req.getN_id_cat_par().getIdcategoria().intValue() == AppConstants.PARAM_DT_CATEGORIA_ACCESO_RECURSOSTIC.intValue()) {
			mapValues.put(MailConstants.MAIL_DET_RECURSOTIC, req.getCategoriaRequerimiento().getNomcategoria() != null ? req.getCategoriaRequerimiento().getNomcategoria() : "");
			mapValues.put(MailConstants.MAIL_DET_CUENTARED, (req.getV_cuenta_red() != null ? req.getV_cuenta_red() : ""));
			mapValues.put(MailConstants.MAIL_DET_COD_INVENTAR, (req.getV_cod_inven() != null ? req.getV_cod_inven() : ""));
			mapValues.put(MailConstants.MAIL_DET_FECHAFINVIGEN, req.getFechafinVigencia() != null ? req.getFechafinVigencia() : "");
		}

		mapValues.put(MailConstants.MAIL_DET_USUAFEC, usuarioAfectado.getNomUsuario());
		
		logger.info("FICHA DEL USUARIO AFECTADO = " + usuarioAfectado.getNficha());
		
		mapValues.put(MailConstants.MAIL_DET_UFICHA, usuarioAfectado.getNficha() != null ? usuarioAfectado.getNficha() : "");
		mapValues.put(MailConstants.MAIL_DET_USITUAC, usuarioAfectado.getDescSituacion());
		mapValues.put(MailConstants.MAIL_DET_UEQUIPO, usuarioAfectado.getDescEquipo());
		mapValues.put(MailConstants.MAIL_DET_UTELEFON, usuarioAfectado.getTelefono());
		mapValues.put(MailConstants.MAIL_DET_ACCION, req.getN_accion_par().getNombreaccion() != null ? req.getN_accion_par().getNombreaccion() : "");
		mapValues.put(MailConstants.MAIL_DET_FECH_FIN_VIGEN, req.getFechafinVigencia() != null ? req.getFechafinVigencia() : "");
		mapValues.put(MailConstants.MAIL_DET_OBSERV, (req.getV_desc_obse() != null ? req.getV_desc_obse() : ""));

		if (req.getN_idarchadju() != null && req.getN_idarchadju().getNombreAdjunto() != null) {
			mapValues.put(MailConstants.MAIL_DET_ARCH_ADJUN, req.getN_idarchadju().getNombreAdjunto());
		} else {
			mapValues.put(MailConstants.MAIL_DET_ARCH_ADJUN, "");
		}

		return mapValues;
	}

	private ResponseBean enviarCorreo(ParametrosCorreo parametrosCorreo, Map<Integer, String> mapaParamValor) {
		ResponseBean correoResponse = new ResponseBean();

		try {
			String urlWs = this.getValorParametro(MailConstants.PARAM_DT_URL_WS_CORREO, mapaParamValor);
			String correoRemitente = this.getValorParametro(MailConstants.PARAM_DT_CORREO_USUARIO_SEGURIDAD, mapaParamValor);
			String claveRemitente = this.getValorParametro(MailConstants.PARAM_DT_CORREO_CLAVE_USUARIO_SEGURIDAD, mapaParamValor);

			SedmailClienteWs sedmailClienteWs = new SedmailClienteWs();
			correoResponse = sedmailClienteWs.enviarCorreo(parametrosCorreo, urlWs, correoRemitente, claveRemitente);
		} catch (Exception e) {
			logger.error("Error al enviar correo!", e.getCause());
			correoResponse.setEstadoRespuesta("ERROR");
		}

		return correoResponse;
	}

	private String prepararCabeceraCorreo(Map<Integer, List<DetalleRequerimiento>> mapRequerimientos,
			Integer idDetParaMsjIntro, Integer idDetParaCabecera, Integer tipoDestinatario,
			Map<Integer, String> mapaParamValor, boolean mostrarUrlRequerimiento, boolean requiereAprobacion) throws SQLException {
		Map<String, Object> datosCabeceraCorreo = new HashMap<>();
		String htmlContenidoCabecera = "";

		for (Map.Entry<Integer, List<DetalleRequerimiento>> entry : mapRequerimientos.entrySet()) {
			List<DetalleRequerimiento> requerimientos = entry.getValue();
			DetalleRequerimiento detalleRequerimiento = requerimientos.get(0);

			UsuarioAfectado solicitante = detalleRequerimiento.getUsuarioSolicitante();
			String formatoCabeceraCorreo = this.getValorParametro(idDetParaCabecera, mapaParamValor);

			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_FECHA_CREA_REQ, (detalleRequerimiento.getFechaSolicitud() != null ? detalleRequerimiento.getFechaSolicitud() : ""));

			if (AppConstants.USUARIO_TIPO_SOLICITANTE.equals(tipoDestinatario)) {
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_NOMBRES, (solicitante.getNomUsuario() != null ? solicitante.getNomUsuario() : ""));
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_USUARIO, (solicitante.getCodUsuario() != null ? solicitante.getCodUsuario() : ""));
			} else {
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_NOMBRES, "{{nombres}}");
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_USUARIO, "{{usuario}}");
			}

			String indVisualizacionNroSoli = this.getValorParametro(MailConstants.PARAM_DT_IND_VISUAL_NRO_SOLICITUD, mapaParamValor);

			if (AppConstants.FLAG_SI.equalsIgnoreCase(indVisualizacionNroSoli)) {
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_MOSTRAR_NRO_SOLI, true);
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_NRO_SOLI, (detalleRequerimiento.getN_id_soli() != null ? detalleRequerimiento.getN_id_soli() : ""));
			} else {
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_MOSTRAR_NRO_SOLI, false);
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_NRO_SOLI, "");
			}

			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_USUARIO_SOLI, (solicitante.getCodUsuario() != null
					? solicitante.getCodUsuario() : ""));
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_NOMBRE_SOLI, (solicitante.getNomUsuario() != null ?
					solicitante.getNomUsuario() : ""));
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_CARGO_SOLI, (solicitante.getDescSituacion() != null
					? solicitante.getDescSituacion() : ""));
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_SEDE, (solicitante.getSede() != null ?
					solicitante.getSede() : ""));
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_NOMBRE_GERENCIA, (solicitante.getDescGerencia() !=
					null ? solicitante.getDescGerencia() : ""));
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_NOMBRE_EQUIPO, (solicitante.getDescEquipo() != null
					? solicitante.getDescEquipo() : ""));
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_CORREO, (solicitante.getCorreo() != null ?
					solicitante.getCorreo() : ""));
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_FICHA_DNI, (solicitante.getDni() != null ?
					solicitante.getDni() : ""));
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_TELEFONO, (solicitante.getTelefono() != null ?
					solicitante.getTelefono() : ""));

			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_MSG_INTRO, this.getValorParametro(idDetParaMsjIntro, mapaParamValor));
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_APROBADO_POR, MailConstants.MAIL_MSJ_APROBADO_POR);
			
			// Mostrar URL requerimiento
			if (mostrarUrlRequerimiento) {
				String asiWebUrl = this.getValorParametro(MailConstants.PARAM_DT_URL_ASI_WEBAPP, mapaParamValor);
				
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_MOSTRAR_URL_REQ, true);
				String urlBandejaAprobacion = asiWebUrl + "/#" + MailConstants.MAIL_RUTA_BANDEJA_APROBACION;
				urlBandejaAprobacion = "<a href=\"" + urlBandejaAprobacion + "\">" + urlBandejaAprobacion + "</a>";
				
				datosCabeceraCorreo.put(MailConstants.MAIL_CAB_URL_REQUERIMIENTO, urlBandejaAprobacion);
			}

			Map<String, Object> mapaAprobadores = new HashMap<>();
			List<String> aprobadores = null;

			// Verificar tipo de orden: SO o CO		
			try {
				ConfiguracionEquipo configuracionEquipo = configuracionEquipoDAO.obtenerConfEquipoPorId(solicitante.getNcodarea());
				List<ConfiguracionRevision> revisores = configuracionRevisionDAO.obtenerAprobadores(solicitante.getNcodarea());

				if (AppConstants.CON_ORDEN.equalsIgnoreCase(configuracionEquipo.getTipoOrden())) {
					mapaAprobadores = this.obtenerRevisores(revisores, AppConstants.CON_ORDEN);
				} else {
					mapaAprobadores = this.obtenerRevisores(revisores, AppConstants.SIN_ORDEN);
				}
			} catch (Exception e) {
				logger.error("Error al tratar de obtener configuracion y aprobadores del area(equipo) " + solicitante.getNcodarea());
			}
			
			aprobadores = (List<String>)mapaAprobadores.get("aprobadores");
			
			if (aprobadores != null) {
				Collections.sort(aprobadores);
			} else {
				if(requiereAprobacion) {
					logger.warn("No hay revisores para el area " + solicitante.getNcodarea());
				}
				
				aprobadores = new ArrayList<>();
			}
			
			if (!requiereAprobacion) {
				aprobadores.add(MailConstants.MAIL_MSJ_APROBADO_POR);
			}

			StringBuilder sbAprobadores = new StringBuilder();
			
			for(int i = 0; i < aprobadores.size(); i++) {
				if (i == aprobadores.size() - 1) {
					sbAprobadores.append(aprobadores.get(i));
				} else {
					sbAprobadores.append(aprobadores.get(i)).append(", ");
				}
			}
			
			datosCabeceraCorreo.put(MailConstants.MAIL_CAB_LISTA_APROBADORES, sbAprobadores.toString());
			htmlContenidoCabecera = MustacheUtil.procesarCabeceraCorreo(formatoCabeceraCorreo, datosCabeceraCorreo);

			break;
		}

		return htmlContenidoCabecera;
	}

	private String prepararDetalleCorreo(Solicitud solicitud,
			Map<Integer, List<DetalleRequerimiento>> mapRequerimientos, Map<Integer, String> mapaParamValor) {
		String htmlContenidoDetalle = "";

		// Procesamiento por categoria
		List<Map<String, Object>> datosDetallesCorreo = null;
		for (Map.Entry<Integer, List<DetalleRequerimiento>> entry : mapRequerimientos.entrySet()) {
			Integer idDetParaFormatoDet = getIdFormatoDetalleReq(entry.getKey());
			String formatoDetalleCorreoPorCat = this.getValorParametro(idDetParaFormatoDet, mapaParamValor);
			List<DetalleRequerimiento> requerimientos = entry.getValue();

			// Preparamos datos
			datosDetallesCorreo = new ArrayList<>();
			for (DetalleRequerimiento requerimiento : requerimientos) {
				Map<String, Object> mapaRequerimiento = this.adicionarMapRequerimientos(
						Long.valueOf("" + entry.getKey()),

						requerimiento, datosDetallesCorreo);
				datosDetallesCorreo.add(mapaRequerimiento);
			}

			htmlContenidoDetalle += MustacheUtil.procesarDetallesCorreo(formatoDetalleCorreoPorCat,

					datosDetallesCorreo);
		}

		return htmlContenidoDetalle;
	}

	private ParametrosCorreo prepararParametrosCorreo(Map<Integer, String> mapaParamValor) {
		ParametrosCorreo parametrosCorreo = new ParametrosCorreo();
		parametrosCorreo.setIdTipoEnvio(Integer.parseInt(getValorParametro(MailConstants.PARAM_DT_TIPO_ENVIO_CORREO, mapaParamValor)));
		parametrosCorreo.setRemitente(getValorParametro(MailConstants.PARAM_DT_NOMBRE_REMITENTE, mapaParamValor));
		parametrosCorreo.setRemitenteCorreo(getValorParametro(MailConstants.PARAM_DT_CORREO_REMITENTE, mapaParamValor));
		parametrosCorreo.setRemitenteClave(getValorParametro(MailConstants.PARAM_DT_CLAVE_CORREO_REMITENTE, mapaParamValor));
		parametrosCorreo.setUserOffice365(getValorParametro(MailConstants.PARAM_DT_CORREO_REMITENTE, mapaParamValor));
		parametrosCorreo.setPassOffice365(getValorParametro(MailConstants.PARAM_DT_CLAVE_CORREO_REMITENTE, mapaParamValor));

		return parametrosCorreo;
	}

	private Map<String, Object> obtenerRevisores(List<ConfiguracionRevision> revisores, String tipoOrden) {
		Map<String, Object> mapaAprobadores = new HashMap<>();
		List<String> aprovadores = new ArrayList<>();
		List<Trabajador> listaAprobadores = new ArrayList<>();

		if (revisores == null || revisores.size() == 0) {
			return mapaAprobadores;
		}

		Integer orden = 999999;
		Trabajador correoAprobadorConOrden = null;
		TrabajadorRequest trabRequest = null;

		for (ConfiguracionRevision revisor : revisores) {
			trabRequest = new TrabajadorRequest();
			trabRequest.setCodTrabajador(revisor.getIdTrabConf());
			List<Trabajador> trabajadores = trabajadorDAO.obtenerTrabajadores(trabRequest);

			if (AppConstants.CON_ORDEN.equals(tipoOrden)) {
				aprovadores.add(revisor.getOrdenConf() + " " + 
			                    trabajadores.get(0).getnombre() + " " + 
			                    trabajadores.get(0).getapellidoPaterno() + " " + 
			                    trabajadores.get(0).getapellidoMaterno());

				if (revisor.getOrdenConf().compareTo(orden) < 0) {
					orden = revisor.getOrdenConf();
					correoAprobadorConOrden = trabajadores.get(0);
				}
			} else {
				// INICIO: NUEVO PARA ENVIAR CORREO A LOS REVISORES SIN ORDEN
				for (Trabajador trabajador : trabajadores) {
					aprovadores.add("" + trabajador.getnombre() + " " + 
							trabajador.getapellidoPaterno() + " " + 
							trabajador.getapellidoMaterno());
					
					if (trabajador.getcorreo() != null
							&& !trabajador.getcorreo().trim().isEmpty()
							&& !listaAprobadores.contains(trabajador)) {
						listaAprobadores.add(trabajador);
					} else if (trabajador.getcorreo() == null
							|| trabajador.getcorreo().trim().isEmpty()) {
						logger.info("El aprobador " + trabajador.getNombreCompleto() + " no tiene correo en la tabla trabajador.");
					}
				}
				// FIN: NUEVO PARA ENVIAR CORREO A LOS REVISORES SIN ORDEN
				
				/*
				// INICIO: ANTES
				aprovadores.add("" + trabajadores.get(0).getnombre() + " " + 
	                    trabajadores.get(0).getapellidoPaterno() + " " + 
	                    trabajadores.get(0).getapellidoMaterno());
				
				if (trabajadores.get(0).getcorreo() != null 
						&& !trabajadores.get(0).getcorreo().trim().isEmpty() 
						&& !listaAprobadores.contains(trabajadores.get(0))) {
					listaAprobadores.add(trabajadores.get(0));
				} else if(trabajadores.get(0).getcorreo() == null 
						|| trabajadores.get(0).getcorreo().trim().isEmpty()) {
					logger.info("El aprobador " + trabajadores.get(0).getNombreCompleto() + " no tiene correo en la tabla trabajador.");
				}
				// FIN: ANTES
				*/
			}
		}

		if (correoAprobadorConOrden != null) {
			listaAprobadores.add(correoAprobadorConOrden);
		}
		
		if (!aprovadores.isEmpty()) {
			mapaAprobadores.put("aprobadores", aprovadores);
		}
		
		if (!listaAprobadores.isEmpty()) {
			mapaAprobadores.put("listaAprobadores", listaAprobadores);
		}

		return mapaAprobadores;
	}

	private Integer getIdFormatoDetalleReq(Integer idDetalleCategoria) {
		Integer idDetParaFormatoDetalle = 0;

		if (AppConstants.PARAM_DT_CATEGORIA_ACCESO_APLICACIONES.equals(idDetalleCategoria)) {
			idDetParaFormatoDetalle = MailConstants.PARAM_DT_FORMATO_CORREO_DET_APLICACIONES;
		} else if (AppConstants.PARAM_DT_CATEGORIA_ACCESO_RECURSOSTIC.equals(idDetalleCategoria)) {
			idDetParaFormatoDetalle = MailConstants.PARAM_DT_FORMATO_CORREO_DET_RECURSOSTIC;
		} else if (AppConstants.PARAM_DT_CATEGORIA_TRASLADO_EQUIPOS.equals(idDetalleCategoria)) {
			idDetParaFormatoDetalle = MailConstants.PARAM_DT_FORMATO_CORREO_DET_TRASLADOS;
		} else if (AppConstants.PARAM_DT_CATEGORIA_ACCESO_MANTENIMIENTO_CUENTAS_SAP.equals(idDetalleCategoria)) {
			idDetParaFormatoDetalle = MailConstants.PARAM_DT_FORMATO_CORREO_DET_SAP;
		} else if (AppConstants.PARAM_DT_CATEGORIA_SOLICITUD_EQUIPOS_INFORMATICOS.equals(idDetalleCategoria)) {
			idDetParaFormatoDetalle = MailConstants.PARAM_DT_FORMATO_CORREO_DET_SOLICITUD_EQUIPOS;
		}

		return idDetParaFormatoDetalle;
	}

	private Map<Integer, String> cargarParametros(Set<Integer> parametros, Map<Integer, String> contenedor) {
		List<DetalleParametro> params = parametrosDAO.obtenerDetallesPorParametros(parametros);

		if (params != null && params.size() > 0) {
			params.forEach(param -> {
				contenedor.put(param.getIdDetallePara(), param.getValorPara1());
			});
		}

		return contenedor;
	}

	private String getValorParametro(Integer parametro, Map<Integer, String> mapaParamValor) {
		String valorParametro = "";

		for (Map.Entry<Integer, String> entry : mapaParamValor.entrySet()) {
			if (entry.getKey().equals(parametro)) {
				valorParametro = entry.getValue();
				break;
			}
		}

		return valorParametro;
	}

	private Map<Integer, List<DetalleRequerimiento>> clasificarRequerimientosPorCategoria(final

	List<DetalleRequerimiento> detallesSolicitud) {
		Map<Integer, List<DetalleRequerimiento>> requirimientosCategorizados = new HashMap<>();

		if (detallesSolicitud != null && detallesSolicitud.size() > 0) {
			for (DetalleRequerimiento detalle : detallesSolicitud) {
				if (detalle.getN_id_cat_par() != null && detalle.getN_id_cat_par().getIdcategoria() !=

						null) {
					if (requirimientosCategorizados.containsKey(detalle.getN_id_cat_par

					().getIdcategoria().intValue())) {
						requirimientosCategorizados.get(detalle.getN_id_cat_par().getIdcategoria

						().intValue()).add(detalle);
					} else {
						List<DetalleRequerimiento> requirimientos = new ArrayList<>();
						requirimientos.add(detalle);
						requirimientosCategorizados.put(Integer.valueOf(detalle.getN_id_cat_par().getIdcategoria

						().toString()), requirimientos);
					}
				}
			}
		}

		return requirimientosCategorizados;
	}

	private List<String> obtenerDestinatarios(String... correos) {
		if (correos != null) {
			for(int i = 0; i < correos.length; i++) {
				logger.info("Correo = " + correos[i]);
			}
		}
//		return Arrays.asList("pbobadilla@canvia.com", "jpsanchezc@canvia.com", "gjaramillo@canvia.com", "emamani@canvia.com"/*, "vdelossantosr@sedapal.com.pe", "marevalo@sedapal.com.pe"*/);// Para pruebas
		return Arrays.asList(correos);
	}
}
