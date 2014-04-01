/**
 * Name: Steve Paley
 * Email: sjp78
 * Description: This class represents a specific route within a maze that has a number of connected 
 * 				cells within the maze.
 */

import java.util.ArrayList;
import java.util.List;


public class MazeRoute {
	final static int MAX_VALUE = Integer.MAX_VALUE;
	
	private boolean isValid;
	private List<MazeCell> route;
	
	
	/**
	 * Initializes a new MazeRoute that is invalid.
	 */
	
	public MazeRoute() {
		isValid = false;
		route = new ArrayList<MazeCell>();
	}
	
	
	/**
	 * This method will add the given route to this MazeRoute's route list, unless this MazeRoute
	 * already has a route list. Adding the route to this MazeRoute sets this MazeRoute as valid.
	 * @param route The route to be set as this MazeRoute's route.
	 * @return True if the route was added. False if the MazeRoute was already valid with a route.
	 * @throws UninitializedObjectException If there is an invalid MazeCell in the route.
	 */
	
	public boolean addCells(List<MazeCell> route) throws UninitializedObjectException {
		if (isValid) {
			return false;
		}
		else {
			//this checks to make sure each MazeCell in the given route is a valid MazeCell.
			//if it is, add it to this MazeRoute's route, else throw exception.
			for (MazeCell cell : route) {
				if (cell.isValid()) {
					this.route.add(cell);
				}
				else {
					throw new UninitializedObjectException("MazeCell: " + cell.toString() + " invalid");
				}
			}
			isValid = true;
			return true;
		}
	}
	
	
	/**
	 * This method checks if the MazeRoute is valid.
	 * @return True if the MazeRoute is valid. False if the MazeRoute is not valid.
	 */
	
	public boolean isValid() {
		return isValid;
	}
	
	
	/**
	 * This method creates a copy of the route by copying the MazeCells in the route.
	 * @return A copy of this MazeRoute's route.
	 * @throws UninitializedObjectException If this MazeRoute is invalid.
	 */
	
	public List<MazeCell> getCells() throws UninitializedObjectException {
		if (isValid) {
			List<MazeCell> copiedRoute = new ArrayList<MazeCell>();
			
			for (MazeCell cell : route) {
				copiedRoute.add(new MazeCell(cell));
			}
			
			return copiedRoute;
		}
		else {
			throw new UninitializedObjectException("MazeRoute invalid");
		}
	}
	
	@Override
	public String toString() {
		if (isValid) {
			String readableRoute = "";
			MazeCell prevCell = null;
		
			if (route.size() == 1) {
				readableRoute = route.get(0).toString() + " is the only MazeCell in this route.\n"
						+ "The travel time of this route is 0 seconds.\n";
				return readableRoute;
			}
			
			for (MazeCell cell : route) {
				if (prevCell != null) {
					try {
						int time = prevCell.passageTimeTo(cell);
						if (time < MAX_VALUE) {
							readableRoute += prevCell.toString();
							readableRoute += " -- " + time + " seconds --> ";
							readableRoute += cell.toString() + "\n";
						}
						else {
							readableRoute += prevCell.toString();
							readableRoute += " -- NO PASSAGE --> ";
							readableRoute += cell.toString() + "\n";
						}
					} catch (UninitializedObjectException e) {
						//unreachable, the route has been authenticated by the addCells() method.
					}
				}
				//else not needed here
			
				prevCell = cell;
			}
		
			return readableRoute;
		}
		else {
			return "MazeRoute invalid";
		}
	}
	
	
	/**
	 * This method gets the total travel time for this MazeRoute's route list.
	 * @return The total travel time in seconds for this MazeRoute.
	 * @throws UninitializedObjectException If the MazeRoute is invalid.
	 */
	
	public Integer travelTime() throws UninitializedObjectException {
		if (isValid) {
			int travelTime = 0;
			if (route.size() == 1) {
				return travelTime;
			}
			else {
				MazeCell prevCell = null;
				
				for (MazeCell cell : route) {
					if (prevCell != null) {
						int currentTime = prevCell.passageTimeTo(cell);
						if (currentTime == MAX_VALUE) {
							return MAX_VALUE;
						}
						else {
							travelTime += currentTime;
						}
					}
					//else not needed here
					
					prevCell = cell;
				}
				
				return travelTime;
			}
		}
		else {
			throw new UninitializedObjectException("MazeRoute invalid");
		}
	}
}
