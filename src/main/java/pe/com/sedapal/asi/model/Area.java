/**
 * @package       pe.com.sedapal.ssi.model
 * @class         Area
 * @description   representa las areas de la empresa
 * @author        sayda moises

 * -------------------------------------------------------------------------------------
 * Historia de modificaciones
 * Requerimiento    Autor       Fecha         Descripci�n
 * -------------------------------------------------------------------------------------
 */
package pe.com.sedapal.asi.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import pe.com.sedapal.asi.model.enums.Estado;

public class Area {
	private Long codigo;
	@JsonInclude(Include.NON_NULL)
	private String descripcion;
	@JsonInclude(Include.NON_NULL)
	private String abreviatura;
	@JsonInclude(Include.NON_NULL)
	private Trabajador jefe;
	@JsonInclude(Include.NON_NULL)
	private Estado estado;
	@JsonInclude(Include.NON_NULL)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime fechaRegistro;
	@JsonInclude(Include.NON_NULL)
	private String responsable;
	@JsonInclude(Include.NON_NULL)
	private String nombre;

	public Area() {
		super();
	}

	public Area(Long codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public Area(Long codigo, String descripcion, String abreviatura) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.abreviatura = abreviatura;
	}

	public Area(String abreviatura) {
		super();
		this.abreviatura = abreviatura;
	}

	public Area(String abreviatura, Trabajador jefe) {
		super();
		this.abreviatura = abreviatura;
		this.jefe = jefe;
	}

	public Area(String abreviatura,String descripcion, Trabajador jefe) {
		super();
		this.abreviatura = abreviatura;
		this.descripcion = descripcion;
		this.jefe = jefe;
	}
	
	public Area(Long codigo, String descripcion, String abreviatura, Trabajador jefe, Estado estado,
			LocalDateTime fechaRegistro, String responsable, String nombre) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.abreviatura = abreviatura;
		this.jefe = jefe;
		this.estado = estado;
		this.fechaRegistro = fechaRegistro;
		this.responsable = responsable;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getcodigo() {
		return codigo;
	}

	public void setcodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getdescripcion() {
		return descripcion;
	}

	public void setdescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getabreviatura() {
		return abreviatura;
	}

	public void setabreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Trabajador getjefe() {
		return jefe;
	}

	public void setjefe(Trabajador jefe) {
		this.jefe = jefe;
	}

	public Estado getestado() {
		return estado;
	}

	public void setestado(Estado estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Object fechaRegistro) {
		try {
			if (fechaRegistro != null) {
				if (fechaRegistro instanceof String) {
					this.fechaRegistro = LocalDateTime.parse((String) fechaRegistro);
				} else if (fechaRegistro instanceof LocalDate) {
					this.fechaRegistro = (LocalDateTime) fechaRegistro;
				} else if (fechaRegistro instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long) fechaRegistro);
					this.fechaRegistro = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
				} else {
					this.fechaRegistro = null;
				}
			}
		} catch (Exception e) {
			this.fechaRegistro = null;
		}
	}

	public String getresponsable() {
		return responsable;
	}

	public void setresponsable(String responsable) {
		this.responsable = responsable;
	}
}