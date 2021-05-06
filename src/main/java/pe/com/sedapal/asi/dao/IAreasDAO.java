/**
 * @package       pe.com.sedapal.ssi.dao
 * @interface     IAreasDAO
 * @description   interface areas
 * @author        sayda moises

 * -------------------------------------------------------------------------------------
 * Historia de modificaciones
 * Requerimiento    Autor       Fecha         Descripci�n
 * -------------------------------------------------------------------------------------
 */
package pe.com.sedapal.asi.dao;


import java.util.List;

import pe.com.sedapal.asi.model.Area;
import pe.com.sedapal.asi.model.Trabajador;
import pe.com.sedapal.asi.model.request_objects.AreaRequest;
import pe.com.sedapal.asi.model.request_objects.PageRequest;
import pe.com.sedapal.asi.model.response_objects.Error;
import pe.com.sedapal.asi.model.response_objects.Paginacion;

public interface IAreasDAO {
	List<Area> obtenerAreas(AreaRequest areaRequest, PageRequest pageRequest, Long codigo);
	List<Area> obtenerAreasEquipos(Long codigo);
	Trabajador obtenerJefeArea(Long codarea);
	Trabajador actualizarJefeArea(Long codArea, Trabajador trabajador, String usuario);
	Paginacion getPaginacion();
	Error getError();
	Trabajador validarJefe(Long codarea,Long nficha);
}
