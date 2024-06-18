package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import model.Intersection;
import model.RoadSegment;

/**
 * This class represents a graph data structure used for modeling road networks.
 * @author H4114
 */
public class Graph {
	
	/**
     * Vector of adjacency lists representing the graph.
     */
    public Vector<List<Edge>> data;

    /**
     * Mapping from intersections to their corresponding indices.
     */
    public Map<Intersection, Integer> R_2_i;

    /**
     * Mapping from indices to intersections.
     */
    public Map<Integer, Intersection> i_2_R;

    /**
     * Current index used for intersection mapping.
     */
    public int index;
	
    /**
     * Creates a new graph with empty adjacency lists and intersection mappings.
     */
	public Graph() {
		
		this.data = new Vector<List<Edge>>();
		this.R_2_i = new HashMap<Intersection, Integer>();
		this.i_2_R = new HashMap<Integer, Intersection>();
		index = 0;
		
	}
	
	/**
     * Adds a road segment to the graph, updating the adjacency lists and intersection mappings.
     *
     * @param rs The road segment to add.
     */
	public void addEdge(RoadSegment rs) {
		
		if (R_2_i.get(rs.getStartingIntersection()) == null) {
			
			R_2_i.put(rs.getStartingIntersection(),index);
			i_2_R.put(index,rs.getStartingIntersection());
			data.add(new ArrayList<Edge>());
			index++;
			
		}
		
		if (R_2_i.get(rs.getEndingIntersection()) == null) {
			
			R_2_i.put(rs.getEndingIntersection(),index);
			i_2_R.put(index,rs.getEndingIntersection());
			data.add(new ArrayList<Edge>());
			index++;
			
		}
		
		int ii = R_2_i.get(rs.getStartingIntersection()); 	// Input
		int io = R_2_i.get(rs.getEndingIntersection()); 	// Output
		Edge e1 = new Edge(io,rs.getLength());
		Edge e2 = new Edge(ii,rs.getLength());
		
		List<Edge> lmodified1 = data.get(ii);
		lmodified1.add(e1);
		data.set(ii, lmodified1);
		
		List<Edge> lmodified2 = data.get(io);
		lmodified2.add(e2);
		data.set(io, lmodified2);
		
	}
	
	/**
     * Gets the number of intersections in the graph.
     *
     * @return the size of the graph
     */
	public Integer getSize() {
		
		return data.size();
		
	}
	
}
