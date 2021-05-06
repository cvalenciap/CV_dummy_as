package pe.com.sedapal.asi.model;

public class DetalleParametro {
	private Integer idPara;
	private Integer idDetallePara;
	private String nombreDetallePara;
	private String valorPara1;
	private String valorPara2;
	private String valorPara3;
	private Integer estado;
	private String usuarioCreacion;

	public Integer getIdPara() {
		return idPara;
	}

	public void setIdPara(Integer idPara) {
		this.idPara = idPara;
	}

	public Integer getIdDetallePara() {
		return idDetallePara;
	}

	public void setIdDetallePara(Integer idDetallePara) {
		this.idDetallePara = idDetallePara;
	}

	public String getNombreDetallePara() {
		return nombreDetallePara;
	}

	public void setNombreDetallePara(String nombreDetallePara) {
		this.nombreDetallePara = nombreDetallePara;
	}

	public String getValorPara1() {
		return valorPara1;
	}

	public void setValorPara1(String valorPara1) {
		this.valorPara1 = valorPara1;
	}

	public String getValorPara2() {
		return valorPara2;
	}

	public void setValorPara2(String valorPara2) {
		this.valorPara2 = valorPara2;
	}

	public String getValorPara3() {
		return valorPara3;
	}

	public void setValorPara3(String valorPara3) {
		this.valorPara3 = valorPara3;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

}
