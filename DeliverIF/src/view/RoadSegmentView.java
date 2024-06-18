package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import model.*;
import java.awt.Point;
import java.util.List;

/**
 * RoadSegmentView contains methods to draw a road segment on the map.
 * @author H4114
 */
public class RoadSegmentView {
	
	/**
	 * Selected road segment on the city map.
	 */
	private RoadSegment selectedRoadSegment;
	
	/**
	 * Creates a new RoadSegmentView.
	 */
	public RoadSegmentView() {
	}
	
	/**
     * Gets the selected roadSegment from the city map.
     *
     * @return The selected roadSegment.
     */
    public RoadSegment getSelectedRoadSegment() {

        return selectedRoadSegment;
        
    }
    
    /**
     * Sets the selected road segment in the city map.
     *
     * @param roadSegment the road segment to select.
     */
    public void setSelectedRoadSegment(RoadSegment roadSegment) {

        this.selectedRoadSegment = roadSegment;
        
    }
    
    /**
	 * Draw road segments.
	 * 
	 * @param cityMap 			The city map on which is drawn the tour.
	 * @param listRoadSegments	List of roadSegments that can be from a cityMap or a tour
	 * @param g The 			Graphics object to paint.
	 * @param color 			Color of the road segment.
	 * @param size 				Size of the drawn road segment.
	 * @param coefX 			x-coefficient to scale the tour.
	 * @param coefY 			y-coefficient to scale the tour.
	 * @param zoom 				Zoom level on the map.
	 * @param sideLength 		Size of the square encompassing the city map.
	 * @param focus 			Point that handles the position of the city map.
	 */
    public void drawRoadSegments(CityMap cityMap, List <RoadSegment> listRoadSegments,Graphics2D g, Color color, int size, double coefX, double coefY, double zoom, int sideLength, Point focus) {
    	
    	if(cityMap != null) {
    		
    		g.setStroke(new BasicStroke(size));
    		g.setColor(color);
    		
    		for (RoadSegment roadSegment : listRoadSegments) {
    			
    			g.drawLine((int) Math.round((roadSegment.getStartingIntersection().getLongitude() - cityMap.getLongitudeMin()) * coefY + focus.x),
    					sideLength - (int) Math.round((roadSegment.getStartingIntersection().getLatitude() - cityMap.getLatitudeMin()) * coefX - focus.y),
    					(int) Math.round((roadSegment.getEndingIntersection().getLongitude() - cityMap.getLongitudeMin()) * coefY + focus.x),
    					sideLength - (int) Math.round((roadSegment.getEndingIntersection().getLatitude() - cityMap.getLatitudeMin())* coefX - focus.y)
    					);
    		}
    		
    	}
    	
    }
    
    /**
	 * Draw the selected road segment on the city map.
	 * 
	 * @param cityMap 		The city map on which is drawn the tour.
	 * @param g 			The Graphics object to paint.
	 * @param color 		Color of the road segment.
	 * @param size 			Size of the drawn road segment.
	 * @param coefX 		x-coefficient to scale the tour.
	 * @param coefY 		y-coefficient to scale the tour.
	 * @param zoom 			Zoom level on the map.
	 * @param sideLength 	Size of the square encompassing the city map.
	 * @param focus 		Point that handles the position of the city map.
	 */
    public void drawSelectedRoadSegment(CityMap cityMap, Graphics2D g, Color color, int size, double coefX, double coefY, double zoom, int sideLength, Point focus) {
    	
		if(cityMap != null && cityMap.getSelectedRoadSegment() != null) {
			
			g.setColor(color);
			g.setStroke(new BasicStroke(size));
			
			selectedRoadSegment = cityMap.getSelectedRoadSegment();
			
			g.drawLine((int) Math.round((selectedRoadSegment.getStartingIntersection().getLongitude() - cityMap.getLongitudeMin()) * coefY + focus.x),
					sideLength - (int) Math.round((selectedRoadSegment.getStartingIntersection().getLatitude() - cityMap.getLatitudeMin()) * coefX - focus.y),
					(int) Math.round((selectedRoadSegment.getEndingIntersection().getLongitude() - cityMap.getLongitudeMin()) * coefY + focus.x),
					sideLength - (int) Math.round((selectedRoadSegment.getEndingIntersection().getLatitude() - cityMap.getLatitudeMin()) * coefX - focus.y));
			
		}
		
    }
    
}
