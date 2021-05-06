package pe.com.sedapal.asi.model;

import java.util.List;
import pe.com.sedapal.asi.model.Sedes;

public class ParametroStorage {
	private Long cnt_max_MB;
	private String msg_max_MB;
    private List<DetalleCategoria> lstRecursosTIC;
    private List<AccionDetalleCategoria> lstAccionesRecursosTIC;
    private List<DetalleCategoria> lstAplicativos;
    private List<AccionDetalleCategoria> lstAccionesAplicativos;
	private List<DetalleCategoria> lstSAP;
    private List<AccionDetalleCategoria> lstAccionesSAP;
    private List<DetalleCategoria> lstSolicitudEquipo;
    private List<AccionDetalleCategoria> lstAccionesSolicitudEquipo;
    
    private List<DetalleCategoria> lstTraslado;
    private String filterTiposArchivo;
    private String msg_filterTiposArchivo;
    private Long cnt_max_reg;
    private String msg_resolucion_altas;
    private String msg_registro_tickets;
    private String msg_ws_integracion_inactivo;
    private String msg_aprobacion_jefe;
    private String idEstadoAprobado;
    private String idEstadoDesaprobado; 
    private String idEstadoEnaprobacion;
    private String idEstadoAnulado;
    private String idWebIntegracion; 
    private String idCategoriaAccesoAPL; 
    private String idCategoriaAccesoTIC;
    private String idEstadoIntegracion;
    private String idTipoRequerimiento; 
    private String idUrgencia;
    private String idOrigen;
    private String idProductoMicroInfo;
    private UsuarioAfectado[] lstAprobadoresEquipo;
    private String nota1TIC;
    private String nota2TIC;
    private String nota1Aplicaciones;
    private String nota2Aplicaciones;
    private String htmlRegistroSolicitante;
    private String htmlRegistroAprobador;
    private String htmlAprobaSolicitante;
    private String htmlDesaprobaSolicitante;
    
    private String cabhtmlContratista;
    private String cabhtmlSolicitante;
    private String cabhtmlRevisor;

    private String dethtmlContratistaRECTIC;
    private String dethtmlContratistaAPLIC;
    private String dethtmlContratistaSAP;
    private String dethtmlContratistaTRAS;

	private String dethtmlSolicitanteRECTIC;
    private String dethtmlSolicitanteAPLIC;
    private String dethtmlSolicitanteSAP;
    private String dethtmlSolicitanteTRAS;
    
    private String dethtmlRevisorRECTIC;
    private String dethtmlRevisorAPLIC;
    private String dethtmlRevisorSAP;
    private String dethtmlRevisorTRAS;
    
    private String urlServicioContratista;
    private List<Revisor> revisores;
    private List<DetalleCategoria> lstTipoRequerimiento;
    private Long n_tiempo_espera;
    private List<Sedes> lstSedes;
    private List<Area> lstAreas;
	private List<Situacion> lstSituacion;
	
	private String mensajeErrorGenerico;
	private String indAdjArchivoNotif;
	private String indEnvNotifContra;
	private String indActServWebInteg;
	private List<DetalleCategoria> lstMsjAprobDesaprob;
	private List<DetalleCategoria> lstFormatoCorreoCab;
	
	private String indVisNroSoliNotif;	
	private List<DetalleCategoria> lstValGenTicket;
	
	private List<DetalleCategoria> lstParametros;		

	public ParametroStorage() {
    	super();
    }
    
    public ParametroStorage(
    	    Long cnt_max_MB,
    	    String msg_max_MB,
    	    List<DetalleCategoria> lstRecursosTIC,
    	    List<AccionDetalleCategoria> lstAccionesRecursosTIC,
    	    List<DetalleCategoria> lstAplicativos,
    	    List<AccionDetalleCategoria> lstAccionesAplicativos,
    	    List<DetalleCategoria> lstSAP,
    	    List<AccionDetalleCategoria> lstAccionesSAP,
    	    List<DetalleCategoria> lstSolicitudEquipo,
    	    List<AccionDetalleCategoria> lstAccionesSolicitudEquipo,
    	    List<DetalleCategoria> lstTraslado,
    	    String filterTiposArchivo,
    	    String msg_filterTiposArchivo,
    	    Long cnt_max_reg,
    	    String msg_resolucion_altas,
    	    String msg_registro_tickets,
    	    String msg_ws_integracion_inactivo,
    	    String msg_aprobacion_jefe,
    	    String idEstadoAprobado,
    	    String idEstadoDesaprobado,
    	    String idEstadoEnaprobacion,
    	    String idEstadoAnulado,
    	    String idWebIntegracion,
    	    String idCategoriaAccesoAPL, 
    	    String idCategoriaAccesoTIC,
    	    String idEstadoIntegracion,
    	    String idTipoRequerimiento, 
    	    String idUrgencia,
    	    String idOrigen,
    	    String idProductoMicroInfo,
    	    UsuarioAfectado[] lstAprobadoresEquipo,
    	    String nota1TIC,
    	    String nota2TIC,
    	    String nota1Aplicaciones,
    	    String nota2Aplicaciones,
    	    String htmlRegistroSolicitante,
    	    String htmlRegistroAprobador,
    	    String htmlAprobaSolicitante,
    	    String htmlDesaprobaSolicitante,
    	    String cabhtmlContratista,
    	    String cabhtmlSolicitante,
    	    String cabhtmlRevisor,
    	    String dethtmlContratistaRECTIC,
    	    String dethtmlContratistaAPLIC,
    	    String dethtmlContratistaSAP,
    	    String dethtmlContratistaTRAS,
    	    String dethtmlSolicitanteRECTIC,
    	    String dethtmlSolicitanteAPLIC,
    	    String dethtmlSolicitanteSAP,
    	    String dethtmlSolicitanteTRAS,
    	    String dethtmlRevisorRECTIC,
    	    String dethtmlRevisorAPLIC,
    	    String dethtmlRevisorSAP,
    	    String dethtmlRevisorTRAS,
    	    String urlServicioContratista,
    	    List<Revisor> revisores,
    	    List<DetalleCategoria> lstTipoRequerimiento,
    	    Long n_tiempo_espera,
    	    List<Sedes> lstSedes,
    	    List<Area> lstAreas,
    	    List<Situacion> lstSituacion,
    	    String mensajeErrorGenerico,
    		String indAdjArchivoNotif,
    		String indEnvNotifContra,
    		String indActServWebInteg,
    		List<DetalleCategoria> lstMsjAprobDesaprob,
    		List<DetalleCategoria> lstFormatoCorreoCab,
    		String indVisNroSoliNotif,
    		List<DetalleCategoria> lstValGenTicket) {    		
        this.cnt_max_MB = cnt_max_MB;
        this.msg_max_MB = msg_max_MB;
        this.lstRecursosTIC = lstRecursosTIC;
        this.lstAccionesRecursosTIC = lstAccionesRecursosTIC;
        this.lstAplicativos = lstAplicativos;
        this.lstAccionesAplicativos = lstAccionesAplicativos;
	    this.lstSAP = lstSAP;
	    this.lstAccionesSAP = lstAccionesSAP;
	    
	    this.lstSolicitudEquipo = lstSolicitudEquipo;
	    this.lstAccionesSolicitudEquipo = lstAccionesSolicitudEquipo;
        this.lstTraslado = lstTraslado;
        
        this.filterTiposArchivo = filterTiposArchivo;
        this.msg_filterTiposArchivo = msg_filterTiposArchivo;
        this.cnt_max_reg = cnt_max_reg;
        this.msg_resolucion_altas = msg_resolucion_altas;
        this.msg_registro_tickets = msg_registro_tickets;
        this.msg_ws_integracion_inactivo = msg_ws_integracion_inactivo;
        this.msg_aprobacion_jefe = msg_aprobacion_jefe;
        
        this.idEstadoAprobado = idEstadoAprobado;
        this.idEstadoDesaprobado = idEstadoDesaprobado;
        this.idEstadoEnaprobacion = idEstadoEnaprobacion;
        this.idEstadoAnulado = idEstadoAnulado;
        this.idWebIntegracion = idWebIntegracion;
        this.idCategoriaAccesoAPL = idCategoriaAccesoAPL;
        this.idCategoriaAccesoTIC = idCategoriaAccesoTIC;
        this.idEstadoIntegracion = idEstadoIntegracion;
        this.idTipoRequerimiento = idTipoRequerimiento;
        this.idUrgencia = idUrgencia;
        this.idOrigen = idOrigen;
        this.idProductoMicroInfo = idProductoMicroInfo;
        this.lstAprobadoresEquipo = lstAprobadoresEquipo;
        this.nota1TIC = nota1TIC;
        this.nota2TIC = nota2TIC;
        this.nota1Aplicaciones = nota1Aplicaciones;
        this.nota2Aplicaciones = nota2Aplicaciones;
        this.htmlRegistroSolicitante = htmlRegistroSolicitante;
        this.htmlRegistroAprobador = htmlRegistroAprobador;
        this.htmlAprobaSolicitante = htmlAprobaSolicitante;
        this.htmlDesaprobaSolicitante = htmlDesaprobaSolicitante;
        this.cabhtmlContratista = cabhtmlContratista;        
        this.dethtmlContratistaRECTIC = dethtmlContratistaRECTIC;
        this.dethtmlContratistaAPLIC = dethtmlContratistaAPLIC;
        this.dethtmlContratistaTRAS = dethtmlContratistaTRAS;        
        this.cabhtmlSolicitante = cabhtmlSolicitante;        
        this.dethtmlSolicitanteRECTIC = dethtmlSolicitanteRECTIC;
        this.dethtmlSolicitanteAPLIC = dethtmlSolicitanteAPLIC;
        this.dethtmlSolicitanteSAP = dethtmlSolicitanteSAP;
        this.dethtmlSolicitanteTRAS = dethtmlSolicitanteTRAS;        
        this.cabhtmlRevisor = cabhtmlRevisor;        
        this.dethtmlRevisorRECTIC = dethtmlRevisorRECTIC;
        this.dethtmlRevisorAPLIC = dethtmlRevisorAPLIC;
        this.dethtmlRevisorAPLIC = dethtmlRevisorTRAS;   
        this.urlServicioContratista = urlServicioContratista;
        this.revisores = revisores;
        this.lstTipoRequerimiento = lstTipoRequerimiento;
        this.n_tiempo_espera = n_tiempo_espera;
        this.lstSedes = lstSedes;
        this.lstAreas = lstAreas;
        this.lstSituacion = lstSituacion;
        this.mensajeErrorGenerico = mensajeErrorGenerico;
    	this.indAdjArchivoNotif = indAdjArchivoNotif;
    	this.indEnvNotifContra = indEnvNotifContra;
    	this.indActServWebInteg = indActServWebInteg;
    	this.lstMsjAprobDesaprob = lstMsjAprobDesaprob;
    	this.lstFormatoCorreoCab = lstFormatoCorreoCab;
    	this.indVisNroSoliNotif = indVisNroSoliNotif;
    	this.lstValGenTicket = lstValGenTicket;
    }    
    
    public List<DetalleCategoria> getLstSolicitudEquipo() {
		return lstSolicitudEquipo;
	}

	public void setLstSolicitudEquipo(List<DetalleCategoria> lstSolicitudEquipo) {
		this.lstSolicitudEquipo = lstSolicitudEquipo;
	}

	public List<AccionDetalleCategoria> getLstAccionesSolicitudEquipo() {
		return lstAccionesSolicitudEquipo;
	}

	public void setLstAccionesSolicitudEquipo(List<AccionDetalleCategoria> lstAccionesSolicitudEquipo) {
		this.lstAccionesSolicitudEquipo = lstAccionesSolicitudEquipo;
	}

	public String getDethtmlContratistaSAP() {
		return dethtmlContratistaSAP;
	}

	public void setDethtmlContratistaSAP(String dethtmlContratistaSAP) {
		this.dethtmlContratistaSAP = dethtmlContratistaSAP;
	}

	public String getDethtmlSolicitanteSAP() {
		return dethtmlSolicitanteSAP;
	}

	public void setDethtmlSolicitanteSAP(String dethtmlSolicitanteSAP) {
		this.dethtmlSolicitanteSAP = dethtmlSolicitanteSAP;
	}

	public String getDethtmlRevisorSAP() {
		return dethtmlRevisorSAP;
	}

	public void setDethtmlRevisorSAP(String dethtmlRevisorSAP) {
		this.dethtmlRevisorSAP = dethtmlRevisorSAP;
	}

	public List<DetalleCategoria> getLstSAP() {
		return lstSAP;
	}

	public void setLstSAP(List<DetalleCategoria> lstSAP) {
		this.lstSAP = lstSAP;
	}

	public List<AccionDetalleCategoria> getLstAccionesSAP() {
		return lstAccionesSAP;
	}

	public void setLstAccionesSAP(List<AccionDetalleCategoria> lstAccionesSAP) {
		this.lstAccionesSAP = lstAccionesSAP;
	}

    public List<Area> getLstAreas() {
		return lstAreas;
	}

	public void setLstAreas(List<Area> lstAreas) {
		this.lstAreas = lstAreas;
	}    
    
	public Long getN_tiempo_espera() {
		return n_tiempo_espera;
	}

	public void setN_tiempo_espera(Long n_tiempo_espera) {
		this.n_tiempo_espera = n_tiempo_espera;
	}
    
	public Long getCnt_max_MB() {
		return cnt_max_MB;
	}

	public void setCnt_max_MB(Long cnt_max_MB) {
		this.cnt_max_MB = cnt_max_MB;
	}

    public String getMsg_max_MB() {
		return msg_max_MB;
	}

	public void setMsg_max_MB(String msg_max_MB) {
		this.msg_max_MB = msg_max_MB;
	}	

    public List<Revisor> getRevisores() {
		return revisores;
	}

	public void setRevisores(List<Revisor> revisores) {
		this.revisores = revisores;
	}	

    public List<Sedes> getLstSedes() {
		return lstSedes;
	}

	public void setLstSedes(List<Sedes> lstSedes) {
		this.lstSedes =  lstSedes;
	}	
	
	
    public String getMsg_resolucion_altas() {
		return msg_resolucion_altas;
	}

	public void setMsg_resolucion_altas(String msg_resolucion_altas) {
		this.msg_resolucion_altas = msg_resolucion_altas;
	}	
	
    public String getMsg_filterTiposArchivo() {
		return msg_filterTiposArchivo;
	}

	public void setMsg_filterTiposArchivo(String msg_filterTiposArchivo) {
		this.msg_filterTiposArchivo = msg_filterTiposArchivo;
	}

    public String getFilterTiposArchivo() {
		return filterTiposArchivo;
	}

	public void setFilterTiposArchivo(String filterTiposArchivo) {
		this.filterTiposArchivo = filterTiposArchivo;
	}
	
	public List<DetalleCategoria> getLstRecursosTIC() {
		return lstRecursosTIC;
	}

	public void setLstRecursosTIC(List<DetalleCategoria> lstRecursosTIC) {
		this.lstRecursosTIC = lstRecursosTIC;
	}

	public List<DetalleCategoria> getLstAplicativos() {
		return lstAplicativos;
	}

	public void setLstAplicativos(List<DetalleCategoria> lstAplicativos) {
		this.lstAplicativos = lstAplicativos;
	}

	public List<DetalleCategoria> getLstTraslado() {
		return lstTraslado;
	}

	public void setLstTraslado(List<DetalleCategoria> lstTraslado) {
		this.lstTraslado = lstTraslado;
	}
	
	public Long getCnt_max_reg() {
		return cnt_max_reg;
	}

	public void setCnt_max_reg(Long cnt_max_reg) {
		this.cnt_max_reg = cnt_max_reg;
	}

	public String getIdEstadoAprobado() {
		return idEstadoAprobado;
	}

	public void setIdEstadoAprobado(String idEstadoAprobado) {
		this.idEstadoAprobado = idEstadoAprobado;
	}

	public String getIdEstadoDesaprobado() {
		return idEstadoDesaprobado;
	}

	public void setIdEstadoDesaprobado(String idEstadoDesaprobado) {
		this.idEstadoDesaprobado = idEstadoDesaprobado;
	}

	public String getIdEstadoEnaprobacion() {
		return idEstadoEnaprobacion;
	}

	public void setIdEstadoEnaprobacion(String idEstadoEnaprobacion) {
		this.idEstadoEnaprobacion = idEstadoEnaprobacion;
	}

	public String getIdEstadoAnulado() {
		return idEstadoAnulado;
	}

	public void setIdEstadoAnulado(String idEstadoAnulado) {
		this.idEstadoAnulado = idEstadoAnulado;
	}

	public String getIdWebIntegracion() {
		return idWebIntegracion;
	}

	public void setIdWebIntegracion(String idWebIntegracion) {
		this.idWebIntegracion = idWebIntegracion;
	}

	public String getIdCategoriaAccesoAPL() {
		return idCategoriaAccesoAPL;
	}

	public void setIdCategoriaAccesoAPL(String idCategoriaAccesoAPL) {
		this.idCategoriaAccesoAPL = idCategoriaAccesoAPL;
	}

	public String getIdCategoriaAccesoTIC() {
		return idCategoriaAccesoTIC;
	}

	public void setIdCategoriaAccesoTIC(String idCategoriaAccesoTIC) {
		this.idCategoriaAccesoTIC = idCategoriaAccesoTIC;
	}

	public String getIdEstadoIntegracion() {
		return idEstadoIntegracion;
	}

	public void setIdEstadoIntegracion(String idEstadoIntegracion) {
		this.idEstadoIntegracion = idEstadoIntegracion;
	}

	public String getIdTipoRequerimiento() {
		return idTipoRequerimiento;
	}

	public void setIdTipoRequerimiento(String idTipoRequerimiento) {
		this.idTipoRequerimiento = idTipoRequerimiento;
	}

	public String getIdUrgencia() {
		return idUrgencia;
	}

	public void setIdUrgencia(String idUrgencia) {
		this.idUrgencia = idUrgencia;
	}

	public String getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(String idOrigen) {
		this.idOrigen = idOrigen;
	}

	public String getIdProductoMicroInfo() {
		return idProductoMicroInfo;
	}

	public void setIdProductoMicroInfo(String idProductoMicroInfo) {
		this.idProductoMicroInfo = idProductoMicroInfo;
	}

	public UsuarioAfectado[] getLstAprobadoresEquipo() {
		return lstAprobadoresEquipo;
	}

	public void setLstAprobadoresEquipo(UsuarioAfectado[] lstAprobadoresEquipo) {
		this.lstAprobadoresEquipo = lstAprobadoresEquipo;
	}

	public String getNota1TIC() {
		return nota1TIC;
	}

	public void setNota1TIC(String nota1tic) {
		nota1TIC = nota1tic;
	}

	public String getNota2TIC() {
		return nota2TIC;
	}

	public void setNota2TIC(String nota2tic) {
		nota2TIC = nota2tic;
	}

	public String getNota1Aplicaciones() {
		return nota1Aplicaciones;
	}

	public void setNota1Aplicaciones(String nota1Aplicaciones) {
		this.nota1Aplicaciones = nota1Aplicaciones;
	}

	public String getNota2Aplicaciones() {
		return nota2Aplicaciones;
	}

	public void setNota2Aplicaciones(String nota2Aplicaciones) {
		this.nota2Aplicaciones = nota2Aplicaciones;
	}

	public String getHtmlRegistroSolicitante() {
		return htmlRegistroSolicitante;
	}

	public void setHtmlRegistroSolicitante(String htmlRegistroSolicitante) {
		this.htmlRegistroSolicitante = htmlRegistroSolicitante;
	}

	public String getHtmlRegistroAprobador() {
		return htmlRegistroAprobador;
	}

	public void setHtmlRegistroAprobador(String htmlRegistroAprobador) {
		this.htmlRegistroAprobador = htmlRegistroAprobador;
	}

	public String getHtmlAprobaSolicitante() {
		return htmlAprobaSolicitante;
	}

	public void setHtmlAprobaSolicitante(String htmlAprobaSolicitante) {
		this.htmlAprobaSolicitante = htmlAprobaSolicitante;
	}

	public String getHtmlDesaprobaSolicitante() {
		return htmlDesaprobaSolicitante;
	}

	public void setHtmlDesaprobaSolicitante(String htmlDesaprobaSolicitante) {
		this.htmlDesaprobaSolicitante = htmlDesaprobaSolicitante;
	}

	public String getCabhtmlContratista() {
		return cabhtmlContratista;
	}

	public void setCabhtmlContratista(String cabhtmlContratista) {
		this.cabhtmlContratista = cabhtmlContratista;
	}

	public String getDethtmlContratistaRECTIC() {
		return dethtmlContratistaRECTIC;
	}

	public void setDethtmlContratistaRECTIC(String dethtmlContratistaRECTIC) {
		this.dethtmlContratistaRECTIC = dethtmlContratistaRECTIC;
	}

	public String getDethtmlContratistaAPLIC() {
		return dethtmlContratistaAPLIC;
	}

	public void setDethtmlContratistaAPLIC(String dethtmlContratistaAPLIC) {
		this.dethtmlContratistaAPLIC = dethtmlContratistaAPLIC;
	}	
	
	public String getCabhtmlSolicitante() {
		return cabhtmlSolicitante;
	}

	public void setCabhtmlSolicitante(String cabhtmlSolicitante) {
		this.cabhtmlSolicitante = cabhtmlSolicitante;
	}

	public String getDethtmlSolicitanteRECTIC() {
		return dethtmlSolicitanteRECTIC;
	}

	public void setDethtmlSolicitanteRECTIC(String dethtmlSolicitanteRECTIC) {
		this.dethtmlSolicitanteRECTIC = dethtmlSolicitanteRECTIC;
	}

	public String getDethtmlSolicitanteAPLIC() {
		return dethtmlSolicitanteAPLIC;
	}

	public void setDethtmlSolicitanteAPLIC(String dethtmlSolicitanteAPLIC) {
		this.dethtmlSolicitanteAPLIC = dethtmlSolicitanteAPLIC;
	}	

	public String getCabhtmlRevisor() {
		return cabhtmlRevisor;
	}

	public void setCabhtmlRevisor(String cabhtmlRevisor) {
		this.cabhtmlRevisor = cabhtmlRevisor;
	}

	public String getDethtmlRevisorRECTIC() {
		return dethtmlRevisorRECTIC;
	}

	public void setDethtmlRevisorRECTIC(String dethtmlRevisorRECTIC) {
		this.dethtmlRevisorRECTIC = dethtmlRevisorRECTIC;
	}

	public String getDethtmlRevisorAPLIC() {
		return dethtmlRevisorAPLIC;
	}

	public void setDethtmlRevisorAPLIC(String dethtmlRevisorAPLIC) {
		this.dethtmlRevisorAPLIC = dethtmlRevisorAPLIC;
	}	
	
	public String getUrlServicioContratista() {
		return urlServicioContratista;
	}

	public void setUrlServicioContratista(String urlServicioContratista) {
		this.urlServicioContratista = urlServicioContratista;
	}
	
    public List<AccionDetalleCategoria> getLstAccionesRecursosTIC() {
		return lstAccionesRecursosTIC;
	}

	public void setLstAccionesRecursosTIC(List<AccionDetalleCategoria> lstAccionesRecursosTIC) {
		this.lstAccionesRecursosTIC = lstAccionesRecursosTIC;
	}

	public List<AccionDetalleCategoria> getLstAccionesAplicativos() {
		return lstAccionesAplicativos;
	}

	public void setLstAccionesAplicativos(List<AccionDetalleCategoria> lstAccionesAplicativos) {
		this.lstAccionesAplicativos = lstAccionesAplicativos;
	}
	
	public List<DetalleCategoria> getLstTipoRequerimiento() {
		return lstTipoRequerimiento;
	}

	public void setLstTipoRequerimiento(List<DetalleCategoria> lstTipoRequerimiento) {
		this.lstTipoRequerimiento = lstTipoRequerimiento;
	}

    public List<Situacion> getLstSituacion() {
		return lstSituacion;
	}

	public void setLstSituacion(List<Situacion> lstSituacion) {
		this.lstSituacion = lstSituacion;
	}
	
    public String getDethtmlContratistaTRAS() {
		return dethtmlContratistaTRAS;
	}

	public void setDethtmlContratistaTRAS(String dethtmlContratistaTRAS) {
		this.dethtmlContratistaTRAS = dethtmlContratistaTRAS;
	}

	public String getDethtmlSolicitanteTRAS() {
		return dethtmlSolicitanteTRAS;
	}

	public void setDethtmlSolicitanteTRAS(String dethtmlSolicitanteTRAS) {
		this.dethtmlSolicitanteTRAS = dethtmlSolicitanteTRAS;
	}

	public String getDethtmlRevisorTRAS() {
		return dethtmlRevisorTRAS;
	}

	public void setDethtmlRevisorTRAS(String dethtmlRevisorTRAS) {
		this.dethtmlRevisorTRAS = dethtmlRevisorTRAS;
	}
	
	public String getMensajeErrorGenerico() {
		return mensajeErrorGenerico;
	}

	public void setMensajeErrorGenerico(String mensajeErrorGenerico) {
		this.mensajeErrorGenerico = mensajeErrorGenerico;
	}

	public String getIndAdjArchivoNotif() {
		return indAdjArchivoNotif;
	}

	public void setIndAdjArchivoNotif(String indAdjArchivoNotif) {
		this.indAdjArchivoNotif = indAdjArchivoNotif;
	}

	public String getIndEnvNotifContra() {
		return indEnvNotifContra;
	}

	public void setIndEnvNotifContra(String indEnvNotifContra) {
		this.indEnvNotifContra = indEnvNotifContra;
	}

	public String getIndActServWebInteg() {
		return indActServWebInteg;
	}

	public void setIndActServWebInteg(String indActServWebInteg) {
		this.indActServWebInteg = indActServWebInteg;
	}

	public List<DetalleCategoria> getLstMsjAprobDesaprob() {
		return lstMsjAprobDesaprob;
	}

	public void setLstMsjAprobDesaprob(List<DetalleCategoria> lstMsjAprobDesaprob) {
		this.lstMsjAprobDesaprob = lstMsjAprobDesaprob;
	}

	public List<DetalleCategoria> getLstFormatoCorreoCab() {
		return lstFormatoCorreoCab;
	}

	public void setLstFormatoCorreoCab(List<DetalleCategoria> lstFormatoCorreoCab) {
		this.lstFormatoCorreoCab = lstFormatoCorreoCab;
	}
	
	public String getIndVisNroSoliNotif() {
		return indVisNroSoliNotif;
	}

	public void setIndVisNroSoliNotif(String indVisNroSoliNotif) {
		this.indVisNroSoliNotif = indVisNroSoliNotif;
	}

	public List<DetalleCategoria> getLstValGenTicket() {
		return lstValGenTicket;
	}

	public void setLstValGenTicket(List<DetalleCategoria> lstValGenTicket) {
		this.lstValGenTicket = lstValGenTicket;
	}
	
	public List<DetalleCategoria> getLstParametros() {
		return lstParametros;
	}

	public void setLstParametros(List<DetalleCategoria> lstParametros) {
		this.lstParametros = lstParametros;
	}

	public String getMsg_registro_tickets() {
		return msg_registro_tickets;
	}

	public void setMsg_registro_tickets(String msg_registro_tickets) {
		this.msg_registro_tickets = msg_registro_tickets;
	}

	public String getMsg_ws_integracion_inactivo() {
		return msg_ws_integracion_inactivo;
	}

	public void setMsg_ws_integracion_inactivo(String msg_ws_integracion_inactivo) {
		this.msg_ws_integracion_inactivo = msg_ws_integracion_inactivo;
	}

	public String getMsg_aprobacion_jefe() {
		return msg_aprobacion_jefe;
	}

	public void setMsg_aprobacion_jefe(String msg_aprobacion_jefe) {
		this.msg_aprobacion_jefe = msg_aprobacion_jefe;
	}
}
