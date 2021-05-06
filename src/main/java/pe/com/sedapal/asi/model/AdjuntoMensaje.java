package pe.com.sedapal.asi.model;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import pe.com.sedapal.asi.util.Base64Util;

public class AdjuntoMensaje {
	private Integer idAdjunto;
	private String nombreAdjunto;
	private String nombreRealAdjunto;
	private String extensionAdjunto;
	private String urlAdjunto;
	private Integer sizeAdjunto;
	private String bytesArray;
	private Long n_estado;
	private String a_v_usucre;
	private String a_v_usumod;

	public String getNombreAdjunto() {
		return nombreAdjunto;
	}

	public void setNombreAdjunto(String NombreAdjunto) {
		this.nombreAdjunto = NombreAdjunto;
	}

	public String getNombreRealAdjunto() {
		return nombreRealAdjunto;
	}

	public void setNombreRealAdjunto(String NombreRealAdjunto) {
		this.nombreRealAdjunto = NombreRealAdjunto;
	}

	public Integer getIdAdjunto() {
		return idAdjunto;
	}

	public void setIdAdjunto(Integer idAdjunto) {
		this.idAdjunto = idAdjunto;
	}

	public String getExtensionAdjunto() {
		return extensionAdjunto;
	}

	public void setExtensionAdjunto(String extensionAdjunto) {
		this.extensionAdjunto = extensionAdjunto;
	}

	public String getUrlAdjunto() {
		return urlAdjunto;
	}

	public void setUrlAdjunto(String urlAdjunto) {
		this.urlAdjunto = urlAdjunto;
	}

	public Integer getSizeAdjunto() {
		return sizeAdjunto;
	}

	public void setSizeAdjunto(Integer sizeAdjunto) {
		this.sizeAdjunto = sizeAdjunto;
	}

	public byte[] getBytesArray() {
		if (bytesArray != null && bytesArray.length() > 0) {
			String arrayDatos[] = bytesArray.split(",");
			String contenido = arrayDatos[1];

			return Base64Util.convertirStringBase64Bytes(contenido);
		} else {
			return null;
		}
	}

	public void setBytesArray(String bytesArray) {
		this.bytesArray = bytesArray;
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

	public String getA_v_usumod() {
		return a_v_usumod;
	}

	public void setA_v_usumod(String a_v_usumod) {
		this.a_v_usumod = a_v_usumod;
	}
}
