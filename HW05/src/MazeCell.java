/**
 * Name: Steve Paley
 * Email: sjp78
 * Description: This class represents a specific cell within a maze that has a number of passages 
 * 				to other cells within the maze.
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MazeCell {
	final static int MAX_VALUE = Integer.MAX_VALUE;
	
	private boolean isValid;
	private int uniqueCode;
	private Map<MazeCell, Integer> passages;
	private static int nextHashCode;
	
	
	/**
	 * This method initializes an invalid MazeCell.
	 */
	
	public MazeCell() {
		isValid = false;
		passages = new HashMap<MazeCell, Integer>();
		uniqueCode = nextHashCode;
		nextHashCode++;
	}
	
	
	/**
	 * Creates a MazeCell that is a copy of the given MazeCell.
	 * @param copyCell The MazeCell that is to be copied.
	 */
	
	//no modifier here so that this is accessible to the class and package only
	MazeCell(MazeCell copyCell) {
		this.isValid = copyCell.isValid;
		this.passages = copyCell.passages;
		this.uniqueCode = copyCell.uniqueCode;
	}
	
	
	/**
	 * @deprecated
	 * This method sets the parameter map as this MazeCell's passages if this MazeCell
	 *  has not yet received a map of passages to other MazeCells.
	 *  By setting the passages, that makes this cell a valid 
	 *  MazeCell and sets the isValid boolean to true.
	 * @param passages The Map of passages that may be set as this MazeCell's passages to other MazeCells.
	 * @return True if the MazeCell has been made valid and the passages are set for this MazeCell.
	 * False if the MazeCell has been made valid previously
	 */

	//Complexity = 1
	public boolean addPassages(Map<MazeCell, Integer> passages) {
		if (isValid) {
			/*
			 * if passages already exist for this cell, then this cell has been made valid and 
			 * passages cannot be added to this cell
			 */
			return false;
		}
		else {
			/*
			 * if passages do not exist, set this cell's passages to the given parameters passages map
			 * make this MazeCell valid at the same time
			 */
			this.passages = passages;
			isValid = true;
			return true;
		}
	}
	
	
	/**
	 * Takes the given passages HashMap and sets it as this MazeCell's passages. If the MazeCell is 
	 * already valid it sets the status to ALREADY_VALID. If any of the passages have a travel time 
	 * of zero or negative then the status is set to INVALID_TIME. Otherwise the status is OK and the 
	 * MazeCell is made valid.
	 * @param passages The HashMap of passages to other MazeCells to set as this MazeCell's passages.
	 * @param status The status variable that is set based on the MazeCell and the passages.
	 */

	//Complexity = 3
	public void addPassages(Map<MazeCell, Integer> passages, Status status) {
		if (isValid) {
			status.set(Status.Code.ALREADY_VALID);
		}
		else {
			for (MazeCell key : passages.keySet()) {
				if (passages.get(key) > 0) {
					this.passages.put(key, passages.get(key));
				}
				else {
					status.set(Status.Code.INVALID_TIME);
				}
			}
			
			isValid = true;
		}
	}
	
	
	/**
	 * Checks if this MazeCell has been made valid (has passages to other MazeCells).
	 * @return True if this MazeCell is valid. False if this MazeCell is not valid.
	 */

	//Complexity = 0
	public boolean isValid() {
		return isValid;
	}
	
	//Complexity = 0
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uniqueCode;
		return result;
	}
	
	
	/**
	 * This method returns all of the valid passages (passage time is less than MAX_VALUE)
	 * from the current MazeCell to all other MazeCells.
	 * @return A copy of the passages map that contains only valid passages
	 * @throws UninitializedObjectException If the MazeCell is invalid. 
	 */

	//Complexity = 3
	public Map<MazeCell, Integer> passages() throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("MazeCell: " + this.toString() + " invalid");
		}
		
		Map<MazeCell, Integer> passagesCopy = new HashMap<MazeCell, Integer>();
			
		//iterate over the passages map and add only valid passages to the copy map to be returned
		for (Map.Entry<MazeCell, Integer> cell : passages.entrySet()) {
			MazeCell currentCell = cell.getKey();
			if (isValidPassageTime(currentCell)) {
				MazeCell copiedCell = new MazeCell(currentCell);
				passagesCopy.put(copiedCell, passages.get(currentCell));
			}
		}
			
		return passagesCopy;
	}
	
	
	/**
	 * This method gets the passage time in seconds from this MazeCell to the given MazeCell.
	 * @param cell The MazeCell to check for a passage to.
	 * @return The time from one MazeCell to another, or MAX_VALUE if there is no direct path between them.
	 * @throws UninitializedObjectException If the given MazeCell is invalid
	 */

	//Complexity = 2
	public Integer passageTimeTo(MazeCell cell) throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("MazeCell: " + this.toString() + " invalid");	
		}

		if (passages.containsKey(cell)) {
			//returns the integer time (in seconds) from the current cell to the given cell
			return passages.get(cell);
		}
		else {
			//if there is not a direct path, return the MAX_VALUE for the time
			return MAX_VALUE;
		}
	}
	
	
	/**
	 * This method creates a set of all of the MazeCells that are connected to this MazeCell and
	 * have valid passage times to these MazeCells. 
	 * @return A copy of all the MazeCells with valid passage times.
	 * @throws UninitializedObjectException If the MazeCell is invalid
	 */

	//Complexity = 3
	public Set<MazeCell> connectedCells() throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("MazeCell: " + this.toString() + " invalid");
		}

		Set<MazeCell> setCopy = new HashSet<MazeCell>();
			
		/*iterate over the passages map and add only cells with valid passages
		** to the copy set to be returned */
		for (Map.Entry<MazeCell, Integer> cell : passages.entrySet()) {
			MazeCell currentCell = cell.getKey();
			//Could use passageTimeTo(MazeCell) but seems more inefficient
			if (isValidPassageTime(currentCell)) {
				MazeCell copiedCell = new MazeCell(currentCell);
				setCopy.add(copiedCell);
			}
		}
			
		return setCopy;
	}
	
	
	/**
	 * This method checks if the current MazeCell does not lead to any other MazeCells.
	 * @return True if this MazeCell does not lead to other MazeCells. 
	 * False if this MazeCell does lead to other MazeCells.
	 * @throws UninitializedObjectException If the current MazeCell is invalid.
	 */

	//Complexity = 1
	public boolean isDeadEnd() throws UninitializedObjectException {
		if (!isValid) {
			throw new UninitializedObjectException("MazeCell: " + this.toString() + " invalid");
		}

		Set<MazeCell> currentValidCellsSet = connectedCells();
			
		return currentValidCellsSet.isEmpty();
	}
	
	//Complexity = 1
	@Override
	public String toString() {
		if (isValid) {
			String name = "MazeCell#" + uniqueCode;
			return name;
		}
		else {
			return "Uninitialized MazeCell";
		}
	}
	
	
	/**
	 * This method checks if if the given MazeCell has a passage time of less than MAX_VALUE.
	 * @param cell The MazeCell to check passage time to.
	 * @return True if the passage time is below MAX_VALUE. False if the passage time is above MAX_VALUE.
	 */

	//Complexity = 1
	private boolean isValidPassageTime(MazeCell cell) {
		return (passages.get(cell) < MAX_VALUE);
	}

	//Complexity = 3
	@Override
	public boolean equals(Object o) {
		MazeCell cell = (MazeCell) o;
		
		if (this.isValid != cell.isValid) {
			return false;
		}
		else if (this.uniqueCode != cell.uniqueCode) {
			return false;
		}
		else if (this.passages != cell.passages) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
	/**
	 * This class is used for the status of the passages being added to a MazeCell.
	 * The Status is ALREADY_VALID if the MazeCell is already valid.
	 * The Status is INVALID_TIME if the passage time to a given MazeCell is zero or negative.
	 * Otherwise the Status is OK. There is a getter and setter method within this class as well
	 * as a getMessage method that returns a message based on the status.
	 */
	
	static class Status {
		Code code;
		
		public enum Code {
			OK, ALREADY_VALID, INVALID_TIME
		}
		
		public Status() {
			this.code = Code.OK;
		}

		//Complexity = 3
		public String getMessage() {
			String message = "";
			
			switch (this.code) {
			case OK:
				message = "The MazeCell is ok.";
				break;
			case ALREADY_VALID:
				message = "This MazeCell was already valid.";
				break;
			case INVALID_TIME:
				message = "The travel time is invalid.";
				break;
			}
			
			return message;
		}

		//Complexity = 0
		public void set(Code code) {
			this.code = code;
		}

		//Complexity = 0
		public Code get() {
			return this.code;
		}
	}
}
