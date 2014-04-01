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
	
	//The route that will be built in the routeFirst() method.
	private List<MazeCell> route = new ArrayList<MazeCell>();
	
	//A copy of the route as a HashSet to allow for constant time lookup later on.
	private Set<MazeCell> mousesPath = new HashSet<MazeCell>();
	
	
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
	
	public boolean isValid() {
		return isValid;
	}
	
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
	 * Builds the MazeRoute List and adds it to the MazeRoute to be returned.
	 * @param initialCell The MazeCell that the route is to begin from.
	 * @return The MazeRoute that was built.
	 */
	
	public MazeRoute routeFirst(MazeCell initialCell) throws UninitializedObjectException {
		if (isValid) {
			MazeRoute mazeRoute = new MazeRoute();
			
			if (mazeCells.contains(initialCell)) {
				buildMazeRoute(initialCell);
				
				mazeRoute.addCells(route);
				return mazeRoute;
			}
			else {
				mazeRoute.addCells(route); //route will be empty
				return mazeRoute;
			}
		}
		else {
			throw new UninitializedObjectException("Maze is invalid. Cannot call this method until this Maze is valid.");
		}
	}
	
	
	/**
	 * This method will recursively call itself to build the MazeRoute's List. 
	 * It will end the recursion based upon the MazeCell passed in if it is a dead end, 
	 * the mouse has already been to that MazeCell, or the cell is not a part of this Maze. Otherwise, 
	 * the given cell is added to the route and the next MazeCell is determined and passed back to 
	 * this method.
	 * @param cell The next MazeCell to determine the next action.
	 */
	
	private void buildMazeRoute(MazeCell cell) {
		try {
			if (addThisCell(cell)) {
				route.add(cell);
				mousesPath.add(cell);
				MazeCell nextCell = getNextCell(cell);
				buildMazeRoute(nextCell); //Recursive call to continue building the route.
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
	
	//Check if the cell should be added to the route list.
	private boolean addThisCell(MazeCell cell) throws UninitializedObjectException {
		return mazeCells.contains(cell) && !mousesPath.contains(cell) && !cell.isDeadEnd();
	}
	
	
	/**
	 * This method will use the given cell's connected cells to determine the next cell the mouse is 
	 * to go to on a random basis.
	 * @param cell The cell that the mouse is currently in.
	 * @return The next cell that the mouse went to.
	 */
	
	private MazeCell getNextCell(MazeCell cell) {
		MazeCell nextCell = new MazeCell();
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
		
		return nextCell;
	}
	
	
	/**
	 * This method will put all the items from a given HashSet into an ArrayList.
	 * This way the items are indexed and can be referenced by an integer value.
	 * @param set The HashSet to move to the ArrayList.
	 * @return An ArrayList with every item from the HashSet.
	 */
	
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
