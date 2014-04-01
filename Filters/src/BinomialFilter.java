import java.util.ArrayList;
import java.util.List;

/**
 * This class is a special class of the {@link #FIRFilter} where the input coefficients are the binomial 
 * distribution of from zero to a number N.
 * 
 * @author StevePaley
 */
public class BinomialFilter extends FIRFilter {
	static List<Number> inCoefficients = new ArrayList<Number>();
	
	/**
	 * Create a new instance of BinomialFilter that has a binomial distribution from zero to N.
	 * 
	 * @param N The number to be used in the binomial distribution calculation.
	 */
	// Complexity = 0
	public BinomialFilter(Integer N) {
		super(getBinomialCoefficients(N));
	}
	
	/**
	 * This method generates the list of coefficients that are binomially distributed from zero to N.
	 * 
	 * @param N The parameter for the binomial calculations.
	 * 
	 * @return The list of binomially distributed numbers.
	 */
	// Complexity = 1
	private static List<Number> getBinomialCoefficients(Integer N) {
		for (int index = 0; index <= N; index++) {
			inCoefficients.add(binomialDistribution(N, index));
		}
		
		return inCoefficients;
	}
	
	/**
	 * This method will calculate the binomial number for the parameters N and index.
	 * 
	 * @param N 
	 * @param index
	 * @return The binomial distribution number for parameters N and index
	 */
	// Complexity = 0
	private static Double binomialDistribution(Integer N, Integer index) {
		Double numerator = factorial(N);
		Double denominator = factorial(index) * factorial(N - index);
		Double toReturn = numerator / denominator;
		
		return toReturn;
	}
	
	/**
	 * Calculates the factorial of a number.
	 * 
	 * @param index The number to calculate the factorial of.
	 * @return The factorial of index.
	 */
	// Complexity = 1
	private static Double factorial(Integer index) {
		Double toReturn = 1.;
		
		for (int j = 1; j <= index; j++) {
			toReturn *= j;
		}
		
		return toReturn;
	}
}
