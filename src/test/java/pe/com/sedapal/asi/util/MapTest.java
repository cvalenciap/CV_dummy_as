package pe.com.sedapal.asi.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapTest {

	@Test
	public void testFindItem() {
		Map<Integer, String> params = new HashMap<>();
		params.put(1, "Edgar");
		params.put(2, "Miguel");
		
		Integer findKey = 2;
		String expectedName = "Miguel";
		
		String name = params.entrySet().stream()
          .filter(e -> e.getKey().equals(findKey))
          .map(Map.Entry::getValue)
          .findFirst()
          .orElse(null);
          
        assertEquals(expectedName, name);
	}
}
