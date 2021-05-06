package pe.com.sedapal.asi.mustache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

public class MustacheTest {
	private String htmlCabApp = "<html><body><p stype=\"font-family: Tahoma;\"><span>Estimado(a) Sr(ta). {{nombressolic}} ({{usuariosolic}})</span><br/><span>Le informamos que su(s) requerimientos ha(n) sido registrado(s) en nuestro sistema.</span><br/><span>A continuacion los detalles:</span></p><table border=\"1\" cellspacing=\"0\" style=\"font-family: Tahoma; font-size:80%\"><tr><td colspan=\"2\" bgcolor=\"#5AB7E0\"><b>DETALLE DEL SERVICIO</b></td></tr><tr><td colspan=\"2\">Datos generales del usuario solicitante:</td></tr><tr><td>Fecha de Creacion</td><td> {{fechacreacionreq}}</td></tr><tr><td>Usuario Solicitante</td><td> {{usuariosolic}}</td></tr><tr><td>Nombres y Apellidos</td><td> {{nombressolic}}</td></tr><tr><td>Tipo Cargo</td><td> {{cargosolic}}</td></tr><tr><td>Sede</td><td> {{sede}}</td></tr><tr><td>Gerencia</td><td> {{nomgerencia}}</td></tr><tr><td>Equipo</td><td> {{nomequipo}}</td></tr><tr><td>Correo</td><td> {{correo}}</td></tr><tr><td>Ficha/DNI</td><td> {{fichadni}}</td></tr><tr><td>Telefono/Anexo</td><td> {{telefonoanexo}}</td></tr></table><p><b>REQUERIMIENTOS DE RECURSOS TIC</b><hr><b>Gerencia de Desarrollo e Investigacion / Equipo Tecnologias de la Informacion y Comunicaciones</b><br/><b>Responsable(s) de aprobacion:</b><br/>{{#listaaprobadores}}{{this}}<br/>{{/listaaprobadores}}</p>";
	private String htmlDetApp = "<p class=\"categoria_recursostic\"><table border=\"1\" cellspacing=\"0\" style=\"font-family: Tahoma; font-size:80%\"><thead><tr><th colspan=\"9\">SOLICITUD DE ACCESOS - ACCESO A RECURSOS TIC</th></tr><tr><th align=\"center\">Ticket</th><th align=\"center\">Usuario Afectado</th><th align=\"center\">Recurso TIC</th><th align=\"center\">Accion</th><th align=\"center\">Cuenta de RED</th><th align=\"center\">Codigo Inventario</th><th align=\"center\">Fecha Fin Vigencia</th><th align=\"center\">Observaciones</th><th align=\"center\">Archivo Adjunto</th></tr></thead><tbody>{{#requerimientos}}<tr class=\"fila_requerimiento\"><td align=\"center\">{{ticket}}</td><td><b>{{usuarioafectado}}</b><br/><small>Ficha: <b>{{uficha}}</b><br/>Situacion: <b>{{usituacion}}</b><br/>Equipo: <b>{{uequipo}}</b><br/>Telefono/Anexo: <b>{{utelefono}}</b><br/></small></td><td align=\"center\">{{recursotic}}</td><td align=\"center\">{{accion}}</td><td align=\"center\">{{cuentared}}</td><td align=\"center\">{{codigoinventario}}</td><td align=\"center\">{{fechafinvigencia}}</td><td align=\"center\">{{observaciones}}</td><td align=\"center\">{{archivoadjunto}}</td></tr>{{/requerimientos}}</tbody></table></p>";

	private String htmlCondic = "{{#mostrar}}<tr><td>{{nrosolicitud}}</td></tr>{{/mostrar}}";

	@Test
	public void testMostrar() {
		Map<String, Object> mapValues = new HashMap<>();
		mapValues.put("mostrar", false);
		mapValues.put("nrosolicitud", "1231546");

		Template tmpl = Mustache.compiler().compile(htmlCondic);
		String result = tmpl.execute(mapValues);
		System.out.println(result);

		mapValues = new HashMap<>();
		mapValues.put("mostrar", true);
		mapValues.put("nrosolicitud", "per&uacute;");
//		htmlCondic = htmlCondic.replace("{{nrosolicitud}}", "per&uacute;");
		
		tmpl = Mustache.compiler().escapeHTML(false).compile(htmlCondic);
		result = tmpl.execute(mapValues);
		System.out.println(result);
	}

//	@Test
	public void testSetVariablesCabecera() {
		List<String> aprobadores = new ArrayList<>();
		aprobadores.add("1 Agreda Diaz, Pablo ");
		aprobadores.add("2 Chumpitaz Ccencho, Anthony");
		aprobadores.add("3 Velásquez Alvarado, Dante Ricardo");

		Map<String, Object> mapValues = new HashMap<>();
		mapValues.put("fechacreacionreq", "27/09/2019");
		mapValues.put("usuariosolic", "DVELASQUEZ");
		mapValues.put("nombressolic", "Velásquez Alvarado, Dante Ricardo");
		mapValues.put("cargosolic", "ANALISTA");
		mapValues.put("sede", "COP Atarjea");
		mapValues.put("nomgerencia", "GERENCIA DE DESARROLLO E INVESTIGACIÓN");
		mapValues.put("nomequipo", "EQUIPO DE TECNOLOGIAS DE LA INFORMACIÓN Y COMUNICACIONES");
		mapValues.put("correo", "dvelasquez@sedapal.com.pe");
		mapValues.put("fichadni", "1233882");
		mapValues.put("telefonoanexo", "4567");
		mapValues.put("listaaprobadores", aprobadores);

		Template tmpl = Mustache.compiler().compile(htmlCabApp);
		String result = tmpl.execute(mapValues);
		System.out.println(result);
	}

//	@Test
	public void testSetVariablesDetalle() {
		List<Map<String, Object>> solicitudDt = new ArrayList<>();

		Map<String, Object> mapValues = new HashMap<>();
		mapValues.put("ticket", "");
		mapValues.put("usuarioafectado", "Víctor De Los Santos Robles");
		mapValues.put("uficha", "3434456");
		mapValues.put("usituacion", "ESTABLE");
		mapValues.put("uequipo", "Equipo Tecnologías de la Información y Comunicaciones");
		mapValues.put("utelefono", "3458");
		mapValues.put("recursotic", "Correo");
		mapValues.put("accion", "Crear");
		mapValues.put("cuentared", "vdelossantos@sedapal.com.pe");
		mapValues.put("codigoinventario", "3344-2018");
		mapValues.put("fechafinvigencia", "31/12/2019");
		mapValues.put("observaciones", "");
		mapValues.put("archivoadjunto", "Resol_09_19.pdf");
		solicitudDt.add(mapValues);

		mapValues = new HashMap<>();
		mapValues.put("ticket", "");
		mapValues.put("usuarioafectado", "Víctor De Los Santos Robles");
		mapValues.put("uficha", "123456");
		mapValues.put("usituacion", "ESTABLE");
		mapValues.put("uequipo", "Equipo Tecnologías de la Información y Comunicaciones");
		mapValues.put("utelefono", "3458");
		mapValues.put("recursotic", "Correo");
		mapValues.put("accion", "Crear");
		mapValues.put("cuentared", "vdelossantos@sedapal.com.pe");
		mapValues.put("codigoinventario", "3344-2018");
		mapValues.put("fechafinvigencia", "31/12/2019");
		mapValues.put("observaciones", "");
		mapValues.put("archivoadjunto", "Resol_09_19.pdf");
		solicitudDt.add(mapValues);

		Template tmpl = Mustache.compiler().compile(htmlDetApp);
		String result = tmpl.execute(new Object() {
			List<Map<String, Object>> requerimientos() {
				return solicitudDt;
			}
		});
		System.out.println(result);
	}
}
