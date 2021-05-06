package pe.com.sedapal.asi.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import pe.com.sedapal.asi.model.request_objects.TicketContratistaRequest;
import pe.com.sedapal.asi.model.response_objects.TicketContratistaResponse;
import pe.com.sedapal.asi.service.ITicketService;
import pe.com.sedapal.asi.util.AppConstants;

@Service
public class TicketServiceImpl implements ITicketService {
	private Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
	
	private TicketContratistaResponse respuestaBean;

	@Override
	public TicketContratistaResponse createTicket(TicketContratistaRequest ticketRequest, String urlWsTicket) {
		respuestaBean = new TicketContratistaResponse();
		
		try{			
			Gson gson = new Gson();
			String paramValue = gson.toJson(ticketRequest);
			/*begin: set configuration header*/
			HttpHeaders headers = new HttpHeaders();
//			headers.add(ConstantesCliente.AUTHORIZATION, setCredentialsAuth(usuario, password));
	        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
	        /*end: set configuration header*/
	        
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<String> request = new HttpEntity<String>(paramValue, headers);
			respuestaBean = restTemplate.postForObject(urlWsTicket, request, TicketContratistaResponse.class);
		} catch(Exception e){
			logger.error("Error al consumir servicio integracion contratista");
			respuestaBean.setEstado(AppConstants.ESTADO_ERROR);
			ticketRequest.setTicket("");
			respuestaBean.setResultado(ticketRequest);
		}
		
		return respuestaBean;
	}

}
