package controller;

import java.awt.Point;
import java.util.Map;

import javax.swing.JOptionPane;

import model.CityMap;
import model.Courier;
import model.Tour;
import parser.ExceptionXML;

/**
 * This class represents the state when the city map is being loaded.
 * @author H4114
 */
public class StateLoadCityMap extends DefaultState {
	
	@Override
	public void loadCityMap(Controller controller, String path) {
		
		CityMap newCityMap = new CityMap();
		
		try {
			
			controller.deserializerXML.loadCityMap(newCityMap, path);
			controller.cityMap = newCityMap;
			
			if(!controller.listTours.isEmpty()) {
				
				for (Map.Entry<Courier, Tour> tourEntry : controller.listTours.entrySet()) {
					 
				    Tour tour = tourEntry.getValue();
					tour.reset();

				}	
				
			}
			
			controller.window.loadCityMap(controller.cityMap);
			
		} catch (ExceptionXML e) {
			
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
		
	}
	
}
