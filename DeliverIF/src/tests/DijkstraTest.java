package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import algo.Dijkstra;
import algo.Graph;
import algo.Pair;

import model.Intersection;
import model.RoadSegment;

/***
 * @author H4114
 * @see algo.Dijkstra
 */
public class DijkstraTest {

	//Fields
    Graph graph;
	
	//Initialize the graph
	@Before
	public void setUp() {
		graph = new Graph();
		
		Intersection first=new Intersection(1L, 10.,10.);
		Intersection second =new Intersection(2L,20.,20.);
		Intersection third = new Intersection(3L,30.,30.);
		Intersection fourth = new Intersection(4L,40.,40.);
		Intersection fifth = new Intersection(5L,50.,50.);
		
		RoadSegment a = new RoadSegment("A",first ,second , 1);
		RoadSegment b = new RoadSegment("B", first,fifth , 4);
		
		RoadSegment c = new RoadSegment("C", second,first, 2);
		RoadSegment d = new RoadSegment("D", second, third, 3);
		RoadSegment e = new RoadSegment("E", second, fifth, 5);
		
		RoadSegment f = new RoadSegment("F",third , second, 1);
		RoadSegment g = new RoadSegment("G", third, fourth, 3);
		RoadSegment h = new RoadSegment("H", third, fifth, 6);
		
		RoadSegment i = new RoadSegment("I", fourth, third, 2);
		RoadSegment j = new RoadSegment("J", fourth, fifth, 3);
		
		RoadSegment k = new RoadSegment("K", fifth, first, 7);
		RoadSegment l = new RoadSegment("L", fifth, second, 4);
		RoadSegment m = new RoadSegment("M", fifth, third, 1);
		RoadSegment n = new RoadSegment("M", fifth, fourth, 2);
		
		graph.addEdge(a);
		graph.addEdge(b);
		graph.addEdge(c);
		graph.addEdge(d);
		graph.addEdge(e);
		graph.addEdge(f);
		graph.addEdge(g);
		graph.addEdge(h);
		graph.addEdge(i);
		graph.addEdge(j);
		graph.addEdge(k);
		graph.addEdge(l);
		graph.addEdge(m);
		graph.addEdge(n);
		        
	}

	/***
	 * Test if the Dijkstra algoritm works 
	 */
	@Test
	public void dijkstraTest() {
		
		Integer[] pred= {-1,0,3,1,3};
		Double[] lengths= {0.,1.,3.,2.,4.};
		
		Pair<Integer[],Double[]> result= Dijkstra.dijkstra(graph.data, 0);

		assertArrayEquals(pred,result.getFirst());
		assertArrayEquals(lengths,result.getSecond());
	}

}





