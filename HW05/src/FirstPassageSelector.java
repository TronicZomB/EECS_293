
/**
 * Gets the next MazeCell for the MazeRoute in an arbitrary manner.
 */

public class FirstPassageSelector implements PassageSelector {
	//Complexity = 0
		public MazeCell nextCell(MazeCell currentCell) {
			MazeCell nextCell = new MazeCell();
			
			try {
				nextCell = (MazeCell) currentCell.connectedCells().toArray()[0];
			} catch (UninitializedObjectException e) {
				e.printStackTrace();
			}
			
			return nextCell;
		}
}
