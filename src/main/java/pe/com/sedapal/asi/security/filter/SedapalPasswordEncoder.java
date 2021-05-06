package pe.com.sedapal.asi.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SedapalPasswordEncoder implements PasswordEncoder {
	private Logger logger = LoggerFactory.getLogger(SedapalPasswordEncoder.class);

	@Override
	public String encode(CharSequence rawPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(final CharSequence rawPassword, final String encodedPassword) {

		if (rawPassword == null || rawPassword.length() == 0) {
			logger.warn("Empty raw password");
			return false;
		}
		
		if (encodedPassword == null || encodedPassword.length() == 0) {
			logger.warn("Empty encoded password");
			return false;
		}

		return checkpw(rawPassword, encodedPassword);
	}

	private boolean checkpw(final CharSequence rawPassword, final String encodedPassword) {
		String hashedPwd = encode(rawPassword);
		
		return equalsNoEarlyReturn(hashedPwd, encodedPassword);
	}
	
	private boolean equalsNoEarlyReturn(String a, String b) {
		logger.info("a=" + a);
		logger.info("b=" + b);
		
		char[] caa = a.toCharArray();
		char[] cab = b.toCharArray();

		if (caa.length != cab.length) {
			return false;
		}

		byte ret = 0;
		for (int i = 0; i < caa.length; i++) {
			ret |= caa[i] ^ cab[i];
		}
		return ret == 0;
	}
}
