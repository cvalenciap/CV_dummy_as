package pe.com.sedapal.asi.model;

import pe.com.sedapal.asi.model.enums.Estado;

public class DetalleCategoria {
	private Long idcategoria;
    private String nomcategoria;
    private Estado estadocategoria;
    private Long idTipoCategoria;
    private String valorCategoria;
    private String valorCategoria2;       

	public DetalleCategoria() {
    	super();
    }    

	public Long getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(Long idcategoria) {
		this.idcategoria = idcategoria;
	}

	public String getNomcategoria() {
		return nomcategoria;
	}

	public void setNomcategoria(String nomcategoria) {
		this.nomcategoria = nomcategoria;
	}

	public Estado getEstadocategoria() {
		return estadocategoria;
	}

	public void setEstadocategoria(Estado estadocategoria) {
		this.estadocategoria = estadocategoria;
	}
	
	public Long getIdTipoCategoria() {
		return idTipoCategoria;
	}

	public void setIdTipoCategoria(Long idTipoCategoria) {
		this.idTipoCategoria = idTipoCategoria;
	}
	
	public String getValorCategoria() {
		return valorCategoria;
	}

	public void setValorCategoria(String valorCategoria) {
		this.valorCategoria = valorCategoria;
	}
	
	public String getValorCategoria2() {
		return valorCategoria2;
	}

	public void setValorCategoria2(String valorCategoria2) {
		this.valorCategoria2 = valorCategoria2;
	}
}
