package view;

import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import controller.Controller;
import model.*;

/**
 * TextualView represents the textual display panel in the application.
 * @author H4114
 */
public class TextualView extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	
	private ListPanel listPanel;
	
	/**
	 * Default constructor for TextualView.
	 */
    public TextualView() {
    }

    /**
	 * Constructs a TextualView with the specified components.
	 *
	 * @param controller 	The controller handling user interactions.
	 * @param cityMap 		The city map data model.
	 * @param listTours 	The tours to be drawn.
	 */
    public TextualView(Controller controller, CityMap cityMap, LinkedHashMap<Courier, Tour> listTours) {
        
    	super();

		setLayout(new CardLayout(40, 40));
		
		if(cityMap != null) {
			
			cityMap.addObserver(this);
				
		}

		listPanel = new ListPanel(controller, cityMap, listTours);
		this.add(listPanel);
    	
    }

    /**
	 * Gets the ListPanel associated with this TextualView.
	 *
	 * @return The ListPanel component.
	 */
    public ListPanel getListPanel() {
        
    	return listPanel;
    	
    }

    /**
	 * Updates the view in response to changes in observed models.
	 *
	 * @param o 	The Observable object.
	 * @param arg 	The argument passed by the observed object.
	 */
    public void update(Observable o, Object arg) {
    	
    	listPanel.repaint();
		listPanel.fillUpList();
		repaint();

    }
    
    /**
	 * Sets the TextualView to display the CityMap mode.
	 */
    public void setCityMapMode() {
    	
    	listPanel.setCourierDisplay(false);
    	listPanel.setDeliveryRequestsDisplay(false);
		listPanel.setToursDisplay(false);

		listPanel.fillUpList();
		repaint();

    }
    
    /**
	 * Sets the TextualView to display the Delivery Requests mode.
	 * This mode shows information about delivery requests.
	 */
	public void setDeliveryRequestsMode() {

		listPanel.setCourierDisplay(false);
		listPanel.setDeliveryRequestsDisplay(true);
		listPanel.setToursDisplay(false);

		listPanel.fillUpList();
		repaint();
		
	}
	
	/**
	 * Sets the TextualView to display the Initialize Tour mode.
	 * This mode shows information about delivery requests assigned to a tour.
	 */
	public void setInitializeTourMode() {
		
		listPanel.setCourierDisplay(false);
		listPanel.setDeliveryRequestsDisplay(true);
		listPanel.setToursDisplay(false);
		
		listPanel.fillUpList();
		repaint();
		
	}
	
	/**
	 * Sets the TextualView to display the delivery requests assigned to a tour.
	 */
	public void setDisplayTourMode() {
		
		listPanel.setCourierDisplay(false);
		listPanel.setDeliveryRequestsDisplay(false);
		listPanel.setToursDisplay(true); 
		
		listPanel.fillUpList(); 
		repaint();
		
	}
	
	/**
	 * Sets the TextualView to display the Courier Management mode.
	 * This mode shows information about the couriers.
	 * 
	 * @param listCouriers The most recent list of couriers.
	 */
	public void setCourierManagementMode(List<Courier> listCouriers) {
		
		listPanel.setDeliveryRequestsDisplay(false);
		listPanel.setToursDisplay(false); 
		listPanel.setCourierDisplay(true);
		
		
		listPanel.setListCourier(listCouriers);
		
		listPanel.fillUpList();
		repaint();
		
	}
		
	/**
	 * Updates the ListPanel with new delivery requests from the provided CityMap.
	 *
	 * @param cityMap The CityMap containing the new delivery requests.
	 */
	public void newDeliveryRequests(CityMap cityMap) {
		
		listPanel.setDeliveryRequests(cityMap);
		
	}
	
	/**
	 * Updates the ListPanel with a new list of tours.
	 *
	 * @param listTours The new list of tours.
	 */
	public void newListTours(LinkedHashMap<Courier, Tour> listTours) {
		
		listPanel.setListTours(listTours);
		
	}

}
