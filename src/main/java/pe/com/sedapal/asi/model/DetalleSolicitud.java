package pe.com.sedapal.asi.model;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

public class DetalleSolicitud {
	private Long n_det_soli;  
	private Long n_id_soli;
	private Categoria n_id_cat_par;
	private UsuarioAfectado n_idtrabafec;
	private DetalleCategoria n_aplic_par;
	private String v_perfil_tran;
	private AccionDetalleCategoria n_accion_par;
	private Date d_fec_finvig;
	private DetalleCategoria n_rectic_par;
	private String v_cuenta_red;
	private String v_cod_inven;
	private DetalleCategoria n_ambcam_par;
	private String v_cta_orig;
	private String v_cta_dest;
	private Long n_ti_pun_par;
	private String v_sustento;
	private String v_ubic_fis;
	private String v_tele_afec;
	private Long n_modequ_par;
	private Long n_motsol_par;
	private String v_anex_ofic;
	private Long n_id_cont;
	private String v_pers_reas;
	private String v_desc_obse;
	private Date d_fe_usu_est;
	private Long n_estado_par;
	private Long n_idtrab_est;
	private AdjuntoMensaje n_idarchadju;
	private String v_id_ticket;
	private String v_id_envio;
	private Long n_estado;
	private String a_v_usucre;
	private Date a_d_feccre;
	private String a_v_usumod;
	private Date a_d_fecmod;
	private String a_v_nomprg;
	private DetalleCategoria categoriaRequerimiento;
	private UsuarioAfectado usuarioSolicitante;
	private String fechaSolicitud;
	private Area gerencia;
	private Area equipo;
	private Long tiempoEspera;
	private String indUltRev;
	private DetalleCategoria n_equ_par;
	private String v_desc_tras;
	private Sedes n_sedeor_par;
	private Area n_areaor_par;
	private String v_ubicor_fis;
	private Sedes n_sedede_par;
	private Area n_areade_par;
	private String v_ubicde_fis;
	private Long n_ficha_des;
	private String v_nombre_des;
	private Situacion v_situsu_des;
	private Long indAprobacion;
	private DetalleCategoria n_requ_par;
	private Date d_fe_inipres;
	private Date d_fe_finpres;
	
	public Long getN_det_soli() {
		return n_det_soli;
	}

	public void setN_det_soli(Long n_det_soli) {
		this.n_det_soli = n_det_soli;
	}

	public Long getN_id_soli() {
		return n_id_soli;
	}

	public void setN_id_soli(Long n_id_soli) {
		this.n_id_soli = n_id_soli;
	}

	public Categoria getN_id_cat_par() {
		return n_id_cat_par;
	}

	public void setN_id_cat_par(Categoria n_id_cat_par) {
		this.n_id_cat_par = n_id_cat_par;
	}

	public UsuarioAfectado getN_idtrabafec() {
		return n_idtrabafec;
	}

	public void setN_idtrabafec(UsuarioAfectado n_idtrabafec) {
		this.n_idtrabafec = n_idtrabafec;
	}

	public DetalleCategoria getN_aplic_par() {
		return n_aplic_par;
	}

	public void setN_aplic_par(DetalleCategoria n_aplic_par) {
		this.n_aplic_par = n_aplic_par;
	}

	public String getV_perfil_tran() {
		return v_perfil_tran;
	}

	public void setV_perfil_tran(String v_perfil_tran) {
		this.v_perfil_tran = v_perfil_tran;
	}

	public AccionDetalleCategoria getN_accion_par() {
		return n_accion_par;
	}

	public void setN_accion_par(AccionDetalleCategoria n_accion_par) {
		this.n_accion_par = n_accion_par;
	}

	public Date getD_fec_finvig() {
		return d_fec_finvig;
	}

	public void setD_fec_finvig(Date d_fec_finvig) {
		this.d_fec_finvig = d_fec_finvig;
	}

	public DetalleCategoria getN_rectic_par() {
		return n_rectic_par;
	}

	public void setN_rectic_par(DetalleCategoria n_rectic_par) {
		this.n_rectic_par = n_rectic_par;
	}

	public String getV_cuenta_red() {
		return v_cuenta_red;
	}

	public void setV_cuenta_red(String v_cuenta_red) {
		this.v_cuenta_red = v_cuenta_red;
	}

	public String getV_cod_inven() {
		return v_cod_inven;
	}

	public void setV_cod_inven(String v_cod_inven) {
		this.v_cod_inven = v_cod_inven;
	}

	public DetalleCategoria getN_ambcam_par() {
		return n_ambcam_par;
	}

	public void setN_ambcam_par(DetalleCategoria n_ambcam_par) {
		this.n_ambcam_par = n_ambcam_par;
	}

	public String getV_cta_orig() {
		return v_cta_orig;
	}

	public void setV_cta_orig(String v_cta_orig) {
		this.v_cta_orig = v_cta_orig;
	}

	public String getV_cta_dest() {
		return v_cta_dest;
	}

	public void setV_cta_dest(String v_cta_dest) {
		this.v_cta_dest = v_cta_dest;
	}

	public Long getN_ti_pun_par() {
		return n_ti_pun_par;
	}

	public void setN_ti_pun_par(Long n_ti_pun_par) {
		this.n_ti_pun_par = n_ti_pun_par;
	}

	public String getV_sustento() {
		return v_sustento;
	}

	public void setV_sustento(String v_sustento) {
		this.v_sustento = v_sustento;
	}

	public String getV_ubic_fis() {
		return v_ubic_fis;
	}

	public void setV_ubic_fis(String v_ubic_fis) {
		this.v_ubic_fis = v_ubic_fis;
	}

	public String getV_tele_afec() {
		return v_tele_afec;
	}

	public void setV_tele_afec(String v_tele_afec) {
		this.v_tele_afec = v_tele_afec;
	}

	public Long getN_modequ_par() {
		return n_modequ_par;
	}

	public void setN_modequ_par(Long n_modequ_par) {
		this.n_modequ_par = n_modequ_par;
	}

	public Long getN_motsol_par() {
		return n_motsol_par;
	}

	public void setN_motsol_par(Long n_motsol_par) {
		this.n_motsol_par = n_motsol_par;
	}

	public String getV_anex_ofic() {
		return v_anex_ofic;
	}

	public void setV_anex_ofic(String v_anex_ofic) {
		this.v_anex_ofic = v_anex_ofic;
	}

	public Long getN_id_cont() {
		return n_id_cont;
	}

	public void setN_id_cont(Long n_id_cont) {
		this.n_id_cont = n_id_cont;
	}

	public String getV_pers_reas() {
		return v_pers_reas;
	}

	public void setV_pers_reas(String v_pers_reas) {
		this.v_pers_reas = v_pers_reas;
	}

	public String getV_desc_obse() {
		return v_desc_obse;
	}

	public void setV_desc_obse(String v_desc_obse) {
		this.v_desc_obse = v_desc_obse;
	}

	public Date getD_fe_usu_est() {
		return d_fe_usu_est;
	}

	public void setD_fe_usu_est(Date d_fe_usu_est) {
		this.d_fe_usu_est = d_fe_usu_est;
	}

	public Long getN_estado_par() {
		return n_estado_par;
	}

	public void setN_estado_par(Long n_estado_par) {
		this.n_estado_par = n_estado_par;
	}

	public Long getN_idtrab_est() {
		return n_idtrab_est;
	}

	public void setN_idtrab_est(Long n_idtrab_est) {
		this.n_idtrab_est = n_idtrab_est;
	}

	public AdjuntoMensaje getN_idarchadju() {
		return n_idarchadju;
	}

	public void setN_idarchadju(AdjuntoMensaje n_idarchadju) {
		this.n_idarchadju = n_idarchadju;
	}

	public String getV_id_ticket() {
		return v_id_ticket;
	}

	public void setV_id_ticket(String v_id_ticket) {
		this.v_id_ticket = v_id_ticket;
	}

	public String getV_id_envio() {
		return v_id_envio;
	}

	public void setV_id_envio(String v_id_envio) {
		this.v_id_envio = v_id_envio;
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
	
	public DetalleCategoria getCategoriaRequerimiento() {
		return categoriaRequerimiento;
	}

	public void setCategoriaRequerimiento(DetalleCategoria categoriaRequerimiento) {
		this.categoriaRequerimiento = categoriaRequerimiento;
	}
	public UsuarioAfectado getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(UsuarioAfectado usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}
	
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	public Area getGerencia() {
		return gerencia;
	}

	public void setGerencia(Area gerencia) {
		this.gerencia = gerencia;
	}

	public Area getEquipo() {
		return equipo;
	}

	public void setEquipo(Area equipo) {
		this.equipo = equipo;
	}
	
	public Long getTiempoEspera() {
		return tiempoEspera;
	}

	public void setTiempoEspera(Long tiempoEspera) {
		this.tiempoEspera = tiempoEspera;
	}
	
	public String getIndUltRev() {
		return indUltRev;
	}

	public void setIndUltRev(String indUltRev) {
		this.indUltRev = indUltRev;
	}

	public DetalleCategoria getN_equ_par() {
		return n_equ_par;
	}

	public void setN_equ_par(DetalleCategoria n_equ_par) {
		this.n_equ_par = n_equ_par;
	}

	public String getV_desc_tras() {
		return v_desc_tras;
	}

	public void setV_desc_tras(String v_desc_tras) {
		this.v_desc_tras = v_desc_tras;
	}

	public Sedes getN_sedeor_par() {
		return n_sedeor_par;
	}

	public void setN_sedeor_par(Sedes n_sedeor_par) {
		this.n_sedeor_par = n_sedeor_par;
	}

	public Area getN_areaor_par() {
		return n_areaor_par;
	}

	public void setN_areaor_par(Area n_areaor_par) {
		this.n_areaor_par = n_areaor_par;
	}

	public String getV_ubicor_fis() {
		return v_ubicor_fis;
	}

	public void setV_ubicor_fis(String v_ubicor_fis) {
		this.v_ubicor_fis = v_ubicor_fis;
	}

	public Sedes getN_sedede_par() {
		return n_sedede_par;
	}

	public void setN_sedede_par(Sedes n_sedede_par) {
		this.n_sedede_par = n_sedede_par;
	}

	public Area getN_areade_par() {
		return n_areade_par;
	}

	public void setN_areade_par(Area n_areade_par) {
		this.n_areade_par = n_areade_par;
	}

	public String getV_ubicde_fis() {
		return v_ubicde_fis;
	}

	public void setV_ubicde_fis(String v_ubicde_fis) {
		this.v_ubicde_fis = v_ubicde_fis;
	}

	public Long getN_ficha_des() {
		return n_ficha_des;
	}

	public void setN_ficha_des(Long n_ficha_des) {
		this.n_ficha_des = n_ficha_des;
	}

	public String getV_nombre_des() {
		return v_nombre_des;
	}

	public void setV_nombre_des(String v_nombre_des) {
		this.v_nombre_des = v_nombre_des;
	}

	public Situacion getV_situsu_des() {
		return v_situsu_des;
	}

	public void setV_situsu_des(Situacion v_situsu_des) {
		this.v_situsu_des = v_situsu_des;
	}
	
	public Long getIndAprobacion() {
		return indAprobacion;
	}

	public void setIndAprobacion(Long indAprobacion) {
		this.indAprobacion = indAprobacion;
	}
	
	public DetalleCategoria getN_requ_par() {
		return n_requ_par;
	}

	public void setN_requ_par(DetalleCategoria n_requ_par) {
		this.n_requ_par = n_requ_par;
	}

	public Date getD_fe_inipres() {
		return d_fe_inipres;
	}

	public void setD_fe_inipres(Date d_fe_inipres) {
		this.d_fe_inipres = d_fe_inipres;
	}

	public Date getD_fe_finpres() {
		return d_fe_finpres;
	}

	public void setD_fe_finpres(Date d_fe_finpres) {
		this.d_fe_finpres = d_fe_finpres;
	}
}
