import java.util.ArrayList;
import java.util.List;

/**
 * This {@link #Filter} subclass will filter by taking the inputs and calculating 
 * the average of previous inputs and that is the output.
 * 
 * @author StevePaley
 */
public class AverageFilter extends ScalarFilter {
	/** 
	 * The range of the input look-behind that this filter will take the average of; 
	 * it is defaulted to {@link #INFINITE_WINDOW}, 0, which will look form the beginning or since the last reset.
	 */
	private Integer windowSize = INFINITE_WINDOW;
	private List<Signal<Number>> prevInputs = new ArrayList<Signal<Number>>();
	
	/**
	 * Creates a new instance of an AverageFilter that will take the average of the inputs 
	 * since the beginning or last reset.
	 */
	// Complexity = 0
	public AverageFilter() {
		// Empty Constructor
	}
	
	/**
	 * Creates a new instance of an AverageFilter that will take the average of the 
	 * last n inputs, where n is determined by the parameter windowSize.
	 * 
	 * @param windowSize The number of previous inputs to take the average of.
	 */
	// Complexity = 0
	public AverageFilter(Integer windowSize) {
		this.windowSize = Math.abs(windowSize);
	}
	
	/**
	 * Take the input {@link #Signal} and add it to the list of inputs and take the average of the inputs
	 * based upon the window size.
	 * 
	 * @param input The input {@link #Signal} to filter.
	 * 
	 * @return A {@link #Signal} that is the average of the input Signals.
	 */
	// Complexity = 1
	@Override
	public Signal<Number> filter(Signal<Number> input) {
		super.filter(input);
		
		prevInputs.add(0, input);
		
		// Determine if the average is to be restricted to the last n inputs or "infinite"
		if (INFINITE_WINDOW == windowSize) {
			output = average();
		}
		else {
			output = windowAverage();
		}
		
		return output;
	}
	
	/**
	 * Resets the filter by clearing all previous inputs from the list and filtering the reset 
	 * {@link #Signal} through this classes filter().
	 */
	// Complexity = 0
	@Override
	public void reset(Signal<Number> resetValue) {
		super.reset(resetValue);
		prevInputs.clear();
		filter(resetValue);
	}
	
	/**
	 * This method will calculate the average magnitudes of the input Signals within 
	 * a window range of previous inputs.
	 * 
	 * @return A {@link #Signal} that is the average of the input Signals.
	 */
	// Complexity = 2
	private Signal<Number> windowAverage() {
		Double totalMagnitudes = 0.0;
		Double averageSignal;
		
		int nInputs = 0;

		// Adjust the window size if the list is smaller than the parameter windowSize
		Integer prevInputSize = prevInputs.size();
		if (prevInputSize < windowSize) {
			nInputs = prevInputSize;
		}
		else {
			nInputs = windowSize;
		}
		
		// Calculate the average of the last n inputs.
		for (int inputIndex = 0; inputIndex < nInputs; inputIndex++) {
			Signal<Number> signal = prevInputs.get(inputIndex);
			Number signalMagnitude = signal.getMagnitude();
			totalMagnitudes += signalMagnitude.doubleValue();
		}
		averageSignal = totalMagnitudes / nInputs;
		
		Signal<Number> returnSignal = new Signal<Number>(averageSignal);
		return returnSignal;
	}
	
	/**
	 * This method will calculate the average magnitudes of the input Signals 
	 * since the beginning or the last reset.
	 * 
	 * @return A {@link #Signal} that is the average of the input Signals.
	 */
	// Complexity = 1
	private Signal<Number> average() {
		Double totalMagnitudes = 0.0;
		int inputSize = prevInputs.size();
		Double averageSignal;
		
		// Calculate the average of all inputs in the list of previous inputs.
		for (Signal<Number> signal : prevInputs) {
			Number signalMagnitude = signal.getMagnitude();
			totalMagnitudes += signalMagnitude.doubleValue();
		}
		averageSignal = totalMagnitudes / inputSize;
		
		Signal<Number> returnSignal = new Signal<Number>(averageSignal);
		return returnSignal;
	}
}
