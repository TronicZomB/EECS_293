import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class ScalarLinearTest {

	@Test
	public void scalarLinearTest() {
		List<Number> inCoefficients = new ArrayList<Number>();
		List<Number> outCoefficients = new ArrayList<Number>();
		
		inCoefficients.add(.5);
		inCoefficients.add(.5);
		outCoefficients.add(.1);
		
		Filter scalarLinear = new ScalarLinearFilter(inCoefficients, outCoefficients);
		
		Signal<Number> s1 = new Signal<Number>(-1);
		Signal<Number> s2 = new Signal<Number>(3);
		Signal<Number> s3 = new Signal<Number>(1);
		Signal<Number> s4 = new Signal<Number>(2);
		Signal<Number> s5 = new Signal<Number>(1);	
		
		scalarLinear.filter(s1);
		scalarLinear.filter(s2);
		scalarLinear.filter(s3);
		scalarLinear.filter(s4);
		
		Number expected = 1.36895;
		Number actual = scalarLinear.filter(s5).getMagnitude();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void scalarLinearResetTest() {
		List<Number> inCoefficients = new ArrayList<Number>();
		List<Number> outCoefficients = new ArrayList<Number>();
		
		inCoefficients.add(.5);
		inCoefficients.add(.5);
		outCoefficients.add(.1);
		
		ScalarFilter scalarLinear = new ScalarLinearFilter(inCoefficients, outCoefficients);
		
		Signal<Number> s1 = new Signal<Number>(-1);
		Signal<Number> s2 = new Signal<Number>(3);
		Signal<Number> s3 = new Signal<Number>(1);
		Signal<Number> s4 = new Signal<Number>(2);
		Signal<Number> s5 = new Signal<Number>(1);	
		
		scalarLinear.filter(s1);
		scalarLinear.filter(s2);
		scalarLinear.filter(s3);
		scalarLinear.filter(s4);
		
		Number expected = 1.36895;
		Number actual = scalarLinear.filter(s5).getMagnitude();
		
		assertEquals(expected, actual);
		
		scalarLinear.reset(new Signal<Number>(0));
		
		Signal<Number> s6 = new Signal<Number>(-1);
		Signal<Number> s7 = new Signal<Number>(1);
		Signal<Number> s8 = new Signal<Number>(2);
		
		scalarLinear.filter(s6);
		scalarLinear.filter(s7);
		
		Number expected2 = 1.495;
		Number actual2 = scalarLinear.filter(s8).getMagnitude();
		
		assertEquals(expected2, actual2);
	}
	
	@Test (expected = InvalidParameterException.class)
	public void ScalarLinearVectorTest() {
		List<Number> inCoefficients = new ArrayList<Number>();
		List<Number> outCoefficients = new ArrayList<Number>();
		
		inCoefficients.add(.5);
		inCoefficients.add(.5);
		outCoefficients.add(.1);
		
		ScalarFilter scalarLinear = new ScalarLinearFilter(inCoefficients, outCoefficients);
		Signal<Number> s1 = new Signal<Number>(1, 1);
		
		scalarLinear.filter(s1);
	}
}
