/**
 * Name: Steve Paley
 * Email: sjp78
 * Description: This JUnit tests the Maze class.
 */


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
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
			
			System.out.println(maze.toString());
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
			
			MazeRoute route = maze.routeRandom(cell0);
			
			System.out.println(route.toString());
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
		MazeCell cell5 = new MazeCell();
		Map<MazeCell, Integer> passages5 = new HashMap<MazeCell, Integer>();
		cell5.addPassages(passages5);
		
		passages4 = new HashMap<MazeCell, Integer>();
		passages4.put(cell5, 4);
		cell4.addPassages(passages4);
		
		try {
			maze.addCells(mazeSet);	
			
			MazeRoute route = maze.routeRandom(cell0);
			
			System.out.println("The MazeRoute after this statement should be empty:");
			System.out.println(route.toString());
			System.out.println("There should be an empty line above this where the empty MazeRotue is.\n");
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}
	
	@Test
	public void testInitialCellNotInMaze() {
		MazeCell cell5 = new MazeCell();
		Map<MazeCell, Integer> passages5 = new HashMap<MazeCell, Integer>();
		cell5.addPassages(passages5);
		
		passages4 = new HashMap<MazeCell, Integer>();
		cell4.addPassages(passages4);
		
		try {
			maze.addCells(mazeSet);	
			
			MazeRoute route = maze.routeRandom(cell5);
			
			System.out.println("The MazeRoute after this statement should be empty:");
			System.out.println(route.toString());
			System.out.println("There should be an empty line above this where the empty MazeRotue is.\n");
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
			
			System.out.println(route.toString());
			System.out.println("The last MazeCell should not include the initial MazeCell even though from \"cell4\" that is the only place to go.");
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
			
			System.out.println("The MazeRoute should contain only one MazeCell");
			System.out.println(route.toString());
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}
}
