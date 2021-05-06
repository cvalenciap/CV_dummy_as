package pe.com.sedapal.asi.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pe.com.sedapal.asi.AppConfig;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = AppConfig.class)
public class StringUtilTest {

	@Test
	public void testEncode() {
		String input = "Ñoño";
		
		String result = StringUtil.encodeCaracterEspecial(input);
		
		System.out.println(result);
		
		assertEquals("_o|o", result);
	}
	
	@Test
	public void testDecode() {
		String input = "_o|o";
		
		String result = StringUtil.decodeCaracterEspecial(input);
		
		System.out.println(result);
		
		assertEquals("Ñoño", result);
	}
}
