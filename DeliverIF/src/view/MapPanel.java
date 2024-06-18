package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import controller.Controller;
import model.*;

/**
 * MapPanel represents a panel for displaying the city map with its road segments, intersections, and tour.
 * @author H4114
 */
public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private CityMap cityMap;
	private LinkedHashMap<Courier, Tour> listTours;
	private Courier selectedCourier;
	
	/**
	 * Tools allowing to draw on the city map.
	 */
	private IntersectionView intersectionView;
	private RoadSegmentView roadSegmentView;
	private TourView tourView;
		
	/**
	 * Size of the square encompassing the city map.
	 */
	private int sideLength;

	/**
	 * Coefficients to scale the city map.
	 */
	private double coefX;
	private double coefY;
	
	/**
	 * Point that handles the position of the city map.
	 */
	private Point focus;
	
	/**
	 * Zoom level on the map.
	 */
	private double zoom;

	/**
	 * Activate the display of the model objects.
	 */
	private boolean displayCityMap;
	private boolean displayDeliveryRequests;
	private boolean displayTour;
	
	/**
	 * Creates a new MapPanel.
	 */
    public MapPanel() {
    }

    /**
	 * Creates a new MapPanel with the specified window, controller, city map, and tour.
	 *
	 * @param controller 	The controller responsible for handling user interactions.
	 * @param cityMap 		The city map to be displayed.
	 * @param listTours 	The list of tours to be displayed.
	 */
    public MapPanel(Controller controller, CityMap cityMap, LinkedHashMap<Courier, Tour> listTours) {
        
    	this.cityMap = cityMap;
		this.listTours = listTours;
		this.selectedCourier = new Courier(-1, "All");
		
		displayCityMap = false;
		displayDeliveryRequests = false;
		displayTour = false;

		MouseListeners mouseListener = new MouseListeners(controller);

		addMouseMotionListener(mouseListener);
		addMouseListener(mouseListener);
		addMouseWheelListener(mouseListener);

		init();

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	
    }
    
    /**
	 * Initializes the MapPanel with default values.
	 */
	private void init() {
		
		coefX = 0;
		coefY = 0;
		focus = new Point();

		resetZoomFocus();
		
	}
    
	/**
	 * Repaints the MapPanel with the specified graphics.
	 *
	 * @param g The Graphics object to paint.
	 */
    public void repaint(Graphics g) {
        
    	super.repaint();

		paintComponent(g);
    	
    }


	/**
	 * Paints the components of the MapPanel using the specified graphics.
	 *
	 * @param g The Graphics object used for painting.
	 */
	public void paintComponent(Graphics g) {
	
		super.paintComponent(g);
	
		Graphics2D g2 = (Graphics2D) g;
	
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
	
		// Background
	
		g2.setColor(new Color(200, 200, 200));
	
		g2.fillRect(0, 0, getSize().width, getSize().height);
	
		// City Map
		
		if (cityMap != null && displayCityMap == true) {
			
			roadSegmentView.drawRoadSegments(cityMap, cityMap.getListRoadSegments(),g2, Color.BLACK, 1, coefX, coefY, zoom, sideLength, focus);
			
			// Non-yet-assigned delivery requests
	
			if (displayDeliveryRequests == true) {
				
				intersectionView.drawDeliveryRequests(cityMap, cityMap.getListDeliveryRequests(), g2, Color.BLUE, coefX, coefY, zoom, sideLength, focus);
				
			}
			
			// Tour
			
			if(displayTour == true && listTours != null) {
				
		    	tourView.drawTour(intersectionView, roadSegmentView, cityMap, listTours, g2, coefX, coefY, zoom, sideLength, focus, displayDeliveryRequests, selectedCourier);

			} 
			
			// Selected Intersection
			intersectionView.drawSelectedIntersection(cityMap, g2, Color.GREEN, coefX, coefY, zoom, sideLength, focus);
				
			// Selected Road segment
			roadSegmentView.drawSelectedRoadSegment(cityMap, g2, Color.GREEN, 4, coefX, coefY, zoom, sideLength, focus);
	
		}
		
		// Reset g properties
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
	
	}


    /**
	 * Resizes the MapPanel based on the current size and the city map's dimensions.
	 */
    public void resize() {
        
    	if (cityMap != null) {
    		
			if (getSize().height < getSize().width) {
				
				sideLength = getSize().height;

				coefX = (getSize().height) / (cityMap.getLatitudeMax() - cityMap.getLatitudeMin()) * zoom;
				coefY = (getSize().height) / (cityMap.getLongitudeMax() - cityMap.getLongitudeMin()) * zoom;
				
			} else {
				
				sideLength = getSize().width;

				coefX = (getSize().width) / (cityMap.getLatitudeMax() - cityMap.getLatitudeMin()) * zoom;
				coefY = (getSize().width) / (cityMap.getLongitudeMax() - cityMap.getLongitudeMin()) * zoom;
				
			}
			
		} else {

			sideLength = 0;
			coefX = 1;
			coefY = 1;
			
		}

		repaint();
    	
    }
    
    /**
	 * Zooms in or out on the map based on the specified number of steps and the zoom point.
	 *
	 * @param steps The number of steps to zoom (positive for zooming in, negative for zooming out).
	 * @param point The point on which the zoom is centered.
	 */
    public void zoom(int steps, Point point) {
    	
		point.setLocation(point.x, point.y - sideLength);

		zoom = zoom * Math.pow(2, -steps);
		
		if(zoom < 0.25) {
			
			zoom = 0.25;
			
		} else if (zoom > 16){
			
			zoom = 16;
			
		} else {
			
			if (steps > 0) {
				
				focus.setLocation((focus.x + point.x) / 2, (focus.y + point.y) / 2);
				
			} else {
				
				focus.setLocation(2 * focus.x - point.x, 2 * focus.y - point.y);
				
			}

			resize();
			
		}
		
	}
    
    /**
	 * Drags the map by the specified delta.
	 *
	 * @param delta The change in position for the map.
	 */
    public void drag(Point delta) {
    	
    	focus.setLocation(focus.x + delta.x, focus.y + delta.y);
		repaint();

	}

    /**
	 * Resets the zoom level and focus point to default values.
	 */
    public void resetZoomFocus() {
    	
    	zoom = 1;
		focus.setLocation(0, 0);
		
    }
    
    /**
	 * Gets the X coefficient for scaling the map.
	 *
	 * @return The X coefficient.
	 */
    public double getCoefX() {
        
    	return this.coefX;
    		
    }
    
    /**
	 * Gets the Y coefficient for scaling the map.
	 *
	 * @return The Y coefficient.
	 */
    public double getCoefY() {
        
    	return this.coefY;
    		
    }
    
    /**
	 * Gets the side length of the square encompassing the city map.
	 *
	 * @return The side length.
	 */
    public int getSideLength() {
        
    	return this.sideLength;
    		
    }
    
    /**
	 * Gets the focus point of the city map.
	 *
	 * @return The focus point.
	 */
    public Point getFocus() {
        
    	return this.focus;
    		
    }
    
    /**
	 * Sets the display state of the city map.
	 *
	 * @param state The display state (true for displaying, false for hiding).
	 */
    public void setCityMapDisplay(boolean state) {
        
    	displayCityMap = state;
    	
    }

    /**
	 * Sets the display state of the delivery requests.
	 *
	 * @param state The display state (true for displaying, false for hiding).
	 */
    public void setDeliveryRequestsDisplay(boolean state) {
        
    	displayDeliveryRequests = state;
    	
    }

    /**
	 * Sets the display state of the tour.
	 *
	 * @param state The display state (true for displaying, false for hiding).
	 */
    public void setTourDisplay(boolean state) {
        
    	displayTour = state;
    	
    }

    /**
	 * Sets the city map to be displayed.
	 *
	 * @param cityMap The city map to be displayed.
	 */
    public void setCityMap(CityMap cityMap) {
        
    	this.cityMap = cityMap;
    	
    	this.intersectionView = new IntersectionView();
    	this.roadSegmentView = new RoadSegmentView();
    	this.tourView = new TourView();
    	
    	setListTours(null);
    	
    	resetZoomFocus();
		resize();
		repaint();
    	
    }
    
    /**
	 * Sets the delivery requests to be displayed.
	 *
	 * @param cityMap The city map containing the delivery requests.
	 */
    public void setDeliveryRequests(CityMap cityMap) {
        
    	this.cityMap.setListDeliveryRequests(cityMap.getListDeliveryRequests());
    	setListTours(null);
    	
    	resetZoomFocus();
		resize();
		repaint();
    	
    }

    /**
	 * Sets the list of tours to be displayed.
	 *
	 * @param listTours The list of tours to be displayed.
	 */
    public void setListTours(LinkedHashMap<Courier, Tour> listTours) {
        
    	this.listTours = listTours;
    		
    }
    
    /**
   	 * Sets the courier corresponding to the tour to display
   	 */
	public void setSelectedCourier(Courier courier) {
		
		selectedCourier = courier;
		
	}
    
}
