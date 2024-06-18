package algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class TemplateTSP implements TSP {
	
	private Integer[] bestSol;
	protected GeneratedGraph g;
	private double bestSolCost;
	private int timeLimit;
	private long startTime;

	public void searchSolution(int timeLimit, GeneratedGraph g) {
		
		if (timeLimit <= 0) {
			
			return;
			
		}
		
		startTime = System.currentTimeMillis();
		
		this.timeLimit = timeLimit;
		this.g = g;
		bestSol = new Integer[g.getNbVertices()];
		
		Collection<Integer> unvisited = new ArrayList<Integer>(g.getNbVertices() - 1);
		for (int i = 1; i < g.getNbVertices(); i++) {
			
			unvisited.add(i);
			
		}
		
		Collection<Integer> visited = new ArrayList<Integer>(g.getNbVertices());
		visited.add(0); // The first visited vertex is 0
		
		bestSolCost = Double.MAX_VALUE;
		branchAndBound(0, unvisited, visited, 0);
		
	}

	public Integer getSolution(int i) {
		
		if (g != null && i >= 0 && i < g.getNbVertices()) {
			
			return bestSol[i];
			
		}
			
		return -1;
		
	}

	public Double getSolutionCost() {
		
		if (g != null) {
			
			return bestSolCost;
			
		}
		
		return -1.0;
		
	}

	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * 
	 * @param currentVertex
	 * @param unvisited
	 * @return a lower bound of the cost of paths in g starting from
	 *         currentVertex, visiting every vertex in unvisited exactly 
	 *         once, and returning back to vertex >0.
	 */
	protected abstract Double bound(Integer currentVertex, Collection<Integer> unvisited);

	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * 
	 * @param currentVertex
	 * @param unvisited
	 * @param g
	 * @return an iterator for visiting all vertices in unvisited which
	 *         are successors of currentVertex
	 */
	protected abstract Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited,
			GeneratedGraph g);

	/**
	 * Template method of a branch and bound algorithm for solving the TSP in g.
	 * 
	 * @param currentVertex the last visited vertex
	 * @param unvisited     the set of vertex that have not yet been visited
	 * @param visited       the sequence of vertices that have been already visited
	 *                      (including currentVertex)
	 * @param currentCost   the cost of the path corresponding to visited
	 */
	private void branchAndBound(int currentVertex, Collection<Integer> unvisited, Collection<Integer> visited, double currentCost) {
		
		if (System.currentTimeMillis() - startTime > timeLimit) {
			
			return;
			
		}
		
		if (unvisited.size() == 0) {
			
			if (g.isArc(currentVertex, 0)) {
				
				if (currentCost + g.getCost(currentVertex, 0) < bestSolCost) {
					
					visited.toArray(bestSol);
					bestSolCost = currentCost + g.getCost(currentVertex, 0);
					
				}
				
			}
			
		} else if (currentCost + bound(currentVertex, unvisited) < bestSolCost) {
			
			Iterator<Integer> it = iterator(currentVertex, unvisited, g);
			
			while (it.hasNext()) {
				
				Integer nextVertex = it.next();
				
				visited.add(nextVertex);
				unvisited.remove(nextVertex);
				
				branchAndBound(nextVertex, unvisited, visited, currentCost + g.getCost(currentVertex, nextVertex));
				
				visited.remove(nextVertex);
				unvisited.add(nextVertex);
				
			}
		}
	}

}
