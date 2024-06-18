package tests;

import parser.XMLserializer;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;

import model.DeliveryRequest;
import model.Intersection;
import model.Warehouse;

/***
 * @author H4114
 * @see parser.XMLserializer
 */
public class XMLserializerTest {
	
	//Fields
	XMLserializer serializer;
	
	/**
	 * Set up method to initialize the XMLserializer for testing.
	 * It creates an instance of XMLserializer and catches any exceptions that might occur during the initialization.
	 * If an exception occurs, the test is marked as failed.
	 */
	@Before
	public void setUp() {
		serializer = new XMLserializer();
	}
	
	/**
	 * Test method for saving a list of intersections (including warehouses and delivery requests) to an XML file.
	 * The generated XML file is then read to verify its content.
	 * 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws FileNotFoundException 
	 * 
	 * @see model.Warehouse
	 * @see model.DeliveryRequest
	 */
	@Test
	public void saveDeliveryRequestTest() throws TransformerException, ParserConfigurationException, FileNotFoundException {
		// Initialize variables for file handling
		File file = null;
		Scanner sc = null;
		String str = "";

		// Create a list of intersections (including a warehouse and delivery requests)
		List<Intersection> list = new ArrayList<Intersection>();
		list.add(new Warehouse(0L, 0.0, 0.0));
		list.add(new DeliveryRequest(1L, 1.1, 1.1, 3,LocalTime.of(3,0),LocalTime.of(3,5)));
		list.add(new DeliveryRequest(2L, 2.2, 2.2, 4,LocalTime.of(4,0),LocalTime.of(4,5)));
			
		// Save the list of intersections to an XML file
		serializer.saveDeliveryRequestsToFile("src/tests/serializers_test.xml", list);

		// Read the generated XML file
		file = new File("src/tests/serializers/serializer_test.xml");
		sc = new Scanner(file);

		// Concatenate the lines from the file to compare with the expected XML content
		while (sc.hasNextLine()) {
			str += sc.nextLine();
		}
		sc.close();
		
		// Assert that the content of the generated XML file matches the expected result
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><deliveryRequests><warehouse idIntersection=\"0\"/><deliveryRequest idIntersection=\"1\" timeWindow=\"3\"/><deliveryRequest idIntersection=\"2\" timeWindow=\"4\"/></deliveryRequests>", str);
	}

}
