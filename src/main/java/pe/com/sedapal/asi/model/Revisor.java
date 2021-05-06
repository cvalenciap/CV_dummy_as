package pe.com.sedapal.asi.model;

import java.io.Serializable;

public class Revisor implements Serializable {

	private static final long serialVersionUID = -2119106253888912803L;
	
	private Long ncodtrabajador;
	private Long nficha;
	private String nombres;
	private String apPaterno;
	private String apMaterno;
	private String nombreCompleto;
	private String cargo;
	private String correo;
	
	private Long codArea;
	private String nombreArea;
	private String abrevArea;
	
	private Long codAreaSuperior;
	private String nombreAreaSuperior;
	private String abrevAreaSuperior;
	
	private String tipoorden;
	private Long orden;
	
	public Long getNcodtrabajador() {
		return ncodtrabajador;
	}

	public void setNcodtrabajador(Long ncodtrabajador) {
		this.ncodtrabajador = ncodtrabajador;
	}

	public Long getNficha() {
		return nficha;
	}

	public void setNficha(Long nficha) {
		this.nficha = nficha;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Long getCodArea() {
		return codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public String getAbrevArea() {
		return abrevArea;
	}

	public void setAbrevArea(String abrevArea) {
		this.abrevArea = abrevArea;
	}

	public Long getCodAreaSuperior() {
		return codAreaSuperior;
	}

	public void setCodAreaSuperior(Long codAreaSuperior) {
		this.codAreaSuperior = codAreaSuperior;
	}

	public String getNombreAreaSuperior() {
		return nombreAreaSuperior;
	}

	public void setNombreAreaSuperior(String nombreAreaSuperior) {
		this.nombreAreaSuperior = nombreAreaSuperior;
	}

	public String getAbrevAreaSuperior() {
		return abrevAreaSuperior;
	}

	public void setAbrevAreaSuperior(String abrevAreaSuperior) {
		this.abrevAreaSuperior = abrevAreaSuperior;
	}

	public String getTipoorden() {
		return tipoorden;
	}

	public void setTipoorden(String tipoorden) {
		this.tipoorden = tipoorden;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}
	
	@Override
	public boolean equals(Object o) {
		// If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Revisor)) { 
            return false; 
        }           
        // typecast o to Complex so that we can compare data members  
        Revisor c = (Revisor) o; 
          
        // Compare the data members and return accordingly  
        return (ncodtrabajador != null && ncodtrabajador.equals(c.getNcodtrabajador()));
	}
	
	@Override
	public int hashCode() {
		int hashCode = 7;
		hashCode = hashCode + ncodtrabajador.hashCode();
		
		return hashCode;
	}
}
