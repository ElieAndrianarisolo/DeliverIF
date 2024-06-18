package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import model.*;
import controller.Controller;

/**
 * ListPanel represents a panel for displaying a list of delivery requests and a list of couriers.
 * @author H4114
 */
public class ListPanel extends JPanel {

    private static final long serialVersionUID = 1L;

	private ListListener listListener;
	private CityMap cityMap;
	private LinkedHashMap<Courier, Tour> listTours;
	
	private boolean displayDeliveryRequests;
	private boolean displayTour;
	private boolean displayCouriers;

	private JList<String> listTextDeliveryRequests;
	private ListSelectionModel listSelectionModel;
	private JScrollPane scrollPane;
	private JPanel splitPanel;
    
	private List<Courier> listCouriers;
	
	private JList<String> listTextCouriers;
	private ListSelectionModel listCouriersSelectionModel;
	private JScrollPane couriersScrollPane;
	private JPanel couriersPanel;

	private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    /**
	 * Creates a new ListPanel with default values.
	 */
    public ListPanel() {
    }

    /**
	 * Creates a new ListPanel with the specified window, city map, list of tours, and controller.
	 *
	 * @param controller 	The controller responsible for handling user interactions.
	 * @param cityMap		The city map associated with the panel.
	 * @param listTours 	The list of tours associated with the panel. 
	 */
    public ListPanel(Controller controller, CityMap cityMap, LinkedHashMap<Courier, Tour> listTours) {
        
    	super();

		setLayout(new BorderLayout());

		listListener = new ListListener(controller);

		this.cityMap = cityMap;
		this.listTours = listTours;

		// Delivery requests list panel
		
		displayDeliveryRequests = false;
		displayTour = false;

		scrollPane = new JScrollPane();
		
		listTextDeliveryRequests = new JList<String>();

		listTextDeliveryRequests.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTextDeliveryRequests.setLayoutOrientation(JList.VERTICAL);
		listTextDeliveryRequests.setVisibleRowCount(20);
		
		UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());

		listSelectionModel = listTextDeliveryRequests.getSelectionModel();
		listSelectionModel.addListSelectionListener(listListener);

		splitPanel = new JPanel();
		splitPanel.setLayout(new BoxLayout(splitPanel, BoxLayout.PAGE_AXIS));
		splitPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		add(splitPanel, BorderLayout.CENTER);

		splitPanel.setVisible(false);

		splitPanel.add(scrollPane);

		splitPanel.setSize(splitPanel.getPreferredSize());
		
		// Courier management panel section
		
		displayCouriers = false;
		
		listCouriers = new ArrayList<Courier>();
		couriersScrollPane = new JScrollPane(); 
		listTextCouriers = new JList<String>();

		listTextCouriers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTextCouriers.setLayoutOrientation(JList.VERTICAL);
		listTextCouriers.setVisibleRowCount(60);

		listCouriersSelectionModel = listTextDeliveryRequests.getSelectionModel();
		listCouriersSelectionModel.addListSelectionListener(listListener);
		
		couriersPanel = new JPanel();
		couriersPanel.setLayout(new BoxLayout(couriersPanel, BoxLayout.PAGE_AXIS));
		couriersPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		add(couriersPanel, BorderLayout.NORTH);

		couriersPanel.setVisible(false);

		couriersPanel.add(couriersScrollPane);

		couriersPanel.setSize(couriersPanel.getPreferredSize());
		
		validate();
    	
    }
    
	/**
	 * Fills up the list of delivery requests and updates the panel accordingly.
	 */
	public void fillUpList() {
		
		String color = "black";
		listTextDeliveryRequests.removeAll();
		
		if(displayCouriers == true) {
			
			String textListArray[] = new String[listCouriers.size()];
			
			for(int i = 0; i< listCouriers.size(); i++) {
				
				Color courierColor = listCouriers.get(i).getColor();
				textListArray[i] = "<html> <span style=\"font-weight:normal\"><span style=\"color: rgb("+courierColor.getRed()+", "+courierColor.getGreen()+", "+courierColor.getBlue()+");\">●</span> Courier n°" + (i+1) + " : <br><li style=\"font-weight:normal\"> Name : <b>" + listCouriers.get(i).getFirstName() + "</b></li></span></html>";
				
			}
			
			listTextCouriers.setListData(textListArray);

			couriersScrollPane.setBorder(new TitledBorder(null, "Courier manager", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
			couriersScrollPane.setViewportView(listTextCouriers);
			splitPanel.setVisible(false);
			couriersPanel.setVisible(true);
			displayCouriers = false;
			
			this.repaint();
			
		} else if(displayTour == true && listTours != null && !listTours.isEmpty()) {
				
			int sizeTextList = 0;
			Tour tour;
			
			for (Map.Entry<Courier, Tour> tourEntry : listTours.entrySet()) {
				 
			    tour = tourEntry.getValue();
			    
			    if(!tour.getListDeliveryRequests().isEmpty() && tour.getWarehouse() != null) {
			    	
			    	sizeTextList += tour.getListDeliveryRequests().size() + 2;
			    	
			    } 
			    
			}
			
			if(sizeTextList >= 0) {
				
				String textListArray[] = new String[sizeTextList];
				int index = 0;
				
				for (Map.Entry<Courier, Tour> tourEntry : listTours.entrySet()) {
					
				    tour = tourEntry.getValue();
				    
				    if(!tour.getListDeliveryRequests().isEmpty()) {
				    	
				    	Color courierColor = tour.getCourier().getColor();
				    	
					    textListArray[index] = "<html> <span style=\"color: rgb("+courierColor.getRed()+", "+courierColor.getGreen()+", "+courierColor.getBlue()+");\">●</span> " + tour.getCourier().getFirstName() + "'s tour : "+
						"<br><li><span style=\"font-weight:normal\">Departure from warehouse at <b>" + tour.getStartTime().format(timeFormat) + "</b></span></li></html>";
					    
					    index++;
					    
					    for(int j = 0; j < tour.getListDeliveryRequests().size(); j++) {
					    	
					    	LocalTime timeWindowBis = LocalTime.ofSecondOfDay((tour.getListDeliveryRequests().get(j).getTimeWindow() + 1)*60*60);
					    	LocalTime timeWindow = LocalTime.ofSecondOfDay((tour.getListDeliveryRequests().get(j).getTimeWindow())*60*60);
					    	
					    	if(tour.getListDeliveryRequests().get(j).getDepartureTime().isAfter(timeWindowBis)) {
					    		
					    		color = "red";
								
								textListArray[index] = "<html><font color='" + color + "'>"+
														"<li>Delivery request n°" + (j+1) + " - from "+ tour.getListDeliveryRequests().get(j).getDepartureTime().format(timeFormat)  + " to " + tour.getListDeliveryRequests().get(j).getArrivalTime().format(timeFormat) + "</li><ul>"+
														"<li><span style=\"font-weight:normal\"> Time window : <b>"  + timeWindow + " - " + timeWindow.plusHours(1)+ "</b></span></li></ul>"+
														"</font></span></html>";
								
					    	} else if(!tour.getListDeliveryRequests().get(j).getWaitingTime().equals(LocalTime.of(0, 0))) {
								
								color = "blue";
								String waitingTimeString = "";
								LocalTime waitingTime = tour.getListDeliveryRequests().get(j).getWaitingTime();
								
								if (waitingTime.getHour() != 0){
									
									waitingTimeString += waitingTime.getHour() + " h ";
									
								}
								
								waitingTimeString += waitingTime.getMinute() + " min";
								
								textListArray[index] = "<html><font color='" + color + "'>"+
														"<li>Delivery request n°" + (j+1) + " - from "+ tour.getListDeliveryRequests().get(j).getDepartureTime().format(timeFormat)  + " to " + tour.getListDeliveryRequests().get(j).getArrivalTime().format(timeFormat) + "</li>"+
														"<ul><li><span style=\"font-weight:normal\"> Time window : <b>"  + timeWindow + " - " + timeWindow.plusHours(1)+ "</b></span></li>"+
														"<li> <span style=\"font-weight:normal\">Waiting time : <b>"+ waitingTimeString+ "</b></span></li>"+
														"</ul></font></html>";
								
							} else {
								
								textListArray[index] = "<html><font color='" + color + "'>"+
														"<li> Delivery request n°" + (j+1) + " - from "+ tour.getListDeliveryRequests().get(j).getDepartureTime().format(timeFormat)  + " to " + tour.getListDeliveryRequests().get(j).getArrivalTime().format(timeFormat) + "</li>"+
														"<ul><li><span style=\"font-weight:normal\"> Time window : <b>"  + timeWindow + " - " + timeWindow.plusHours(1)+ "</b></span></li></ul>"+
														"</font></html>";
								
							}
					    	
					    	color = "black";
					    	index++;
					    	
					    }
					    
					    textListArray[index] = "<html><li><span style=\"font-weight:normal\">Arrival at warehouse at <b>" + tour.getEndTime().format(timeFormat) + "</b></span></li></html>";
					    index++;
				    
				    }
				    
				}
				
				listTextDeliveryRequests.setListData(textListArray);

				scrollPane.setBorder(new TitledBorder(null, "Tours", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
				scrollPane.setViewportView(listTextDeliveryRequests);
				splitPanel.setVisible(true);
				couriersPanel.setVisible(false);
				
				this.repaint();
				
			}
			
		} else if (displayDeliveryRequests == true && cityMap.getListDeliveryRequests() != null && !cityMap.getListDeliveryRequests().isEmpty()) {
			
			String textListArray[] = new String[cityMap.getListDeliveryRequests().size()]; 
			boolean isGreen = false;
			
			Intersection deliveryLocation;
				
			for (int i = 0; i < cityMap.getListDeliveryRequests().size(); i++) {
				
				deliveryLocation = cityMap.getListDeliveryRequests().get(i);
				
				if(i == 0) {
					
					if(deliveryLocation instanceof Warehouse) {
						
						textListArray[i] = "<html><span style=\"font-weight:normal\">Departure time from the warehouse : <b>8:00</b> </span></html>" ;
						
					}
					
				}
				
				if(deliveryLocation instanceof DeliveryRequest) {
					
					Courier courier = null;
					Tour tour;
						
					if(!(cityMap.getListDeliveryRequests().get(0) instanceof Warehouse)) {
						
						if(listTours != null) {
							
							for (Map.Entry<Courier, Tour> tourEntry : listTours.entrySet()) {
								
								courier = tourEntry.getKey();
							    tour = tourEntry.getValue();
							    
							    if(tour.getListDeliveryRequests().contains((DeliveryRequest)deliveryLocation)) {
							    	
							    	color = "green";
									isGreen = true;
							    	break;
							    	
							    } 
							    
							}
							
						}
						
						textListArray[i] = "<html><font color='" + color + "'> - Delivery request n°" + (i+1) + " : ";
							
					} else {
						
						if(listTours != null) {
							
							for (Map.Entry<Courier, Tour> tourEntry : listTours.entrySet()) {
								
								courier = tourEntry.getKey();
							    tour = tourEntry.getValue();
							    
							    if(tour.getListDeliveryRequests().contains((DeliveryRequest)deliveryLocation)) {
							    	
							    	color = "green";
									isGreen = true;
							    	break;
							    	
							    } 
							    
							}
							
						}
						
						textListArray[i] = "<html><font color='" + color + "'>  - Delivery request n°" + i + " : ";
						
					}
					
					LocalTime timeWindow = LocalTime.of(((DeliveryRequest)deliveryLocation).getTimeWindow(), 0, 0);
				
					if(isGreen == true && courier != null) {
						
						textListArray[i] +=  "<br><li><span style=\"font-weight:normal\"> Assigned courier : <b>" + courier.getFirstName() + "</b></span></li>"+
						"<li><span style=\"font-weight:normal\"> Time window : <b>" + timeWindow +" - "+ timeWindow.plusHours(1)+ "</b></span></li></font></html>";
						
					} else {
						
						textListArray[i] += "<br><li><span style=\"font-weight:normal\"> Time window : <b>" + timeWindow +" - "+ timeWindow.plusHours(1)+ "</b></span></li></font></span></html>";
						
					}
					
				}
				
				color = "black";
				isGreen = false;
			
			}
			
		
			listTextDeliveryRequests.setListData(textListArray);

			scrollPane.setBorder(new TitledBorder(null, "Delivery requests", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
			scrollPane.setViewportView(listTextDeliveryRequests);
			splitPanel.setVisible(true);
			couriersPanel.setVisible(false);
			
			this.repaint();
			
			
		} else {
			
			splitPanel.setVisible(false);
			couriersPanel.setVisible(false);
			this.repaint();
			
		}

	}
	
	/**
	 * Sets the display state of the delivery requests.
	 *
	 * @param state The display state (true for displaying, false for hiding).
	 */
	public void setDeliveryRequestsDisplay(boolean state) {
		
		displayDeliveryRequests = state;
		
	}
	
	/**
	 * Sets the display state of the list of tours.
	 *
	 * @param state The display state (true for displaying, false for hiding).
	 */
	public void setToursDisplay(boolean state) {
		
		displayTour = state;
		
	}
	
	/**
	 * Sets the display state of the courier.
	 *
	 * @param state The display state (true for displaying, false for hiding).
	 */
	public void setCourierDisplay(boolean state) {
		
		displayCouriers = state;
		
	}
	
	/**
	 * Sets the city map for the panel.
	 *
	 * @param cityMap The city map to be associated with the panel.
	 */
	public void setDeliveryRequests(CityMap cityMap) {
		
		this.cityMap = cityMap;
		setListTours(null);
		
	}
    
	/**
	 * Sets the list of tours for the panel.
	 *
	 * @param listTours The list of tours to be associated with the panel.
	 */
	public void setListTours(LinkedHashMap<Courier, Tour> listTours) {
		
		this.listTours = listTours;
	}
	
	/**
	 * Gets the index of the currently selected item in the list of delivery requests.
	 *
	 * @return The index of the currently selected item.
	 */
	public int getCurrentSelectionListDeliveryRequests() {
		
		return listTextDeliveryRequests.getSelectedIndex();
		
	}
	
	/**
	 * Gets the index of the currently selected item in the list of couriers.
	 *
	 * @return The index of the currently selected item.
	 */
	public int getCurrentSelectionListCouriers() {
		
		return listTextCouriers.getSelectedIndex();
		
	}
		
	/**
	 * Sets the selected index in the list.
	 *
	 * @param index The index to be selected.
	 */
	public void setSelectedIndex(int index) {
		
		listTextDeliveryRequests.setSelectedIndex(index);
		
	}
	
	/**
	 * Sets the list of couriers.
	 *
	 * @param listCouriers The list of couriers to set.
	 */
	public void setListCourier(List<Courier> listCouriers) {
		
		this.listCouriers = listCouriers;
		
	}
	
	/**
	 * Clear the selection in the list of delivery requests.
	 */
	public void clearSelection() {
		
		listTextDeliveryRequests.clearSelection();
		
	}

}
