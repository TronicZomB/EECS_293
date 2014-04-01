import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Test;


public class AverageTest {

	@Test
	public void AvgScalarTest() {
		Filter avg = new AverageFilter();
		Signal<Number> s1 = new Signal<Number>(1.2);
		Signal<Number> s2 = new Signal<Number>(3.4);
		Signal<Number> s3 = new Signal<Number>(2.1);
		Signal<Number> s4 = new Signal<Number>(1.8);
		
		avg.filter(s1);
		avg.filter(s2);
		avg.filter(s3);
		
		Number expected = 2.125;
		Number actual = avg.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void AvgScalarBoundedTest() {
		Filter avg = new AverageFilter(2);
		Signal<Number> s1 = new Signal<Number>(1.2);
		Signal<Number> s2 = new Signal<Number>(3.4);
		Signal<Number> s3 = new Signal<Number>(2.);
		Signal<Number> s4 = new Signal<Number>(1.);
		
		avg.filter(s1);
		avg.filter(s2);
		avg.filter(s3);
		
		Number expected = 1.5;
		Number actual = avg.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void AvgResetTest() {
		ScalarFilter avg = new AverageFilter();
		Signal<Number> s1 = new Signal<Number>(1.2);
		Signal<Number> s2 = new Signal<Number>(3.4);
		Signal<Number> s3 = new Signal<Number>(2.1);
		Signal<Number> s4 = new Signal<Number>(1.8);
		
		avg.filter(s1);
		avg.filter(s2);
		avg.filter(s3);
		
		Number expected = 2.125;
		Number actual = avg.filter(s4).getMagnitude();
		
		assertEquals(expected, actual);
		
		avg.reset(new Signal<Number>(0));
		
		Signal<Number> s5 = new Signal<Number>(1.2);
		Signal<Number> s6 = new Signal<Number>(2.4);
		
		avg.filter(s5);
		
		Number expected2 = 1.2;
		Number actual2 = avg.filter(s6).getMagnitude();
		
		assertEquals(expected2, actual2);
	}
	
	@Test (expected = InvalidParameterException.class)
	public void AvgVectorTest() {
		ScalarFilter avg = new AverageFilter();
		Signal<Number> s1 = new Signal<Number>(1, 1);
		
		avg.filter(s1);
	}
}
