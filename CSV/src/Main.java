import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
	
	/**
	 * Reads from Standard.in and builds a list of space-formatted rows that will make up the table. 
	 * Creates a new csv formatted table.
	 * 
	 * @param args unused
	 */
	// Complexity = 2
	public static void main(String[] args) {
		// Read space-formatted table from standard in
		List<DatabaseRow> rows = new ArrayList<DatabaseRow>();
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()){
			DatabaseRow rowToAdd = new DatabaseRow(in.nextLine());
			if (rowToAdd.isValid()) {
				rows.add(rowToAdd);
			}
        }
		in.close();
		
		CSVTable table = new CSVTable(rows);
		printTable(table.toCSVTable());
	}
	
	
	/**
	 * This method takes a table represented as a List of DatabaseRows and prints them out to Standard.out
	 * 
	 * @param table The table represented as a list to print
	 */
	// Complexity = 1
	public static void printTable(List<DatabaseRow> table) {
		for (DatabaseRow dr : table) {
			assert (!dr.toString().contains(" ")) : "The CSV formatted string contained spaces!";
			System.out.println(dr);
		}
	}
}
