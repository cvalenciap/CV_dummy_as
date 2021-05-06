package pe.com.sedapal.asi.model;

public class Sedes {
	private Long idSede;
	private String descSede;
	
	public Sedes() {
		super();
	}
	
	public Sedes (Long idSede, String descSede) {
		this.idSede = idSede;
		this.descSede = descSede;
	}

	public Long getIdSede() {
		return idSede;
	}

	public void setIdSede(Long idSede) {
		this.idSede = idSede;
	}

	public String getDescSede() {
		return descSede;
	}

	public void setDescSede(String descSede) {
		this.descSede = descSede;
	}
}
