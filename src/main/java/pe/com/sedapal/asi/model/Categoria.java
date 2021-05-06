package pe.com.sedapal.asi.model;

import pe.com.sedapal.asi.model.enums.Estado;

public class Categoria {
	private Long idcategoria;
    private String nomcategoria;
    private Estado estadocategoria;
    private Long idTipoCategoria;
    private String valorCategoria;    
   
    public Categoria() {
    	super();
    }
    
    public Categoria(Long idcategoria, String nomcategoria, Estado estadocategoria, Long idTipoCategoria, String valorCategoria) {
        this.idcategoria = idcategoria;
        this.nomcategoria = nomcategoria;
        this.estadocategoria = estadocategoria;
        this.valorCategoria = valorCategoria;
        this.idTipoCategoria = idTipoCategoria;
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
}
