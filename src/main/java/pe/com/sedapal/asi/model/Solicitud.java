package pe.com.sedapal.asi.model;

import java.sql.Date;

public class Solicitud {
	private Long n_id_soli;
	private Long n_idtrabsoli;
	private Date d_fecha_soli;
	private Long n_ind_alta;
	private AdjuntoMensaje n_idarchadju;
	private Long n_estado;
	private String a_v_usucre;
	private Date a_d_feccre;
	private String a_v_usumod;
	private Date a_d_fecmod;
	private String a_v_nomprg;
	
	public Long getN_id_soli() {
		return n_id_soli;
	}

	public void setN_id_soli(Long n_id_soli) {
		this.n_id_soli = n_id_soli;
	}

	public Long getN_idtrabsoli() {
		return n_idtrabsoli;
	}

	public void setN_idtrabsoli(Long n_idtrabsoli) {
		this.n_idtrabsoli = n_idtrabsoli;
	}

	public Date getD_fecha_soli() {
		return d_fecha_soli;
	}

	public void setD_fecha_soli(Date d_fecha_soli) {
		this.d_fecha_soli = d_fecha_soli;
	}

	public Long getN_ind_alta() {
		return n_ind_alta;
	}

	public void setN_ind_alta(Long n_ind_alta) {
		this.n_ind_alta = n_ind_alta;
	}

	public AdjuntoMensaje getN_idarchadju() {
		return n_idarchadju;
	}

	public void setN_idarchadju(AdjuntoMensaje n_idarchadju) {
		this.n_idarchadju = n_idarchadju;
	}

	public Long getN_estado() {
		return n_estado;
	}

	public void setN_estado(Long n_estado) {
		this.n_estado = n_estado;
	}

	public String getA_v_usucre() {
		return a_v_usucre;
	}

	public void setA_v_usucre(String a_v_usucre) {
		this.a_v_usucre = a_v_usucre;
	}

	public Date getA_d_feccre() {
		return a_d_feccre;
	}

	public void setA_d_feccre(Date a_d_feccre) {
		this.a_d_feccre = a_d_feccre;
	}

	public String getA_v_usumod() {
		return a_v_usumod;
	}

	public void setA_v_usumod(String a_v_usumod) {
		this.a_v_usumod = a_v_usumod;
	}

	public Date getA_d_fecmod() {
		return a_d_fecmod;
	}

	public void setA_d_fecmod(Date a_d_fecmod) {
		this.a_d_fecmod = a_d_fecmod;
	}

	public String getA_v_nomprg() {
		return a_v_nomprg;
	}

	public void setA_v_nomprg(String a_v_nomprg) {
		this.a_v_nomprg = a_v_nomprg;
	}
}
