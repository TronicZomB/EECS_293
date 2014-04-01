import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;


public class SignalTest {
	
	Signal<Number> scalar, vector1, vector2;
	
	@Before
	public void initialize() {
		scalar = new Signal<Number>(123);
		vector1 = new Signal<Number>(12, 34);
		
		Vector<Number> vector = new Vector<Number>();
		vector.add(56);
		vector.add(78);
		vector.add(90);
		vector2 = new Signal<Number>(vector);
	}

	@Test
	public void testIsScalar() {
		assertTrue(scalar.isScalar());
		assertFalse(vector1.isScalar());
	}
	
	@Test
	public void testGetMagnitude() {
		Number expected1 = 123;
		assertEquals(expected1, scalar.getMagnitude());
		
		Number expected2 = Math.sqrt(1300);
		assertEquals(expected2, vector1.getMagnitude());
	}
	
	@Test
	public void testGetVector() {
		Vector<Number> expected1 = new Vector<Number>();
		expected1.add(12);
		expected1.add(34);
		
		assertEquals(expected1, vector1.getVector());
		
		Vector<Number> expected2 = new Vector<Number>();
		expected2.add(56);
		expected2.add(78);
		expected2.add(90);
		
		assertEquals(expected2, vector2.getVector());
	}

	@Test
	public void testToString() {
		String expected = "123";
		assertEquals(expected, scalar.toString());
	}
}
