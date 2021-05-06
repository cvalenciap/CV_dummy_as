package pe.com.sedapal.asi.model.response_objects;

import pe.com.sedapal.asi.model.request_objects.TicketContratistaRequest;

public class TicketContratistaResponse {
	private String estado;
	private String error;
	private TicketContratistaRequest resultado;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public TicketContratistaRequest getResultado() {
		return resultado;
	}

	public void setResultado(TicketContratistaRequest resultado) {
		this.resultado = resultado;
	}
}
