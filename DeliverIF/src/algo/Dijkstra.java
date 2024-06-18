package algo;

import java.util.*;

/**
 * The `Dijkstra` class provides a static method for performing Dijkstra's algorithm
 * on a graph represented by adjacency lists to find the shortest paths from a given
 * source node to all other nodes.
 * @author H4114
 */
public class Dijkstra {
	
	/**
     * Applies Dijkstra's algorithm to find the shortest paths from the source node
     * to all other nodes in the graph.
     *
     * @param graph   The graph represented by adjacency lists.
     * @param source  The source node.
     * @return A pair containing the predecessor array and the distance array
     *         of the shortest paths from the source node
     */
	public static Pair<Integer[],Double[]> dijkstra(Vector<List<Edge>> graph, Integer source) {
    	
        Integer numNodes = graph.size();
        Integer[] predecessor = new Integer[numNodes];
        Arrays.fill(predecessor, -1);
        Double[] distance = new Double[numNodes];
        Arrays.fill(distance, Double.MAX_VALUE);
              
        distance[source] = 0.0;

        PriorityQueue<Integer> grey = new PriorityQueue<>(Comparator.comparingDouble(node -> distance[node]));
        grey.add(source);

        while (!grey.isEmpty()) {
        	
            Integer current = grey.poll();
            
            for (Edge edge : graph.get(current)) {
            	
                Integer neighbor = edge.target;
                Double newDist = distance[current] + edge.distance;

                if (newDist < distance[neighbor]) {
                	
                    grey.remove(neighbor);  
                    distance[neighbor] = newDist;
                    predecessor[neighbor] = current;
                    grey.add(neighbor);
                    
                }
                
            }
            
        }
        
        return new Pair<Integer[],Double[]>(predecessor, distance);
        
    }

}


