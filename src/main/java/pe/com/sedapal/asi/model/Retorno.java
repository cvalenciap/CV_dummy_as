package pe.com.sedapal.asi.model;

import java.io.Serializable;

public class Retorno implements Serializable{
	private Integer sid;	
	public Retorno() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getSid() {
		return sid;
	}


	public void setSid(Integer sid) {
		this.sid = sid;
	}


	public Retorno(Integer sid) {
		super();
		this.sid = sid;
	}

	
	@Override
	public String toString() {
		return "Retorno [sid=" + sid.toString() + "]";
	}

}
