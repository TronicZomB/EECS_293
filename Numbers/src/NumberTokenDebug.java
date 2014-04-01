import static org.junit.Assert.*;

import org.junit.Test;


public class NumberTokenDebug {

	@Test
	/* Structural Basis, Nominal Case (No Errors) */
	public void NumberTokenConstructorTest() {
		NumberToken token = new NumberToken("six");
		int expected = 0;
		ErrorManager em = ErrorManager.getInstance();
		int actual = em.getExitCode();
		assertEquals(expected, actual);
	}
	
	@Test 
	/* Structural Basis, Not All LowerCase */
	public void NumberTokenConstructorNotLowerTest() {
		NumberToken token = new NumberToken("sEvEn");
		int expected = 7;
		ErrorManager em = ErrorManager.getInstance();
		int actual = em.getExitCode();
		assertEquals(expected, actual);
		em.cleanup();
	}

	@Test 
	/* Structural Basis, Not Valid Token */
	public void NumberTokenConstructorNotValidTokenTest() {
		NumberToken token = new NumberToken("steven");
		int expected = 7;
		ErrorManager em = ErrorManager.getInstance();
		int actual = em.getExitCode();
		assertEquals(expected, actual);
		em.cleanup();
	}
	
	@Test
	/* Structural Basis, Nominal Case */
	public void toStringTest() {
		NumberToken token = new NumberToken("nine");
		
		String expected = "nine";
		String actual = token.toString();
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Nominal Case */
	public void getTypeOfTest() {
		NumberToken token = new NumberToken("one");
		NumberToken.Test test = token.new Test();
		
		TokenType expected = TokenType.DIGIT;
		TokenType actual = test.getTypeOf("eight");
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Bad Data (No Pattern to Match) */
	public void getTypeOfNullTest() {
		NumberToken token = new NumberToken("one");
		NumberToken.Test test = token.new Test();
		
		TokenType expected = null;
		TokenType actual = test.getTypeOf("nill");
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Nominal Digit Case */
	public void getValueOfDigitTest() {
		NumberToken token = new NumberToken("one");
		NumberToken.Test test = token.new Test();
		
		int expected = 2;
		int actual = test.getValueOf(TokenType.DIGIT, "two");
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Nominal Nty Case */
	public void getValueOfNtyTest() {
		NumberToken token = new NumberToken("one");
		NumberToken.Test test = token.new Test();
		
		int expected = 4;
		int actual = test.getValueOf(TokenType.NTY, "forty");
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Nominal Teen Case */
	public void getValueOfTeenTest() {
		NumberToken token = new NumberToken("one");
		NumberToken.Test test = token.new Test();
		
		int expected = 3;
		int actual = test.getValueOf(TokenType.TEEN, "thirteen");
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Default Case */
	public void getValueOfDefaultTest() {
		NumberToken token = new NumberToken("one");
		NumberToken.Test test = token.new Test();
		
		int expected = -1;
		int actual = test.getValueOf(TokenType.ZERO, "zero");
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Mismatch Type and String Value */
	public void getValueOfMismatchTest() {
		NumberToken token = new NumberToken("one");
		NumberToken.Test test = token.new Test();
		
		int expected = -1;
		int actual = test.getValueOf(TokenType.TEEN, "twenty");
		
		assertEquals(expected, actual);
	}
}
