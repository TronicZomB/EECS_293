import static org.junit.Assert.*;

import org.junit.Test;


public class MinFilterTest {

	@Test
	public void MinScalarTest() {
		Filter min = new MinFilter();
		Signal<Number> s1 = new Signal<Number>(1.2);
		Signal<Number> s2 = new Signal<Number>(3.4);
		Signal<Number> s3 = new Signal<Number>(2.1);
		Signal<Number> s4 = new Signal<Number>(1.8);
		
		min.filter(s1);
		min.filter(s2);
		min.filter(s3);
		
		Number expected = 1.2;
		Number actual = min.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void MinScalarBoundedTest() {
		Filter min = new MinFilter(2);
		Signal<Number> s1 = new Signal<Number>(1.2);
		Signal<Number> s2 = new Signal<Number>(3.4);
		Signal<Number> s3 = new Signal<Number>(2.1);
		Signal<Number> s4 = new Signal<Number>(1.8);
		
		min.filter(s1);
		min.filter(s2);
		min.filter(s3);
		
		Number expected = 1.8;
		Number actual = min.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void MinResetTest() {
		Filter min = new MinFilter();
		Signal<Number> s1 = new Signal<Number>(1.2);
		Signal<Number> s2 = new Signal<Number>(3.4);
		Signal<Number> s3 = new Signal<Number>(2.1);
		Signal<Number> s4 = new Signal<Number>(1.8);
		
		min.filter(s1);
		min.filter(s2);
		min.filter(s3);
		
		Number expected = 1.2;
		Number actual = min.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
		
		min.reset(new Signal<Number>(0));
		
		Signal<Number> s5 = new Signal<Number>(5.0);
		
		Number expected2 = 0;
		Number actual2 = min.filter(s5).getMagnitude();
		
		assertEquals(expected2, actual2);
	}
	
	@Test
	public void MinVectorTest() {
		Filter min = new MinFilter();
		Signal<Number> s1 = new Signal<Number>(1, 1);
		Signal<Number> s2 = new Signal<Number>(3, 4);
		Signal<Number> s3 = new Signal<Number>(5, 12);
		Signal<Number> s4 = new Signal<Number>(2, 2);
		
		min.filter(s1);
		min.filter(s2);
		min.filter(s3);
		
		Number expected = Math.sqrt(2);
		Number actual = min.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
	}
}
