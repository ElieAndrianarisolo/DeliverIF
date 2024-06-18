package tests;

import parser.ExceptionXML;
import parser.XMLdeserializer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import model.CityMap;
import model.DeliveryRequest;

/***
 * @author H4114
 * @see parser.XMLdeserializer
 */
public class XMLdeserializerTest {
	
	//Fields
	CityMap cityMap;
	XMLdeserializer deserializer;
	DeliveryRequest deliveryRequest;
	String exception;
	
	/**
	 * Set up the test environment before each test case.
	 * Initializes the CityMap and XMLdeserializer objects.
	 * 
	 * @throws ExceptionXML if an unexpected XML-related exception occurs during XMLdeserializer initialization.
	 */
	@Before
	public void setUp() throws ExceptionXML {
		cityMap = new CityMap();

		deserializer = new XMLdeserializer();
	}
	
	/**
	 * Test to ensure that attempting to load a non-map XML file results in the expected exception.
	 *
	 * @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings = {"src/tests/citiesmap/notmap/not_a_map.xml", "src/tests/a_map.xml"})
	public void FileNotMapTest(String path) {
		// Initialize CityMap and XMLdeserializer
		cityMap = new CityMap();

		try {
			// Attempt to initialize the XMLdeserializer and load the CityMap from the specified file path
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap, path);
		} catch (Exception e) {
			// Verify that the expected exception is thrown
			assertEquals(new ExceptionXML("The file is not a city map file").toString(), e.toString());
		}
	}


	/**
	 * Test to ensure that attempting to load an empty map XML file results in the expected exception.
	 *
	 * @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings = {"src/tests/citiesmap/emptymap/empty_map.xml", "src/tests/a_map.xml"})
	public void emptyMapTest(String path) {
		// Initialize CityMap and XMLdeserializer
		cityMap = new CityMap();

		try {
			// Attempt to initialize the XMLdeserializer and load the CityMap from the specified file path
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap, path);
		} catch (Exception e) {
			// Verify that the expected exception is thrown
			assertEquals(new ExceptionXML("The file is empty").toString(), e.toString());
		}
	}

	
	/**
	 * Test to ensure that attempting to load a map with incorrect attributes for an intersection results in the expected exception.
	 *
	 * @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings = {"src/tests/citiesmap/incorrectattributes/incorrectattributes1.xml", "src/tests/citiesmap/incorrectattributes/incorrectattributes2.xml","src/tests/citiesmap/incorrectattributes/incorrectattributes3.xml", "src/tests/citiesmap/incorrectattributes/incorrectattributes4.xml","src/tests/a_map.xml"})
	public void wrongAttributesIntersectionTest(String path) {
		// Initialize CityMap and XMLdeserializer
		cityMap = new CityMap();

		try {
			// Attempt to initialize the XMLdeserializer and load the CityMap from the specified file path
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap, path);
		} catch (Exception e) {
			// Verify that the expected exception is thrown
			assertEquals(new ExceptionXML("Error when reading file: the attributes of the intersection n°5 are incorrect").toString(), e.toString());
		}
	}

	/***
	 * Test to ensure that attempting to load a map with incorrect id for an intersection results in the expected exception.
	 * 
	 * @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings= {"src/tests/citiesmap/incorrectid/incorrectid1.xml","src/tests/citiesmap/incorrectid/incorrectid2.xml","src/tests/citiesmap/incorrectid/incorrectid3.xml","src/tests/a_map.xml"})
	public void wrongIDTest(String path){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,path);
		} catch (Exception e) {
			assertEquals(new ExceptionXML("Error when reading file: idIntersection must be positive").toString(),e.toString());
		}
	}

	/***
	 * Test to ensure that attempting to load a map with incorrect latitude for an intersection results in the expected exception.
	 * 
	 * @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings= {"src/tests/citiesmap/incorrectlatitude/incorrectlatitude1.xml","src/tests/citiesmap/incorrectlatitude/incorrectlatitude2.xml","src/tests/citiesmap/incorrectlatitude/incorrectlatitude3.xml","src/tests/citiesmap/incorrectlatitude/incorrectlatitude4.xml","src/tests/citiesmap/incorrectlatitude/incorrectlatitude5.xml"})
	public void wrongLatitudeTest(String path){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,path);
		} catch (Exception e) {
			// TODO: handle exception
			assertEquals(new ExceptionXML("Error when reading file: latitude must be between -90° and 90°").toString(),e.toString());
		}
	}

	/***
	 * Test to ensure that attempting to load a map with incorrect longitude for an intersection results in the expected exception.
	 * 
	 * @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings= {"src/tests/citiesmap/incorrectlongitude/incorrectlongitude1.xml","src/tests/citiesmap/incorrectlongitude/incorrectlongitude2.xml","src/tests/citiesmap/incorrectlongitude/incorrectlongitude3.xml","src/tests/citiesmap/incorrectlongitude/incorrectlongitude4.xml","src/tests/citiesmap/incorrectlongitude/incorrectlongitude5.xml","src/tests/a_map.xml"})
	public void wrongLongitudeTest(String path){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,path);
		} catch (Exception e) {
			// TODO: handle exception
			assertEquals(new ExceptionXML("Error when reading file: longitude must be between -180° and 180°").toString(),e.toString());
		}
	}

	/***
	 * Test to ensure that attempting to load a map without intersections results in the expected exception.
	 * 
	 * @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings= {"src/tests/citiesmap/incorrectintersection/incorrectintersection.xml","src/tests/a_map.xml"})
	public void wrongNumberOfIntersectionTest(String path){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,path);
		} catch (Exception e) {
			// TODO: handle exception
			assertEquals(new ExceptionXML("Error when reading file: there must be at least 1 intersection").toString(),e.toString());
		}
	}
	
	/***
	 * Test to ensure that attempting to load a map with incorrect attributes for a road segment results in the expected exception.
	 * 
	 * @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings= {"src/tests/citiesmap/incorrectattributesroad/incorrectattributesroad1.xml","src/tests/citiesmap/incorrectattributesroad/incorrectattributesroad2.xml","src/tests/citiesmap/incorrectattributesroad/incorrectattributesroad3.xml","src/tests/citiesmap/incorrectattributesroad/incorrectattributesroad4.xml","src/tests/a_map.xml"})
	public void wrongAttributesSegmentRoadTest(String path){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,path);
		} catch (Exception e) {
			// TODO: handle exception
			assertEquals(new ExceptionXML("Error when reading file: the attributes of the road segment n°1 are incorrect").toString(),e.toString());
		}
	}

	/***
	 * Test to ensure that attempting to load a map with an incorrect starting intersection for a road segment results in the expected exception.
	 *
	 *  @param path The file path of the XML file to be tested.
	 *  @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings= {"src/tests/citiesmap/incorrectstart/incorrectstart.xml","src/tests/a_map.xml"})
	public void wrongOriginTest(String path){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,path);
		} catch (Exception e) {
			// TODO: handle exception
			assertEquals(new ExceptionXML("The starting intersection of the road segment n°1doesn't exist").toString(),e.toString());
		}
	}
	
	/***
	 * Test to ensure that attempting to load a map with an incorrect length for a road segment results in the expected exception.
	 * 
	 * @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings= {"src/tests/citiesmap/incorrectlength/incorrectlength1.xml","src/tests/citiesmap/incorrectlength/incorrectlength2.xml","src/tests/a_map.xml"})
	public void wrongLengthTest(String path){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,path);
		} catch (Exception e) {
			// TODO: handle exception
			assertEquals(new ExceptionXML("Error when reading file: a length must be a positive number").toString(),e.toString());
		}
	}
	
	/***
	 * Test to ensure that attempting to load a map with an incorrect destination for a road segment results in the expected exception.
	 *
	 *  @param path The file path of the XML file to be tested.
	 * @see parser.XMLdeserializer#loadCityMap(CityMap, String)
	 */
	@ParameterizedTest
	@ValueSource(strings= {"src/tests/citiesmap/incorrectdest/incorrectdest.xml","src/tests/a_map.xml"})
	public void wrongDestinationTest(String path){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,path);
		} catch (Exception e) {
			assertEquals(new ExceptionXML("The starting intersection of the road segment n°1doesn't exist").toString(),e.toString());
		}
	}
	
	/***
	 * Test if the map is correctly loaded
	 * 
	 * @throws ExceptionXML
	 */
	@Test
	public void modelTest() throws ExceptionXML{

		deserializer.loadCityMap(cityMap,"src/tests/a_map.xml");

		assertEquals("CityMap [listIntersections={1=Intersection [idIntersection=1, latitude=45.75406, longitude=4.857418], 2=Intersection [idIntersection=2, latitude=45.75406, longitude=4.857418], 3=Intersection [idIntersection=3, latitude=45.750404, longitude=4.8744674]}, \n"
				+ "listRoadSegments=[RoadSegment [streetName=Rue Danton, startingIntersection=Intersection [idIntersection=1, latitude=45.75406, longitude=4.857418], endingIntersection=Intersection [idIntersection=3, latitude=45.750404, longitude=4.8744674], length=69.979805]], \n"
				+ "listDeliveryRequests[]]",cityMap.toString());	
	}
	
	/**
	 * Test to ensure that attempting to load a file which is not a delivery request file results in the expecte exception
	 * 
	 * @param map The file path of the XML map file
	 * @param requests  The file path of the XML requests file
	 * @see parser.XMLdeserializer#loadDeliveryRequests(CityMap, String)
	 */
	@ParameterizedTest
	@CsvSource(value = {"src/tests/a_map.xml;src/tests/deliveriesrequests/notdeliveriesrequests/not_deliveries_requests.xml"}, delimiter = ';')
	public void NotDeliveryRequestsTest(String map,String requests){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,map);
			deserializer.loadDeliveryRequests(cityMap,requests);
		} catch (Exception e) {
			// TODO: handle exception
			assertEquals(new ExceptionXML("The file is not a delivery requests file").toString(),e.toString());
		}
	}
	
	/**
	 * Test to ensure that attempting to load a file without delivery requests results in the expected exception
	 * 
	 * @param map The file path of the XML map file
	 * @param requests  The file path of the XML requests file
	 * @see parser.XMLdeserializer#loadDeliveryRequests(CityMap, String)
	 */
	@ParameterizedTest
	@CsvSource(value = {"src/tests/a_map.xml;src/tests/deliveriesrequests/empty/empty.xml"}, delimiter = ';')
	public void EmptyTest(String map,String requests){
		cityMap = new CityMap();
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,map);
			deserializer.loadDeliveryRequests(cityMap,requests);
		} catch (Exception e) {
			// TODO: handle exception
			assertEquals(new ExceptionXML("The file is empty").toString(),e.toString());
		}
	}
	
	/**
	 * Test to ensure that attempting to load a file without a warehouse and delivery requests results in the expected exception
	 * 
	 * @param map The file path of the XML map file
	 * @param requests  The file path of the XML requests file
	 * @see parser.XMLdeserializer#loadDeliveryRequests(CityMap, String)
	 */
	@ParameterizedTest
	@CsvSource(value = {"src/tests/a_map.xml;src/tests/deliveriesrequests/wrongattributes/wrongid1.xml","src/tests/a_map.xml;src/tests/deliveriesrequests/wrongattributes/wrongid2.xml"}, delimiter = ';')
	public void WrongIdIntersectionTest(String map,String requests){
		cityMap = new CityMap();
		exception="";
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,map);
			deserializer.loadDeliveryRequests(cityMap,requests);
		} catch (Exception e) {
			// TODO: handle exception
			exception =e.toString();
			assertEquals(new ExceptionXML("The idIntersection is erroneous").toString(),e.toString());
		}
		if(!exception.equals("parser.ExceptionXML: The idIntersection is erroneous")) {
			System.out.println(requests);
			assert(false);
		}
		
	}
	
	/***
	 * Test to ensure that attempting to load delivery requests with incorrect attributes for a delivery request results in the expected exception.
	 *
	 * @param map The file path of the XML map file
	 * @param requests  The file path of the XML requests file
	 */
	@ParameterizedTest
	@CsvSource(value = {"src/tests/a_map.xml;src/tests/deliveriesrequests/wrongattributes/wrongtimewindow1.xml","src/tests/a_map.xml;src/tests/deliveriesrequests/wrongattributes/wrongtimewindow2.xml"}, delimiter = ';')
	public void wrongTimeWindowTest(String map,String requests){
		cityMap = new CityMap();
		String exception="";
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,map);
			deserializer.loadDeliveryRequests(cityMap,requests);
		} catch (Exception e) {
			// TODO: handle exception
			assertEquals(new ExceptionXML("The time window is erroneous").toString(),e.toString());
			System.out.println(requests);
			exception=e.toString();
		}

		if(!exception.equals("parser.ExceptionXML: The time window is erroneous")) {
			System.out.println(requests);
			assert(false);
		}
	}
	
	/***
	 * Test to ensure that attempting to load delivery requests with a wrong intersection for a delivery request results in the expected exception.
	 *
	 * @param map The file path of the XML map file
	 * @param requests  The file path of the XML requests file
	 */
	@ParameterizedTest
	@CsvSource(value = {"src/tests/a_map.xml;src/tests/deliveriesrequests/nointersection/no_intersection.xml"}, delimiter = ';')
	public void noIntersectionTest(String map,String requests){
		cityMap = new CityMap();
		exception="";
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,map);
			deserializer.loadDeliveryRequests(cityMap,requests);
		} catch (Exception e) {
			// TODO: handle exception
			exception = e.toString();
			assertEquals(new ExceptionXML("The idIntersection of the deliveryRequest n°2 doesn't exist").toString(),e.toString());
		}
	}

	/***
	 * Test to ensure that attempting to load delivery requests with no warehouse results in the expected exception.
	 *
	 * @param map The file path of the XML map file
	 * @param requests  The file path of the XML requests file
	 */
	@ParameterizedTest
	@CsvSource(value = {"src/tests/a_map.xml;src/tests/deliveriesrequests/nowarehouse/no_warehouse.xml"}, delimiter = ';')
	public void noWarehouseTest(String map,String requests){
		cityMap = new CityMap();
		exception="";
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,map);
			deserializer.loadDeliveryRequests(cityMap,requests);
		} catch (Exception e) {
			// TODO: handle exception
			exception = e.toString();
			assertEquals(new ExceptionXML("The warehouse doesn't exist").toString(),e.toString());
		}
	}

	/***
	 * Test to ensure that attempting to load delivery requests with more than one warehouse results in the expected exception.
	 *
	 * @param map The file path of the XML map file
	 * @param requests  The file path of the XML requests file
	 */
	@ParameterizedTest
	@CsvSource(value = {"src/tests/a_map.xml;src/tests/deliveriesrequests/warehouses/twowarehouse.xml"}, delimiter = ';')
	public void warehouses(String map,String requests){
		cityMap = new CityMap();
		exception ="";
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,map);
			deserializer.loadDeliveryRequests(cityMap,requests);
		} catch (Exception e) {
			// TODO: handle exception
			exception=e.toString();
			assertEquals(new ExceptionXML("The file has more than 1 warehouse").toString(),e.toString());
		}
	}

	/***
	 * Test to ensure that attempting to load delivery requests without delivery requests results in the expected exception.
	 *
	 * @param map The file path of the XML map file
	 * @param requests  The file path of the XML requests file
	 */
	@ParameterizedTest
	@CsvSource(value = {"src/tests/a_map.xml;src/tests/deliveriesrequests/nodeliveriesrequests/nodeliveriesrequests.xml"}, delimiter = ';')
	public void noDeliveriesRequests(String map,String requests){
		cityMap = new CityMap();
		exception ="";
		try {
			deserializer = new XMLdeserializer();
			deserializer.loadCityMap(cityMap,map);
			deserializer.loadDeliveryRequests(cityMap,requests);
		} catch (Exception e) {
			// TODO: handle exception
			exception=e.toString();
			assertEquals(new ExceptionXML("No delivery request available").toString(),e.toString());
		}
	}	
}
