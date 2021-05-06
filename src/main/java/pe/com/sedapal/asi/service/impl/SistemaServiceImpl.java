package pe.com.sedapal.asi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pe.com.sedapal.asi.dao.ISistemaDAO;
import pe.com.sedapal.asi.model.InformacionSistema;
import pe.com.sedapal.asi.service.ISistemaService;

@Service
public class SistemaServiceImpl implements ISistemaService {

    @Value("${app.information.name}")
    private String nombreAplicacion;

    @Value("${app.information.package}")
    private String paqueteAplicacion;

    @Value("${app.information.version}")
    private String versionAplicacion;

    @Autowired
    private ISistemaDAO dao;

    @Override
    public InformacionSistema obtenerInformacionSistema() {
        InformacionSistema info = new InformacionSistema();
        info.setNombre(nombreAplicacion);
        info.setPaquete(paqueteAplicacion);
        info.setVersion(versionAplicacion);
        dao.obtenerParametros(info);
        return info;
    }
}
