package controller;

import java.awt.Point;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.*;
import parser.ExceptionXML;

/**
 * This class represents the state for creating, removing and saving delivery requests.
 * @author H4114
 */
public class StateManageDeliveryRequests extends DefaultState {
		
	@Override
	public void manageDeliveryRequests(Controller controller) {
		
		controller.cityMap.selectDeliveryRequest(null);
		controller.window.loadDeliveryRequests(controller.cityMap);
		
	}
	
	@Override
	public void createDeliveryRequest(Controller controller) {
		
		controller.cityMap.selectDeliveryRequest(null);
		controller.window.createDeliveryRequest(controller.cityMap);
		
	}
	
	@Override
	public void validateCreatedDeliveryRequest(Controller controller) {
		
		int timeWindow = controller.window.getSelectedTimeWindow();
		
		if(timeWindow != 0) {
			
			Intersection selectedIntersection = controller.cityMap.getSelectedIntersection();

			if(selectedIntersection != null) {
				
				for (Intersection deliveryRequest : controller.cityMap.getListDeliveryRequests()) {
					
					if (selectedIntersection.getIdIntersection().equals(deliveryRequest.getIdIntersection())) {
						
						selectedIntersection = null;
						break;
						
					}
					
				}

				if(selectedIntersection != null) {
					
					int isWarehouse = controller.window.getSelectedIsWarehouse();
					
					if(isWarehouse == 1) {
						
						if(controller.cityMap.getListDeliveryRequests().isEmpty() || !(controller.cityMap.getListDeliveryRequests().get(0) instanceof Warehouse)) {
							
							try {
								
								controller.cityMap.addWarehouse(selectedIntersection.getIdIntersection(), selectedIntersection.getLatitude(), selectedIntersection.getLongitude());
								controller.cityMap.selectDeliveryRequest(null);
								
							} catch (ExceptionXML e) {

								JOptionPane.showMessageDialog(controller.window, "Error during the creation of the warehouse", "Error", JOptionPane.ERROR_MESSAGE);

							}
							
						} else {
							
							JOptionPane.showMessageDialog(controller.window, "There is already a warehouse", "Error", JOptionPane.ERROR_MESSAGE);
							
						}
						
					} else if(isWarehouse == 2) {
						
						try {
												
							controller.cityMap.addDeliveryRequest(selectedIntersection.getIdIntersection(), selectedIntersection.getLatitude(), selectedIntersection.getLongitude(), timeWindow);								
							controller.cityMap.selectDeliveryRequest(null);

						} catch (ExceptionXML e) {

							JOptionPane.showMessageDialog(controller.window, "Error during the creation of the delivery request", "Error", JOptionPane.ERROR_MESSAGE);
							
						}
						
					} else {
						
						JOptionPane.showMessageDialog(controller.window, "You need to specify if it is a warehouse or not", "Error", JOptionPane.ERROR_MESSAGE);
						
					}
					
				} else {
					
					JOptionPane.showMessageDialog(controller.window, "The intersection already exists", "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
			} else {
				
				JOptionPane.showMessageDialog(controller.window, "No intersection has been selected", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
			
		} else {
			
			JOptionPane.showMessageDialog(controller.window, "The time-window has not been selected", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	@Override
	public void removeDeliveryRequestFromListDeliveryRequests(Controller controller) {
		
		if(!controller.cityMap.getListDeliveryRequests().isEmpty()) {
			
			controller.cityMap.resetListDeliveryRequests();
			controller.cityMap.selectDeliveryRequest(null);
			controller.window.setLoadDeliveryRequestsMode();
			
		} else {
			
			JOptionPane.showMessageDialog(controller.window, "No delivery requests have been created", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	@Override
	public void saveDeliveryRequestToFile(Controller controller, String filePath) {
		
		if(!controller.cityMap.getListDeliveryRequests().isEmpty()){
			
			try {
				
				controller.serializer.saveDeliveryRequestsToFile(filePath, controller.cityMap.getListDeliveryRequests());
				
			} catch (TransformerException | ParserConfigurationException e) {

				JOptionPane.showMessageDialog(controller.window, "Error during the serialization", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
			
		} else {
			
			JOptionPane.showMessageDialog(controller.window, "No delivery requests have been loaded/created", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}

	@Override
	public void goBackDeliveryRequestsHome(Controller controller) {
		
		if(!controller.cityMap.getListDeliveryRequests().isEmpty()) {
			
			controller.window.setInitializeTourMode();
			
		} else {
			
			controller.window.setLoadDeliveryRequestsMode();
			
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
