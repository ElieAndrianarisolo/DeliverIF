package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.*;

/**
 * TourView contains methods to draw a list of tours on the map.
 * @author H4114
 */
public class TourView {
	
	/**
	 * Creates a new TourView.
	 */
	public TourView() {
	}
	
	/**
	 * Draws all the components of the list of tours.
	 * 
	 * @param intersectionView 			The tool allowing to draw an intersection.
	 * @param roadSegmentView 			The tool allowing to draw a road segment.
	 * @param cityMap 					The city map on which is drawn the tour.
	 * @param listTours 				The tours to be drawn.
	 * @param g 						The Graphics object to paint.
	 * @param coefX 					x-coefficient to scale the tour.
	 * @param coefY 					y-coefficient to scale the tour.
	 * @param zoom 						Zoom level on the map.
	 * @param sideLength 				Size of the square encompassing the city map.
	 * @param focus 					Point that handles the position of the city map.
	 * @param displayDeliveryRequests 	true if the delivery requests 
	 * @param selectedCourier			The selected courier for whom their tour is to be displayed
	 */
	public void drawTour(IntersectionView intersectionView, RoadSegmentView roadSegmentView, CityMap cityMap, LinkedHashMap<Courier, Tour> listTours, Graphics2D g, double coefX, double coefY, double zoom, int sideLength, Point focus, boolean displayDeliveryRequests, Courier selectedCourier) {
		
		 for (Map.Entry<Courier, Tour> tourEntry : listTours.entrySet()) {
			 
		    Tour tour = tourEntry.getValue();
		    Courier courier = tourEntry.getKey();
		    
		    if(tour != null && !tour.getListDeliveryRequests().isEmpty()) {
		    
			    if(displayDeliveryRequests == false && (courier.equals(selectedCourier) || selectedCourier.getIdCourier() == -1)) {
			    	
					//RoadSegments for tour
					if(tour.getCourier() == null) {
						
						roadSegmentView.drawRoadSegments(cityMap, tour.getListRoadSegments(), g, null, 4, coefX, coefY, zoom, sideLength, focus);
						
					} else {
						
						roadSegmentView.drawRoadSegments(cityMap, tour.getListRoadSegments(), g, tour.getCourier().getColor(), 4, coefX, coefY, zoom, sideLength, focus);
						
					}
	
			 	}
		    	
			    // Transform delivery requests and the warehouse into a list of intersections containing warehouse
				List<Intersection> listIntersections = new ArrayList<Intersection>();
				listIntersections.add(tour.getWarehouse());
				
				for(DeliveryRequest deliveryLocation : tour.getListDeliveryRequests()) {
					
					listIntersections.add(new Intersection(deliveryLocation.getIdIntersection(), deliveryLocation.getLatitude(), deliveryLocation.getLongitude()));
					
				}
				
				intersectionView.drawDeliveryRequests(cityMap, listIntersections, g, Color.BLACK, coefX, coefY, zoom, sideLength, focus);

				//Delivery Requests
				g.setColor(Color.BLACK);
				
				if(tour.getListDeliveryRequests() != null) {
					
					for (DeliveryRequest deliveryRequest : tour.getListDeliveryRequests()) {
						
						g.fillRect((int) Math.round((deliveryRequest.getLongitude() + focus.x / coefY - cityMap.getLongitudeMin()) * coefY) - (int)(4*zoom),
								sideLength - (int) Math.round((deliveryRequest.getLatitude() - focus.y / coefX - cityMap.getLatitudeMin()) * coefX)- (int)(4*zoom),
								(int)(7*zoom), 
								(int)(7*zoom));
		
					}
		
				}
				
				// Information about the tour
				if(tour.getStartTime() != null && tour.getEndTime() != null && displayDeliveryRequests == false && (courier.equals(selectedCourier) || selectedCourier.getIdCourier() == -1)) {
					
					g.setFont(new Font("default", Font.BOLD, 16));
					g.setColor(Color.RED);
					
					if(courier.equals(selectedCourier)) {
						
						// Tour time interval
						g.drawString(tour.getStartTime()+ " - " + tour.getEndTime() ,
									(int) Math.round((tour.getWarehouse().getLongitude() + focus.x / coefY - cityMap.getLongitudeMin()) * coefY) - (int)(5*zoom),
									sideLength - (int) Math.round((tour.getWarehouse().getLatitude()- focus.y / coefX - cityMap.getLatitudeMin()) * coefX)- (int)(5*zoom));
					
					}
					
					// Delivery order
					for(int i = 0; i<tour.getListDeliveryRequests().size(); i++) {
						
						g.drawString(" " + (i+1),
								(int) Math.round((tour.getListDeliveryRequests().get(i).getLongitude() + focus.x / coefY - cityMap.getLongitudeMin()) * coefY) - (int)(5*zoom),
								sideLength - (int) Math.round((tour.getListDeliveryRequests().get(i).getLatitude()- focus.y / coefX - cityMap.getLatitudeMin()) * coefX)- (int)(5*zoom));
						
					}
					
				}
				
		    }
		    
		}
		 
	}
	
}
