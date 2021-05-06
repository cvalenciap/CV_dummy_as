package pe.com.sedapal.asi.model;

import java.util.List;

import pe.com.sedapal.asi.model.DetalleSolicitud;
import pe.com.sedapal.asi.model.Solicitud;

public class SolicitudPackage {
	private Solicitud cabecera;
	private List<DetalleSolicitud> detalle;
	
	public SolicitudPackage() {
		super();
	}
	
	public SolicitudPackage(Solicitud cabecera, List<DetalleSolicitud> detalle) {
		this.cabecera = cabecera;
		this.detalle = detalle;
	}
	public Solicitud getCabecera() {
		return cabecera;
	}

	public void setCabecera(Solicitud cabecera) {
		this.cabecera = cabecera;
	}

	public List<DetalleSolicitud> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<DetalleSolicitud> detalle) {
		this.detalle = detalle;
	}	
}
