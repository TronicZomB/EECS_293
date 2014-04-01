/**
 * This abstract class is a basic outline of what makes up all other filters. Extend this class 
 * in order to create new types of filters.
 * 
 * @author StevePaley
 */
public abstract class Filter {
	/**
	 * This constant value of zero is used in certain filters where there can be either an infinite 
	 * look-behind for calculating the output or there can be a particular range for 
	 * the look-behind; in those filters, this constant is the default value that will 
	 * cause the filter to look-behind infinitely. 
	 */
	final static protected int INFINITE_WINDOW = 0;
	
	protected Signal<Number> output;

	/**
	 * This abstract method will be overridden in each filter subclass. It will be 
	 * the method that will take the input and generate the output for each type of filter.
	 * 
	 * @param input The input Signal to the filter.
	 * 
	 * @return The output Signal from the filter.
	 */
	public abstract Signal<Number> filter(Signal<Number> input);
	
	/**
	 * This method can be overridden to be used as a reset for particular types of filters. Not all 
	 * filters support reset and in that case the default action is to do nothing when this method is 
	 * called. It takes a parameter that is of the same type as the input to filter and will filter 
	 * that value to be used to reinitialize the filter.
	 * 
	 * @param reset The Signal to reset the filter with.
	 */
	public void reset(Signal<Number> reset) {
		/* Default Action */
		/* Do nothing */
	}
}
