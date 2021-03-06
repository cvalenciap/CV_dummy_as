package pe.com.sedapal.asi.util;

public class DBConstants {

	public static final Integer OK = 0;
	public static final Integer ERR_OPERACION = 1;
	public static final Integer ERR_SOLICITUD = 20;
	public static final Integer ERR_NO_CONTROLADO = 99;
	public static final String FECHA_DEFAULT_DESDE = "01/01/2000";
	public static final String FECHA_DEFAULT_HASTA = "31/12/2999";

	// Sistema
	public static final String PACKAGE_SISTEMA = "PCK_ARS_SISTEMA";
	public static final String PROCEDURE_PARAMETROS_SISTEMA = "PRC_SISTEMA_OBTENER_PARAM";
	public static final String PROCEDURE_INFORMACION_USUARIO = "PRC_USUARIO_OBTENER_INFO";
	public static final String PROCEDURE_USUARIOS_AFECTADOS = "PRC_CONS_USU_AFEC_SOLIC";
	
	public static final String PACKAGE_REGISTRO = "PCK_ARS_REGISTRO";
	public static final String PROCEDURE_REGISTRAR_SOLICITUD = "PRC_REGISTRAR_SOLICITUD";
	public static final String PROCEDURE_GUARDAR_ADJUNTOS = "PRC_GUARDAR_ARCHIVO_ADJUNTO";
	
	public static final String PACKAGE_BANDEJA = "PCK_ARS_BANDEJA";
	public static final String PROCEDURE_OBTENER_REQUERIMIENTOS = "PRC_OBTENER_REQUERIMIENTOS";
	public static final String PROCEDURE_REVISAR_SOLICITUD = "PRC_REVISAR_SOLICITUD";
	public static final String PROCEDURE_OBTENER_DET_REQ_REC_TIC = "PRC_OBTENER_DET_REQ_REC_TIC";
	public static final String PROCEDURE_OBTENER_DET_REQ_APLIC = "PRC_OBTENER_DET_REQ_APLIC";
	public static final String PACKAGE_ARS_MANTENIMIENTO = "PCK_ARS_MANTENIMIENTO";
	public static final String PROCEDURE_LISTAR_PARAMETROS_POR_TIPO = "PRC_CONS_LISTAR_PARAMS";
	public static final String PROCEDURE_DETALLES_PARAMETRO = "PRC_CONS_DETALLE_PARAM";
	public static final String PROCEDURE_OBTENER_PARAMETRO = "PRC_CONS_PARAM";
	public static final String PROCEDURE_OBTENER_REVISORES_AREAS = "PRC_OBT_REVIS_AREAS";
	
	public static final String PACKAGE_MANTENIMIENTO = "PCK_STD_MANTENIMIENTO";
	public static final String PROCEDURE_TIPODOC_OBTENER = "PRC_TIPODOC_OBTENER";
	public static final String PROCEDURE_TIPODOC_GUARDAR = "PRC_TIPODOC_GUARDAR";
	public static final String PROCEDURE_TIPODOC_ELIMINAR = "PRC_TIPODOC_ELIMINAR";
	public static final String PROCEDURE_FERIADO_OBTENER = "PRC_CALEN_OBTENER";
	public static final String PROCEDURE_FERIADO_GUARDAR = "PRC_CALEN_GUARDAR";
	public static final String PROCEDURE_FERIADO_ELIMINAR = "PRC_CALEN_ELIMINAR";
	public static final String PROCEDURE_TIPO_OBTENER = "PRC_TIPO_OBTENER";
	public static final String PROCEDURE_OBTENER_AREAS = "PRC_AREA_OBTENER";
	public static final String PROCEDURE_AREA_JEFE_OBTENER = "PRC_TRAB_OBTENER_JEFE";
	public static final String PROCEDURE_AREA_JEFE_VALIDAR = "PRC_TRAB_VALIDAR_JEFE";
	public static final String PROCEDURE_AREA_JEFE_GUARDAR = "PRC_TRAB_ACTUALIZAR_JEFE";
	public static final String PROCEDURE_ASUNTOS_OBTENER = "PRC_ASUNT_OBTENER";
	public static final String PROCEDURE_ASUNTOS_GUARDAR = "PRC_ASUNT_GUARDAR";
	public static final String PROCEDURE_ASUNTOS_ELIMINAR = "PRC_ASUNT_ELIMINAR";
	public static final String PROCEDURE_SECUENCIAL_OBTENER = "PRC_SECUEN_OBTENER";
	public static final String PROCEDURE_SECUENCIAL_VALIDAR = "PRC_SECUEN_VALIDAR";
	public static final String PROCEDURE_SECUENCIAL_ACTUALIZAR = "PRC_SECUEN_ACTUALIZAR";
	public static final String PROCEDURE_EMPRESAS_OBTENER = "PRC_REMIT_OBTENER";
	public static final String PROCEDURE_REPRESENTANTES_OBTENER = "PRC_REPRE_OBTENER";
	public static final String PROCEDURE_EMPRESAS_GUARDAR = "PRC_REMIT_GUARDAR";
	public static final String PROCEDURE_EMPRESAS_ELIMINAR = "PRC_REMIT_ELIMINAR";
	public static final String PROCEDURE_REPRESENTANTES_GUARDAR = "PRC_REPRE_GUARDAR";
	public static final String PROCEDURE_REPRESENTANTES_ELIMINAR = "PRC_REPRE_ELIMINAR";
	public static final String PROCEDURE_TRABAJADORES_OBTENER = "PRC_TRAB_OBTENER";
	public static final String PROCEDURE_GRUPO_OBTENER = "PRC_GRUPO_OBTENER";
	public static final String PROCEDURE_GRUPO_GUARDAR = "PRC_GRUPO_GUARDAR";
	public static final String PROCEDURE_GRUPO_ELIMINAR = "PRC_GRUPO_ELIMINAR";
	public static final String PROCEDURE_GRUPO_OBTENER_AREAS = "PRC_GRUPO_OBTENER_AREAS";
	public static final String PROCEDURE_OBTENER_CONTACTOS = "PRC_OBTENER_CONTACTOS";

	public static final String PACKAGE_BANDEJAS = "PCK_STD_BANDEJAS";
	public static final String PROCEDURE_MESAPARTES_OBTENER = "PRC_MESAPARTES_OBTENER_DOC";
	public static final String PROCEDURE_RECIBIDOS_OBTENER = "PRC_RECIBIDOS_OBTENER_DOC";
	public static final String PROCEDURE_PLAZO_OBTENER = "PRC_PLAZO_OBTENER_DOC";
	public static final String PROCEDURE_PENDIENTES_OBTENER = "PRC_PENDIENTES_OBTENER_DOC";
	public static final String PROCEDURE_VISADOS_OBTENER = "PRC_VISADOS_OBTENER_DOC";
	public static final String PROCEDURE_FIRMADOS_OBTENER = "PRC_FIRMADOS_OBTENER_DOC";
	public static final String PROCEDURE_PENDIENTES_PARAMETROS = "PRC_PENDIENTES_PARAMETROS";
	public static final String FUNCTION_CALCULAR_FECHA_LABORABLE = "FNC_CALCULAR_FECHA_LABORABLE";
	public static final String PROCEDURE_MESAPARTES_OBTENER_PARAMETROS = "PRC_MESAPARTES_PARAMETROS";
	public static final String PROCEDURE_MESAPARTES_ACTUALIZAR_RECIBIDO = "PRC_DOC_ACTUALIZAR_RECIBIDO";
	public static final String PROCEDURE_PARAMETROS_AREAS_GRUPOS = "PRC_OBTENER_AREAS_GRUPOS";
	public static final String PROCEDURE_PARAMETROS_AREAS = "PRC_OBTENER_AREAS";
	public static final String PROCEDURE_PARAMETROS_TRABAJADORS = "PRC_OBTENER_TRABAJADORES";
	public static final String PROCEDURE_PARAMETROS_TIPO_DOC = "PRC_OBTENER_TIPO_DOC";
	public static final String PROCEDURE_PARAMETROS_EMPRESAS = "PRC_OBTENER_EMPRESAS";
	public static final String PROCEDURE_PARAMETROS_REPRESENTANTES = "PRC_OBTENER_REPRESENTANTE";
	public static final String PROCEDURE_PARAMETROS_ASUNTOS= "PRC_OBTENER_ASUNTOS";
	public static final String PROCEDURE_PARAMETROS_PERIODOS = "PRC_OBTENER_PERIODOS";
	public static final String PROCEDURE_PARAMETROS_JEFE_TRBAJADOR = "PRC_OBTENER_JEFE";

	public static final String PACKAGE_DOCUMENTOS_ENTRANTES = "PCK_STD_DOCUMENTOS_ENTRANTES";
	public static final String PROCEDURE_DOC_PARAMETROS = "PRC_DOC_PARAMETROS";
	public static final String PROCEDURE_DOC_ENT_GUARDAR = "PRC_DOC_GUARDAR";
	public static final String PROCEDURE_DOC_ENT_OBTENER = "PRC_DOC_OBTENER";
	public static final String PROCEDURE_DOC_ENT_OBTENER_REFERENCIA = "PRC_DOC_OBTENER_REF";
	public static final String PROCEDURE_DOC_ENT_ELIMINAR = "PRC_DOC_ELIMINAR";
	public static final String PROCEDURE_DOC_ENT_ATENDER = "PRC_DOC_ATENDER";
	public static final String PROCEDURE_DOC_ENT_OBTENER_SEGUIMIENTO = "PRC_DOC_OBTENER_SEGUIMIENTO";
	public static final String PROCEDURE_DOC_ENT_OBTENER_ARBOL_SEG = "PRC_DOC_OBTENER_ARBOL";
	public static final String PROCEDURE_DOC_ENT_ACTUALIZAR_ADJUNTO = "PRC_DOC_ACTUALIZAR_ARCHIVO";
	public static final String PROCEDURE_DOC_ENT_GUARDAR_SEGUIMIENTO = "PRC_DOC_GUARDAR_SEGUIMIENTO";
	public static final String PROCEDURE_DOC_ENT_ELIMINAR_ANEXO = "PRC_DOC_ELIMINAR_ANEXO";
	public static final String PROCEDURE_DOC_ENT_INSERTAR_ANEXO = "PRC_DOC_INSERTAR_ANEXO";
	public static final String PROCEDURE_DOC_ENT_RECIBIR_FISICO = "PRC_DOC_RECEPCION_FISICA";
	public static final String PROCEDURE_DOC_ENT_ELIMINAR_SEGUIMIENTO = "PRC_DOC_ELIMINAR_SEGUIMIENTO";
	public static final String PROCEDURE_DOC_ENT_ACTUALIZAR_LEIDO = "PRC_DOC_ACTUALIZAR_LEIDO";
	public static final String PROCEDURE_DOC_ENT_VALIDAR_ENTRANTE = "PRC_DOC_VALIDA_ENTRANTE";

	// Documentos Salientes
	public static final String PACKAGE_DOCUMENTOS_SALIENTES = "PCK_STD_DOCUMENTOS_SALIENTES";
	public static final String PROCEDURE_OBTENER_DOCUMENTO = "PRC_DOC_OBTENER";
	public static final String PROCEDURE_GUARDAR_FIRMANTE = "PRC_DOC_GUARDAR_FIRMANTE";
	public static final String PROCEDURE_OBTENER_COMENTARIO = "PRC_DOC_OBTENER_COMENTARIOS";
	public static final String PROCEDURE_GUARDAR_COMENTARIO = "PRC_DOC_GUARDAR_COMENTARIO";
	public static final String PROCEDURE_GUARDAR_DOCUMENTO = "PRC_DOC_GUARDAR";
	public static final String PROCEDURE_GUARDAR_DESTINATARIO = "PRC_DOC_GUARDAR_DESTINATARIO";
	public static final String PROCEDURE_ELIMINAR_DOCUMENTO = "PRC_DOC_ELIMINAR";
	public static final String PROCEDURE_ENVIAR_DOCUMENTO = "PRC_DOC_ENVIAR";
	public static final String PROCEDURE_VISAR_DOCUMENTO = "PRC_DOC_VISAR";
	public static final String PROCEDURE_FIRMAR_DOCUMENTO = "PRC_DOC_FIRMAR";
	public static final String PROCEDURE_OBSERVAR_DOCUMENTO = "PRC_DOC_OBSERVAR";
	public static final String PROCEDURE_OBTENER_DIRIGIDOS = "PRC_DOC_DIRIGIDOS_OBTENER";
	public static final String PROCEDURE_OBTENER_VISANTES = "PRC_DOC_VISANTES_OBTENER";
	public static final String PROCEDURE_OBTENER_AREASGRUPOS = "PRC_OBTENER_AREASGRUPOS";
	public static final String PROCEDURE_VERIFICAR_DOC_ENTRADA = "PRC_VERIFICAR_DOC_ENTRADA";
	public static final String PROCEDURE_OBTENER_PARAMETROS_SALIDA = "PRC_PENDIENTES_PARAMETROS";
	public static final String PROCEDURE_GUARDAR_COMETARIO = "PRC_DOC_GUARDAR_COMENTARIO";
	public static final String PROCEDURE_OBTENER_COMETARIO = "PRC_DOC_OBTENER_COMENTARIOS";
	public static final String PROCEDURE_ACTUALIZAR_ADJUNTO = "PRC_ACTUALIZAR_ADJUNTO";
	public static final String PROCEDURE_VISAR_DOCUMENTOS = "PRC_DOC_VISAR_MULTIPLE";
	public static final String PROCEDURE_FIRMAR_DOCUMENTOS = "PRC_DOC_FIRMAR_MULTIPLE";
	public static final String PROCEDURE_OBSERVAR_DOCUMENTOS = "PRC_DOC_OBSERVAR_MULTIPLE";
	// Reportes Generales
	public static final String PACKAGE_REPORTES = "PCK_STD_REPORTES";
	public static final String PROCEDURE_ATENCION_DOC = "PRC_GEN_ATENCION_DOC";
	public static final String PROCEDURE_AREA_FECHA_ESTADO = "PRC_GEN_AREA_FECHA_ESTADO";
	public static final String PROCEDURE_DOCUMENTOS_ASIGNADOS = "PRC_GEN_DOC_ASIGNADOS";
	public static final String PROCEDURE_POR_SEGUIMIENTO = "PRC_GEN_POR_SEGUIMIENTO";
	public static final String PROCEDURE_PARAMETROS_REPORTE = "PRC_PARAMETROS_REPORTE";
	public static final String PROCEDURE_RESUMEN_INICIO = "PRC_RESUMEN_INICIO_OBTENER";
	public static final String PROCEDURE_REGISTRO_CARGOS = "PRC_REGISTRO_CARGOS";
	public static final String PROCEDURE_POR_REGISTRO_ENTRADA = "PRC_GEN_POR_REGISTRO_ENTRADA";
	public static final String PROCEDURE_DOCUMENTO_ENTRADA = "PRC_GEN_DOCUMENTO_ENTRADA";
	public static final String PROCEDURE_SEGUIMIENTO_DOC_ENTRADA = "PRC_GEN_SEGUIMIENTO";
	
	//Mantenimiento de Par??metros
	public static final String PROCEDURE_MANTENIMIENTO_DETALLE_PARAMETRO = "PRC_MANT_DETA_PARA";

}
