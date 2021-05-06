/**
 * @package       pe.com.sedapal.ssi.model
 * @class         Trabajador
 * @description   representa el trabajador de area
 * @author        sayda moises

 * -------------------------------------------------------------------------------------
 * Historia de modificaciones
 * Requerimiento    Autor       Fecha         Descripci�n
 * -------------------------------------------------------------------------------------
 */
package pe.com.sedapal.asi.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Trabajador {
	@JsonInclude(Include.NON_NULL)
	Long codigo;
	Long ficha;
	@JsonInclude(Include.NON_NULL)
	String nombre;
	@JsonInclude(Include.NON_NULL)
	String apellidoPaterno;
	@JsonInclude(Include.NON_NULL)
	String apellidoMaterno;
	@JsonInclude(Include.NON_NULL)
	Area area;
	@JsonInclude(Include.NON_NULL)
	String cargo;
	@JsonInclude(Include.NON_NULL)
	String ubicacion;
	@JsonInclude(Include.NON_NULL)
	Long anexo;
	@JsonInclude(Include.NON_NULL)
	String correo;
	@JsonInclude(Include.NON_NULL)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime fechaRegistro;
	@JsonInclude(Include.NON_NULL)
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime fechaActualizacion;
	@JsonInclude(Include.NON_NULL)
	String usuarioCreacion;
	@JsonInclude(Include.NON_NULL)
	String usuarioModificacion;
	@JsonInclude(Include.NON_NULL)
	String nombreCompleto;
	@JsonInclude(Include.NON_NULL)
	int jefe;
	@JsonInclude(Include.NON_NULL)
	Long estadoModificado;
	@JsonInclude(Include.NON_NULL)
	Long secuencial;
	@JsonInclude(Include.NON_NULL)
	Long estado;
	
	Long revisor;
	
	@Override
	public boolean equals(Object o) {
		// If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Trabajador)) { 
            return false; 
        }           
        // typecast o to Complex so that we can compare data members  
        Trabajador c = (Trabajador) o; 
          
        // Compare the data members and return accordingly  
        return (correo != null && correo.equals(c.getcorreo())) && 
        	   ((codigo != null && codigo.equals(c.getCodigo())) || (ficha != null && ficha.equals(c.getficha())));
	}
	
	@Override
	public int hashCode() {
		int hashCode = 13; // numero primo cualquiera
		hashCode = hashCode + (correo != null ? correo.hashCode() : 0);
		hashCode = hashCode + (codigo != null ? codigo.hashCode() : 0);
		hashCode = hashCode + (ficha != null ? ficha.hashCode() : 0);
		
		return hashCode;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo=codigo;
	}
	
	public Long getEstadoModificado() {
		return estadoModificado;
	}

	public void setEstadoModificado(Long estadoModificado) {
		this.estadoModificado = estadoModificado;
	}

	public Long getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(Long secuencial) {
		this.secuencial = secuencial;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public int getJefe() {
		return jefe;
	}
	
	public void setJefe(int jefe) {
		this.jefe=jefe;
	}
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto=nombreCompleto;
	}
	
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}	
	public Long getficha() {
		return ficha;
	}
	public void setficha(Long ficha) {
		this.ficha = ficha;
	}
	public String getnombre() {
		return nombre;
	}
	public void setnombre(String nombre) {
		this.nombre = nombre;
	}
	public String getapellidoPaterno() {
		return apellidoPaterno;
	}
	public void setapellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getapellidoMaterno() {
		return apellidoMaterno;
	}
	public void setapellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public Area getarea() {
		return area;
	}
	public void setarea(Area area) {
		this.area = area;
	}
	public String getcargo() {
		return cargo;
	}
	public void setcargo(String cargo) {
		this.cargo = cargo;
	}
	public String getubicacion() {
		return ubicacion;
	}
	public void setubicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public Long getanexo() {
		return anexo;
	}
	public void setanexo(Long anexo) {
		this.anexo = anexo;
	}
	public String getcorreo() {
		return correo;
	}
	public void setcorreo(String correo) {
		this.correo = correo;
	}
	
	public Long getRevisor() {
		return revisor;
	}

	public void setRevisor(Long revisor) {
		this.revisor = revisor;
	}

	public LocalDateTime getFechaRegistro() {
		return this.fechaRegistro;
	}
	public void setFechaRegistro(Object fechaRegistro) {
		try {
			if (fechaRegistro != null) {
				if (fechaRegistro instanceof String) {
					this.fechaRegistro = LocalDateTime.parse((String)fechaRegistro);
				} else if (fechaRegistro instanceof LocalDate) {
					this.fechaRegistro = (LocalDateTime)fechaRegistro;
				} else if (fechaRegistro instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long)fechaRegistro);
					this.fechaRegistro = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
				} else {
					this.fechaRegistro = null;
				}
			} 
		} catch (Exception e) {
			this.fechaRegistro = null;
		}
	}
	public LocalDateTime getFechaActualizacion() {
		return this.fechaActualizacion;
	}
	public void setFechaActualizacion(Object fechaActualizacion) {
		try {
			if (fechaActualizacion != null) {
				if (fechaActualizacion instanceof String) {
					this.fechaActualizacion = LocalDateTime.parse((String)fechaActualizacion);
				} else if (fechaActualizacion instanceof LocalDate) {
					this.fechaActualizacion = (LocalDateTime)fechaActualizacion;
				} else if (fechaActualizacion instanceof Long) {
					Instant instant = Instant.ofEpochMilli((Long)fechaActualizacion);
					this.fechaActualizacion = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
				} else {
					this.fechaActualizacion = null;
				}
			} 
		} catch (Exception e) {
			this.fechaActualizacion = null;
		}
	}
}
