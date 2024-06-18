package algo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SortIter implements Iterator<Integer> {
	private HashMap<Integer, Double> candidates;
	private Integer[] sortedCandidates;
	private int nbCandidates;
	private int index = 0;

	/**
	 * Create an iterator to traverse the set of vertices in unvisited
	 * which are successors of currentVertex in g
	 * Vertices are traversed in the same order as in unvisited
	 * @param unvisited
	 * @param currentVertex
	 * @param g
	 */
	public SortIter(Collection<Integer> unvisited, int currentVertex, GeneratedGraph g){
		
		this.sortedCandidates = new Integer[unvisited.size()];
		this.candidates = new HashMap<>();
		
		for (Integer s : unvisited){
			
			if (g.isArc(currentVertex, s)){
				
				candidates.put(s, g.getCost(currentVertex, s));
				
			}
			
		}

        ArrayList<Double> list = new ArrayList<>();

        for (Map.Entry<Integer, Double> entry : candidates.entrySet()) {
        	
            list.add(entry.getValue());
            
        }
        
        Collections.sort(list);
        for (Double i : list) {
        	
            for (java.util.Map.Entry<Integer, Double> entry : candidates.entrySet()) {
            	
                if (entry.getValue().equals(i)) {
                	
                    sortedCandidates[nbCandidates++ ] = entry.getKey();
					candidates.remove(entry.getKey());
					break ;
					
                }
                
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
		return sortedCandidates[index++];
		
	}

	@Override
	public void remove() {}

}
