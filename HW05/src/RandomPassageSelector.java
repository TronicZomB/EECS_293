import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;



/**
 * Gets the next MazeCell for the MazeRoute in a random manner.
 */

class RandomPassageSelector implements PassageSelector {
	//Complexity = 0
	public MazeCell nextCell(MazeCell currentCell) {
		MazeCell nextCell = new MazeCell();
		
		Set<MazeCell> nextPossibilities = new HashSet<MazeCell>();
		List<MazeCell> indexedPosibilities = new ArrayList<MazeCell>();
		int rnMax, rn;
		
		try {
			//Get the candidates for next cells to visit.
			nextPossibilities = currentCell.connectedCells();
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