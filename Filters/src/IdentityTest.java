import static org.junit.Assert.*;

import org.junit.Test;


public class IdentityTest {

	@Test
	public void identityScalarFilterTest() {
		Filter identity = new IdentityFilter();
		Signal<Number> signal1 = new Signal<Number>(12);
		
		Number expected = 12;
		
		assertEquals(expected, identity.filter(signal1).getMagnitude());
	}
	
	@Test
	public void identityVectorFilterTest() {
		Filter identity = new IdentityFilter();
		Signal<Number> signal1 = new Signal<Number>(3, 4);
		
		Number expected = 5.0;
		
		assertEquals(expected, identity.filter(signal1).getMagnitude());
	}
}
