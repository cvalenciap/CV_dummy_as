package pe.com.sedapal.asi.util;

public class SessionConstants {
	
	/*Constantes de Seguridad*/
	
	public static final String SUCCESS = "1";
	public static final String FAILURE = "0";
	
	public static final int ESTADO_ACTIVO = 1;
	public static final int ESTADO_INACTIVO = 0;
	
	
	public static final String URL_LOGIN = "/auth/login";
	public static final String URL_LOGOUT = "/auth/logout";
	public static final String URL_APP_INFO = "/api/";
	
	
	public static final String REDIS_PREFIX_USERS = "TOKEN";
	public static final String REDIS_KEYS_SEPARATOR = ":";
	
	
	/*Constantes Permisos para opciones*/
	/*Configuracion*/
	public static final String OPCION_CONFIG = "/configuracion";
	public static final String OPCION_CONFIG_ASUNTOS = "/configuracion/asuntos";
	public static final String OPCION_CONFIG_EMPRESAS = "/configuracion/empresas";
	public static final String OPCION_CONFIG_FERIADOS = "/configuracion/feriados";
	public static final String OPCION_CONFIG_TIPOS_DOC = "/configuracion/tipos-dcoumentos";
	
	/*Despacho*/
	public static final String OPCION_DESPACHO = "/despacho";
	public static final String OPCION_DESPACHO_GRUPOS = "/despacho/grupos";
	public static final String OPCION_DESPACHO_JEFE_EQUIPO = "/despacho/jefe-equipo";
	public static final String OPCION_DESPACHO_SECUENCIALES = "/despacho/secuenciales";
	
	/*Mesa de partes*/
	public static final String OPCION_MESA_PARTES = "/mesa-partes";
	public static final String OPCION_MESA_PARTES_ADJUNTAR = "/mesa-partes/adjuntar";
	public static final String OPCION_MESA_PARTES_ELIMINAR = "/mesa-partes/eliminar";
	public static final String OPCION_MESA_PARTES_REGISTRAR = "/mesa-partes/registrar";
	
	/*Bandeja entrada*/
	public static final String OPCION_BANDEJA_ENT = "/bandeja-entrada";
	public static final String OPCION_BANDEJA_ENT_AREA = "/bandeja-entrada/consultar-area";
	public static final String OPCION_BANDEJA_ENT_ELIMINAR = "/bandeja-entrada/eliminar";
	public static final String OPCION_BANDEJA_ENT_REGISTRAR = "/bandeja-entrada/registrar";
	public static final String OPCION_BANDEJA_ENT_SEG = "/bandeja-entrada/seguimiento";
	public static final String OPCION_BANDEJA_ENT_SEG_ATENDER = "/bandeja-entrada/seguimiento/atender";
	public static final String OPCION_BANDEJA_ENT_SEG_CONSULTA_AREA = "/bandeja-entrada/seguimiento/consultar-area";
	public static final String OPCION_BANDEJA_ENT_SEG_ELIMINAR = "/bandeja-entrada/seguimiento/eliminar";
	public static final String OPCION_BANDEJA_ENT_SEG_ELIMINAR_AREA = "/bandeja-entrada/seguimiento/eliminar-area";
	public static final String OPCION_BANDEJA_ENT_SEG_ENVIAR = "/bandeja-entrada/seguimiento/enviar";

	/*Bandeja salida*/
	public static final String OPCION_BANDEJA_SAL = "/bandeja-salida";
	public static final String OPCION_BANDEJA_SAL_CON_AREA = "/bandeja-salida/consultar-area";
	public static final String OPCION_BANDEJA_SAL_CON_FIRMADO = "/bandeja-salida/consultar-firmados";
	public static final String OPCION_BANDEJA_SAL_FIRMAR = "/bandeja-salida/firmar";
	public static final String OPCION_BANDEJA_SAL_OBSERVAR = "/bandeja-salida/observar";
	public static final String OPCION_BANDEJA_SAL_REGISTRAR = "/bandeja-salida/registrar";
	public static final String OPCION_BANDEJA_SAL_VISAR = "/bandeja-salida/visar";
	
	/*Reportes*/
	public static final String OPCION_REPORTES = "/reportes-generales";
	public static final String OPCION_REPORTES_ATE_DOCU = "/reportes-generales/atencion-documentos	";
	public static final String OPCION_REPORTES_CON_AREA_FEC_EST = "/reportes-generales/consulta-area-fecha-estado";
	public static final String OPCION_REPORTES_CON_DOC_ASIG = "/reportes-generales/consulta-documentos-asignados";
	public static final String OPCION_REPORTES_CON_REG_ENTR = "/reportes-generales/consulta-registro-entrada";
	public static final String OPCION_REPORTES_CON_SEG = "/reportes-generales/consulta-seguimiento";
	public static final String OPCION_REPORTES_DOC_ENTRADA = "/reportes-generales";

	/* Constantes para acciones permitidas */
	public static final String ACCION_REGISTRAR = "REGISTRAR";
	public static final String ACCION_ADJUNTAR = "ADJUNTAR";
	public static final String ACCION_MODIFICAR = "MODIFICAR";
	public static final String ACCION_ELIMINAR = "ELIMINAR";
	public static final String ACCION_ENVIAR = "ENVIAR";
	public static final String ACCION_RESPONDER = "RESPONDER";
	public static final String ACCION_ATENDER = "ATENDER";
	public static final String ACCION_EXPORTAR = "EXPORTAR";
	public static final String ACCION_IMPRIMIR = "IMPRIMIR";
	public static final String ACCION_VISAR = "VISAR";
	public static final String ACCION_FIRMAR = "FIRMAR";
	public static final String ACCION_OBSERVAR = "OBSERVAR";

}
