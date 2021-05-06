package pe.com.sedapal.asi.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.sedapal.asi.model.AsiUser;
import pe.com.sedapal.asi.model.Usuario;
import pe.com.sedapal.asi.model.request_objects.LoginRequest;
import pe.com.sedapal.asi.model.request_objects.ResetPasswordRequest;
import pe.com.sedapal.asi.model.response_objects.AuthResponse;
import pe.com.sedapal.asi.model.response_objects.Estado;
import pe.com.sedapal.asi.model.response_objects.ResponseObject;
import pe.com.sedapal.asi.service.IUsuariosService;

@RestController
@RequestMapping("/auth")
public class AuthApi {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private IUsuariosService service;

	private AuthResponse auth;

	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> autenticaUsuario(@Valid @RequestBody LoginRequest loginRequest,
			HttpServletRequest req) {
		ResponseObject response = new ResponseObject();

		try {
			req.setAttribute("password", loginRequest.getPassword());
			req.setAttribute("username", loginRequest.getUsername());

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			if (userDetails != null) {
				AsiUser asiUser = (AsiUser) userDetails;
				auth = this.service.getUserDetail(asiUser);

				if (auth == null) {
					response.setEstado(Estado.ERROR);
					response.setError(404, "No se obtiene los parametros", null);
					return new ResponseEntity<ResponseObject>(response, HttpStatus.NOT_FOUND);
				} else {
					response.setResultado(auth);
					response.setEstado(Estado.OK);
					return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
				}
			} else {
				response.setEstado(Estado.ERROR);
				response.setError(404, "No se encuentra el registro", null);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.NOT_FOUND);
			}

		} catch (InternalAuthenticationServiceException e) {
			response.setError(401, e.getMessage(), e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/login-profile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> getPermissions(@Valid @RequestBody LoginRequest loginRequest,
			HttpServletRequest req) {
		ResponseObject response = new ResponseObject();

		try {

			Usuario usuario = service.obtenerPerfilesModulos(loginRequest);

			if (usuario == null) {
				response.setEstado(Estado.ERROR);
				response.setError(404, "No se obtiene los parametros", null);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				response.setEstado(Estado.OK);
				response.setResultado(usuario);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}

		} catch (InternalAuthenticationServiceException e) {
			response.setError(401, "El nombre de usuario o la contraseña son inválidos", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/login/password/request", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> solicitarPassword(@Valid @RequestBody ResetPasswordRequest passwordRequest,
			HttpServletRequest req) {
		ResponseObject response = new ResponseObject();

		try {
			String result = this.service.requestPassword(passwordRequest.getUsername());
			response.setEstado(Estado.OK);
			response.setResultado(result);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (BadCredentialsException e) {
			response.setError(404, "El usuario ingresado no es válido", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.NOT_FOUND);
		} catch (InternalAuthenticationServiceException e) {
			response.setError(2, "Error en el servicio de seguridad", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/login/password/reset", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> cambiarPassword(@Valid @RequestBody ResetPasswordRequest passwordRequest,
			HttpServletRequest req) {
		ResponseObject response = new ResponseObject();
		try {
			String result = this.service.resetPassword(passwordRequest);
			response.setEstado(Estado.OK);
			response.setResultado(result);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch (BadCredentialsException e) {
			response.setError(401, e.getMessage(), e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.UNAUTHORIZED);
		} catch (InternalAuthenticationServiceException e) {
			response.setError(2, "Error en el servicio de seguridad", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@RequestMapping(value = "auth/refresh/{delta}", method = RequestMethod.GET)
//	@ResponseBody
//	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, @PathVariable("delta") Long delta, Device device) {
//	    String authToken = request.getHeader(tokenHeader);
//	    if(authToken != null && authToken.startsWith("Bearer ")) {
//	        authToken = authToken.substring(7);
//	    }
//	    String username = jwtTokenUtil.getUsernameFromToken(authToken);
//	    boolean isOk = true;
//	    if(username == null) {
//	        isOk = false;
//	    } else {
//	        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//	        isOk = jwtTokenUtil.validateToken(authToken, userDetails);
//	    }
//	    if(!isOk) {
//	        Map<String, String> errorMap = new HashMap<>();
//	        errorMap.put("message", "You are not authorized");
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
//	    }
//	    // renew the token
//	    final String token = jwtTokenUtil.generateToken(username, device, delta);
//	    return ResponseEntity.ok(new JwtAuthenticationResponse(token));
//	}

}
