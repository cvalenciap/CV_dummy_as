package pe.com.sedapal.asi.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import pe.com.sedapal.asi.dao.IContactoDAO;
import pe.com.sedapal.asi.model.Contacto;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Nivel;
import pe.com.sedapal.asi.util.DBConstants;

@Repository
public class ContactoDAOImpl extends AbstractDAO implements IContactoDAO{
	private Logger logger = LoggerFactory.getLogger(AreasDAOImpl.class);

    public List<Contacto> mapearContactos(Map<String, Object> resultados, Long codigo) {
    	this.error = null;
        List<Contacto> listcontacto = new ArrayList<>();
        Contacto item = null;
        List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
        for(Map<String, Object> map : lista) {
               item = new Contacto();
               item.setN_id_cont(((BigDecimal)map.get("N_ID_CONT")).longValue());
               item.setN_cod_area(((BigDecimal)map.get("N_COD_AREA")).longValue());
               item.setNaresuperior(((BigDecimal)map.get("NARESUPERIOR")).longValue());
               item.setV_nomb_cont((String)map.get("V_NOMB_CONT"));
               item.setV_tele_cont((String)map.get("V_TELE_CONT"));
               item.setGerencia((String)map.get("GERENCIA"));
               item.setEquipo((String)map.get("EQUIPO"));
               listcontacto.add(item);
        }
        return listcontacto;
    }
    @Override
    public List<Contacto> obtenerListaContacto(Long codigo) {
          Map<String, Object> out = null;
          List<Contacto> lista = new ArrayList<>();
          this.error = null;
          this.jdbcCall = new SimpleJdbcCall(this.jdbc)
                        .withSchemaName(dbSchema)                      
                        .withCatalogName(DBConstants.PACKAGE_ARS_MANTENIMIENTO)
        				.withProcedureName(DBConstants.PROCEDURE_OBTENER_CONTACTOS)
        				.declareParameters(
				                            new SqlParameter("i_ncodigo", OracleTypes.NUMBER),                                   
				                            new SqlOutParameter("o_cursor", OracleTypes.CURSOR),				                            
				                            new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
				                            new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
				                            new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR)
				                            );
          
          SqlParameterSource in = new MapSqlParameterSource()
                        .addValue("i_ncodigo", codigo);
          try {
                 out = this.jdbcCall.execute(in);
                 Integer resultado = (Integer)out.get("o_retorno");
		          if(resultado == DBConstants.OK) {
		        	  	lista = mapearContactos(out,codigo);
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
