package pe.com.sedapal.asi.util;

import java.util.List;
import java.util.Map;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.MustacheException;
import com.samskivert.mustache.Template;

public class MustacheUtil {

	public static String procesarCabeceraCorreo(String htmlCabeceraCorreo, Map<String, Object> cabeceraCorreo) {
		String result = "";
		
		try {
			Template tmpl = Mustache.compiler().escapeHTML(false).compile(htmlCabeceraCorreo);
			result = tmpl.execute(cabeceraCorreo);
		} catch (MustacheException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	public static String procesarDetallesCorreo(String htmlDetalleCorreo, List<Map<String, Object>> detalleCorreo) {
		String result = "";
		
		try {
			Template tmpl = Mustache.compiler().escapeHTML(false).compile(htmlDetalleCorreo);
			result = tmpl.execute(new Object() {
				List<Map<String, Object>> requerimientos() {
					return detalleCorreo;
				}
			});
		} catch (MustacheException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
