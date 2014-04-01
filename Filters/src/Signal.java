import java.util.Vector;

/**
 * The Signal class is used as an intermediary to the Filter classes so that the Filter 
 * classes will be able to handle scalars and vectors similarly by handling Signals instead.
 * A scalar Signal is simply a vector that is one dimensional. Signals are of generic type Number.
 * 
 * @author StevePaley
 * 
 * @param <T> The type of Number that this Signal will hold.
 */
public class Signal<T extends Number> {
	/**
	 * This vector will hold the Number values that make up this Signal.
	 */
	private Vector<Number> vector = new Vector<Number>();
	
	/**
	 * This constructor is used to create a scalar Signal.
	 * 
	 * @param t1 The one dimensional number that will make up this scalar Signal.
	 */
	// Complexity = 0
	public Signal(T t1) {
		vector.add(t1);
	}
	
	/**
	 * This constructor takes in one or more numbers and creates a vector Signal out of the numbers. 
	 * 
	 * @param t1 The first number in the vector
	 * @param tn The second through n-th number in the vector
	 */
	// Complexity = 1
	public Signal(T t1, T... tn) {
		vector.add(t1);
		
		for (Number number : tn) {
			vector.add(number);
		}
	}
	
	/**
	 * This constructor takes in a vector of type Number as a parameter and sets it as this Signal's vector
	 * 
	 * @param vector The vector to be set as this Signal's vector
	 */
	// Complexity = 0
	public Signal(Vector<Number> vector) {
		this.vector = vector;
	}
	
	/**
	 * This method will return the scalar number that is this Signal if the Signal is scalar. 
	 * If this Signal is a vector this method will return the magnitude of the vector.
	 * 
	 * @return The scalar value or magnitude of this Signal.
	 */
	// Complexity = 2
	public Number getMagnitude() {
		if (isScalar()) {
			return vector.get(0);
		}
		
		Double toReturn = 0.0;
		
		// Calculate the vector's magnitude
		for (Number n : vector) {
			Double temp = n.doubleValue();
			toReturn += (temp * temp);
		}
		toReturn = Math.sqrt(toReturn);

		return toReturn;
	}
	
	/**
	 * Gets this Signal's vector.
	 * 
	 * @return This Signal's vector.
	 */
	// Complexity = 0
	public Vector<Number> getVector() {
		return this.vector;
	}
	
	/**
	 * Returns if this Signal is scalar or not.
	 * 
	 * @return True if this Signal is scalar, false otherwise
	 */
	// Complexity = 0
	public boolean isScalar() {
		return (vector.size() == 1);
	}
	
	@Override
	public String toString() {
		return getMagnitude().toString();
	}
}
