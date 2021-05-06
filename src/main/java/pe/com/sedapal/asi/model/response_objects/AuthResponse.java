package pe.com.sedapal.asi.model.response_objects;

import pe.com.sedapal.asi.model.Usuario;

public class AuthResponse {
	
	private int expiresIn;
	
	private String token;
	
	private Usuario userProfile;
	
	
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthResponse(int expiresIn, String token, Usuario userProfile) {
		super();
		this.expiresIn = expiresIn;
		this.token = token;
		this.userProfile = userProfile;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Usuario userProfile) {
		this.userProfile = userProfile;
	}
	
	
	

}
