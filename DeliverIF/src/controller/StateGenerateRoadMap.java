package controller;

import java.awt.Point;
import java.io.IOException;
import java.util.Map;

import javax.swing.JOptionPane;

import model.*;
import parser.ExceptionHTML;

/**
 * This class represents the state when we generate a road map.
 * @author H4114
 */
public class StateGenerateRoadMap extends DefaultState {
	
	@Override
	public void generateRoadMap(Controller controller, String filePath) {
		
		try {
			
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
					
					controller.window.displayTourCorrespondingToCourier(new Courier(-1, "All"));
					controller.window.setSelectedIndexCourierDisplayTour();
					controller.deserializerHTML.createRoadMap(filePath, controller.listTours, controller.window.getLeftPanel());
					controller.setCurrentState(controller.stateComputeBestTour);
					
				} else {
					
					JOptionPane.showMessageDialog(controller.window, "All the tours are empty" , "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
			} else {
				
				JOptionPane.showMessageDialog(controller.window, "There are no tours" , "Error", JOptionPane.ERROR_MESSAGE);
				
			}

		} catch (IOException | ExceptionHTML e) {

			JOptionPane.showMessageDialog(controller.window, e.getMessage(), "Error during the deserialization", JOptionPane.ERROR_MESSAGE);
			
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
		
	}
	
}