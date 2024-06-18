package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Warehouse;

/***
 * @author H4114
 * @see model.Warehouse
 */
public class WarehouseTest {
	
	//Fields
	Warehouse warehouse;
	
	/**
	 * Set up method executed before each test case. 
	 * Initializes a Warehouse object with a specified ID, latitude, longitude, 
	 * and the number of outgoing road segments.
	 */
	@Before
	public void setUp() {
		warehouse=new Warehouse();
		assertNotNull(warehouse);
		warehouse = new Warehouse(1L, 0.0, 0.0);
		assertNotNull(warehouse);
	}


	/**
	 * Test method for checking the correct string representation of a Warehouse object.
	 * It asserts that the warehouse's string representation matches the expected value.
	 * The expected value includes the warehouse's ID, latitude, longitude, and the count of incoming and outgoing road segments.
	 */
	@Test
	public void toStringTest() {
		assertEquals("Warehouse [idIntersection=1, latitude=0.0, longitude=0.0]", warehouse.toString());
	}

}
