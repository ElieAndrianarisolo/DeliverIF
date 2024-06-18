package model;

import java.awt.Point;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import parser.ExceptionXML;

/**
 * Represents a city map with intersections, road segments, and delivery requests.
 * @author H4114
 */
public class CityMap extends Observable {

    private HashMap<Long, Intersection> listIntersections;
    private List<Intersection> listDeliveryRequests;
    private List<RoadSegment> listRoadSegments;
    private Intersection selectedIntersection;
    private RoadSegment selectedRoadSegment;
    private double latitudeMin;
    private double latitudeMax;
    private double longitudeMin;
    private double longitudeMax;
    
    /**
     * Default constructor for creating a city map.
     */
    public CityMap() {
    	
    	this.listIntersections = new HashMap<Long, Intersection>();
		this.listRoadSegments = new ArrayList<RoadSegment>();
		this.listDeliveryRequests = new ArrayList<Intersection>();
		
		init();
    	
    }
    
    /**
     * Constructor for creating a city map with existing intersections and road segments.
     *
     * @param listIntersections The existing intersections.
     * @param listRoadSegments 	The existing road segments.
     */
    public CityMap(HashMap<Long, Intersection> listIntersections, List<RoadSegment> listRoadSegments) {
        
    	this.listIntersections = listIntersections;
    	this.listRoadSegments = listRoadSegments;
    	
    	init();
    	
    }

    /**
     * Initializes the city map.
     */
    private void init() {
    	
    	latitudeMin = 999999;
    	longitudeMin = 999999;
		latitudeMax = 0;
		longitudeMax = 0;
		
		resetBounds();
		
		notifyObservers();
		
    }

    /**
     * Resets the city map by clearing all intersections, road segments, and delivery requests.
     */
    public void reset() {
        
    	listIntersections.clear();
		listRoadSegments.clear();
		listDeliveryRequests.clear();
		
		resetBounds();
		
    }
    
    /**
     * Resets the list of delivery requests.
     */
    public void resetListDeliveryRequests() {
        
		listDeliveryRequests.clear();
		
		resetBounds();
		
    }

    /**
     * Resets the bounds of the city map.
     */
    public void resetBounds() {
    	
    	latitudeMin = 999999;
		longitudeMin = 999999;
    	latitudeMax = 0;
		longitudeMax = 0;
		
		for (Map.Entry<Long, Intersection> mapentry : listIntersections.entrySet()) {
			
			if (mapentry.getValue().getLatitude() > latitudeMax){
				
				latitudeMax = mapentry.getValue().getLatitude();
				
			} else if (mapentry.getValue().getLatitude() < latitudeMin){
				
				latitudeMin = mapentry.getValue().getLatitude();
				
			}
			
			if (mapentry.getValue().getLongitude() > longitudeMax) {
				
				longitudeMax = mapentry.getValue().getLongitude();
				
			} else if (mapentry.getValue().getLongitude() < longitudeMin){
				
				longitudeMin = mapentry.getValue().getLongitude();
				
			}
        }
		
		setChanged();
		notifyObservers();
		
    }

    /**
     * Gets the list of intersections in the city map.
     *
     * @return The list of intersections.
     */
    public HashMap<Long, Intersection> getListIntersections() {

        return listIntersections;
        
    }
    
    /**
     * Gets the list of delivery requests in the city map.
     *
     * @return The list of delivery requests.
     */
    public List<Intersection> getListDeliveryRequests() {

        return listDeliveryRequests;
        
    }

    /**
     * Gets the list of road segments in the city map.
     *
     * @return The list of road segments.
     */
    public List<RoadSegment> getListRoadSegments() {
        
    	return listRoadSegments;
    	
    }

    /**
     * Gets the selected intersection in the city map.
     *
     * @return The selected intersection.
     */
    public Intersection getSelectedIntersection() {

        return selectedIntersection;
        
    }

    /**
     * Gets the selected road segment in the city map.
     *
     * @return The selected road segment.
     */
    public RoadSegment getSelectedRoadSegment() {

        return selectedRoadSegment;
        
    }

    /**
     * Gets the minimum latitude value in the city map.
     *
     * @return The minimum latitude value.
     */
    public double getLatitudeMin() {

        return latitudeMin;
        
    }

    /**
     * Gets the maximum latitude value in the city map.
     *
     * @return The maximum latitude value.
     */
    public double getLatitudeMax() {

        return latitudeMax;
        
    }

    /**
     * Gets the minimum longitude value in the city map.
     *
     * @return The minimum longitude value.
     */
    public double getLongitudeMin() {

        return longitudeMin;
        
    }
    
    /**
     * Gets the maximum longitude value in the city map.
     *
     * @return The maximum longitude value.
     */
    public double getLongitudeMax() {

        return longitudeMax;
        
    }
    
    
    /**
     * Sets the selected Road Segment
     *
     * @param selectedRoadSegment The selected Road Segment
     */
    public void setSelectedRoadSegment(RoadSegment selectedRoadSegment) {
    	
    	this.selectedRoadSegment = selectedRoadSegment;
    	
    }

    /**
     * Sets the list of intersections in the city map.
     *
     * @param listIntersections The list of intersections to set.
     */
    public void setListIntersections(HashMap<Long, Intersection> listIntersections) {

        this.listIntersections = listIntersections;
        
        resetBounds();
        
    }
    
    /**
     * Sets the list of delivery requests in the city map.
     *
     * @param listDeliveryRequests The list of delivery requests to set.
     */
    public void setListDeliveryRequests(List<Intersection> listDeliveryRequests) {

        this.listDeliveryRequests = listDeliveryRequests;
        
    }

    /**
     * Sets the list of road segments in the city map.
     *
     * @param listRoadSegments The list of road segments to set.
     */
    public void setListRoadSegments(List<RoadSegment> listRoadSegments) {

        this.listRoadSegments = listRoadSegments;
        
        setChanged();
        notifyObservers();
        
    }

    /**
     * Provides a string representation of the city map.
     *
     * @return A string representation of the city map.
     */
    @Override
    public String toString() {
        
        return "CityMap [listIntersections=" + listIntersections + ", \nlistRoadSegments=" + listRoadSegments + ", \nlistDeliveryRequests"+ listDeliveryRequests + "]";
        
    }

    /**
     * Selects a delivery request in the city map.
     *
     * @param deliveryRequest The delivery request to select.
     */
    public void selectDeliveryRequest(DeliveryRequest deliveryRequest){
		
    	selectedIntersection = deliveryRequest;
		
		setChanged();
		notifyObservers();
		
	}
    
    /**
     * Selects a warehouse in the city map.
     *
     * @param warehouse The warehouse to select.
     */
    public void selectWarehouse(Warehouse warehouse){
		
    	selectedIntersection = warehouse;
		
		setChanged();
		notifyObservers();
		
	}
    
    /**
     * Adds an intersection to the city map.
     *
     * @param intersectionToAdd 	The intersection to add.
     * @throws ExceptionXML 		If there's an issue with XML.
     */
    public void addIntersection(Intersection intersectionToAdd) throws ExceptionXML {
        
		this.listIntersections.put(intersectionToAdd.getIdIntersection(), intersectionToAdd);
		
		resetBounds();
		
    }

    /**
     * Adds an intersection to the city map with specified parameters.
     *
     * @param idIntersection 	The unique identifier of the intersection.
     * @param latitude 			The latitude of the intersection.
     * @param longitude 		The longitude of the intersection.
     * @throws ExceptionXML 	If there's an issue with XML.
     */
    public void addIntersection(Long idIntersection, double latitude, double longitude) throws ExceptionXML {
        
		this.listIntersections.put(idIntersection, new Intersection(idIntersection, latitude, longitude));
		
		resetBounds();
		
    }
    
    /**
     * Adds a warehouse to the city map.
     *
     * @param idIntersection 	The unique identifier of the warehouse.
     * @param latitude 			The latitude of the warehouse.
     * @param longitude 		The longitude of the warehouse.
     * @throws ExceptionXML 	If there's an issue with XML.
     */
    public void addWarehouse(Long idIntersection, double latitude, double longitude) throws ExceptionXML {
        
		this.listDeliveryRequests.add(0, new Warehouse(idIntersection, latitude, longitude));
		
		resetBounds();
		
    }
    
    /**
     * Adds a delivery request to the city map at a specific index.
     *
     * @param index 			The index where the delivery request should be added.
     * @param idIntersection 	The unique identifier of the delivery request.
     * @param latitude 			The latitude of the delivery request.
     * @param longitude 		The longitude of the delivery request.
     * @param timeWindow 		The time window of the delivery request.
     * @throws ExceptionXML 	If there's an issue with XML.
     */
    public void addDeliveryRequest(int index, Long idIntersection, double latitude, double longitude, int timeWindow) throws ExceptionXML {
        
		this.listDeliveryRequests.add(index, new DeliveryRequest (idIntersection, latitude, longitude, timeWindow));
		
		resetBounds();
		
    }
    
    /**
     * Adds a delivery request to the city map.
     *
     * @param idIntersection 	The unique identifier of the delivery request.
     * @param latitude 			The latitude of the delivery request.
     * @param longitude 		The longitude of the delivery request.
     * @param timeWindow 		The time window of the delivery request.
     * @throws ExceptionXML 	If there's an issue with XML.
     */
    public void addDeliveryRequest(Long idIntersection, double latitude, double longitude, int timeWindow) throws ExceptionXML {
        
		this.listDeliveryRequests.add(new DeliveryRequest (idIntersection, latitude, longitude, timeWindow));
		
		resetBounds();
		
    }
    
    /**
     * Adds a delivery request to the city map at a specific index with specified departure and arrival times.
     *
     * @param index 			The index where the delivery request should be added.
     * @param idIntersection 	The unique identifier of the delivery request.
     * @param latitude 			The latitude of the delivery request.
     * @param longitude 		The longitude of the delivery request.
     * @param timeWindow 		The time window of the delivery request.
     * @param departureTime 	The departure time of the delivery request.
     * @param arrivalTime 		The arrival time of the delivery request.
     * @throws ExceptionXML 	If there's an issue with XML.
     */
    public void addDeliveryRequest(int index, Long idIntersection, double latitude, double longitude, int timeWindow, LocalTime departureTime, LocalTime arrivalTime) throws ExceptionXML {
        
		this.listDeliveryRequests.add(index, new DeliveryRequest (idIntersection, latitude, longitude, timeWindow, departureTime, arrivalTime));
		
		resetBounds();
		
    }
    
    /**
     * Removes a delivery request from the list of delivery requests.
     *
     * @param position The position of the delivery request to be removed.
     */
    public void removeDeliveryRequest(int position) {
    	
        listDeliveryRequests.remove(position);
        
    }

    /**
     * Adds a road segment to the city map.
     *
     * @param streetName 			The name of the road segment.
     * @param startingIntersection 	The starting intersection of the road segment.
     * @param endingIntersection 	The ending intersection of the road segment.
     * @param length 				The length of the road segment.
     */
    public void addRoadSegment(String streetName, Intersection startingIntersection, Intersection endingIntersection, double length) {
        
    	RoadSegment roadSegment = new RoadSegment(streetName, startingIntersection, endingIntersection, length);
		this.listRoadSegments.add(roadSegment);
		
		setChanged();
		notifyObservers();
    	
    }

    /**
     * Gets the intersection at a specified point in the city map.
     *
     * @param p 			The point to check.
     * @param focus 		The focus point.
     * @param coefX 		The coefficient for the x-coordinate.
     * @param coefY 		The coefficient for the y-coordinate.
     * @param sideLength 	The side length.
     */
	public void getAtPoint(Point p, Point focus, double coefX, double coefY, int sideLength) {
		
		int toleranceIntersection = 5;
		int toleranceRoadSegment = 5;
		selectedIntersection = null;
		selectedRoadSegment = null;
		
		for (Map.Entry<Long, Intersection> intersection : listIntersections.entrySet()) {
			
			if (p.getX() < (int) Math.round((intersection.getValue().getLongitude() + focus.x / coefY - longitudeMin) * coefY) + toleranceIntersection
				&& p.getX() > (int) Math.round((intersection.getValue().getLongitude() + focus.x / coefY - longitudeMin) * coefY) - toleranceIntersection
				&& p.getY() < sideLength - (int) Math.round((intersection.getValue().getLatitude() - focus.y / coefX - latitudeMin) * coefX) + toleranceIntersection
				&& p.getY() > sideLength - (int) Math.round((intersection.getValue().getLatitude() - focus.y / coefX - latitudeMin) * coefX) - toleranceIntersection) {
				
				selectedIntersection = intersection.getValue();
						
			}
			
		}
		
		if (selectedIntersection == null){
			
			double departX, departY, arriveeX, arriveeY, minX, minY, maxX, maxY;
			double a, b;
			
			for (RoadSegment roadSegment : listRoadSegments) {
				
				departX = (int) Math.round((roadSegment.getStartingIntersection().getLongitude() + focus.x / coefY - longitudeMin) * coefY); 
				departY = sideLength - (int) Math.round((roadSegment.getStartingIntersection().getLatitude() - focus.y / coefX - latitudeMin) * coefX);
				arriveeX = (int) Math.round((roadSegment.getEndingIntersection().getLongitude() + focus.x / coefY - longitudeMin) * coefY); 
				arriveeY = sideLength - (int) Math.round((roadSegment.getEndingIntersection().getLatitude() - focus.y / coefX - latitudeMin) * coefX); 
				
				minX = Math.min(departX, arriveeX);
				maxX = Math.max(departX, arriveeX);
				minY = Math.min(departY, arriveeY);
				maxY = Math.max(departY, arriveeY);
				
				a = (arriveeX - departX) / (arriveeY - departY);				
				b = departX - a * departY;
				
				if (a * p.getY() + b < p.getX() + toleranceRoadSegment * Math.pow(1.5, Math.abs(a)) && a * p.getY() + b > p.getX() - toleranceRoadSegment * Math.pow(1.5, Math.abs(a)) &&
						p.getX() <= maxX + toleranceRoadSegment && p.getX() >= minX - toleranceRoadSegment && p.getY() <= maxY + toleranceRoadSegment && p.getY() >= minY - toleranceRoadSegment){
					
					selectedRoadSegment = roadSegment;
					
				}
				
			}
		
		}
		
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * Finds the corresponding road segment on the city map.
	 * 
	 * @param startingIntersection 	The starting intersection of the road segment to find.
	 * @param endingIntersection 	The ending intersection of the road segment to find.
	 * @return The found road segment, null otherwise.
	 */
	public RoadSegment findCorrespondingRoadSegment(Intersection startingIntersection, Intersection endingIntersection) {
		
		for (RoadSegment rs : listRoadSegments) {
			
			if (((rs.getStartingIntersection().getIdIntersection().equals(startingIntersection.getIdIntersection())) && (rs.getEndingIntersection().getIdIntersection().equals(endingIntersection.getIdIntersection()))) 
					|| (((rs.getEndingIntersection().getIdIntersection().equals(startingIntersection.getIdIntersection())) && (rs.getStartingIntersection().getIdIntersection().equals(endingIntersection.getIdIntersection()))))){
				
				return rs;
				
			}
			
		}
		
		return null;
		
	}

}
