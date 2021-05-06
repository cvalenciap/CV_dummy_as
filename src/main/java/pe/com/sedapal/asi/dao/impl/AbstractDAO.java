package pe.com.sedapal.asi.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Paginacion;

public class AbstractDAO {

	@Autowired
	protected JdbcTemplate jdbc;
	
	protected SimpleJdbcCall jdbcCall;
	
	@Value("${app.config.database.schema}")
	protected String dbSchema;
	
	protected Error error;
	
	protected Paginacion paginacion;
	
	public void setSchema(String schema) {
		dbSchema = schema;
	}
	
	public Error getError() {
		return this.error;
	}
	
	public Paginacion getPaginacion() {
		return this.paginacion;
	}
}
