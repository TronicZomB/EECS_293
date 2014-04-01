import java.util.ArrayList;
import java.util.List;


/**
 * This {@link #Filter} subclass will determine the minimum input {@link #Signal} from the set of previous 
 * inputs and that {@link #Signal} will be the output of this filter.
 * 
 * @author StevePaley
 */
public class MinFilter extends Filter {
	/** 
	 * The range of the input look-behind that this filter will take the minimum from; 
	 * it is defaulted to {@link #INFINITE_WINDOW}, 0, which will look form the beginning or since the last reset.
	 */
	private Integer windowSize = INFINITE_WINDOW;
	private List<Signal<Number>> prevInputs = new ArrayList<Signal<Number>>();
	
	/**
	 * Creates a new instance of a MinFilter that will return the minimum input since 
	 * the beginning of the inputs or the last reset.
	 */
	// Complexity = 0
	public MinFilter() {
		// Empty Constructor
	}
	
	/**
	 * Creates a new instance of a MinFilter that will return the minimum input from 
	 * within the given window size (the n previous inputs). 
	 * 
	 * @param windowSize The number of previous inputs to check in for the minimum from. 
	 * 					  If the input is negative, take the absolute value. Passing 
	 * 				      zero acts the same as the no parameter constructor.
	 */
	// Complexity = 0
	public MinFilter(Integer windowSize) {
		this.windowSize = Math.abs(windowSize);
	}
	
	/**
	 * Filters the input based upon the predetermined window size in order to 
	 * output the minimum signal from the inputs.
	 * 
	 * @param input The input {@link #Signal} to filter.
	 * 
	 * @return The minimum {@link #Signal} from the set of inputs.
	 */
	// Complexity = 1
	@Override
	public Signal<Number> filter(Signal<Number> input) {
		prevInputs.add(0, input);

		// Determine if the min signal is to be found in the last n inputs, or over all inputs
		if (INFINITE_WINDOW == windowSize) {
			output = min();
		}
		else {
			output = windowMin();
		}
		
		return output;
	}
	
	/**
	 * Resets the filter by clearing all previous inputs from the list and filtering in the resetValue {@link #Signal}. 
	 */
	// Complexity = 0
	@Override
	public void reset(Signal<Number> resetValue) {
		prevInputs.clear();
		filter(resetValue);
	}
	
	/**
	 * This will return the minimum input from the previous n inputs, where n is determined by the 
	 * window size parameter that was passed to the constructor.
	 * 
	 * @return The minimum signal from the subset of inputs.
	 */
	// Complexity = 3
	private Signal<Number> windowMin() {
		Signal<Number> returnSignal = null;
		// The magnitude of the current minimum Signal
		Double minMagnitude = Double.POSITIVE_INFINITY;
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

		// Find the minimum Signal from within the window size
		for (int inputIndex = 0; inputIndex < nInputs; inputIndex++) {
			Signal<Number> signal = prevInputs.get(inputIndex);
			Number signalMagnitude = signal.getMagnitude();
			Double magnitudeAsDouble = signalMagnitude.doubleValue();
			if (magnitudeAsDouble < minMagnitude) {
				returnSignal = signal;
				minMagnitude = magnitudeAsDouble;
			}
		}
		
		return returnSignal;
	}
	
	/**
	 * This method will return the minimum signal input since the beginning of this filter or 
	 * since the last reset.
	 * 
	 * @return The minimum signal since the beginning or last reset.
	 */
	// Complexity = 2
	private Signal<Number> min() {
		Signal<Number> returnSignal = null;
		// The magnitude of the current minimum Signal
		Double minMagnitude = Double.POSITIVE_INFINITY;

		// Find the minimum signal from the entire list of inputs.
		for (Signal<Number> signal : prevInputs) {
			Number signalMagnitude = signal.getMagnitude();
			Double magnitudeAsDouble = signalMagnitude.doubleValue();
			if (magnitudeAsDouble < minMagnitude) {
				returnSignal = signal;
				minMagnitude = magnitudeAsDouble;
			}
		}
		
		return returnSignal;
	}
}
