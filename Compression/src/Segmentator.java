import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

public class Segmentator {
	final static char EndOfFile = (char) -1;

	String fileName;
	int segmentLength;
	
	List<String> stringSegment = new ArrayList<String>();
	
	/**
	 * Compression Variables
	 */
	private static int uniqueSegCount = 1;
	//initialize an empty hash table that uses strings for the keys and integers for values
	private static Map<String, Integer> segmentsMap = new HashMap<String, Integer>();
	//initialize an empty linked list that is the list arrangement
	private static List<Integer> list = new LinkedList<Integer>();
	//initialize an empty linked list that is the output
	private static List<Integer> output = new LinkedList<Integer>();

	/**
	 * The Segmentator class calls the methods that will segment the file into
	 * substrings. It then calls the method to create the numerical expression
	 * based upon the substrings.
	 * 
	 * @param segment_length	The length that each substring will be at maximum.
	 * @param filename		The name of the text file that contains the string to be segmented.
	 */

	// Complexity = 2
	public Segmentator(int segment_length, String filename) {
		fileName = filename;
		segmentLength = segment_length;

		// throw error if the segment k value is less than or equal to zero
		if (segmentLength <= 0) {
			throw new IndexOutOfBoundsException("Cannot segment a string into token lengths of zero or less");
		}

		// Add .txt to file name if it is not there
		if (!fileName.contains(".txt")) {
			fileName += ".txt";
		}
	}

	/**
	 * This method will extract the string from the file that was specified. It
	 * then uses that string and the segment length value to break that string
	 * into substrings that are segment length or shorter. These substrings are
	 * placed into the List&#60String&#62 stringSegment list. Each unique
	 * substring is placed into a HashMap with a unique integer identifier.
	 * 
	 * @return The stringSegment substring list.
	 * @throws IOException
	 */

	// Complexity = 3
	public List<String> segmentString() throws IOException {
		FileInputStream file = new FileInputStream(fileName);

		// The list of string segments will be stored in stringSegment
		// This list needs cleared before to make sure other data is not inside
		stringSegment.clear();
		// The current character the buffered reader is on
		char currentChar = (char) file.read();
		// The current buffer of characters that are the making of the next token
		String buffer = "";

		while (currentChar != EndOfFile) {
			if (isPunctuation(currentChar)) {
				addBuffer(buffer);
				addBuffer(Character.toString(currentChar));
				buffer = "";
			}
			else if (buffer.length() == segmentLength) {
				/*
				 * the character is not punctuation and needs to be added to the
				 * buffer, but the buffer is full and needs added to the list
				 * first.
				 */
				addBuffer(buffer);
				buffer = "";
				buffer += currentChar;
			}
			else {
				buffer += currentChar;
			}

			currentChar = (char) file.read();
		}
		
		addBuffer(buffer);
		
		file.close();
		return stringSegment;
	}
	
	
	/**
	 * Add the given buffer to the stringSegment list and compress it. 
	 * First it checks that the string is not empty.
	 * 
	 * @param buffer The string to be added and compressed.
	 */
	
	// Complexity = 1
	private void addBuffer(String buffer) {
		if (!buffer.isEmpty()) {
			stringSegment.add(buffer);
			compressInput(buffer);
		}
	}

	/**
	 * Utilizes the String list that was produced by segmentString() along with
	 * the HashMap that was filled in that method to create the numerical
	 * representation of the string list. This numerical expression is stored in
	 * the List&#60Integer&#62 intSegment variable.
	 * 
	 * @return The intSegment integer list.
	 * @throws IOException
	 */

	// Complexity = 2
	public List<Integer> segmentInteger() throws IOException {
		List<Integer> intSegment = new ArrayList<Integer>();
		List<String> stringSegment = segmentString();

		Map<String, Integer> segmentHashMap = new HashMap<String, Integer>();
		int tokenCount = 1;

		for (String key : stringSegment) {
			if (segmentHashMap.containsKey(key)) {
				// if the segment has been found before add its hash code to the
				// integer segment's list
				intSegment.add(segmentHashMap.get(key));
			}
			else {
				// else add the segment to the hash map and add the integer to
				// the integer segment list
				segmentHashMap.put(key, tokenCount);
				intSegment.add(tokenCount);
				// increment the number of unique segments in the hash map
				tokenCount++;
			}
		}

		return intSegment;
	}
	
	
	/**
	 * This method will compress the file string using similar methods to bzip2
	 * compression. It will shift the most currently used item to the front of
	 * the compressed list.
	 */
	
	// Complexity = 1
	private void compressInput(String substring) {
		if (segmentsMap.containsKey(substring)) {
			//get the value from the hash table using the key that is this segment
			int segValue = segmentsMap.get(substring);
			int index = list.indexOf(segValue);
			
			//add the outputPosition/indexFromBeginning to the end of the output linked list
			output.add(index);
			
			//remove the old value from the list and add the new value to it
			list.remove(index);
			list.add(0, segValue);
		}
		//else if the segment's numerical value is not in the hash table
		else {
			//add the segment and the segCount as a key/value pair into the hash table
			segmentsMap.put(substring, uniqueSegCount);
			//add the segCount to the end of the linked list
			list.add(0, uniqueSegCount);
			//increment the segCount by 1
			uniqueSegCount++;
			//add the list size - 1 value to the end of the output
			output.add(list.size() - 1);
		}
	}


	/**
	 * This method will call the segmentString method which in turn will compress the input as it is 
	 * segmented. Then this method will print out the compressed output and a legend.
	 */

	// Complexity = 1
	public List<Integer> compression() {
		resetCompressionVars();
		
		//segment the input text
		try {
			segmentString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//output the compression
		System.out.println(segmentsMap.toString());
		for (Integer i : output) {
			System.out.print(Integer.toString(i) + " ");
		}
		
		return output;
	}

	/**
	 * Returns whether or not the given character is a punctuation mark.
	 * 
	 * @param c		The character to test
	 * @return True if it is a punctuation. False if it is not.
	 */

	// Complexity = 1
	private boolean isPunctuation(Character c) {
		return !(Character.isLetterOrDigit(c));
	}
	
	
	/**
	 * This method just resets & clears the global variables used for compressing the input.
	 */
	
	// Complexity = 0
	private void resetCompressionVars() {
		uniqueSegCount = 1;
		segmentsMap.clear();
		list.clear();
		output.clear();
	}
	
	
	
	class Test {
		public Test() {
			//empty constructor.
		}
		
		public boolean testIsPunctuation(char c) {
			return isPunctuation(c);
		}
		
		public boolean testResetCompressionVars() {
			uniqueSegCount = 3;
			segmentsMap.put("asdf", 123);
			list.add(456);
			output.add(0);
			
			resetCompressionVars();
			
			if (uniqueSegCount != 1) {
				return false;
			}
			else if (!segmentsMap.isEmpty()) {
				return false;
			}
			else if (!list.isEmpty()) {
				return false;
			}
			else if (!output.isEmpty()) {
				return false;
			}
			else {
				return true;
			}
		}
		
		public boolean testAddBuffer(String buffer) {
			addBuffer(buffer);
			
			if (!buffer.equals("") && stringSegment.contains(buffer)) {
				return true;
			}
			else if (buffer.equals("") && !stringSegment.contains(buffer)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		public List<Integer> testCompressInput(List<String> segmentsToCompress) {
			resetCompressionVars();
			List<Integer> out = new ArrayList<Integer>();
			
			for (String s : segmentsToCompress) {
				compressInput(s);
			}
			out.addAll(output);
			
			return out;
		}
	}
}
