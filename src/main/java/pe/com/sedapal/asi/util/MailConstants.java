package pe.com.sedapal.asi.util;

public class MailConstants {
	
	private MailConstants() {}
	
	// SERVICIO CORREO URL
	public static final Integer PARAM_URL_WS_CORREO = 77;
	public static final Integer PARAM_DT_URL_WS_CORREO = 257;
	
	// TIPO ENVIO CORREO
	public static final Integer PARAM_TIPO_ENVIO_CORREO = 79;
	public static final Integer PARAM_DT_TIPO_ENVIO_CORREO = 259;
	
	// REMITENTE
	public static final Integer PARAM_CORREO_REMITENTE = 32;
	public static final Integer PARAM_DT_CORREO_REMITENTE = 591;
	public static final Integer PARAM_CLAVE_CORREO_REMITENTE = 69;
	public static final Integer PARAM_DT_CLAVE_CORREO_REMITENTE = 250;
	public static final Integer PARAM_NOMBRE_REMITENTE = 72;
	public static final Integer PARAM_DT_NOMBRE_REMITENTE = 622;

	// INTEGRACION WS CONTRATISTA
	public static final Integer PARAM_IND_ACTIVACION_WS_INTEGRACION = 29;
	public static final Integer PARAM_DT_IND_ACTIVACION_WS_INTEGRACION = 589;
	public static final Integer PARAM_URL_WS_CONTRATISTA = 39;
	public static final Integer PARAM_DT_URL_WS_CONTRATISTA = 596;
	
	// CONTRATISTA
	public static final Integer PARAM_IND_ENVIO_NOTIFICACION_CONTRATISTA = 35;
	public static final Integer PARAM_DT_IND_ENVIO_NOTIFICACION_CONTRATISTA = 594;
	public static final Integer PARAM_CORREO_RECEPTOR_CONTRATISTA = 33;
	public static final Integer PARAM_DT_CORREO_RECEPTOR_CONTRATISTA = 592;
	public static final Integer PARAM_NOMBRE_RECEPTOR_CONTRATISTA = 73;
	public static final Integer PARAM_DT_NOMBRE_RECEPTOR_CONTRATISTA = 623;
	
	public static final Integer PARAM_IND_VISUAL_NRO_SOLICITUD = 36;
	public static final Integer PARAM_DT_IND_VISUAL_NRO_SOLICITUD = 595;
	
	public static final Integer PARAM_CARGO_JEFE = 47;
	public static final Integer PARAM_CARGO_GERENTE = 48;
	public static final Integer PARAM_CARGO_SECRETARIA_GERENCIAS = 49;
	public static final Integer PARAM_CARGO_SECRETARIA_GERENCIA_GENERAL = 61;
	public static final Integer PARAM_CARGO_GERENTE_GENERAL = 62;
	public static final Integer PARAM_CARGO_SECRETARIA_EQUIPOS = 63;
	
	public static final Integer PARAM_FORMATO_CORREO_CAB = 64;
	public static final Integer PARAM_DT_FORMATO_CORREO_CAB = 572;
	public static final Integer PARAM_DT_FORMATO_CORREO_CAB_CONTRATISTA = 621;
	
	public static final Integer PARAM_FORMATO_CORREO_DET = 65;
	public static final Integer PARAM_DT_FORMATO_CORREO_DET_RECURSOSTIC = 579;
	public static final Integer PARAM_DT_FORMATO_CORREO_DET_APLICACIONES = 582;
	public static final Integer PARAM_DT_FORMATO_CORREO_DET_TRASLADOS = 604;
	public static final Integer PARAM_DT_FORMATO_CORREO_DET_SAP = 610;
	public static final Integer PARAM_DT_FORMATO_CORREO_DET_SOLICITUD_EQUIPOS = 619;
	
	// MENSAJE INTRODUCTORIO CORREOS
	public static final Integer PARAM_MSJ_INTRO = 70;
	public static final Integer PARAM_DT_MSJ_INTRO_PARA_SOLICITANTE = 227;
	public static final Integer PARAM_DT_MSJ_INTRO_PARA_REVISOR = 228;
	public static final Integer PARAM_DT_MSJ_INTRO_PARA_CONTRATISTA = 253;
	
	// ASUNTO DE CORREOS
	public static final Integer PARAM_ASUNTO = 30;
	public static final Integer PARAM_DT_ASUNTO_SOLICITANTE = 222;
	public static final Integer PARAM_DT_ASUNTO_CONTRATISTA = 224;
	public static final Integer PARAM_DT_ASUNTO_APROBADOR = 223;
	
	// USUARIO DE SEGURIDAD PARA ENVIO CORREO
	public static final Integer PARAM_CORREO_USUARIO_SEGURIDAD = 74;
	public static final Integer PARAM_DT_CORREO_USUARIO_SEGURIDAD = 254;
	
	public static final Integer PARAM_CORREO_CLAVE_USUARIO_SEGURIDAD = 75;
	public static final Integer PARAM_DT_CORREO_CLAVE_USUARIO_SEGURIDAD = 255;
	
	public static final Integer PARAM_FLAG_WS_INTEG_CONTRATISTA = 29;
	public static final Integer PARAM_DT_FLAG_WS_INTEG_CONTRATISTA = 589;
	
	public static final Integer PARAM_FLAG_URL_WS_INTEG_CONTRATISTA = 39;
	public static final Integer PARAM_DT_URL_WS_INTEG_CONTRATISTA = 596;
	
	public static final Integer PARAM_CORREO_CONTRATISTA = 33;
	public static final Integer PARAM_DT_CORREO_CONTRATISTA = 592;
	
	public static final Integer PARAM_FLAG_ADJUNTAR_ARCH_NOTIFICACION = 34;
	public static final Integer PARAM_DT_FLAG_ADJUNTAR_ARCH_NOTIFICACION = 593;
	
	public static final Integer PARAM_FLAG_ENVIO_NOTIF_CONTRATISTA = 35;
	public static final Integer PARAM_DT_FLAG_ENVIO_NOTIF_CONTRATISTA = 594;
	
	// TICKET
	public static final Integer PARAM_TICKET = 22;
	public static final Integer PARAM_DT_TICKET_ESTADO_REQ = 75;
	public static final Integer PARAM_DT_TICKET_TIPO_REQ = 76;
	public static final Integer PARAM_DT_TICKET_URGENCIA = 77;
	public static final Integer PARAM_DT_TICKET_ORIGEN = 78;
	public static final Integer PARAM_DT_TICKET_PRODUCTO_MICROINFORM = 79;
	public static final Integer PARAM_DT_TICKET_IMPACTO = 467;
	public static final Integer PARAM_DT_TICKET_PRIORIDAD_INCID = 468;
	public static final Integer PARAM_DT_TICKET_PRIORIDAD_REQ = 469;
	public static final Integer PARAM_DT_TICKET_ESTADO_INCIDENTE = 470;
	public static final Integer PARAM_DT_TICKET_TIPO_INCIDENTE = 471;
	public static final Integer PARAM_DT_TICKET_PRODUCTO_TELECOMUNIC = 472;
	
	// SALUDO
	public static final Integer PARAM_SALUDO = 71;
	public static final Integer PARAM_DT_SALUDO = 620;
	
	// FILE SERVER
	public static final Integer PARAM_IND_ADJUNTAR_ARCHIVO = 34;
	public static final Integer PARAM_DT_IND_ADJUNTAR_ARCHIVO = 593;
	public static final Integer PARAM_CARPETA_FILE_SERVER = 50;
	public static final Integer PARAM_DT_CARPETA_FILE_SERVER = 598;
	public static final Integer PARAM_CANT_MAX_MB_POR_SOLICITUD = 31;
	public static final Integer PARAM_DT_CANT_MAX_MB_POR_SOLICITUD = 590;
	
	// MESA DE AYUDA
	public static final Integer PARAM_FLAG_COPIAR_CONTRATISTA = 85;
	public static final Integer PARAM_DET_FLAG_COPIAR_CONTRATISTA = 277;
	
	// CAMOS FORMATOS CABECERA HTML
	public static final String MAIL_CAB_FECHA_CREA_REQ = "fechacreacionreq";
	public static final String MAIL_CAB_USUARIO = "usuario";
	public static final String MAIL_CAB_NOMBRES = "nombres";
	public static final String MAIL_CAB_USUARIO_SOLI = "usuariosolic";
	public static final String MAIL_CAB_NOMBRE_SOLI = "nombressolic";
	public static final String MAIL_CAB_CARGO_SOLI = "cargosolic";
	public static final String MAIL_CAB_SEDE = "sede";
	public static final String MAIL_CAB_NOMBRE_GERENCIA = "nomgerencia";
	public static final String MAIL_CAB_NOMBRE_EQUIPO = "nomequipo";
	public static final String MAIL_CAB_CORREO = "correo";
	public static final String MAIL_CAB_FICHA_DNI = "fichadni";
	public static final String MAIL_CAB_TELEFONO = "telefonoanexo";
	public static final String MAIL_CAB_MSG_INTRO = "msjintroduccion";
	public static final String MAIL_CAB_LISTA_APROBADORES = "listaaprobadores";
	public static final String MAIL_CAB_NRO_SOLI = "numerosolicitud";
	public static final String MAIL_CAB_MOSTRAR_NRO_SOLI = "mostrarsolicitud";
	public static final String MAIL_CAB_MOSTRAR_URL_REQ = "mostrarurlreq";
	public static final String MAIL_CAB_URL_REQUERIMIENTO = "urlrequerimiento";
	public static final String MAIL_CAB_APROBADO_POR = "aprobadopor";
	
	// CAMPOS FORMATOS DETALLE HTML
	public static final String MAIL_DET_TICKET = "ticket";
	public static final String MAIL_DET_USUAFEC = "usuarioafectado";
	public static final String MAIL_DET_UFICHA = "uficha";
	public static final String MAIL_DET_USITUAC = "usituacion";
	public static final String MAIL_DET_UEQUIPO = "uequipo";
	public static final String MAIL_DET_UTELEFON = "utelefono";
	public static final String MAIL_DET_RECURSOTIC = "recursotic";
	public static final String MAIL_DET_ACCION = "accion";
	public static final String MAIL_DET_CUENTARED = "cuentared";
	public static final String MAIL_DET_COD_INVENTAR = "codigoinventario";
	public static final String MAIL_DET_FECH_FIN_VIGEN = "fechafinvigencia";
	public static final String MAIL_DET_OBSERV = "observaciones";
	public static final String MAIL_DET_ARCH_ADJUN = "archivoadjunto";
	public static final String MAIL_DET_NOMSISTEMA = "nombresistema";
	public static final String MAIL_DET_PERFILTRANS = "perfiltransaccion";
	public static final String MAIL_DET_SUSTENTO = "sustento";
	public static final String MAIL_DET_FECHAFINVIGEN = "fechafinvigencia";
	public static final String MAIL_CAB_NROTICKET = "nroticket";
	
	public static final Integer MAIL_DET_CLAVE_CORREO_REMITENTE = 69;
	public static final Integer MAIL_DET_CORREO_REMITENTE = 32;
	public static final Integer MAIL_DET_CORREO_CONTRATISTA = 33;
	public static final Integer MAIL_DET_NOMBRE_REMITENTE_CORREO = 72;
	public static final Integer MAIL_DET_NOMBRE_CONTRATISTA_CORREO = 73;
	
	// TIPO USUARIO A QUIEN SE ENVIA CORREO
	public static final String USUARIO_SOLICITANTE = "SOLICITANTE";
	public static final String USUARIO_CONTRATISTA = "CONTRATISTA";
	public static final String USUARIO_REVISOR = "REVISOR";
	public static final String MAIL_ALTA_SECRETARIA = "<b>Alta de Nuevos Jefes Gerentes y Directivos</b>: SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Resoluci&oacute;n de Nuevo Personal:</b> ";
	public static final String MAIL_RUTA_BANDEJA_APROBACION = "/bandeja/aprobacion?soli=123456&token=eyJhbGciOiJiJ9(EN CONSTRUCCION)";
	
	// MENSAJES DE APROBACION POR DEFECTO
	public static final String MAIL_MSJ_APROBADO_POR = "No requiere aprobaci&oacute;n.";
	
	// URL ASI WEBAPP
	public static final Integer PARAM_URL_ASI_WEBAPP = 86;
	public static final Integer PARAM_DT_URL_ASI_WEBAPP = 278;
}
