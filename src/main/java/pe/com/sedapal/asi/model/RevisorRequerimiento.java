package pe.com.sedapal.asi.model;

public class RevisorRequerimiento {
	private Long idReviReq;
	private Long idDetSoli;
	private Long idTrabRevi;
	private Long ordenRevi;
	private String resultadoRevision;
	private Long estado;
	
	public Long getIdReviReq() {
		return idReviReq;
	}

	public void setIdReviReq(Long idReviReq) {
		this.idReviReq = idReviReq;
	}

	public Long getIdDetSoli() {
		return idDetSoli;
	}

	public void setIdDetSoli(Long idDetSoli) {
		this.idDetSoli = idDetSoli;
	}

	public Long getIdTrabRevi() {
		return idTrabRevi;
	}

	public void setIdTrabRevi(Long idTrabRevi) {
		this.idTrabRevi = idTrabRevi;
	}

	public Long getOrdenRevi() {
		return ordenRevi;
	}

	public void setOrdenRevi(Long ordenRevi) {
		this.ordenRevi = ordenRevi;
	}

	public String getResultadoRevision() {
		return resultadoRevision;
	}

	public void setResultadoRevision(String resultadoRevision) {
		this.resultadoRevision = resultadoRevision;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}
}
