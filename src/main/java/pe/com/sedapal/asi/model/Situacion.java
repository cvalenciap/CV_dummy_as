package pe.com.sedapal.asi.model;

public class Situacion {
	private String vcodtipo;
	private String vdescripcion;
	
	public Situacion() {
		super();
	}
	
	public Situacion (String vcodtipo, String vdescripcion) {
		this.vcodtipo = vcodtipo;
		this.vdescripcion = vdescripcion;
	}
	public String getVcodtipo() {
		return vcodtipo;
	}

	public void setVcodtipo(String vcodtipo) {
		this.vcodtipo = vcodtipo;
	}

	public String getVdescripcion() {
		return vdescripcion;
	}

	public void setVdescripcion(String vdescripcion) {
		this.vdescripcion = vdescripcion;
	}
}
