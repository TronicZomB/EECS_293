import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class TokenTypeDebug {

	@Test
	/* Structural Basis */
	public void getPatternTest() {
		TokenType type = null;
		type = TokenType.ZERO;
		String expected = "zero|naught";
		assertEquals(expected, type.getPattern());
	}
	
	@Test
	/* Structural Basis, Nominal Case */
	public void getValueTest() {
		TokenType type = null;
		type = TokenType.NTY;
		int expected = 2;
		int actual = type.getValue("twenty");
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Bad Data */
	public void getValueBadTest() {
		TokenType type = null;
		type = TokenType.TEEN;
		int expected = -1;
		int actual = type.getValue("twenty");
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Nominal Case */
	public void getValuesTest() {
		TokenType type = null;
		type = TokenType.TEEN;
		Map<String, Integer> expected = new HashMap<String, Integer>();
		expected.put("eleven", 1);
		expected.put("twelve", 2);
		expected.put("thirteen", 3);
		expected.put("fourteen", 4);
		expected.put("fifteen", 5);
		expected.put("sixteen", 6);
		expected.put("seventeen", 7);
		expected.put("eighteen", 8);
		expected.put("nineteen", 9);
		
		Map<String, Integer> actual = type.getValues();
		assertEquals(expected, actual);
	}
}
