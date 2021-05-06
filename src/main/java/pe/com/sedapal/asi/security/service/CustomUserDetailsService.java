package pe.com.sedapal.asi.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.com.sedapal.asi.model.AsiUser;
import pe.com.sedapal.asi.security.filter.SedapalPasswordEncoder;
import pe.com.sedapal.asi.util.SessionConstants;
import pe.com.sedapal.seguridad.core.bean.Retorno;
import pe.com.sedapal.seguridad.ws.SeguridadClienteWs;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private SeguridadClienteWs stub;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private Environment env;
	
	@Autowired
	private ParametrosSeguridad paramsSeguridad;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Integer codigo_sistema = Integer.parseInt(env.getProperty("parametro.sistema.codigo"));

		Retorno retornoAuthUser = null;
		Retorno retornoAuthUserComplete = null;
		List<String> permisos = null;
		List<GrantedAuthority> authorities = null;
		Integer codPerfil = null;

		try {
			String password = (String) request.getAttribute("password");
			String urlWsSeguridad = paramsSeguridad.getUrlWsSeguridad();

			retornoAuthUser = stub.autenticacionUsuarioActWs(urlWsSeguridad, username.toUpperCase(), codigo_sistema, password);
			retornoAuthUser.setClave(password);

			if (SessionConstants.FAILURE.equals(retornoAuthUser.getCodigo())) {
				throw new BadCredentialsException(retornoAuthUser.getMensaje());
			} else {
				if (retornoAuthUser.getPerfilesAct() != null && retornoAuthUser.getPerfilesAct().size() > 0) {
					codPerfil = retornoAuthUser.getPerfilesAct().get(0).getCodPerfil();
				}
				
				retornoAuthUserComplete = stub.autenticacionUsuarioCompletaActWs(urlWsSeguridad,
						username.toUpperCase(),
						request.getRemoteAddr(),
						retornoAuthUser.getToken(),
						codigo_sistema,
						codPerfil);
				
				retornoAuthUser.setCodigo(retornoAuthUserComplete.getCodigo());
				retornoAuthUser.setMensaje(retornoAuthUserComplete.getMensaje());
				retornoAuthUser.setUltimoAcceso(retornoAuthUserComplete.getUltimoAcceso());
			}

			// permisos orientado a rutas de menu
			if (Integer.parseInt(retornoAuthUser.getFlagCambiarClave()) != SessionConstants.ESTADO_INACTIVO) {
				permisos = new ArrayList<>();
				permisos.add("restablecerClave");
			} else {
				permisos = stub.obtenerPermisosActWs(paramsSeguridad.getUrlWsSeguridad(), username, codigo_sistema, codPerfil);
				permisos.add("cambioClave");
			}
			authorities = buildUserAuthority(permisos);

			// servicio de parametros
			request.removeAttribute("password");
		} catch (UsernameNotFoundException e) {
			logger.debug("Usuario no encontrado: " + username);
			throw new UsernameNotFoundException(e.getMessage());
		} catch (Exception e) {
			logger.error("No se pudo acceder al webservice de seguridad", e);
			if (e.getMessage() != null && (e.getMessage().contains("error al realizar la operacion = I/O error")
					|| e.getMessage().contains("error al realizar la operacion = No Encontrado"))) {
				throw new AccessDeniedException("Error: No se puede acceder al webservice de seguridad");
			}
			throw new AccessDeniedException(e.getMessage());
		}

		return buildUserForAuthentication(retornoAuthUser, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<String> permisos) {
		Set<GrantedAuthority> setAuths = new HashSet<>();

		for (String permiso : permisos) {
			setAuths.add(new SimpleGrantedAuthority(permiso));
		}

		return new ArrayList<>(setAuths);
	}

	private User buildUserForAuthentication(Retorno retornoAuthUser,List<GrantedAuthority> authorities) {
		SedapalPasswordEncoder pwdEncoder = new SedapalPasswordEncoder();
		AsiUser user = new AsiUser(retornoAuthUser.getUsuario(), pwdEncoder.encode(retornoAuthUser.getClave()), authorities);
		user.setRetorno(retornoAuthUser);
		retornoAuthUser.setClave(null);
		return user;
	}

}
