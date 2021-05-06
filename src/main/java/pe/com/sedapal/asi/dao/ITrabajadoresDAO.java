/**
 * @package       pe.com.sedapal.ssi.dao
 * @interface     ITrabajadoresDAO
 * @description   interface trabajadores
 * @author        sayda moises

 * -------------------------------------------------------------------------------------
 * Historia de modificaciones
 * Requerimiento    Autor       Fecha         Descripci�n
 * -------------------------------------------------------------------------------------
 */
package pe.com.sedapal.asi.dao;

import java.util.List;

import pe.com.sedapal.asi.model.Trabajador;
import pe.com.sedapal.asi.model.request_objects.TrabajadorRequest;
import pe.com.sedapal.asi.model.response_objects.Error;

public interface ITrabajadoresDAO {
	List<Trabajador> obtenerTrabajadores(TrabajadorRequest trabajadorRequest);
	Error getError();
}
