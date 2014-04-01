import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;


public class SegmentatorJUnit {

	@Test
	/* Structural Basis, Nominal "Good Data" Case, Boundary Case */
	public void testSegmentatorConstructor() {
		Segmentator segmentator = new Segmentator(1, "test.txt");
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	/* Structural Basis, Boundary Case, Bad Data */
	public void testSegmentatorBadLength() {
		// The segment length should be greater than 0. 
		// You cannot segment into substrings of length 0.
		Segmentator segmentator = new Segmentator(0, "test.txt");
	}
	
	@Test
	/* Structural Basis, Take Bad Data in and Make it Usable */
	public void testSegmentatorBadFileName() {
		/*
		 *  The file 'test.txt' exists but not 'test'. This would throw an IOException error when the file 
		 *  is read except that I handle adding the extension if it is not there so no error should be 
		 *  throw in this situation.
		 */
		Segmentator segmentator = new Segmentator(1, "test");
		try {
			segmentator.segmentString();
		} catch (IOException e) {
			//Should not get in here.
			e.printStackTrace();
		}
	}
	
	@Test
	/* Structured Basis, Nominal "Good Data" Case */
	public void testStringSegments() throws IOException {
		Segmentator segmentator = new Segmentator(3, "test.txt");
		
		List<String> expectedSegs = new ArrayList<String>();
		expectedSegs.add("I");
		expectedSegs.add(" ");
		expectedSegs.add("cam");
		expectedSegs.add("e");
		expectedSegs.add(",");
		expectedSegs.add(" ");
		expectedSegs.add("I");
		expectedSegs.add(" ");
		expectedSegs.add("saw");
		expectedSegs.add(",");
		expectedSegs.add(" ");
		expectedSegs.add("I");
		expectedSegs.add(" ");
		expectedSegs.add("lef");
		expectedSegs.add("t");
		expectedSegs.add(".");
		
		assertEquals(expectedSegs, segmentator.segmentString());
	}
	
	@Test (expected = IOException.class)
	/* Structural Basis, Bad Data */
	public void testFileNotFound() throws IOException {
		// 'text.txt' does not exist
		Segmentator segmentator = new Segmentator(1, "text.txt");
		segmentator.segmentString();
	}
	
	@Test
	/* Structural Basis */
	public void testEmptyFile() throws IOException {
		Segmentator segmentator = new Segmentator(1, "emptyFile.txt");
		// An empty file should skip the while !EOF and return an empty list
		List<String> expectedList = new ArrayList<String>();
		List<String> actualList = segmentator.segmentString();
		
		assertEquals(expectedList, actualList);
	}
	
	@Test
	/* Structural Basis, Data Flow */
	public void testSegmentPunctuation() throws IOException {
		/*
		 * This test file should only go through the first if-statement and not the inner if.  
		 * Therefore each piece of punctuation should have its own token and this is independent of
		 * the tokenLength.
		 */
		Random rand = new Random();
		// Get a random integer between 1 and Integer.MAX_VALUE for the tokenLength.
		int tokenLength = rand.nextInt(Integer.MAX_VALUE) + 1;
		Segmentator segmentator = new Segmentator(tokenLength, "punctuation.txt");
		List<String> actualList = segmentator.segmentString();
		List<String> expectedList = new ArrayList<String>();
		expectedList.add(".");
		expectedList.add(",");
		expectedList.add("!");
		expectedList.add("?");
		
		assertEquals(expectedList, actualList);
	}
	
	@Test
	/* Structural Basis, Data Flow */
	public void testLargeTokenLength() throws IOException {
		/*
		 * Segmenting a file of a single word, i.e. no punctuation, into segment length greater than the 
		 * number of characters in the file should put the entire word into a single token and that 
		 * be the only item in the returned list.
		 */
		Segmentator segmentator = new Segmentator(4, "threeChars.txt");
		List<String> actualList = segmentator.segmentString();
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("the");
		
		assertEquals(expectedList, actualList);
	}
	
	@Test
	/* Structural Basis, Data Flow */
	public void testNoPunctuation() throws IOException {
		// Test file contains no punctuation
		Segmentator segmentator = new Segmentator(3, "noPunctuation.txt");
		List<String> actualList = segmentator.segmentString();
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("the");
		expectedList.add("dog");
		expectedList.add("ran");
		expectedList.add("far");
		
		assertEquals(expectedList, actualList);
	}
	
	@Test
	/* Structural Basis, Nominal Case, No other tests needed since segmentInteger() not used in Compression */
	public void testIntegerSegments() throws IOException {
		Segmentator segmentator = new Segmentator(3, "test.txt");
		
		List<Integer> expectedSegs = new ArrayList<Integer>();
		expectedSegs.add(1);
		expectedSegs.add(2);
		expectedSegs.add(3);
		expectedSegs.add(4);
		expectedSegs.add(5);
		expectedSegs.add(2);
		expectedSegs.add(1);
		expectedSegs.add(2);
		expectedSegs.add(6);
		expectedSegs.add(5);
		expectedSegs.add(2);
		expectedSegs.add(1);
		expectedSegs.add(2);
		expectedSegs.add(7);
		expectedSegs.add(8);
		expectedSegs.add(9);
		
		assertEquals(expectedSegs, segmentator.segmentInteger());
	}
	
	@Test
	/* Structural Basis, Nominal Case */
	public void testCompression() {
		Segmentator segmentator = new Segmentator(3, "test.txt");
		List<Integer> actualList = segmentator.compression();
		List<Integer> expectedList = new ArrayList<Integer>();
		expectedList.add(0);
		expectedList.add(1);
		expectedList.add(2);
		expectedList.add(3);
		expectedList.add(4);
		expectedList.add(3);
		expectedList.add(4);
		expectedList.add(1);
		expectedList.add(5);
		expectedList.add(3);
		expectedList.add(2);
		expectedList.add(3);
		expectedList.add(1);
		expectedList.add(6);
		expectedList.add(7);
		expectedList.add(8);
		
		assertEquals(expectedList, actualList);
	}
	
	@Test
	/* Structural Basis */
	public void testCompressEmptyFile() {
		Segmentator segmentator = new Segmentator(1, "emptyFile.txt");
		// Compressing an empty file should return an empty compressed list.
		List<Integer> actualList = segmentator.compression();
		List<Integer> expectedList = new ArrayList<Integer>();
		
		assertEquals(expectedList, actualList);
	}
	
	@Test
	/* Structural Basis */
	public void testAlphaChar() {
		Segmentator seg = new Segmentator(1, "test.txt");
		Segmentator.Test test = seg.new Test();
		
		assertEquals(false, test.testIsPunctuation('a'));
	}
	
	@Test
	/* Structural Basis */
	public void testDigitChar() {
		Segmentator seg = new Segmentator(1, "test.txt");
		Segmentator.Test test = seg.new Test();
		
		assertEquals(false, test.testIsPunctuation('1'));
	}
	
	@Test
	/* Structural Basis */
	public void testPunctuationChar() {
		Segmentator seg = new Segmentator(1, "test.txt");
		Segmentator.Test test = seg.new Test();
		
		assertEquals(true, test.testIsPunctuation('.'));
	}
	
	@Test
	/* Structural Basis */
	public void testResetCompressionVars() {
		Segmentator seg = new Segmentator(1, "test.txt");
		Segmentator.Test test = seg.new Test();
		
		assertTrue(test.testResetCompressionVars());
	}
	
	@Test
	/* Structural Basis */
	public void testAddBuffer() {
		Segmentator seg = new Segmentator(1, "test.txt");
		Segmentator.Test test = seg.new Test();
		
		assertTrue(test.testAddBuffer("abc"));
	}
	
	@Test
	/* Structural Basis */
	public void testAddBufferEmptyString() {
		Segmentator seg = new Segmentator(1, "test.txt");
		Segmentator.Test test = seg.new Test();
		
		assertTrue(test.testAddBuffer(""));
	}
	
	@Test
	/* Structural Basis, Nominal Case */
	public void testCompressInput() throws IOException {
		// The given file will go through both parts of the if-statement.
		Segmentator seg = new Segmentator(3, "test.txt");
		Segmentator.Test test = seg.new Test();
		
		List<String> segmentsToCompress = seg.segmentString();
		
		List<Integer> actualList = test.testCompressInput(segmentsToCompress);
		List<Integer> expectedList = new ArrayList<Integer>();
		expectedList.add(0);
		expectedList.add(1);
		expectedList.add(2);
		expectedList.add(3);
		expectedList.add(4);
		expectedList.add(3);
		expectedList.add(4);
		expectedList.add(1);
		expectedList.add(5);
		expectedList.add(3);
		expectedList.add(2);
		expectedList.add(3);
		expectedList.add(1);
		expectedList.add(6);
		expectedList.add(7);
		expectedList.add(8);
		
		assertEquals(expectedList, actualList);
	}
	
	@Test
	/* Structural Basis, Data Flow */
	public void testCompressInputAllDiffSegs() throws IOException {
		// The file will have all unique segments and thus only go into one section of if-statement (the else, not the if)
		Segmentator seg = new Segmentator(2, "punctuation.txt");
		Segmentator.Test test = seg.new Test();
		
		List<String> segmentsToCompress = seg.segmentString();
		
		List<Integer> actualList = test.testCompressInput(segmentsToCompress);
		List<Integer> expectedList = new ArrayList<Integer>();
		expectedList.add(0);
		expectedList.add(1);
		expectedList.add(2);
		expectedList.add(3);
		
		assertEquals(expectedList, actualList);
	}
	
	@Test
	/* Structural Basis, Data Flow */
	public void testCompressInputAllSameSeg() throws IOException {
		// The file will have all the same segment and will go into the else once and then the if exclusively
		Segmentator seg = new Segmentator(3, "SameSegments.txt");
		Segmentator.Test test = seg.new Test();
		
		List<String> segmentsToCompress = seg.segmentString();
		
		List<Integer> actualList = test.testCompressInput(segmentsToCompress);
		List<Integer> expectedList = new ArrayList<Integer>();
		expectedList.add(0);
		expectedList.add(0);
		expectedList.add(0);
		
		assertEquals(expectedList, actualList);
	}
	
	@Test
	/* Stress Test */
	public void testSegmentStringStress() {
		Segmentator segmentator = new Segmentator(5, "Frankenstein.txt");
		segmentator.compression();
	}
}
