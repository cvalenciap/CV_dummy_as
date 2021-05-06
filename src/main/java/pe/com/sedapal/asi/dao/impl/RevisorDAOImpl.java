package pe.com.sedapal.asi.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
import pe.com.sedapal.asi.dao.IRevisorDAO;
import pe.com.sedapal.asi.model.Revisor;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.util.DBConstants;

@Repository
public class RevisorDAOImpl extends AbstractDAO implements IRevisorDAO {
	private Logger logger = LoggerFactory.getLogger(RevisorDAOImpl.class);

	@Override
	public List<Revisor> obtenerRevisoresPorSolicitud(Long idSolicitud) {
		logger.info("Obteniendo revisores y sus areas para la solicitud " + idSolicitud);
		
		Map<String, Object> out = null;
		List<Revisor> listaRevisores = new ArrayList<>();
		
		Integer resultado = 0;
		String mensaje;
		String mensajeInterno;
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_BANDEJA)
				.withProcedureName(DBConstants.PROCEDURE_OBTENER_REVISORES_AREAS)
				.declareParameters(
						new SqlParameter("I_N_ID_SOLI", OracleTypes.NUMBER),
						new SqlOutParameter("O_CURSOR", OracleTypes.CURSOR),
						new SqlOutParameter("O_RETORNO", OracleTypes.INTEGER),
						new SqlOutParameter("O_MENSAJE", OracleTypes.VARCHAR),
						new SqlOutParameter("O_SQLERRM", OracleTypes.VARCHAR));
		
		try {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("I_N_ID_SOLI", idSolicitud);
		
			out = this.jdbcCall.execute(in);
			resultado = (Integer)out.get("O_RETORNO");
			
			if(resultado != null && resultado.intValue() == 0) {
				listaRevisores = mapRevisores(out);
			} else {
				mensaje = (String)out.get("O_MENSAJE");
				mensajeInterno = (String)out.get("O_SQLERRM");
				this.error = new Error(resultado,mensaje,mensajeInterno);
				logger.error("Error al obtener revisores.", mensajeInterno);
			}
		} catch(Exception e){
			if(resultado != null && resultado.intValue() > 0) {
				this.error = new Error(resultado,(String)out.get("O_MENSAJE"),(String)out.get("O_SQLERRM"));
			} else {
				this.error = new Error(resultado,"Error",e.getMessage());
			}
			
			logger.error("Error al obtener revisores.", e.getCause());
		}
		
		return listaRevisores;
	}
	
	@SuppressWarnings("unchecked")
	private List<Revisor> mapRevisores(Map<String, Object> resultados) {
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("O_CURSOR");
		Set<Revisor> listaRevisores = new HashSet<>();
		
		Revisor revisor = null;
		
		for(Map<String, Object> map : lista) {
			revisor = new Revisor();
			revisor.setCodArea(((BigDecimal)map.get("NCODAREA")).longValue());
			revisor.setNombreArea((String)map.get("VDESCRIPCION"));
			revisor.setAbrevArea((String)map.get("VABREVIATURA"));
			
			revisor.setCodAreaSuperior(((BigDecimal)map.get("SNCODAREA")).longValue());
			revisor.setNombreAreaSuperior((String)map.get("SVDESCRIPCION"));
			revisor.setAbrevAreaSuperior((String)map.get("SVABREVIATURA"));
			
			revisor.setOrden(((BigDecimal)map.get("N_ORDEN_REVI")).longValue());
			revisor.setNcodtrabajador(((BigDecimal)map.get("NCODTRABAJADOR")).longValue());
			revisor.setNficha(((BigDecimal)map.get("NFICHA")).longValue());
			revisor.setNombres((String)map.get("VNOMBRES"));
			revisor.setApPaterno((String)map.get("VAPEPATERNO"));
			revisor.setApMaterno((String)map.get("VAPEMATERNO"));
			revisor.setCargo((String)map.get("VCARGO"));
			revisor.setCorreo((String)map.get("VDIRELECTRONICA"));
			revisor.setNombreCompleto((String)map.get("NOMBRECOMPLETO"));
			
			listaRevisores.add(revisor);
		}
		
		return new ArrayList<Revisor>(listaRevisores);
	}
}
