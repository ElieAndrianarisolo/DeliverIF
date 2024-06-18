package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;

/**
 * ButtonListener handles actions performed on buttons in the user interface.
 * @author H4114
 */
public class ButtonListener implements ActionListener{

	private Controller controller;
	private JFileChooser fileChooser;
		
	/**
	 * Constructs a new ButtonListener.
	 */
    public ButtonListener() {
    }

    /**
	 * Constructs a new ButtonListener with the specified controller.
	 *
	 * @param controller The controller associated with the listener.
	 */
    public ButtonListener(Controller controller) {
        
    	this.controller = controller;

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setCurrentDirectory(new File("./data"));
    	
    }

    /**
	 * Handles button click events.
	 * 
	 * @param e The ActionEvent associated with the button click.
	 */
    public void actionPerformed(ActionEvent e) {
    	
		int userChoice;
		
		switch (e.getActionCommand()) {
		
			case "Load a city map":
				
				String pathCityMap = "";
				fileChooser.setCurrentDirectory(new File("./data/cityMap"));
				fileChooser.setFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
				
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					pathCityMap = fileChooser.getSelectedFile().getAbsolutePath();
					controller.loadCityMap(pathCityMap);
				}
				
				break;
				
			case "Load a delivery requests file":
				
				String pathDeliveryRequests = "";
				fileChooser.setCurrentDirectory(new File("./data/deliveryRequests"));
				fileChooser.setFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
				
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					pathDeliveryRequests = fileChooser.getSelectedFile().getAbsolutePath();
					controller.loadDeliveryRequests(pathDeliveryRequests);
				}
				
				break;
			
			case "Create a delivery request":
				
				controller.createDeliveryRequest();
				
				break;
				
			case "Manage delivery requests":
				
				controller.manageDeliveryRequests();
				
				break;
				
			case "Validate the created delivery request":
				
				controller.validateCreatedDeliveryRequest();
				
				break;
				
			case "Go back to the delivery requests home":
				
				controller.goBackDeliveryRequestsHome();
				
				break;
				
			case "Save the delivery requests in a file":
				
				String pathSaveDeliveryRequestsFile = "";
				fileChooser.setCurrentDirectory(new File("./data/deliveryRequests"));
				fileChooser.setFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
				
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	
					pathSaveDeliveryRequestsFile = fileChooser.getSelectedFile().getAbsolutePath();
					
					Path path = Paths.get(pathSaveDeliveryRequestsFile);
					String fileName = path.getFileName().toString();
					String pattern = "^[a-zA-Z0-9._-]+\\.xml$"; 
	
					if(fileName.matches(pattern)) {
						
						File file = new File(pathSaveDeliveryRequestsFile);
						
						if(file.exists()) {
						
							userChoice = JOptionPane.showConfirmDialog(null, "This file already exists. Do you want to overwrite it ? ", "Confirmation", JOptionPane.YES_NO_OPTION);
							
							if (userChoice == JOptionPane.YES_OPTION) {
								
								controller.saveDeliveryRequestToFile(pathSaveDeliveryRequestsFile);
					            
							} 
							
						} else {
							
							controller.saveDeliveryRequestToFile(pathSaveDeliveryRequestsFile);
							
						}
						
					} else {
						
						JOptionPane.showMessageDialog(null, "The file name is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
						
					}
	
				}
			
				break;
			
			case "Remove the selected delivery request":
				
				userChoice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this delivery requests from the map ? ", "Confirmation",JOptionPane.YES_NO_OPTION);
				
				if (userChoice == JOptionPane.YES_OPTION) {
					
					controller.removeDeliveryRequestFromListDeliveryRequests();
		            
		        }
				
				break;
				
			case "Initialize a tour":
				
				controller.initializeTour();
				
				break;
				
			case "Add a delivery request to the tour":
				
				controller.addDeliveryRequestToTour();
				
				break;
			
			case "Remove a delivery request from the tour":
				
				controller.removeDeliveryRequestFromTour();
				
				break;
				
			case "Compute the best tour":
				
				controller.computeBestTour();
				
				break;
				
			case "Generate a road map":
				
				String pathSaveDeliveryRequestsFileHTML = "";
				fileChooser.setCurrentDirectory(new File("./data/roadMap"));
				fileChooser.setFileFilter(new FileNameExtensionFilter("*.html", "html"));
				
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	
					pathSaveDeliveryRequestsFileHTML = fileChooser.getSelectedFile().getAbsolutePath();
					
					Path path = Paths.get(pathSaveDeliveryRequestsFileHTML);
					String fileName = path.getFileName().toString();
					String pattern = "^[a-zA-Z0-9._-]+\\.html$"; 
	
					if(fileName.matches(pattern)) {
						
						File file = new File(pathSaveDeliveryRequestsFileHTML);
						
						if(file.exists()) {
						
							userChoice = JOptionPane.showConfirmDialog(null, "This file already exists. Do you want to overwrite it ? ", "Confirmation", JOptionPane.YES_NO_OPTION);
							
							if (userChoice == JOptionPane.YES_OPTION) {
								
								controller.generateRoadMap(pathSaveDeliveryRequestsFileHTML);
					            
							} 
							
						} else {
							
							controller.generateRoadMap(pathSaveDeliveryRequestsFileHTML);
							
						}
						
					} else {
						
						JOptionPane.showMessageDialog(null, "The file name is incorrect ", "Error", JOptionPane.ERROR_MESSAGE);
						
					}
	
				}
				
				break;
				
			case "Manage couriers":
				
				controller.manageCouriers();

				break;
				
			case "Create a courier":
				
				String courierName;
				courierName = JOptionPane.showInputDialog("Enter the courier's name : ");
				
				if(courierName != null && courierName.matches("[a-zA-Z\\é\\è\\ê\\-\\_]+")) {
					
					controller.createCourier(courierName);
					
				} else if (courierName != null && !courierName.matches("[a-zA-Z\\é\\è\\ê\\-\\_]+")) {
					
					JOptionPane.showMessageDialog(null, "The courier's name is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
					
				}

				break;
				
			case "Remove a courier":
				
				controller.removeCourier();

				break;
				
			case "Add a delivery request":
				
				controller.addDeliveryRequestToComputedTour();
				
				break;
			
			case "Remove a delivery request":
				
				controller.removeDeliveryRequestToComputedTour();
				
				break;
				
			case "Undo":
				
				controller.undo();
				
				break;
			
			case "Redo":
				
				controller.redo();
				
				break;

		}
		
    }

}
