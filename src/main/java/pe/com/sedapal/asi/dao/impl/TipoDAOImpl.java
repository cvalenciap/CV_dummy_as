package pe.com.sedapal.asi.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;
import pe.com.sedapal.asi.dao.ITipoDAO;
import pe.com.sedapal.asi.model.Tipo;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Nivel;
import pe.com.sedapal.asi.util.DBConstants;

@Repository
public class TipoDAOImpl extends AbstractDAO implements ITipoDAO {
	
	@SuppressWarnings("unchecked")
	private List<Tipo> mapearTipos(Map<String, Object> resultados) {
		this.error = null;
		Tipo item;
		List<Tipo> listatipos = new ArrayList<>();		
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");		
		for(Map<String, Object> map : lista) {
			item = new Tipo();
			item.setCodigo((String)map.get("VCODTIPO"));					
			item.setDescripcion((String)map.get("VDESCRIPCION"));
			listatipos.add(item);
		}
		return listatipos;
	}
	
	@Override
	public List<Tipo> consultarTipos(String grupo) {
		this.error = null;
		Map<String, Object> out = null;
		List<Tipo> lista = new ArrayList<>();
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_MANTENIMIENTO)
				.withProcedureName(DBConstants.PROCEDURE_TIPO_OBTENER)
				.declareParameters(
						new SqlParameter("i_vcodgrupo", OracleTypes.VARCHAR),						
						new SqlOutParameter("o_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR)); 		
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("i_vcodgrupo", grupo);
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer)out.get("o_retorno");
			if(resultado == DBConstants.OK) {
				lista = mapearTipos(out);
			} else if (resultado == DBConstants.ERR_SOLICITUD) {
				String mensaje = (String)out.get("o_mensaje");
				String mensajeInterno = (String)out.get("o_sqlerrm");
				this.error = new Error(resultado,mensaje,mensajeInterno,Nivel.REQUEST);
			} else {
				String mensaje = (String)out.get("o_mensaje");
				String mensajeInterno = (String)out.get("o_sqlerrm");
				this.error = new Error(resultado,mensaje,mensajeInterno,Nivel.SERVICE);
			}
		}catch(Exception e){
			Integer resultado = (Integer)out.get("o_retorno");
			String mensaje = (String)out.get("o_mensaje");
			String mensajeInterno = (String)out.get("o_sqlerrm");
			this.error = new Error(resultado,mensaje,mensajeInterno);
		}
		return lista;
	}
}