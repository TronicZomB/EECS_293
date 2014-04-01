import java.security.InvalidParameterException;

/**
 * This abstract subclass of {@link #Filter} is used to create new variants of filters that are to 
 * only take scalar numbers as their input Signal.
 * 
 * @author StevePaley
 */
public abstract class ScalarFilter extends Filter {
	
	/**
	 * This method should always be overridden in scalar filter subclasses. 
	 * The subclasses must call super.filter() before filtering the input {@link #Signal}. 
	 * This method will perform the check that insures all input Signals are of a scalar nature.
	 * 
	 * @param input The {@link #Signal} to be filtered.
	 * 
	 * @return The input {@link #Signal} so that it may be handled within the subclass appropriately.
	 */
	@Override
	public Signal<Number> filter(Signal<Number> input) {
		if (!input.isScalar()) {
			throw new InvalidParameterException("Expected Scalar Input");
		}
		
		return input;
	}
	
	/**
	 * This reset method overrides the {@link #Filter} reset() in order to perform a check that the Signal 
	 * is a scalar signal and not a vector. It will thrown an InvalidParameterException if the 
	 * Signal is not scalar. 
	 */
	@Override
	public void reset(Signal<Number> resetValue) {
		if (!resetValue.isScalar()) {
			throw new InvalidParameterException("Expected Scalar Input");
		}
	}
}
