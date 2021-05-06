package pe.com.sedapal.asi.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import pe.com.sedapal.asi.dao.IBandejaDAO;
import pe.com.sedapal.asi.model.AccionDetalleCategoria;
import pe.com.sedapal.asi.model.AdjuntoMensaje;
import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.Categoria;
import pe.com.sedapal.asi.model.DetalleCategoria;
import pe.com.sedapal.asi.model.DetalleRequerimiento;
import pe.com.sedapal.asi.model.DetalleSolicitud;
import pe.com.sedapal.asi.model.Revisor;
import pe.com.sedapal.asi.model.UsuarioAfectado;
import pe.com.sedapal.asi.model.request_objects.BandejaRequest;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Paginacion;
import pe.com.sedapal.asi.util.AppConstants;
import pe.com.sedapal.asi.util.DBConstants;
import pe.com.sedapal.asi.util.FechaUtil;
import pe.com.sedapal.asi.util.StringUtil;

@Repository
public class BandejaDAOImpl extends AbstractDAO implements IBandejaDAO{
	
	private Logger logger = LoggerFactory.getLogger(BandejaDAOImpl.class);
	
	@Override
	public List<DetalleRequerimiento> obtenerRequerimientos(BandejaRequest bandejaRequest, PageRequest pageRequest) {
		this.error = null;
		this.paginacion = new Paginacion();
		this.paginacion.setPagina(pageRequest.getPagina());
		this.paginacion.setRegistros(pageRequest.getRegistros());
		Map<String, Object> out = null;
		List<DetalleRequerimiento> listaRequerimientos = new ArrayList<DetalleRequerimiento>();
		Date fechaDesde = null;
		Date fechaHasta = null;
		Integer resultado = 0;
		String mensaje;
		String mensajeInterno;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_BANDEJA)
				.withProcedureName(DBConstants.PROCEDURE_OBTENER_REQUERIMIENTOS)
				.declareParameters(
						new SqlParameter("I_N_ID_TRAB_REVI", OracleTypes.NUMBER),
						new SqlParameter("I_N_ID_CAT_PAR", OracleTypes.NUMBER),
						new SqlParameter("I_N_ID_SOLI", OracleTypes.NUMBER),
						new SqlParameter("I_D_FECINI", OracleTypes.DATE),	
						new SqlParameter("I_D_FECFIN", OracleTypes.DATE),
						new SqlOutParameter("O_C_LISTA_REQU", OracleTypes.CURSOR),
						new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR));		
		
		try {
			if(bandejaRequest.getFechaSolicitudDesde() != null) {
				fechaDesde = FechaUtil.convertirStringToDate(bandejaRequest.getFechaSolicitudDesde(), AppConstants.FORMATO_DD_MM_YYYY);
			}/* else {
				fechaDesde = FechaUtil.convertirStringToDate(DBConstants.FECHA_DEFAULT_DESDE, AppConstants.FORMATO_DD_MM_YYYY);
			}*/
			
			if(bandejaRequest.getFechaSolicitudHasta() != null) {
				fechaHasta = FechaUtil.convertirStringToDate(bandejaRequest.getFechaSolicitudHasta(), AppConstants.FORMATO_DD_MM_YYYY);					
			}/* else {
				fechaHasta = FechaUtil.convertirStringToDate(DBConstants.FECHA_DEFAULT_HASTA, AppConstants.FORMATO_DD_MM_YYYY);		
			}*/
		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_ID_TRAB_REVI", bandejaRequest.getCodigoTrabajador())
					.addValue("I_N_ID_CAT_PAR", bandejaRequest.getCategoriaRequerimiento())
					.addValue("I_N_ID_SOLI", bandejaRequest.getIdSolicitud())
					.addValue("I_D_FECINI", fechaDesde)
					.addValue("I_D_FECFIN", fechaHasta);
		
			out = this.jdbcCall.execute(in);
			resultado = (Integer)out.get("O_RETORNO");
			if(resultado == 0) {
				listaRequerimientos = mapearRequerimientos(out);
			} else {
				mensaje = (String)out.get("O_MENSAJE");
				mensajeInterno = (String)out.get("O_SQLERRM");
				this.error = new Error(resultado,mensaje,mensajeInterno);
				logger.error("Error al obtener requerimientos.", mensajeInterno);
			}
		}catch(Exception e){
			if(resultado > 0) {
				this.error = new Error(resultado,(String)out.get("O_MENSAJE"),(String)out.get("O_SQLERRM"));
			}else {
				this.error = new Error(resultado,"Error",e.getMessage());
			}
			logger.error("Error al obtener requerimientos.", e.getCause());
		}
		return listaRequerimientos;
	}
	
	private List<DetalleRequerimiento> mapearRequerimientos(Map<String, Object> resultados) {
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("O_C_LISTA_REQU");
		List<DetalleRequerimiento> listaRequerimientos = new ArrayList<DetalleRequerimiento>();		
		DetalleRequerimiento ds = null;
		for(Map<String, Object> map : lista) {
			ds = new DetalleRequerimiento();
			ds.setN_id_soli(((BigDecimal)map.get("n_id_soli")).longValue());
			ds.setN_det_soli(((BigDecimal)map.get("n_det_soli")).longValue());
			Categoria categoriaSolicitud = new Categoria();
			categoriaSolicitud.setIdcategoria(((BigDecimal)map.get("n_id_cat_soli")).longValue());
			categoriaSolicitud.setNomcategoria((String)map.get("des_cat_soli"));
			ds.setN_id_cat_par(categoriaSolicitud);
			DetalleCategoria categoriaRequerimiento = new DetalleCategoria();
			categoriaRequerimiento.setIdcategoria(((BigDecimal)map.get("n_id_cat_req")).longValue());
			categoriaRequerimiento.setNomcategoria((String)map.get("des_cat_req"));
			ds.setCategoriaRequerimiento(categoriaRequerimiento);
//			ds.setV_desc_obse((String)map.get("v_desc_obse"));
			ds.setV_desc_obse(StringUtil.decodeCaracterEspecial((String)map.get("v_desc_obse")));
			UsuarioAfectado usuarioSolicitante = new UsuarioAfectado();
			usuarioSolicitante.setNcodtrabajador(((BigDecimal)map.get("n_idtrabsoli")).longValue());
			usuarioSolicitante.setNomUsuario((String)map.get("nom_trabsoli"));
			usuarioSolicitante.setSede((String)map.get("sede_trabsoli"));
			ds.setUsuarioSolicitante(usuarioSolicitante);
			ds.setFechaSolicitud((String)map.get("d_fecha_soli"));
			UsuarioAfectado usuarioAfectado = new UsuarioAfectado();
			usuarioAfectado.setNcodtrabajador(((BigDecimal)map.get("n_idtrabafec")).longValue());
			usuarioAfectado.setNomUsuario((String)map.get("nom_trabafec"));
			ds.setN_idtrabafec(usuarioAfectado);
			Area gerencia = new Area();
			gerencia.setcodigo(((BigDecimal)map.get("naresuperior")).longValue());			
			gerencia.setdescripcion((String)map.get("vdescripcion_gcia"));
			gerencia.setabreviatura((String)map.get("vabreviatura_gcia"));
			ds.setGerencia(gerencia);
			Area equipo = new Area();
			equipo.setcodigo(((BigDecimal)map.get("ncodarea")).longValue());			
			equipo.setdescripcion((String)map.get("vdescripcion_equ"));
			equipo.setabreviatura((String)map.get("vabreviatura_equ"));
			ds.setEquipo(equipo);
			ds.setTiempoEspera(((BigDecimal)map.get("tiempo_espera")).longValue());
			ds.setIndUltRev((String)map.get("ind_ult_rev"));
			ds.setIndAprobacion(((BigDecimal)map.get("ind_aprob")).longValue());
			listaRequerimientos.add(ds);
			if (map.get("RESULT_COUNT") != null && this.paginacion.getTotalRegistros() == null) {
				this.paginacion.setTotalRegistros(((BigDecimal)map.get("RESULT_COUNT")).intValue());
			}			
		}		
		return listaRequerimientos;
	}
	
	@Override
	public Integer revisarSoliciutd(List<DetalleRequerimiento> listDetalleSolicitud) {
		this.error = null;
		DetalleSolicitud item = new DetalleSolicitud();
		Connection conn = null;
		StructDescriptor structDetalle=null;
		ArrayDescriptor arrayDetalle=null;
		Map<String, Object> out = null;	
		Integer resultado = 0;
		String mensaje;
		String mensajeInterno;
		
		try {			
			conn = this.jdbc.getDataSource().getConnection();
			structDetalle = StructDescriptor.createDescriptor("ARS.TYPE_ARS_OBJ_SOLICITUD_DT", conn.getMetaData().getConnection());
			arrayDetalle = ArrayDescriptor.createDescriptor("ARS.TYPE_ARS_TAB_SOLICITUD_DT", conn.getMetaData().getConnection());
			List<Object> iStructsDetalle = new ArrayList<>();
			Object[] iDetalle = new Object[item.getClass().getDeclaredFields().length];
			
			for(DetalleSolicitud ds: listDetalleSolicitud) {
				iDetalle[0] = ds.getN_det_soli();
				iDetalle[1] = ds.getN_id_soli();
				iDetalle[3] = ds.getN_idtrabafec().getNcodtrabajador();
				iDetalle[25] = ds.getN_estado_par();
				iDetalle[26] = ds.getN_idtrab_est();
				iDetalle[28] = ds.getV_id_ticket();
				iDetalle[33] = ds.getA_v_usumod();
				iDetalle[37] = ds.getUsuarioSolicitante().getNcodtrabajador();
				iDetalle[42] = ds.getIndUltRev();
				iDetalle[54] = ds.getIndAprobacion();				
				
				iStructsDetalle.add( new STRUCT(structDetalle,conn.getMetaData().getConnection(),iDetalle));								
			}			
			ARRAY datosIn = new ARRAY(arrayDetalle,conn.getMetaData().getConnection(),iStructsDetalle.toArray());								
			
			this.jdbcCall = new SimpleJdbcCall(this.jdbc)
					.withSchemaName(dbSchema)
					.withCatalogName(DBConstants.PACKAGE_BANDEJA)
					.withProcedureName(DBConstants.PROCEDURE_REVISAR_SOLICITUD)
					.declareParameters(
							new SqlParameter("I_T_SOLICITUD_DT", OracleTypes.ARRAY, "ARS.TYPE_ARS_TAB_SOLICITUD_DT"),
							new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
							new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
							new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR));
			
			 SqlParameterSource in = new MapSqlParameterSource().addValue("I_T_SOLICITUD_DT", datosIn); 
			 
			 out = this.jdbcCall.execute(in);
			 resultado = (Integer)out.get("O_RETORNO");
			 if(resultado == 0) {
				return resultado;
			} else {
				mensaje = (String)out.get("O_MENSAJE");
				mensajeInterno = (String)out.get("O_SQLERRM");
				this.error = new Error(resultado,mensaje,mensajeInterno);
				logger.error("Error al revisar los requerimientos.", mensajeInterno);
				return 1;
			}			
		}catch(Exception e) {
			if(resultado > 0) {
				this.error = new Error(resultado,(String)out.get("O_MENSAJE"),(String)out.get("O_SQLERRM"));
			}else {
				resultado = 1;
				this.error = new Error(resultado,"Error",e.getMessage());
			}
			logger.error("Error al revisar los requerimientos.", e.getCause());
		}				
		return resultado;
	}
	
	@Override
	public DetalleRequerimiento obtenerDetalleRequerimiento(BandejaRequest bandejaRequest) {
		DetalleRequerimiento detalleRequerimiento = null;
		switch(bandejaRequest.getCategoriaRequerimiento().intValue()) {
		  case 7:
			  detalleRequerimiento = obtenerDetalleAccesoRequAplicacion(bandejaRequest);
		    break;
		  case 8:
			  detalleRequerimiento = obtenerDetalleRequRecursoTIC(bandejaRequest);
		    break;
		  default:
		    // code block
		}		
		return detalleRequerimiento;
	}
	
	public DetalleRequerimiento obtenerDetalleRequRecursoTIC(BandejaRequest bandejaRequest) {
		this.error = null;
		Map<String, Object> out = null;		
		DetalleRequerimiento detalleRequerimiento = new DetalleRequerimiento();
		Integer resultado = 0;
		String mensaje;
		String mensajeInterno;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_BANDEJA)
				.withProcedureName(DBConstants.PROCEDURE_OBTENER_DET_REQ_REC_TIC)
				.declareParameters(
						new SqlParameter("I_N_ID_SOLI", OracleTypes.NUMBER),
						new SqlParameter("I_N_DET_SOLI", OracleTypes.NUMBER),	
						new SqlOutParameter("O_C_DET_REQU", OracleTypes.CURSOR),
						new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR));		
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_ID_SOLI", bandejaRequest.getIdSolicitud())
					.addValue("I_N_DET_SOLI", bandejaRequest.getIdDetalleSolicitud());
		
			out = this.jdbcCall.execute(in);
			resultado = (Integer)out.get("O_RETORNO");
			if(resultado == 0) {
				detalleRequerimiento = mapearDetalleRequRecursoTIC(out);
			} else {
				mensaje = (String)out.get("O_MENSAJE");
				mensajeInterno = (String)out.get("O_SQLERRM");
				this.error = new Error(resultado,mensaje,mensajeInterno);
				logger.error("Error al obtener detalle del requerimiento.", mensajeInterno);
			}
		}catch(Exception e){
			if(resultado > 0) {
				this.error = new Error(resultado,(String)out.get("O_MENSAJE"),(String)out.get("O_SQLERRM"));
			}else {
				this.error = new Error(resultado,"Error",e.getMessage());
			}					
			logger.error("Error al obtener detalle del requerimiento.", e.getCause());
		}
		return detalleRequerimiento;
	}
	
	private DetalleRequerimiento mapearDetalleRequRecursoTIC(Map<String, Object> resultados) {
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("O_C_DET_REQU");
		DetalleRequerimiento dr = null;		
		for(Map<String, Object> map : lista) {
			dr = new DetalleRequerimiento();			
			dr.setN_id_soli(((BigDecimal)map.get("n_id_soli")).longValue());
			dr.setN_det_soli(((BigDecimal)map.get("n_det_soli")).longValue());
			
			UsuarioAfectado usuarioSolicitante = new UsuarioAfectado();
			usuarioSolicitante.setNcodtrabajador(((BigDecimal)map.get("n_idtrabsoli")).longValue());			
			usuarioSolicitante.setNomUsuario((String)map.get("AP_PATE_SOLIC")+" "+(String)map.get("AP_MATE_SOLIC")+", "+(String)map.get("NOM_SOLIC"));
			usuarioSolicitante.setNficha(((BigDecimal)map.get("NFICHA")).longValue());
			usuarioSolicitante.setNcodareasup(((BigDecimal)map.get("COD_GCIA_SOLIC")).longValue());
			usuarioSolicitante.setDescGerencia((String)map.get("DESC_GCIA_SOLIC"));
			usuarioSolicitante.setGerencia((String)map.get("ABRE_GCIA_SOLIC"));
			usuarioSolicitante.setNcodarea(((BigDecimal)map.get("COD_EQU_SOLIC")).longValue());
			usuarioSolicitante.setEquipo((String)map.get("ABRE_EQU_SOLIC"));
			usuarioSolicitante.setDescEquipo((String)map.get("DESC_EQU_SOLIC"));
			usuarioSolicitante.setSede((String)map.get("SEDE"));
			usuarioSolicitante.setTelefono((String)map.get("TELEFONO"));
			usuarioSolicitante.setCorreo((String)map.get("vdirelectronica"));
			usuarioSolicitante.setDni((String)map.get("FICHA_DNI_SOLIC"));
			usuarioSolicitante.setCodUsuario((String)map.get("COD_USUA_SOLIC"));
			usuarioSolicitante.setDescSituacion((String)map.get("SITUACION_SOLIC"));
			dr.setUsuarioSolicitante(usuarioSolicitante);				
			
			Categoria categoriaSolicitud = new Categoria();
			categoriaSolicitud.setIdcategoria(((BigDecimal)map.get("N_ID_CAT_SOLI")).longValue());
			categoriaSolicitud.setNomcategoria((String)map.get("des_cat_soli"));
			dr.setN_id_cat_par(categoriaSolicitud);
			
			DetalleCategoria categoriaRequerimiento = new DetalleCategoria();
			categoriaRequerimiento.setIdcategoria(((BigDecimal)map.get("n_rectic_par")).longValue());
			categoriaRequerimiento.setNomcategoria((String)map.get("DES_CAT_REQ"));			
			dr.setCategoriaRequerimiento(categoriaRequerimiento);
			
			AccionDetalleCategoria accionDetalleCategoria = new AccionDetalleCategoria();
			accionDetalleCategoria.setIdaccion(((BigDecimal)map.get("n_accion_par")).longValue());
			accionDetalleCategoria.setNombreaccion((String)map.get("DES_ACCION"));
			dr.setN_accion_par(accionDetalleCategoria);
			
			dr.setV_cuenta_red((String)map.get("v_cuenta_red"));
			dr.setV_cod_inven((String)map.get("v_cod_inven"));
			dr.setFechafinVigencia((String)map.get("D_FEC_FINVIG"));
			
			AdjuntoMensaje adjunto = new AdjuntoMensaje();
			Object obj = map.get("n_idarchadju");
			adjunto.setIdAdjunto(obj == null ? 0 : ((BigDecimal)map.get("n_idarchadju")).intValue());
			adjunto.setNombreAdjunto((String)map.get("V_NOMB_ADJUN"));
			adjunto.setUrlAdjunto((String)map.get("V_RUTA_ADJUN"));
			dr.setN_idarchadju(adjunto);
			
			//dr.setV_desc_obse((String)map.get("v_desc_obse"));
			dr.setV_desc_obse(StringUtil.decodeCaracterEspecial((String)map.get("v_desc_obse")));
			dr.setTipoOrdenAprobacion((String)map.get("TIPO_ORDEN_EQUIPO"));			
			dr.setAprobadores((String)map.get("APROBADORES"));			
			dr.setV_id_ticket((String)map.get("TICKET"));
			dr.setN_estado_par(((BigDecimal)map.get("ESTADO_REQ")).longValue());
			Revisor revisor = new Revisor();
			String stringRevisor = (String)map.get("SIGU_REVISOR");
			logger.info("Siguiente revisor = " + stringRevisor);
			if(stringRevisor != null) {
				List<String> detalleRevisor = Arrays.asList(stringRevisor.split("\\s*,\\s*"));				
				revisor.setNcodtrabajador(Long.parseLong(detalleRevisor.get(0)));
				revisor.setNficha(Long.parseLong(detalleRevisor.get(1)));
				revisor.setNombres(detalleRevisor.get(2));
				revisor.setApPaterno(detalleRevisor.get(3));
				revisor.setApMaterno(detalleRevisor.get(4));
				revisor.setCargo(detalleRevisor.get(5));
				revisor.setCorreo(detalleRevisor.get(6));
				revisor.setCodArea(Long.parseLong(detalleRevisor.get(7)));				
			}
			dr.setRevisor(revisor);	
			
			dr.setFechaSolicitud((String)map.get("D_FECHA_SOLI"));
			
			UsuarioAfectado usuarioAfectado = new UsuarioAfectado();
			usuarioAfectado.setNcodtrabajador(((BigDecimal)map.get("n_idtrabafec")).longValue());
			usuarioAfectado.setNomUsuario((String)map.get("NOMB_USUA_AFEC")+" "+(String)map.get("AP_PATE_USUA_AFEC")+" "+(String)map.get("AP_MATE_USUA_AFEC"));
			usuarioAfectado.setNficha(((BigDecimal)map.get("NFICHA")).longValue());
			usuarioAfectado.setDni((String)map.get("FICHA_DNI"));
			usuarioAfectado.setDescSituacion((String)map.get("SITUACION"));
			usuarioAfectado.setNcodarea(((BigDecimal)map.get("COD_EQU_USUA_AFEC")).longValue());
			usuarioAfectado.setEquipo((String)map.get("ABRE_EQU_USUA_AFEC"));
			usuarioAfectado.setDescEquipo((String)map.get("DESC_EQU_USUA_AFEC"));
			usuarioAfectado.setNcodareasup(((BigDecimal)map.get("COD_GCIA_USUA_AFEC")).longValue());
			usuarioAfectado.setDescGerencia((String)map.get("DESC_GCIA_USUA_AFEC"));
			usuarioAfectado.setGerencia((String)map.get("ABRE_GCIA_USUA_AFEC"));
			usuarioAfectado.setTelefono((String)map.get("TELEFONO_USUA_AFEC"));
			dr.setN_idtrabafec(usuarioAfectado);
		}		
		return dr;
	}
	
	
	public DetalleRequerimiento obtenerDetalleAccesoRequAplicacion(BandejaRequest bandejaRequest) {
		this.error = null;
		Map<String, Object> out = null;		
		DetalleRequerimiento detalleRequerimiento = new DetalleRequerimiento();
		Integer resultado = 0;
		String mensaje;
		String mensajeInterno;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_BANDEJA)
				.withProcedureName(DBConstants.PROCEDURE_OBTENER_DET_REQ_APLIC)
				.declareParameters(
						new SqlParameter("I_N_ID_SOLI", OracleTypes.NUMBER),
						new SqlParameter("I_N_DET_SOLI", OracleTypes.NUMBER),	
						new SqlOutParameter("O_C_DET_REQU", OracleTypes.CURSOR),
						new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR));		
		
		try {		
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_ID_SOLI", bandejaRequest.getIdSolicitud())
					.addValue("I_N_DET_SOLI", bandejaRequest.getIdDetalleSolicitud());
		
			out = this.jdbcCall.execute(in);
			resultado = (Integer)out.get("O_RETORNO");
			if(resultado == 0) {
				detalleRequerimiento = mapearDetalleRequAplicacion(out);
			} else {
				mensaje = (String)out.get("O_MENSAJE");
				mensajeInterno = (String)out.get("O_SQLERRM");
				this.error = new Error(resultado,mensaje,mensajeInterno);
				logger.error("Error al obtener detalle del requerimiento.", mensajeInterno);
			}
		}catch(Exception e){
			if(resultado > 0) {
				this.error = new Error(resultado,(String)out.get("O_MENSAJE"),(String)out.get("O_SQLERRM"));
			}else {
				this.error = new Error(resultado,"Error",e.getMessage());
			}		
			logger.error("Error al obtener detalle del requerimiento.", e.getCause());
		}
		return detalleRequerimiento;
	}
	
	private DetalleRequerimiento mapearDetalleRequAplicacion(Map<String, Object> resultados) {
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("O_C_DET_REQU");
		DetalleRequerimiento dr = null;		
		for(Map<String, Object> map : lista) {
			dr = new DetalleRequerimiento();			
			dr.setN_id_soli(((BigDecimal)map.get("n_id_soli")).longValue());
			dr.setN_det_soli(((BigDecimal)map.get("n_det_soli")).longValue());
			
			UsuarioAfectado usuarioSolicitante = new UsuarioAfectado();
			usuarioSolicitante.setNcodtrabajador(((BigDecimal)map.get("n_idtrabsoli")).longValue());
			usuarioSolicitante.setNomUsuario((String)map.get("AP_PATE_SOLIC")+" "+(String)map.get("AP_MATE_SOLIC")+", "+(String)map.get("NOM_SOLIC"));
			usuarioSolicitante.setNficha(((BigDecimal)map.get("NFICHA")).longValue());
			usuarioSolicitante.setNcodareasup(((BigDecimal)map.get("COD_GCIA_SOLIC")).longValue());
			usuarioSolicitante.setDescGerencia((String)map.get("DESC_GCIA_SOLIC"));
			usuarioSolicitante.setGerencia((String)map.get("ABRE_GCIA_SOLIC"));
			usuarioSolicitante.setNcodarea(((BigDecimal)map.get("COD_EQU_SOLIC")).longValue());
			usuarioSolicitante.setEquipo((String)map.get("ABRE_EQU_SOLIC"));
			usuarioSolicitante.setDescEquipo((String)map.get("DESC_EQU_SOLIC"));
			usuarioSolicitante.setSede((String)map.get("SEDE"));
			usuarioSolicitante.setTelefono((String)map.get("TELEFONO"));
			usuarioSolicitante.setCorreo((String)map.get("vdirelectronica"));
			usuarioSolicitante.setDni((String)map.get("FICHA_DNI_SOLIC"));
			usuarioSolicitante.setCodUsuario((String)map.get("COD_USUA_SOLIC"));
			usuarioSolicitante.setDescSituacion((String)map.get("SITUACION_SOLIC"));
			dr.setUsuarioSolicitante(usuarioSolicitante);						
			
			Categoria categoriaSolicitud = new Categoria();
			categoriaSolicitud.setIdcategoria(((BigDecimal)map.get("N_ID_CAT_SOLI")).longValue());
			categoriaSolicitud.setNomcategoria((String)map.get("des_cat_soli"));
			dr.setN_id_cat_par(categoriaSolicitud);
			
			DetalleCategoria categoriaRequerimiento = new DetalleCategoria();
			categoriaRequerimiento.setIdcategoria(((BigDecimal)map.get("n_aplic_par")).longValue());
			categoriaRequerimiento.setNomcategoria((String)map.get("DES_CAT_REQ"));			
			dr.setCategoriaRequerimiento(categoriaRequerimiento);
			
			dr.setV_perfil_tran((String)map.get("v_perfil_tran"));
			
			AccionDetalleCategoria accionDetalleCategoria = new AccionDetalleCategoria();
			accionDetalleCategoria.setIdaccion(((BigDecimal)map.get("n_accion_par")).longValue());
			accionDetalleCategoria.setNombreaccion((String)map.get("DES_ACCION"));
			dr.setN_accion_par(accionDetalleCategoria);
			
			AdjuntoMensaje adjunto = new AdjuntoMensaje();
			Object obj = map.get("n_idarchadju");
			adjunto.setIdAdjunto(obj == null ? 0 : ((BigDecimal)map.get("n_idarchadju")).intValue());
			adjunto.setNombreAdjunto((String)map.get("V_NOMB_ADJUN"));
			adjunto.setUrlAdjunto((String)map.get("V_RUTA_ADJUN"));
			dr.setN_idarchadju(adjunto);
			
			dr.setFechafinVigencia((String)map.get("D_FEC_FINVIG"));									
//			dr.setV_desc_obse((String)map.get("v_desc_obse"));
			dr.setV_desc_obse(StringUtil.decodeCaracterEspecial((String)map.get("v_desc_obse")));	
			dr.setTipoOrdenAprobacion((String)map.get("TIPO_ORDEN_EQUIPO"));
			dr.setAprobadores((String)map.get("APROBADORES"));	
			dr.setV_id_ticket((String)map.get("TICKET"));
			dr.setN_estado_par(((BigDecimal)map.get("ESTADO_REQ")).longValue());
			
			Revisor revisor = new Revisor();
			String stringRevisor = (String)map.get("SIGU_REVISOR");
			logger.info("Siguiente revisor = " + stringRevisor);
			if(stringRevisor != null) {
				List<String> detalleRevisor = Arrays.asList(stringRevisor.split("\\s*,\\s*"));				
				revisor.setNcodtrabajador(Long.parseLong(detalleRevisor.get(0)));
				revisor.setNficha(Long.parseLong(detalleRevisor.get(1)));
				revisor.setNombres(detalleRevisor.get(2));
				revisor.setApPaterno(detalleRevisor.get(3));
				revisor.setApMaterno(detalleRevisor.get(4));
				revisor.setCargo(detalleRevisor.get(5));
				revisor.setCorreo(detalleRevisor.get(6));
				revisor.setCodArea(Long.parseLong(detalleRevisor.get(7)));				
			}
			dr.setRevisor(revisor);	
							
			dr.setFechaSolicitud((String)map.get("D_FECHA_SOLI"));
			
			UsuarioAfectado usuarioAfectado = new UsuarioAfectado();
			usuarioAfectado.setNcodtrabajador(((BigDecimal)map.get("n_idtrabafec")).longValue());
			usuarioAfectado.setNomUsuario((String)map.get("NOMB_USUA_AFEC")+" "+(String)map.get("AP_PATE_USUA_AFEC")+" "+(String)map.get("AP_MATE_USUA_AFEC"));
			usuarioAfectado.setNficha(((BigDecimal)map.get("NFICHA")).longValue());
			usuarioAfectado.setDni((String)map.get("FICHA_DNI"));
			usuarioAfectado.setDescSituacion((String)map.get("SITUACION"));
			usuarioAfectado.setNcodarea(((BigDecimal)map.get("COD_EQU_USUA_AFEC")).longValue());
			usuarioAfectado.setEquipo((String)map.get("ABRE_EQU_USUA_AFEC"));
			usuarioAfectado.setDescEquipo((String)map.get("DESC_EQU_USUA_AFEC"));
			usuarioAfectado.setNcodareasup(((BigDecimal)map.get("COD_GCIA_USUA_AFEC")).longValue());
			usuarioAfectado.setDescGerencia((String)map.get("DESC_GCIA_USUA_AFEC"));
			usuarioAfectado.setGerencia((String)map.get("ABRE_GCIA_USUA_AFEC"));	
			usuarioAfectado.setTelefono((String)map.get("TELEFONO_USUA_AFEC"));
			dr.setN_idtrabafec(usuarioAfectado);
		}		
		return dr;
	}
}
