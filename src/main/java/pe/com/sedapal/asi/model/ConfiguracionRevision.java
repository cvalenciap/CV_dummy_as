package pe.com.sedapal.asi.model;

import java.io.Serializable;

public class ConfiguracionRevision implements Serializable {
	private static final long serialVersionUID = -8504140214665531385L;

	private Long idConfRev;
	private Long equipo;
	private Long idTrabConf;
	private Integer ordenConf;
	private Integer estado;

	public Long getIdConfRev() {
		return idConfRev;
	}

	public void setIdConfRev(Long idConfRev) {
		this.idConfRev = idConfRev;
	}

	public Long getEquipo() {
		return equipo;
	}

	public void setEquipo(Long equipo) {
		this.equipo = equipo;
	}

	public Long getIdTrabConf() {
		return idTrabConf;
	}

	public void setIdTrabConf(Long idTrabConf) {
		this.idTrabConf = idTrabConf;
	}

	public Integer getOrdenConf() {
		return ordenConf;
	}

	public void setOrdenConf(Integer ordenConf) {
		this.ordenConf = ordenConf;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}
