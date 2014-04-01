import java.util.Arrays;

/**
 * This class is a variant of the {@link #FIRFilter} that has an input coefficient list that is a single 
 * scalar number that will output a {@link #Signal} proportional to the input.
 * 
 * @author StevePaley
 */
public class PureGainFilter extends FIRFilter {
	
	/**
	 * Creates a new instance of the PureGainFilter using the gain number to create a single item list 
	 * that will be used in the super method calculations.
	 * 
	 * @param gain The gain value for this filter.
	 */
	// Complexity = 0
	public PureGainFilter(Number gain) {
		super(Arrays.asList(gain));
	}
}
