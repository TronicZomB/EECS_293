import static org.junit.Assert.*;
import org.junit.Test;


public class BinomialFilterTest {

	@Test
	public void testBinomialFilter() {
		ScalarFilter scalarLinear = new BinomialFilter(3);
		
		Signal<Number> s1 = new Signal<Number>(-1);
		Signal<Number> s2 = new Signal<Number>(3);
		Signal<Number> s3 = new Signal<Number>(1);
		Signal<Number> s4 = new Signal<Number>(2);
		Signal<Number> s5 = new Signal<Number>(1);	
		
		scalarLinear.filter(s1);
		scalarLinear.filter(s2);
		scalarLinear.filter(s3);
		scalarLinear.filter(s4);
		
		Number expected = 13.0;
		Number actual = scalarLinear.filter(s5).getMagnitude();
		
		assertEquals(expected, actual);
	}

}
