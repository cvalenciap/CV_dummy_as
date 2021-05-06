package pe.com.sedapal.asi.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.asi.dao.IParametrosDAO;
import pe.com.sedapal.asi.mapper.DetalleParametroMapper;
import pe.com.sedapal.asi.mapper.ParametroMapper;
import pe.com.sedapal.asi.model.AccionDetalleCategoria;
import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.Categoria;
import pe.com.sedapal.asi.model.DetalleCategoria;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.Parametro;
import pe.com.sedapal.asi.model.ParametroStorage;
import pe.com.sedapal.asi.model.Revisor;
import pe.com.sedapal.asi.model.Sedes;
import pe.com.sedapal.asi.model.Situacion;
import pe.com.sedapal.asi.model.Tipo;
import pe.com.sedapal.asi.model.Trabajador;
import pe.com.sedapal.asi.model.UsuarioAfectado;
import pe.com.sedapal.asi.model.enums.Estado;
import pe.com.sedapal.asi.model.enums.TipoParametro;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.request_objects.RequestUsuarioAfectado;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Nivel;
import pe.com.sedapal.asi.model.response_objects.Paginacion;
import pe.com.sedapal.asi.util.DBConstants;

@Repository
public class ParametrosDAOImpl extends AbstractDAO implements IParametrosDAO {
	Logger logger = LoggerFactory.getLogger(ParametrosDAOImpl.class);

	private ParametroStorage mapearParametroStorage(Map<String, Object> map) {
		this.error = null;
		ParametroStorage item;
		item = new ParametroStorage();
		List<Sedes> lstSedes = new ArrayList<>();
		List<Area> lstAreas = new ArrayList<>();
		List<Situacion> lstSituacion = new ArrayList<>();
		List<DetalleCategoria> lstRecursosTIC = new ArrayList<>();
		List<DetalleCategoria> lstAplicativos = new ArrayList<>();
		List<DetalleCategoria> lstSAP = new ArrayList<>();
		List<DetalleCategoria> lstSolicitudEquipo = new ArrayList<>();
		List<DetalleCategoria> lstTraslado = new ArrayList<>();
		List<AccionDetalleCategoria> lstAccionesDetalleRecursosTIC = new ArrayList<>();
		List<AccionDetalleCategoria> lstAccionesDetalleAplicativos = new ArrayList<>();
		List<AccionDetalleCategoria> lstAccionesDetalleSAP = new ArrayList<>();
		List<AccionDetalleCategoria> lstAccionesSolicitudEquipo = new ArrayList<>();
		List<Revisor> lstRevisor = new ArrayList<>();
		List<DetalleCategoria> lstTipoRequerimiento = new ArrayList<DetalleCategoria>();
		Categoria categoria;
		DetalleCategoria detalleCategoria;
		AccionDetalleCategoria accionDetalleCategoria;
		Revisor revisor;
		Sedes sede;
		Area area;
		Situacion situacion;
		List<DetalleCategoria> lstMensajeAprobDesaprob = new ArrayList<DetalleCategoria>();
		List<DetalleCategoria> lstFormatoCorreoCab = new ArrayList<DetalleCategoria>();
		List<DetalleCategoria> lstValGenTicket = new ArrayList<DetalleCategoria>();
		List<DetalleCategoria> lstParametros = new ArrayList<DetalleCategoria>();

		List<Map<String, Object>> listaRevisores = (List<Map<String, Object>>) map.get("C_REVISORES");
		List<Map<String, Object>> listaSedes = (List<Map<String, Object>>) map.get("C_LST_SEDES");
		List<Map<String, Object>> listaAreas = (List<Map<String, Object>>) map.get("C_LST_AREAS");
		List<Map<String, Object>> listaSituacion = (List<Map<String, Object>>) map.get("C_LST_SITUACION");

		List<Map<String, Object>> listaRecursosTIC = (List<Map<String, Object>>) map.get("C_LST_RECURSOS_TIC");
		List<Map<String, Object>> listaAccionesRecursosTIC = (List<Map<String, Object>>) map
				.get("C_ACCIONES_RECURSOS_TIC");

		List<Map<String, Object>> listaAplicativos = (List<Map<String, Object>>) map.get("C_LST_APLICACIONES");
		List<Map<String, Object>> listaAccionesAplicativos = (List<Map<String, Object>>) map
				.get("C_ACCIONES_APLICACIONES");

		List<Map<String, Object>> listaSAP = (List<Map<String, Object>>) map.get("C_LST_SAP");
		List<Map<String, Object>> listaAccionesSAP = (List<Map<String, Object>>) map.get("C_ACCIONES_SAP");

		List<Map<String, Object>> listaSolicitudEquipo = (List<Map<String, Object>>) map.get("C_LST_SOLICITUD");
		List<Map<String, Object>> listaAccionesSolicitudEquipo = (List<Map<String, Object>>) map
				.get("C_LST_TIPO_EQUIPO");

		List<Map<String, Object>> listaTraslado = (List<Map<String, Object>>) map.get("C_LST_TRASLADO");

		List<Map<String, Object>> listaTipoRequerimiento = (List<Map<String, Object>>) map
				.get("C_LST_TIPO_REQUERIMIENTO");

		List<Map<String, Object>> listaMensajeAprobDesaprob = (List<Map<String, Object>>) map
				.get("C_LST_MSJ_APROB_DESAPROB");
		List<Map<String, Object>> listaFormatoCorreoCab = (List<Map<String, Object>>) map
				.get("C_LST_FORMATO_CORREO_CAB");
		List<Map<String, Object>> listaValGenTicket = (List<Map<String, Object>>) map.get("C_LST_VALORES_GEN_TICKET");

		List<Map<String, Object>> listaParametros = (List<Map<String, Object>>) map.get("C_LST_PARAMETROS");

		item.setCnt_max_MB(((BigDecimal) map.get("n_cnt_max_MB")).longValue());
		item.setMsg_max_MB((String) map.get("V_MSG_MAX_MB"));
		item.setFilterTiposArchivo((String) map.get("V_TIPOS_ARCHIVO"));
		item.setMsg_filterTiposArchivo((String) map.get("V_MSG_TIPOS_ARCHIVO"));
		item.setMsg_resolucion_altas((String) map.get("V_MSG_RESOLUCION_ALTAS"));
		item.setMsg_registro_tickets((String) map.get("V_MSG_REGISTRO_TICKETS"));
		item.setMsg_ws_integracion_inactivo((String) map.get("V_MSG_WS_INTEGRACION_INACTIVO"));
		item.setMsg_aprobacion_jefe((String) map.get("V_MSG_APROBACION_JEFE"));
		item.setCabhtmlContratista((String) map.get("V_CAB_HTML_CONTRATISTA"));
		item.setDethtmlContratistaRECTIC((String) map.get("V_DET_HTML_CONTRATISTA_RECTIC"));
		item.setDethtmlContratistaAPLIC((String) map.get("V_DET_HTML_CONTRATISTA_APLIC"));
		item.setDethtmlContratistaSAP((String) map.get("V_DET_HTML_CONTRATISTA_SAP"));
		item.setDethtmlContratistaTRAS((String) map.get("V_DET_HTML_CONTRATISTA_TRAS"));
		item.setCabhtmlSolicitante((String) map.get("V_CAB_HTML_SOLICITANTE"));
		item.setDethtmlSolicitanteRECTIC((String) map.get("V_DET_HTML_SOLICITANTE_RECTIC"));
		item.setDethtmlSolicitanteAPLIC((String) map.get("V_DET_HTML_SOLICITANTE_APLIC"));
		item.setDethtmlSolicitanteSAP((String) map.get("V_DET_HTML_SOLICITANTE_SAP"));
		item.setDethtmlSolicitanteTRAS((String) map.get("V_DET_HTML_SOLICITANTE_TRAS"));
		item.setCabhtmlRevisor((String) map.get("V_CAB_HTML_REVISOR"));
		item.setDethtmlRevisorRECTIC((String) map.get("V_DET_HTML_REVISOR_RECTIC"));
		item.setDethtmlRevisorAPLIC((String) map.get("V_DET_HTML_REVISOR_APLIC"));
		item.setDethtmlRevisorSAP((String) map.get("V_DET_HTML_REVISOR_SAP"));
		item.setDethtmlRevisorTRAS((String) map.get("V_DET_HTML_REVISOR_TRAS"));
		item.setN_tiempo_espera(((BigDecimal) map.get("N_TIEMPO_ESPERA")).longValue());

		item.setMensajeErrorGenerico((String) map.get("V_MENSAJE_ERROR_GENERICO"));
		item.setIndAdjArchivoNotif((String) map.get("V_IND_ADJ_ARCHIVO_NOTIF"));
		item.setIndEnvNotifContra((String) map.get("V_IND_ENV_NOTIF_CONTRA"));
		item.setIndActServWebInteg((String) map.get("V_IND_ACT_SERV_WEB_INTEG"));

		item.setIndVisNroSoliNotif((String) map.get("V_IND_VIS_NRO_SOL_NOTIF"));

		for (Map<String, Object> mapa1 : listaRevisores) {
			revisor = new Revisor();
			revisor.setNombres((String) mapa1.get("NOMBRES"));
			revisor.setOrden(((BigDecimal) mapa1.get("N_ORDEN_CONF")).longValue());
			revisor.setNcodtrabajador(((BigDecimal) mapa1.get("NCODTRABAJADOR")).longValue());
			revisor.setNficha(((BigDecimal) mapa1.get("NFICHA")).longValue());
			revisor.setTipoorden((String) mapa1.get("V_TIPO_ORDEN"));
			lstRevisor.add(revisor);
		}

		for (Map<String, Object> mapa1 : listaSedes) {
			sede = new Sedes();
			sede.setIdSede(((BigDecimal) mapa1.get("N_IDDETPARA")).longValue());
			sede.setDescSede((String) mapa1.get("V_VALORPARA1"));
			lstSedes.add(sede);
		}

		for (Map<String, Object> mapa1 : listaSituacion) {
			situacion = new Situacion();
			situacion.setVcodtipo((String) mapa1.get("VCODTIPO"));
			situacion.setVdescripcion((String) mapa1.get("VDESCRIPCION"));
			lstSituacion.add(situacion);
		}

		for (Map<String, Object> mapa1 : listaAreas) {
			area = new Area();
			area.setcodigo(((BigDecimal) mapa1.get("NCODAREA")).longValue());
			area.setdescripcion((String) mapa1.get("VDESCRIPCION"));
			area.setabreviatura((String) mapa1.get("VABREVIATURA"));
			area.setNombre((String) mapa1.get("VNOMBRE"));
			lstAreas.add(area);
		}

		for (Map<String, Object> mapa1 : listaAccionesRecursosTIC) {
			accionDetalleCategoria = new AccionDetalleCategoria();
			accionDetalleCategoria.setIdcategoria(((BigDecimal) mapa1.get("N_IDPARA")).longValue());
			accionDetalleCategoria.setIdaccion(((BigDecimal) mapa1.get("N_IDDETPARA")).longValue());
			accionDetalleCategoria.setNombreaccion((String) mapa1.get("V_VALORPARA1"));
			accionDetalleCategoria.setEstadoaccion(Estado.ACTIVO);
			lstAccionesDetalleRecursosTIC.add(accionDetalleCategoria);
		}

		for (Map<String, Object> mapa1 : listaRecursosTIC) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdcategoria(((BigDecimal) mapa1.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa1.get("V_VALORPARA1"));
			detalleCategoria.setEstadocategoria(Estado.ACTIVO);
			lstRecursosTIC.add(detalleCategoria);
		}

		for (Map<String, Object> mapa1 : listaTraslado) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdcategoria(((BigDecimal) mapa1.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa1.get("V_VALORPARA1"));
			detalleCategoria.setEstadocategoria(Estado.ACTIVO);
			lstTraslado.add(detalleCategoria);
		}

		for (Map<String, Object> mapa2 : listaAccionesAplicativos) {
			accionDetalleCategoria = new AccionDetalleCategoria();
			accionDetalleCategoria.setIdcategoria(((BigDecimal) mapa2.get("N_IDPARA")).longValue());
			accionDetalleCategoria.setIdaccion(((BigDecimal) mapa2.get("N_IDDETPARA")).longValue());
			accionDetalleCategoria.setNombreaccion((String) mapa2.get("V_VALORPARA1"));
			accionDetalleCategoria.setEstadoaccion(Estado.ACTIVO);
			lstAccionesDetalleAplicativos.add(accionDetalleCategoria);
		}

		for (Map<String, Object> mapa3 : listaAplicativos) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdcategoria(((BigDecimal) mapa3.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa3.get("V_VALORPARA1"));
			detalleCategoria.setEstadocategoria(Estado.ACTIVO);
			lstAplicativos.add(detalleCategoria);
		}

		for (Map<String, Object> mapa2 : listaAccionesSolicitudEquipo) {
			accionDetalleCategoria = new AccionDetalleCategoria();
			accionDetalleCategoria.setIdcategoria(((BigDecimal) mapa2.get("N_IDPARA")).longValue());
			accionDetalleCategoria.setIdaccion(((BigDecimal) mapa2.get("N_IDDETPARA")).longValue());
			accionDetalleCategoria.setNombreaccion((String) mapa2.get("V_VALORPARA1"));
			accionDetalleCategoria.setEstadoaccion(Estado.ACTIVO);
			lstAccionesSolicitudEquipo.add(accionDetalleCategoria);
		}

		for (Map<String, Object> mapa3 : listaSolicitudEquipo) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdcategoria(((BigDecimal) mapa3.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa3.get("V_VALORPARA1"));
			detalleCategoria.setEstadocategoria(Estado.ACTIVO);
			lstSolicitudEquipo.add(detalleCategoria);
		}

		for (Map<String, Object> mapa2 : listaAccionesSAP) {
			accionDetalleCategoria = new AccionDetalleCategoria();
			accionDetalleCategoria.setIdcategoria(((BigDecimal) mapa2.get("N_IDPARA")).longValue());
			accionDetalleCategoria.setIdaccion(((BigDecimal) mapa2.get("N_IDDETPARA")).longValue());
			accionDetalleCategoria.setNombreaccion((String) mapa2.get("V_VALORPARA1"));
			accionDetalleCategoria.setEstadoaccion(Estado.ACTIVO);
			lstAccionesDetalleSAP.add(accionDetalleCategoria);
		}

		for (Map<String, Object> mapa3 : listaSAP) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdcategoria(((BigDecimal) mapa3.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa3.get("V_VALORPARA1"));
			detalleCategoria.setEstadocategoria(Estado.ACTIVO);
			lstSAP.add(detalleCategoria);
		}

		for (Map<String, Object> mapa4 : listaTipoRequerimiento) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdTipoCategoria(((BigDecimal) mapa4.get("N_IDPARA")).longValue());
			detalleCategoria.setIdcategoria(((BigDecimal) mapa4.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa4.get("V_NOMDETPARA"));
			detalleCategoria.setValorCategoria((String) mapa4.get("V_VALORPARA1"));
			lstTipoRequerimiento.add(detalleCategoria);
		}

		for (Map<String, Object> mapa : listaMensajeAprobDesaprob) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdTipoCategoria(((BigDecimal) mapa.get("N_IDPARA")).longValue());
			detalleCategoria.setIdcategoria(((BigDecimal) mapa.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa.get("V_NOMDETPARA"));
			detalleCategoria.setValorCategoria((String) mapa.get("V_VALORPARA1"));
			lstMensajeAprobDesaprob.add(detalleCategoria);
		}

		for (Map<String, Object> mapa : listaFormatoCorreoCab) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdTipoCategoria(((BigDecimal) mapa.get("N_IDPARA")).longValue());
			detalleCategoria.setIdcategoria(((BigDecimal) mapa.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa.get("V_NOMDETPARA"));
			detalleCategoria.setValorCategoria((String) mapa.get("V_VALORPARA1"));
			lstFormatoCorreoCab.add(detalleCategoria);
		}

		for (Map<String, Object> mapa : listaValGenTicket) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdTipoCategoria(((BigDecimal) mapa.get("N_IDPARA")).longValue());
			detalleCategoria.setIdcategoria(((BigDecimal) mapa.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa.get("V_NOMDETPARA"));
			detalleCategoria.setValorCategoria((String) mapa.get("V_VALORPARA1"));
			lstValGenTicket.add(detalleCategoria);
		}

		for (Map<String, Object> mapa : listaParametros) {
			detalleCategoria = new DetalleCategoria();
			detalleCategoria.setIdTipoCategoria(((BigDecimal) mapa.get("N_IDPARA")).longValue());
			detalleCategoria.setIdcategoria(((BigDecimal) mapa.get("N_IDDETPARA")).longValue());
			detalleCategoria.setNomcategoria((String) mapa.get("V_NOMDETPARA"));
			detalleCategoria.setValorCategoria((String) mapa.get("V_VALORPARA1"));
			detalleCategoria.setValorCategoria2((String) mapa.get("V_VALORPARA2"));
			lstParametros.add(detalleCategoria);
		}

		item.setLstRecursosTIC(lstRecursosTIC);
		item.setLstAccionesRecursosTIC(lstAccionesDetalleRecursosTIC);
		item.setLstAplicativos(lstAplicativos);
		item.setLstAccionesAplicativos(lstAccionesDetalleAplicativos);
		item.setLstSolicitudEquipo(lstSolicitudEquipo);
		item.setLstAccionesSolicitudEquipo(lstAccionesSolicitudEquipo);
		item.setLstSAP(lstSAP);
		item.setLstAccionesSAP(lstAccionesDetalleSAP);
		item.setLstTraslado(lstTraslado);
		item.setRevisores(lstRevisor);
		item.setLstTipoRequerimiento(lstTipoRequerimiento);
		item.setLstSedes(lstSedes);
		item.setLstAreas(lstAreas);
		item.setLstSituacion(lstSituacion);
		item.setLstMsjAprobDesaprob(lstMensajeAprobDesaprob);
		item.setLstFormatoCorreoCab(lstFormatoCorreoCab);
		item.setLstValGenTicket(lstValGenTicket);
		item.setLstParametros(lstParametros);

		return item;
	}

	public ParametroStorage obtenerParametroStorage(Long n_codarea) {
		this.error = null;
		Map<String, Object> out = null;
		ParametroStorage parametro = new ParametroStorage();
		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_SISTEMA)
				.withProcedureName(DBConstants.PROCEDURE_PARAMETROS_SISTEMA)
				.declareParameters(new SqlParameter("N_CODAREA", OracleTypes.NUMBER),
						new SqlOutParameter("v_mensaje", OracleTypes.VARCHAR),
						new SqlOutParameter("n_cnt_max_MB", OracleTypes.NUMBER),
						new SqlOutParameter("V_MSG_MAX_MB", OracleTypes.VARCHAR),
						new SqlOutParameter("C_LST_RECURSOS_TIC", OracleTypes.CURSOR),
						new SqlOutParameter("C_ACCIONES_RECURSOS_TIC", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_APLICACIONES", OracleTypes.CURSOR),
						new SqlOutParameter("C_ACCIONES_APLICACIONES", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_SAP", OracleTypes.CURSOR),
						new SqlOutParameter("C_ACCIONES_SAP", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_SOLICITUD", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_TIPO_EQUIPO", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_TRASLADO", OracleTypes.CURSOR),
						new SqlOutParameter("V_TIPOS_ARCHIVO", OracleTypes.VARCHAR),
						new SqlOutParameter("V_MSG_TIPOS_ARCHIVO", OracleTypes.VARCHAR),
						new SqlOutParameter("V_MSG_RESOLUCION_ALTAS", OracleTypes.VARCHAR),
						new SqlOutParameter("V_MSG_REGISTRO_TICKETS", OracleTypes.VARCHAR),
						new SqlOutParameter("C_LST_TIPO_REQUERIMIENTO", OracleTypes.CURSOR),
						new SqlOutParameter("C_REVISORES", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_SEDES", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_AREAS", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_SITUACION", OracleTypes.CURSOR),
						new SqlOutParameter("V_CAB_HTML_CONTRATISTA", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_CONTRATISTA_RECTIC", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_CONTRATISTA_APLIC", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_CONTRATISTA_SAP", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_CONTRATISTA_TRAS", OracleTypes.VARCHAR),
						new SqlOutParameter("V_CAB_HTML_SOLICITANTE", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_SOLICITANTE_RECTIC", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_SOLICITANTE_APLIC", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_SOLICITANTE_SAP", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_SOLICITANTE_TRAS", OracleTypes.VARCHAR),
						new SqlOutParameter("V_CAB_HTML_REVISOR", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_REVISOR_RECTIC", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_REVISOR_APLIC", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_REVISOR_SAP", OracleTypes.VARCHAR),
						new SqlOutParameter("V_DET_HTML_REVISOR_TRAS", OracleTypes.VARCHAR),
						new SqlOutParameter("C_LST_TIPO_REQUERIMIENTO", OracleTypes.CURSOR),
						new SqlOutParameter("N_TIEMPO_ESPERA", OracleTypes.NUMBER),
						new SqlOutParameter("V_MENSAJE_ERROR_GENERICO", OracleTypes.VARCHAR),
						new SqlOutParameter("V_IND_ADJ_ARCHIVO_NOTIF", OracleTypes.VARCHAR),
						new SqlOutParameter("V_IND_ENV_NOTIF_CONTRA", OracleTypes.VARCHAR),
						new SqlOutParameter("V_IND_ACT_SERV_WEB_INTEG", OracleTypes.VARCHAR),
						new SqlOutParameter("C_LST_MSJ_APROB_DESAPROB", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_FORMATO_CORREO_CAB", OracleTypes.CURSOR),
						new SqlOutParameter("V_IND_VIS_NRO_SOL_NOTIF", OracleTypes.VARCHAR),
						new SqlOutParameter("C_LST_VALORES_GEN_TICKET", OracleTypes.CURSOR),
						new SqlOutParameter("C_LST_PARAMETROS", OracleTypes.CURSOR),
						new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR));
		SqlParameterSource in = new MapSqlParameterSource().addValue("N_CODAREA", n_codarea);
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("O_RETORNO");
			if (resultado == 0) {
				parametro = mapearParametroStorage(out);
			} else {
				String mensaje = (String) out.get("O_MENSAJE");
				String mensajeInterno = (String) out.get("O_SQLERRM");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			Integer resultado = (Integer) out.get("O_RETORNO");
			String mensaje = (String) out.get("O_MENSAJE");
			String mensajeInterno = (String) out.get("O_SQLERRM");
			this.error = new Error(resultado, mensaje, mensajeInterno);
		}
		return parametro;
	}

	private List<UsuarioAfectado> mapearUsuariosAfectados(Map<String, Object> resultados) {
		this.error = null;
		UsuarioAfectado item;
		List<UsuarioAfectado> listausuarios = new ArrayList<>();
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("O_CURSOR");
		int size = lista.size();
		for (Map<String, Object> map : lista) {
			item = new UsuarioAfectado();
			item.setNficha(((BigDecimal) map.get("NFICHA")).longValue());
			item.setNcodtrabajador(((BigDecimal) map.get("NCODTRABAJADOR")).longValue());
			item.setNomUsuario((String) map.get("USUARIO"));
			item.setDni((String) map.get("VDNI"));
			item.setCodSituacion((String) map.get("CODSITUACION"));
			item.setDescSituacion((String) map.get("DESCSITUACION"));
			item.setNcodarea(((BigDecimal) map.get("NCODAREA")).longValue());
			item.setEquipo((String) map.get("EQUIPO"));
			item.setNcodareasup(((BigDecimal) map.get("NCODAREASUP")).longValue());
			item.setGerencia((String) map.get("GERENCIA"));
			item.setDescEquipo((String) map.get("DESCEQUIPO"));
			item.setDescGerencia((String) map.get("DESCGERENCIA"));
			item.setTelefono((String) map.get("TELEFONO"));
			listausuarios.add(item);
			if (map.get("RESULT_COUNT") != null) {
				this.paginacion.setTotalRegistros(((BigDecimal) map.get("RESULT_COUNT")).intValue());
			}
		}
		return listausuarios;
	}

	public List<UsuarioAfectado> obtenerUsuariosAfectados(Long nficha, PageRequest pageRequest,
			RequestUsuarioAfectado requestUsuarioAfectado) {
		this.error = null;
		Map<String, Object> out = null;
		List<UsuarioAfectado> lista = new ArrayList<>();
		this.paginacion = new Paginacion();
		paginacion.setPagina(pageRequest.getPagina());
		paginacion.setRegistros(pageRequest.getRegistros());
		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_SISTEMA)
				.withProcedureName(DBConstants.PROCEDURE_USUARIOS_AFECTADOS)
				.declareParameters(new SqlParameter("N_FICHA", OracleTypes.NUMBER),
						new SqlParameter("V_SEARCH_FICHA", OracleTypes.VARCHAR),
						new SqlParameter("V_SEARCH_DNI", OracleTypes.VARCHAR),
						new SqlParameter("V_SEARCH_PATERNO", OracleTypes.VARCHAR),
						new SqlParameter("V_SEARCH_MATERNO", OracleTypes.VARCHAR),
						new SqlParameter("V_SEARCH_NOMBRES", OracleTypes.VARCHAR),
						new SqlParameter("N_PAGINA", OracleTypes.NUMBER),
						new SqlParameter("N_REGISTROS", OracleTypes.NUMBER),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR));
		SqlParameterSource in = new MapSqlParameterSource().addValue("N_FICHA", nficha)
				.addValue("V_SEARCH_FICHA",
						requestUsuarioAfectado.getNfichaBusqueda() != null
								? requestUsuarioAfectado.getNfichaBusqueda().toString()
								: null)
				.addValue("V_SEARCH_DNI",
						requestUsuarioAfectado.getDni() != null ? requestUsuarioAfectado.getDni().toString() : null)
				.addValue("V_SEARCH_PATERNO",
						requestUsuarioAfectado.getApPaterno() != null
								? requestUsuarioAfectado.getApPaterno().toUpperCase()
								: null)
				.addValue("V_SEARCH_MATERNO",
						requestUsuarioAfectado.getApMaterno() != null
								? requestUsuarioAfectado.getApMaterno().toUpperCase()
								: null)
				.addValue("V_SEARCH_NOMBRES",
						requestUsuarioAfectado.getNombres() != null ? requestUsuarioAfectado.getNombres().toUpperCase()
								: null)
				.addValue("N_PAGINA", pageRequest.getPagina()).addValue("N_REGISTROS", pageRequest.getRegistros());
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("O_RETORNO");
			if (resultado == 0) {
				lista = mapearUsuariosAfectados(out);
			} else {
				String mensaje = (String) out.get("O_MENSAJE");
				String mensajeInterno = (String) out.get("O_SQLERRM");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			Integer resultado = (Integer) out.get("O_RETORNO");
			String mensaje = (String) out.get("O_MENSAJE");
			String mensajeInterno = (String) out.get("O_SQLERRM");
			this.error = new Error(resultado, mensaje, mensajeInterno);
		}
		return lista;
	}

	private List<Area> mapearAreas(Map<String, Object> resultados) {
		this.error = null;
		List<Area> listaareas = new ArrayList<>();
		Area item = null;
		Area areaJefe = null;
		Trabajador itemjefe = null;
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("o_cursor");
		for (Map<String, Object> map : lista) {
			item = new Area();
			areaJefe = new Area();
			item.setcodigo(((BigDecimal) map.get("NCODAREA")).longValue());
			item.setdescripcion((String) map.get("VDESCRIPCION"));
			item.setabreviatura((String) map.get("VABREVIATURA"));
			listaareas.add(item);
		}
		return listaareas;
	}

	public List<Area> obtenerAreas() {
		this.error = null;
		Map<String, Object> out = null;
		List<Area> lista = new ArrayList<>();
		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_BANDEJAS).withProcedureName(DBConstants.PROCEDURE_PARAMETROS_AREAS)
				.declareParameters(new SqlOutParameter("o_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR));
		try {
			out = this.jdbcCall.execute();
			Integer resultado = (Integer) out.get("o_retorno");
			if (resultado == 0) {
				lista = mapearAreas(out);
			} else {
				String mensaje = (String) out.get("o_mensaje");
				String mensajeInterno = (String) out.get("o_sqlerrm");
				this.error = new Error(resultado, mensaje, mensajeInterno);
			}
		} catch (Exception e) {
			Integer resultado = (Integer) out.get("o_retorno");
			String mensaje = (String) out.get("o_mensaje");
			String mensajeInterno = (String) out.get("o_sqlerrm");
			this.error = new Error(resultado, mensaje, mensajeInterno);
		}
		return (lista.size() > 0) ? lista : null;
	}

	private List<Parametro> mapearParametrosPorTipo(Map<String, Object> resultados) {
		this.error = null;
		Parametro item;
		Tipo tipo;

		List<Parametro> listatipos = new ArrayList<>();
		List<Map<String, Object>> lista = (List<Map<String, Object>>) resultados.get("o_cursor");

		for (Map<String, Object> map : lista) {
			item = new Parametro();
			item.setCodigo(((BigDecimal) map.get("N_IDPARA")).toString());
			String tipoParam = (String) map.get("V_TIPOPARA");
			tipo = new Tipo();

			if (TipoParametro.VALOR_UNICO.getValor().equals(tipoParam)) {
				tipo.setCodigo(TipoParametro.VALOR_UNICO.getCodigo());
				tipo.setDescripcion(TipoParametro.VALOR_UNICO.getTexto());
			} else if (TipoParametro.LISTADO_VALORES.getValor().equals(tipoParam)) {
				tipo.setCodigo(TipoParametro.LISTADO_VALORES.getCodigo());
				tipo.setDescripcion(TipoParametro.LISTADO_VALORES.getTexto());
			}

			item.setTipo(tipo);
			item.setNombre((String) map.get("V_NOMPARA"));
			listatipos.add(item);
			if (map.get("RESULT_COUNT") != null) {
				this.paginacion.setTotalRegistros(((BigDecimal) map.get("RESULT_COUNT")).intValue());
			}
		}

		return listatipos;
	}

	@Override
	public List<Parametro> obtenerParametrosPorTipo(String tipoParametro, PageRequest pageRequest) {
		this.error = null;
		Map<String, Object> out = null;
		List<Parametro> lista = new ArrayList<>();

		this.paginacion = new Paginacion();
		paginacion.setPagina(pageRequest.getPagina());
		paginacion.setRegistros(pageRequest.getRegistros());

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_ARS_MANTENIMIENTO)
				.withProcedureName(DBConstants.PROCEDURE_LISTAR_PARAMETROS_POR_TIPO)
				.declareParameters(new SqlParameter("v_search_tipo_param", OracleTypes.VARCHAR),
						new SqlParameter("n_pagina", OracleTypes.INTEGER),
						new SqlParameter("n_registros", OracleTypes.INTEGER),
						new SqlOutParameter("o_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource().addValue("V_SEARCH_TIPO_PARAM", tipoParametro)
				.addValue("n_pagina", pageRequest.getPagina()).addValue("n_registros", pageRequest.getRegistros());

		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");
			if (resultado == DBConstants.OK) {
				lista = mapearParametrosPorTipo(out);
				// lista = ParametroMapper.mapRows(out);
			} else if (resultado == DBConstants.ERR_SOLICITUD) {
				String mensaje = (String) out.get("o_mensaje");
				String mensajeInterno = (String) out.get("o_sqlerrm");
				this.error = new Error(resultado, mensaje, mensajeInterno, Nivel.REQUEST);
			} else {
				String mensaje = (String) out.get("o_mensaje");
				String mensajeInterno = (String) out.get("o_sqlerrm");
				this.error = new Error(resultado, mensaje, mensajeInterno, Nivel.SERVICE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Integer resultado = (Integer) out.get("o_retorno");
			String mensaje = (String) out.get("o_mensaje");
			String mensajeInterno = (String) out.get("o_sqlerrm");
			this.error = new Error(resultado, mensaje, mensajeInterno);
		}

		return lista;

	}

	@Override
	public List<DetalleParametro> obtenerDetallesPorParametro(Integer idParametro) {
		Map<String, Object> out = null;
		List<DetalleParametro> lista = new ArrayList<>();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_ARS_MANTENIMIENTO)
				.withProcedureName(DBConstants.PROCEDURE_DETALLES_PARAMETRO)
				.declareParameters(new SqlParameter("n_id_para", OracleTypes.VARCHAR),
						new SqlOutParameter("o_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource().addValue("N_ID_PARA", idParametro);

		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");
			logger.info("Resultado de la consulta: " + resultado + ", PARAMETRO = " + idParametro);
			if (resultado == DBConstants.OK) {
				lista = DetalleParametroMapper.mapRows(out);
				if (out.get("o_cursor") != null) {
					List<Map<String, Object>> listaCursor = (List<Map<String, Object>>)out.get("o_cursor");
					// this.paginacion.setTotalRegistros(((BigDecimal) listaCursor.get(0).get("RESULT_COUNT")).intValue());
				}
				logger.info("Total detalles " + lista.size());
			} else if (resultado == DBConstants.ERR_SOLICITUD) {
				String mensaje = (String) out.get("o_mensaje");
				String mensajeInterno = (String) out.get("o_sqlerrm");
				this.error = new Error(resultado, mensaje, mensajeInterno, Nivel.REQUEST);
			} else {
				String mensaje = (String) out.get("o_mensaje");
				String mensajeInterno = (String) out.get("o_sqlerrm");
				this.error = new Error(resultado, mensaje, mensajeInterno, Nivel.SERVICE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.getCause());
			Integer resultado = (Integer) out.get("o_retorno");
			String mensaje = (String) out.get("o_mensaje");
			String mensajeInterno = (String) out.get("o_sqlerrm");
			this.error = new Error(resultado, mensaje, mensajeInterno);
		}

		return lista;
	}

	@Override
	public Parametro obtenerParametroPorId(Integer idParametro) {
		Map<String, Object> out = null;
		Parametro parametro = new Parametro();

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_ARS_MANTENIMIENTO)
				.withProcedureName(DBConstants.PROCEDURE_OBTENER_PARAMETRO)
				.declareParameters(new SqlParameter("n_id_para", OracleTypes.INTEGER),
						new SqlOutParameter("o_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource().addValue("N_ID_PARA", idParametro);

		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");
			logger.info("Resultado de la consulta: " + resultado);
			if (resultado == DBConstants.OK) {
				parametro = ParametroMapper.mapRow(out);
			} else if (resultado == DBConstants.ERR_SOLICITUD) {
				String mensaje = (String) out.get("o_mensaje");
				String mensajeInterno = (String) out.get("o_sqlerrm");
				this.error = new Error(resultado, mensaje, mensajeInterno, Nivel.REQUEST);
			} else {
				String mensaje = (String) out.get("o_mensaje");
				String mensajeInterno = (String) out.get("o_sqlerrm");
				this.error = new Error(resultado, mensaje, mensajeInterno, Nivel.SERVICE);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			Integer resultado = (Integer) out.get("o_retorno");
			String mensaje = (String) out.get("o_mensaje");
			String mensajeInterno = (String) out.get("o_sqlerrm");
			this.error = new Error(resultado, mensaje, mensajeInterno);
		}

		return parametro;
	}

	@Override
	public List<DetalleParametro> obtenerDetallesPorParametros(Set<Integer> parametros) {
		List<DetalleParametro> parametrosDetalles = new ArrayList<>();

		if (parametros != null && parametros.size() > 0) {
			parametros.forEach(parametro -> {
				try {
					List<DetalleParametro> detalles = obtenerDetallesPorParametro(parametro);
					parametrosDetalles.addAll(detalles);
				} catch (Exception ex) {
				}
			});
		}

		return parametrosDetalles;
	}

	@Override
	public Integer registrarListaValores(DetalleParametro dtParametro) {
		Map<String, Object> out = null;

		this.jdbcCall = new SimpleJdbcCall(this.jdbc).withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_ARS_MANTENIMIENTO)
				.withProcedureName(DBConstants.PROCEDURE_MANTENIMIENTO_DETALLE_PARAMETRO)
				.declareParameters(new SqlParameter("PN_IDPARA", OracleTypes.INTEGER),
						new SqlParameter("PN_IDDETPARA", OracleTypes.INTEGER),
						new SqlParameter("PV_NOMDETPARA", OracleTypes.VARCHAR),
						new SqlParameter("PV_VALORPARA1", OracleTypes.VARCHAR),
						new SqlParameter("PV_VALORPARA2", OracleTypes.VARCHAR),
						new SqlParameter("PV_VALORPARA3", OracleTypes.VARCHAR),
						new SqlParameter("PN_ESTADO", OracleTypes.INTEGER),
						new SqlParameter("PA_V_USUCRE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR));

		SqlParameterSource in = new MapSqlParameterSource().addValue("PN_IDPARA", dtParametro.getIdPara())
				.addValue("PN_IDDETPARA", dtParametro.getIdDetallePara())
				.addValue("PV_NOMDETPARA", dtParametro.getNombreDetallePara())
				.addValue("PV_VALORPARA1",
						dtParametro.getValorPara1() != null ? dtParametro.getValorPara1().toUpperCase()
								: dtParametro.getValorPara1())
				.addValue("PV_VALORPARA2",
						dtParametro.getValorPara2() != null ? dtParametro.getValorPara2().toUpperCase()
								: dtParametro.getValorPara3())
				.addValue("PV_VALORPARA3",
						dtParametro.getValorPara3() != null ? dtParametro.getValorPara3().toUpperCase()
								: dtParametro.getValorPara3())
				.addValue("PN_ESTADO", dtParametro.getEstado())
				.addValue("PA_V_USUCRE", dtParametro.getUsuarioCreacion());

		Integer resultado = null;

		try {
			out = this.jdbcCall.execute(in);
			resultado = (Integer) out.get("O_RETORNO");
			logger.info("Resultado de la consulta: " + resultado);
			if (resultado == DBConstants.OK) {
				String mensaje = (String) out.get("O_MENSAJE");
				String mensajeInterno = (String) out.get("O_SQLERRM");
				this.error = new Error(resultado, mensaje, mensajeInterno, Nivel.SERVICE);
			} else if (resultado == DBConstants.ERR_SOLICITUD) {
				String mensaje = (String) out.get("O_MENSAJE");
				String mensajeInterno = (String) out.get("O_SQLERRM");
				this.error = new Error(resultado, mensaje, mensajeInterno, Nivel.REQUEST);
			} else {
				String mensaje = (String) out.get("O_MENSAJE");
				String mensajeInterno = (String) out.get("O_SQLERRM");
				this.error = new Error(resultado, mensaje, mensajeInterno, Nivel.SERVICE);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
			resultado = (Integer) out.get("O_RETORNO");
			String mensaje = (String) out.get("O_MENSAJE");
			String mensajeInterno = (String) out.get("O_SQLERRM");
			this.error = new Error(resultado, mensaje, mensajeInterno);
		}

		return resultado;
	}
}
