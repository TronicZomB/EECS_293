/*
 * Name: Steve Paley
 * Email: sjp78
 * Description: This database table object is created with strings of space-formatted rows and 
 *              converts the rows into csv formatted rows.
 */

import java.util.ArrayList;
import java.util.List;


public class CSVTable {
	/** The list of rows that make up that make up this database table */
	List<DatabaseRow> rows = new ArrayList<DatabaseRow>();

	private static ErrorManager em = ErrorManager.getInstance();
	
	/**
	 * Constructor that takes in a list of DatabaseRows and sets the list as this tables row list.
	 * 
	 * @param rows The list of space-formatted rows to be formatted as csv for this table.
	 */
	// Complexity = 3
	public CSVTable(List<DatabaseRow> rows) {
		if(!rows.isEmpty()) {
			for (DatabaseRow dr : rows) {
				if (dr.isValid()) {
					this.rows.add(dr);
				}
				else {
					em.error("There was an invalid DatabaseRow: %s", dr);
				}
			}
		}
		else {
			em.error("The List was empty. No input to format as csv.");
		}
	}
	
	/**
	 * This method goes through each row in this table and formats it as comma separated.
	 * 
	 * @return The list of DatabaseRows that have been formatted as comma separated values.
	 */
	// Complexity = 1
	public List<DatabaseRow> toCSVTable() {
		for (DatabaseRow spaceFormattedRow : rows) {
			spaceFormattedRow.formatAsCSV();
			assert (!spaceFormattedRow.toString().contains(" ")) : "The space-formatted row was not properly formatted as CSV";
		}
		
		return rows;
	}
}
