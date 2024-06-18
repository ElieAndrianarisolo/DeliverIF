package view;

import controller.Controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CourierDisplayTourListListener implements ItemListener {
    
	private Controller controller;
	
	/**
	 * Constructs a new ListListener.
	 */
    public CourierDisplayTourListListener() {
    }
    
    /**
	 * Constructs a MouseListeners object with the specified controller.
	 *
	 * @param controller The controller responsible for handling mouse events.
	 */
    public CourierDisplayTourListListener(Controller controller) {
        
		this.controller = controller;
    	
    }
	
	@Override
    public void itemStateChanged(ItemEvent e) {
		
        if (e.getStateChange() == ItemEvent.SELECTED) {
        	
        	controller.displayTourCorrespondingToCourier();
        	
        }
        
    }
	
}