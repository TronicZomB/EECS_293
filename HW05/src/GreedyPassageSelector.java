import java.util.HashMap;
import java.util.Map;

/**
 * Gets the next MazeCell for the MazeRoute by finding the smallest travel time in the passages.
 */

class GreedyPassageSelector implements PassageSelector {
	//Complexity = 2
	public MazeCell nextCell(MazeCell currentCell) {
		MazeCell nextCell = new MazeCell();
		
		Map<MazeCell, Integer> nextCellPossibilities = new HashMap<MazeCell, Integer>();
		Map<MazeCell, Integer> emptyMap = new HashMap<MazeCell, Integer>();
		MazeCell.Status status = new MazeCell.Status();
		
		MazeCell lowestTimeCell = new MazeCell();
		lowestTimeCell.addPassages(emptyMap, status);
		
		try {
			nextCellPossibilities = currentCell.passages();
		
			for (MazeCell key : nextCellPossibilities.keySet()) {
				if (currentCell.passageTimeTo(key) < currentCell.passageTimeTo(lowestTimeCell)) {
					lowestTimeCell = key;
				}
			}
		} catch (UninitializedObjectException e) {
			e.printStackTrace();
		}
		
		nextCell = lowestTimeCell;
		
		return nextCell;
	}
}