package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;
import java.time.LocalTime;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import model.CityMap;
import model.DeliveryRequest;
import model.Intersection;
import model.RoadSegment;
import model.Warehouse;

import parser.ExceptionXML;

/***
 * @author H4114
 * @see model.CityMap
 */
public class CityMapTest {
	
	//Fields
	CityMap cityMap;
	
	/***
	 * Compute the minimum of a double's array
	 * 
	 * @param tab
	 * @return
	 */
	public double minTab(double[] tab){
		double min =999999.;
		for(double d: tab) {
			if(d < min)
				min = d;
		}
		return min;
	}
	
	/***
	 * Compute the the maximum of a double's array
	 * @param tab
	 * @return
	 */
	public double maxTab(double[] tab){
		double max = -999999.;
		for(double d: tab) {
			if(d > max)
				max = d;
		}
		return max;
	}
	
	//Initialize the cityMap
	@Before
	public void setUp(){
		cityMap=new CityMap();
		assertNotNull(cityMap);
		cityMap = new CityMap(new HashMap<Long, Intersection>(), new ArrayList<RoadSegment>());
		assertNotNull(cityMap);
	}
	/***
	 * Test CityMap's getters
	 * 
	 * @see model.CityMap#getLatitudeMin()
	 * @see model.CityMap#getLatitudeMax()
	 * @see model.CityMap#getLongitudeMin()
	 * @see model.CityMap#getLongitudeMax()
	 * @see model.CityMap#getListDeliveryRequests()
	 * @see model.CityMap#getListIntersections()
	 * @see model.CityMap#getListRoadSegments()
	 * @see model.CityMap#getSelectedIntersection()
	 * @see model.CityMap#getSelectedRoadSegment()
	 * 
	 */
	@Test
	public void gettersTest(){

		assertEquals("The Latitude min getter don't work",999999,cityMap.getLatitudeMin(),0);
		assertEquals("The Latitue max's getter don't work",0,cityMap.getLatitudeMax(),0);
		assertEquals("The Longitude min's getter don't work",999999,cityMap.getLongitudeMin(),0);
		assertEquals("The Longitude max's getter don't work",0,cityMap.getLongitudeMax(),0);
		assertEquals("The List of delivery requests's getter don't work",null,cityMap.getListDeliveryRequests());
		assertEquals("The List of intersections's getter don't work","{}",cityMap.getListIntersections().toString());
		assertEquals("The List of road segments's getter don't work","[]",cityMap.getListRoadSegments().toString());
		assertEquals("The selected intersection's getter don't work",null,cityMap.getSelectedIntersection());
		assertEquals("The selected road segment min's getter don't work",null,cityMap.getSelectedRoadSegment());
		
	}
	
	/***
	 * Test CityMap's setters
	 * 
	 * @see model.CityMap#setListDeliveryRequests(List)
	 * @see model.CityMap#setListIntersections(List)
	 * @see model.CityMap#setListRoadSegments(List)
	 * @see model.CityMap#setSelectedRoadSegment(RoadSegment)
	 */
	@Test
	public void settersTest(){
		
		HashMap<Long,Intersection> intersections = new HashMap<Long,Intersection>();
		intersections.put(1L,new Intersection(1L, 0.,0.));
		intersections.put(2L,new Intersection(2L,1.,1.));

		List<RoadSegment> roadSegments = new ArrayList<RoadSegment>();
		roadSegments.add(new RoadSegment("Avenue Albert Einstein",new Intersection(1L, 0.,0.),new Intersection(2L, 1.,1.),10));
		roadSegments.add(new RoadSegment("Rue Bouteille",new Intersection(1L, 0.,0.),new Intersection(3L, 3.,3.),10));

		List<Intersection> requests = new ArrayList<Intersection>();
		requests.add(new Intersection(1L, 0.,0.));
		requests.add(new Intersection(2L,1.,1.));

		cityMap.setListDeliveryRequests(requests);
		cityMap.setListIntersections(intersections);
		cityMap.setListRoadSegments(roadSegments);
		cityMap.setSelectedRoadSegment(new RoadSegment("Avenue Albert Einstein",new Intersection(1L, 0.,0.),new Intersection(2L, 1.,1.),10));

		assertArrayEquals("The delivery requests setting did not happen",requests.toArray(), cityMap.getListDeliveryRequests().toArray());
		assertArrayEquals("The road segments setting did not happen",roadSegments.toArray(), cityMap.getListRoadSegments().toArray());
		assertEquals("The selected road setting did not happen",roadSegments.get(0).toString(), cityMap.getSelectedRoadSegment().toString());
		assertEquals("The first intersection setting did not happen",intersections.get(1L).toString(), cityMap.getListIntersections().get(1L).toString());
		assertEquals("The second intersection setting did not happen",intersections.get(2L).toString(), cityMap.getListIntersections().get(2L).toString());
		
	}

	/**
	 * Test the CityMap's first method addIntersection with :
	 * 	<ol>
	 * 		<li>Corrects values</li>
	 * 		<li>Incorrect id</li>
	 * 		<li>Incorrects latitude or longitude</li>
	 * 		<li>On bounds</li>
	 * 	</ol>
	 * 
	 * @param id The id of the intersection
	 * @param latitude The latitude of the intersection
	 * @param longitude The longitude of the inersection
	 * 
	 * @see model.Intersection
	 * @see model.CityMap#addIntersection(Intersection)
	 */
	@ParameterizedTest
	@CsvSource(value = { "1;90;-25", "-1;90;-25", "1;91;-25", "1;-91;-25", "1;90;-181", "1;90;181","1;-90;-25","1;90;180","1;90;-180"}, delimiter = ';')
	public void addIntersectionTest1(long id, double latitude, double longitude) {
		
		//Initialization
		cityMap = new CityMap(new HashMap<Long, Intersection>(), new ArrayList<RoadSegment>());
		
		// Creating an intersection with the provided parameters
		Intersection intersection = new Intersection((long) id, latitude, longitude);
		
		try {
			//Use of the method being tested
			cityMap.addIntersection(intersection);
		} catch (Exception e) {

			//Catching the wanted exception
			assertEquals("Unexpected intersection",new ExceptionXML("Valeur incorrectes(id<0;|latitude|>90;|longitude|>180)").toString(),
					e.toString());
		}

		//Testing the intersection added
		if (cityMap.getListIntersections().get(id) != null)
			assertEquals("The intersection isn't added",intersection.toString(), cityMap.getListIntersections().get(id).toString());
	}

	/**
	 * Test the CityMap's second method addIntersection with :
	 * <ol>
	 * 		<li>Corrects values</li>
	 * 		<li>Incorrect id</li>
	 * 		<li>Incorrects latitude or longitude</li>
	 * 		<li>On bounds</li>
	 * 	</ol>
	 * 
	 * @param id The id of the intersection
	 * @param latitude The latitude of the intersection
	 * @param longitude The longitude of the inersection
	 * 
	 * @see model.Intersection
	 * @see model.CityMap#addIntersection(Long, Double, Double)
	 */
	@ParameterizedTest
	@CsvSource(value = { "1;90;-25", "-1;90;-25", "1;91;-25", "1;-91;-25", "1;90;-181", "1;90;181","1;-90;-25","1;90;180","1;90;-180"}, delimiter = ';')
	public void addIntersectionTest2(long id, double latitude, double longitude) {
		
		//Initialization
		cityMap = new CityMap(new HashMap<Long, Intersection>(), new ArrayList<RoadSegment>());

		try {
			//Use of the method being tested
			cityMap.addIntersection(id, latitude, longitude);
		} catch (Exception e) {
			
			//Catching the expected exception
			assertEquals("Unexpected exception",new ExceptionXML("Valeur incorrectes(id<0;|latitude|>90;|longitude|>180)").toString(),
					e.toString());
		}

		// Verifying if the intersection has been added to the CityMap
		if (cityMap.getListIntersections().get(id) != null)
			assertEquals("The intersection isn't added","Intersection [idIntersection=" + id + ", latitude=" + latitude + ", longitude=" + longitude+"]",cityMap.getListIntersections().get(id).toString());
	}
	
	/**
	 * Test the CityMap's method addWarehouse
	 * 
	 * @see model.Warehouse
	 * @see model.CityMap#addWarehouse(Long, double, double)
	 */
	@Test
	public void addWarehouseTest() {
		
		//Initialization
		cityMap = new CityMap(new HashMap<Long, Intersection>(), new ArrayList<RoadSegment>());
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());	

		// Creating a warehouse for testing
		Warehouse warehouse = new Warehouse(1L,90.,-25.);
		
		try {

			//Use of the tested method
			cityMap.addWarehouse(1L,90.,-25.);

		} catch (Exception e) {
			// Fail the test if an exception is caught
			assertEquals("Unexpected exception","",e.toString());
		}
		// Testing the added warehouse
		if(cityMap.getListDeliveryRequests().get(0)!=null)
			assertEquals("The warehouse isn't added",cityMap.getListDeliveryRequests().get(0).toString(), warehouse.toString());
	}

	/**
	 * Test the CityMap's method addRoadSegment
	 * 
	 * @param idO The id of the starting intersection
	 * @param latitudeO The latitude of the starting intersection
	 * @param longitudeO The longitude of the starting intersection
	 * @param idD The id of the destination
	 * @param latitudeD The latitude of the destination
	 * @param longitudeD The longitude of the destination
	 * 
	 * @see model.Intersection
	 * @see model.RoadSegment
	 * 
	 * @see model.CityMap#addRoadSegment(String, Intersection, Intersection, double)
	 */
	@ParameterizedTest
	@CsvSource(value = { "1;90;-25;2;-10;-10", "2;90;-25;1;-10;-10" }, delimiter = ';')
	public void addRoadSegmentTest(long idO, double latitudeO, double longitudeO, long idD, double latitudeD, double longitudeD) {
		// Initialization
		cityMap = new CityMap(new HashMap<Long, Intersection>(), new ArrayList<RoadSegment>());
		
		// Generating a random length for the road segment
		double length = Math.random() * 100;

		// Creating intersections for the road segment
		Intersection origin = new Intersection(idO, latitudeO, longitudeO);
		Intersection destination = new Intersection(idD, latitudeD, longitudeD);

		// Use of the method being tested
		cityMap.addRoadSegment("Avenue Albert Einstein", origin, destination, length);

		// Verifying the value of the road segment added to the CityMap
		assertEquals("The road segment isn't added correctly",
				"RoadSegment [streetName=Avenue Albert Einstein, startingIntersection=Intersection [idIntersection="+idO+", latitude="+latitudeO+", longitude="+longitudeO+"], endingIntersection=Intersection [idIntersection="+idD+", latitude="+latitudeD+", longitude="+longitudeD+"], length="+length+"]",
				cityMap.getListRoadSegments().get(0).toString());
	}

	/**
	 * Test the CityMap's first method addDeliveryRequest with :
	 * 		<ol>
	 * 			<li>Corrects values</li>
	 * 			<li>Incorrect time window</li>
	 * 			<li>On bounds</li>
	 * 		</ol>
	 * 
	 *  @param id          The id of the delivery request.
	 *  @param latitude    The latitude of the delivery request location.
	 *  @param longitude   The longitude of the delivery request location.
	 *  @param timeWindow  The time window for the delivery request.
	 * 
	 *  @see model.DeliveryRequest
	 * 	@see model.CityMap#addDeliveryRequest(Long, double, double, int)
	 */
	@ParameterizedTest
	@CsvSource(value = { "1;90;-25;4","1;90;-25;-1","1;90;181;14","1;90;-25;0","1;90;-25;12"}, delimiter = ';')
	public void addDeliveryRequestTest1(long id, double latitude, double longitude, int timeWindow) {
		// Initialization
		cityMap = new CityMap(new HashMap<Long, Intersection>(), new ArrayList<RoadSegment>());
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());

		
		// Creating a delivery request for testing
		DeliveryRequest rq = new DeliveryRequest(id, latitude, longitude, timeWindow);

		try {
			// Use of the method being tested
			cityMap.addDeliveryRequest(id, latitude, longitude, timeWindow);
		} catch (Exception e) {
			// If an exception is caught during the test, fail the test
			fail("Unexpected exception :"+e.getMessage());
		}

		// Verifying the value of the added delivery request
		assertEquals("The delivery request isn't added correctly",cityMap.getListDeliveryRequests().get(0).toString(), rq.toString());
	}
	
	/***
	 * Test the second CityMap's method addDeliveryRequest with :
	 * <ol>
	 * 		<li>Corrects values</li>
	 * 		<li>Incorrect time window</li>
	 * 		<li>On bounds</li>
	 * 	</ol>
	 * 
	 * @param index       The index at which the delivery request should be added.
	 * @param id          The id of the delivery request.
	 * @param latitude    The latitude of the delivery request location.
	 * @param longitude   The longitude of the delivery request location.
	 * @param timeWindow  The time window for the delivery request.
	 * 
	 * @see model.DeliveryRequest
	 * @see model.CityMap#addDeliveryRequest(int,Long, double, double, int)
	 */
	@ParameterizedTest
	@CsvSource(value = { "0;1;90;-25;4", "0;1;90;-25;-1","0;1;90;181;14","0;1;90;-25;0","0;1;90;-25;12" }, delimiter = ';')
	public void addDeliveryRequestTest2(int index,long id, double latitude, double longitude,int timeWindow) {
		
		//Initialization
		cityMap = new CityMap(new HashMap<Long, Intersection>(), new ArrayList<RoadSegment>());
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());

		
		// Creating a delivery request for testing
		DeliveryRequest rq = new DeliveryRequest(id, latitude, longitude, timeWindow);
		
		try {
			//Use of the tested method
			cityMap.addDeliveryRequest(index,id, latitude, longitude, timeWindow);
		} catch (Exception e) {
			// If an exception is caught during the test, fail the test
			fail("Unexpected exception :"+e.getMessage());
		}
		// Verifying the value of the added delivery request
		assertEquals("The delivery request isn't added",rq.toString(),cityMap.getListDeliveryRequests().get(index).toString());
	}

	/***
	 * Test the CityMap's third method addIntersection with :
	 * 
	 * <ol>
	 * 		<li>Corrects values</li>
	 * 		<li>Incorrect timewindow</li>
	 * 		<li>On bounds</li>
	 * </ol>
	 * 
	 * @param index       The index at which the delivery request should be added.
	 * @param id          The ID of the delivery request.
	 * @param latitude    The latitude of the delivery request location.
	 * @param longitude   The longitude of the delivery request location.
	 * @param timeWindow  The time window for the delivery request.
	 * 
	 * @see model.DeliveryRequest
	 * @see model.CityMap#addDeliveryRequest(int, Long, double, double, int, LocalTime, LocalTime)

	 */
	@ParameterizedTest
	@CsvSource(value = { "0;1;90;-25;4", "0;1;90;-25;-1","0;1;90;181;14","0;1;90;-25;0","0;1;90;-25;12" }, delimiter = ';')
	public void addDeliveryRequestTest3(int index, long id, double latitude, double longitude, int timeWindow) {
		// Initialization
		cityMap = new CityMap(new HashMap<Long, Intersection>(), new ArrayList<RoadSegment>());
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Creating a delivery request for testing
		DeliveryRequest rq = new DeliveryRequest(id, latitude, longitude, timeWindow);

		try {
			// Use of the method being tested with additional delivery time window parameters
			cityMap.addDeliveryRequest(index, id, latitude, longitude, timeWindow, LocalTime.of(1,0), LocalTime.of(2,0));
		} catch (Exception e) {
			// If an exception is caught during the test, fail the test
			fail("Unexpected exception :"+e.getMessage());
		}

		// Verifying the value of the added delivery request
		assertEquals("The delivery request isn't added correctly",rq.toString(),cityMap.getListDeliveryRequests().get(index).toString());
	}

	/***
	 * Test on the selection of an intersection
	 * 
	 * @see model.Intersection
	 */
	@Test
	public void selectIntersectionTest() {
		
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Creating intersections for testing
		Intersection a = new Intersection(1L, 0.0, 0.0);
		Intersection b = new Intersection(2L, 90., 180.);
	
		try {
			//Add intersections to the city map
			cityMap.addIntersection(a);
			cityMap.addIntersection(b);
		} catch (Exception e) {
			// If an exception is caught during the test, fail the test
			fail("Unexpected exception :"+e.getMessage());
		}

		//Use of the method being tested
		cityMap.getAtPoint(new Point(0,0), new Point(0,0), 1,1, 1);

		// Test on the value of the selected intersection
		assertEquals("The first intersection is not selecetd",a.toString(),cityMap.getSelectedIntersection().toString());
		
				
	}

	
	/**
	 * Test the CityMap's method selectWareHouse
	 * 
	 * @see model.Warehouse
	 * @see model.CityMap#selectWarehouse(Warehouse)
	 */
	@Test
	public void selectWarehouseTest(){
		
		// Initialization
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Creating a warehouse for testing
		Warehouse warehouse = new Warehouse(1L,90.,-25.);
		
		try {
			//Add the warehouse to the city map
			cityMap.addWarehouse(warehouse.getIdIntersection(), warehouse.getLatitude(), warehouse.getLongitude());

		} catch (Exception e) {
			//Fail the test if an exception is caught
			fail("Unexpected exception :"+e.getMessage());
		}

		//Use of the tested method
		cityMap.selectWarehouse(warehouse);
				
		//Testing the selected intersection 
		assertEquals("The road segment is not selected",Long.valueOf(1L),cityMap.getSelectedIntersection().getIdIntersection());
	}

	/***
	 * Test on the selection of a road segment
	 * 
	 * @see model.Intersection
	 * @see model.RoadSegment
	 */
	@Test
	public void selectRoadSegmentTest() {
		
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Creating intersections and road segment for testing
		Intersection a = new Intersection(1L, 0.0, 0.0);
		Intersection b = new Intersection(2L, 50., 50.);
		RoadSegment ra = new RoadSegment("Avenue Albert Einstein", a, b, 100);

		try {
			//Add intersections to the city map
			cityMap.addIntersection(a);
			cityMap.addIntersection(b);
			cityMap.addRoadSegment("Avenue Albert Einstein", a, b, 100);
			cityMap.addRoadSegment("Avenue Hubert Reeves", b, a, 50);
		} catch (Exception e) {
			// If an exception is caught during the test, fail the test
			fail("Unexpected exception :"+e.getMessage());
		}

		//Use of the method being tested
		cityMap.getAtPoint(new Point(50,50), new Point(0,0), 1,1, 10);

		System.out.println(cityMap.getSelectedRoadSegment());
		// Test on the value of the selected intersection
		assertEquals("The road segment is not selected",ra.toString(), cityMap.getSelectedRoadSegment().toString());	}

	/**
	 * Test the CityMap's method selectDeliveryRequest
	 * 
	 * @see model.DeliveryRequest
	 * @see model.CityMap#selectDeliveryRequest(DeliveryRequest)
	 */
	@Test
	public void selectDeliveryRequestTest() {
		// Initialization
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Creating a delivery request for testing
		DeliveryRequest rq= new DeliveryRequest(1L,90.,-25.,1);
		
		try {
			// Adding a delivery request to the city map
			cityMap.addDeliveryRequest(rq.getIdIntersection(), rq.getLatitude(), rq.getLongitude(), rq.getTimeWindow());
		} catch (Exception e) {
			//Fail the test if an exception is caught
			fail("Unexpected exception :"+e.getMessage());
		}

		//Use of the tested method
		cityMap.selectDeliveryRequest(rq);

		//Testing the selected intersection 
		assertEquals("The road segment is not selected",Long.valueOf(1L),cityMap.getSelectedIntersection().getIdIntersection());

	}

	/**
	 * Test the CityMap's method resetBound
	 * 
	 * @see model.Intersection
	 * @see model.CityMap#resetBounds()
	 */
	@Test
	public void resetBoundTest() {
			
		//Initialization
		cityMap = new CityMap(new HashMap<Long, Intersection>(), new ArrayList<RoadSegment>());
		// Creating intersections for testing
		Intersection a = new Intersection(1L,1.,50.);
		Intersection b = new Intersection(2L,-1.,-25.);
		Intersection c = new Intersection(3L,-90.,-25.);
		Intersection d = new Intersection(4L,-90.,-50.);
		Intersection e = new Intersection(5L,-1.,-50.);
		Intersection f = new Intersection(6L,-1.,-60.);
		Intersection g = new Intersection(7L,-90.,25.);
		Intersection h = new Intersection(8L,-1.,25.);
		Intersection i = new Intersection(10L,-15.,-25.);
		Intersection j = new Intersection(11L,-33.,-25.);
		Intersection k = new Intersection(12L,-1.,-70.);
		Intersection l = new Intersection(13L,20.,-25.);
		Intersection m = new Intersection(14L,50.,-25.);
		
		try {
			//Add intersections to the city map
			cityMap.addIntersection(a);
			cityMap.addIntersection(b);
			cityMap.addIntersection(c);
			cityMap.addIntersection(d);
			cityMap.addIntersection(e);
			cityMap.addIntersection(f);
			cityMap.addIntersection(g);
			cityMap.addIntersection(h);
			cityMap.addIntersection(i);
			cityMap.addIntersection(j);
			cityMap.addIntersection(k);
			cityMap.addIntersection(l);
			cityMap.addIntersection(m);
		} catch (Exception exception) {

			//Fail the test if an exception is caught
			fail("Unexpected exception :"+exception.getMessage());
		}

		//Use of the method being tested
		cityMap.resetBounds();
		double[] latitude = {a.getLatitude(), b.getLatitude(), c.getLatitude(),d.getLatitude(),e.getLatitude(),f.getLatitude(),g.getLatitude(),h.getLatitude(),i.getLatitude(),j.getLatitude(),k.getLatitude(),l.getLatitude(),m.getLatitude()};
		double[] longitude = {a.getLongitude(), b.getLongitude(), c.getLongitude(),d.getLongitude(),e.getLongitude(),f.getLongitude(),g.getLongitude(),h.getLongitude(),i.getLongitude(),j.getLongitude(),k.getLongitude(),l.getLongitude(),m.getLongitude()};
		double minLat = minTab(latitude);
		double maxLat = maxTab(latitude);
		double minLong = minTab(longitude);
		double maxLong = maxTab(longitude);
		//Case with no exception
		if (cityMap.getListIntersections().size() > 0) {
			
			//Test on values
			assertEquals("The new latitude min is wrong",minLat,cityMap.getLatitudeMin(),0);
			assertEquals("The new longitude min is wrong",minLong,cityMap.getLongitudeMin(),0);
			assertEquals("The new latitude max is wrong",Math.max(0,maxLat),cityMap.getLatitudeMax(),0);
			assertEquals("The new latitude max is wrong",Math.max(0,maxLong),cityMap.getLatitudeMax(),0);
		}
	}

	/**
	 * Test if the resetListDeliveryRequests method correctly resets the list of delivery requests in a CityMap instance.
	 * 
	 * @see model.Intersection
	 * @see model.CityMap#resetListDeliveryRequests()
	 */
	@Test
	public void resetDeliveryRequestsTest() {
		
		//Initialization
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Create intersections for testing
		Intersection a = new Intersection(1L, -25.0, 50.0);
		Intersection b = new Intersection(2L, 50.0, -25.0);

		try {
			// Add intersections, road segment, and delivery requests to the city map
			cityMap.addIntersection(a);
			cityMap.addIntersection(b);
			cityMap.addRoadSegment("Avenue Albert Einstein", a, b, 100);
			cityMap.addDeliveryRequest(1L, 0, 0, 1);
			cityMap.addDeliveryRequest(2L, 50, -50, 2);
			cityMap.addDeliveryRequest(3L, 50, -50, 3);
		} catch (Exception e) {
			// Fail the test if an exception is caught
			assert(false);
		}

		if (cityMap.getListIntersections().size() > 0) {
			// Use of the tested method
			cityMap.resetListDeliveryRequests();

			// Testing the size of the delivery requests list
			assertEquals("The delivery request list isn't reset",0,cityMap.getListDeliveryRequests().size());
		}
	}
	
	/**
	 * Test if the resetListDeliveryRequests method correctly resets the list of delivery requests in a CityMap instance.
	 * 
	 * @see model.Intersection
	 * @see model.CityMap#resetListDeliveryRequests()
	 */
	@Test
	public void removeDeliveryRequestsTest() {
		
		//Initialization
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Create intersections for testing
		Intersection a = new Intersection(1L, -25.0, 50.0);
		Intersection b = new Intersection(2L, 50.0, -25.0);

		try {
			// Add intersections, road segment, and a delivery request to the city map
			cityMap.addIntersection(a);
			cityMap.addIntersection(b);
			cityMap.addRoadSegment("Avenue Albert Einstein", a, b, 100);
			cityMap.addDeliveryRequest(1L, 0, 0, 1);
			cityMap.addDeliveryRequest(2L, 50, -50, 2);
			cityMap.addDeliveryRequest(3L, 50, -50, 3);
		} catch (Exception e) {
			// Fail the test if an exception is caught
			fail("Unexpected exception "+e.getMessage());
		}

		if (cityMap.getListDeliveryRequests().get(0)!=null) {
			// Use of the tested method
			cityMap.removeDeliveryRequest(0);

			// Testing the size of the delivery requests list
			assertNotEquals("The delivery request list isn't removed correctly",new DeliveryRequest(1L, 0., 0., 1),cityMap.getListDeliveryRequests().get(0));
		}
	}
	@Test
	public void getAtPointTest() {
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		Intersection a = new Intersection(1L, 0.0, 0.0);
		Intersection b = new Intersection(2L, 90., 180.);
		try {
			// Add intersections, road segment, and a delivery request to the city map
			cityMap.addIntersection(a);
			cityMap.addIntersection(b);
			cityMap.addRoadSegment("Avenue Albert Einstein", a, b, 100);
			cityMap.addDeliveryRequest(1L, 0, 0, 1);
		}catch(Exception e){
			fail("Unexpected exception :"+ e.getMessage());
		}
		
		cityMap.getAtPoint(new Point(0,0), new Point(0,0), 1,1, 1);
		assertEquals("The first intersection is not selecetd",a.toString(),cityMap.getSelectedIntersection().toString());
	}
	
	/**
	 * Test the CityMap's method reset
	 * 
	 * @see model.Intersection
	 * @see model.CityMap#reset()
	 */
	@Test
	public void resetTest() {
		
		//Initialization
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Creating intersections for testing
		Intersection a = new Intersection(1L,-25.,50.);
		Intersection b = new Intersection(2L,50.,-25.);

		try {
			//Add intersections to the city map
			cityMap.addIntersection(a);
			cityMap.addIntersection(b);

			//Add the road segment to the city map
			cityMap.addRoadSegment("Avenue Albert Einstein", a, b, 100);

			//Add the delivery request to the city map
			cityMap.addDeliveryRequest(a.getIdIntersection(),a.getLatitude(),a.getLongitude(),1);

		} catch (Exception e) {
			
			//Fail the test if an exception is caught
			fail("Unexpected exception :"+e.getMessage());

		}

		if (cityMap.getListIntersections().size() > 0) {
			
			//Use of the method being tested
			cityMap.reset();
			
			//Testing size of all CityMap instance's lists
			assertEquals ("The list of intersection isn't reset",0,cityMap.getListIntersections().size());
			assertEquals("The list of road segment isn't reset",0,cityMap.getListRoadSegments().size());
			assertEquals("The delivery request list isn't reset",0,cityMap.getListDeliveryRequests().size());
		}
	}

	@Test
	public void findCorrespondingRoadSegmentTest() {
		
		//Initialization
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Creating intersections for testing
		Intersection a = new Intersection(1L,-25.,50.);
		Intersection b = new Intersection(2L,50.,-25.);
		RoadSegment rs = new RoadSegment("Avenue Albert Einstein", a ,b ,100);
		try {
			//Add intersections to the city map
			cityMap.addIntersection(a);
			cityMap.addIntersection(b);

			//Add the road segment to the city map
			cityMap.addRoadSegment("Avenue Albert Einstein", a, b, 100);

			//Add the delivery request to the city map
			cityMap.addDeliveryRequest(a.getIdIntersection(),a.getLatitude(),a.getLongitude(),1);

		} catch (Exception e) {
			
			//Fail the test if an exception is caught
			fail("Unexpected exception :"+e.getMessage());

		}

		assertNull ("The road segment exist",cityMap.findCorrespondingRoadSegment(a, a));
		assertEquals ("The road segment don't exist",rs.toString(),cityMap.findCorrespondingRoadSegment(a, b).toString());
		assertEquals ("The road segment don't exist",rs.toString(),cityMap.findCorrespondingRoadSegment(b, a).toString());
	}

	/**
	 * Test if the toString method produces the expected string representation of a CityMap instance.
	 * 
	 * @see model.Intersection
	 * @see model.CityMap#toString()
	 */
	@Test
	public void toStringTest() {
		// Initialization
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		// Create intersections for testing
		Intersection a = new Intersection(1L, -25.0, 50.0);
		Intersection b = new Intersection(2L, 50.0, -25.0);

		try {
			// Add intersections, road segment, and delivery request to the city map
			cityMap.addIntersection(a);
			cityMap.addIntersection(b);
			cityMap.addRoadSegment("Avenue Albert Einstein", a, b, 100);
			cityMap.addDeliveryRequest(a.getIdIntersection(), a.getLatitude(), a.getLongitude(), 1);
		} catch (Exception e) {
			// Fail the test if an exception is caught
			fail("Unexpected exception :"+e.getMessage());
		}

		if (cityMap.getListIntersections().size() > 0) {
			// Testing the string returned by the method
			assertEquals("CityMap [listIntersections={1=Intersection [idIntersection=1, latitude=-25.0, longitude=50.0], 2=Intersection [idIntersection=2, latitude=50.0, longitude=-25.0]}, \n"
					+ "listRoadSegments=[RoadSegment [streetName=Avenue Albert Einstein, startingIntersection=Intersection [idIntersection=1, latitude=-25.0, longitude=50.0], endingIntersection=Intersection [idIntersection=2, latitude=50.0, longitude=-25.0], length=100.0]], \n"
					+ "listDeliveryRequests[DeliveryRequest [idIntersection=1, latitude=-25.0, longitude=50.0, departureTime=null, arrivalTime=null, timeWindow=1, deliveringTime=300 s, waitingTime=null]]]",
					cityMap.toString());
		}
	}

}