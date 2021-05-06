package pe.com.sedapal.asi.dao.impl;

import java.math.BigDecimal;
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
import pe.com.sedapal.asi.dao.ITrabajadoresDAO;
import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.Trabajador;
import pe.com.sedapal.asi.model.request_objects.TrabajadorRequest;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Nivel;
import pe.com.sedapal.asi.util.DBConstants;

@Repository
public class TrabajadoresDAOImpl extends AbstractDAO implements ITrabajadoresDAO{
	
	private List<Trabajador> mapearTrabajadores(Map<String, Object> resultados){
		this.error = null;
		Trabajador item = null;
		Area itemArea = null;
		Trabajador itemJefe = null;
		Boolean jefeEncontrado = false;
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
		List<Trabajador> listatrabajadores = new ArrayList<>();
		
		for(Map<String, Object> map : lista) {
			item = new Trabajador();
			itemArea = new Area();
			item.setficha(((BigDecimal)map.get("NCODTRABAJADOR")).longValue());
			item.setficha(((BigDecimal)map.get("NFICHA")).longValue());
			itemArea.setcodigo(((BigDecimal)map.get("NCODAREA")).longValue());
			item.setnombre((String)map.get("VNOMBRES"));
			item.setapellidoPaterno((String)map.get("VAPEPATERNO"));
			item.setapellidoMaterno((String)map.get("VAPEMATERNO"));
			item.setNombreCompleto((String)map.get("VNOMBRECOMPLETO"));
			item.setcargo((String)map.get("VCARGO"));
			item.setcorreo((String)map.get("CORREO"));
			item.setJefe(((BigDecimal) map.get("NJEFE")).intValue());
			if(item.getJefe()==1) {
				jefeEncontrado = true;
			}			
			itemArea.setdescripcion((String)map.get("VDESCRIPCIONAREA"));
			itemArea.setabreviatura((String)map.get("VABREVIATURAAREA"));
			item.setarea(itemArea);
			
			item.setubicacion((String)map.get("VUBICACION"));
			if(map.get("NANEXO")!=null) {
				item.setanexo(((BigDecimal)map.get("NANEXO")).longValue());
			}else {
				item.setanexo(null);
			}
			item.setRevisor(((BigDecimal)map.get("ESREVISOR")).longValue());
			
			listatrabajadores.add(item);
		}
		
		return listatrabajadores;
	}
	
	@Override
	public List<Trabajador> obtenerTrabajadores(TrabajadorRequest trabajadorRequest) {
		Map<String, Object> out = null;
		List<Trabajador> lista = new ArrayList<>();
		this.error = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_ARS_MANTENIMIENTO)
				.withProcedureName(DBConstants.PROCEDURE_TRABAJADORES_OBTENER)
				.declareParameters(
						new SqlParameter("i_ncodtrab", OracleTypes.NUMBER),
						new SqlParameter("i_nficha", OracleTypes.NUMBER),
						new SqlOutParameter("o_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR));
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("i_ncodtrab", trabajadorRequest.getCodTrabajador())
		.addValue("i_nficha", trabajadorRequest.getNumeroFicha());
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer)out.get("o_retorno");
			if(resultado == DBConstants.OK) {
				lista = mapearTrabajadores(out);
			} else if (resultado == DBConstants.ERR_SOLICITUD) {
				String mensaje = (String)out.get("o_mensaje");
				String mensajeInterno = (String)out.get("o_sqlerrm");
				this.error = new Error(resultado,mensaje,mensajeInterno,Nivel.REQUEST);
			} else {
				String mensaje = (String)out.get("o_mensaje");
				String mensajeInterno = (String)out.get("o_sqlerrm");
				this.error = new Error(resultado,mensaje,mensajeInterno,Nivel.SERVICE);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			Integer resultado = (Integer)out.get("o_retorno");
			String mensaje = (String)out.get("o_mensaje");
			String mensajeInterno = (String)out.get("o_sqlerrm");
			this.error = new Error(resultado,mensaje,mensajeInterno);
		}
		return lista;
	}
}