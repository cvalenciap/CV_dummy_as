package pe.com.sedapal.asi.model;

public class DetalleRequerimiento extends DetalleSolicitud{
		
	String aprobadores;
	String fechafinVigencia;
	String correoSiguienteRevisor;
	Revisor revisor;
	String tipoOrdenAprobacion;		

	public String getAprobadores() {
		return aprobadores;
	}

	public void setAprobadores(String aprobadores) {
		this.aprobadores = aprobadores;
	}

	public String getFechafinVigencia() {
		return fechafinVigencia;
	}

	public void setFechafinVigencia(String fechafinVigencia) {
		this.fechafinVigencia = fechafinVigencia;
	}

	public Revisor getRevisor() {
		return revisor;
	}

	public void setRevisor(Revisor revisor) {
		this.revisor = revisor;
	}

	public String getTipoOrdenAprobacion() {
		return tipoOrdenAprobacion;
	}

	public void setTipoOrdenAprobacion(String tipoOrdenAprobacion) {
		this.tipoOrdenAprobacion = tipoOrdenAprobacion;
	}

	public String getCorreoSiguienteRevisor() {
		return correoSiguienteRevisor;
	}

	public void setCorreoSiguienteRevisor(String correoSiguienteRevisor) {
		this.correoSiguienteRevisor = correoSiguienteRevisor;
	}		
	
}
