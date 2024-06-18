package algo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a graph with a cost matrix and time constraints on vertices.
 * @author H4114
 */
public class TimeWindowGraph implements GeneratedGraph {
    
	private static final int START_TIME = 8;
    private static final int END_TIME = 12;
    int nbVertices;
    Double[][] cost;
    int[] timeWindow;
    
    /**
     * Creates a graph with a cost matrix and time constraints on vertices.
     * 
     * @param cost			Cost matrix.
     * @param timeWindow	Time constraints.
     */
    public TimeWindowGraph(Double[][] cost, int[] timeWindow) {
    	
        this.cost = cost;
        this.timeWindow = timeWindow;
        nbVertices = cost.length;
        
    }
    
    /**
     * Gets the number of vertices of the graph.
     * 
     * @return The number of vertices.
     */
    @Override
    public int getNbVertices() {
    	
        return nbVertices;
        
    }
    
    /**
     * Gets the cost of an arc.
     * 
     * @param i The starting vertex.
     * @param j The ending vertex.
     * @return The cost of an arc.
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
     * @param i The starting vertex.
     * @param j The ending vertex.
     * @return true if there is an arc, false otherwise.
     */
    @Override
    public boolean isArc(int i, int j) {
    	
        if (i < 0 || i >= nbVertices || j < 0 || j >= nbVertices || getCost(i, j) == -1) {
        	
            return false;
            
        }
        
        return i != j;
        
    }

    /**
     * Gets the time window at the position i.
     * 
     * @param i Position of the time window.
     * @return The time window constraint of the vertex.
     */
    public int getTimeWindow(int i) {
    	
        return timeWindow[i];
        
    }

    /**
     * Gets the starting time window.
     * 
     * @return The starting time window
     */
    public int getStartTime() {
    	
        return START_TIME;
        
    }

    /**
     * Gets the ending time window.
     * 
     * @return The ending time window.
     */
    public int getEndTime() {
    	
        return END_TIME;
        
    }

    /**
     * Removes the arc from i to j.
     * 
     * @param i Vertex i.
     * @param j Vertex j.
     */
    public void removeArc(int i, int j) {
    	
        cost[i][j] = -1.0;
        
    }

    /**
     * Cuts the arcs that are not respecting time window constraints.
     */
    public void removeArcsToSatisfyTimeConstraints() {
    	
        List<Integer> vertexVisited = new ArrayList<Integer>();
        List<Integer> vertexToVisit = new ArrayList<Integer>();
        
        for (int hour = START_TIME; hour < END_TIME; hour++) {
        	
            for (int i = 0; i < nbVertices; i++) {
            	
                if (timeWindow[i] == hour) {
                	
                    vertexToVisit.add(i);
                    
                }
                
            }
            
            for (Integer toVisit : vertexToVisit) {
            	
                for (Integer visited : vertexVisited) {
                	
                    this.removeArc(toVisit, visited);
                    
                }
                
            }
            
            while (!vertexToVisit.isEmpty()) {
            	
                vertexVisited.add(vertexToVisit.get(0));
                vertexToVisit.remove(0);
                
            }
            
        }
        
    }
    
    @Override
    public String toString() {
    	
    	String toPrint;
    	
    	toPrint = "nbVertices = " + nbVertices + " , cost [";
    	
    	
    	for(int i = 0; i<cost.length; i++) {
    		
    		for(int j = 0; j<cost[i].length; j++) {
    			
    			toPrint += cost[i][j] + ", ";
    			
    		}
    		
    	}
    	
    	toPrint += " ], timeWindows = [";
    	
    	for(int i = 0; i<timeWindow.length; i++) {
    		 			
			toPrint += timeWindow[i] + ", ";

    	}
    	
    	toPrint += " ]";
    	
    	return toPrint;
    	
    }

}
