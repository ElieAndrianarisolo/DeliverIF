package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import algo.CompleteGraph;
import algo.Graph;
import algo.TimeWindowGraph;

import model.Intersection;
import model.RoadSegment;

/***
 * @author H4114
 * @see algo.Graph
 * @see algo.CompleteGraph
 * @see algo.TimeWindowGraph
 */
public class GraphTest {
	
	//Fields 
	Graph graph;
	CompleteGraph completeGraph;
	TimeWindowGraph timeGraph;
	
	/**
	 * Set up the initial state before each test execution.
	 * <p>
	 * This method initializes instances of Graph, CompleteGraph, and TimeWindowGraph for testing
	 * purposes. It creates specific structures and values to ensure a consistent starting state for each test.
	 *
	 * @see Graph
	 * @see CompleteGraph
	 * @see TimeWindowGraph
	 */
	@Before
	public void setUp() {
		// Cost matrix for testing
		Double[][] cost = {
				{-1.0, 1.0, 1.0, 1.0},
				{1.0, -1.0, 1.0, 1.0},
				{1.0, 1.0, -1.0, 1.0},
				{1.0, 1.0, 1.0, -1.0}
		};

		// Time constraint array for testing
		int[] timeConstraint = {1, 2, 3, 4, 5};

		// Initializing instances of Graph, CompleteGraph, and TimeWindowGraph
		graph = new Graph();
		completeGraph = new CompleteGraph(cost);
		timeGraph = new TimeWindowGraph(cost, timeConstraint);
	}

	/**
	 * Test the addition of a road segment to the graph.
	 * <p>
	 * This method initializes a road segment, adds it to the graph using the tested method, and then
	 * asserts that the resulting graph's string representation is as expected.
	 *
	 * @see Graph
	 * @see RoadSegment
	 * @see Intersection
	 */
	@Ignore
	public void addEdge() {
		// Initialization of a road segment
		RoadSegment rs = new RoadSegment("Avenue Albert Einstein",new Intersection(1L, 1.0, 1.0),new Intersection(2L, 2.0, 2.0),10);

		// Adding the road segment to the graph using the tested method
		graph.addEdge(rs);
		// Asserting the value of the graph's string representation after adding the edge
		assertEquals("", graph.toString());
	}


	/**
	 * Test the retrieval of the cost from a CompleteGraph.
	 * <p>
	 * This method calls the tested method to get the cost at a specified position (0, 0) in a
	 * CompleteGraph and asserts that the retrieved value is equal to the expected value.
	 *
	 * @see CompleteGraph
	 */
	@Test
	public void getCost() {
		// Asserting that the cost at position (0, 0) in the CompleteGraph is equal to -1.0
		assertEquals("The method getCost don't work",-1.0,completeGraph.getCost(0, 0),0);
	}

	/***
	 * Test of the CompleteGraph's method isArc when the arc don't exist
	 */
	@Test
	public void isArcF() {
		assertFalse("(0,0) is an arc",completeGraph.isArc(0, 0));
	}

	/**
	 * Test the identification of an arc in a CompleteGraph.
	 * <p>
	 * This method calls the tested method to check if there is an arc from node 1 to node 0 in a
	 * CompleteGraph and asserts that the result is true.
	 *
	 * @see CompleteGraph
	 */
	@Test
	public void isArcT() {
		// Asserting that there is an arc from node 1 to node 0 in the CompleteGraph
		assertTrue("(1,0) is not an arc",completeGraph.isArc(1, 0));
	}


	/**
	 * Test the removal of an arc in a TimeWindowGraph.
	 * <p>
	 * This method calls the tested method to remove the arc from node 1 to node 0 in a TimeWindowGraph.
	 * It then asserts that the cost of the removed arc is equal to -1.0, indicating successful removal.
	 *
	 * @see TimeWindowGraph
	 */
	@Test
	public void removeArc() {
		// Use the tested method to remove the arc from node 1 to node 0
		timeGraph.removeArc(1, 0);

		// Asserting that the cost of the removed arc is -1.0
		assertEquals("The arc(1,0) isn't removed",-1.0,timeGraph.getCost(1, 0),0);
	}


	/**
	 * Test the removal of arcs in a TimeWindowGraph to satisfy time constraints.
	 * <p>
	 * This method calls the tested method to remove arcs in a TimeWindowGraph based on time constraints.
	 * It then asserts that the state of the new graph is as expected, represented by an empty string in the graph's string representation.
	 *
	 * @see TimeWindowGraph
	 */
	@Ignore
	public void removeArcsToSatisfyTimeConstraints() {
		// Use the tested method to remove arcs based on time constraints
		timeGraph.removeArcsToSatisfyTimeConstraints();

		// Asserting that the state of the new graph is as expected (empty string representation)
		assertEquals("", timeGraph.toString());
	}



}