package tests;

import parser.ExceptionHTML;
import parser.ExceptionXML;
import parser.HTMLdeserializer;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.awt.Rectangle;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;

import model.CityMap;
import model.DeliveryRequest;
import model.Intersection;
import model.RoadSegment;
import model.Tour;

/***
 * @author H4114
 * @see parser.XMLdeserializer
 */
public class HTMLdeserializerTest {
	
	//Fields
	CityMap cityMap;
	HTMLdeserializer deserializer;
	DeliveryRequest deliveryRequest;
	String exception;
	Tour tour;
	String path;
	
	/**
	 * Set up the test environment before each test case.
	 * Initializes the CityMap and XMLdeserializer objects.
	 * 
	 * @throws ExceptionXML if an unexpected XML-related exception occurs during XMLdeserializer initialization.
	 */
	@Before
	public void setUp() throws ExceptionXML {
		cityMap = new CityMap();
		tour = new Tour();
		deserializer = new HTMLdeserializer();
		path = "src/tests/serializers/htmlserializer_test.html";
	}
	
	/**
	 * Test to ensure that attempting to load a non-map XML file results in the expected exception.
	 *
	 * @param path The file path of the XML file to be tested.
	 * @throws ExceptionHTML 
	 * @throws IOException 
	 */
	@Test
	public void createRoadMapNotCompleteTourTest() throws IOException{
		// Initialize a map, a controller, and a panel
		cityMap = new CityMap();
		Controller controller = new Controller();
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(new Rectangle(new Dimension(200,200)));

		try {
			//Us eof the method being tested
			deserializer.createRoadMap(path, controller.getListTour(),leftPanel);
		} catch (ExceptionHTML e) {
			//Catching the expected exception
			assertEquals("The tour is not complete",e.getMessage());
		}
	}
	
	@Test
	public void createRoadMapCompleteTourTest() throws IOException, ExceptionHTML{
		// Initialize the tour
		cityMap = new CityMap(new HashMap<Long, Intersection>(),new ArrayList<RoadSegment>());
		JPanel leftPanel = new JPanel();
		Controller controller = new Controller();
		
		leftPanel.setBounds(new Rectangle(new Dimension(200,200)));
		cityMap.setListDeliveryRequests(new ArrayList<Intersection>());
		
		controller.launch();
		controller.loadCityMap("src/tests/compute/a_map_compute.xml");
		controller.loadDeliveryRequests("src/tests/compute/deliveriesrequestscompute.xml");
		controller.manageCouriers();
		controller.createCourier("Nicolas");
		cityMap = controller.getCityMap();
		controller.initializeTour();
	
		//Add delivery requests to the tour
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
		
		//Compute a tour
		controller.computeBestTour();

		//Use of the method being tested
		deserializer.createRoadMap(path, controller.getListTour(),leftPanel);
		
		// Read the generated HTML file
		File file = new File(path);
		Scanner sc = new Scanner(file);

		String str="";
		// Concatenate the lines from the file to compare with the expected HTML content
		while (sc.hasNextLine()) {
			str += sc.nextLine();
		}
		sc.close();
		
		assertEquals("<!DOCTYPE html><style>h1{text-align: center;font-family: \"Oswald\";}h2{font-family: \"Oswald\";}body{margin-left: 10%;margin-right: 10%;font-family: \"Open Sans\";}ul{list-style-type: circle;}</style><head><title>RoadMap</title></head><body><h1>Peter's roadmap</h1><h2>Information</h2><p>This tour of <b>8333</b> meters contains <b>4</b> deliveries, Starting at <b>08:00</b> Finishing at <b>10:35</b></p><h2>Instructions</h2><p id=\"warehouse\">08:00 : leave the Warehouse</p><br><ul id=\"segments\"><li>take Avenue Lacassagne (119 m)</li><li>continue straight on Avenue Lacassagne (50 m)</li><li>continue straight on Avenue Lacassagne (113 m)</li><li>continue straight on Avenue Lacassagne (88 m)</li><li>continue straight on Avenue Lacassagne (47 m)</li><li>continue straight on Avenue Lacassagne (96 m)</li><li>continue straight on Avenue Lacassagne (66 m)</li><li>continue straight on Avenue Lacassagne (175 m)</li><li>continue straight on Avenue Lacassagne (59 m)</li><li>continue straight on Avenue Lacassagne (74 m)</li><li>continue straight on Avenue Lacassagne (77 m)</li><li>continue straight on Avenue Lacassagne (177 m)</li><li>continue straight on Avenue Lacassagne (164 m)</li><li>continue straight on Rue Paul Bert (67 m)</li><li>turn left on Rue Paul Bert (225 m)</li><li>turn right on Boulevard Vivier-Merle (72 m)</li><li>turn left on Boulevard Vivier-Merle (123 m)</li><li>turn right on Boulevard Vivier-Merle (343 m)</li><li>continue straight on Boulevard Vivier-Merle (343 m)</li><li>continue straight on Avenue Georges Pompidou (27 m)</li><li>turn left on Avenue Georges Pompidou (236 m)</li><li>turn left on Avenue Georges Pompidou (64 m)</li><li>turn left on Avenue Georges Pompidou (33 m)</li><li>continue straight on Rue Maurice Flandin (252 m)</li><li>continue straight on Rue d'Aubigny (71 m)</li><li>turn left on Rue d'Aubigny (61 m)</li><li>continue straight on Rue d'Aubigny (63 m)</li><li>turn left on Rue GandoliÃ¨re (134 m)</li><li>continue straight on Rue des Petites SÅ“urs (58 m)</li><li>continue straight on Rue des Petites SÅ“urs (138 m)</li><li>continue straight on Rue Baraban (54 m)</li><li>continue straight on Rue Baraban (30 m)</li><li>continue straight on Rue Sainte-Anne de Baraban (139 m)</li><li>continue straight on Rue Sainte-Anne de Baraban (67 m)</li><li>continue straight on Rue Sainte-Anne de Baraban (43 m)</li><li>continue straight on Rue Sainte-Anne de Baraban (130 m)</li><li>continue straight on Rue Sainte-Anne de Baraban (74 m)</li><li>continue straight on Rue Sainte-Anne de Baraban (113 m)</li><li>continue straight on Rue Sainte-Anne de Baraban (126 m)</li><li>continue straight on Rue FrÃ©dÃ©ric Mistral (91 m)</li><li>continue straight on Rue FrÃ©dÃ©ric Mistral (116 m)</li><li>continue straight on Rue FrÃ©dÃ©ric Mistral (11 m)</li><li>continue straight on Place des Maisons Neuves (12 m)</li><li>turn left on Place des Maisons Neuves (31 m)</li><li>turn left on Rue Paul Pechoux (94 m)</li><li>continue straight on Route de Genas (22 m)</li><li>continue straight on Route de Genas (83 m)</li><li>continue straight on Route de Genas (83 m)</li><li>continue straight on Route de Genas (22 m)</li><li>turn right on Rue Paul Pechoux (94 m)</li><li>turn right on Place des Maisons Neuves (31 m)</li><li>continue straight on Avenue FÃ©lix Faure (52 m)</li><li>continue straight on Avenue FÃ©lix Faure (227 m)</li><li>continue straight on Avenue FÃ©lix Faure (82 m)</li><li>turn right on Avenue FÃ©lix Faure (70 m)</li><li>continue straight on Avenue FÃ©lix Faure (255 m)</li><li>turn right on Avenue FÃ©lix Faure (50 m)</li><li>continue straight on Avenue FÃ©lix Faure (26 m)</li><li>continue straight on Avenue FÃ©lix Faure (214 m)</li><li>turn left on Avenue FÃ©lix Faure (48 m)</li><li>turn left on Avenue FÃ©lix Faure (127 m)</li><li>continue straight on Avenue FÃ©lix Faure (112 m)</li><li>continue straight on Avenue FÃ©lix Faure (62 m)</li><li>turn right on Avenue FÃ©lix Faure (149 m)</li><li>turn right on Rue Professeur RenÃ© Guillet (87 m)</li><li>continue straight on Rue Professeur RenÃ© Guillet (87 m)</li><li>turn left on Avenue FÃ©lix Faure (149 m)</li><li>continue straight on Avenue FÃ©lix Faure (62 m)</li><li>continue straight on Avenue FÃ©lix Faure (112 m)</li><li>turn left on Avenue FÃ©lix Faure (127 m)</li><li>turn right on Avenue FÃ©lix Faure (48 m)</li><li>continue straight on Avenue FÃ©lix Faure (214 m)</li><li>continue straight on Avenue Lacassagne (74 m)</li><li>turn right on Avenue Lacassagne (59 m)</li><li>turn right on Avenue Lacassagne (175 m)</li><li>turn left on Avenue Lacassagne (66 m)</li><li>turn right on Avenue Lacassagne (96 m)</li><li>turn left on Avenue Lacassagne (47 m)</li><li>turn left on Avenue Lacassagne (88 m)</li><li>turn right on Avenue Lacassagne (113 m)</li><li>turn right on Avenue Lacassagne (50 m)</li><li>turn left on Avenue Lacassagne (119 m)</li></ul> <br><p id=\"warehouse\">10:35 : return to the Warehouse</p></body></html>",str);
	}

}
