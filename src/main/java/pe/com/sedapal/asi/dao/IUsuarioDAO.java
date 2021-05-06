package pe.com.sedapal.asi.dao;

import pe.com.sedapal.asi.model.Usuario;

public interface IUsuarioDAO {

	Usuario consultarUsuario(String username);
}
