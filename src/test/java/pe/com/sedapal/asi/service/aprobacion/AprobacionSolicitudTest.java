package pe.com.sedapal.asi.service.aprobacion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import pe.com.sedapal.asi.AppConfig;
import pe.com.sedapal.asi.service.IAprobacionSolicitudService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class AprobacionSolicitudTest {

	@Autowired
	private IAprobacionSolicitudService aprobacionSolicitud;
	
	@Test
	public void testAprobacionSolicitud() throws Exception {
		String codTrabajador = "";
		boolean requiereAprobacion = aprobacionSolicitud.requireAprobacion(codTrabajador);
		
		assertEquals(true, requiereAprobacion);
	}
}
