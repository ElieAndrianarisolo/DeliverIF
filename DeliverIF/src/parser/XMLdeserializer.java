package parser;

import model.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class provides methods for deserializing XML data to create a city map.
 * @author H4114
 */
public class XMLdeserializer {
	
	final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;

	/**
	 * Constructor for XMLdeserializer class.
	 *
	 * @throws ExceptionXML If there's an error during XML deserialization.
	 */
	public XMLdeserializer() throws ExceptionXML {
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ExceptionXML(e.getMessage());
		}
		
	}

	/**
	 * Loads a city map from an XML file.
	 *
	 * @param cityMap 			The city map object to be populated.
	 * @param filePath 			The path to the XML file.
	 * @throws ExceptionXML 	If there's an error during XML deserialization.
	 */
	public void loadCityMap(CityMap cityMap, String filePath) throws ExceptionXML {

		Document document = null;
		
		try {
			document = builder.parse(new File(filePath));
		} catch (SAXException e) {
			throw new ExceptionXML(e.getMessage());
		} catch (IOException e) {
			throw new ExceptionXML(e.getMessage());
		}

		final Element root = document.getDocumentElement();

		if (!root.getNodeName().equals("map")) {
			throw new ExceptionXML("The file is not a city map file");
		}

		NodeList rootNodes = root.getChildNodes();
		int nbRootNodes = rootNodes.getLength();

		if (nbRootNodes == 0) {
			throw new ExceptionXML("The file is empty");
		}

		int nbNodes = 0;
		int nbRoadSegment = 0;

		for (int i = 0; i < nbRootNodes; i++) {
			
			if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				
				Element elt = (Element) rootNodes.item(i);

				Long idIntersection;
				double latitude, longitude;
				if (elt.getNodeName().equals("intersection")) {
					
					try {
						
						idIntersection = Long.parseLong(elt.getAttribute("id"), 10);
						latitude = Double.parseDouble(elt.getAttribute("latitude"));
						longitude = Double.parseDouble(elt.getAttribute("longitude"));
						
						
					} catch (Exception e) {
						
						throw new ExceptionXML("Error when reading file: the attributes of the intersection n°" + ++nbNodes + " are incorrect");
						
					}
					
					if (idIntersection <= 0L) {
			   			throw new ExceptionXML("Error when reading file: idIntersection must be positive");
			   		}
					
					if (latitude < -90 || latitude > 90) {
			   			throw new ExceptionXML("Error when reading file: latitude must be between -90° and 90°");
			   		}
					
					if (longitude < -180 || longitude > 180) {
			   			throw new ExceptionXML("Error when reading file: longitude must be between -180° and 180°");
			   		}
					
					++nbNodes;
					cityMap.addIntersection(new Intersection(idIntersection, latitude, longitude));
					
				}

				String streetName;
				Long idDeparture, idArrival;
				double length;
				Intersection origin, destination;
				if (elt.getNodeName().equals("segment")) {
					
					if (nbNodes == 0) {
						throw new ExceptionXML("Error when reading file: there must be at least 1 intersection");
					}

					try {
						
						streetName = elt.getAttribute("name");
						idDeparture = Long.parseLong(elt.getAttribute("origin"), 10);
						idArrival = Long.parseLong(elt.getAttribute("destination"), 10);
						length = Double.parseDouble(elt.getAttribute("length"));
						
						if (length < 0) {
							throw new ExceptionXML("Error when reading file: a length must be a positive number");
						}
						
					} catch (Exception e) {
						throw new ExceptionXML("Error when reading file: the attributes of the road segment n°" + ++nbRoadSegment + " are incorrect");
					}

					origin = cityMap.getListIntersections().get(idDeparture);
					if (origin == null) {
						throw new ExceptionXML("The starting intersection of the road segment n°" + ++nbRoadSegment + "doesn't exist");
					}

					destination = cityMap.getListIntersections().get(idArrival);
					if (destination == null) {
							throw new ExceptionXML("The ending intersection of the road segment n°" + ++nbRoadSegment + "doesn't exist");
					}

					++nbRoadSegment;
					cityMap.addRoadSegment(streetName, origin, destination, length);
					
				}
				
			}
			
		}
		
	}
	
	/**
	 * Loads delivery requests from an XML file.
	 *
	 * @param cityMap 		The city map object to be populated with delivery requests.
	 * @param filePath 		The path to the XML file containing delivery requests.
	 * @throws ExceptionXML If there's an error during XML deserialization.
	 */
	public void loadDeliveryRequests(CityMap cityMap, String filePath) throws ExceptionXML {
		
		List<Intersection> oldListDeliveryRequests =  new ArrayList<Intersection>();
		oldListDeliveryRequests.addAll(cityMap.getListDeliveryRequests());
		cityMap.resetListDeliveryRequests();
		
		Document document = null;
		
		try {
			document = builder.parse(new File(filePath));
		} catch (SAXException e) {
			throw new ExceptionXML(e.getMessage());
		} catch (IOException e) {
			throw new ExceptionXML(e.getMessage());
		}
	
		final Element root = document.getDocumentElement();
	
		if (!root.getNodeName().equals("deliveryRequests")) {
			
			throw new ExceptionXML("The file is not a delivery requests file");
			
		}
	
		NodeList rootNodes = root.getChildNodes();
		int nbRootNodes = rootNodes.getLength();
	
		if (nbRootNodes == 0) {
			
			throw new ExceptionXML("The file is empty");
			
		}
	
		int nbWarehouse = 0;
		int nbDeliveryRequest = 0;
		int timeWindow = 0;
		int sizeOldListDeliveryRequests = oldListDeliveryRequests.size();
		
		for (int i = 0; i < nbRootNodes; i++) {
			
			if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE){
				
				Element elt = (Element) rootNodes.item(i);
				
				Long idIntersection;
				Intersection deliveryLocation;
				LocalTime departureTime = null;
				LocalTime arrivalTime = null;
				int hour, minute, seconde;
				
				if (elt.getNodeName().equals("warehouse")) {
						
					try {
						
						idIntersection = Long.parseLong(elt.getAttribute("idIntersection"));
						
					} catch (Exception e) {
						
						if(sizeOldListDeliveryRequests == 0) {
							cityMap.resetListDeliveryRequests();
						} else {
							cityMap.setListDeliveryRequests(oldListDeliveryRequests);
						}
						
						throw new ExceptionXML("The idIntersection is erroneous");
						
					}
	
					++nbWarehouse;
					if (nbWarehouse > 1) {
						
						throw new ExceptionXML("The file has more than 1 warehouse");
					
					}
					
					deliveryLocation = cityMap.getListIntersections().get(idIntersection);
					
					if (deliveryLocation == null) {
						
						if(sizeOldListDeliveryRequests == 0) {
							cityMap.resetListDeliveryRequests();
						} else {
							cityMap.setListDeliveryRequests(oldListDeliveryRequests);
						}
						
						throw new ExceptionXML("The warehouse doesn't exist");
						
					}
					
					cityMap.addWarehouse(idIntersection, deliveryLocation.getLatitude(), deliveryLocation.getLongitude());
					
				}
				
				if (elt.getNodeName().equals("deliveryRequest")) {
	
					try {
						
						idIntersection = Long.parseLong(elt.getAttribute("idIntersection"));
						
					} catch (Exception e) {
						
						if(sizeOldListDeliveryRequests == 0) {
							cityMap.resetListDeliveryRequests();
						} else {
							cityMap.setListDeliveryRequests(oldListDeliveryRequests);
						}
						
						throw new ExceptionXML("The idIntersection of the deliveryRequest n°" + ++nbDeliveryRequest + " is erroneous");
						
					}
	
					deliveryLocation = cityMap.getListIntersections().get(idIntersection);
					if (deliveryLocation == null) {
						
						
						if(sizeOldListDeliveryRequests == 0) {
							cityMap.resetListDeliveryRequests();
						} else {
							cityMap.setListDeliveryRequests(oldListDeliveryRequests);
							System.out.println("JEEEU");
						}

						throw new ExceptionXML("The idIntersection of the deliveryRequest n°" + ++nbDeliveryRequest + " doesn't exist");
						
					}
					
					try {
						
						timeWindow = Integer.parseInt(elt.getAttribute("timeWindow"));
						
					} catch (Exception e) {
						
						if(sizeOldListDeliveryRequests == 0) {
							cityMap.resetListDeliveryRequests();
						} else {
							cityMap.setListDeliveryRequests(oldListDeliveryRequests);
						}
						
						throw new ExceptionXML("The time window is erroneous");
						
					}
	
					if (!elt.getAttribute("departureTime").equals("")) {
						
						try {
							
							String departureTimeString = elt.getAttribute("departureTime");
							hour = Integer.parseInt(departureTimeString.split(":")[0]);
							minute = Integer.parseInt(departureTimeString.split(":")[1]);
							seconde = Integer.parseInt(departureTimeString.split(":")[2]);
							departureTime = LocalTime.of(hour, minute, seconde);
							
						} catch (Exception e) {
							
							if(sizeOldListDeliveryRequests == 0) {
								cityMap.resetListDeliveryRequests();
							} else {
								cityMap.setListDeliveryRequests(oldListDeliveryRequests);
							}
							
							throw new ExceptionXML("The departure time of the delivery request n°"+ ++nbDeliveryRequest + "is erroneous");
							
						}
						
					} else {
						
						departureTime = null;
						
					}
	
					if (!elt.getAttribute("arrivalTime").equals("")) {
						
						try {
							
							String arrivalTimeString = elt.getAttribute("arrivalTime");
							hour = Integer.parseInt(arrivalTimeString.split(":")[0]);
							minute = Integer.parseInt(arrivalTimeString.split(":")[1]);
							seconde = Integer.parseInt(arrivalTimeString.split(":")[2]);
							arrivalTime = LocalTime.of(hour, minute, seconde);
							
						} catch (Exception e) {
							
							if(sizeOldListDeliveryRequests == 0) {
								cityMap.resetListDeliveryRequests();
							} else {
								cityMap.setListDeliveryRequests(oldListDeliveryRequests);
							}
							
							throw new ExceptionXML("The departure time of the delivery request n°"+ ++nbDeliveryRequest + "is erroneous");
							
						}
						
					} else {
						
						arrivalTime = null;	
						
					}
	
					++nbDeliveryRequest;
					if(!elt.getAttribute("departureTime").equals("") && !elt.getAttribute("arrivalTime").equals("")) {
						
						cityMap.addDeliveryRequest(nbDeliveryRequest, idIntersection, deliveryLocation.getLatitude(), deliveryLocation.getLongitude(), timeWindow, departureTime, arrivalTime);
						
					} else {
						
						cityMap.addDeliveryRequest(nbDeliveryRequest, idIntersection, deliveryLocation.getLatitude(), deliveryLocation.getLongitude(), timeWindow);
						
					}
					
				}
				
			}
			
		}
		
		if (nbDeliveryRequest == 0) {
			
			if(sizeOldListDeliveryRequests == 0) {
				cityMap.resetListDeliveryRequests();
			} else {
				cityMap.setListDeliveryRequests(oldListDeliveryRequests);
			}
			
			throw new ExceptionXML("No delivery request available");
			
		} else if (nbWarehouse == 0) {
			
			if(sizeOldListDeliveryRequests == 0) {
				cityMap.resetListDeliveryRequests();
			} else {
				cityMap.setListDeliveryRequests(oldListDeliveryRequests);
			}
			
			throw new ExceptionXML("There is no warehouse");
			
		} 
		
	}

}
