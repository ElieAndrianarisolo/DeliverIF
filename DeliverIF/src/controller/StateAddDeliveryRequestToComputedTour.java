package controller;

import java.awt.Point;

import javax.swing.JOptionPane;

import model.Courier;
import model.DeliveryRequest;
import model.Intersection;
import model.Tour;

/**
 * This class represents the state when a delivery request is being added to a tour that has already been computed at least once.
 * @author H4114
 */
public class StateAddDeliveryRequestToComputedTour extends DefaultState {

	@Override
	public void undo(Controller controller) {
		
		controller.setCurrentState(controller.stateComputeBestTour);
		controller.window.setModifyTourMode(null);
		
	}

	@Override
	public void leftClick(Controller controller, Point p) {
		
		controller.cityMap.getAtPoint(p, controller.window.getGraphicalView().getMapPanel().getFocus(), controller.window.getGraphicalView().getMapPanel().getCoefX(), controller.window.getGraphicalView().getMapPanel().getCoefY(), controller.window.getGraphicalView().getMapPanel().getSideLength());
		controller.window.displaySelectedRoadSegment(controller.cityMap.getSelectedRoadSegment());
		controller.window.getTextualView().getListPanel().clearSelection();
		
		Intersection selectedPoint = controller.cityMap.getSelectedIntersection();
		int indexCourier = controller.window.getIndexSelectedCourierComputedTour();
		int timeWindowToSet = controller.window.getSelectedTimeWindowComputedTour();
		Courier courier;
		Tour tour;
		
		if(selectedPoint != null) {
			

			if(indexCourier != -1 && !controller.listCouriers.isEmpty() && indexCourier >= 0 && indexCourier < controller.listCouriers.size()) {
				
				
				
				if(timeWindowToSet != 0) {
					
					courier = controller.listCouriers.get(indexCourier);

					if(!controller.listTours.isEmpty() && courier != null) {
						
						tour = controller.listTours.get(courier);
						
						
						if(tour != null) {
							
							for (DeliveryRequest deliveryRequest : tour.getListDeliveryRequests()) {
								
								if (deliveryRequest.getIdIntersection().equals(selectedPoint.getIdIntersection())) {
									
									selectedPoint = null;
									break;
									
								}
								
							}
							
							if(selectedPoint != null) {
								
								controller.cityMap.selectDeliveryRequest(null);
			            		DeliveryRequest deliveryRequestToAdd = new DeliveryRequest(selectedPoint.getIdIntersection(), selectedPoint.getLatitude(), selectedPoint.getLongitude(), timeWindowToSet);
			            		controller.listCommands.add(new CommandAddDeliveryRequest(deliveryRequestToAdd, tour, controller.cityMap));
			            		controller.window.setModifyTourMode(null);
			            		controller.setCurrentState(controller.stateComputeBestTour);
		    	        		controller.window.setSelectedIndexCourierDisplayTour();
								
							} 
							
						} 
						
					}

				} else {
					
					controller.cityMap.selectDeliveryRequest(null);
					JOptionPane.showMessageDialog(null, "Select a time window", "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
				
			} else {
				
				controller.cityMap.selectDeliveryRequest(null);
				JOptionPane.showMessageDialog(null, "Select a courier", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		
		}
	
	}
	
	@Override
	public void displayTourCorrespondingToCourier(Controller controller) {
		
		int indexCourier = controller.window.getIndexSelectedCourierToDisplay();
		Courier courier;
		
		if(indexCourier != -1) {
			
			if(indexCourier == 0) {
	
				if(!controller.listTours.isEmpty()) {
					
					controller.window.displayTourCorrespondingToCourier(new Courier(-1, "All"));
					
				} else {
					
					JOptionPane.showMessageDialog(controller.window, "There are no tours", "Error", JOptionPane.ERROR_MESSAGE);
					
				}

			} else {
				
				if(!controller.listCouriers.isEmpty()) { 
					
					if(!controller.listTours.isEmpty()) {
						
						courier = controller.listCouriers.get(indexCourier-1);
						
						if(courier != null && !controller.listTours.get(courier).getListDeliveryRequests().isEmpty()) {
							
							controller.window.displayTourCorrespondingToCourier(courier);
							
						} else {
							
							JOptionPane.showMessageDialog(controller.window, "The tour is empty", "Error", JOptionPane.ERROR_MESSAGE);
							controller.window.setSelectedIndexCourierDisplayTour();
							
						}
						
					} else {
						
						JOptionPane.showMessageDialog(controller.window, "There are no tours", "Error", JOptionPane.ERROR_MESSAGE);
						controller.window.setSelectedIndexCourierDisplayTour();
						
					}
					
				} else {
					
					JOptionPane.showMessageDialog(controller.window, "There are couriers", "Error", JOptionPane.ERROR_MESSAGE);
					controller.window.setSelectedIndexCourierDisplayTour();
					
				}
				
			}
			
		} else {
			
			JOptionPane.showMessageDialog(controller.window, "No courier has been selected", "Error", JOptionPane.ERROR_MESSAGE);
			controller.window.setSelectedIndexCourierDisplayTour();
			
		}
		
	}
	
	
	@Override
	public void mouseDrag(Controller controller, Point delta) {
		
		controller.window.getGraphicalView().getMapPanel().drag(delta);
		
	}

	@Override
	public void mouseWheel(Controller controller, int steps, Point p) {
		
		controller.window.getGraphicalView().getMapPanel().zoom(steps, p);
		
	}
	
}
