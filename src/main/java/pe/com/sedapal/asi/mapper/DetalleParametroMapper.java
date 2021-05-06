package pe.com.sedapal.asi.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.com.sedapal.asi.model.DetalleParametro;

public class DetalleParametroMapper {

	public static List<DetalleParametro> mapRows(Map<String, Object> resultados) {
		DetalleParametro item;
		
		List<DetalleParametro> detallesParametro = new ArrayList<>();		
		List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
		
		for(Map<String, Object> map : lista) {
			item = new DetalleParametro();
			item.setIdPara(((BigDecimal)map.get("N_IDPARA")).intValue());					
			item.setIdDetallePara(((BigDecimal)map.get("N_IDDETPARA")).intValue());
			item.setNombreDetallePara((String)map.get("V_NOMDETPARA"));
			item.setValorPara1((String)map.get("V_VALORPARA1"));
			item.setValorPara2((String)map.get("V_VALORPARA2"));
			item.setValorPara3((String)map.get("V_VALORPARA3"));
			detallesParametro.add(item);
		}
		
		return detallesParametro;
	}
}
