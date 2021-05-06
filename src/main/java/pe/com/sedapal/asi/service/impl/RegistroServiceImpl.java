package pe.com.sedapal.asi.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.com.sedapal.asi.dao.IBandejaDAO;
import pe.com.sedapal.asi.dao.IDetalleSolicitudDAO;
import pe.com.sedapal.asi.dao.IParametrosDAO;
import pe.com.sedapal.asi.dao.IRegistroDAO;
import pe.com.sedapal.asi.model.AdjuntoMensaje;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.DetalleRequerimiento;
import pe.com.sedapal.asi.model.DetalleSolicitud;
import pe.com.sedapal.asi.model.Solicitud;
import pe.com.sedapal.asi.model.request_objects.BandejaRequest;
import pe.com.sedapal.asi.model.request_objects.TicketContratistaRequest;
import pe.com.sedapal.asi.model.response_objects.TicketContratistaResponse;
import pe.com.sedapal.asi.model.response_objects.UploadFileResponse;
import pe.com.sedapal.asi.service.IAprobacionSolicitudService;
import pe.com.sedapal.asi.service.ICorreoRegistroService;
import pe.com.sedapal.asi.service.IFileServerService;
import pe.com.sedapal.asi.service.IRegistroService;
import pe.com.sedapal.asi.service.ITicketService;
import pe.com.sedapal.asi.util.AppConstants;
import pe.com.sedapal.asi.util.DBConstants;
import pe.com.sedapal.asi.util.MailConstants;
import pe.com.sedmail.cliente.bean.ArchivoAdjunto;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RegistroServiceImpl implements IRegistroService {
	private Logger logger = LoggerFactory.getLogger(RegistroServiceImpl.class);
	
	@Autowired
	private IRegistroDAO registroDao;
	
	@Autowired
	private IBandejaDAO bandejaDAO;
	
	@Autowired
	private IDetalleSolicitudDAO detalleSolicitudDAO;

	@Autowired
	private ICorreoRegistroService correoService;
	
	@Autowired
	private IFileServerService fileServerService;
	
	@Autowired
	private IParametrosDAO parametroDAO;
	
	@Autowired
	private ITicketService ticketService;
	
	@Autowired
	private IAprobacionSolicitudService aprobacionSolicitudService;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public Map<String, Object> generarSolicitud(Solicitud solicitud, List<DetalleSolicitud> detallesSolicitud, Long codArea) throws SQLException {
		Map<String, Object> respuesta = new HashMap<>();
		Integer resultado = 1; // 0=Success, 1=Error
		respuesta.put("resultado", resultado);
		List<String> archivosSubidosFileServer = new ArrayList<>();
		List<String> tickets = new ArrayList<>();
		
		try {
			
			boolean requiereAprobacion = false;
			
			if (solicitud.getN_ind_alta() != null && solicitud.getN_ind_alta().equals(AppConstants.FLAG_IND_ALTA_SI)) {
				requiereAprobacion = false;				
			} else if (solicitud.getN_ind_alta() != null && solicitud.getN_ind_alta().equals(AppConstants.FLAG_IND_ALTA_NO)) {
				requiereAprobacion = true;				
			} else {
				requiereAprobacion = aprobacionSolicitudService.requireAprobacion(solicitud.getN_idtrabsoli().toString());
			}
			
			if (requiereAprobacion == true) {
				detallesSolicitud.forEach(detalle -> {
					detalle.setN_estado_par(12l);
				});
			} else {
				detallesSolicitud.forEach(detalle -> {
					detalle.setN_estado_par(10l);
				});
			}
			
			boolean resultadoCargaFileServer = subirArchivosFileServer(solicitud, detallesSolicitud, archivosSubidosFileServer);
			
			if (resultadoCargaFileServer) {
				Map<Integer, String> mapaParamValor = new HashMap<>();
				Set<Integer> parametros = new HashSet<>();
				parametros.add(MailConstants.PARAM_TICKET);
				parametros.add(MailConstants.PARAM_URL_WS_CONTRATISTA);
				parametros.add(MailConstants.PARAM_FLAG_ENVIO_NOTIF_CONTRATISTA);
				parametros.add(MailConstants.PARAM_FLAG_WS_INTEG_CONTRATISTA);
				parametros.add(AppConstants.PARAM_CATEGORIA_REQUIRIMIENTO);
				
				mapaParamValor = this.cargarParametros(parametros, mapaParamValor);
				String flagWsTicket = this.getValorParametro(MailConstants.PARAM_DT_FLAG_WS_INTEG_CONTRATISTA, mapaParamValor);

				if (requiereAprobacion == false && AppConstants.FLAG_SI.equalsIgnoreCase(flagWsTicket)) 
				{
					tickets = this.generarTicket(solicitud, detallesSolicitud, mapaParamValor);
					respuesta.put("tickets", tickets);
				} else if(!AppConstants.FLAG_SI.equalsIgnoreCase(flagWsTicket)){
					logger.info("No se generaran tickets debido a que el flag de WS de integracion esta desactivado.");
				}
				
				resultado = this.registroDao.generarSolicitud(solicitud, detallesSolicitud, codArea);
				respuesta.put("resultado", resultado);
			}
			
			if (DBConstants.OK.equals(resultado)) {
				logger.info("Registro de la solicitud exitosa, se enviará correo.");
				
				Map<String, Object> mapaSoliDet = registroDao.getRequerimientos();
				respuesta.put("idSoli", mapaSoliDet.get("solicitud"));
				List<Long> idsDetSoli = (List<Long>)mapaSoliDet.get("requerimientos");
				
				List<DetalleRequerimiento> requerimientos = new ArrayList<>();
				
				logger.info("Requerimientos registrados: " + idsDetSoli);
				
				if (idsDetSoli != null && idsDetSoli.size() > 0) {
					BandejaRequest bandejaRequest = null;
					
					List<DetalleSolicitud> detalles = detalleSolicitudDAO.obtenerDetallesPorIds(idsDetSoli);
					
					for(Long idDetSoli : idsDetSoli) {
						bandejaRequest = new BandejaRequest();
						bandejaRequest.setCategoriaRequerimiento(getCategoriaRequerimiento(detalles, idDetSoli));
						bandejaRequest.setCodigoTrabajador(solicitud.getN_idtrabsoli());
						bandejaRequest.setIdSolicitud(detalles.get(0).getN_id_soli());
						bandejaRequest.setIdDetalleSolicitud(idDetSoli);
						DetalleRequerimiento requerimiento = bandejaDAO.obtenerDetalleRequerimiento(bandejaRequest);
						
						if (requerimiento != null) {
							requerimientos.add(requerimiento);
						}
					}
				}
				
				if (requerimientos != null && requerimientos.size() > 0) {
					correoService.aprobacionEnviarCorreo(solicitud, requerimientos, requiereAprobacion);
				}
			} else {
				logger.warn("No se registró la solicitud, por tanto no se enviará correo.");
				
				eliminarArchivosSubidos(archivosSubidosFileServer);
			}
		} catch (Exception e) {
			logger.error("Error al registrar solicitud...");
			e.printStackTrace();
		}
		
		return respuesta;
	}
	 
	 private List<String> generarTicket(Solicitud solicitud, List<DetalleSolicitud> requerimientos, Map<Integer, String> mapaParamValor) {
			List<String> listaTickets = new ArrayList<>();
		 try {
			logger.info("Generando tickets para los requerimientos.");

			TicketContratistaRequest ticketRequest = null;
			TicketContratistaResponse ticketResponse = null;
			String urlWsTicket = this.getValorParametro(MailConstants.PARAM_DT_URL_WS_INTEG_CONTRATISTA, mapaParamValor);

			for (DetalleSolicitud requerimiento : requerimientos) {
				ticketRequest = new TicketContratistaRequest();
				ticketRequest.setCategoria(setupCategory(requerimiento));
				ticketRequest.setEstadoSolicitud(getValorParametro(MailConstants.PARAM_DT_TICKET_ESTADO_REQ, mapaParamValor));
				ticketRequest.setResumen(getValorParametro(requerimiento.getN_id_cat_par().getIdcategoria().intValue(), mapaParamValor));
				ticketRequest.setDescripcion(setupDescription(requerimiento));
				ticketRequest.setObservaciones(requerimiento.getV_desc_obse());
				ticketRequest.setUsuarioSolicitante(requerimiento.getUsuarioSolicitante().getNomUsuario());
				ticketRequest.setUsuarioAfectado(requerimiento.getN_idtrabafec().getNomUsuario());
				ticketRequest.setPrioridad(getValorParametro(MailConstants.PARAM_DT_TICKET_PRIORIDAD_REQ, mapaParamValor));
				ticketRequest.setImpacto(getValorParametro(MailConstants.PARAM_DT_TICKET_IMPACTO, mapaParamValor));
				ticketRequest.setUrgencia(getValorParametro(MailConstants.PARAM_DT_TICKET_URGENCIA, mapaParamValor));
				ticketRequest.setTipo(getValorParametro(MailConstants.PARAM_DT_TICKET_TIPO_REQ, mapaParamValor));
				ticketRequest.setSede(requerimiento.getUsuarioSolicitante().getSede());
				ticketRequest.setOrigen(getValorParametro(MailConstants.PARAM_DT_TICKET_ORIGEN, mapaParamValor));
				ticketRequest.setProducto(getValorParametro(MailConstants.PARAM_DT_TICKET_PRODUCTO_MICROINFORM, mapaParamValor));
				ticketRequest.setTicket("");

				ticketResponse = this.ticketService.createTicket(ticketRequest, urlWsTicket);

				requerimiento.setV_id_envio(AppConstants.FLAG_IND_ENVIO);
				
				if (AppConstants.ESTADO_OK.equals(ticketResponse.getEstado())) {
					requerimiento.setV_id_ticket(ticketResponse.getResultado().getTicket());
					listaTickets.add(requerimiento.getV_id_ticket());
					
				} else {
					requerimiento.setV_id_ticket("");
					logger.warn("No genero ticket para requerimiento " + requerimiento.getN_det_soli());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return listaTickets;
	}
	 
	private String setupDescription(DetalleSolicitud dSolicitud) {
		
		String out = "";
		
		if (dSolicitud == null || dSolicitud.getN_id_cat_par() == null) {
			return out;
		}
		
		int category = dSolicitud.getN_id_cat_par().getIdcategoria().intValue();
		
		if (category == AppConstants.PARAM_DT_CATEGORIA_ACCESO_RECURSOSTIC.intValue()) {
			out = dSolicitud.getN_rectic_par().getNomcategoria() + " - " + dSolicitud.getN_accion_par().getNombreaccion();
		} else if (category == AppConstants.PARAM_DT_CATEGORIA_ACCESO_APLICACIONES.intValue()) {
			out = dSolicitud.getN_aplic_par().getNomcategoria() + " - " + dSolicitud.getV_perfil_tran() 
			+ " - " + dSolicitud.getN_accion_par().getNombreaccion();
		}
		 
		return out;
	}

	private String setupCategory(DetalleSolicitud dSolicitud) {
		
		String out = "";
		
		if (dSolicitud == null || dSolicitud.getN_id_cat_par() == null) {
			return out;
		}
		
		int category = dSolicitud.getN_id_cat_par().getIdcategoria().intValue();
		
		if (category == AppConstants.PARAM_DT_CATEGORIA_ACCESO_RECURSOSTIC.intValue()) {
			out = "Acceso a Recursos TIC - " + dSolicitud.getN_rectic_par().getNomcategoria();
		} else if (category == AppConstants.PARAM_DT_CATEGORIA_ACCESO_APLICACIONES.intValue()) {
			out = "Acceso a Aplicaciones - " + dSolicitud.getN_aplic_par().getNomcategoria();
		}
		 
		return out;
	}
	 
	private boolean subirArchivosFileServer(final Solicitud solicitud, final List<DetalleSolicitud> detallesSolicitud,
										 final List<String> archivosSubidosFileServer) {
		boolean resultado = false;
		
		try {
			// Paso 0: Parametros a considerar
			Set<Integer> parametros = new HashSet<>();
			parametros.add(MailConstants.PARAM_CARPETA_FILE_SERVER);
			parametros.add(MailConstants.PARAM_CANT_MAX_MB_POR_SOLICITUD);
			
			// Paso 1: Obtener la url del file server y tamanio maximo permito
			List<DetalleParametro> valoresParams = parametroDAO.obtenerDetallesPorParametros(parametros);
			String urlFileServer = this.obtenerValorParam(valoresParams, MailConstants.PARAM_DT_CARPETA_FILE_SERVER);
			String tamanioMaxMBAdj = this.obtenerValorParam(valoresParams, MailConstants.PARAM_DT_CANT_MAX_MB_POR_SOLICITUD);
			Long tamanioMaxMB = 0L;
			
			if (tamanioMaxMBAdj != null && tamanioMaxMBAdj.matches(AppConstants.REGEXP_INTEGERS)) {
				tamanioMaxMB = Long.parseLong(tamanioMaxMBAdj, 10);
			}
			
			// Paso 2: Validar tamanio de los ficheros y Subir ficheros al file server
			if (urlFileServer != null) {
				logger.info("Url del file server: " + urlFileServer);
				
				Long sumSizeAdj = 0L;
				boolean esValidoTamanioTotal = false;
				boolean esExitosoCargasFileServer = true;
				
				// Paso 2.1: Validar tamanio total de ficheros
				if (solicitud.getN_idarchadju() != null 
						&& solicitud.getN_idarchadju().getBytesArray() != null 
						&& solicitud.getN_idarchadju().getBytesArray().length > 0) {
					sumSizeAdj += solicitud.getN_idarchadju().getBytesArray().length;
				}
				
				for(DetalleSolicitud detalle : detallesSolicitud) {
					if (detalle.getN_idarchadju() != null 
							&& detalle.getN_idarchadju().getBytesArray() != null 
							&& detalle.getN_idarchadju().getBytesArray().length > 0) {
						sumSizeAdj += detalle.getN_idarchadju().getBytesArray().length;
					}
				}
				
				if (sumSizeAdj <= tamanioMaxMB * 1024 * 1024) {
					esValidoTamanioTotal = true;
				}
				
				// Paso 2.2: Subir ficheros al file server
				// TODO: REFACTORIZAR EL CARGADO DE FICHEROS
				if (esValidoTamanioTotal) {
					List<AdjuntoMensaje> adjuntos = null;
					ArchivoAdjunto archivoAdjunto = null;
					
					if (solicitud.getN_idarchadju() != null 
							&& solicitud.getN_idarchadju().getBytesArray() != null 
							&& solicitud.getN_idarchadju().getBytesArray().length > 0) {
					
						archivoAdjunto = new ArchivoAdjunto();
						archivoAdjunto.setBytesArchivo(solicitud.getN_idarchadju().getBytesArray());
						archivoAdjunto.setExtension(solicitud.getN_idarchadju().getExtensionAdjunto());
						archivoAdjunto.setNombreArchivo(solicitud.getN_idarchadju().getNombreAdjunto());
						
						UploadFileResponse uploadFileResp = fileServerService.uploadFile(archivoAdjunto, urlFileServer);
						
						if (uploadFileResp != null && uploadFileResp.isCargado()) {
							archivosSubidosFileServer.add(uploadFileResp.getNombre());
							
							solicitud.getN_idarchadju().setUrlAdjunto(uploadFileResp.getNombre());
							solicitud.getN_idarchadju().setN_estado(AppConstants.ESTADO_ACTIVO);
							
							adjuntos = new ArrayList<>();
							adjuntos.add(solicitud.getN_idarchadju());
							adjuntos = this.guardarAdjuntos(adjuntos);
							
							if (adjuntos != null && adjuntos.size() > 0) {
								solicitud.getN_idarchadju().setIdAdjunto(adjuntos.get(0).getIdAdjunto());
							}
						} else {
							esExitosoCargasFileServer = false;
						}
					}// end for solicitud
					
					for(DetalleSolicitud detalle : detallesSolicitud) {
						if (detalle.getN_idarchadju() != null 
								&& detalle.getN_idarchadju().getBytesArray() != null 
								&& detalle.getN_idarchadju().getBytesArray().length > 0) {
							archivoAdjunto = new ArchivoAdjunto();
							archivoAdjunto.setBytesArchivo(detalle.getN_idarchadju().getBytesArray());
							archivoAdjunto.setExtension(detalle.getN_idarchadju().getExtensionAdjunto());
							archivoAdjunto.setNombreArchivo(detalle.getN_idarchadju().getNombreAdjunto());
							
							UploadFileResponse uploadFileResp = fileServerService.uploadFile(archivoAdjunto, urlFileServer);
							
							if (uploadFileResp != null && uploadFileResp.isCargado()) {
								// Paso 2.3: Almacenamos nombres para revertir (borrar ficheros) en el rollback
								archivosSubidosFileServer.add(uploadFileResp.getNombre());
								
								detalle.getN_idarchadju().setUrlAdjunto(uploadFileResp.getNombre());
								detalle.getN_idarchadju().setN_estado(AppConstants.ESTADO_ACTIVO);
								
								// Paso 2.4: Registramos en la base de datos
								adjuntos = new ArrayList<>();
								adjuntos.add(detalle.getN_idarchadju());
								adjuntos = this.guardarAdjuntos(adjuntos);
								
								if (adjuntos != null && adjuntos.size() > 0) {
									detalle.getN_idarchadju().setIdAdjunto(adjuntos.get(0).getIdAdjunto());
								}
							} else {
								esExitosoCargasFileServer = false;
								break;
							}
						}
					}// End for detalles solicitud
				} else {
					logger.warn("Ha superado el tamanio permitido = " + sumSizeAdj + " bytes");
					logger.warn("Total = " + (sumSizeAdj / (1024 * 1024)) + "MB");
					logger.info("No se registrará ni se subira al file server.");
					eliminarArchivosSubidos(archivosSubidosFileServer);
				}
				
				// RESULTADO
				resultado = esValidoTamanioTotal && esExitosoCargasFileServer;
			} else {
				logger.warn("La url del file server nulo");
			}
		} catch (Exception e) {
			logger.error("Error al cargar archivos al file server", e.getCause());
			eliminarArchivosSubidos(archivosSubidosFileServer);
		}
		
		return resultado;
	}
	
	private void eliminarArchivosSubidos(final List<String> archivosSubidosFileServer) {
		if (archivosSubidosFileServer != null && archivosSubidosFileServer.size() > 0) {
			logger.info("Se eliminarán archivos subidos al file server.");
			
			Set<Integer> parametros = new HashSet<>();
			parametros.add(MailConstants.PARAM_CARPETA_FILE_SERVER);
			
			List<DetalleParametro> valoresParams = parametroDAO.obtenerDetallesPorParametros(parametros);
			String urlFileServer = this.obtenerValorParam(valoresParams, MailConstants.PARAM_DT_CARPETA_FILE_SERVER);
			
			for(String fileName : archivosSubidosFileServer) {
				fileServerService.deleteFile(urlFileServer + "/" + fileName);
			}
		}
	}
	
	private String obtenerValorParam(final List<DetalleParametro> parametros, final Integer idParaDt) {
		for(DetalleParametro parametro : parametros) {
			if (parametro.getIdDetallePara().equals(idParaDt)) {
				return parametro.getValorPara1();
			}
		}
		
		return null;
	}
	
	@Override
	public List<AdjuntoMensaje> guardarAdjuntos(List<AdjuntoMensaje> adjuntos) throws SQLException {
		return this.registroDao.guardarAdjuntos(adjuntos);
	}
	
	private Long getCategoriaRequerimiento(List<DetalleSolicitud> detallesSolicitud, Long idDetSolicitud) {
		for(DetalleSolicitud detSoli : detallesSolicitud) {
			if (detSoli.getN_det_soli().equals(idDetSolicitud)) {
				return detSoli.getN_id_cat_par().getIdcategoria();
			}
		}
		
		return 0L;
	}
	
	private Map<Integer, String> cargarParametros(Set<Integer> parametros, Map<Integer, String> contenedor) {
		List<DetalleParametro> params = parametroDAO.obtenerDetallesPorParametros(parametros);

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
				valorParametro = entry.getValue() != null ? entry.getValue() : "";
				break;
			}
		}

		return valorParametro;
	}
}
