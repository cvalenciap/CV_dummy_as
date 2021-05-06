package pe.com.sedapal.asi.model;

public class UsuarioAfectado {
	private Long nficha;
	private Long ncodtrabajador;
	private String nomUsuario;
	private String dni;
	private String codSituacion;
	private String descSituacion;
	private Long ncodarea;
	private String equipo;
	private Long ncodareasup;
	private String gerencia;
	private String descEquipo;
	private String descGerencia;
	private String telefono;
	private String correo;	
	private String sede;
	private String codUsuario;
	
	@Override
	public boolean equals(Object o) {
		// If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof UsuarioAfectado)) { 
            return false; 
        }           
        // typecast o to Complex so that we can compare data members  
        UsuarioAfectado c = (UsuarioAfectado) o; 
          
        // Compare the data members and return accordingly  
        return (nficha != null && nficha.equals(c.getNficha()));
	}
	
	@Override
	public int hashCode() {
		int hashCode = 7; // numero primo cualquiera
		hashCode = hashCode + (nficha != null ? nficha.hashCode() : 0);
		
		return hashCode;
	}

	public String getDescEquipo() {
		return descEquipo;
	}

	public void setDescEquipo(String descEquipo) {
		this.descEquipo = descEquipo;
	}

	public String getDescGerencia() {
		return descGerencia;
	}

	public void setDescGerencia(String descGerencia) {
		this.descGerencia = descGerencia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Long getNficha() {
		return nficha;
	}
	
	public void setNficha(Long nficha) {
		this.nficha = nficha;
	}

	public Long getNcodtrabajador() {
		return ncodtrabajador;
	}
	
	public void setNcodtrabajador(Long ncodtrabajador) {
		this.ncodtrabajador = ncodtrabajador;
	}
	
	public String getNomUsuario() {
		return nomUsuario;
	}
	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getCodSituacion() {
		return codSituacion;
	}
	public void setCodSituacion(String codSituacion) {
		this.codSituacion = codSituacion;
	}
	public String getDescSituacion() {
		return descSituacion;
	}
	public void setDescSituacion(String descSituacion) {
		this.descSituacion = descSituacion;
	}
	public Long getNcodarea() {
		return ncodarea;
	}
	public void setNcodarea(Long ncodarea) {
		this.ncodarea = ncodarea;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public Long getNcodareasup() {
		return ncodareasup;
	}
	public void setNcodareasup(Long ncodareasup) {
		this.ncodareasup = ncodareasup;
	}
	public String getGerencia() {
		return gerencia;
	}
	public void setGerencia(String gerencia) {
		this.gerencia = gerencia;
	}
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}
	
	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

}
