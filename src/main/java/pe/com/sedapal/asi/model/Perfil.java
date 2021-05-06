package pe.com.sedapal.asi.model;

import java.io.Serializable;
import java.util.List;

public class Perfil implements Serializable {
	
	private static final long serialVersionUID = 8320778498372937044L;
	private Integer codigo;
	private String descripcion;
	private List<Modulo> modulos;
	private String defaultPath;
	
	public Perfil() {}

	public Perfil(Integer codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public Perfil(Integer codigo, String descripcion, String defaultPath) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.defaultPath = defaultPath;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}
	
	public String getDefaultPath() {
		return defaultPath;
	}

	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}
}
