package pe.com.sedapal.asi.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AsiUser extends User {
	private static final long serialVersionUID = 1034958750547279637L;
	
	private pe.com.sedapal.seguridad.core.bean.Retorno retorno;

	public AsiUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
	}

	public pe.com.sedapal.seguridad.core.bean.Retorno getRetorno() {
		return retorno;
	}

	public void setRetorno(pe.com.sedapal.seguridad.core.bean.Retorno retorno) {
		this.retorno = retorno;
	}
}
