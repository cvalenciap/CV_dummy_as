package pe.com.sedapal.asi.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {
	
	private static Map<String, String> mapaEncode = new HashMap<>();
	private static Map<String, String> mapaDecode = new HashMap<>();
	
	static {
		mapaEncode.put("ñ", "|");
		mapaEncode.put("Ñ", "_");
		
		mapaDecode.put("|", "ñ");
		mapaDecode.put("_", "Ñ");
	}
	
	private StringUtil() {}

	public static String encodeCaracterEspecial(String value) {
		String result = "" + (value != null ? value : "");
		
		if (value != null) {
			for(Map.Entry<String, String> kv: mapaEncode.entrySet()) {
				result = result.replace(kv.getKey(), kv.getValue());
			}
		}
		
		return result.trim();
	}
	
	public static String decodeCaracterEspecial(String value) {
		String result = "" + (value != null ? value : "");
		
		if (value != null) {
			for(Map.Entry<String, String> kv: mapaDecode.entrySet()) {
				result = result.replace(kv.getKey(), kv.getValue());
			}
		}
		
		return result.trim();
	}
}
