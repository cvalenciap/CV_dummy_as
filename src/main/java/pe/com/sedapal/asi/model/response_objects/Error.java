package pe.com.sedapal.asi.model.response_objects;

public class Error {
	
	private Integer codigo;
	private Nivel nivel;
	private String mensaje;
	private String mensajeInterno;
	
	public Error() {}
	public Error(Integer codigo, String mensaje, String mensajeInterno) {
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.mensajeInterno = mensajeInterno;
		this.nivel = Nivel.UNEXPECTED;
	}
	public Error(Integer codigo, String mensaje, Nivel nivel) {
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.mensajeInterno = "";
		this.nivel = nivel;
	}
	public Error(Integer codigo, String mensaje, String mensajeInterno, Nivel nivel) {
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.mensajeInterno = mensajeInterno;
		this.nivel = nivel;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getMensajeInterno() {
		return mensajeInterno;
	}
	public void setMensajeInterno(String mensajeInterno) {
		this.mensajeInterno = mensajeInterno;
	}
	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

}
