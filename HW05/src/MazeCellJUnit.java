/**
 * Name: Steve Paley
 * Email: sjp78
 * Description: This JUnit tests the MazeCell class with both correct and expected exception cases.
 */

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class MazeCellJUnit {
	final static int MAX_VALUE = Integer.MAX_VALUE;
	
	MazeCell testCell0, testCell1, testCell2, testCell3, testCell4;
	Map<MazeCell, Integer> passagesTest;
	Map<MazeCell, Integer> emptyPassage;
	
	@Before
	public void initialize() {
		testCell0 = new MazeCell();
		testCell1 = new MazeCell();
		testCell2 = new MazeCell();
		testCell3 = new MazeCell();
		testCell4 = new MazeCell();
		
		passagesTest = new HashMap<MazeCell, Integer>();
		emptyPassage = new HashMap<MazeCell, Integer>();
		
		passagesTest.put(testCell1, 20);
		passagesTest.put(testCell2, 5);
		passagesTest.put(testCell3, Integer.MAX_VALUE);
		
	}

	@Test
	public void testAddPassagesAndIsValid() {
		assertEquals("isValid() should return false", false, testCell0.isValid());
		assertEquals("addPassages() should return true", true, testCell0.addPassages(passagesTest));
		assertEquals("isValid() should now be true", true, testCell0.isValid());
		assertEquals("adding passages a second time should return false", false, testCell0.addPassages(passagesTest));
	}
	
	@Test
	public void testCorrectPassages() {
		Map<MazeCell, Integer> expectedMap = new HashMap<MazeCell, Integer>();
		expectedMap.put(testCell1, 20);
		expectedMap.put(testCell2, 5);
		
		testCell0.addPassages(passagesTest);
		try {
			assertEquals("test the passages method to see if it filters out MAX_VALUE", expectedMap, testCell0.passages());
		} catch (UninitializedObjectException e) {
			e.printStackTrace();
			fail("ERROR: Should never have an UninitializedObjectException in this test.");
		}
	}
	
	@Test
	public void testEqualsOverride() {
		Map<MazeCell, Integer> expectedMap = new HashMap<MazeCell, Integer>();
		expectedMap.put(testCell1, 20);
		expectedMap.put(testCell2, 5);
		expectedMap.put(testCell4, 1);
		
		testCell0.addPassages(passagesTest);
		try {
			assertNotSame("expect this to fail", expectedMap, testCell0.passages());
		} catch (UninitializedObjectException e) {
			e.printStackTrace();
			fail("ERROR: Should never have an UninitializedObjectException in this test.");
		}
	}
	
	@Test (expected = UninitializedObjectException.class)
	public void testIncorrectPassages() throws UninitializedObjectException {
		testCell0.passages();
	}
	
	@Test
	public void testCorrectPassageTimeTo() {
		testCell0.addPassages(passagesTest);
		
		try {
			assertEquals("make sure an MazeCell not in passages returns MAX_VALUE", Integer.valueOf(MAX_VALUE), testCell0.passageTimeTo(testCell4));
			assertEquals("test valid passage time", Integer.valueOf(20), testCell0.passageTimeTo(testCell1));
			assertEquals("MAX_VALUE is the value of a MazeCell in passages", Integer.valueOf(MAX_VALUE), testCell0.passageTimeTo(testCell3));
		} catch (UninitializedObjectException e) {
			e.printStackTrace();
			fail("ERROR: Should never have an UninitializedObjectException in this test.");
		}
		
	}
	
	@Test (expected = UninitializedObjectException.class)
	public void testIncorrectPassageTimeTo() throws UninitializedObjectException {
		testCell0.passageTimeTo(testCell2);
	}
	
	@Test
	public void testCorrectConnectedCells() {
		Set<MazeCell> expectedSet = new HashSet<MazeCell>();
		expectedSet.add(testCell1);
		expectedSet.add(testCell2);
		
		testCell0.addPassages(passagesTest);
		
		try {
			assertEquals("make sure only valid passages are included", expectedSet, testCell0.connectedCells());
		} catch (UninitializedObjectException e) {
			e.printStackTrace();
			fail("ERROR: Should never have an UninitializedObjectException in this test.");
		}
	}
	
	@Test (expected = UninitializedObjectException.class)
	public void testIncorrectConnectedCells() throws UninitializedObjectException {
		testCell0.connectedCells();
	}
	
	@Test
	public void testCorrectIsDeadEnd() {
		testCell0.addPassages(passagesTest);
		testCell4.addPassages(emptyPassage);
		
		try {
			assertEquals("expect testCell4 to be dead end", true, testCell4.isDeadEnd());
			assertEquals("expect testCell0 to not be a dead end", false, testCell0.isDeadEnd());
		} catch (UninitializedObjectException e) {
			e.printStackTrace();
			fail("ERROR: Should never have an UninitializedObjectException in this test.");
		}
	}
	
	@Test (expected = UninitializedObjectException.class)
	public void testIncorrectIsDeadEnd() throws UninitializedObjectException {
		testCell0.isDeadEnd();
	}
}
