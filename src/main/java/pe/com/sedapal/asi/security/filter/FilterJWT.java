package pe.com.sedapal.asi.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import pe.com.sedapal.asi.security.service.ParametrosSeguridad;
import pe.com.sedapal.asi.util.JwtUtil;
import pe.com.sedapal.asi.util.SessionConstants;
import pe.com.sedapal.seguridad.ws.SeguridadClienteWs;

@Order(Ordered.LOWEST_PRECEDENCE)
public class FilterJWT extends GenericFilterBean {
	private static final Logger logger = LoggerFactory.getLogger(FilterJWT.class);
	
	private SeguridadClienteWs stub;
	private ParametrosSeguridad paramsSeguridad;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
			throws IOException, ServletException {
		try {
			 HttpServletRequest req = (HttpServletRequest) request;
			
			if (!req.getRequestURI().equals(SessionConstants.URL_LOGIN)
					&& !req.getRequestURI().equals(SessionConstants.URL_LOGOUT)
					&& !req.getRequestURI().equals(SessionConstants.URL_APP_INFO)) {
				
				ServletContext servletContext = request.getServletContext();
	            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	            paramsSeguridad = webApplicationContext.getBean(ParametrosSeguridad.class);
	            stub = webApplicationContext.getBean(SeguridadClienteWs.class);
				
				
				 String urlWsSeguridad = paramsSeguridad.getUrlWsSeguridad();
				 Authentication authentication = JwtUtil.getAuthentication(req, urlWsSeguridad, stub);
				 
					 if(authentication!= null) {
						 SecurityContextHolder.getContext().setAuthentication(authentication);
					 } else {
						 SecurityContextHolder.getContext().setAuthentication(null);
					 }
				}
		} catch (Exception e) {
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			e.printStackTrace();
			logger.debug("FilterJWT Exception: " + e.getMessage(), e);
		}
		
		filterchain.doFilter(request, response);
	}

}
