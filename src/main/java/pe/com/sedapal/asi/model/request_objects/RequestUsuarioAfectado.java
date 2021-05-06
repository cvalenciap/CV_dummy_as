package pe.com.sedapal.asi.model.request_objects;

public class RequestUsuarioAfectado {
	private Long nfichaBusqueda;
	private Long dni;
	private String apPaterno;
	private String apMaterno;
	private String nombres;
	
	public Long getNfichaBusqueda() {
		return nfichaBusqueda;
	}

	public void setNfichaBusqueda(Long nfichaBusqueda) {
		this.nfichaBusqueda = nfichaBusqueda;
	}

	public Long getDni() {
		return dni;
	}

	public void setDni(Long dni) {
		this.dni = dni;
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

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}	
}
