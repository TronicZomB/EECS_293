import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class CSVTableJUnit {

	@Test
	public void testConstructorValid() {
		ErrorManager.getInstance().cleanup();
		
		List<DatabaseRow> row = new ArrayList<DatabaseRow>();
		String rowToAdd = "0987654  MULCAHY   BRENDAN SUPER  bm     F13 S14";
		row.add(new DatabaseRow(rowToAdd));
		
		CSVTable table = new CSVTable(row);
		int expected = 0;
		int actual = ErrorManager.getInstance().getExitCode();
		
		assertEquals(expected, actual);
	}

	@Test
	public void testConstructorEmptyList() {
		List<DatabaseRow> row = new ArrayList<DatabaseRow>();
		
		CSVTable table = new CSVTable(row);
		int expected = 7;
		int actual = ErrorManager.getInstance().getExitCode();
		
		assertEquals(expected, actual);
		
		ErrorManager.getInstance().cleanup();
	}
	
	@Test
	public void testConstructorInvalidDatabaseRow() {
		List<DatabaseRow> row = new ArrayList<DatabaseRow>();
		row.add(new DatabaseRow("asdfasdf"));
		
		CSVTable table = new CSVTable(row);
		int expected = 7;
		int actual = ErrorManager.getInstance().getExitCode();
		
		assertEquals(expected, actual);
		
		ErrorManager.getInstance().cleanup();
	}
	
	@Test
	public void testToCSVTableValid() {
		List<DatabaseRow> row = new ArrayList<DatabaseRow>();
		String rowToAdd = "0987654  MULCAHY   BRENDAN SUPER  bm     F13 S14";
		row.add(new DatabaseRow(rowToAdd));
		
		CSVTable table = new CSVTable(row);
		
		String expected = "0987654,Mulcahy,Brendan,Super,bm,S14";
		
		List<DatabaseRow> actual = table.toCSVTable();
		
		for (DatabaseRow actualDR : actual) {
			assertEquals(expected, actualDR.toString());
		}
	}

	@Test
	public void testToCSVTableInvalid() {
		List<DatabaseRow> row = new ArrayList<DatabaseRow>();
		CSVTable table = new CSVTable(row);

		List<DatabaseRow> expected = new ArrayList<DatabaseRow>();
		List<DatabaseRow> actual = table.toCSVTable();
		
		assertEquals(expected, actual);
	}
}
