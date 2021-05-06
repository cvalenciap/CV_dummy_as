package pe.com.sedapal.asi.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;

import pe.com.sedapal.asi.dao.IUsuarioDAO;
import pe.com.sedapal.asi.model.AsiUser;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.Formulario;
import pe.com.sedapal.asi.model.Modulo;
import pe.com.sedapal.asi.model.Perfil;
import pe.com.sedapal.asi.model.Usuario;
import pe.com.sedapal.asi.model.request_objects.LoginRequest;
import pe.com.sedapal.asi.model.request_objects.ResetPasswordRequest;
import pe.com.sedapal.asi.model.response_objects.AuthResponse;
import pe.com.sedapal.asi.security.service.ParametrosSeguridad;
import pe.com.sedapal.asi.service.IParametrosService;
import pe.com.sedapal.asi.service.IUsuariosService;
import pe.com.sedapal.asi.util.AppConstants;
import pe.com.sedapal.asi.util.SessionConstants;
import pe.com.sedapal.seguridad.core.bean.Retorno;
import pe.com.sedapal.seguridad.core.bean.SistemaModuloOpcionBean;
import pe.com.sedapal.seguridad.ws.SeguridadClienteWs;

@Service
public class UsuariosServiceImpl implements IUsuariosService {
	Logger logger = LoggerFactory.getLogger(UsuariosServiceImpl.class);

	@Autowired
	private SeguridadClienteWs stub;

	@Autowired
	IUsuarioDAO dao;

	@Value("${parametro.token.jwtExpiration}")
	private int timeSesion;

	@Value("${parametro.sistema.codigo}")
	private int codigoSistema;

	@Autowired
	private ParametrosSeguridad paramsSeguridad;

	@Autowired
	private IParametrosService parametrosService;

	@Override
	public Usuario consultarUsuario(String username) {
		Usuario usuario = new Usuario();
		usuario = this.dao.consultarUsuario(username);
		return usuario;
	}

	@Override
	public AuthResponse getUserDetail(AsiUser asiUser) {

		Usuario usuario = new Usuario();
		AuthResponse auth = new AuthResponse();

		// Datos del usuario
		usuario = this.dao.consultarUsuario(asiUser.getUsername());

		// Permisos/urls
		List<SistemaModuloOpcionBean> lstpermisos = listaPermiso(asiUser.getUsername(), codigoSistema);
		Comparator<SistemaModuloOpcionBean> compareByCodModulo = (SistemaModuloOpcionBean o1, SistemaModuloOpcionBean o2) -> o1.getCodModulo().compareTo( o2.getCodModulo() );
		Collections.sort(lstpermisos, compareByCodModulo);
		usuario.setPermisos(lstpermisos);

		// Profile
		List<Perfil> lstPerfiles = obtenerPerfiles(asiUser);
		usuario.setPerfiles(lstPerfiles);

		auth.setExpiresIn(timeSesion);
		auth.setToken(asiUser.getRetorno().getToken());
		auth.setUserProfile(usuario);

		return auth;
	}

	@Override
	public Usuario obtenerPerfilesModulos(LoginRequest loginRequest) {

		Usuario usuario = new Usuario();
		List<SistemaModuloOpcionBean> listaMenus = null;
		List<String> listaPermisos = null;
		List<Perfil> listaPerfil = null;
		List<Modulo> listaModulos = null;

		String username = loginRequest.getUsername();
		int idProfile = loginRequest.getIdProfile();

		listaMenus = stub.obtenerMenuActWs(paramsSeguridad.getUrlWsSeguridad(), username, codigoSistema, idProfile);
		listaPermisos = stub.obtenerPermisosActWs(paramsSeguridad.getUrlWsSeguridad(), username, codigoSistema,
				idProfile);

		try {
			usuario.setDescripcionSistema(listaMenus.get(0).getSistemaNombre());
			usuario.setUidSistema(listaMenus.get(0).getCodSistema());

			listaPerfil = obtenerPerfiles(listaMenus);
			listaModulos = listaPerfil.get(0).getModulos();

			usuario.setModulos(listaModulos);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<SistemaModuloOpcionBean> objectList = (List)listaPermisos;
			usuario.setPermisos(objectList);

		} catch (Exception e) {
			logger.error("[ASI: UsuarioServicioImpl - obtenerPerfilesModulos()] - " + e.getMessage());
		}
		return usuario;
	}

	private List<Perfil> obtenerPerfiles(List<SistemaModuloOpcionBean> menus) {

		String defaultPath = "";
		Integer codigoPerfilAux = 0, codigoModuloAux = 0, index = 0;
		List<Integer> iCambioModulo = new ArrayList<Integer>();
		List<Integer> iCambioPerfil = new ArrayList<Integer>();
		List<Perfil> perfiles = new ArrayList<Perfil>();
		List<Modulo> modulos = new ArrayList<Modulo>();
		List<Formulario> formularios = new ArrayList<Formulario>();

		// Se realiza el ordenamiento por código de módulo
		Collections.sort(menus, (SistemaModuloOpcionBean s1, SistemaModuloOpcionBean s2) -> s1.getCodModulo()
				.compareTo(s2.getCodModulo()));

		// Se realiza el ordenamiento por código de perfil
		Collections.sort(menus, (SistemaModuloOpcionBean s1, SistemaModuloOpcionBean s2) -> s1.getCodPerfil()
				.compareTo(s2.getCodPerfil()));

		for (SistemaModuloOpcionBean menu : menus) {

			codigoModuloAux = menu.getCodModulo();
			codigoPerfilAux = menu.getCodPerfil();

			if (perfiles.isEmpty()) {
				defaultPath = creatDefaultPath(menu.getPerfilNombre());
				perfiles.add(new Perfil(menu.getCodPerfil(), menu.getPerfilNombre(), defaultPath));
			}

			if (modulos.isEmpty())
				modulos.add(new Modulo(menu.getCodModulo(), menu.getModuloNombre()));

			if (!codigoModuloAux.equals(modulos.get(modulos.size() - 1).getCodigo())
					|| !codigoPerfilAux.equals(perfiles.get(perfiles.size() - 1).getCodigo())) {
				modulos.add(new Modulo(menu.getCodModulo(), menu.getModuloNombre()));
				iCambioModulo.add(index);
			}

			if (!codigoPerfilAux.equals(perfiles.get(perfiles.size() - 1).getCodigo())) {
				defaultPath = creatDefaultPath(menu.getPerfilNombre());
				perfiles.add(new Perfil(menu.getCodPerfil(), menu.getPerfilNombre(), defaultPath));
				iCambioPerfil.add(iCambioModulo.size());
			}

			formularios.add(cargarFormulario(menu));
			index++;
		}

		asignarFormularios(modulos, formularios, iCambioModulo);
		asignarModulos(perfiles, modulos, iCambioPerfil);

		return perfiles;
	}

	private List<Perfil> obtenerPerfiles(AsiUser asiUser) {

		Retorno retorno = asiUser.getRetorno();

		String defaultPath = "";
		List<Perfil> listaPerfiles = new ArrayList<Perfil>();

		for (pe.com.sedapal.seguridad.core.bean.PerfilSistemaBean perfil : retorno.getPerfilesAct()) {
			defaultPath = creatDefaultPath(perfil.getDescripcion());
			listaPerfiles.add(new Perfil(perfil.getCodPerfil(), perfil.getDescripcion(), defaultPath));
		}

		return listaPerfiles;
	}

	private String creatDefaultPath(String descripcion) {

		Boolean flagNo = false;
		String defaultPath = AppConstants.PATH_DEFAULT;
		List<DetalleParametro> detailParam = this.parametrosService.obtenerDetallesParametro(67);

		if (detailParam.size() > 0) {
			flagNo = detailParam.get(0).getValorPara1().equals(AppConstants.FLAG_NO);
		}

		if (ArrayUtils.contains(new String[] { AppConstants.CLIENTE, AppConstants.REVISOR }, descripcion.toUpperCase())
				&& flagNo) {
			defaultPath = AppConstants.PATH_REGISTER;
		}

		return defaultPath;
	}

	private List<Perfil> asignarModulos(List<Perfil> perfiles, List<Modulo> modulos, List<Integer> iCambioPerfil) {
		Integer index = 0, indexPerfil = 0;
		List<List<Modulo>> arrayListaModulos = new ArrayList<List<Modulo>>();

		if (iCambioPerfil.isEmpty()) {
			perfiles.get(indexPerfil).setModulos(modulos);
		} else {
			List<Modulo> listaInicial = new ArrayList<Modulo>();
			arrayListaModulos.add(listaInicial);
			for (Modulo modulo : modulos) {
				if (!iCambioPerfil.contains(index)) {
					arrayListaModulos.get(indexPerfil).add(modulo);
				} else {
					perfiles.get(indexPerfil).setModulos(arrayListaModulos.get(indexPerfil));
					List<Modulo> listaAuxiliar = new ArrayList<Modulo>();
					arrayListaModulos.add(listaAuxiliar);
					indexPerfil++;
					arrayListaModulos.get(indexPerfil).add(modulo);
				}

				index++;
			}

			perfiles.get(indexPerfil).setModulos(arrayListaModulos.get(indexPerfil));
		}
		return perfiles;
	}

	private Formulario cargarFormulario(SistemaModuloOpcionBean menu) {
		Formulario formulario = new Formulario();
		formulario.setCodigo(menu.getCodFormulario());
		formulario.setCodigoPadre(menu.getCodFormularioPadre());
		formulario.setDescripcion(menu.getDescripcion());
		formulario.setEstado(menu.getEstado());
		formulario.setDescripcionEstado(menu.getEstadoNombre());
		formulario.setNivel(menu.getNivelFormulario());
		formulario.setOrden(menu.getOrdenFormulario());
		formulario.setUrl(menu.getUrlFormulario());
		return formulario;
	}

	private List<Modulo> asignarFormularios(List<Modulo> modulos, List<Formulario> formularios,
			List<Integer> iCambioModulo) {
		Integer index = 0, indexModulo = 0;
		List<List<Formulario>> arrayListaFormularios = new ArrayList<List<Formulario>>();

		if (iCambioModulo.isEmpty()) {
			modulos.get(indexModulo).setFormularios(formularios);
		} else {
			List<Formulario> listaInicial = new ArrayList<Formulario>();
			arrayListaFormularios.add(listaInicial);
			for (Formulario formulario : formularios) {
				if (!iCambioModulo.contains(index)) {
					arrayListaFormularios.get(indexModulo).add(formulario);
				} else {
					modulos.get(indexModulo).setFormularios(arrayListaFormularios.get(indexModulo));
					List<Formulario> listaAuxiliar = new ArrayList<Formulario>();
					arrayListaFormularios.add(listaAuxiliar);
					indexModulo++;
					arrayListaFormularios.get(indexModulo).add(formulario);
				}
				index++;
			}
			modulos.get(indexModulo).setFormularios(arrayListaFormularios.get(indexModulo));
		}

		return modulos;
	}

	public List<SistemaModuloOpcionBean> listaPermiso(String username, Integer codSistema) {
		List<SistemaModuloOpcionBean> lstMenus = stub.obtenerMenueWs(paramsSeguridad.getUrlWsSeguridad(), username,
				codSistema);

		return lstMenus;
	}

	/* se ejecuta cuando usuario solicita nueva contraseña */
	@Override
	public String requestPassword(String username) {
		Retorno retorno = null;
		try {
			retorno = stub.olvidarClaveWs(paramsSeguridad.getUrlWsSeguridad(), username);
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage());
		}
		if (retorno == null) {
			throw new InternalAuthenticationServiceException("No se obtuvo una respuesta del servicio");
		} else if (SessionConstants.FAILURE.equals(retorno.getCodigo())) {
			throw new BadCredentialsException(retorno.getMensaje());
		} else if (SessionConstants.SUCCESS.equals(retorno.getCodigo())) {
			return retorno.getMensaje();
		} else {
			throw new InternalAuthenticationServiceException("No se obtuvo el resultado esperado");
		}
	}

	/* se ejecuta cuando usuario establece nueva contraseña */
	@Override
	public String resetPassword(ResetPasswordRequest request) {
		Retorno retorno = null;
		try {
			retorno = stub.actualizarClaveWs(paramsSeguridad.getUrlWsSeguridad(), request.getUsername().toUpperCase(),
					request.getOldPassword(), request.getNewPassword(), request.getNewPasswordCheck());
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage());
		}
		if (retorno == null) {
			throw new InternalAuthenticationServiceException("No se obtuvo una respuesta del servicio");
		} else if (SessionConstants.FAILURE.equals(retorno.getCodigo())) {
			throw new BadCredentialsException(retorno.getMensaje());
		} else if (SessionConstants.SUCCESS.equals(retorno.getCodigo())) {
			return retorno.getMensaje();
		} else {
			throw new InternalAuthenticationServiceException("No se obtuvo el resultado esperado");
		}
	}
}