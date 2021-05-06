package pe.com.sedapal.asi.model;

import pe.com.sedapal.asi.model.enums.Estado;

public class AccionDetalleCategoria {
	private Long idcategoria;
    private Long idaccion;
    private String nombreaccion;
    private Estado estadoaccion;
    
    public AccionDetalleCategoria() {
    	super();
    }
    
    public AccionDetalleCategoria(Long idcategoria, Long idaccion, String nombreaccion, Estado estadoaccion) {
    	super();
    	this.idcategoria = idcategoria;
    	this.idaccion = idaccion;
    	this.nombreaccion = nombreaccion;
    	this.estadoaccion = estadoaccion;
    }
    public Long getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(Long idcategoria) {
		this.idcategoria = idcategoria;
	}

	public Long getIdaccion() {
		return idaccion;
	}

	public void setIdaccion(Long idaccion) {
		this.idaccion = idaccion;
	}

	public String getNombreaccion() {
		return nombreaccion;
	}

	public void setNombreaccion(String nombreaccion) {
		this.nombreaccion = nombreaccion;
	}

	public Estado getEstadoaccion() {
		return estadoaccion;
	}

	public void setEstadoaccion(Estado estadoaccion) {
		this.estadoaccion = estadoaccion;
	}
}
