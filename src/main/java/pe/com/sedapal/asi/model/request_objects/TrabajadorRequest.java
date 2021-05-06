package pe.com.sedapal.asi.model.request_objects;

public class TrabajadorRequest {
	private long codTrabajador;
	private long numeroFicha;

	public Long getCodTrabajador() {
		return codTrabajador;
	}

	public void setCodTrabajador(Long codTrabajador) {
		this.codTrabajador = codTrabajador;
	}

	public Long getNumeroFicha() {
		return numeroFicha;
	}

	public void setNumeroFicha(Long numeroFicha) {
		this.numeroFicha = numeroFicha;
	}
}
