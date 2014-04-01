import java.util.ArrayList;
import java.util.List;

/**
 * This class is the ScalarLinearFilter and is a subclass of the {@link #ScalarFilter}. 
 * This filter follows filtering and resetting formulas that can be found in the Hw11(2).pdf file. 
 * 
 * @author StevePaley
 */
public class ScalarLinearFilter extends ScalarFilter {
	private List<Signal<Number>> prevInputs = new ArrayList<Signal<Number>>();
	private List<Signal<Number>> prevOutputs = new ArrayList<Signal<Number>>();
	
	/** The coefficients that the inputs will be multiplied by. */
	private List<Number> inCoefficients = new ArrayList<Number>();
	/** The coefficients that the outputs will be multiplied by. */
	private List<Number> outCoefficients = new ArrayList<Number>();
	
	/** The size of the input coefficients list. */
	private int N;
	/** The size of the output coefficients list. */
	private int M;
	
	/**
	 * Create a new instance of the ScalarLinearFilter with the coefficient parameters that 
	 * will be used in the calculations.
	 * 
	 * @param inCoefficients The coefficients used in the input dot product calculation
	 * @param outCoefficients The coefficients used in the output dot product calculation
	 */
	// Complexity = 0
	public ScalarLinearFilter(List<Number> inCoefficients, List<Number> outCoefficients) {
		this.inCoefficients = inCoefficients;
		this.outCoefficients = outCoefficients;

		N = this.inCoefficients.size();
		M = this.outCoefficients.size();
		
		/*
		 *  Initialize the previous input/output lists to the same size as the coefficient list
		 *  and fill each item with zero.
		 */
		fillSignalList(prevInputs, N, 0);
		fillSignalList(prevOutputs, M, 0);
	}
	
	/**
	 * Filter the input {@link #Signal} and generate the output is generated via the equation 
	 * found in Hw11(2).pdf. This equation takes the dot product of the coefficients and the 
	 * list of previous inputs/outputs and then subtracts the output dot product from the input 
	 * dot product.
	 * 
	 * @return The {@link #Signal} that is the result of the filter calculations.
	 */
	// Complexity = 0
	@Override
	public Signal<Number> filter(Signal<Number> input) {
		super.filter(input);
		
		// Shift the input Signal into the previous inputs list and shift the oldest previous input out.
		prevInputs.add(0, input);
		prevInputs.remove(N);
		
		Double inputDotProduct = dotProduct(prevInputs, inCoefficients);
		Double outputDotProduct = dotProduct(prevOutputs, outCoefficients);
		
		output = new Signal<Number>(inputDotProduct - outputDotProduct);
		
		// Shift the output Signal into the previous outputs list and shift the oldest previous input out.
		prevOutputs.add(0, output);
		prevOutputs.remove(M);
		
		return output;
	}
	
	/**
	 * Reset the filter using the equation found in Hw11(2).pdf. The equation is to sum the coefficient 
	 * lists and the input coefficient sum are multiplied by the reset value and divided by one plus the 
	 * output coefficient sum. The previous input list is set to the reset value and the previous 
	 * output list is set to the calculated reset value.
	 * 
	 * @param resetValue The number that will be used in the reset calculation.
	 */
	// Complexity = 0
	@Override
	public void reset(Signal<Number> resetValue) {
		super.reset(resetValue);
		Number resetValueNumber = resetValue.getMagnitude();
		Double inCoefficientSum = sumList(inCoefficients);
		Double outCoefficientSum = sumList(outCoefficients);
		
		Double outputResetValue = resetValueNumber.doubleValue() * inCoefficientSum;
		resetValueNumber = outputResetValue / (1 + outCoefficientSum);

		fillSignalList(prevInputs, N, resetValueNumber);
		fillSignalList(prevOutputs, M, outputResetValue);
	}
	
	/**
	 * This method will fill the given list to the size of the parameter listSize where each list item is 
	 * the numberToFill.
	 * 
	 * @param listToFill The list that will be filled with numberToFill
	 * @param listSize The number of inputs to put in the list
	 * @param numberToFill The input to the list
	 */
	// Complexity = 1
	private void fillSignalList(List<Signal<Number>> listToFill, Integer listSize, Number numberToFill) {
		listToFill.clear();
		
		for (int i = 0; i < Math.abs(listSize); i++) {
			listToFill.add(new Signal<Number>(numberToFill));
		}
	}
	
	/**
	 * This method will calculate the dot product between a list of numbers and a list of signals.
	 * 
	 * @param signals The list of signals used
	 * @param coefficients The list of numbers used
	 * @return The dot product of signals and coefficients
	 */
	// Complexity = 1
	private Double dotProduct(List<Signal<Number>> signals, List<Number> coefficients) {
		Double dotProduct = 0.0;
		Integer coefficientsSize = coefficients.size();
		
		for (int i = 0; i < coefficientsSize; i++) {
			Signal<Number> signal = signals.get(i);
			Number signalMagnitude = signal.getMagnitude();
			Number coefficientNumber = coefficients.get(i);
			
			dotProduct += coefficientNumber.doubleValue() * signalMagnitude.doubleValue();
		}
		
		return dotProduct;
	}
	
	/**
	 * This method will sum a list of numbers and return the value as a Double.
	 * 
	 * @param list The list of numbers to sum
	 * @return The sum of the list
	 */
	// Complexity = 1
	private Double sumList(List<Number> list) {
		Double sum = 0.0;
		
		for (Number number : list) {
			sum += number.doubleValue();
		}
		
		return sum;
	}
}
