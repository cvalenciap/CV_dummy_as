package pe.com.sedapal.asi.util;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64Util {
	private static Logger logger = LoggerFactory.getLogger(Base64Util.class);
	
	public static String convertirBytesStringBase64(byte[] buffer){
		String s = "";
		try {
			s = Base64.getEncoder().encodeToString(buffer);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return s;
	}
	
	public static byte[] convertirStringBase64Bytes(String cadena){
		byte[] buf = null;
		try {
			buf = Base64.getDecoder().decode(cadena);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return buf;
	}
}
