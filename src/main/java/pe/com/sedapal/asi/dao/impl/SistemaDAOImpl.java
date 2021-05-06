package pe.com.sedapal.asi.dao.impl;

import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.asi.dao.ISistemaDAO;
import pe.com.sedapal.asi.model.InformacionSistema;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.util.DBConstants;

@Repository
public class SistemaDAOImpl extends AbstractDAO implements ISistemaDAO {
		
	@Override
	public void obtenerParametros(InformacionSistema informacionSistema) {
		Map<String, Object> out = null;		
		 this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				 .withSchemaName(dbSchema)
				 	.withCatalogName(DBConstants.PACKAGE_SISTEMA)
					.withProcedureName(DBConstants.PROCEDURE_PARAMETROS_SISTEMA)
					.declareParameters(
							new SqlParameter("N_CODAREA", OracleTypes.NUMBER),
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
		 SqlParameterSource in = new MapSqlParameterSource()
					.addValue("N_CODAREA", null); 		 
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer)out.get("O_RETORNO");
			if(resultado == 0) {
				informacionSistema.setMensaje((String)out.get("v_mensaje"));
			} else {
				String mensaje = (String)out.get("O_MENSAJE");
				String mensajeInterno = (String)out.get("O_SQLERRM");
				this.error = new Error(resultado,mensaje,mensajeInterno);
			}
		}catch(Exception e){
			Integer resultado = (Integer)out.get("O_RETORNO");
			String mensaje = (String)out.get("O_MENSAJE");
			String mensajeInterno = (String)out.get("O_SQLERRM");
			this.error = new Error(resultado,mensaje,mensajeInterno);
		}
	}
}
