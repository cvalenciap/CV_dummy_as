package pe.com.sedapal.asi.model;

import java.sql.Date;

public class Contacto {
    
	private Long n_id_cont;
	private Long n_cod_area;
	private Long naresuperior;
	private String v_nomb_cont;
	private String v_tele_cont;
	private String gerencia;
	private String equipo;
	private Long n_estado;
	private String a_v_usucre;
	private Date a_d_feccre;
	private String a_v_usumod;
	private Date a_d_fecmod;
	private String a_v_nomprg;
	
	public Long getN_id_cont() {
		return n_id_cont;
	}
	public void setN_id_cont(Long n_id_cont) {
		this.n_id_cont = n_id_cont;
	}
	public Long getN_cod_area() {
		return n_cod_area;
	}
	public void setN_cod_area(Long n_cod_area) {
		this.n_cod_area = n_cod_area;
	}
	public String getV_nomb_cont() {
		return v_nomb_cont;
	}
	public void setV_nomb_cont(String v_nomb_cont) {
		this.v_nomb_cont = v_nomb_cont;
	}
	public String getV_tele_cont() {
		return v_tele_cont;
	}
	public void setV_tele_cont(String v_tele_cont) {
		this.v_tele_cont = v_tele_cont;
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
	public Long getNaresuperior() {
		return naresuperior;
	}
	public void setNaresuperior(Long naresuperior) {
		this.naresuperior = naresuperior;
	}
	public String getGerencia() {
		return gerencia;
	}
	public void setGerencia(String gerencia) {
		this.gerencia = gerencia;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
}
