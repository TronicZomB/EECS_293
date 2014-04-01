/**
 * Name: Steve Paley
 * Email: sjp78
 * Description: This JUnit tests the Maze class.
 */


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


@SuppressWarnings("deprecation")
public class MazeJUnit {
	
	MazeCell cell0, cell1, cell2, cell3, cell4;
	Map<MazeCell, Integer> passages0, passages1, passages2, passages3, passages4;
	Maze maze;
	Set<MazeCell> mazeSet = new HashSet<MazeCell>();
	
	@Before
	public void initialize() {
		cell0 = new MazeCell();
		cell1 = new MazeCell();
		cell2 = new MazeCell();
		cell3 = new MazeCell();
		cell4 = new MazeCell();
		
		passages0 = new HashMap<MazeCell, Integer>();
		passages0.put(cell1, 5);
		passages0.put(cell2, 10);
		passages0.put(cell3, 15);
		passages0.put(cell4, 20);
		cell0.addPassages(passages0);
		
		passages1 = new HashMap<MazeCell, Integer>();
		passages1.put(cell2, 7);
		passages1.put(cell3, 14);
		cell1.addPassages(passages1);
		
		passages2 = new HashMap<MazeCell, Integer>();
		passages2.put(cell3, 8);
		cell2.addPassages(passages2);
		
		passages3 = new HashMap<MazeCell, Integer>();
		passages3.put(cell4, 12);
		cell3.addPassages(passages3);
		
		maze = new Maze();
		
		mazeSet.add(cell0);
		mazeSet.add(cell1);
		mazeSet.add(cell2);
		mazeSet.add(cell3);
		mazeSet.add(cell4);
	}
	
	@Test
	public void testIsValidAndAddCells() {
		passages4 = new HashMap<MazeCell, Integer>();
		cell4.addPassages(passages4);
		
		try {
			assertEquals(false, maze.isValid());
			assertEquals(true, maze.addCells(mazeSet));
			assertEquals(true, maze.isValid());
			assertEquals(false, maze.addCells(mazeSet));
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}

	@Test
	public void testRouteFristToDeadEnd() {
		passages4 = new HashMap<MazeCell, Integer>();
		cell4.addPassages(passages4);
		
		try {
			maze.addCells(mazeSet);	
			
			MazeRoute route1 = new MazeRoute();
			MazeRoute route2 = new MazeRoute();
			
			for (int i = 0; i < 1000; i++) {
				route1 = maze.routeRandom(cell0);
				route2 = maze.routeRandom(cell0);
				
				if (!route2.toString().equals(route1.toString())) {
					//assertNotEquals(route2.toString(), route1.toString());
					return;
				}
			}
			
			fail("routeRandom created the same route 1000 times.");
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}
	
	@Test (expected = UninitializedObjectException.class)
	public void testAddCellsException() throws UninitializedObjectException {
		MazeCell cell = new MazeCell();
		Set<MazeCell> set = new HashSet<MazeCell>();
		set.add(cell);
		
		maze.addCells(set);
		
		System.out.println(maze.toString());
	}
	
	@Test (expected = UninitializedObjectException.class) 
	public void testRouteFirstException() throws UninitializedObjectException{
		maze.routeRandom(cell0);
	}
	
	@Test
	public void testCellNotInMaze() {
		//The expected list is empty
		List<MazeCell> expectedList = new ArrayList<MazeCell>();
		
		MazeCell cell5 = new MazeCell();
		Map<MazeCell, Integer> passages5 = new HashMap<MazeCell, Integer>();
		cell5.addPassages(passages5);
		
		passages4 = new HashMap<MazeCell, Integer>();
		passages4.put(cell5, 4);
		cell4.addPassages(passages4);
		
		try {
			maze.addCells(mazeSet);	
			
			MazeRoute route = maze.routeRandom(cell0);
			
			assertEquals(expectedList, route.getCells());
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}
	
	@Test
	public void testInitialCellNotInMaze() {
		//The expected list is empty
		List<MazeCell> expectedList = new ArrayList<MazeCell>();
		
		MazeCell cell5 = new MazeCell();
		Map<MazeCell, Integer> passages5 = new HashMap<MazeCell, Integer>();
		cell5.addPassages(passages5);
		
		passages4 = new HashMap<MazeCell, Integer>();
		cell4.addPassages(passages4);
		
		try {
			maze.addCells(mazeSet);	
			
			MazeRoute route = maze.routeRandom(cell5);
			
			assertEquals(expectedList, route.getCells());
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}
	
	@Test
	public void testAlreadyVisitedCell() {
		passages4 = new HashMap<MazeCell, Integer>();
		passages4.put(cell0, 4);
		cell4.addPassages(passages4);
		
		try {
			maze.addCells(mazeSet);	
			
			MazeRoute route = maze.routeRandom(cell0);
			
			//The route did not include the second time traveled to cell0 as the last cell traveled before ending the route.
			assertTrue(route.getCells().toArray()[route.getCells().size() - 1] != cell0);
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}
	
	@Test
	public void testInitialCellDeadEnd() {
		MazeCell cell = new MazeCell();
		Map<MazeCell, Integer> map = new HashMap<MazeCell, Integer>();
		cell.addPassages(map);
		
		Set<MazeCell> set = new HashSet<MazeCell>();
		set.add(cell);
		
		try {
			maze.addCells(set);	
			
			MazeRoute route = maze.routeRandom(cell);
			
			//The initial cell was a dead end so it is the only cell in the route making the route size 1.
			assertTrue(route.getCells().size() == 1);
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}
}
