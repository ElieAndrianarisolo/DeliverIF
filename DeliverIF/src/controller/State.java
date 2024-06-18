package controller;

import java.awt.Point;

/**
 * This interface defines the various states and their behaviors for the controller.
 * @author H4114
 */
public interface State {

	/**
	 * Loads a city map from a file.
	 * 
	 * @param controller The controller handling the application.
	 * @param path       The file path for the city map.
	 */
	public void loadCityMap(Controller controller, String path);
	
	/**
	 * Loads delivery requests from a file.
	 * 
	 * @param controller The controller handling the application.
	 * @param path       The file path for the delivery requests.
	 */
	public void loadDeliveryRequests(Controller controller, String path);
	
	/**
	 * Creates a new delivery request.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void createDeliveryRequest(Controller controller);
	
	/**
	 * Validates a created delivery request.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void validateCreatedDeliveryRequest(Controller controller);
	
	/**
	 * Removes the selected delivery request.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void removeDeliveryRequestFromListDeliveryRequests(Controller controller);
	
	/**
	 * Saves delivery requests to a file.
	 * 
	 * @param controller The controller handling the application.
	 * @param filePath   The path to the file where the delivery requests should be saved.
	 */
	public void saveDeliveryRequestToFile(Controller controller, String filePath);
	
	/**
	 * Goes back to the main menu of the delivery requests
	 *
	 * @param controller The controller handling the application.
	 */
	public void goBackDeliveryRequestsHome(Controller controller);
	
	/**
	 * Initiates a tour.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void initializeTour(Controller controller);
	
	/**
	 * Adds a delivery request to a tour.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void addDeliveryRequestToTour(Controller controller);
	
	/**
	 * Removes a delivery request from a tour.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void removeDeliveryRequestFromTour(Controller controller);
	
	/**
	 * Manages a delivery request.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void manageDeliveryRequests(Controller controller);
	
	/**
	 * Computes the best tour possible.
	 * 
	 * @param controller The controller handling the application.
	 * @param previousState The previous state of the controller.
	 */
	public void computeBestTour(Controller controller, State previousState); 
	
	/**
	 * Displays the tour corresponding to the selected courier.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void displayTourCorrespondingToCourier(Controller controller);
	
	/**
	 * Initiates the management of the couriers.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void manageCouriers(Controller controller);
	
	/**
	 * Creates a new courier.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void createCourier(Controller controller, String newName);

	/**
	 * Removes a courier.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void removeCourier(Controller controller);
	
	/**
	 * Adds a delivery request to a tour that has already been computed at least once.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void addDeliveryRequestToComputedTour(Controller controller);
	
	/**
	 * Removes a delivery request from a tour that has already been computed at least once.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void removeDeliveryRequestToComputedTour(Controller controller);
	
	/**
	 * Generates a road map to file.
	 * 
	 * @param controller The controller handling the application.
	 * @param filePath   The path to the file where the road map should be saved.
	 */
	public void generateRoadMap(Controller controller, String filePath);
	
	/**
	 * Undoes a command.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void undo(Controller controller);
	
	/**
	 * Redoes a command.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void redo(Controller controller);
	
	/**
	 * Handles mouse dragging events.
	 * 
	 * @param controller The controller handling the application.
	 * @param delta      The change in mouse position.
	 */
	public void mouseDrag(Controller controller, Point delta);
	
	/**
	 * Handles mouse wheel events.
	 * 
	 * @param controller The controller handling the application.
	 * @param steps      The number of steps the mouse wheel has moved.
	 * @param p          The current mouse position.
	 */
	public void mouseWheel(Controller controller, int steps, Point p);
	
	/**
	 * Handles left-click events.
	 * 
	 * @param controller The controller handling the application.
	 * @param p          The current mouse position.
	 */
	public void leftClick(Controller controller, Point p);
	
	/**
	 * Modifies the city map according to the selected delivery request in the textual view.
	 * 
	 * @param controller The controller handling the application.
	 */
	public void selectItemTextualList(Controller controller);

}
