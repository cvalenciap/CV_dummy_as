package pe.com.sedapal.asi.service.ticket;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pe.com.sedapal.asi.AppConfig;
import pe.com.sedapal.asi.model.DetalleParametro;
import pe.com.sedapal.asi.model.request_objects.TicketContratistaRequest;
import pe.com.sedapal.asi.model.response_objects.TicketContratistaResponse;
import pe.com.sedapal.asi.service.IParametrosService;
import pe.com.sedapal.asi.service.ITicketService;
import pe.com.sedapal.asi.util.MailConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class TicketServiceTest {

	@Autowired
	private ITicketService ticketService;
	
	@Autowired
	private IParametrosService parametrosService;
	
	@Test
	public void testGenerarTicket() {
		TicketContratistaRequest ticketRequest = new TicketContratistaRequest();
		ticketRequest.setCategoria("");
		ticketRequest.setDescripcion("");
		ticketRequest.setEstadoSolicitud("");
		ticketRequest.setImpacto("");
		ticketRequest.setObservaciones("");
		ticketRequest.setOrigen("");
		ticketRequest.setPrioridad("");
		ticketRequest.setProducto("");
		ticketRequest.setResumen("");
		ticketRequest.setSede("");
		ticketRequest.setTicket("");
		ticketRequest.setTipo("");
		ticketRequest.setUrgencia("");
		ticketRequest.setUsuarioSolicitante("NUÑEZ DEL PRADO");

		List<DetalleParametro> parametroDetalle = parametrosService.obtenerDetallesParametro(MailConstants.PARAM_URL_WS_CONTRATISTA);
		String urlWsTicket = parametroDetalle.get(0).getValorPara1();
		
		//urlWsTicket = "http://10.240.147.42:8080/integracion/crearticket";
		
		TicketContratistaResponse response = ticketService.createTicket(ticketRequest, urlWsTicket);
		
		System.out.println("Ticket=" + response.getResultado().getTicket());
	}
}
