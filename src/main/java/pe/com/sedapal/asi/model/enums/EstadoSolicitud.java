package pe.com.sedapal.asi.model.enums;

public enum EstadoSolicitud {
	EN_APROBACION("12"),
	APROBADA("10"),
	DESAPROBADA("11"),
	ANULADA("9"),
	INGRESADO("5");
	
	private final String text;
	
	EstadoSolicitud(final String text) {
	    this.text = text;
	}
	
	@Override
	public String toString() {
	    return text;
	}
	
	static public EstadoSolicitud setEstadoSolicitud(String valor) {    	
		EstadoSolicitud result = EN_APROBACION;
		switch (valor.trim()) {    		
	    	case "12":
	    		result = EN_APROBACION;
	    		break;
	    	case "10":
	    		result = APROBADA;
	    		break;
	    	case "11":
	    		result = DESAPROBADA;
	    		break;
	    	case "9":
	    		result = ANULADA;
	    		break;
	    	case "5":
	    		result = INGRESADO;
	    		break;
		}
	return result;
	}
}
