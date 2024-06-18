package tests;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import model.DeliveryRequest;

/***
 * @author H4114
 * @see model.DeliveryRequest
 */
public class DeliveryRequestTest {
	
	//Fields
	DeliveryRequest request;

	/**
	 * Set up the initial state before each test execution.
	 * <p>
	 * This method initializes a DeliveryRequest object with specific values to create a consistent
	 * starting state for each test. It ensures that the 'request' object is ready for testing.
	 *
	 */
	@Before
	public void setUp() {
		// Initializing a DeliveryRequest object for testing
		request = new DeliveryRequest();
		assertNotNull(request);
		
		request = new DeliveryRequest(1L, 90.0, -25.0, 1);
		assertNotNull(request);
	}
	
	/***
	 * Test DeliveryRequest's getters
	 * 
	 * @see model.DeliveryRequest#getArrivalTime()
	 * @see model.DeliveryRequest#getDepartureTime()
	 * @see model.DeliveryRequest#getIdIntersection()
	 * @see model.DeliveryRequest#getLatitude()
	 * @see model.DeliveryRequest#getLongitude()
	 * @see model.DeliveryRequest#getTimeWindow()
	 * @see model.DeliveryRequest#getUnloadingTime()
	 * @see model.DeliveryRequest#getWaitingTime()
	 */
	@Test
	public void getterTest() {
		DeliveryRequest rq = new DeliveryRequest(1L, 90.0, -25.0,1, LocalTime.of(1, 0),LocalTime.of(1, 10));
		
		assertEquals(LocalTime.of(1, 10),rq.getArrivalTime());
		assertEquals(LocalTime.of(1, 0),rq.getDepartureTime());
		
		assertEquals(Long.valueOf(1L),rq.getIdIntersection());
		assertEquals(90,rq.getLatitude(),0);
		assertEquals(-25,rq.getLongitude(),0);
		assertEquals(1,rq.getTimeWindow());
		assertEquals(300,rq.getUnloadingTime());
		assertEquals(null,rq.getWaitingTime());
	}
	
	/***
	 * Test DeliveryRequest's getters
	 * 
	 * @see model.DeliveryRequest#setArrivalTime(LocalTime)
	 * @see model.DeliveryRequest#setDepartureTime(LocalTime)
	 * @see model.DeliveryRequest#setIdIntersection(Long)
	 * @see model.DeliveryRequest#setLatitude(Double)
	 * @see model.DeliveryRequest#setLongitude(Double)
	 * @see model.DeliveryRequest#setTimeWindow(Integer)
	 * @see model.DeliveryRequest#setWaitingTime(LocalTime)
	 */
	@Test
	public void setterTest() {

		request.setArrivalTime(LocalTime.of(1, 0));
		request.setDepartureTime(LocalTime.of(2, 0));
		request.setIdIntersection(1L);
		request.setLatitude(10.0);
		request.setLongitude(10.0);
		request.setTimeWindow(0);
		request.setWaitingTime(LocalTime.of(0, 5));

		assertEquals(LocalTime.of(1, 0),request.getArrivalTime());
		assertEquals(LocalTime.of(2, 0),request.getDepartureTime());
		assertEquals(Long.valueOf(1L),request.getIdIntersection());
		assertEquals(10.0,request.getLatitude(),0);
		assertEquals(10.0,request.getLongitude(),0);
		assertEquals(0,request.getTimeWindow());
		assertEquals(LocalTime.of(0, 5),request.getWaitingTime());
	}

	/**
	* Test the string representation of a DeliveryRequest.
	* <p>
	* This method tests the correctness of the string representation of a DeliveryRequest by comparing
	* it to the expected result. It checks that the generated string matches the specified format.
	*
	*/
	@Test
	public void toStringTest() {
		// Asserting that the generated string matches the expected result
		assertEquals("The method toString don't work","DeliveryRequest [idIntersection=1, latitude=90.0, longitude=-25.0, departureTime=null, arrivalTime=null, timeWindow=1, deliveringTime=300 s, waitingTime=null]",
				request.toString());
	}


	
}
