package pe.com.sedapal.asi.model.request_objects;

public class AreaRequest {
	private String descripcion;
	private Long codigo;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
}
