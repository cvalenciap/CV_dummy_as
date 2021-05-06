package pe.com.sedapal.asi.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import pe.com.sedapal.asi.dao.IAreasDAO;
import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.Trabajador;
import pe.com.sedapal.asi.model.enums.Estado;
import pe.com.sedapal.asi.model.request_objects.AreaRequest;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Nivel;
import pe.com.sedapal.asi.util.DBConstants;

@Repository
public class AreasDAOImpl extends AbstractDAO implements IAreasDAO {
	private Logger logger = LoggerFactory.getLogger(AreasDAOImpl.class);

    public List<Area> mapearAreas(Map<String, Object> resultados, Long codigo) {
    	this.error = null;
        List<Area> listaareas = new ArrayList<>();
        Area item = null;
        List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
        for(Map<String, Object> map : lista) {
               item = new Area();
               item.setcodigo(((BigDecimal)map.get("NCODAREA")).longValue());
               item.setdescripcion((String)map.get("VDESCRIPCION"));
               item.setabreviatura((String)map.get("VABREVIATURA"));
               listaareas.add(item);
        }
        return listaareas;
    }

	private List<Trabajador> mapearJefeArea(Map<String, Object> resultados){
		this.error = null;
		Trabajador item = null;
		Area area = null;
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
		List<Trabajador> listatrabajadores = new ArrayList<>();
		for(Map<String, Object> map : lista) {
			item = new Trabajador();
			item.setficha(((BigDecimal)map.get("NFICHA")).longValue());
			item.setnombre((String)map.get("VNOMBRES"));
			item.setapellidoPaterno((String)map.get("VAPEPATERNO"));
			item.setapellidoMaterno((String)map.get("VAPEMATERNO"));
			if (map.get("NCODAREA")!=null) {
				area=new Area();
					area.setcodigo(((BigDecimal)map.get("NCODAREA")).longValue());
					area.setdescripcion((String) map.get("VDESCRIPCIONAREA"));
			
						
				item.setarea(area);
				item.setUsuarioCreacion((String) map.get("VRESCRE"));
				Timestamp fechaCreacion = (Timestamp) map.get("DFECCRE");
				item.setFechaRegistro(fechaCreacion.getTime());			
				item.setUsuarioModificacion((String) map.get("VRESACT"));
				Timestamp fechaTimer = (Timestamp) map.get("DFECACT");
				item.setFechaActualizacion(fechaTimer.getTime());
			}
			listatrabajadores.add(item);
		}
		return listatrabajadores;
	}
	
    @Override
    public List<Area> obtenerAreas(AreaRequest areaRequest, PageRequest pageRequest, Long codigo) {
          Map<String, Object> out = null;
          List<Area> lista = new ArrayList<>();
          this.error = null;
          this.jdbcCall = new SimpleJdbcCall(this.jdbc)
                        .withSchemaName(dbSchema)                      
                        .withCatalogName(DBConstants.PACKAGE_ARS_MANTENIMIENTO)
        				.withProcedureName(DBConstants.PROCEDURE_OBTENER_AREAS)
        				.declareParameters(
				                            new SqlParameter("i_ncodigo", OracleTypes.NUMBER),
				                            new SqlParameter("i_pagina", OracleTypes.NUMBER),
				                            new SqlParameter("i_registros", OracleTypes.NUMBER),                                   
				                            new SqlOutParameter("o_cursor", OracleTypes.CURSOR),				                            
				                            new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
				                            new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
				                            new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR)
				                            );
          
          SqlParameterSource in = new MapSqlParameterSource()
                        .addValue("i_ncodigo", codigo)
                        .addValue("i_pagina", pageRequest.getPagina());
          try {
                 out = this.jdbcCall.execute(in);
                 Integer resultado = (Integer)out.get("o_retorno");
		          if(resultado == DBConstants.OK) {
		        	  	lista = mapearAreas(out,codigo);
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

	@Override
	public Trabajador obtenerJefeArea(Long codarea) {
		Map<String, Object> out = null;
		List<Trabajador> lista = new ArrayList<>();
		this.error = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName(dbSchema)
				.withCatalogName(DBConstants.PACKAGE_MANTENIMIENTO)
				.withProcedureName(DBConstants.PROCEDURE_AREA_JEFE_OBTENER)
				.declareParameters(
						new SqlParameter("i_ncodarea", OracleTypes.NUMBER),
						new SqlOutParameter("o_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR));		
		SqlParameterSource in = new MapSqlParameterSource().addValue("i_ncodarea", codarea);
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");
				if(resultado == DBConstants.OK) {
					lista = mapearJefeArea(out);
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
		return lista.size()>0?lista.get(0):null;
	}
	
	@Override
	public Trabajador actualizarJefeArea(Long codArea,Trabajador trabajador, String usuario) {
		Map<String, Object> out = null;
		this.error = null;
		List<Trabajador> lista = new ArrayList<>();
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
   				.withSchemaName(dbSchema)
   				.withCatalogName(DBConstants.PACKAGE_MANTENIMIENTO)
   				.withProcedureName(DBConstants.PROCEDURE_AREA_JEFE_GUARDAR)
				.declareParameters(
						new SqlParameter("i_ncodarea", OracleTypes.NUMBER),
					//	new SqlParameter("n_codtrabajador", OracleTypes.NUMBER),
						new SqlParameter("i_nficha", OracleTypes.NUMBER),
						new SqlParameter("i_vapepaterno", OracleTypes.VARCHAR),
						new SqlParameter("i_vapematerno", OracleTypes.VARCHAR),
						new SqlParameter("i_vnombres", OracleTypes.VARCHAR),
						new SqlParameter("i_vusuario", OracleTypes.VARCHAR),
						new SqlOutParameter("o_cursor", OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR));	
		
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("i_ncodarea", codArea)		
		.addValue("i_nficha", trabajador.getficha())
		.addValue("i_vapepaterno", trabajador.getapellidoPaterno())
		.addValue("i_vapematerno", trabajador.getapellidoMaterno())
		.addValue("i_vnombres", trabajador.getnombre())
		.addValue("i_vusuario", usuario);		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");
			if(resultado == DBConstants.OK) {
				lista = mapearJefeArea(out);				
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
		return lista.size()>0?lista.get(0):null;
	}
	
	/*validar jefe*/
    @Override
    public Trabajador validarJefe(Long codarea,Long nficha) {
           Map<String, Object> out = null;
           this.error = null;
           List<Trabajador> lista = new ArrayList<>();
           /*Long validacionJefe=0L;*/
           this.jdbcCall = new SimpleJdbcCall(this.jdbc)
   				.withSchemaName(dbSchema)
   				.withCatalogName(DBConstants.PACKAGE_MANTENIMIENTO)
   				.withProcedureName(DBConstants.PROCEDURE_AREA_JEFE_VALIDAR)
                        .declareParameters(
                                      new SqlParameter("i_ncodarea", OracleTypes.NUMBER),
                                      new SqlParameter("i_nficha", OracleTypes.NUMBER),
                                      new SqlOutParameter("o_cursor", OracleTypes.CURSOR),
                                      new SqlOutParameter("o_retorno", OracleTypes.INTEGER),
                                      new SqlOutParameter("o_mensaje", OracleTypes.VARCHAR),
                                      new SqlOutParameter("o_sqlerrm", OracleTypes.VARCHAR));              
           SqlParameterSource in = new MapSqlParameterSource()
                                        .addValue("i_ncodarea", codarea)
                                .addValue("i_nficha", nficha);
           try {
              out = this.jdbcCall.execute(in);
              Integer resultado = (Integer)out.get("o_retorno");
	           if(resultado == DBConstants.OK) {
	        	    lista = mapearJefeArea(out);
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
           return lista.size()>0?lista.get(0):null;
    }

    @Override
    public List<Area> obtenerAreasEquipos(Long codigo) {
          Map<String, Object> out = null;
          List<Area> lista = new ArrayList<>();
          this.error = null;
          this.jdbcCall = new SimpleJdbcCall(this.jdbc)
                        .withSchemaName(dbSchema)                      
                        .withCatalogName(DBConstants.PACKAGE_ARS_MANTENIMIENTO)
        				.withProcedureName(DBConstants.PROCEDURE_OBTENER_AREAS)
        				.declareParameters(
				                            new SqlParameter("i_ncodigo", OracleTypes.NUMBER),
				                            new SqlParameter("i_registros", OracleTypes.NUMBER),                                   
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
		        	  	lista = mapearAreas(out,codigo);
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