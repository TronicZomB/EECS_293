/**
 * Name: Steve Paley
 * Email: sjp78
 * Description: This class represents a specific route within a maze that has a number of connected 
 * 				cells within the maze.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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

	//Complexity = 3
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

	//Complexity = 0
	public boolean isValid() {
		return isValid;
	}
	
	
	/**
	 * This method creates a copy of the route by copying the MazeCells in the route.
	 * @return A copy of this MazeRoute's route.
	 * @throws UninitializedObjectException If this MazeRoute is invalid.
	 */

	//Complexity = 2
	public List<MazeCell> getCells() throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("MazeRoute invalid");
		}

		List<MazeCell> copiedRoute = new ArrayList<MazeCell>();
			
		for (MazeCell cell : route) {
			copiedRoute.add(new MazeCell(cell));
		}
		
		return copiedRoute;
	}

	//Complexity = 5
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

	//Complexity = 5
	public Integer travelTime() throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("MazeRoute invalid");
		}
			
		if (route.size() == 1) {
			return 0;
		}
		
		int travelTime = 0;
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
	
	
	/**
	 * This method determines a random travel time to go through this MazeRoute.
	 * The times vary from 1 second up to the time given as the passage time to a given 
	 * MazeCell. 
	 * @return The total time to travel this MazeRoute. 
	 * @throws UninitializedObjectException If this MazeRoute is invalid.
	 */

	//Complexity = 5
	public Integer travelTimeRandom() throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("MazeRoute invalid");
		}
		
		//if the route is only one MazeCell the travel time is zero.
		if (route.size() == 1) {
			return 0;
		}
		
		//if the route is more than one MazeCell, then add up the random travel times.
		int randomTravelTime = 0;
		MazeCell prevCell = null;
		
		for (MazeCell cell : route) {
			if (prevCell != null) {
				int currentTime = getRandomTime(prevCell.passageTimeTo(cell));
				if (currentTime == MAX_VALUE) {
					return MAX_VALUE;
				}
				else {
					randomTravelTime += currentTime;
				}
			}
			//else not needed here
				
			prevCell = cell;
		}
		
		return randomTravelTime;
	}
	
	
	/**
	 * This method uses the given travel time for a MazeCell as a maximum to determine a random 
	 * travel time for the passage. 
	 * @param maxTime The maximum travel time through a passage.
	 * @return The random time it takes to travel the passage.
	 */

	//Complexity = 1
	private Integer getRandomTime(Integer maxTime) {
		int randomTime = 0;
		
		if (maxTime == MAX_VALUE) {
			randomTime = MAX_VALUE;
		}
		else {
			Random rand = new Random();
			randomTime = rand.nextInt(maxTime + 1);
		}
		
		return randomTime;
	}
}
