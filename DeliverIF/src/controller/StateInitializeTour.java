package controller;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import model.Courier;
import model.DeliveryRequest;
import model.Intersection;
import model.Tour;
import model.Warehouse;

/**
 * This class represents the state when we assign a delivery request to a tour.
 * @author H4114
 */
public class StateInitializeTour extends DefaultState {
	
	private int idTour = 0;
	
	@Override
	public void initializeTour(Controller controller) {
		
		if(!controller.listCouriers.isEmpty()) {
		
			if(controller.cityMap.getListDeliveryRequests().size() > 1 && controller.cityMap.getListDeliveryRequests().get(0) instanceof Warehouse) {
				
				 for (Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
					 
					    Tour tour = tourEntry.getValue();
					    
					    if(tour.getWarehouse() == null) {
					    	
					    	idTour++;
						    tour = new Tour(idTour, (Warehouse) controller.cityMap.getListDeliveryRequests().get(0));
					    	
					    } 
					    
					    tourEntry.setValue(tour);
					 
				 }

				controller.cityMap.selectDeliveryRequest(null);
				controller.window.setIndexSelectedCourier(0);
				controller.window.initializeListTours(controller.listTours);
				
			} else {
				
				JOptionPane.showMessageDialog(controller.window, "The delivery requests are not well initialized", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		
		} else {
			
			JOptionPane.showMessageDialog(controller.window, "Create at least one courier in the 'Manage Couriers' section", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	@Override
	public void addDeliveryRequestToTour(Controller controller) {
			
		Intersection selectedIntersection = controller.cityMap.getSelectedIntersection();
		DeliveryRequest newDeliveryRequest = null;
		int indexSelectedCourier = controller.window.getIndexSelectedCourier();
		Courier courier = null;
		
		if(indexSelectedCourier != -1) {
			
			if(indexSelectedCourier >= 0 && indexSelectedCourier < controller.listCouriers.size()) {
				
				courier = controller.listCouriers.get(indexSelectedCourier);
				
				if(courier != null) {
					
					if(selectedIntersection != null) {
						
						for (Intersection deliveryRequest : controller.cityMap.getListDeliveryRequests()) {
							
							if (selectedIntersection.getIdIntersection().equals(deliveryRequest.getIdIntersection())) {
								
								if(deliveryRequest instanceof DeliveryRequest) {
									
									newDeliveryRequest = (DeliveryRequest)deliveryRequest;
									break;
									
								}
								
							}
							
						}
						
						if(newDeliveryRequest != null) {
							
							for(Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
								
								Tour tour = tourEntry.getValue();
								
								for (DeliveryRequest deliveryRequest : tour.getListDeliveryRequests()) {
									
									if (newDeliveryRequest.getIdIntersection().equals(deliveryRequest.getIdIntersection())) {
										
										newDeliveryRequest = null;
										break;
										
									}
									
								}
								
							}

							if(newDeliveryRequest != null) {
								
								controller.listTours.get(courier).addDeliveryRequest(newDeliveryRequest);
								controller.listTours.get(courier).setCourier(courier);
								
								controller.cityMap.selectDeliveryRequest(null);
								controller.window.setComputeBestTourMode();
								
							} else {
								
								JOptionPane.showMessageDialog(controller.window, "This delivery request has already been added to a tour", "Error", JOptionPane.ERROR_MESSAGE);
								
							}
							
						} else {
							
							JOptionPane.showMessageDialog(controller.window, "The selected intersection is not a delivery request", "Error", JOptionPane.ERROR_MESSAGE);
							
						}
						
					} else {
						
						JOptionPane.showMessageDialog(controller.window, "No delivery request has been selected", "Error", JOptionPane.ERROR_MESSAGE);
						
					}
				
				} else {
					
					JOptionPane.showMessageDialog(controller.window, "The selected courier is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
			} else {
				
				JOptionPane.showMessageDialog(controller.window, "The selected courier is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
			
		} else {
			
			JOptionPane.showMessageDialog(controller.window, "No courier has been selected", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
				
	}
	
	@Override
	public void removeDeliveryRequestFromTour(Controller controller) {
		
		Intersection selectedIntersection = controller.cityMap.getSelectedIntersection();
		DeliveryRequest deliveryRequestToDelete = null;
		int position = 0;
		Tour tour;
		Courier courier = null;
		boolean isToDelete = false;
		
		if(selectedIntersection != null && !controller.listTours.isEmpty()) {
			
			for(Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
				
				courier = tourEntry.getKey();
				tour = tourEntry.getValue();
				
				for (position = 0; position < tour.getListDeliveryRequests().size(); position++) {
					
					if (selectedIntersection.getIdIntersection().equals(tour.getListDeliveryRequests().get(position).getIdIntersection())) {
						
						deliveryRequestToDelete = tour.getListDeliveryRequests().get(position);
						isToDelete = true;
						break;
						
					}
					
				}
				
				if(isToDelete == true) {
					
					break;
					
				}
				
			}
								
			if(deliveryRequestToDelete != null) {
				
				controller.listTours.get(courier).getListDeliveryRequests().get(position).setDepartureTime(null);
				controller.listTours.get(courier).getListDeliveryRequests().get(position).setArrivalTime(null);
				controller.listTours.get(courier).removeDeliveryRequest(position);
				controller.cityMap.selectDeliveryRequest(null);
				controller.window.setComputeBestTourMode();
				
			} else {
				
				JOptionPane.showMessageDialog(controller.window, "This delivery request does not already exist", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
							
							
		} else {
			
			JOptionPane.showMessageDialog(controller.window, "No delivery request has been selected", "Error", JOptionPane.ERROR_MESSAGE);
			
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
	
	@Override
	public void leftClick(Controller controller, Point p) {
		
		controller.cityMap.getAtPoint(p, controller.window.getGraphicalView().getMapPanel().getFocus(), controller.window.getGraphicalView().getMapPanel().getCoefX(), controller.window.getGraphicalView().getMapPanel().getCoefY(), controller.window.getGraphicalView().getMapPanel().getSideLength());
		controller.window.displaySelectedRoadSegment(controller.cityMap.getSelectedRoadSegment());
		controller.window.getTextualView().getListPanel().clearSelection();
		
		Intersection selectedPoint = controller.cityMap.getSelectedIntersection();

		List<Intersection> listDeliveryRequests = controller.cityMap.getListDeliveryRequests();
		
		if (!controller.cityMap.getListDeliveryRequests().isEmpty() && selectedPoint != null){
			
			for (int i = 0; i < listDeliveryRequests.size(); i++) {
				
				Intersection deliveryRequest = listDeliveryRequests.get(i);
				
				if(deliveryRequest instanceof Warehouse) {

					if (deliveryRequest.getIdIntersection().equals(selectedPoint.getIdIntersection())) {

						controller.window.getTextualView().getListPanel().setSelectedIndex(0);
						break;
						
					}

				} else {
					
					if (deliveryRequest.getIdIntersection().equals(selectedPoint.getIdIntersection())) {
						
						controller.window.getTextualView().getListPanel().setSelectedIndex(i);
						break;
						
					}
					
				}	
				
			}
			
		}
		
	}
	
	@Override
	public void selectItemTextualList(Controller controller) {

		int index = controller.window.getTextualView().getListPanel().getCurrentSelectionListDeliveryRequests();
		
		List<Intersection> listDeliveryRequests = controller.cityMap.getListDeliveryRequests();
		
		if (index > 0 && index <= listDeliveryRequests.size() && listDeliveryRequests.get(index) instanceof DeliveryRequest) {
			
			controller.cityMap.selectDeliveryRequest((DeliveryRequest)listDeliveryRequests.get(index));
			
		} else if (index == 0 && listDeliveryRequests.get(index) instanceof Warehouse) {
			
			controller.cityMap.selectWarehouse((Warehouse)listDeliveryRequests.get(index));
			
		}
		
	}
	
}

