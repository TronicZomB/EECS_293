/**
 * Name: Steve Paley
 * Email: sjp78
 * Description: This JUnit tests the MazeRoute class with both correct and expected exception cases.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


@SuppressWarnings("deprecation")
public class MazeRouteJUnit {
	final static int MAX_VALUE = Integer.MAX_VALUE;
	
	MazeCell testCell0, testCell1, testCell2, testCell3, testCell4;
	Map<MazeCell, Integer> testCell0Passages;
	Map<MazeCell, Integer> testCell1Passages;
	Map<MazeCell, Integer> emptyPassage;
	
	MazeRoute testMazeRoute;
	List<MazeCell> testRoute;
	List<MazeCell> singleCellRoute;
	List<MazeCell> maxValueRoute;
	List<MazeCell> uninitializedMazeCell;
	
	@Before
	public void initialize() {
		testCell0 = new MazeCell();
		testCell1 = new MazeCell();
		testCell2 = new MazeCell();
		testCell3 = new MazeCell();
		testCell4 = new MazeCell();
		
		testMazeRoute = new MazeRoute();
		
		testCell0Passages = new HashMap<MazeCell, Integer>();
		testCell0Passages.put(testCell1, 20);
		testCell0Passages.put(testCell2, 5);
		testCell0.addPassages(testCell0Passages);
		
		testCell1Passages = new HashMap<MazeCell, Integer>();
		testCell1Passages.put(testCell2, 30);
		testCell1Passages.put(testCell3, 65);
		testCell1.addPassages(testCell1Passages);
		
		emptyPassage = new HashMap<MazeCell, Integer>();
		testCell2.addPassages(emptyPassage);
		testCell3.addPassages(emptyPassage);
		
		testRoute = new ArrayList<MazeCell>();
		testRoute.add(testCell0);
		testRoute.add(testCell1);
		testRoute.add(testCell2);
		
		singleCellRoute = new ArrayList<MazeCell>();
		singleCellRoute.add(testCell0);
		
		maxValueRoute = new ArrayList<MazeCell>();
		maxValueRoute.add(testCell0);
		maxValueRoute.add(testCell1);
		maxValueRoute.add(testCell2);
		maxValueRoute.add(testCell3);
		
		uninitializedMazeCell = new ArrayList<MazeCell>();
		uninitializedMazeCell.add(testCell4);
	}

	@Test
	public void testAddCellsAndIsValidAndTravelTime() {
		Integer expectedTravelTime = 50;
		
		try {
			assertEquals("expect isValid() to be false", false, testMazeRoute.isValid());
			assertEquals("expect the MazeRoute to add the MazeCells successfully, returning true", true, testMazeRoute.addCells(testRoute));
			assertEquals("expect isValid() to be true now", true, testMazeRoute.isValid());
			assertEquals("expect trying to add the route again to MazeRoute will return false", false, testMazeRoute.addCells(testRoute));

			assertEquals(expectedTravelTime, testMazeRoute.travelTime());
		} catch (UninitializedObjectException e) {
			fail("ERROR: Should not throw an UninitializedObjectError in this test.");
		}
	}
	
	@Test
	public void testSingleMazeCellTravelTime() {
		try {
			testMazeRoute.addCells(singleCellRoute);
			assertEquals("expect a single MazeCell route to return travel time of 0", Integer.valueOf(0), testMazeRoute.travelTime());
		} catch (UninitializedObjectException e) {
			fail("ERROR: Should not throw an UninitializedObjectError in this test.");
		}
	}
	
	@Test
	public void testMaxValueTravelTime() {
		try {
			testMazeRoute.addCells(maxValueRoute);
			assertEquals("expect an unreachable MazeCell route to return travel time of MAX_VALUE", Integer.valueOf(MAX_VALUE), testMazeRoute.travelTime());
		} catch (UninitializedObjectException e) {
			fail("ERROR: Should not throw an UninitializedObjectError in this test.");
		}
	}
	
	@Test (expected = UninitializedObjectException.class)
	public void testIncorrectAddCellsRoute() throws UninitializedObjectException {
		testMazeRoute.addCells(uninitializedMazeCell);
	}
	
	@Test (expected = UninitializedObjectException.class)
	public void testIncorrectTravelTime() throws UninitializedObjectException {
		testMazeRoute.travelTime();
	}
	
	@Test
	public void testCorrectGetCells() {
		List<MazeCell> expectedCellRoute = new ArrayList<MazeCell>();
		expectedCellRoute.add(testCell0);
		expectedCellRoute.add(testCell1);
		expectedCellRoute.add(testCell2);
		
		try {
			testMazeRoute.addCells(testRoute);
			assertEquals("the getCells() should return the testCells 0 through 2", expectedCellRoute, testMazeRoute.getCells());
		} catch (UninitializedObjectException e) {
			fail("ERROR: Should not throw an UninitializedObjectError in this test.");
		}
	}
	
	@Test (expected = UninitializedObjectException.class)
	public void testIncorrectGetCells() throws UninitializedObjectException {
		testMazeRoute.getCells();
	}
}
