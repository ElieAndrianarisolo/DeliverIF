package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controller.Controller;
import model.CityMap;
import model.Courier;
import model.Tour;

/**
 * GraphicalView provides a graphical representation of the map, delivery requests, and tours.
 * @author H4114
 */
public class GraphicalView extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	
	private MapPanel mapPanel;
	
	/**
	 * Constructs a new GraphicalView.
	 */
    public GraphicalView() {
    }

    /**
	 * Constructs a new GraphicalView with the specified window, controller, city map, and tours.
	 *
	 * @param controller 	The controller responsible for handling user interactions.
	 * @param cityMap 		The city map containing the map data.
	 * @param listTours 	The list of tours.
	 */
    public GraphicalView(Controller controller, CityMap cityMap, LinkedHashMap<Courier, Tour> listTours) {
        
    	super();

		setLayout(new CardLayout(40,40));
		setBackground(Color.white);

		if (cityMap != null) {
			
			cityMap.addObserver(this);
				
				
		}

		mapPanel = new MapPanel(controller, cityMap, listTours);
		this.add(mapPanel);
		
	}
    
    /**
	 * Overrides the paintComponent method.
	 */
    @Override
    public void paintComponent(Graphics g) {
        // Custom painting code if needed
    }

    /**
	 * Updates the view in response to changes in the model.
	 */
    @Override
    public void update(Observable o, Object arg) {
        
    	mapPanel.resize();
    	
    }

    /**
	 * Resizes the map panel.
	 */
    public void resize() {
        
    	mapPanel.resize();
    	
    }

    /**
	 * Retrieves the map panel associated with this view.
	 *
	 * @return The map panel.
	 */
    public MapPanel getMapPanel() {
        
    	return mapPanel;

    }
    
    /**
	 * Sets the new city map in the view.
	 *
	 * @param cityMap The new city map.
	 */
    public void newCityMap(CityMap cityMap) {
        
    	cityMap.addObserver(this);
    	mapPanel.setCityMap(cityMap);
    	
    }
    
    /**
	 * Sets the new delivery requests in the view.
	 *
	 * @param cityMap The city map containing the updated delivery requests.
	 */
    public void newDeliveryRequests(CityMap cityMap) {
        
    	mapPanel.setDeliveryRequests(cityMap);
    	
    }
    
    /**
	 * Sets the new list of tours in the view.
	 *
	 * @param listTours The new list of tours.
	 */
    public void newListTours(LinkedHashMap<Courier, Tour> listTours) {
        
    	mapPanel.setListTours(listTours);
    	
    }
    
    /**
	 * Sets the view to display only the city map.
	 */
    public void setCityMapMode() {
        
    	mapPanel.resize();

		mapPanel.setCityMapDisplay(true);
		mapPanel.setDeliveryRequestsDisplay(false);
		mapPanel.setTourDisplay(false);
    	
    }
    
    /**
	 * Sets the view to display the city map along with the delivery requests.
	 */
    public void setLoadDeliveryRequestsMode() {
        
		mapPanel.setCityMapDisplay(true);
		mapPanel.setDeliveryRequestsDisplay(true);
		mapPanel.setTourDisplay(false);
		
		mapPanel.repaint();
		repaint();
    	
    }
    
    /**
	 * Sets the view to display the city map along with the delivery requests and tours.
	 */
    public void setInitializeTourMode() {
        
		mapPanel.setCityMapDisplay(true);
		mapPanel.setDeliveryRequestsDisplay(true);
		mapPanel.setTourDisplay(true);
		
		mapPanel.repaint();
		repaint();
    	
    }
    
    /**
	 * Sets the view to display the city map along with the tour.
	 */
    public void setDisplayTourMode() {
        
		mapPanel.setCityMapDisplay(true);
		mapPanel.setDeliveryRequestsDisplay(false);
		mapPanel.setTourDisplay(true);
		
		mapPanel.repaint();
		repaint();
    	
    }
    
    /**
	 * Sets the courier corresponding to the tour to display
	 */
	public void setCourierCorrespondingToTourDisplay(Courier courier) {
		
		mapPanel.setSelectedCourier(courier);
		
		mapPanel.repaint();
		repaint();
		
	}
    
}