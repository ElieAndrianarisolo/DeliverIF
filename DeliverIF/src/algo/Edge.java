package algo;

/**
 * Represents an edge in a graph with a target node and a distance.
 * @author H4114
 */
public class Edge {
	
    Integer target;
    Double distance;
    
    /**
     * Constructs an Edge object with a target node and distance.
     *
     * @param target   The target node connected by this edge.
     * @param distance The distance or weight of this edge.
     */
    public Edge(Integer target, Double distance) {
    	
        this.target = target;
        this.distance = distance;
        
    }
    
    /**
     * Provides a string representation of the Edge object.
     *
     * @return A string representation of the Edge.
     */
    public String toString() {
    	
    	return "[target=" + target + ", distance=" + distance + "]";
    	
    }
    
}


