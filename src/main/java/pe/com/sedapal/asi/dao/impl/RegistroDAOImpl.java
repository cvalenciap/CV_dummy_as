package pe.com.sedapal.asi.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.transaction.annotation.Transactional;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import pe.com.sedapal.asi.dao.IRegistroDAO;
import pe.com.sedapal.asi.model.AdjuntoMensaje;
import pe.com.sedapal.asi.model.DetalleSolicitud;
import pe.com.sedapal.asi.model.Solicitud;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.util.DBConstants;
import pe.com.sedapal.asi.util.StringUtil;

@Repository
public class RegistroDAOImpl extends AbstractDAO implements IRegistroDAO {
	private Logger logger = LoggerFactory.getLogger(RegistroDAOImpl.class);
	
	private Map<String, Object> requerimientos;
	
	public Map<String, Object> getRequerimientos() {
		return requerimientos;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Integer generarSolicitud(Solicitud cabecera, List<DetalleSolicitud> detalle, Long n_cod_area) throws SQLException {
		Map<String, Object> mapResultado = new HashMap<>();
		
		DetalleSolicitud item = new DetalleSolicitud();
		item = new DetalleSolicitud();
		Connection conn = null;
		StructDescriptor structDetalle=null;
		StructDescriptor structCabecera=null;
		ArrayDescriptor arrayDetalle=null;
		conn = this.jdbc.getDataSource().getConnection();
		structCabecera = StructDescriptor.createDescriptor("ARS.TYPE_ARS_OBJ_SOLICITUD", conn.getMetaData().getConnection());
		structDetalle = StructDescriptor.createDescriptor("ARS.TYPE_ARS_OBJ_SOLICITUD_DT", conn.getMetaData().getConnection());
		arrayDetalle = ArrayDescriptor.createDescriptor("ARS.TYPE_ARS_TAB_SOLICITUD_DT", conn.getMetaData().getConnection());
		Object iStructsCabecera = new Object();
		List<Object> iStructsDetalle = new ArrayList<>();  //este sera la base del ARRAY
		Object[] iCabecera = new Object[cabecera.getClass().getDeclaredFields().length]; // este es un registro del ARRAY
		iCabecera[1] = cabecera.getN_idtrabsoli();
		iCabecera[2] = cabecera.getD_fecha_soli();
		iCabecera[3] = cabecera.getN_ind_alta();
		iCabecera[4] = cabecera.getN_idarchadju() != null ? cabecera.getN_idarchadju().getIdAdjunto() : null;
		iCabecera[5] = cabecera.getN_estado();
		iCabecera[6] = cabecera.getA_v_usucre();
		iCabecera[8] = cabecera.getA_v_usumod();
		iStructsCabecera = new STRUCT(structCabecera,conn.getMetaData().getConnection(),iCabecera);
		Object[] iDetalle = new Object[item.getClass().getDeclaredFields().length]; // este es un registro del ARRAY
		for (int e=0; e < detalle.size(); e++) {
			iDetalle[0] = 0;
			iDetalle[1] = 0;
			iDetalle[2] = detalle.get(e).getN_id_cat_par().getIdcategoria();
			iDetalle[3] = detalle.get(e).getN_idtrabafec().getNcodtrabajador();
			iDetalle[4] = detalle.get(e).getN_aplic_par()!=null ? detalle.get(e).getN_aplic_par().getIdcategoria(): null;;
			iDetalle[5] = detalle.get(e).getV_perfil_tran();
			iDetalle[6] = detalle.get(e).getN_accion_par()!=null ? detalle.get(e).getN_accion_par().getIdaccion() : null;
			iDetalle[7] = detalle.get(e).getD_fec_finvig();
			iDetalle[8] = detalle.get(e).getN_rectic_par()!=null ? detalle.get(e).getN_rectic_par().getIdcategoria(): null;
			iDetalle[9] = detalle.get(e).getV_cuenta_red(); 
			iDetalle[10] = detalle.get(e).getV_cod_inven();
			iDetalle[11] = detalle.get(e).getN_ambcam_par()!=null ? detalle.get(e).getN_ambcam_par().getIdcategoria(): null;
			iDetalle[12] = detalle.get(e).getV_cta_orig();
			iDetalle[13] = detalle.get(e).getV_cta_dest();
			iDetalle[14] = detalle.get(e).getN_ti_pun_par();
			iDetalle[15] = detalle.get(e).getV_sustento();
			iDetalle[16] = detalle.get(e).getV_ubic_fis();
			iDetalle[17] = detalle.get(e).getV_tele_afec();
			iDetalle[18] = detalle.get(e).getN_modequ_par();
			iDetalle[19] = detalle.get(e).getN_motsol_par();
			iDetalle[20] = detalle.get(e).getV_anex_ofic();
			iDetalle[21] = detalle.get(e).getN_id_cont();
			iDetalle[22] = detalle.get(e).getV_pers_reas();
//			iDetalle[23] = detalle.get(e).getV_desc_obse();
			iDetalle[23] = StringUtil.encodeCaracterEspecial(detalle.get(e).getV_desc_obse());
			iDetalle[24] = detalle.get(e).getD_fe_usu_est();
			iDetalle[25] = detalle.get(e).getN_estado_par();
			iDetalle[26] = detalle.get(e).getN_idtrab_est();
			iDetalle[27] = detalle.get(e).getN_idarchadju().getIdAdjunto();
			iDetalle[28] = detalle.get(e).getV_id_ticket();
			iDetalle[29] = detalle.get(e).getV_id_envio();
			iDetalle[30] = detalle.get(e).getN_estado();
			iDetalle[31] = detalle.get(e).getA_v_usucre();
			iDetalle[32] = detalle.get(e).getA_d_feccre();
			iDetalle[33] = detalle.get(e).getA_v_usumod();
			iDetalle[34] = detalle.get(e).getA_d_fecmod();
			iDetalle[35] = detalle.get(e).getA_v_nomprg();		
			iDetalle[43] = detalle.get(e).getN_equ_par() != null ? detalle.get(e).getN_equ_par().getIdcategoria() : null;
			iDetalle[44] = detalle.get(e).getV_desc_tras();
			iDetalle[45] = detalle.get(e).getN_sedeor_par() != null ? detalle.get(e).getN_sedeor_par().getIdSede() : null;
			iDetalle[46] = detalle.get(e).getN_areaor_par() != null ? detalle.get(e).getN_areaor_par().getcodigo() : null;
			iDetalle[47] = detalle.get(e).getV_ubicor_fis();
			iDetalle[48] = detalle.get(e).getN_sedede_par() != null ? detalle.get(e).getN_sedede_par().getIdSede() : null;
			iDetalle[49] = detalle.get(e).getN_areade_par() != null ? detalle.get(e).getN_areade_par().getcodigo() : null;
			iDetalle[50] = detalle.get(e).getV_ubicde_fis();
			iDetalle[51] = detalle.get(e).getN_ficha_des();
			iDetalle[52] = detalle.get(e).getV_nombre_des();
			iDetalle[53] = detalle.get(e).getV_situsu_des() != null ? detalle.get(e).getV_situsu_des().getVcodtipo() : null;
			iDetalle[55] = detalle.get(e).getN_requ_par() != null ? detalle.get(e).getN_requ_par().getIdcategoria() : null;
			iDetalle[56] = detalle.get(e).getD_fe_inipres();
			iDetalle[57] = detalle.get(e).getD_fe_finpres();
			try {
				iStructsDetalle.add( new STRUCT(structDetalle,conn.getMetaData().getConnection(),iDetalle));
			} catch (Exception er) {
				System.out.println(er.getMessage());
			}
		}
		ARRAY datosIn = new ARRAY(arrayDetalle,conn.getMetaData().getConnection(),iStructsDetalle.toArray());
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_REGISTRO)
				.withProcedureName(DBConstants.PROCEDURE_REGISTRAR_SOLICITUD)
				.declareParameters(
						new SqlParameter("N_COD_AREA", OracleTypes.NUMBER),
						new SqlParameter("SOLICITUD", OracleTypes.STRUCT, "ARS.TYPE_ARS_OBJ_SOLICITUD"),
						new SqlParameter("LISTA", OracleTypes.ARRAY, "ARS.TYPE_ARS_TAB_SOLICITUD_DT"),
						new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR));
		 SqlParameterSource in = new MapSqlParameterSource()
				 	.addValue("N_COD_AREA", n_cod_area)
				 	.addValue("SOLICITUD", iStructsCabecera)
					.addValue("LISTA", datosIn); 
		 try {
			 out = this.jdbcCall.execute(in);
			 Integer resultado = (Integer)out.get("O_RETORNO");
			 if(resultado == 0) {
				 requerimientos = getIdsDetSoli(out);
				 mapResultado.put("resultado", 0);
				 mapResultado.put("requerimientos", requerimientos);
				 return resultado;
			 } else {
				String mensaje = (String)out.get("O_MENSAJE");
				String mensajeInterno = (String)out.get("O_SQLERRM");
				this.error = new Error(resultado,mensaje,mensajeInterno);
				return 1;
			 } 
		 } catch (Exception e) {
			e.printStackTrace();
			logger.error(out == null ? "Null" : "Not null");
			 
			String mensaje = (String)out.get("O_MENSAJE");
			String mensajeInterno = (String)out.get("O_SQLERRM");
			
			logger.error("O_MENSAJE=" + mensaje);
			logger.error("O_SQLERRM=" + mensajeInterno);
			
			this.error = new Error(1,mensaje,mensajeInterno);
			return 1;
		 }
	}
	
	private Map<String, Object> getIdsDetSoli(Map<String, Object> out) {
		Map<String, Object> mapaSoliDet = new HashMap<>();
		
		List<Long> idsDetalleSolicitudes = new ArrayList<>();
		Long idSolicitud = null;
		
		List<Map<String, Object>> lista = (List<Map<String, Object>>) out.get("O_CURSOR");
		
		if (lista != null && lista.size() > 0) {
			for(Map<String, Object> record : lista) {
				idSolicitud = ((BigDecimal)record.get("N_ID_SOLI")).longValue();
				idsDetalleSolicitudes.add(((BigDecimal)record.get("N_DET_SOLI")).longValue());
			}
		}
		
		mapaSoliDet.put("requerimientos", idsDetalleSolicitudes);
		mapaSoliDet.put("solicitud", idSolicitud);
		
		return mapaSoliDet;
	}
	
	public List<AdjuntoMensaje> guardarAdjuntos(List<AdjuntoMensaje> adjuntos) throws SQLException {
		AdjuntoMensaje item = new AdjuntoMensaje();
		Connection conn = null;
		StructDescriptor structAdjunto=null;
		ArrayDescriptor arrayAdjuntos=null;
		conn = this.jdbc.getDataSource().getConnection();

		structAdjunto = StructDescriptor.createDescriptor("ARS.TYPE_ARS_OBJ_ARCH_ADJUNTO", conn.getMetaData().getConnection());
		arrayAdjuntos = ArrayDescriptor.createDescriptor("ARS.TYPE_ARS_TAB_ARCH_ADJUNTO", conn.getMetaData().getConnection());
		List<Object> iStructsAdjuntos = new ArrayList<>();  //este sera la base del ARRAY
		
		Object[] iAdjuntos = new Object[item.getClass().getDeclaredFields().length]; // este es un registro del ARRAY
		for (int e=0; e < adjuntos.size(); e++) {
			iAdjuntos[0] = 0;
			iAdjuntos[1] = adjuntos.get(e).getNombreAdjunto();
			iAdjuntos[2] = adjuntos.get(e).getExtensionAdjunto();
			iAdjuntos[3] = adjuntos.get(e).getUrlAdjunto();
			iAdjuntos[4] = adjuntos.get(e).getN_estado();
			iAdjuntos[5] = adjuntos.get(e).getA_v_usucre();
			iAdjuntos[7] = adjuntos.get(e).getA_v_usumod();
			iStructsAdjuntos.add( new STRUCT(structAdjunto,conn.getMetaData().getConnection(),iAdjuntos));
		}
		
		ARRAY datosIn = new ARRAY(arrayAdjuntos,conn.getMetaData().getConnection(),iStructsAdjuntos.toArray());
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_REGISTRO)
				.withProcedureName(DBConstants.PROCEDURE_GUARDAR_ADJUNTOS)
				.declareParameters(
						new SqlParameter("LISTA", OracleTypes.ARRAY, "ARS.TYPE_ARS_TAB_ARCH_ADJUNTO"),
						new SqlOutParameter("C_ADJUNTOS", OracleTypes.CURSOR),
						new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR));
		 SqlParameterSource in = new MapSqlParameterSource()
					.addValue("LISTA", datosIn); 
		 try {
			 out = this.jdbcCall.execute(in);
			 Integer resultado = (Integer)out.get("O_RETORNO");
			 if(resultado == 0) {
				 List<Map<Object, Object>> listaAdjuntos = (List<Map<Object, Object>>)out.get("C_ADJUNTOS");
			     List<AdjuntoMensaje> listaAdj = new ArrayList<>();
			     AdjuntoMensaje adjunto = null;
			     for(Map<Object, Object> map : listaAdjuntos) {
			    	 adjunto = new AdjuntoMensaje();
			    	 adjunto.setIdAdjunto(((BigDecimal) map.get("N_IDARCHADJU")).intValue());
			    	 adjunto.setUrlAdjunto((String) map.get("V_RUTA_ADJUN"));
			    	 listaAdj.add(adjunto);
			     }
				 return listaAdj;
			 } else {
				String mensaje = (String)out.get("O_MENSAJE");
				String mensajeInterno = (String)out.get("O_SQLERRM");
				this.error = new Error(resultado,mensaje,mensajeInterno);
				return null;
			 } 
		 } catch (Exception e) {
			String mensaje = (String)out.get("O_MENSAJE");
			String mensajeInterno = (String)out.get("O_SQLERRM");
			this.error = new Error(1,mensaje,mensajeInterno);
			return null;
		 }
	}
}
