package pe.com.sedapal.asi.util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import pe.com.sedapal.seguridad.core.bean.Retorno;
import pe.com.sedapal.seguridad.ws.SeguridadClienteWs;

@Component
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${parametro.token.jwtExpiration}")
	private int jwtExpiration;

	public static Authentication getAuthentication(HttpServletRequest request, 
			                                       String urlWsSeguridad,
			                                       SeguridadClienteWs stub) {
		String token = request.getHeader("Authorization");

		if (token != null && token.startsWith("Bearer ")) {
			token = token.replace("Bearer ", "");
		}

		if (token != null) {
			Retorno retorno = stub.validarTokenWs(urlWsSeguridad, token);
			
			if (retorno != null && AppConstants.TOKEN_VALIDO.equals(retorno.getCodigo())) {
				String user = retorno.getUsuario();
				return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
			} else {
				logger.error("Token no valido para el usuario ");
			}
		}

		return null;
	}

	private static Collection<? extends GrantedAuthority> emptyList() {
		return null;
	}

}
