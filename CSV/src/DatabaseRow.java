/*
 * Name: Steve Paley
 * Email: sjp78
 * Description: This is a row entry in the Database
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;



class DatabaseRow {
	/**
	 * This regex follows the pattern of (+ is one or more, * is zero or more):
	 * <br>
	 * digits, space(s)+, upper case letters+, space(s)+,
	 * upper case letters+, space(s)+, upper case letters*,
	 * space(s)+, lower case letters+, space(s)+, "F13", "S##" 
	 */
	private static final String pattern = "(\\d{7})\\s+([A-Z]?'?[A-Z]+-?[A-Z]*)\\s+([A-Z]+)\\s+([A-Z]*)\\s+([a-z]+)\\s+(F13)\\s+([S]\\d\\d)";
	private Pattern spacePattern = Pattern.compile(pattern);
	private Matcher matcher;
	
	// The regex group numbers associated with the space-formatted row
	private static final int ID_NUMBER = 1;
	private static final int LAST_NAME = 2;
	private static final int FIRST_NAME = 3;
	private static final int MIDDLE_NAME = 4;
	private static final int NET_ID = 5;
	// Group 6 is skipped since it always returns F13 and is to be filtered out
	private static final int S_VALUE = 7;
	
	private static ErrorManager em = ErrorManager.getInstance();
	
	private String spaceFormattedRow = "";
	private String csvFormattedRow = "";
	
	private boolean valid = false;
	
	/**
	 * Creates a new instance of a DatabaseRow that matches the regex, {@link #pattern} and makes 
	 * this DatabaseRow valid. 
	 * If the string does not match, the ErrorManager will throw an error, invalid format. 
	 * 
	 * @param row The string row to match.
	 */
	// Complexity = 1
	DatabaseRow(String row) {
		matcher = spacePattern.matcher(row);
		if (matcher.matches()) {
			valid = true;
			spaceFormattedRow = row;
		}
		else {
			em.error("Invalid Row Format: %s", row);
			// The DatabaseRow is not made valid and therefore will not be added to the List, i.e. it will be thrown out.
		}
	}
	
	/**
	 * Returns if this DatabaseRow is valid.
	 * 
	 * @return True if it is valid, false otherwise.
	 */
	// Complexity = 0
	boolean isValid() {
		return valid;
	}
	
	/**
	 * Formats the space-formatted row that this DatabaseRow was created with as a csv formatted row.
	 */
	// Complexity = 0
	void formatAsCSV() {
		//The DatabaseRow does not need to be checked if it is valid since invalid DatabaseRows are not kept.

		csvFormattedRow = matcher.group(ID_NUMBER);
		csvFormattedRow += ",";
		csvFormattedRow += formatLastName(matcher.group(LAST_NAME));
		csvFormattedRow += ",";
		csvFormattedRow += formatName(matcher.group(FIRST_NAME));
		csvFormattedRow += ",";
		csvFormattedRow += formatName(matcher.group(MIDDLE_NAME));
		csvFormattedRow += ",";
		csvFormattedRow += matcher.group(NET_ID);
		csvFormattedRow += ",";
		csvFormattedRow += matcher.group(S_VALUE);

		assert (!csvFormattedRow.contains(" ")) : "The CSV formatted string contained spaces!";
	}
	
	/**
	 * Formats first and middle names with appropriate capitalization.
	 * 
	 * @param name The name to format.
	 * @return The formatted name, or empty string for the case of no middle name.
	 */
	// Complexity = 1
	private String formatName(String name) {
		if (name.equals("")) {
			return name;
		}
		
		String formattedName;
		formattedName = Character.toString(name.charAt(0));
		formattedName += name.substring(1).toLowerCase();
		
		return formattedName;
	}
	
	/**
	 * Formats the last name with appropriate capitalization 
	 * as best as possible according to popular prefixes of last names ("Mc", "Mac" "O' "). 
	 * 
	 * @param name The name to format.
	 * @return The formatted name.
	 */
	// Complexity = 4
	private String formatLastName(String name) {
		String formattedName;
		formattedName = Character.toString(name.charAt(0));
		
		if (name.startsWith("MC")) {
			formattedName += Character.toString(name.charAt(1)).toLowerCase();
			formattedName += Character.toString(name.charAt(2));
			formattedName += name.substring(3).toLowerCase();
		}
		else if (name.startsWith("MAC")) {
			formattedName += Character.toString(name.charAt(1)).toLowerCase();
			formattedName += Character.toString(name.charAt(2)).toLowerCase();
			formattedName += Character.toString(name.charAt(3));
			formattedName += name.substring(4).toLowerCase();
		}
		else if (name.contains("'")) {
			formattedName += Character.toString(name.charAt(1));
			formattedName += Character.toString(name.charAt(2));
			formattedName += name.substring(3).toLowerCase();
		}
		else if (name.contains("-")) {
			formattedName += name.substring(1, name.indexOf("-")).toLowerCase();
			formattedName += name.substring(name.indexOf("-"), name.indexOf("-")+2);
			formattedName += name.substring(name.indexOf("-")+2).toLowerCase();
		}
		else {
			formattedName += name.substring(1).toLowerCase();
		}
		
		return formattedName;
	}
	
	public String toString() {
		if (!csvFormattedRow.isEmpty()) {
			return csvFormattedRow;
		}
		else {
			return "This DatabaseRow has not been formatted as a comma separated value row. "
					+ "\nThe space-formatted row is: " + spaceFormattedRow;
		}
	}
	
	
	public class Test {
		
		Test() { }
		
		public String formatName(String name) {
			return DatabaseRow.this.formatName(name);
		}
		
		public String formatLastName(String name) {
			return DatabaseRow.this.formatLastName(name);
		}
	}
}
