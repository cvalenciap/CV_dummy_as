package pe.com.sedapal.asi.model.enums;

public enum TipoParametro {
	TODOS("001", "", "TODOS"),
    VALOR_UNICO("002", "S", "VALOR ÚNICO"),
    LISTADO_VALORES("003", "M", "LISTADO DE VALORES");
	
	private final String codigo;
	private final String valor;
	private final String texto;
	
	TipoParametro(String codigo, String valor, String texto) {
		this.codigo = codigo;
		this.valor = valor;
		this.texto = texto;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getValor() {
		return valor;
	}
	
	public String getTexto() {
		return texto;
	}
	
}
