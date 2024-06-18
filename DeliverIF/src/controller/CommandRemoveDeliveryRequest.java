package controller;

import javax.swing.JOptionPane;

import model.CityMap;
import model.DeliveryRequest;
import model.Tour;
import parser.ExceptionXML;

/**
 * This class represents the command to remove a delivery request from a tour.
 * @author H4114
 */
public class CommandRemoveDeliveryRequest implements Command {
	
	private CityMap cityMap;
	private DeliveryRequest deliveryRequest;
	private Tour tour;
	
	/**
	 * Creates a command allowing to remove a delivery request to a tour 
	 * for which the best path has already been computed at least once.
	 * 
	 * @param deliveryRequest	The delivery request to remove.
	 * @param tour				The current tour.
	 * @param cityMap			The city map.
	 */
	public CommandRemoveDeliveryRequest(DeliveryRequest deliveryRequest, Tour tour, CityMap cityMap) {
		
		this.cityMap = cityMap;
		this.deliveryRequest = deliveryRequest;
		this.tour = tour;
		
	}
	
	@Override
	public void doCommand() {
		
		for(int i = 0; i < tour.getListDeliveryRequests().size(); i++) {
			
			if(deliveryRequest.getIdIntersection().equals(tour.getListDeliveryRequests().get(i).getIdIntersection())) {
				
				tour.removeDeliveryRequest(i); 
				break;
				
			}

		}
		
		cityMap.removeDeliveryRequest(cityMap.getListDeliveryRequests().size()-1);
		
		if(!tour.getListDeliveryRequests().isEmpty()) {
			
			tour.computeBestPath(cityMap);
			
		} else {
			
			tour.clearListRoadSegments();
			
		}
		
	}

	@Override
	public void undoCommand() {
		
		try {
			
			tour.addDeliveryRequest(deliveryRequest);
			cityMap.addDeliveryRequest(deliveryRequest.getIdIntersection(), deliveryRequest.getLatitude(), deliveryRequest.getLongitude(), deliveryRequest.getTimeWindow());
			
		} catch (ExceptionXML e) {
			
			JOptionPane.showMessageDialog(null, "Error adding the delivery request", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
		tour.computeBestPath(cityMap);
		
	}
	
}
