package pe.com.sedapal.asi.model.response_objects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

public class ResponseObject {
	
	private Estado estado;
	@JsonInclude(Include.NON_NULL)
	private Paginacion paginacion;
	@JsonInclude(Include.NON_NULL)
	private pe.com.sedapal.asi.model.response_objects.Error error;
	@JsonInclude(Include.NON_NULL)
	private Object resultado;
	@JsonInclude(Include.NON_NULL)
	private List<String> acciones;
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Paginacion getPaginacion() {
		return paginacion;
	}
	public void setPaginacion(Paginacion paginacion) {
		this.paginacion = paginacion;
	}
	public pe.com.sedapal.asi.model.response_objects.Error getError() {
		return error;
	}
	public void setError(pe.com.sedapal.asi.model.response_objects.Error error) {
		this.error = error;
	}
	public void setError(Integer codigo, String mensaje, String mensajeInterno) {
		this.error = new pe.com.sedapal.asi.model.response_objects.Error(codigo, mensaje, mensajeInterno);
	}
	public Object getResultado() {
		return resultado;
	}
	public void setResultado(Object resultado) {
		this.resultado = resultado;
	}
	public List<String> getAcciones() {
		return acciones;
	}
	public void setAcciones(List<String> acciones) {
		this.acciones = acciones;
	}
}
