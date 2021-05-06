package pe.com.sedapal.asi.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class FechaUtil {

	public static String convertDateToString(Date fecha) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.FORMATO_DD_MM_YYYY);
			return sdf.format(fecha);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String convertirUTCaDDMMYYY(String fechaUTC) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern( "E MMM dd HH:mm:ss z uuuu" )
                .withLocale( Locale.US );
		ZonedDateTime zdt = ZonedDateTime.parse(fechaUTC , f );
		LocalDate ld = zdt.toLocalDate();
		DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern( "dd-MM-uuuu" );
		String output = ld.format( fLocalDate) ;
		return output;
	}
	
	public static Date convertirStringToDate(String fecha, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(fecha);
		} catch(Exception e) {
			return null;
		}
	}
}
