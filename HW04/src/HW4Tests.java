import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class HW4Tests {
	
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
		
		passages1 = new HashMap<MazeCell, Integer>();
		passages1.put(cell2, 7);
		
		passages2 = new HashMap<MazeCell, Integer>();
		passages2.put(cell3, 8);
		
		passages3 = new HashMap<MazeCell, Integer>();
		passages3.put(cell4, 12);
		
		maze = new Maze();
		
		mazeSet.add(cell0);
		mazeSet.add(cell1);
		mazeSet.add(cell2);
		mazeSet.add(cell3);
		mazeSet.add(cell4);
	}

	@Test
	public void testRandomFirstSame() {
		cell0.addPassages(passages0);
		cell1.addPassages(passages1);
		cell2.addPassages(passages2);
		cell3.addPassages(passages3);
		
		passages4 = new HashMap<MazeCell, Integer>();
		cell4.addPassages(passages4);
		
		try {
			maze.addCells(mazeSet);	
			
			MazeRoute first = maze.routeFirst(cell0);
			MazeRoute random = maze.routeRandom(cell0);
			
			//Test MazeRoutes equal
			assertEquals(first.toString(), random.toString());
			
			//Show that MazeRoutes are the same
			System.out.println("The following two MazeRoutes should be the same:");
			System.out.println("Maze.routeFirst():\n" + first.toString());
			System.out.println("Maze.routeRandom():\n" + random.toString());
			
			//Test the travelTime and travelTimeRandom
			System.out.println("The travel time for the random route is " + random.travelTime() + " seconds.");
			System.out.println("The random travel time for the random route is " + random.travelTimeRandom() + " seconds.\n");
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}
	
	@Test
	public void testRandomFirstDifferent() {
		passages0.put(cell2, 10);
		passages0.put(cell3, 15);
		passages0.put(cell4, 20);
		
		passages1.put(cell3, 14);
		passages1.put(cell4, 34);
		
		passages2.put(cell4, 67);
		
		cell0.addPassages(passages0);
		cell1.addPassages(passages1);
		cell2.addPassages(passages2);
		cell3.addPassages(passages3);
		
		passages4 = new HashMap<MazeCell, Integer>();
		cell4.addPassages(passages4);
		
		try {
			maze.addCells(mazeSet);	
			
			MazeRoute first = maze.routeFirst(cell0);
			MazeRoute random = maze.routeRandom(cell0);
			
			//Show that routeFirst and routeRandom can produce different results
			System.out.println("The following two MazeRoutes should be different:");
			System.out.println("Maze.routeFirst():\n" + first.toString());
			System.out.println("Maze.routeRandom():\n" + random.toString());
		} catch (UninitializedObjectException e) {
			//Not happening.
		}
	}
	
	@Test
	public void testAddPassagesNew() {
		MazeCell.Status status = new MazeCell.Status();
		
		System.out.println("The Status before addPassages: " + status.getMessage());
		
		cell0.addPassages(passages0, status);
		
		System.out.println("The Status after addPassages: " + status.getMessage());
		
		cell0.addPassages(passages0, status);
		
		System.out.println("The Status after addPassages a second time: " + status.getMessage() + "\n");
	}
	
	@Test
	public void testAddPassagesZero() {
		MazeCell.Status status = new MazeCell.Status();
		
		System.out.println("The Status before addPassages: " + status.getMessage());
		
		passages0.put(cell1, 0);
		cell0.addPassages(passages0, status);
		
		System.out.println("The Status after addPassages with a passage of travel time zero: " + status.getMessage() + "\n");
	}
	
	@Test
	public void testAddPassagesNegative() {
		MazeCell.Status status = new MazeCell.Status();
		
		System.out.println("The Status before addPassages: " + status.getMessage());
		
		passages0.put(cell1, -1);
		cell0.addPassages(passages0, status);
		
		System.out.println("The Status after addPassages with a passage of travel time negative: " + status.getMessage() + "\n");
	}
}
