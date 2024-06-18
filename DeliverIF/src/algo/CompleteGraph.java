package algo;

/**
 * This class represents a complete directed graph with a cost matrix.
 * @author H4114
 */
public class CompleteGraph implements GeneratedGraph {

    /**
     * The number of vertices in the graph.
     */
    int nbVertices;

    /**
     * The cost matrix representing the weights of arcs between vertices.
     */
    Double[][] cost;

    /**
     * Creates a complete directed graph with the specified cost matrix.
     *
     * @param cost The cost matrix representing the weights of arcs between vertices
     */
    public CompleteGraph(Double[][] cost) {
    	
        this.cost = cost;
        this.nbVertices = cost.length;
        
    }

    /**
     * Gets the number of vertices in the graph.
     *
     * @return The number of vertices
     */
    @Override
    public int getNbVertices() {
    	
        return nbVertices;
        
    }

    /**
     * Gets the cost of the arc between vertices i and j.
     *
     * @param i The starting vertex.
     * @param j The ending vertex.
     * @return The cost of the arc between vertices i and j, or -1.0 if indices are out of bounds.
     */
    @Override
    public Double getCost(int i, int j) {
       
    	if (i < 0 || i >= nbVertices || j < 0 || j >= nbVertices) {
        	
            return -1.0;
            
        }
        return cost[i][j];
    }

    /**
     * Checks if there is an arc between vertices i and j.
     *
     * @param i The starting vertex
     * @param j The ending vertex
     * @return true if there is an arc between vertices i and j, false otherwise
     */
    @Override
    public boolean isArc(int i, int j) {
    	
        if (i < 0 || i >= nbVertices || j < 0 || j >= nbVertices) {
        	
            return false;
            
        }
        
        return i != j;
    }
    
}