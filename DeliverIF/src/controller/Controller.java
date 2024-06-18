package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;					  
import javax.swing.JOptionPane;

import view.*;
import model.*;
import parser.*;

/**
 * The Controller class manages the application's logic.
 */
public class Controller {
	
	protected XMLdeserializer deserializerXML;
	protected XMLserializer serializer;
	protected HTMLdeserializer deserializerHTML;
	protected Window window;
	protected CityMap cityMap;
	protected List<Courier> listCouriers;
	protected LinkedHashMap<Courier, Tour> listTours;
	protected CommandList listCommands;
	
	protected State currentState;
	protected StateLoadCityMap stateLoadCityMap = new StateLoadCityMap();
	protected StateLoadDeliveryRequests stateLoadDeliveryRequests = new StateLoadDeliveryRequests();
	protected StateManageDeliveryRequests stateManageDeliveryRequests = new StateManageDeliveryRequests();
	protected StateInitializeTour stateInitializeTour = new StateInitializeTour();
	protected StateComputeBestTour stateComputeBestTour = new StateComputeBestTour();
	protected StateCourierManagement stateCourierManagement = new StateCourierManagement();
	protected StateGenerateRoadMap stateGenerateRoadMap = new StateGenerateRoadMap();
	protected StateAddDeliveryRequestToComputedTour stateAddDeliveryRequestToComputedTour = new StateAddDeliveryRequestToComputedTour();
	protected StateRemoveDeliveryRequestToComputedTour stateRemoveDeliveryRequestToComputedTour = new StateRemoveDeliveryRequestToComputedTour();
	
	/**
	 * Initializes the Controller and sets up the necessary components.
	 */
	public Controller() {
		
		cityMap = null;

		try {
			
			deserializerXML = new XMLdeserializer();
			deserializerHTML = new HTMLdeserializer();
			serializer = new XMLserializer();
			
			listCommands = new CommandList();
			
			listCouriers = new ArrayList<Courier>();
			listTours = new LinkedHashMap<Courier, Tour>();
			
			window = new Window(this, cityMap, listTours);
			
		} catch (ExceptionXML e) {
			
			JOptionPane.showMessageDialog(window, e.getMessage(), "Error during the deserialization", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	/**
	 * Launches the application.
	 */
	public void launch() {
		
		setCurrentState(stateLoadCityMap);
		window.setLoadCityMapMode();
		
	}
	
	/**
	 * Sets the current state of the application.
	 *
	 * @param state The state to set as the current state.
	 */
	protected void setCurrentState(State state) {
		
		currentState = state;
		
	}
	
	/**
	 * Gets the current state of the application.
	 *
	 * @return currentState The current state of the controller
	 */
	public State getCurrentState() {
		
		return currentState;
		
	}
	
	/**
	 * Loads a city map from the specified file path.
	 *
	 * @param path The file path to load the city map from.
	 */
	public void loadCityMap(String path) {
		
		setCurrentState(stateLoadCityMap);
		currentState.loadCityMap(this, path);
		
	}
	
	/**
	 * Loads delivery requests from the specified file path.
	 *
	 * @param path The file path to load delivery requests from.
	 */
	public void loadDeliveryRequests(String path) {
		
		setCurrentState(stateLoadDeliveryRequests);
		currentState.loadDeliveryRequests(this, path);
		
	}
	
	/**
	 * Removes the selected delivery request.
	 */
	public void removeDeliveryRequestFromListDeliveryRequests() {
		
		setCurrentState(stateLoadDeliveryRequests);
		currentState.removeDeliveryRequestFromListDeliveryRequests(this);
		
	}
	
	/**
	 * Saves delivery requests to a file with the specified file path.
	 *
	 * @param filePath The file path to save delivery requests to.
	 */
	public void saveDeliveryRequestToFile(String filePath) {
		
		setCurrentState(stateLoadDeliveryRequests);
		currentState.saveDeliveryRequestToFile(this, filePath);
		
	}
	
	/**
	 * Manages the loaded and created delivery requests.
	 */
	public void manageDeliveryRequests() {
		
		setCurrentState(stateManageDeliveryRequests);
		currentState.manageDeliveryRequests(this);
		
	}
	
	/**
	 * Initiates the creation of a delivery request.
	 */
	public void createDeliveryRequest() {
		
		setCurrentState(stateManageDeliveryRequests);
		currentState.createDeliveryRequest(this);
		
	}
	
	/**
	 * Validates the created delivery request.
	 */
	public void validateCreatedDeliveryRequest() {
		
		setCurrentState(stateManageDeliveryRequests);
		currentState.validateCreatedDeliveryRequest(this);
		
	}
	
	/**
	 * Goes back to the main menu of the delivery requests
	 *
	 */
	public void goBackDeliveryRequestsHome() {
		
		setCurrentState(stateManageDeliveryRequests);
		currentState.goBackDeliveryRequestsHome(this);
		
	}
	
	/**
	 * Initiates a tour.
	 */
	public void initializeTour() {
		
		setCurrentState(stateInitializeTour);
		currentState.initializeTour(this);
		
	}
	
	/**
	 * Adds a delivery request to a tour.
	 */
	public void addDeliveryRequestToTour() {
		
		setCurrentState(stateInitializeTour);
		currentState.addDeliveryRequestToTour(this);
		
	}
	
	/**
	 * Removes a delivery request from a tour.
	 */
	public void removeDeliveryRequestFromTour() {
		
		setCurrentState(stateInitializeTour);
		currentState.removeDeliveryRequestFromTour(this);
		
	}
	
	/**
	 * Computes the best tour possible.
	 */
	public void computeBestTour() {
		
		State previousState = currentState;
		setCurrentState(stateComputeBestTour);
		currentState.computeBestTour(this, previousState);
		
	}
	
	/**
	 * Display the best tour possible.
	 */
	public void displayTourCorrespondingToCourier() {
		
		currentState.displayTourCorrespondingToCourier(this);
		
	}
	
	/**
	 * Generates a road map
	 */
	public void generateRoadMap(String filePath) {
		
		setCurrentState(stateGenerateRoadMap);
		currentState.generateRoadMap(this, filePath);
		
	}
	
	/**
	 * Adds a delivery request to a tour that has already been computed at least once.
	 */
	public void addDeliveryRequestToComputedTour() {
		
		setCurrentState(stateComputeBestTour);
		currentState.addDeliveryRequestToComputedTour(this);
		
	}
	
	/**
	 * Removes a delivery request from a tour that has already been computed at least once.
	 */
	public void removeDeliveryRequestToComputedTour() {
		
		setCurrentState(stateComputeBestTour);
		currentState.removeDeliveryRequestToComputedTour(this);
		
	}
	
	/**
	 * Creates a courier.
	 * 
	 * @param courierName The courier's name.
	 */
	public void createCourier(String courierName) {
		
		setCurrentState(stateCourierManagement);
		currentState.createCourier(this, courierName);
		
	}
	
	/**
	 * Removes a courier.
	 */
	public void removeCourier() {
		
		setCurrentState(stateCourierManagement);
		currentState.removeCourier(this);
		
	}
	
	/**
	 * Manages couriers.
	 */
	public void manageCouriers() {
		
		setCurrentState(stateCourierManagement);
		currentState.manageCouriers(this);
		
	}
	
	/**
	 * Undoes a command.
	 */
	public void undo() {
		
		currentState.undo(this);
		
	}
	
	/**
	 * Redoes a command.
	 */
	public void redo() {
		
		currentState.redo(this);
		
	}
	
	/**
	 * Handles dragging events.
	 *
	 * @param delta The change in position.
	 */
	public void mouseDrag(Point delta) {
		
		currentState.mouseDrag(this, delta);
		
	}
	
	/**
	 * Handles mouse wheel events.
	 *
	 * @param steps The number of steps scrolled.
	 * @param p     The point at which the wheel was scrolled.
	 */
	public void mouseWheel(int steps, Point p) {
		
		currentState.mouseWheel(this, steps, p);
		
	}
	
	/**
	 * Handles left-click events.
	 *
	 * @param p The point where the left-click occurred.
	 */
	public void leftClick(Point p) {
		
		currentState.leftClick(this, p);
		
	}
	
	/**
	 * Modifies the list view.
	 */
	public void modifyList() {
	
		currentState.selectItemTextualList(this);
		
	}
	
	/***
	 * ONLY USED ON TESTSING
	 */
	public CityMap getCityMap(){
		return cityMap;
	}

	/***
	 * ONLY USED ON TESTSING
	 */
    public Window getWindow() {
        return window;
    }

	/***
	 * ONLY USED ON TESTSING
	 */
	public void setCityMap(CityMap map) {
		cityMap= map;
	}

	/***
	 * ONLY USED ON TESTSING
	 */
	public LinkedHashMap<Courier,Tour> getListTour() {
		return listTours;
	}

	/***
	 * ONLY USED ON TESTSING
	 */
	public void setTour(Courier indexCourier,Tour newTour) {
		listTours.replace(indexCourier, newTour);
	}

	/***
	 * ONLY USED FOR TESTING
	 */
	public void setWindow(Window window2) {
		window = window2;
	}


}
