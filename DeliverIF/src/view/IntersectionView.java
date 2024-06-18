package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import model.*;
import java.awt.Point;
import java.util.List;

/**
 * IntersectionView contains methods to draw an intersection on the map.
 * @author H4114
 */
public class IntersectionView {
	
	/**
	 * Selected intersection on the city map.
	 */
	private Intersection selectedIntersection;
	
	/**
	 * Creates a new IntersectionView.
	 */
	public IntersectionView() {
	}
	
	/**
     * Gets the selected intersection from the city map.
     *
     * @return The selected intersection.
     */
    public Intersection getSelectedIntersection() {

        return selectedIntersection;
        
    }
    
    /**
     * Sets the selected intersection in the city map.
     *
     * @param intersection the intersection to select.
     */
    public void setSelectedIntersection(Intersection intersection) {

        this.selectedIntersection = intersection;
        
    }
    
	/**
	 * Draw a rectangle for all non-assigned delivery requests.
	 *
	 * @param cityMap 				The city map on which is drawn the tour.
	 * @param listDeliveryRequests 	List of deliveryRequest that can be from city map or a tour
	 * @param g The 				Graphics object to paint.
	 * @param color 				Color of the road segment.
	 * @param coefX 				x-coefficient to scale the tour.
	 * @param coefY 				y-coefficient to scale the tour.
	 * @param zoom 					Zoom level on the map.
	 * @param sideLength 			Size of the square encompassing the city map.
	 * @param focus 				Point that handles the position of the city map.
	 */
	 public void drawDeliveryRequests(CityMap cityMap, List <Intersection> listDeliveryRequests, Graphics2D g, Color color, double coefX, double coefY, double zoom, int sideLength, Point focus) {
	   	
	   	if(!cityMap.getListDeliveryRequests().isEmpty()) {
	   		
	   		for (Intersection deliveryLocation : listDeliveryRequests) {
	   			
	   			if(deliveryLocation instanceof Warehouse) { // Warehouse
	   				
	   				g.setColor(new Color(255, 105, 180)); // beautiful pink
	   				g.setStroke(new BasicStroke(3));
	   								
	   			} else { // Delivery request
	   				
	   				g.setColor(color);
	   				g.setStroke(new BasicStroke(3));
	   				
	   			}
	
	   			g.fillRect((int) Math.round((deliveryLocation.getLongitude() + focus.x / coefY - cityMap.getLongitudeMin()) * coefY) - (int)(4*zoom),
	   						sideLength - (int) Math.round((deliveryLocation.getLatitude() - focus.y / coefX - cityMap.getLatitudeMin()) * coefX)- (int)(4*zoom),
	   						(int)(7*zoom), 
	   						(int)(7*zoom));
	   				
	   		}
	   	}
	   	
	}
       
   	/**
   	 * Draw a rectangle for the selected intersection
   	 *
   	 * @param cityMap 		The city map on which is drawn the tour.
   	 * @param g The 		Graphics object to paint.
   	 * @param color 		Color of the road segment.
   	 * @param coefX 		x-coefficient to scale the tour.
   	 * @param coefY 		y-coefficient to scale the tour.
   	 * @param zoom 			Zoom level on the map.
   	 * @param sideLength 	Size of the square encompassing the city map.
   	 * @param focus 		Point that handles the position of the city map.
   	 */
     public void drawSelectedIntersection(CityMap cityMap, Graphics2D g, Color color, double coefX, double coefY, double zoom, int sideLength, Point focus) {
       	
	   	if(cityMap != null && cityMap.getSelectedIntersection() != null) {
	   		
	   		g.setColor(color);
	       	
	       	selectedIntersection = cityMap.getSelectedIntersection();
	       	
	       	g.fillRect((int) Math.round((selectedIntersection.getLongitude() + focus.x / coefY - cityMap.getLongitudeMin()) * coefY) - (int)(6*zoom),
	   				sideLength - (int) Math.round((selectedIntersection.getLatitude() - focus.y / coefX - cityMap.getLatitudeMin()) * coefX)- (int)(6*zoom),
	   				(int)(12*zoom), 
	   				(int)(12*zoom));
	       	
	   	}
       
   	}

   
}
