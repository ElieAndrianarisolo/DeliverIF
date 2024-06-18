package view;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Controller;

/**
 * ListListener is responsible for handling list selection events and notifying the controller.
 * @author H4114
 */
public class ListListener implements ListSelectionListener{
	
	private Controller controller;
	
	/**
	 * Constructs a new ListListener.
	 */
    public ListListener() {
    }

    /**
	 * Constructs a new ListListener with the specified controller.
	 *
	 * @param controller The controller responsible for handling user interactions.
	 */
    public ListListener(Controller controller) {

    	this.controller = controller;

    }
    
    /**
	 * Handles list selection events and notifies the controller.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		controller.modifyList();

	}

}