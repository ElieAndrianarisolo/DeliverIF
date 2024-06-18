package controller;

import java.awt.Point;
import java.util.Map;

import javax.swing.JOptionPane;

import model.*;

/**
 * This class represents the state when we compute the best tour.
 * @author H4114
 */
public class StateComputeBestTour extends DefaultState {

	@Override
	public void computeBestTour(Controller controller, State previousState) {
		
		if(!(previousState instanceof StateComputeBestTour)) {
			
			if(!controller.listTours.isEmpty()) {
				
				int nbEmptyTours = 0;
				Tour tour; 
				Courier courier;
				
				for (Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
					 
				    tour = tourEntry.getValue();
				    courier = tourEntry.getKey();
				    
				    if(tour.getWarehouse() == null) {
				    	
					    tour.setWarehouse((Warehouse) controller.cityMap.getListDeliveryRequests().get(0));
				    	
				    } 
				    
				    if(tour.getCourier() == null) {
				    	
				    	tour.setCourier(courier);
				    	
				    }
				    
				    if(tour.getListDeliveryRequests().isEmpty()) {
				    	
				    	nbEmptyTours++;
				    	
				    } 
				    
				}
				
				if(nbEmptyTours != controller.listTours.size()) {
					
					controller.cityMap.selectDeliveryRequest(null);
					
					for (Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
						 
					    tour = tourEntry.getValue();
					    
					    if(!tour.getListDeliveryRequests().isEmpty()) {
					    	
					    	tour.computeBestPath(controller.cityMap);
					    	
					    }
					    
					   
					}
					
					 controller.window.computeBestTour(controller.listTours);
					 
				} else {
					
					JOptionPane.showMessageDialog(controller.window, "The tours are not well initialized", "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
			} else {
				
				JOptionPane.showMessageDialog(controller.window, "The tours are not well initialized", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
			
		}
	
	}

	@Override
	public void addDeliveryRequestToComputedTour(Controller controller) {
		
		controller.cityMap.selectDeliveryRequest(null);
		controller.window.setModifyTourMode("AddDeliveryRequest");
		controller.window.setIndicationLabel("<html><font size='6'>Select the delivery request to add</font></html>");
		controller.setCurrentState(controller.stateAddDeliveryRequestToComputedTour);
		
	}

	@Override
	public void removeDeliveryRequestToComputedTour(Controller controller) {
		
		if(!controller.listTours.isEmpty()) {
			
			int nbEmptyTours = 0;
			Tour tour; 
			
			for (Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
				 
			    tour = tourEntry.getValue();
			    
			    if(tour.getListDeliveryRequests().isEmpty()) {
			    	
			    	nbEmptyTours++;
			    	
			    } 
			    
			}
			
			if(nbEmptyTours != controller.listTours.size()) {
				
				controller.cityMap.selectDeliveryRequest(null);
				controller.window.setModifyTourMode("RemoveDeliveryRequest");
				controller.window.setIndicationLabel("<html><font size='6'>Select the delivery request to remove</font></html>");
				controller.setCurrentState(controller.stateRemoveDeliveryRequestToComputedTour);
				
			} else {
				
				JOptionPane.showMessageDialog(controller.window, "There must be at least 1 tour with 1 delivery request", "Error", JOptionPane.ERROR_MESSAGE);
				
			}

		} else {
			
			JOptionPane.showMessageDialog(controller.window, "There must be at least 1 tour", "Error", JOptionPane.ERROR_MESSAGE);
			
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
	public void undo(Controller controller) {
		
		controller.listCommands.undo();
		controller.window.setModifyTourMode(null);
		
	}

	@Override
	public void redo(Controller controller) {
		
		controller.listCommands.redo();
		controller.window.setModifyTourMode(null);
		
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
		boolean isFinished = false;
		Tour tour;
		int sizeCompleteList = 0;
		
		if(!controller.listTours.isEmpty() && selectedPoint != null) {
			
			for (Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
				
			    tour = tourEntry.getValue();
			    
		    	if(!tour.getListDeliveryRequests().isEmpty() && tour.getWarehouse() != null) {
			    	
		    		if(selectedPoint.getIdIntersection().equals(tour.getWarehouse().getIdIntersection())) {
		    			
		    			controller.window.getTextualView().getListPanel().setSelectedIndex(0);
		    			break;
		    			
		    		}
		    		
		    		for(int position = 0; position < tour.getListDeliveryRequests().size(); position++) {
		    			
		    			if(selectedPoint.getIdIntersection().equals(tour.getListDeliveryRequests().get(position).getIdIntersection())) {
		    				
		    				controller.window.getTextualView().getListPanel().setSelectedIndex(position + sizeCompleteList + 1);
		    				isFinished = true;
							break;
		    				
		    			}
		    			
		    		}
		    		
		    		if(isFinished == true) {
		    			
		    			break;
		    			
		    		}
		    		
		    		sizeCompleteList += tour.getListDeliveryRequests().size() + 2;
		    			
			    } 
			    	
			}
			
		}
			
	}

	@Override
	public void selectItemTextualList(Controller controller) {

		int indexSelectedList = controller.window.getTextualView().getListPanel().getCurrentSelectionListDeliveryRequests();
		int born1 = 0;
		int born2 = 0;
		int newIndex;
		Tour tour;
		
		if(!controller.listTours.isEmpty()) {
			
			for (Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
				
			    tour = tourEntry.getValue();
			    
			    if(!tour.getListDeliveryRequests().isEmpty() && tour.getWarehouse() != null) {
			    	
		    		born2 = born1 + tour.getListDeliveryRequests().size() + 1;
	
			    	if(indexSelectedList == born1 || indexSelectedList == born2) {
			    		
			    		controller.cityMap.selectWarehouse(tour.getWarehouse());
			    		break;
			    		
			    	} else if(indexSelectedList > born1 && indexSelectedList < born2) {
			    		
			    		newIndex = indexSelectedList-born1-1;
			    		
			    		controller.cityMap.selectDeliveryRequest(tour.getListDeliveryRequests().get(newIndex));
			    		break;
			    		
			    	}
			    	
			    	born1 += tour.getListDeliveryRequests().size()+2;
			    	
			    } 
			    
			}
			
		}
		
	}
	
}

