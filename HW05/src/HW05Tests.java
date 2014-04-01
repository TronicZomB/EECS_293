/**
 * Name: Steve Paley
 * Email: sjp78
 * Description: The test cases for the latest assignment, HW05.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class HW05Tests {
	
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
		passages0.put(cell2, 3);
		passages0.put(cell3, 15);
		passages0.put(cell4, 20);
		cell0.addPassages(passages0);
		
		passages1 = new HashMap<MazeCell, Integer>();
		passages1.put(cell2, 7);
		passages1.put(cell3, 14);
		passages1.put(cell4, 34);
		cell1.addPassages(passages1);
		
		passages2 = new HashMap<MazeCell, Integer>();
		passages2.put(cell3, 8);
		passages2.put(cell4, 67);
		cell2.addPassages(passages2);
		
		passages3 = new HashMap<MazeCell, Integer>();
		passages3.put(cell4, 12);
		cell3.addPassages(passages3);
		
		passages4 = new HashMap<MazeCell, Integer>();
		cell4.addPassages(passages4);
		
		maze = new Maze();
		
		mazeSet.add(cell0);
		mazeSet.add(cell1);
		mazeSet.add(cell2);
		mazeSet.add(cell3);
		mazeSet.add(cell4);
	}

	@Test
	public void testGreedyRoute() {
		Integer expectedTravelTime = 23;
		
		MazeRoute greedy = new MazeRoute();
		
		try {
			maze.addCells(mazeSet);

			greedy = maze.routeGreedy(cell0);
			
			assertEquals(expectedTravelTime, greedy.travelTime());
		} catch (UninitializedObjectException e) {
			//Not happening.
		}		
	}
	
	@Test
	public void testFirstPassageSelector() {
		MazeRoute routeFirst = new MazeRoute();
		MazeRoute firstSelectorRoute = new MazeRoute();
		PassageSelector firstPassageSelector = new FirstPassageSelector();
		
		try {
			maze.addCells(mazeSet);
			
			routeFirst = maze.routeFirst(cell0);
			firstSelectorRoute = maze.route(cell0, firstPassageSelector);
			
			assertEquals(routeFirst.toString(), firstSelectorRoute.toString());
		} catch (UninitializedObjectException e) {
			//Not happening
		}
	}
	
	@Test
	public void testRandomPassageSelector() {
		MazeRoute routeRandom = new MazeRoute();
		MazeRoute randomSelectorRoute = new MazeRoute();
		PassageSelector randomPassageSelector = new RandomPassageSelector();
		
		try {
			maze.addCells(mazeSet);
			
			for (int i = 0; i < 1000; i++) {
				routeRandom = maze.routeRandom(cell0);
				randomSelectorRoute = maze.route(cell0, randomPassageSelector);
				
				if (routeRandom.toString().equals(randomSelectorRoute.toString())){
					assertEquals(routeRandom.toString(), randomSelectorRoute.toString());
					return;
				}
			}
			
			fail();
		} catch (UninitializedObjectException e) {
			//Not happening
		}
	}
	
	@Test
	public void testGreedyPassageSelector() {
		MazeRoute routeGreedy = new MazeRoute();
		MazeRoute greedySelectorRoute = new MazeRoute();
		PassageSelector greedyPassageSelector = new GreedyPassageSelector();
		
		try {
			maze.addCells(mazeSet);
			
			routeGreedy = maze.routeGreedy(cell0);
			greedySelectorRoute = maze.route(cell0, greedyPassageSelector);
			
			assertEquals(routeGreedy.toString(), greedySelectorRoute.toString());
		} catch (UninitializedObjectException e) {
			//Not happening
		}
	}
	
	@Test
	public void testAverageExitTime() {
		Double expectedAverageExitTime = 20.5;
		PassageSelector greedyPassageSelector = new GreedyPassageSelector();
		
		try {
			maze.addCells(mazeSet);
			
			assertEquals(expectedAverageExitTime, maze.averageExitTime(cell4, greedyPassageSelector));
		} catch (UninitializedObjectException e) {
			//Not happening
		}
	}
	
	@Test
	public void testAverageExitTimeMaxValue() {
		Double expectedAverageExitTime = (double) Integer.MAX_VALUE;
		PassageSelector greedyPassageSelector = new GreedyPassageSelector();
		
		MazeCell cell5 = new MazeCell();
		Map<MazeCell, Integer> passages5 = new HashMap<MazeCell, Integer>();
		cell5.addPassages(passages5);
		
		mazeSet.add(cell5);
		
		try {
			maze.addCells(mazeSet);
			
			assertEquals(expectedAverageExitTime, maze.averageExitTime(cell5, greedyPassageSelector));
		} catch (UninitializedObjectException e) {
			//Not happening
		}
	}
}
