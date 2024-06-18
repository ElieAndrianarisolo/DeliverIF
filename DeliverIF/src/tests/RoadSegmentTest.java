package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Intersection;
import model.RoadSegment;

/***
 * @author H4114
 * @see model.RoadSegment
 */

public class RoadSegmentTest {
	
	//Fields
	RoadSegment road;
	Intersection origin;
	Intersection destination;

	/**
	 * Set up the test environment by creating an Intersection origin, an Intersection destination,
	 * and a RoadSegment connecting the two.
	 *
	 * @see Intersection
	 * @see RoadSegment
	 */
	@Before
	public void setUp() {
		
		road = new RoadSegment();
		assertNotNull(road);
		// Create an Intersection object for the origin
		origin = new Intersection((long) 1, 90.0, -25.0);

		// Create an Intersection object for the destination
		destination = new Intersection((long) 2, 50.0, -25.0);

		// Create a RoadSegment connecting the origin and destination
		road = new RoadSegment("Avenue Albert Einstein", origin, destination, 100);
		
		assertNotNull(road);
	}
	
	/***
	 * Test if RoadSegment's getters works
	 * 
	 * @see model.RoadSegment#getStreetName()
	 * @see model.RoadSegment#getStartingIntersection()
	 * @see model.RoadSegment#getEndingIntersection()
	 * @see model.RoadSegment#getLength()
	 */
	@Test
	public void gettersTest() {
		assertEquals("Avenue Albert Einstein",road.getStreetName());
		assertTrue(origin.equals(road.getStartingIntersection()));
		assertTrue(destination.equals(road.getEndingIntersection()));
		assertEquals(100,road.getLength(),0);
	}
	
	/***
	 * Test if RoadSegment's getters works
	 * 
	 * @see model.RoadSegment#setStreetName(String)
	 * @see model.RoadSegment#setStartingIntersection(Intersection)
	 * @see model.RoadSegment#setEndingIntersection(Intersection)
	 * @see model.RoadSegment#setLength(double)
	 */
	@Test
	public void settersTest() {
		
		road.setStreetName("Faubourg Saint-Honore");
		road.setStartingIntersection(destination);
		road.setEndingIntersection(origin);
		road.setLength(50);
		
		assertEquals("Faubourg Saint-Honore",road.getStreetName());
		assertTrue(destination.equals(road.getStartingIntersection()));
		assertTrue(origin.equals(road.getEndingIntersection()));
		assertEquals(50,road.getLength(),0);
	}
	
	/**
	 * Test the string representation of the RoadSegment for accurate details.
	 * <p>
	 * This method asserts that the string representation of the RoadSegment named "Avenue Albert Einstein" matches the expected value.
	 * The expected value includes details such as street name, starting and ending intersections with their IDs, latitudes, and longitudes,
	 * as well as the length of the road segment.
	 *
	 * @see RoadSegment
	 * @see Intersection
	 */
	@Test
	public void toStringTest() {
		// Assert the details of the RoadSegment
		assertEquals("The method toString don't work","RoadSegment [streetName=Avenue Albert Einstein, startingIntersection=Intersection [idIntersection=1, latitude=90.0, longitude=-25.0], endingIntersection=Intersection [idIntersection=2, latitude=50.0, longitude=-25.0], length=100.0]",
				road.toString());
	}

}
