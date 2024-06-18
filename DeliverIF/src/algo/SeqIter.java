package algo;

import java.util.Collection;
import java.util.Iterator;

public class SeqIter implements Iterator<Integer> {
	
	private Integer[] candidates;
	private int nbCandidates;

	/**
	 * Create an iterator to traverse the set of vertices in unvisited
	 * which are successors of currentVertex in g
	 * Vertices are traversed in the same order as in unvisited
	 * 
	 * @param unvisited
	 * @param currentVertex
	 * @param g
	 */
	public SeqIter(Collection<Integer> unvisited, int currentVertex, GeneratedGraph g) {
		
		this.candidates = new Integer[unvisited.size()];
		
		for (Integer s : unvisited) {
			
			if (g.isArc(currentVertex, s)) {
				
				candidates[nbCandidates++] = s;
				
			}
				
			
		}
	}

	@Override
	public boolean hasNext() {
		
		return nbCandidates > 0;
		
	}

	@Override
	public Integer next() {
		
		nbCandidates--;
		return candidates[nbCandidates];
		
	}

	@Override
	public void remove() {
		
	}
	
}
