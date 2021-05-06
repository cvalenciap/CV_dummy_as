package pe.com.sedapal.asi.service;

import pe.com.sedapal.asi.model.request_objects.TicketContratistaRequest;
import pe.com.sedapal.asi.model.response_objects.TicketContratistaResponse;

public interface ITicketService {
	
	TicketContratistaResponse createTicket(TicketContratistaRequest ticketRequest, String urlWsTicket);
	
}
