import static org.junit.Assert.*;

import org.junit.Test;


public class MaxFilterTest {

	@Test
	public void MaxScalarTest() {
		Filter max = new MaxFilter();
		Signal<Number> s1 = new Signal<Number>(1.2);
		Signal<Number> s2 = new Signal<Number>(3.4);
		Signal<Number> s3 = new Signal<Number>(2.1);
		Signal<Number> s4 = new Signal<Number>(1.8);
		
		max.filter(s1);
		max.filter(s2);
		max.filter(s3);
		
		Number expected = 3.4;
		Number actual = max.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void MaxScalarBoundedTest() {
		Filter max = new MaxFilter(2);
		Signal<Number> s1 = new Signal<Number>(1.2);
		Signal<Number> s2 = new Signal<Number>(3.4);
		Signal<Number> s3 = new Signal<Number>(2.1);
		Signal<Number> s4 = new Signal<Number>(1.8);
		
		max.filter(s1);
		max.filter(s2);
		max.filter(s3);
		
		Number expected = 2.1;
		Number actual = max.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void MaxResetTest() {
		Filter max = new MaxFilter();
		Signal<Number> s1 = new Signal<Number>(1.2);
		Signal<Number> s2 = new Signal<Number>(3.4);
		Signal<Number> s3 = new Signal<Number>(2.1);
		Signal<Number> s4 = new Signal<Number>(1.8);
		
		max.filter(s1);
		max.filter(s2);
		max.filter(s3);
		
		Number expected = 3.4;
		Number actual = max.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
		
		max.reset(new Signal<Number>(0));
		
		Signal<Number> s5 = new Signal<Number>(1.1);
		
		Number expected2 = 1.1;
		Number actual2 = max.filter(s5).getMagnitude();
		
		assertEquals(expected2, actual2);
	}
	
	@Test
	public void MaxVectorTest() {
		Filter max = new MaxFilter();
		Signal<Number> s1 = new Signal<Number>(1, 1);
		Signal<Number> s2 = new Signal<Number>(3, 4);
		Signal<Number> s3 = new Signal<Number>(5, 12);
		Signal<Number> s4 = new Signal<Number>(2, 2);
		
		max.filter(s1);
		max.filter(s2);
		max.filter(s3);
		
		Number expected = 13.0;
		Number actual = max.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
	}
}
