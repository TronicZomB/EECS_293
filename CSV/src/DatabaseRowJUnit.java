import static org.junit.Assert.*;

import org.junit.Test;


public class DatabaseRowJUnit {

	@Test
	public void testConstructorValid() {
		ErrorManager.getInstance().cleanup();
		
		String testRow = "0987654  MULCAHY   BRENDAN SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		
		int expected = 0;
		int actual = ErrorManager.getInstance().getExitCode();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testConstructorExtraString() {		
		String testRow = "0987654  MULCAHY   BRENDAN SUPER  bm   gibberish  F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		
		int expected = 7;
		int actual = ErrorManager.getInstance().getExitCode();
		
		assertEquals(expected, actual);
		
		ErrorManager.getInstance().cleanup();
	}
	
	@Test
	public void testConstructorExtraNumbers() {		
		String testRow = "09876123454  MULCAHY   BRENDAN SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		
		int expected = 7;
		int actual = ErrorManager.getInstance().getExitCode();
		
		assertEquals(expected, actual);
		
		ErrorManager.getInstance().cleanup();
	}
	
	@Test
	public void testConstructorLowerCaseName() {		
		String testRow = "0987654  MULCAHY   brendan SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		
		int expected = 7;
		int actual = ErrorManager.getInstance().getExitCode();
		
		assertEquals(expected, actual);
		
		ErrorManager.getInstance().cleanup();
	}
	
	@Test
	public void testConstructorUpperCaseNetId() {		
		String testRow = "0987654  MULCAHY   BRENDAN SUPER  BM     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		
		int expected = 7;
		int actual = ErrorManager.getInstance().getExitCode();
		
		assertEquals(expected, actual);
		
		ErrorManager.getInstance().cleanup();
	}
	
	@Test
	public void testConstructorMissingElement() {		
		String testRow = "0987654  MULCAHY   BRENDAN SUPER  bm      S14";
		DatabaseRow row = new DatabaseRow(testRow);
		
		int expected = 7;
		int actual = ErrorManager.getInstance().getExitCode();
		
		assertEquals(expected, actual);
		
		ErrorManager.getInstance().cleanup();
	}
	
	@Test
	public void testIsValidTrue() {
		String testRow = "0987654  MULCAHY   BRENDAN SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		
		boolean expected = true;
		boolean actual = row.isValid();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testIsValidFalse() {		
		String testRow = "0987654  MULCAHY   BRENDAN SUPER  bm   gibberish  F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		
		boolean expected = false;
		boolean actual = row.isValid();
		
		assertEquals(expected, actual);
	}
	
	@Test
	// The only way to test toCSVRow() is to use toString() so testing both here.
	public void testFormatAsCSVAndToString() {
		String testRow = "0987654  MULCAHY   BRENDAN SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		
		String expectedPreToCSV = "This DatabaseRow has not been formatted as a comma separated value row. "
					+ "\nThe space-formatted row is: 0987654  MULCAHY   BRENDAN SUPER  bm     F13 S14";
		String actualPreToCSV = row.toString();
		assertEquals(expectedPreToCSV, actualPreToCSV);
		
		row.formatAsCSV();
		
		String expectedPostToCSV = "0987654,Mulcahy,Brendan,Super,bm,S14";
		String actualPostToCSV = row.toString();
		assertEquals(expectedPostToCSV, actualPostToCSV);
	}
	
	@Test
	public void testFormatNameValid() {
		// Normal first and middle name situation
		String testRow = "0987654  MULCAHY   BRENDAN SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		DatabaseRow.Test test = row.new Test();
		
		String name = "BRENDAN";
		
		String expected = "Brendan";
		String actual = test.formatName(name);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFormatNameEmpty() {
		// No middle name situation
		String testRow = "0987654  MULCAHY   BRENDAN   bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		DatabaseRow.Test test = row.new Test();
		
		String name = "";
		
		String expected = "";
		String actual = test.formatName(name);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFormatLastNameStandard() {
		String testRow = "0987654  MULCAHY   BRENDAN SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		DatabaseRow.Test test = row.new Test();
		
		String name = "MULCAHY";
		
		String expected = "Mulcahy";
		String actual = test.formatLastName(name);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFormatLastNameMC() {
		String testRow = "1234567  MCKINLEY  NATHAN AWESOME nmc    F13 S15";
		DatabaseRow row = new DatabaseRow(testRow);
		DatabaseRow.Test test = row.new Test();
		
		String name = "MCKINLEY";
		
		String expected = "McKinley";
		String actual = test.formatLastName(name);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFormatLastNameMAC() {
		String testRow = "0987654  MACHARRIS  BOB SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		DatabaseRow.Test test = row.new Test();
		
		String name = "MACHARRIS";
		
		String expected = "MacHarris";
		String actual = test.formatLastName(name);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFormatLastNameAppostraphe() {
		String testRow = "0987654  O'DONNELL   BOB SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		DatabaseRow.Test test = row.new Test();
		
		String name = "O'DONNELL";
		
		String expected = "O'Donnell";
		String actual = test.formatLastName(name);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFormatLastNameHyphen() {
		String testRow = "0987654  SMITH-JOHN   BOB SUPER  bm     F13 S14";
		DatabaseRow row = new DatabaseRow(testRow);
		DatabaseRow.Test test = row.new Test();
		
		String name = "SMITH-JOHN";
		
		String expected = "Smith-John";
		String actual = test.formatLastName(name);
		
		assertEquals(expected, actual);
	}
}
