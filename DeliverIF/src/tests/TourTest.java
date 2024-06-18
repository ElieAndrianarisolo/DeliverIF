package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.Controller;
import model.CityMap;
import model.Courier;
import model.DeliveryRequest;
import model.Intersection;
import model.RoadSegment;
import model.Tour;
import model.Warehouse;
import parser.XMLdeserializer;

/***
 * @author H4114
 * @see model.Tour
 */
public class TourTest {
	
	//Fields
	Tour tour;
	Warehouse warehouse;
	Courier courier;
	Controller controller;
	XMLdeserializer deserializer;

	/**
	 * Set up the initial state for the Tour test.
	 * <p>
	 * This method creates a Tour object for testing purposes. The Tour is initialized with an ID of 0,
	 * a Warehouse located at latitude 0.0 and longitude 0.0, and a Courier with ID 1 and name "Tony".
	 * The created Tour object is assigned to the 'tour' instance variable for further testing.
	 *
	 * @see Tour
	 * @see Warehouse
	 * @see Courier
	 */
	@Before
	public void setUp() {
		
		courier = new Courier(1,"Tony");
		warehouse = new Warehouse(1L, 0.0, 0.0);
		
		tour = new Tour();
		assertNotNull(tour);
		
		tour = new Tour(0,warehouse);
		assertNotNull(tour);
		
		tour = new Tour(0,warehouse,new ArrayList<DeliveryRequest>(),courier);
		assertNotNull(tour);
		
		
		tour = new Tour(0,warehouse , courier);
		assertNotNull(tour);
		
		controller = new Controller();
	}
	
	/***
	 * Test if Tour's getters work
	 * 
	 * @see model.Tour#getIdTour()
	 * @see model.Tour#getWarehouse()
	 * @see model.Tour#getCourier()
	 * @see model.Tour#getLength()
	 * @see model.Tour#getStartTime()
	 * @see model.Tour#getEndTime()
	 * @see model.Tour#getListDeliveryRequests()
	 * @see model.Tour#getListRoadSegments()
	 */
	@Test
	public void gettersSet() {
		assertEquals(Integer.valueOf(0),tour.getIdTour());
		assertTrue(warehouse.equals(tour.getWarehouse()));;
		assertTrue(courier.equals(tour.getCourier()));
		assertEquals(0,tour.getLength(),0);
		assertEquals(null,tour.getStartTime());
		assertEquals(null,tour.getEndTime());
		assertEquals("[]",tour.getListDeliveryRequests().toString());
		assertEquals("[]",tour.getListRoadSegments().toString());


	}
	
	/***
	 * Test if Tour's setters work
	 * 
	 * @see model.Tour#setWarehouse(Warehouse)
	 * @see model.Tour#setCourier(Courier)
	 * @see model.Tour#setDeliveryRequests(List)
	 * @see model.Tour#setStartTime(LocalTime)
	 * @see model.Tour#setEndTime(LocalTime)
	 * @see model.Tour#setLength(double)
	 */
	@Test
	public void settersSet() {
		
		List<DeliveryRequest> listDeliveryRequests= new ArrayList<DeliveryRequest>();
		listDeliveryRequests.add(new DeliveryRequest(1L, 10., 10., 1));
		listDeliveryRequests.add(new DeliveryRequest(2L, 20., 20., 2));
		listDeliveryRequests.add(new DeliveryRequest(3L, 30., 30., 3));
		listDeliveryRequests.add(new DeliveryRequest(4L, 40., 40., 4));
		
		tour.setDeliveryRequests(listDeliveryRequests);
		
		assertArrayEquals(listDeliveryRequests.toArray(),tour.getListDeliveryRequests().toArray());
		
		List<RoadSegment> listRoadSegments= new ArrayList<RoadSegment>();
		listRoadSegments.add(new RoadSegment("A", new Intersection(1L, 10.,10.), new Intersection(2L,20.,20.), 1));
		listRoadSegments.add(new RoadSegment("B", new Intersection(1L,10.,10.), new Intersection(3L,30.,30.), 2));
		listRoadSegments.add(new RoadSegment("C", new Intersection(2L,20.,20.), new Intersection(3L,30.,30.), 3));
		listRoadSegments.add(new RoadSegment("D", new Intersection(3L,30.,30.), new Intersection(4L,40.,40.), 4));
		
		tour.setListRoadSegments(listRoadSegments);
		
		assertArrayEquals(listRoadSegments.toArray(),tour.getListRoadSegments().toArray());
		
		tour.setStartTime(LocalTime.of(0, 0));
		assertEquals(LocalTime.of(0, 0),tour.getStartTime());
		
		tour.setEndTime(LocalTime.of(1, 30));
		assertEquals(LocalTime.of(1, 30),tour.getEndTime());
		
		
		Courier nico = new Courier(2,"Nicolas");
		tour.setCourier(nico);
		assertTrue(nico.equals(tour.getCourier()));
		
		tour.setLength(2000);
		assertEquals(2000,tour.getLength(),0);
		
		
	}
	
	/**
	 * Parameterized test for adding a delivery request to a tour.
	 * <p>
	 * This test method adds a delivery request to a tour with various input values,
	 * including latitude, longitude, and arrival time. The delivery request is created
	 * based on the provided parameters and added to the tour using the addDeliveryRequest method.
	 * The test then asserts that the added delivery request in the tour matches the expected value.
	 *
	 * @param index        The index of the delivery request.
	 * @param latitude     The latitude of the delivery request location.
	 * @param longitude    The longitude of the delivery request location.
	 * @param arrivalTime  The arrival time of the delivery request.
	 *
	 * @see Tour
	 * @see DeliveryRequest
	 * @see Warehouse
	 * @see Courier
	 */
	@ParameterizedTest
	@CsvSource(value = {"2;90;-25;1", "2;-10;-10;2", "2;-40;15;4", "2;90;25;8"}, delimiter = ';')
	public void addDeliveryRequestTest(long index, double latitude, double longitude, int arrivalTime) {
		// Create a delivery request based on the provided parameters
		DeliveryRequest request = new DeliveryRequest((long) index, latitude, longitude, 0);

		// Create a tour with a warehouse and courier
		tour = new Tour(1, new Warehouse(0L, 0.0, 0.0), new Courier(1, "Tony"));

		// Add the delivery request to the tour
		tour.addDeliveryRequest(request);

		// Assert that the added delivery request in the tour matches the expected value
		assertEquals(request.toString(), tour.getListDeliveryRequests().get(0).toString());
	}


	/**
	 * Parameterized test for removing a delivery request from a tour.
	 * <p>
	 * This test method adds a delivery request to a tour and then removes it based on the provided parameters,
	 * including the index of the delivery request to be removed. The test asserts that the list of delivery
	 * requests in the tour is empty after the removal.
	 *
	 * @param index        The index of the delivery request to be added and removed.
	 * @param latitude     The latitude of the delivery request location.
	 * @param longitude    The longitude of the delivery request location.
	 * @param arrivalTime  The arrival time of the delivery request.
	 *
	 * @see Tour
	 * @see DeliveryRequest
	 * @see Warehouse
	 * @see Courier
	 */
	@ParameterizedTest
	@CsvSource(value = {"1;90;-25;1", "1;-10;-10;2", "1;-40;15;4", "1;91;25;8"}, delimiter = ';')
	public void removeDeliveryRequestTest(long index, double latitude, double longitude, int arrivalTime) {
		// Create a delivery request based on the provided parameters
		DeliveryRequest request = new DeliveryRequest((long) index, latitude, longitude, 0);

		// Create a tour with a warehouse and courier
		tour = new Tour(2, new Warehouse(0L, 0.0, 0.0), new Courier(1, "Tony"));

		// Add the delivery request to the tour
		tour.addDeliveryRequest(request);

		// Remove the delivery request from the tour based on the provided index
		tour.removeDeliveryRequest(0);

		// Assert that the list of delivery requests in the tour is empty after the removal
		assertEquals(0,tour.getListDeliveryRequests().size());
	}

	/**
	 * Parameterized test for resetting a tour.
	 * <p>
	 * This test method adds a delivery request to a tour, then resets the tour, and finally asserts
	 * that the list of delivery requests and road segments in the tour is empty after the reset.
	 *
	 * @param index        The index of the delivery request to be added.
	 * @param latitude     The latitude of the delivery request location.
	 * @param longitude    The longitude of the delivery request location.
	 * @param arrivalTime  The arrival time of the delivery request.
	 *
	 * @see Tour
	 * @see DeliveryRequest
	 * @see Warehouse
	 * @see Courier
	 */
	@ParameterizedTest
	@CsvSource(value = {"1;90;-25;1", "1;-10;-10;2", "1;-40;15;4", "1;91;25;8"}, delimiter = ';')
	public void resetTourTest(long index, double latitude, double longitude, int arrivalTime) {
		// Create a tour with a warehouse and courier
		tour = new Tour(3, new Warehouse(0L, 0.0, 0.0), new Courier(1, "Tony"));

		// Create a delivery request based on the provided parameters
		DeliveryRequest request = new DeliveryRequest((long) index, latitude, longitude, 0);

		// Add the delivery request to the tour
		tour.addDeliveryRequest(request);

		// Reset the tour
		tour.reset();

		// Assert that the list of delivery requests in the tour is empty after the reset
		assert (tour.getListDeliveryRequests().size() == 0);

		// Assert that the list of road segments in the tour is empty after the reset
		assert (tour.getListRoadSegments().size() == 0);
	}

	
	/**
	 * Test method that verifies the string representation of an empty tour.
	 * It ensures that the tour's details are correctly formatted when there are no delivery requests.
	 */
	@Test
	public void toStringEmptyTourTest() {
		// Create an empty tour with a warehouse and courier
		tour = new Tour(4, new Warehouse(0L, 0.0, 0.0), new Courier(1, "Tony"));

		// Verify the string representation of the empty tour
		assertEquals("Tour [length=0.0, courier=Courier [idCourier=1, name=Tony, color="+tour.getCourier().getColor()+"], listRoadSegments=[], listDeliveryRequests=[],warehouse=Warehouse [idIntersection=0, latitude=0.0, longitude=0.0]]", tour.toString());
	}
	
	/**
	 * Parameterized test that verifies the string representation of a tour with a given delivery request.
	 *
	 * @param id           The ID of the delivery request.
	 * @param latitude     The latitude of the delivery request.
	 * @param longitude    The longitude of the delivery request.
	 */
	@ParameterizedTest
	@CsvSource(value = {"1;90;-25", "1;-10;-10", "1;-40;15", "1;91;25"}, delimiter = ';')
	public void toStringTest(long id, double latitude, double longitude) {

		// Create a delivery request with the provided parameters
		DeliveryRequest request = new DeliveryRequest((long) id, latitude, longitude, 0);

		// Initialize a tour with a warehouse and courier
		courier = new Courier(1, "Tony");
		Color color = courier.getColor();
		tour = new Tour(5, new Warehouse(0L, 0.0, 0.0),courier);

		// Add the delivery request to the tour
		tour.addDeliveryRequest(request);

		// Verify the string representation of the tour
		assertEquals("The method toString don't work","Tour [length=0.0, courier=Courier [idCourier=1, name=Tony, color="+color+"], " +
				"listRoadSegments=[], listDeliveryRequests=[DeliveryRequest [idIntersection=1, latitude=" + latitude +
				", longitude=" + longitude + ", " +
				"departureTime=null, arrivalTime=null, timeWindow=0, deliveringTime=300 s, waitingTime=null]],warehouse=Warehouse [idIntersection=0, " +
				"latitude=0.0, longitude=0.0]]",
				tour.toString());
	}

	/**
	 * Parameterized test for computing the best path in a tour.
	 * 
	 * @param map      The file path to the XML file containing the city map.
	 * @param requests The file path to the XML file containing delivery requests.
	 */
	@ParameterizedTest
	@CsvSource(value = {"src/tests/compute/a_map_compute.xml;src/tests/compute/deliveriesrequestscompute.xml"}, delimiter = ';')
	public void computeBestPathTest(String map, String requests) {
		// Initialize city map, list of intersections, and a tour
		CityMap cityMap = new CityMap(new HashMap<Long, Intersection>(),new ArrayList<RoadSegment>());
		JPanel leftPanel = new JPanel();
		Controller controller = new Controller();

		leftPanel.setBounds(new Rectangle(new Dimension(200,200)));
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());	
		
		//Initialize a tour
		controller.launch();
		controller.manageCouriers();
		controller.createCourier("Tony");
		controller.loadCityMap(map);
		controller.loadDeliveryRequests(requests);
		cityMap = controller.getCityMap();
		controller.initializeTour();

		//Add delivery requests
		for(Intersection i : cityMap.getListDeliveryRequests()) {
			if(i instanceof DeliveryRequest) {
				cityMap.selectDeliveryRequest((DeliveryRequest) i);
				controller.setCityMap(cityMap);
				controller.addDeliveryRequestToTour();
				cityMap = controller.getCityMap();
			}
		}
		cityMap.selectDeliveryRequest(null);
		controller.setCityMap(cityMap);

		//Use of the method being tested
		controller.computeBestTour();
		controller.getListTour().toString();
	}
}





