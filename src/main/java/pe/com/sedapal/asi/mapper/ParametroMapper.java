package pe.com.sedapal.asi.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.asi.model.Parametro;
import pe.com.sedapal.asi.model.Tipo;
import pe.com.sedapal.asi.model.enums.TipoParametro;

public class ParametroMapper {

	public static List<Parametro> mapRows(Map<String, Object> resultados) {
		Parametro item;
		Tipo tipo;
		
		List<Parametro> listatipos = new ArrayList<>();		
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
		
		for(Map<String, Object> map : lista) {
			item = new Parametro();
			item.setCodigo(((BigDecimal)map.get("N_IDPARA")).toString());					
			String tipoParam = (String)map.get("V_TIPOPARA");
			tipo = new Tipo();
			
			if (TipoParametro.VALOR_UNICO.getValor().equals(tipoParam)) {	
				tipo.setCodigo(TipoParametro.VALOR_UNICO.getCodigo());
				tipo.setDescripcion(TipoParametro.VALOR_UNICO.getTexto());
			} else if(TipoParametro.LISTADO_VALORES.getValor().equals(tipoParam)) {
				tipo.setCodigo(TipoParametro.LISTADO_VALORES.getCodigo());
				tipo.setDescripcion(TipoParametro.LISTADO_VALORES.getTexto());
			}
			
			item.setTipo(tipo);
			item.setNombre((String)map.get("V_NOMPARA"));
			listatipos.add(item);
		}
		
		return listatipos;
	}

	public static Parametro mapRow(Map<String, Object> resultados) {
		Parametro item = new Parametro();
		Tipo tipo;
		
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
		
		for(Map<String, Object> map : lista) {
			item = new Parametro();
			item.setCodigo(((BigDecimal)map.get("N_IDPARA")).toString());					
			String tipoParam = (String)map.get("V_TIPOPARA");
			tipo = new Tipo();
			
			if (TipoParametro.VALOR_UNICO.getValor().equals(tipoParam)) {	
				tipo.setCodigo(TipoParametro.VALOR_UNICO.getCodigo());
				tipo.setDescripcion(TipoParametro.VALOR_UNICO.getTexto());
			} else if(TipoParametro.LISTADO_VALORES.getValor().equals(tipoParam)) {
				tipo.setCodigo(TipoParametro.LISTADO_VALORES.getCodigo());
				tipo.setDescripcion(TipoParametro.LISTADO_VALORES.getTexto());
			}
			
			item.setTipo(tipo);
			item.setNombre((String)map.get("V_NOMPARA"));
			
			break;
		}
		
		return item;
	}
}
