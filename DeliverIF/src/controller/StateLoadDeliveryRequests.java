package controller;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.*;
import parser.ExceptionXML;

/**
 * This class represents the state when the delivery requests are being loaded.
 * @author H4114
 */
public class StateLoadDeliveryRequests extends DefaultState {
	
	@Override
	public void loadDeliveryRequests(Controller controller, String path) {

		try {
			
			controller.deserializerXML.loadDeliveryRequests(controller.cityMap, path);
			
			if(!controller.listTours.isEmpty()) {
				
				for (Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
					 
				    Tour tour = tourEntry.getValue();
				    tour.reset();
				    
				}	
				
			}
				
			controller.window.loadDeliveryRequests(controller.cityMap);
			controller.cityMap.selectDeliveryRequest(null);
			
		} catch (ExceptionXML e) {
			
			JOptionPane.showMessageDialog(controller.window, e.getMessage(), "Error during the deserialization", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	@Override
	public void removeDeliveryRequestFromListDeliveryRequests(Controller controller) {
		
		Intersection selectedIntersection = controller.cityMap.getSelectedIntersection();
		Intersection deliveryRequestToDelete = null;
		int position;
		
		if (!controller.cityMap.getListDeliveryRequests().isEmpty()){
			
			if(selectedIntersection != null) {
								
				for(position = 0; position < controller.cityMap.getListDeliveryRequests().size(); position++) {
					
					if (selectedIntersection.getIdIntersection().equals(controller.cityMap.getListDeliveryRequests().get(position).getIdIntersection())) {
						
						deliveryRequestToDelete = controller.cityMap.getListDeliveryRequests().get(position);
						break;
						
					}
					
				}
					
				if(deliveryRequestToDelete != null) {

					controller.cityMap.removeDeliveryRequest(position);
					controller.cityMap.selectDeliveryRequest(null);
					
					if(!controller.cityMap.getListDeliveryRequests().isEmpty()) {
						
						controller.window.setInitializeTourMode();
						
					} else {
						
						controller.window.setLoadDeliveryRequestsMode();
						
					}
					
				} else {
					
					JOptionPane.showMessageDialog(controller.window, "This delivery request does not exist", "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
				
			} else {
				
				JOptionPane.showMessageDialog(controller.window, "No delivery request has been selected", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		
		} else {
			
			JOptionPane.showMessageDialog(controller.window, "No delivery request has been loaded/created", "Error", JOptionPane.ERROR_MESSAGE);
			
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
