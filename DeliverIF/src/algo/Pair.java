package algo;

/**
 * The `Pair` class represents a simple generic pair of two values, typically used
 * to store two related values together.
 * @author H4114
 */
public class Pair<F, S> {

    /**
     * The first element in the pair.
     */
    public F first;

    /**
     * The second element in the pair.
     */
    public S second;

    /**
     * Creates a new `Pair` instance with the specified values.
     * 
     * @param first the first element
     * @param second the second element
     */
    public Pair(F first, S second) {
    	
        this.first = first;
        this.second = second;
        
    }

    /**
     * Gets the first element in the pair.
     * 
     * @return the first element
     */
    public F getFirst() {
    	
        return first;
        
    }

    /**
     * Gets the second element in the pair.
     * 
     * @return the second element
     */
    public S getSecond() {
    	
        return second;
        
    }

    /**
     * Returns a string representation of the `Pair` object.
     * 
     * @return a string representation of the object
     */
    @Override
    public String toString() {
    	
        return "Pair [" + first + " | " + second + "]";
        
    }
    
}



