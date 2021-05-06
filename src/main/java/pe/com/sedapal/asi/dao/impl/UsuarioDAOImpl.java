package pe.com.sedapal.asi.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.asi.dao.IUsuarioDAO;
import pe.com.sedapal.asi.model.Usuario;
import pe.com.sedapal.asi.util.DBConstants;

@Repository
public class UsuarioDAOImpl extends AbstractDAO implements IUsuarioDAO{
	
	public Usuario mapearUsuario (Map<String,Object> resultado) {
		this.error = null;
		Usuario usuario = null;	
		if (resultado != null) {
			List<Map<String, Object>> lista = (List<Map<String, Object>>)resultado.get("cursor_usuario");
			for(Map<String, Object> map : lista) {			
				usuario = new Usuario();
				usuario.setCodTrabajador(((BigDecimal)map.get("ncodtrabajador")).longValue());
				usuario.setCodFicha(((BigDecimal)map.get("ncodficha")).longValue());
				usuario.setNombUsuario((String)map.get("vnombre"));
				usuario.setCodUsuario((String)map.get("vcodusuario"));
				usuario.setCodPerfil(((BigDecimal)map.get("ncodperfil")).intValue());
				usuario.setDescPerfil((String)map.get("vnombreperfil"));
				usuario.setCodArea(((BigDecimal)map.get("ncodarea")).longValue());
				usuario.setCodAreaSuperior(((BigDecimal)map.get("ncodareasuperior")).longValue());
				usuario.setDescArea((String)map.get("vnombrearea"));
				usuario.setAbrevArea((String)map.get("vabrevarea"));
				usuario.setDescAreaSuperior((String)map.get("vnombreareasuperior"));
				usuario.setAbrevAreaSuperior((String)map.get("vabrevareasuperior"));
				usuario.setMail((String)map.get("vdirelectronica"));
				usuario.setTelefono((String)map.get("telefono"));
				usuario.setDescSede((String)map.get("vdescsede"));
				usuario.setEsGerente(((BigDecimal)map.get("esgerente")).intValue());
				usuario.setEsGerenteGral(((BigDecimal)map.get("esgerentegral")).intValue());
				usuario.setEsJefe(((BigDecimal)map.get("esjefe")).intValue());
				usuario.setEsSecretariaGerGral(((BigDecimal)map.get("essecretariagergral")).intValue());
				usuario.setEsSecretariaGerencias(((BigDecimal)map.get("essecretariagerencias")).intValue());
				usuario.setEsSecretariaEquipos(((BigDecimal)map.get("essecretariaequipos")).intValue());
				usuario.setVcargo((String)map.get("vcargo"));
				usuario.setVnomcargo((String)map.get("vnomcargo"));
				usuario.setNomSede((String)map.get("vnomsede"));
				usuario.setMsg_info_aprobadores((String) map.get("MSG_INFO_APROBADORES"));
				usuario.setEsAprobador(((BigDecimal)map.get("ES_APROBADOR")).longValue());
			}
		} 		
		return usuario;
	}
	
	@Override
	public Usuario consultarUsuario(String username) {
		this.error = null;
		Map<String, Object> out = null;
		 this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				 .withSchemaName(dbSchema)
				 	.withCatalogName(DBConstants.PACKAGE_SISTEMA)
					.withProcedureName(DBConstants.PROCEDURE_INFORMACION_USUARIO)
					.declareParameters(
							new SqlParameter("v_username",OracleTypes.VARCHAR),
							new SqlOutParameter("cursor_usuario", OracleTypes.CURSOR)); 
		 SqlParameterSource in = new MapSqlParameterSource()
					.addValue("v_username", username); 
		 out = this.jdbcCall.execute(in);
		 Usuario usuario = new Usuario();
		 usuario = mapearUsuario(out);
		 return usuario;
	}
}