import static org.junit.Assert.*;

import org.junit.Test;


public class CascadeTest {

	@Test
	public void twoFilterCascadeTest() {
		Filter max2 = new MaxFilter(2);
		Filter min3 = new MinFilter(3);
		
		Filter cascade = new FilterCascade(max2, min3);
		
		Signal<Number> s1 = new Signal<Number>(-1);
		Signal<Number> s2 = new Signal<Number>(3);
		Signal<Number> s3 = new Signal<Number>(1);
		Signal<Number> s4 = new Signal<Number>(2);
		Signal<Number> s5 = new Signal<Number>(1);
		
		cascade.filter(s1);
		cascade.filter(s2);
		cascade.filter(s3);
		cascade.filter(s4);
		
		Number expected = 2;
		Number actual = cascade.filter(s5).getMagnitude();
		
		assertEquals(expected, actual);
	}

}
