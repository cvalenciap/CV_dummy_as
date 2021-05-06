package pe.com.sedapal.asi.service;

import pe.com.sedapal.asi.model.AsiUser;
import pe.com.sedapal.asi.model.Usuario;
import pe.com.sedapal.asi.model.request_objects.LoginRequest;
import pe.com.sedapal.asi.model.request_objects.ResetPasswordRequest;
import pe.com.sedapal.asi.model.response_objects.AuthResponse;

public interface IUsuariosService {
	Usuario consultarUsuario(String username);
    AuthResponse getUserDetail(AsiUser asiUser);
    String requestPassword(String username);
	String resetPassword(ResetPasswordRequest request);
	Usuario obtenerPerfilesModulos(LoginRequest loginRequest);
}
