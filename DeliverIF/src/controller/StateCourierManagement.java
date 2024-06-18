package controller;

import java.awt.Point;

import javax.swing.JOptionPane;

import model.*;

/**
 * This class represents the state for managing the courier.
 * @author H4114
 */
public class StateCourierManagement extends DefaultState {
	
	private int indexIdCourier = 1;

	@Override
	public void manageCouriers(Controller controller) {
		
		controller.window.setManageCourierMode(controller.listCouriers);

	}

	@Override
	public void createCourier(Controller controller, String newName) {
		
		boolean courierExist = false;
		
		for(Courier courier : controller.listCouriers) {
			
			if(courier.getFirstName().equals(newName)) {
				
				courierExist = true;
				break;
				
			}
			
		}
		
		if(courierExist == false) {
			
			Courier e = new Courier(indexIdCourier, newName);
			indexIdCourier++;
			
			controller.listTours.put(e, new Tour());
			
			controller.listCouriers.add(e) ;
			controller.window.updateCouriers(controller.listCouriers);
			
		} else {
			
			JOptionPane.showMessageDialog(controller.window, "The courier already exists", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}

	@Override
	public void removeCourier(Controller controller) {
		
		int index = controller.window.getTextualView().getListPanel().getCurrentSelectionListCouriers();
		
		if(index >= 0 && index < controller.listCouriers.size()) {
			
			controller.listTours.remove(controller.listCouriers.get(index));
			controller.listCouriers.remove(index);
			controller.window.updateCouriers(controller.listCouriers);
			
		} else if(index == -1) {
			
			JOptionPane.showMessageDialog(controller.window, "Select a courier by clicking on it", "Error", JOptionPane.ERROR_MESSAGE);
			
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
		
		if(controller.cityMap != null) {
			
			controller.cityMap.getAtPoint(p, controller.window.getGraphicalView().getMapPanel().getFocus(), controller.window.getGraphicalView().getMapPanel().getCoefX(), controller.window.getGraphicalView().getMapPanel().getCoefY(), controller.window.getGraphicalView().getMapPanel().getSideLength());
			controller.window.displaySelectedRoadSegment(controller.cityMap.getSelectedRoadSegment());
			
		}
		
	}

}