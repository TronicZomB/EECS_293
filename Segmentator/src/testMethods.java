
import static org.junit.Assert.*;

import org.junit.Test;

public class testMethods {

	@Test
	public void test() {
		assertEquals("asdf", 1, 2);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void test2() {
		Segmentator seg = new Segmentator(-1, "test");
		
	}

}
