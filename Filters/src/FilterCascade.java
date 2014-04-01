import java.util.ArrayList;
import java.util.List;

/**
 * This {@link #Filter} subclass will take an input {@link #Signal} and filter it through all of the filters 
 * that are contained within the cascade and the output of the last filter in the cascade is 
 * the output of this filter subclass.
 * 
 * @author StevePaley
 */
public class FilterCascade extends Filter {
	private List<Filter> filters = new ArrayList<Filter>();
	
	/**
	 * Creates a new instance of a FilterCascade, multiple filters feeding into the next.
	 * There is a minimum requirement of at least two filters to be in the cascade. There is 
	 * no maximum number.
	 * 
	 * @param f1 The first filter in the cascade
	 * @param f2 The second filter in series in the cascade
	 * @param f The rest of the filters to be added to the cascade. 
	 * 			This can be zero or more filters, accessed via lists.
	 */
	// Complexity = 1
	public FilterCascade(Filter f1, Filter f2, Filter... f) {
		filters.add(f1);
		filters.add(f2);
		
		// Add all the rest of the filters passed in through 'f' to the filter list
		for (Filter filter : f) {
			filters.add(filter);
		}
	}
	
	/**
	 * Filters the input {@link #Signal} by feeding it into the first filter in the list and feeding 
	 * the subsequent outputs to the next filter in the list.
	 * 
	 * @param input The {@link #Signal} that will be filtered.
	 * 
	 * @return The filtered output after going through all the filters in the cascade.
	 */
	// Complexity = 1
	@Override
	public Signal<Number> filter(Signal<Number> input) {
		Signal<Number> temp = input;
		
		// Feed the input into the next Filter and set the output of that Filter as the input for the next.
		for (Filter f : filters) {
			temp = f.filter(temp);
		}
		
		output = temp;
		return output;
	}
}
