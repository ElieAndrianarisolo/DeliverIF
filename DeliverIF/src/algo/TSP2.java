package algo;

import java.util.Collection;
import java.util.Iterator;

public class TSP2 extends TemplateTSP {
	
	@Override
	protected Double bound(Integer currentVertex, Collection<Integer> unvisited) {
		
		Double minCostFollowingVertex = Double.MAX_VALUE;
		Double minCostFinalStep = Double.MAX_VALUE;
		Iterator<Integer> it = iterator(currentVertex, unvisited, g);
		
		while (it.hasNext()) {
			
			Integer nextVertex = it.next();
			
			if (g.getCost(currentVertex, nextVertex) < minCostFollowingVertex) {
				minCostFollowingVertex = g.getCost(currentVertex, nextVertex);
			}
			
			if (g.getCost(nextVertex, 0) < minCostFinalStep) {
				minCostFinalStep = g.getCost(nextVertex, 0);
			}
			
		}
		
		return minCostFollowingVertex + minCostFinalStep;
		
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, GeneratedGraph g) {
		
		return new SeqIter(unvisited, currentVertex, g);
		
	}

}



