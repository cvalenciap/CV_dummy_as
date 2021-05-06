package pe.com.sedapal.asi.model;

import java.io.Serializable;
import java.util.List;

import pe.com.sedapal.seguridad.core.bean.SistemaModuloOpcionBean;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 8320778498372937044L;
	private Long codTrabajador;
	private String codUsuario;
	private String nombUsuario;
	private Long codArea;
	private String descArea;
	private Long codAreaSuperior;
	private String descAreaSuperior;
	private Long codFicha;
	private Integer codPerfil;
	private String descPerfil;
	private String abrevArea;
	private String abrevAreaSuperior;
	private List<SistemaModuloOpcionBean> permisos;
	private String telefono;
	private String mail;
	private Integer esJefe;
	private Integer esGerente;
	private Integer esGerenteGral;
	private Integer esSecretariaGerGral;
	private Integer esSecretariaGerencias;
	private Integer esSecretariaEquipos;
	private String descSede;
	private String nomSede;
	private String vcargo;
	private String vnomcargo;
	private String msg_info_aprobadores;
	private Long esAprobador;
	private List<Perfil> perfiles;
	private Integer uidSistema;
	private String descripcionSistema;
	private List<Modulo> modulos;

	public String getMsg_info_aprobadores() {
		return msg_info_aprobadores;
	}

	public void setMsg_info_aprobadores(String msg_info_aprobadores) {
		this.msg_info_aprobadores = msg_info_aprobadores;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getNombUsuario() {
		return nombUsuario;
	}

	public void setNombUsuario(String nombUsuario) {
		this.nombUsuario = nombUsuario;
	}

	public Long getEsAprobador() {
		return esAprobador;
	}

	public void setEsAprobador(Long esAprobador) {
		this.esAprobador = esAprobador;
	}

	public Long getCodArea() {
		return codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public String getDescArea() {
		return descArea;
	}

	public void setDescArea(String descArea) {
		this.descArea = descArea;
	}

	public Long getCodFicha() {
		return codFicha;
	}

	public void setCodFicha(Long codFicha) {
		this.codFicha = codFicha;
	}

	public Integer getCodPerfil() {
		return codPerfil;
	}

	public void setCodPerfil(Integer codPerfil) {
		this.codPerfil = codPerfil;
	}

	public String getDescPerfil() {
		return descPerfil;
	}

	public void setDescPerfil(String descPerfil) {
		this.descPerfil = descPerfil;
	}

	public String getAbrevArea() {
		return abrevArea;
	}

	public void setAbrevArea(String abrevArea) {
		this.abrevArea = abrevArea;
	}

	public List<SistemaModuloOpcionBean> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<SistemaModuloOpcionBean> permisos) {
		this.permisos = permisos;
	}

	public Long getCodAreaSuperior() {
		return codAreaSuperior;
	}

	public void setCodAreaSuperior(Long codAreaSuperior) {
		this.codAreaSuperior = codAreaSuperior;
	}

	public String getDescAreaSuperior() {
		return descAreaSuperior;
	}

	public void setDescAreaSuperior(String descAreaSuperior) {
		this.descAreaSuperior = descAreaSuperior;
	}

	public String getAbrevAreaSuperior() {
		return abrevAreaSuperior;
	}

	public void setAbrevAreaSuperior(String abrevAreaSuperior) {
		this.abrevAreaSuperior = abrevAreaSuperior;
	}

	public Long getCodTrabajador() {
		return codTrabajador;
	}

	public void setCodTrabajador(Long codTrabajador) {
		this.codTrabajador = codTrabajador;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getEsJefe() {
		return esJefe;
	}

	public void setEsJefe(Integer esJefe) {
		this.esJefe = esJefe;
	}

	public Integer getEsGerente() {
		return esGerente;
	}

	public void setEsGerente(Integer esGerente) {
		this.esGerente = esGerente;
	}

	public Integer getEsGerenteGral() {
		return esGerenteGral;
	}

	public void setEsGerenteGral(Integer esGerenteGral) {
		this.esGerenteGral = esGerenteGral;
	}

	public String getDescSede() {
		return descSede;
	}

	public void setDescSede(String descSede) {
		this.descSede = descSede;
	}

	public String getNomSede() {
		return nomSede;
	}

	public void setNomSede(String nomSede) {
		this.nomSede = nomSede;
	}

	public String getVcargo() {
		return vcargo;
	}

	public void setVcargo(String vcargo) {
		this.vcargo = vcargo;
	}

	public String getVnomcargo() {
		return vnomcargo;
	}

	public void setVnomcargo(String vnomcargo) {
		this.vnomcargo = vnomcargo;
	}

	public Integer getEsSecretariaGerGral() {
		return esSecretariaGerGral;
	}

	public void setEsSecretariaGerGral(Integer esSecretariaGerGral) {
		this.esSecretariaGerGral = esSecretariaGerGral;
	}

	public Integer getEsSecretariaGerencias() {
		return esSecretariaGerencias;
	}

	public void setEsSecretariaGerencias(Integer esSecretariaGerencias) {
		this.esSecretariaGerencias = esSecretariaGerencias;
	}

	public Integer getEsSecretariaEquipos() {
		return esSecretariaEquipos;
	}

	public void setEsSecretariaEquipos(Integer esSecretariaEquipos) {
		this.esSecretariaEquipos = esSecretariaEquipos;
	}

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	public Integer getUidSistema() {
		return uidSistema;
	}

	public void setUidSistema(Integer uidSistema) {
		this.uidSistema = uidSistema;
	}

	public String getDescripcionSistema() {
		return descripcionSistema;
	}

	public void setDescripcionSistema(String descripcionSistema) {
		this.descripcionSistema = descripcionSistema;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

}