package pe.com.sedapal.asi.model;

public class ConfiguracionEquipo {
	private Long equipo;
	private String tipoOrden;
	private Long estado;

	public Long getEquipo() {
		return equipo;
	}

	public void setEquipo(Long equipo) {
		this.equipo = equipo;
	}

	public String getTipoOrden() {
		return tipoOrden;
	}

	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}
}
