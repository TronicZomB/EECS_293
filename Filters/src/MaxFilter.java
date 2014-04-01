import java.util.ArrayList;
import java.util.List;

/**
 * This {@link #Filter} subclass will determine the maximum input Signal from the set of previous 
 * inputs and that Signal will be the output of this filter.
 * 
 * @author StevePaley
 */
public class MaxFilter extends Filter {
	/** 
	 * The range of the input look-behind that this filter will take the maximum from; 
	 * it is defaulted to {@link #INFINITE_WINDOW}, 0, which will look form the beginning or since the last reset.
	 */
	private Integer windowSize = INFINITE_WINDOW;
	private List<Signal<Number>> prevInputs = new ArrayList<Signal<Number>>();
	
	/**
	 * Creates a new instance of a MaxFilter that will return the max input since 
	 * the beginning of the inputs or the last reset.
	 */
	// Complexity = 0
	public MaxFilter() {
		// Empty Constructor
	}
	
	/**
	 * Creates a new instance of a MaxFilter that will return the max input from 
	 * within the given window size (the n previous inputs). 
	 * 
	 * @param windowSize The number of previous inputs to check in for the max from. 
	 * 					  If the input is negative, take the absolute value. Passing 
	 * 				      zero acts the same as the no parameter constructor.
	 */
	// Complexity = 0
	public MaxFilter(Integer windowSize) {
		this.windowSize = Math.abs(windowSize);
	}
	
	/**
	 * Filters the input based upon the predetermined window size in order to 
	 * output the max signal from the inputs.
	 * 
	 * @param input The input {@link #Signal} to filter.
	 * 
	 * @return The max {@link #Signal} from the set of inputs.
	 */
	// Complexity = 1
	@Override
	public Signal<Number> filter(Signal<Number> input) {
		prevInputs.add(0, input);

		// Determine if the max value is to be found in the last n inputs, or over all inputs
		if (INFINITE_WINDOW == windowSize) {
			output = max();
		}
		else {
			output = windowMax();
		}

		return output;
	}
	
	/**
	 * Resets the filter by clearing all previous inputs from the list and filtering the resetValue 
	 * through the filter() method.
	 */
	// Complexity = 0
	@Override
	public void reset(Signal<Number> resetValue) {
		prevInputs.clear();
		filter(resetValue);
	}
	
	/**
	 * This will return the max input from the previous n inputs, where n is determined by the 
	 * window size parameter that was passed to the constructor.
	 * 
	 * @return The max signal from the subset of inputs.
	 */
	// Complexity = 3
	private Signal<Number> windowMax() {
		Signal<Number> returnSignal = null;
		// The magnitude of the current max Signal
		Double maxMagnitude = Double.NEGATIVE_INFINITY;
		// The size of the window to look back on
		int nInputs = 0;
		
		// Adjust the window size if the list is smaller than the parameter windowSize
		Integer prevInputsSize = prevInputs.size();
		if (prevInputsSize < windowSize) {
			nInputs = prevInputsSize;
		}
		else {
			nInputs = windowSize;
		}
		
		// Find the max Signal from within the window size
		for (int inputIndex = 0; inputIndex < nInputs; inputIndex++) {
			Signal<Number> signal = prevInputs.get(inputIndex);
			Number signalMagnitude = signal.getMagnitude();
			Double magnitudeAsDouble = signalMagnitude.doubleValue();
			if (magnitudeAsDouble > maxMagnitude) {
				returnSignal = signal;
				maxMagnitude = magnitudeAsDouble;
			}
		}
		
		return returnSignal;
	}
	
	/**
	 * This method will return the max {@link #Signal} input since the beginning of this filter or 
	 * since the last reset.
	 * 
	 * @return The max {@link #Signal} since the beginning or last reset.
	 */
	// Complexity = 2
	private Signal<Number> max() {
		Signal<Number> returnSignal = null;
		// The magnitude of the current max Signal
		Double maxMagnitude = Double.NEGATIVE_INFINITY;
		
		// Find the max signal from the entire list of inputs.
		for (Signal<Number> signal : prevInputs) {
			Number signalMagnitude = signal.getMagnitude();
			Double magnitudeAsDouble = signalMagnitude.doubleValue();
			if (magnitudeAsDouble > maxMagnitude) {
				returnSignal = signal;
				maxMagnitude = magnitudeAsDouble;
			}
		}
		
		return returnSignal;
	}
}
