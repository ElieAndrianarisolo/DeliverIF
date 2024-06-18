package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;


import model.Intersection;

/***
 * @author H4114
 * @see model.Intersection
 */
public class IntersectionTest {
	
	//Fields
	Intersection intersection;
	
	/**
	 * Set up the test environment before each test case.
	 * <p>
	 * This method initializes an Intersection object with specific values (id=0, latitude=0.0, longitude=0.0) to be used in the test cases.
	 * It ensures that the Intersection object is ready for testing.
	 *
	 * @see Intersection
	 */
	@Before
	public void setUp() {
		// Initialize an Intersection object with specific values for testing
		intersection = new Intersection();
		assertNotNull(intersection);
		intersection = new Intersection((long) 0, 0.0, 0.0);
		assertNotNull(intersection);
	}
	
	/***
	 * Test Intersection's getters
	 * 
	 * @see model.Intersection#getIdIntersection()
	 * @see model.Intersection#getLatitude()
	 * @see model.Intersection#getLongitude()
	 */
	@Test
	public void getterTest() {
		assertEquals(Long.valueOf(0L),intersection.getIdIntersection());
		assertEquals(0.,intersection.getLatitude(),0);
		assertEquals(0.,intersection.getLongitude(),0);
	}
	
	/***
	 * Test Intersection's setters
	 * 
	 * @see model.Intersection#setIdIntersection(Long)
	 * @see model.Intersection#setLatitude(Double)
	 * @see model.Intersection#setLongitude(Double)
	 */
	@Test
	public void setterTest() {
		intersection.setIdIntersection(2L);
		intersection.setLatitude(10.);
		intersection.setLongitude(10.);
		
		assertEquals(Long.valueOf(2L),intersection.getIdIntersection());
		assertEquals(10.,intersection.getLatitude(),0);
		assertEquals(10.,intersection.getLongitude(),0);
	}

	/***
	 * Test if the computing of an intersection hashcode works
	 * @see model.Intersection#hashCode()
	 */
	@Test
	public void hashCodeTest() {
		assertEquals(29791,intersection.hashCode());
	}
	
	/***
	 * Test if the method equals works
	 * @see model.Intersection#equals(Object)
	 * 
	 */
	@Test
	public void equalsTest() {
		assertTrue(intersection.equals(intersection));
		assertTrue(intersection.equals(new Intersection(0L,0.,0.)));
		assertFalse(intersection.equals(new Intersection(1L,0.,0.)));
		assertFalse(intersection.equals(new Point()));
		assertFalse(intersection.equals(null));
	}
	
	/**
	 * Test the string representation of the Intersection object.
	 * <p>
	 * This method initializes an Intersection object with ID 0 and coordinates (0.0, 0.0).
	 * It then checks if the string representation of the Intersection matches the expected value.
	 * The test asserts that the generated string accurately reflects the state of the Intersection object.
	 *
	 * @see model.Intersection#toString()
	 */
	@Test
	public void toStringTest() {
		// Initialize an Intersection object
		intersection = new Intersection((long) 0, 0.0, 0.0);
		// Assert that the string representation of the Intersection matches the expected value
		assertEquals("The method toString don't work","Intersection [idIntersection=0, latitude=0.0, longitude=0.0]", intersection.toString());
	}

}
