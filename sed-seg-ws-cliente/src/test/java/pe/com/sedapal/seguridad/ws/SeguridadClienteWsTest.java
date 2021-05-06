package pe.com.sedapal.seguridad.ws;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import pe.com.sedapal.seguridad.core.bean.Retorno;
import pe.com.sedapal.seguridad.core.bean.SistemaModuloOpcionBean;

public class SeguridadClienteWsTest {
	private ApplicationContext factory;
	private String urlWs = "http://10.240.147.46:8080/webserviceserguridad_ACT/seguridadws?wsdl";

	/**
	 * Inicializamos el contexto de Spring
	 */
//	@Before
	public void initTests() {
		this.factory = new FileSystemXmlApplicationContext(
				"classpath:pe/com/sedapal/seguridad/ws/config/applicationContext.xml");
	}

	
//	@Test
	public void olvidarClaveWsTest() {
		SeguridadClienteWs stub = (SeguridadClienteWs) factory.getBean("seguridadClienteWs");
		Retorno retorno = stub.olvidarClaveWs(urlWs, "SCCMADMIN");
		System.out.println(retorno.toString());
		// Assert.assertTrue(true);
	}
	
//	@Test
	public void autenticacionUsuarioWsTest() {
		SeguridadClienteWs stub = (SeguridadClienteWs) factory.getBean("seguridadClienteWs");
		Retorno retorno = stub.autenticacionUsuarioWs(urlWs, "SCCMADMIN",47,"+Qkn65g5J");
		System.out.println(retorno.toString());
		// Assert.assertTrue(true);
	}
	
//	@Test
	public void autenticacionUsuarioCompletaWsTest() {
		SeguridadClienteWs stub = (SeguridadClienteWs) factory.getBean("seguridadClienteWs");
		Retorno retorno = stub.autenticacionUsuarioCompletaWs(urlWs, "MALDANA","127.0.0.1", "#sdosdm",46);
		System.out.println(retorno.toString());
		// Assert.assertTrue(true);
	}
	
//	@Test
	public void obtenerMenuWsTest() {
		SeguridadClienteWs stub = (SeguridadClienteWs) factory.getBean("seguridadClienteWs");
		List<SistemaModuloOpcionBean> lista = stub.obtenerMenueWs(urlWs, "SCCMADMIN", 47);
		for (SistemaModuloOpcionBean sistemaModuloOpcionBean : lista) {
			System.out.println(sistemaModuloOpcionBean.toString());
		}
		
		// Assert.assertTrue(true);
	}
}
