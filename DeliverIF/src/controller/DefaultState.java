package controller;

import java.awt.Point;

/**
 * This class provides default implementations for the State interface methods.
 * @author H4114
 */
public class DefaultState implements State{
	
	@Override
	public void loadCityMap(Controller controller, String path) {
		// Default implementation
	}

	@Override
	public void loadDeliveryRequests(Controller controller, String path) {
		// Default implementation
	}

	@Override
	public void createDeliveryRequest(Controller controller) {
		// Default implementation
	}

	@Override
	public void validateCreatedDeliveryRequest(Controller controller) {
		// Default implementation
	}

	@Override
	public void removeDeliveryRequestFromListDeliveryRequests(Controller controller) {
		// Default implementation
	}

	@Override
	public void saveDeliveryRequestToFile(Controller controller, String filePath) {
		// Default implementation
	}

	@Override
	public void goBackDeliveryRequestsHome(Controller controller) {
		// Default implementation
	}

	@Override
	public void initializeTour(Controller controller) {
		// Default implementation
	}

	@Override
	public void addDeliveryRequestToTour(Controller controller) {
		// Default implementation
	}

	@Override
	public void removeDeliveryRequestFromTour(Controller controller) {
		// Default implementation
	}

	@Override
	public void computeBestTour(Controller controller, State previousState) {
		// Default implementation
	}
	
	@Override
	public void displayTourCorrespondingToCourier(Controller controller) {
		// Default implementation
	}

	@Override
	public void generateRoadMap(Controller controller, String filePath) {
		// Default implementation
	}

	@Override
	public void addDeliveryRequestToComputedTour(Controller controller) {
		// Default implementation
	}

	@Override
	public void removeDeliveryRequestToComputedTour(Controller controller) {
		// Default implementation
	}

	@Override
	public void createCourier(Controller controller, String newName) {
		// Default implementation
	}

	@Override
	public void removeCourier(Controller controller) {
		// Default implementation
	}	

	@Override
	public void manageCouriers(Controller controller) {
		// Default implementation
	}

	@Override
	public void undo(Controller controller) {
		// Default implementation
	}

	@Override
	public void redo(Controller controller) {
		// Default implementation
	}
	
	@Override
	public void mouseDrag(Controller controller, Point p) {
		// Default implementation
	}
	
	@Override
	public void mouseWheel(Controller controller, int steps, Point p) {
		// Default implementation
	}
	
	@Override
	public void leftClick(Controller controller, Point p) {
		// Default implementation
	}
	
	@Override
	public void selectItemTextualList(Controller controller) {
		// Default implementation
	}
	
	@Override
	public void manageDeliveryRequests(Controller controller) {
		// TODO Auto-generated method stub
	}
	
}
