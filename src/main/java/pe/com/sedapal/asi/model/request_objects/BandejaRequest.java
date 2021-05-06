package pe.com.sedapal.asi.model.request_objects;

import java.util.Date;

public class BandejaRequest {
	private Long codigoTrabajador;
	private Long categoriaRequerimiento;
	private String fechaSolicitudDesde;
	private String fechaSolicitudHasta;
	private Long idSolicitud;
	private Long idDetalleSolicitud;
		
	public Long getCodigoTrabajador() {
		return codigoTrabajador;
	}
	public void setCodigoTrabajador(Long codigoTrabajador) {
		this.codigoTrabajador = codigoTrabajador;
	}
	public Long getCategoriaRequerimiento() {
		return categoriaRequerimiento;
	}
	public void setCategoriaRequerimiento(Long categoriaRequerimiento) {
		this.categoriaRequerimiento = categoriaRequerimiento;
	}
	public String getFechaSolicitudDesde() {
		return fechaSolicitudDesde;
	}
	public void setFechaSolicitudDesde(String fechaSolicitudDesde) {
		this.fechaSolicitudDesde = fechaSolicitudDesde;
	}
	public String getFechaSolicitudHasta() {
		return fechaSolicitudHasta;
	}
	public void setFechaSolicitudHasta(String fechaSolicitudHasta) {
		this.fechaSolicitudHasta = fechaSolicitudHasta;
	}
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public Long getIdDetalleSolicitud() {
		return idDetalleSolicitud;
	}
	public void setIdDetalleSolicitud(Long idDetalleSolicitud) {
		this.idDetalleSolicitud = idDetalleSolicitud;
	}
		
}
