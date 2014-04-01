/**
 * Name: Steve Paley
 * Email: sjp78
 * Description: This class sets up a Maze with MazeCells and creates a random MazeRoute based on 
 * 				an initial MazeCell.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Maze {
	final static int MAX_VALUE = Integer.MAX_VALUE;
	
	private boolean isValid;
	private Set<MazeCell> mazeCells = new HashSet<MazeCell>();
	
	//The route that will be built in the route() methods.
	private List<MazeCell> route = new ArrayList<MazeCell>();
	
	//A copy of the route as a HashSet to allow for constant time lookup later on.
	private Set<MazeCell> mousesPath = new HashSet<MazeCell>();
	
	public enum routeType {
		FIRST, RANDOM, GREEDY
	}
	
	
	/**
	 * Creates a new Maze that is invalid.
	 */
	
	public Maze() {
		isValid = false;
	}
	
	
	/**
	 * Adds the given set of MazeCells to this maps as long as they are valid.
	 * Sets the Maze to a valid state.
	 * @param cells The MazeCell set passed in that will be this Maze's set of cells.
	 * @return True if the given MazeCell set was set to this Maze's set. 
	 * False if this Maze already had a MazeCell set.
	 * @throws UninitializedObjectException If a cell in the given set is invalid.
	 */
	
	//Complexity = 3
	public boolean addCells(Set<MazeCell> cells) throws UninitializedObjectException {
		if (isValid) {
			return false;
		}
		else {
			for (MazeCell c : cells) {
				if (c.isValid()) {
					mazeCells.add(c);
				}
				else {
					throw new UninitializedObjectException(c.toString() + " was invalid");
				}
			}
			isValid = true;
			return true;
		}
	}
	
	
	/**
	 * Returns if this Maze is valid.
	 * @return True if the Maze is valid. False if the Maze is invalid.
	 */

	//Complexity = 0
	public boolean isValid() {
		return isValid;
	}

	//Complexity = 4
	public String toString() {
		if (isValid) {
			String maze = "";
			Iterator<MazeCell> iterator = mazeCells.iterator();
			
			while (iterator.hasNext()) {
				MazeCell c = iterator.next();
				Map<MazeCell, Integer> map = new HashMap<MazeCell, Integer>();
				maze += c.toString() + " passages:\n";
				
				try {
					map = c.passages();
				} catch (UninitializedObjectException e) {
					e.printStackTrace();
				}
				
				if (!map.isEmpty()) {
					for (MazeCell cell : map.keySet()) {
						maze += "\t" + cell.toString() + ", " + map.get(cell) + " seconds\n";
					}
				}
				else {
					maze += "\tNo passages\n";
				}
			}
			
			return maze;
		}
		else {
			return "Uninitialized Maze";
		}
	}
	
	
	/**
	 * Calculates the average exit time from every cell that is in the Maze besides the given outside cell.
	 * Creates each route based on the given PassageSelector and checks if the route ends on the given 
	 * outside MazeCell. If a route does not end on the given outside MazeCell it is ignored. If not one 
	 * MazeRoute exists to the given outside MazeCell, this returns MAX_VALUE.
	 * @param outside The MazeCell that is to be the last in the MazeRoute travel times considered.
	 * @param passageSelector Determines the types of MazeRoutes to build for to get the travel times from.
	 * @return The average of travel/exit time for each route that ends with the given MazeCell. 
	 * @throws UninitializedObjectException If the Maze is invalid.
	 */
	
	//Complexity = 4
	public Double averageExitTime(MazeCell outside, PassageSelector passageSelector) throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("Maze is invalid");
		}
		
		Double averageExitTime = 0.0;
		Double allTravelTimes = 0.0;
		Double totalRoutes = 0.0;
		
		Set<MazeCell> initialCells = getInitialCells(outside);
		
		//iterate through all of the initial cells to create routes for them
		for (MazeCell cell : initialCells) {
			MazeRoute thisRoute = route(cell, passageSelector);
			
			//make sure the route has the outside cell
			if (isLastCellOutsideCell(thisRoute, outside)) {
				totalRoutes++;
				allTravelTimes += thisRoute.travelTime();
			}
			else {
				return (double) MAX_VALUE;
			}
		}
		
		
		averageExitTime = allTravelTimes / totalRoutes;
		
		return averageExitTime;
	}
	
	
	/**
	 * Creates a copy of this Maze's cells and removes the outside MazeCell given in averageExitTime().
	 * This way that MazeCell will not be considered for the MazeRoutes to be averaged.
	 * @param outside The MazeCell to be removed from the copy of this Maze's cells.
	 * @return A list of all the MazeCells that will be initial cells in MazeRoutes to average the time.
	 */
	
	//Complexity = 1
	private Set<MazeCell> getInitialCells(MazeCell outside) {
		Set<MazeCell> initialCells = new HashSet<MazeCell>();
		initialCells.addAll(mazeCells);
		initialCells.remove(outside);
		
		return initialCells;
	}
	
	
	/**
	 * Checks to make sure that the last cell in the MazeRoute is the outside cell given in 
	 * averageExitTime().
	 * @param route The MazeRoute to check the last element in.
	 * @param outside The MazeCell that should be last in the MazeRoute.
	 * @return True if the last MazeCell in the MazeRoute is the given one. False if it is not the last 
	 * MazeCell in the MazeRoute.
	 * @throws UninitializedObjectException If the MazeRoute is invalid.
	 */
	
	//Complexity = 0
	private Boolean isLastCellOutsideCell(MazeRoute route, MazeCell outside) throws UninitializedObjectException {
		return route.getCells().get(route.getCells().size() - 1).equals(outside);
	}
	
	
	/**
	 * This method creates a MazeRoute starting from the given initial MazeCell. The PassageSelector will 
	 * determine how the next cell will be determined. 
	 * @param initialCell The first MazeCell in the MazeRoute.
	 * @param passageSelector Determines how to build the MazeRoute.
	 * @return The MazeRoute that was built.
	 * @throws UninitializedObjectException If the Maze is invalid
	 */
	
	//Complexity = 2
	public MazeRoute route(MazeCell initialCell, PassageSelector passageSelector) throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("Maze is invalid");
		}
		
		MazeRoute mazeRoute = new MazeRoute();
		route.removeAll(route);
		mousesPath.removeAll(mousesPath);
		
		if (mazeCells.contains(initialCell)) {
			buildMazeRoute(initialCell, passageSelector);
			
			mazeRoute.addCells(route);
		}
		else {
			mazeRoute.addCells(route); //route will be empty
		}
		
		return mazeRoute;
	}
	
	
	/**
	 * The method recursively builds a MazeRoute. It will pick the next cell to add to the route 
	 * based upon the PassageSelector that is passed in.
	 * @param cell The next cell to test for an end condition or add to the MazeRoute. 
	 * @param passageSelector The type of MazeRoute to build, arbitrary, random, or smallest travel time.
	 */
	
	//Complexity = 3
	private void buildMazeRoute(MazeCell cell, PassageSelector passageSelector) {
		try {
			if (addThisCell(cell)) {
				route.add(cell);
				mousesPath.add(cell);
				MazeCell nextCell = passageSelector.nextCell(cell);
				buildMazeRoute(nextCell, passageSelector); //Recursive call to continue building the route.
			}
			else if (!mazeCells.contains(cell)) {
				//If the cell is not a part of this Maze, end the recursion and remove all of the MazeCells in the route.
				route.removeAll(route);
			}
			else if (mousesPath.contains(cell)) {
				//If the mouse has been to this cell, end the recursion, but don't add the cell to the list.
			}
			else {
				//The cell is a dead end, end the recursion and add the cell to the route.
				route.add(cell);
				mousesPath.add(cell);
			}
		} catch (UninitializedObjectException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @deprecated
	 * This method sets up a MazeRoute that is made up of the first MazeCell returned that is 
	 * connected to the present MazeCell in the route. 
	 * @param initialCell The first MazeCell to start the MazeRoute from.
	 * @return The MazeRoute that was built.
	 * @throws UninitializedObjectException If this Maze is not valid. 
	 */

	//Complexity = 2
	public MazeRoute routeFirst(MazeCell initialCell) throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("Maze is invalid. Cannot call this method until this Maze is valid.");
		}
		
		MazeRoute mazeRoute = new MazeRoute();
		route.removeAll(route);
		mousesPath.removeAll(mousesPath);
		
		if (mazeCells.contains(initialCell)) {
			buildMazeRoute(initialCell, routeType.FIRST);
			
			mazeRoute.addCells(route);
		}
		else {
			mazeRoute.addCells(route); //route will be empty
		}
		
		return mazeRoute;
	}
	
	
	/**
	 * @deprecated
	 * Builds the MazeRoute List randomly and adds it to the MazeRoute to be returned.
	 * @param initialCell The MazeCell that the route is to begin from.
	 * @return The MazeRoute that was built.
	 * @throws UninitializedObjectException If the Maze is invalid.
	 */

	//Complexity = 2
	public MazeRoute routeRandom(MazeCell initialCell) throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("Maze is invalid. Cannot call this method until this Maze is valid.");
		}

		MazeRoute mazeRoute = new MazeRoute();
		route.removeAll(route);
		mousesPath.removeAll(mousesPath);
			
		if (mazeCells.contains(initialCell)) {
			buildMazeRoute(initialCell, routeType.RANDOM);
			
			mazeRoute.addCells(route);
		}
		else {
			mazeRoute.addCells(route); //route will be empty
		}
		
		return mazeRoute;
	}
	
	
	/**
	 * @deprecated
	 * Builds the MazeRoute List by finding the passage with the lowest travel time 
	 * and adds it to the MazeRoute to be returned.
	 * @param initialCell The MazeCell that the route is to begin from.
	 * @return The MazeRoute that was built.
	 * @throws UninitializedObjectException If the Maze is invalid.
	 */
	
	//Complexity = 2
	public MazeRoute routeGreedy(MazeCell initialCell) throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("Maze is invalid. Cannot call this method until this Maze is valid.");
		}
		
		MazeRoute mazeRoute = new MazeRoute();
		route.removeAll(route);
		mousesPath.removeAll(mousesPath);
			
		if (mazeCells.contains(initialCell)) {
			buildMazeRoute(initialCell, routeType.GREEDY);
			
			mazeRoute.addCells(route);
		}
		else {
			mazeRoute.addCells(route); //route will be empty
		}
		
		return mazeRoute;
	}
	
	
	/**
	 * This method will recursively call itself to build the arbitrary, random, or greedy MazeRoute that 
	 * is made up from the first returned MazeCell from the connected cells of the 
	 * last MazeCell added to the MazeRoute. The type of route is determined by the routeType parameter. 
	 * This routeType is passed onto the getNextCell() in order to determine the cell.
	 * @param cell The next cell to test for an end condition or add to the MazeRoute. 
	 * @param type The type of MazeRoute this will build.
	 */

	//Complexity = 3
	private void buildMazeRoute(MazeCell cell, routeType type) {
		try {
			if (addThisCell(cell)) {
				route.add(cell);
				mousesPath.add(cell);
				MazeCell nextCell = getNextCell(cell, type);
				buildMazeRoute(nextCell, type); //Recursive call to continue building the route.
			}
			else if (!mazeCells.contains(cell)) {
				//If the cell is not a part of this Maze, end the recursion and remove all of the MazeCells in the route.
				route.removeAll(route);
			}
			else if (mousesPath.contains(cell)) {
				//If the mouse has been to this cell, end the recursion, but don't add the cell to the list.
			}
			else {
				//The cell is a dead end, end the recursion and add the cell to the route.
				route.add(cell);
				mousesPath.add(cell);
			}
		} catch (UninitializedObjectException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method uses the passed in MazeCell to determine the next MazeCell to add to the MazeRoute.
	 * The next cell returned is determined by the routeType.
	 * @param cell The MazeCell that is the last one in the MazeRoute.
	 * @return The next MazeCell to be added to the MazeRoute.
	 */

	//Complexity = 5
	private MazeCell getNextCell(MazeCell cell, routeType type) {
		MazeCell nextCell = new MazeCell();
		
		switch (type) {
		case FIRST:		
			try {
				nextCell = (MazeCell) cell.connectedCells().toArray()[0];
			} catch (UninitializedObjectException e) {
				e.printStackTrace();
			}
			break;
		case RANDOM:
			Set<MazeCell> nextPossibilities = new HashSet<MazeCell>();
			List<MazeCell> indexedPosibilities = new ArrayList<MazeCell>();
			int rnMax, rn;
			
			try {
				//Get the candidates for next cells to visit.
				nextPossibilities = cell.connectedCells();
			} catch (UninitializedObjectException e) {
				e.printStackTrace();
			}
			
			//Make the candidates an indexed list for use with the random number.
			indexedPosibilities = setToList(nextPossibilities);
			
			rnMax = indexedPosibilities.size();
			Random rand = new Random();
			rn = rand.nextInt(rnMax);
			
			nextCell = indexedPosibilities.get(rn);
			break;
		case GREEDY:
			Map<MazeCell, Integer> nextCellPossibilities = new HashMap<MazeCell, Integer>();
			Map<MazeCell, Integer> emptyMap = new HashMap<MazeCell, Integer>();
			MazeCell.Status status = new MazeCell.Status();
			
			MazeCell lowestTimeCell = new MazeCell();
			lowestTimeCell.addPassages(emptyMap, status);
			
			try {
				nextCellPossibilities = cell.passages();
			
				for (MazeCell key : nextCellPossibilities.keySet()) {
					if (cell.passageTimeTo(key) < cell.passageTimeTo(lowestTimeCell)) {
						lowestTimeCell = key;
					}
				}
			} catch (UninitializedObjectException e) {
				e.printStackTrace();
			}
			
			nextCell = lowestTimeCell;
			break;
		}
		
		return nextCell;
	}
	
	
	/**
	 * This method checks if the given MazeCell should be added to the MazeRoute.
	 * @param cell The MazeCell to be tested.
	 * @return True if the MazeCell should be added. False if it should not be added.
	 * @throws UninitializedObjectException If the cell is invalid.
	 */

	//Complexity = 2
	private boolean addThisCell(MazeCell cell) throws UninitializedObjectException {
		return mazeCells.contains(cell) && !mousesPath.contains(cell) && !cell.isDeadEnd();
	}
	
	
	/**
	 * This method will put all the items from a given HashSet into an ArrayList.
	 * This way the items are indexed and can be referenced by an integer value.
	 * @param set The HashSet to move to the ArrayList.
	 * @return An ArrayList with every item from the HashSet.
	 */

	//Complexity = 1
	private List<MazeCell> setToList(Set<MazeCell> set) {
		List<MazeCell> list = new ArrayList<MazeCell>();
		Iterator<MazeCell> iterator = set.iterator();
		
		while (iterator.hasNext()) {
			MazeCell c = iterator.next();
			list.add(c);
		}
		
		return list;
	}
}
