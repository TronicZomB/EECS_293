import java.util.Arrays;
import java.util.List;

/**
 * This class is a subclass of the {@link #ScalarLinearFilter} with a zero output coefficient parameter list. 
 * 
 * @author StevePaley
 */
public class FIRFilter extends ScalarLinearFilter {
	
	/**
	 * Create a new instance of the FIRFilter with input coefficient number list.
	 * 
	 * @param inCoefficients The list of numbers for the input coefficient list.
	 */
	// Complexity = 0
	public FIRFilter(List<Number> inCoefficients) {
		super(inCoefficients, Arrays.asList((Number)0));
	}
}
