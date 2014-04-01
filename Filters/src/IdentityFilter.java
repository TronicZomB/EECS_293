
/**
 * The IdentityFilter is a {@link #Filter} that will simply output exactly what was input to it.
 * 
 * @author StevePaley
 */
public class IdentityFilter extends Filter {
	
	/**
	 * Creates a new instance of an IdentityFilter
	 */
	// Complexity = 0
	public IdentityFilter() {
		// Empty Constructor
	}
	
	/**
	 * Filters the input {@link #Signal} to this filter.
	 * 
	 * @param input The input {@link #Signal} to filter.
	 * 
	 * @return The input {@link #Signal} exactly as is.
	 */
	// Complexity = 0
	@Override
	public Signal<Number> filter(Signal<Number> input) {
		output = input;
		return output;
	}
}