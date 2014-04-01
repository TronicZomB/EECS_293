import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;



public class main {
	public static void main(String[] args) {
		int segmentLength = Integer.parseInt(args[0]);
		String file_name = args[1];
		
		//Create a Segmentator
		Segmentator segment = new Segmentator(segmentLength, file_name);
		
		//tokenize the string in the file
		List<String> stringSegments = null;
		try {
			stringSegments = segment.segmentString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//create the integer tokens associated with the string tokens
		List<Integer> intSegments = null;
		try {
			intSegments = segment.segmentInteger();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//print the string tokens
		for (String s : stringSegments) {
			System.out.println(s);
		}
		
		//print the integer tokens
		for (Integer i : intSegments) {
			System.out.print(Integer.toString(i) + " ");
		}
	}
}
