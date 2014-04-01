import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class Segmentator {
	final static String FILE_PATH = "src/res/";

	List<String> stringSegment = new ArrayList<String>();
	List<Integer> intSegment = new ArrayList<Integer>();
	Map<String, Integer> hash = new HashMap<String, Integer>();
	String fileName;
	int segmentLength;
	int tokenCount = 1;
	char[] punctuation = new char[36];


	/**
	 * The Segmentator class calls the methods that will segment the file into substrings.
	 * It then calls the method to create the numerical expression based upon the substrings.
	 * @param segment_length The length that each substring will be at maximum.
	 * @param filename The name of the text file that contains the string to be segmented.
	 */

	Segmentator(int segment_length, String filename) {
		fileName = filename;
		segmentLength = segment_length;

		setPunctuation();

		if (segmentLength <= 0) {
			throw new IndexOutOfBoundsException("Cannot segment a string into token lengths of zero or less");
		}
		
		try {
			segmentString();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		segmentInteger();
	}


	/**
	 * This method will extract the string from the file that was specified. 
	 * It then uses that string and the segment length value to break that string 
	 * into substrings that are segment length or shorter. These substrings are 
	 * placed into the List&#60String&#62 stringSegment list. Each unique substring 
	 * is placed into a HashMap with a unique integer identifier.
	 * @return The stringSegment substring list.
	 * @throws FileNotFoundException
	 */

	List<String> segmentString() throws FileNotFoundException {
		FileInputStream file = new FileInputStream(FILE_PATH + fileName);
		String stringToSegment = fileToString(file);
		String buffer;
		boolean loopBroken = false;
		
		if (stringToSegment.equals("")) {
			throw new NullPointerException("The specified text file was empty.");
		}

		segment_loop:
			do {
				try {
					buffer = stringToSegment.substring(0, segmentLength);

					buffer_loop:
						for (int i = 0; i < segmentLength; i++) {
							for (int j = 0; j < punctuation.length; j++) {
								if (buffer.charAt(i) == punctuation[j]) {
									buffer = buffer.substring(0, buffer.indexOf(punctuation[j]));
									if (buffer.length() == 0) {
										stringSegment.add(Character.toString(punctuation[j]));
										if (!hash.containsKey(Character.toString(punctuation[j]))) {
											hash.put(Character.toString(punctuation[j]), tokenCount++);
										}
										stringToSegment = stringToSegment.substring(1);
									}
									else {
										stringSegment.add(buffer);
										if (!hash.containsKey(buffer)) {
											hash.put(buffer, tokenCount++);	
										}
										stringToSegment = stringToSegment.substring(buffer.length());
									}
									loopBroken = true;
									break buffer_loop;
								}
								loopBroken = false;
							}
						}

					//if (buffer.length() == segmentLength) {
					if (!loopBroken) {
						stringSegment.add(buffer);
						if (!hash.containsKey(buffer)) {
							hash.put(buffer, tokenCount++);
						}
						stringToSegment = stringToSegment.substring(segmentLength);
					}


				} catch (StringIndexOutOfBoundsException obe) {
					//TODO handle punctuation
					buffer = stringToSegment.substring(0);
					String temp = "";

					for (int i = 0; i < buffer.length(); i++) {
						inner_loop:
						for (int j = 0; j < punctuation.length; j++) {
							if (buffer.charAt(i) == punctuation[j]) {
								if (!temp.equals("")) {
									stringSegment.add(temp);
									if (!hash.containsKey(temp)) {
										hash.put(temp, tokenCount++);	
									}
									temp = "";
								}
								
								stringSegment.add(Character.toString(punctuation[j]));
								if (!hash.containsKey(Character.toString(punctuation[j]))) {
									hash.put(Character.toString(punctuation[j]), tokenCount++);
								}
								break inner_loop;
							}
							else if (j == punctuation.length - 1) {
								if (i == buffer.length() - 1) {
									temp += buffer.charAt(i);
									stringSegment.add(temp);
									if (!hash.containsKey(temp)) {
										hash.put(temp, tokenCount++);	
									}
								}
								else {
									temp += buffer.charAt(i);
								}
							}
							
						}
					}

					break segment_loop;
				}

			} while (true);

		return stringSegment;
	}


	/**
	 * Utilizes the String list that was produced by segmentString() along with the HashMap 
	 * that was filled in that method to create the numerical representation of the string list. 
	 * This numerical expression is stored in the List&#60Integer&#62 intSegment variable.
	 * @return The intSegment integer list.
	 */

	List<Integer> segmentInteger() {
		ListIterator<String> iterator = stringSegment.listIterator();
		while (iterator.hasNext()) {
			intSegment.add(hash.get(iterator.next()));
		}

		return intSegment;
	}


	/**
	 * This method converts the FileInputStream to a String Object. 
	 * @param fis The FileInputStream that has opened the text file to be segmented.
	 * @return The text from the file in the form of a String Object.
	 */

	String fileToString(FileInputStream fis) {
		try {
			BufferedReader br = new BufferedReader( new InputStreamReader(fis, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line;

			while((line = br.readLine()) != null ) {
				sb.append(line);
			}

			String stringToReturn = standardizeString(sb.toString());
			return stringToReturn;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return "";
		}
	}


	/**
	 * This method will print all of the string values that were
	 *  segmented out of the text file, separated by a new line. 
	 */

	void printStringSegments() {
		ListIterator<String> iterator = stringSegment.listIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}


	/**
	 * This method will print the segmentation in a numerical expression,
	 *  with each integer separated by a space.
	 */

	void printIntegerSegments() {
		ListIterator<Integer> iterator = intSegment.listIterator();
		while(iterator.hasNext()) {
			System.out.print(iterator.next()+ " ");
		}
	}


	/**
	 * This method will put all of the ASCII characters that are 
	 * considered punctuation into the punctuation char array.
	 */

	void setPunctuation() {
		int i = 0;
		for (int j = 0; j < 128; j++) {
			if (j >= 32 && j <= 47) {
				punctuation[i] = (char) j;
				i++;
			}
			else if (j >= 58 && j <= 64) {
				punctuation[i] = (char) j;
				i++;
			}
			else if (j >= 91 && j <= 96) {
				punctuation[i] = (char) j;
				i++;
			}
			else if (j >= 123 && j <= 127) {
				punctuation[i] = (char) j;
				i++;
			}
		}
	}


	/**
	 * This method will remove any nonprintable characters, such as new line characters, and replace them 
	 * with spaces to create a standard for all strings being segmented.
	 * @param stringToStandardize The string that will be standardized.
	 * @return The standardized string.
	 */

	String standardizeString(String stringToStandardize) {
		for (int i = 0; i < stringToStandardize.length(); i++) {
			if ((stringToStandardize.charAt(i) < (char) 32) || (stringToStandardize.charAt(i) == '\n')) {
				stringToStandardize.replace(stringToStandardize.charAt(i), (char) 32);
			}
		}

		return stringToStandardize;
	}
}
