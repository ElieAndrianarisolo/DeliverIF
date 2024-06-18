package algo;

import java.util.Collection;
import java.util.Iterator;

public class TSP3 extends TemplateTSP {
	
	@Override
	protected Double bound(Integer currentVertex, Collection<Integer> unvisited) {
		
		Double minCostFollowingVertex = Double.MAX_VALUE;
		Double minCostFinalStep = Double.MAX_VALUE;
		
		Iterator<Integer> it = new SeqIter(unvisited, currentVertex, g);
		
		while (it.hasNext()) {
			
			Integer nextVertex = it.next();
			
			if (g.getCost(currentVertex, nextVertex) < minCostFollowingVertex) {
				
				minCostFollowingVertex = g.getCost(currentVertex, nextVertex);
				
			}
			
			if (g.getCost(nextVertex, 0) < minCostFinalStep) {
				
				minCostFinalStep = g.getCost(nextVertex, 0);
				
			}
			
		}
		
		return minCostFollowingVertex + minCostFinalStep + 1*(unvisited.size()-1);
		
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, GeneratedGraph g) {
		
		return new SortIter(unvisited, currentVertex, g);
		
	}

}



