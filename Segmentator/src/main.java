

public class main {
	public static void main(String[] args) {
		int segmentLength = Integer.parseInt(args[0]);
		String file_name = args[1];
		
		//Add .txt to file name if it is not there
		if (!file_name.contains(".txt")) {
			file_name += ".txt";
		}
		
		//Create a Segmentator
		Segmentator segment = new Segmentator(segmentLength, file_name);
		
		segment.printStringSegments();
		segment.printIntegerSegments();
	}
}
