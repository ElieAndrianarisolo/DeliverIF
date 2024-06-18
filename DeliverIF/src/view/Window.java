package view; 

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import java.util.List;
import java.util.Vector;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import controller.Controller;
import model.*;

import javax.swing.SwingConstants;

/**
 * The Window class represents the main graphical user interface of the DeliverIF application.
 * @author H4114
 */
public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private GraphicalView graphicalView;
	private TextualView textualView;
	private Vector<String> listCouriers;	
	private Vector<String> listCouriersDisplay;	
	private JPanel leftPanel;
	
	/**
	 * Buttons of the different states
	 */
	private JButton buttonLoadCityMap;
	private JButton buttonLoadDeliveryRequests;
	private JButton buttonManageDeliveryRequests;
	private JButton buttonAssignDeliveryRequest;
	private JButton buttonComputeBestTour;
	private JButton buttonGenerateRoadMap;
	private JButton buttonManageCourier;
	
	/**
	 * Buttons of the different states
	 */
	private JLabel roadSegmentInfoLabel;
	
	/**
	 * Indications for the delivery planner
	 */
	private JLabel indicationsLabel;
	
	/**
	 * Panel to create a delivery request
	 */
	private JPanel createDeliveryRequestPanel;
	private JComboBox<String> timeWindowList;
	private JRadioButton yesWarehouseRadioButton;
	private JRadioButton noWarehouseRadioButton;
	
	/**
	 * Panel to initialize a tour
	 */
	private JPanel initializeTourPanel;
	
	/**
	 * Panel with the buttons to modify a tour
	 */
	private JPanel modifyDeliveryRequestsPanel;
	
	/**
	 * Panel to manage the couriers
	 */
	private JPanel manageCourierPanel;
	private JComboBox<String> courierNameList;
	
	/**
	 * Panel to modify a tour computed at least once
	 */
	private JPanel modifyTourPanel;
	private JPanel selectTourPanel;
	private JPanel selectCourierTourPanel;
	private JPanel selectTimeWindowTourPanel;
	private JPanel selectDisplayCourierTourPanel;
	
	/**
	 * Buttons to modify a tour computed at least once
	 */
	private JButton buttonAddDeliveryRequestToComputedTour;
	private JButton buttonRemoveDeliveryRequestToComputedTour;
	private JButton buttonUndo;
	private JButton buttonRedo;
	private JComboBox<String> courierNameListTour;
	private JComboBox<String> timeWindowListTour;
	private JComboBox<String> courierNameListDisplayTour;
	
	/**
	 * Fonts
	 */
	private Font primaryFont = new Font("Serif", Font.BOLD, 16);
	private Font secondaryFont = new Font("Serif", Font.PLAIN, 14);
	private Font tertiaryFont = new Font("Serif", Font.ITALIC, 14);

	
    /**
     * Constructs a new Window without any specific parameters.
     */
    public Window() {
    }

    /**
     * Constructs a new Window with a controller, city map, and tour.
     *
     * @param controller 	The controller associated with this window.
     * @param cityMap 		The city map used in the application.
     * @param listTours 	The list of tours used in the application.
     */
    public Window(Controller controller, CityMap cityMap, LinkedHashMap<Courier, Tour> listTours) {
        
    	super("DeliverIF");
    	
		this.listCouriers = new Vector<String>();
		this.listCouriersDisplay = new Vector<String>();
		listCouriersDisplay.add("All");
		
		CourierDisplayTourListListener courierListener = new CourierDisplayTourListListener(controller);
		
		String[] timeWindowsToChoose = {"8-9", "9-10", "10-11", "11-12"};
		
    	ButtonListener buttonListener = new ButtonListener(controller);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 1 mainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);
		
		// 1.1 mainPanel/centerPanel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2, 20, 20));
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		// 1.1.1 mainPanel/centerPanel/leftPanel
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		centerPanel.add(leftPanel);
		
		// 1.1.1.1 mainPanel/centerPanel/leftPanel/graphicalView
		graphicalView = new GraphicalView(controller, cityMap, listTours);
		leftPanel.add(graphicalView, BorderLayout.CENTER);
		
		// 1.1.1.2 mainPanel/centerPanel/leftPanel/legendPanel
		JPanel legendPanel = new JPanel();
		legendPanel.setLayout(new BorderLayout());
		leftPanel.add(legendPanel, BorderLayout.SOUTH);
		
		// 1.1.1.2.1 mainPanel/centerPanel/leftPanel/legendPanel/roadSegmentInfoPanel
		JPanel roadSegmentInfoPanel = new JPanel();
		roadSegmentInfoPanel.setLayout(new CardLayout(50, 0));
		roadSegmentInfoPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		legendPanel.add(roadSegmentInfoPanel, BorderLayout.CENTER);
		
		// 1.1.1.2.1.1 mainPanel/centerPanel/leftPanel/legendPanel/indicationsPanel/roadSegmentInfoLabel
		roadSegmentInfoLabel = new JLabel("", SwingConstants.LEFT);
		roadSegmentInfoLabel.setFont(tertiaryFont);
		roadSegmentInfoPanel.add(roadSegmentInfoLabel);
		
		// 1.1.1.2.2 mainPanel/centerPanel/leftPanel/legendPanel/indicationsPanel
		JPanel indicationsPanel = new JPanel();
		indicationsPanel.setLayout(new CardLayout(50, 0));
		indicationsPanel.setPreferredSize(new Dimension(500, 140));
		legendPanel.add(indicationsPanel, BorderLayout.SOUTH);
		
		// 1.1.1.2.2.1 mainPanel/centerPanel/leftPanel/legendPanel/indicationsPanel/indicationsLabel
		indicationsLabel = new JLabel("", SwingConstants.LEFT);
		indicationsLabel.setFont(primaryFont);
		indicationsPanel.add(indicationsLabel);
		
		// 1.1.2 mainPanel/centerPanel/rightPanel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		centerPanel.add(rightPanel);
		
		// 1.1.2.1 mainPanel/centerPanel/rightPanel/textualView
		textualView = new TextualView(controller, cityMap, listTours);
		rightPanel.add(textualView, BorderLayout.CENTER);
		
		//1.1.2.2 mainPanel/centerPanel/rightPanel/optionsPanel
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BorderLayout());
		rightPanel.add(optionsPanel, BorderLayout.SOUTH);
			
		//1.1.2.2.1 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel
		modifyTourPanel = new JPanel();
		modifyTourPanel.setLayout(new BorderLayout());
		optionsPanel.add(modifyTourPanel, BorderLayout.NORTH);
		modifyTourPanel.setVisible(false);
			
		//1.1.2.2.1.1 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel
		selectTourPanel = new JPanel();
		selectTourPanel.setLayout(new BorderLayout());
		modifyTourPanel.add(selectTourPanel, BorderLayout.NORTH);
			
		//1.1.2.2.1.1.2 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel/selectDisplayCourierTourPanel
		selectDisplayCourierTourPanel = new JPanel();
		selectDisplayCourierTourPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		selectTourPanel.add(selectDisplayCourierTourPanel, BorderLayout.NORTH);
		
		//1.1.2.2.1.1.2.1 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel/selectDisplayCourierTourPanel/instructionSelectionDisplayCourierTour
		JLabel instructionSelectionDisplayCourierTour = new JLabel ("- Which courier's tour do you want to display ? : ", SwingConstants.LEFT);
		instructionSelectionDisplayCourierTour.setFont(secondaryFont);
		selectDisplayCourierTourPanel.add(instructionSelectionDisplayCourierTour);
			
		//1.1.2.2.1.1.2.2 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel/selectDisplayCourierTourPanel/courierNameListDisplayTour
		courierNameListDisplayTour = new JComboBox<>(listCouriersDisplay);
		courierNameListDisplayTour.setPreferredSize(new Dimension(80, 20));
		selectDisplayCourierTourPanel.add(courierNameListDisplayTour);
		courierNameListDisplayTour.addItemListener(courierListener);
				
		//1.1.2.2.1.1.2 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel/selectCourierTourPanel
		selectCourierTourPanel = new JPanel();
		selectCourierTourPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		selectTourPanel.add(selectCourierTourPanel, BorderLayout.CENTER);
		
		//1.1.2.2.1.1.2.1 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel/selectCourierTourPanel/instructionSelectionCourierTour
		JLabel instructionSelectionCourierTour = new JLabel ("- Select the courier's tour to which to add a delivery request : ", SwingConstants.LEFT);
		instructionSelectionCourierTour.setFont(secondaryFont);
		selectCourierTourPanel.add(instructionSelectionCourierTour);
		
		//1.1.2.2.1.1.2.2 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel/selectCourierTourPanel/courierNameListTour
		courierNameListTour = new JComboBox<>(listCouriers);
		courierNameListTour.setPreferredSize(new Dimension(80, 20));
		selectCourierTourPanel.add(courierNameListTour);
		
		//1.1.2.2.1.1.3 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel/selectTimeWindowTourPanel
		selectTimeWindowTourPanel = new JPanel();
		selectTimeWindowTourPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		selectTourPanel.add(selectTimeWindowTourPanel, BorderLayout.SOUTH);
		
		//1.1.2.2.1.1.3.1 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel/selectTimeWindowTourPanel/instructionSelectionTimeWindowTour
		JLabel instructionSelectionTimeWindowTour = new JLabel("- Select a time window : ", SwingConstants.LEFT);
		instructionSelectionTimeWindowTour.setFont(secondaryFont);
		selectTimeWindowTourPanel.add(instructionSelectionTimeWindowTour);
		
		//1.1.2.2.1.1.3.2 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/selectTourPanel/selectTimeWindowTourPanel/selectTimeWindowTourPanel
		timeWindowListTour = new JComboBox<>(timeWindowsToChoose);
		timeWindowListTour.setPreferredSize(new Dimension(80, 20));
		selectTimeWindowTourPanel.add(timeWindowListTour);
		
		//1.1.2.2.1.2 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/modifyTourPanelButtons
		JPanel modifyTourPanelButtons = new JPanel();
		modifyTourPanelButtons.setLayout(new GridLayout(2, 2, 10, 10));
		modifyTourPanelButtons.setBorder(new EmptyBorder(0, 175, 0, 160));
		modifyTourPanel.add(modifyTourPanelButtons, BorderLayout.SOUTH);

		//1.1.2.2.1.2.1 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/modifyTourPanelButtons/buttonAddDeliveryRequestToComputedTour
		buttonAddDeliveryRequestToComputedTour = new JButton("Add a delivery request");
		buttonAddDeliveryRequestToComputedTour.addActionListener(buttonListener);
		modifyTourPanelButtons.add(buttonAddDeliveryRequestToComputedTour);
		
		//1.1.2.2.1.2.2 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/modifyTourPanelButtons/buttonRemoveDeliveryRequestToComputedTour
		buttonRemoveDeliveryRequestToComputedTour = new JButton("Remove a delivery request");
		buttonRemoveDeliveryRequestToComputedTour.addActionListener(buttonListener);
		modifyTourPanelButtons.add(buttonRemoveDeliveryRequestToComputedTour);
		
		//1.1.2.2.1.2.3 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/modifyTourPanelButtons/buttonUndo
		buttonUndo = new JButton("Undo");
		buttonUndo.addActionListener(buttonListener);
		modifyTourPanelButtons.add(buttonUndo);
		
		//1.1.2.2.1.2.4 mainPanel/centerPanel/rightPanel/optionsPanel/modifyTourPanel/modifyTourPanelButtons/buttonRedo
		buttonRedo = new JButton("Redo");
		buttonRedo.addActionListener(buttonListener);
		modifyTourPanelButtons.add(buttonRedo);
			
		//1.1.2.2.2 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel
		JPanel optionsPanelFirstSubPanel = new JPanel();
		optionsPanelFirstSubPanel.setLayout(new BorderLayout());
		optionsPanel.add(optionsPanelFirstSubPanel, BorderLayout.SOUTH);
		
		//1.1.2.2.2.1 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/manageCourierPanel
		manageCourierPanel = new JPanel();
		manageCourierPanel.setLayout(new BorderLayout());
		optionsPanelFirstSubPanel.add(manageCourierPanel,BorderLayout.NORTH);
		manageCourierPanel.setVisible(false);
			
		//1.1.2.2.2.2 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/instructionSelectionCourierLabel
		JLabel instructionSelectionCourierLabel = new JLabel("Select the courier by clicking on it.", SwingConstants.CENTER);
		instructionSelectionCourierLabel.setFont(secondaryFont);
		instructionSelectionCourierLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
		manageCourierPanel.add(instructionSelectionCourierLabel, BorderLayout.NORTH);
			
		//1.1.2.2.2.3 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/manageCourierButtonsPanel
		JPanel manageCourierButtonsPanel = new JPanel();
		manageCourierPanel.add(manageCourierButtonsPanel, BorderLayout.SOUTH);
		
		//1.1.2.2.2.3.1 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/manageCourierButtonsPanel/createCourierButton
		JButton createCourierButton = new JButton("Create a courier");
		createCourierButton.addActionListener(buttonListener);
		manageCourierButtonsPanel.add(createCourierButton);
		
		//1.1.2.2.2.3.2 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/manageCourierButtonsPanel/removeCourierButton
		JButton removeCourierButton = new JButton("Remove a courier");
		removeCourierButton.addActionListener(buttonListener);
		manageCourierButtonsPanel.add(removeCourierButton);
		
		//1.1.2.2.2.1 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/initializeTourPanel
		initializeTourPanel = new JPanel();
		initializeTourPanel.setLayout(new BorderLayout());
		optionsPanelFirstSubPanel.add(initializeTourPanel, BorderLayout.SOUTH);
		initializeTourPanel.setVisible(false);
		
		//1.1.2.2.2.1.1 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/initializeTourPanel/assignCourierToTourPanel
		JPanel assignCourierToTourPanel = new JPanel();
		assignCourierToTourPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
		initializeTourPanel.add(assignCourierToTourPanel, BorderLayout.NORTH);
		
		//1.1.2.2.2.1.1.1 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/initializeTourPanel/assignCourierToTourPanel/instructionSelectionCourier
		JLabel instructionSelectionCourier = new JLabel ("Assign a courier to the tour : ", SwingConstants.LEFT);
		instructionSelectionCourier.setFont(secondaryFont);
		assignCourierToTourPanel.add(instructionSelectionCourier);
		
		//1.1.2.2.2.1.1.2 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/initializeTourPanel/assignCourierToTourPanel/courierNameList
		courierNameList = new JComboBox<>(listCouriers);
		courierNameList.setPreferredSize(new Dimension(80, 20));
		assignCourierToTourPanel.add(courierNameList);
		
		//1.1.2.2.2.1.2 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/initializeTourPanel/assignDeliveryRequestToTourButtonsPanel
		JPanel assignDeliveryRequestToTourButtonsPanel = new JPanel();
		initializeTourPanel.add(assignDeliveryRequestToTourButtonsPanel, BorderLayout.SOUTH);
		
		//1.1.2.2.2.1.2.1 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/initializeTourPanel/assignDeliveryRequestToTourButtonsPanel/buttonAddDeliveryRequest
		JButton buttonAddDeliveryRequest = new JButton("Add a delivery request to the tour");
		buttonAddDeliveryRequest.addActionListener(buttonListener);
		assignDeliveryRequestToTourButtonsPanel.add(buttonAddDeliveryRequest);
		
		//1.1.2.2.2.2.1.2.2 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/initializeTourPanel/assignDeliveryRequestToTourButtonsPanel/buttonRemoveDeliveryRequest
		JButton buttonRemoveDeliveryRequest = new JButton("Remove a delivery request from the tour");
		buttonRemoveDeliveryRequest.addActionListener(buttonListener);
		assignDeliveryRequestToTourButtonsPanel.add(buttonRemoveDeliveryRequest);
		
		//1.1.2.2.2.1 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/modifyDeliveryRequestsPanel
		modifyDeliveryRequestsPanel = new JPanel();
		optionsPanelFirstSubPanel.add(modifyDeliveryRequestsPanel, BorderLayout.CENTER);
		modifyDeliveryRequestsPanel.setVisible(false);
		
		//1.1.2.2.2.1.2 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/modifyDeliveryRequestsPanel/buttonCreateDeliveryRequests
		JButton buttonCreateDeliveryRequests = new JButton ("Create a delivery request");
		buttonCreateDeliveryRequests.addActionListener(buttonListener);
		modifyDeliveryRequestsPanel.add(buttonCreateDeliveryRequests);
		
		//1.1.2.2.2.1.2 mainPanel/centerPanel/rightPanel/optionsPanel/modifyDeliveryRequestsPanel/buttonRemoveAllDeliveryRequests
		JButton buttonRemoveAllDeliveryRequests = new JButton ("Remove the selected delivery request");
		buttonRemoveAllDeliveryRequests.addActionListener(buttonListener);
		modifyDeliveryRequestsPanel.add(buttonRemoveAllDeliveryRequests);
		
		//1.1.2.2.2.1.3 mainPanel/centerPanel/rightPanel/optionsPanel/optionsPanelFirstSubPanel/modifyDeliveryRequestsPanel/buttonSaveDeliveryRequests
		JButton buttonSaveDeliveryRequests = new JButton ("Save the delivery requests in a file");
		buttonSaveDeliveryRequests.addActionListener(buttonListener);
		modifyDeliveryRequestsPanel.add(buttonSaveDeliveryRequests);
		
		// 1.1.2.3 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel
		createDeliveryRequestPanel = new JPanel();
		createDeliveryRequestPanel.setLayout(new BorderLayout());
		createDeliveryRequestPanel.setBorder(new EmptyBorder(40, 0, 0, 40));
		rightPanel.add(createDeliveryRequestPanel, BorderLayout.NORTH);
		
		// 1.1.2.3.1 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestSubPanel
		JPanel createDeliveryRequestSubPanel = new JPanel(); 
		createDeliveryRequestSubPanel.setLayout(new BorderLayout());
		createDeliveryRequestSubPanel.setBorder(new TitledBorder(null, "Create a delivery request", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		createDeliveryRequestPanel.add(createDeliveryRequestSubPanel, BorderLayout.NORTH);
		
		// 1.1.2.3.1.1 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestSubPanel/createDeliveryRequestFirstSubPanel
		JPanel createDeliveryRequestFirstSubPanel = new JPanel();
		createDeliveryRequestFirstSubPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		createDeliveryRequestFirstSubPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
		createDeliveryRequestSubPanel.add(createDeliveryRequestFirstSubPanel, BorderLayout.NORTH);
		
		// 1.1.2.3.1.1.1 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestSubPanel/createDeliveryRequestFirstSubPanel/instructionSelectIntersection
		JLabel instructionSelectIntersection = new JLabel ("- Select an intersection by clicking on it on the city map.", SwingConstants.LEFT);
		instructionSelectIntersection.setFont(secondaryFont);
		createDeliveryRequestFirstSubPanel.add(instructionSelectIntersection);
		
		// 1.1.2.3.1.2 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestSubPanel/createDeliveryRequestSecondSubPanel
		JPanel createDeliveryRequestSecondSubPanel = new JPanel();
		createDeliveryRequestSecondSubPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
		createDeliveryRequestSubPanel.add(createDeliveryRequestSecondSubPanel, BorderLayout.CENTER);
		
		// 1.1.2.3.1.2.1 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestSubPanel/createDeliveryRequestSecondSubPanel/createDeliveryRequestTimeWindowInstruction
		JLabel createDeliveryRequestTimeWindowInstruction = new JLabel("- Select a time window :", SwingConstants.LEFT);
		createDeliveryRequestTimeWindowInstruction.setFont(secondaryFont);
		createDeliveryRequestSecondSubPanel.add(createDeliveryRequestTimeWindowInstruction);
		
		// 1.1.2.3.1.2.2 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestSubPanel/createDeliveryRequestSecondSubPanel/timeWindowList
		timeWindowList = new JComboBox<>(timeWindowsToChoose);
		timeWindowList.setPreferredSize(new Dimension(80, 20));
		createDeliveryRequestSecondSubPanel.add(timeWindowList);
		
		// 1.1.2.3.1.3 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestSubPanel/createDeliveryRequestThirdSubPanel
		JPanel createDeliveryRequestThirdSubPanel = new JPanel();
		createDeliveryRequestThirdSubPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
		createDeliveryRequestSubPanel.add(createDeliveryRequestThirdSubPanel, BorderLayout.SOUTH);
		
		// 1.1.2.3.1.3.1 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestSubPanel/createDeliveryRequestThirdSubPanel/createDeliveryRequestWarehouseInstruction
		JLabel createDeliveryRequestWarehouseInstruction = new JLabel("- Is the selected intersection a warehouse ? :", SwingConstants.LEFT);
		createDeliveryRequestWarehouseInstruction.setFont(secondaryFont);
		createDeliveryRequestThirdSubPanel.add(createDeliveryRequestWarehouseInstruction);
		
		// 1.1.2.3.1.3.2 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestSubPanel/createDeliveryRequestThirdSubPanel/yesWarehouseRadioButton
		yesWarehouseRadioButton = new JRadioButton("Yes");
        noWarehouseRadioButton = new JRadioButton("No");
		ButtonGroup buttonWarehouseGroup = new ButtonGroup();
		buttonWarehouseGroup.add(yesWarehouseRadioButton);
		buttonWarehouseGroup.add(noWarehouseRadioButton);
		createDeliveryRequestThirdSubPanel.add(yesWarehouseRadioButton);
		createDeliveryRequestThirdSubPanel.add(noWarehouseRadioButton);
		
		// 1.1.2.3.2 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestButtonsPanel
		JPanel createDeliveryRequestButtonsPanel = new JPanel();
		createDeliveryRequestButtonsPanel.setLayout(new GridLayout(1, 3, 5, 20));
		createDeliveryRequestButtonsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		createDeliveryRequestPanel.add(createDeliveryRequestButtonsPanel);
		
		// 1.1.2.3.2.1 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestButtonsPanel/validateDeliveryRequestButton
		JButton validateDeliveryRequestButton = new JButton ("Validate the created delivery request");
		validateDeliveryRequestButton.addActionListener(buttonListener);
		createDeliveryRequestButtonsPanel.add(validateDeliveryRequestButton);
		
		// 1.1.2.3.2.2 mainPanel/centerPanel/rightPanel/createDeliveryRequestPanel/createDeliveryRequestButtonsPanel/buttonDeliveryRequestsHome
		JButton buttonDeliveryRequestsHome = new JButton ("Go back to the delivery requests home");
		buttonDeliveryRequestsHome.addActionListener(buttonListener);
		createDeliveryRequestButtonsPanel.add(buttonDeliveryRequestsHome);
		
		// 1.2 mainPanel/bottomPanel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new CardLayout(40, 40));
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		// 1.2.1 mainPanel/bottomPanel/stateButtonsPanel
		JPanel stateButtonsPanel = new JPanel();
		stateButtonsPanel.setLayout(new GridLayout(1, 6, 20, 20));
		bottomPanel.add(stateButtonsPanel);
		
		// 1.2.1.1 mainPanel/bottomPanel/stateButtonsPanel/buttonManageCourier
		buttonManageCourier = new JButton ("Manage couriers");
		buttonManageCourier.addActionListener(buttonListener);
		stateButtonsPanel.add(buttonManageCourier);
		
		// 1.2.1.2 mainPanel/bottomPanel/stateButtonsPanel/buttonLoadCityMap
		buttonLoadCityMap = new JButton("Load a city map");
		buttonLoadCityMap.addActionListener(buttonListener);
		stateButtonsPanel.add(buttonLoadCityMap);
		
		// 1.2.1.3 mainPanel/bottomPanel/stateButtonsPanel/buttonLoadDeliveryRequests
		buttonLoadDeliveryRequests = new JButton("Load a delivery requests file");
		buttonLoadDeliveryRequests.addActionListener(buttonListener);
		stateButtonsPanel.add(buttonLoadDeliveryRequests);
		
		// 1.2.1.4 mainPanel/bottomPanel/stateButtonsPanel/buttonLoadDeliveryRequests
		buttonManageDeliveryRequests = new JButton("Manage delivery requests");
		buttonManageDeliveryRequests.addActionListener(buttonListener);
		stateButtonsPanel.add(buttonManageDeliveryRequests);
		
		// 1.2.1.5 mainPanel/bottomPanel/stateButtonsPanel/buttonAssignDeliveryRequest
		buttonAssignDeliveryRequest = new JButton("Initialize a tour");
		buttonAssignDeliveryRequest.addActionListener(buttonListener);
		stateButtonsPanel.add(buttonAssignDeliveryRequest);
		
		// 1.2.1.6 mainPanel/bottomPanel/stateButtonsPanel/buttonComputeBestTour
		buttonComputeBestTour = new JButton("Compute the best tour");
		buttonComputeBestTour.addActionListener(buttonListener);
		stateButtonsPanel.add(buttonComputeBestTour);

		// 1.2.1.7 mainPanel/bottomPanel/stateButtonsPanel/buttonGenerateRoadMap
		buttonGenerateRoadMap = new JButton ("Generate a road map");
		buttonGenerateRoadMap.addActionListener(buttonListener);
		stateButtonsPanel.add(buttonGenerateRoadMap);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, screenSize.height - 50);
		
		createDeliveryRequestPanel.setVisible(false);
		repaint();
		setVisible(true);
    	
    }
    
    public JPanel getLeftPanel() {
		
    	return leftPanel;
    	
	}

    /**
     * Gets the graphical view associated with this window.
     *
     * @return The graphical view.
     */
    public GraphicalView getGraphicalView() {
        
    	return graphicalView;
    	
    }

    /**
     * Gets the textual view associated with this window.
     *
     * @return The textual view.
     */
    public TextualView getTextualView() {
       
    	return textualView;
    	
    }
    
    /**
     * Gets the selected time window.
     *
     * @return The selected time window.
     */
    public int getSelectedTimeWindow() {
       
    	int timeWindow = 0;
    	String selectedValue;
    	
    	selectedValue = (String) timeWindowList.getSelectedItem();
	
    	switch (selectedValue) {
    	
			case "8-9":
				
				timeWindow = 8;
				break;
				
			case "9-10":
				
				timeWindow = 9;
				break;
				
			case "10-11":
				
				timeWindow = 10;
				break;
				
			case "11-12":
				
				timeWindow = 11;
				break;
				
		} 
    	
    	return timeWindow;
    	
    }
    
    /**
     * Gets the selected time window for a delivery request of a computed tour.
     *
     * @return The selected time window.
     */
    public int getSelectedTimeWindowComputedTour() {
       
    	int timeWindow = 0;
    	String selectedValue;
    	
    	selectedValue = (String) timeWindowListTour.getSelectedItem();
	
    	switch (selectedValue) {
    	
			case "8-9":
				
				timeWindow = 8;
				break;
				
			case "9-10":
				
				timeWindow = 9;
				break;
				
			case "10-11":
				
				timeWindow = 10;
				break;
				
			case "11-12":
				
				timeWindow = 11;
				break;
				
		} 
    	
    	return timeWindow;
    	
    }
    
    /**
     * Gets the index of the selected courier.
     *
     * @return The index of the selected courier.
     */
    public int getIndexSelectedCourier() {
    	
    	return courierNameList.getSelectedIndex();
    	
    }
    
    /**
     * Selects the courier with the given index.
     *
     * @param index The index of the selected courier.
     */
    public void setIndexSelectedCourier(int index) {
    	
    	courierNameList.setSelectedIndex(index);
    	
    }
    
    /**
     * Gets the index of the selected courier to display their tour.
     *
     * @return The index of the selected courier.
     */
    public int getIndexSelectedCourierToDisplay() {
    	
    	return courierNameListDisplayTour.getSelectedIndex();
    	
    }
    
    /**
     * Gets the index of the selected courier when we add a delivery
     * request to a tour already computed at least once.
     *
     * @return The index of the selected courier.
     */
    public int getIndexSelectedCourierComputedTour() {
    	
    	return courierNameListTour.getSelectedIndex();
    	
    }
    
    
    /**
     * Gets the selected warehouse option.
     *
     * @return 1 if "Yes" is selected, 2 if "No" is selected, 0 otherwise.
     */
    public int getSelectedIsWarehouse() {
    	
    	if(yesWarehouseRadioButton.isSelected()) {
    		
    		return 1;
    		
    	} else if(noWarehouseRadioButton.isSelected()) {
    		
    		return 2;
    		
    	} else {
    		
    		return 0;
    		
    	}
    	
    }
    
    /**
     * Sets the indication label with the provided text.
     *
     * @param indication The text to be set as the indication label.
     */
	public void setIndicationLabel(String indication) {
		
		indicationsLabel.setText(indication);
		
	}
	
	
	/**
     * Updates the list of couriers.
     *
     * @param listCouriers The list of couriers to set.
     */
	public void updateCouriers(List<Courier> listCouriers) {
		
		this.listCouriers.clear();
		this.listCouriersDisplay.clear();
		
		this.listCouriersDisplay.add("All");
		
		for(int i = 0; i < listCouriers.size(); i++) { 
			
			this.listCouriers.add(listCouriers.get(i).getFirstName());
			this.listCouriersDisplay.add(listCouriers.get(i).getFirstName());
			
		}
		
		textualView.setCourierManagementMode(listCouriers);
		
		repaint();
		
	}
	
	/**
     * Displays the information about the selected road segment.
     *
     * @param roadSegment The road segment to display.
     */
	public void displaySelectedRoadSegment(RoadSegment roadSegment) {
		
		if(roadSegment != null) {
			
			roadSegmentInfoLabel.setText("<html>Street name : " + roadSegment.getStreetName()  + " | Length : " + roadSegment.getLength() + " m</html>");
			
		} else {
			
			roadSegmentInfoLabel.setText("<html> Click on a road segment to see its details here. </html>");
			
		}
		
	}
	
	/**
	 * Displays the tour corresponding to the selected courier.
	 * 
	 * @param courier The selected courier.
	 */
	public void displayTourCorrespondingToCourier(Courier courier) {
		
		graphicalView.setCourierCorrespondingToTourDisplay(courier);
		
		repaint();
		setVisible(true);
		
	}
	
	/**
	 * Sets the selected courier per default.
	 */
	public void setSelectedIndexCourierDisplayTour() {
		
		courierNameListDisplayTour.setSelectedIndex(0);
		
		repaint();
		setVisible(true);
		
	}
    
	/**
	 * Loads a city map in the graphical and textual views.
	 *
	 * @param cityMap The city map to be loaded.
	 */
    public void loadCityMap(CityMap cityMap) {
        
		graphicalView.newCityMap(cityMap);
		setLoadDeliveryRequestsMode();
		 
    }
	
	/**
	 * Loads the delivery requests in the graphical and textual views.
	 *
	 * @param cityMap The city map associated with the delivery requests.
	 */
    public void loadDeliveryRequests(CityMap cityMap) {
        
		graphicalView.newDeliveryRequests(cityMap);
		textualView.newDeliveryRequests(cityMap);
		setInitializeTourMode();
		 
    }
    
	/**
	 * Initiates the creation of a delivery request.
	 *
	 * @param cityMap The city map associated with the new delivery request.
	 */
    public void createDeliveryRequest(CityMap cityMap) {
    	
    	graphicalView.newDeliveryRequests(cityMap);
		textualView.newDeliveryRequests(cityMap);
    	setCreateDeliveryRequestMode();
		
	}
    
    /**
	 * Initiates tours.
	 *
	 * @param listTours The new list of tours.
	 */
    public void initializeListTours(LinkedHashMap<Courier, Tour> listTours) {
    	
    	graphicalView.newListTours(listTours);
    	textualView.newListTours(listTours);
    	setComputeBestTourMode();
    	
    }
    
    /**
	 * Initiates the computations of the best path for all the tours.
	 *
	 * @param listTours The new list of tours.
	 */
    public void computeBestTour(LinkedHashMap<Courier, Tour> listTours) {
    	
    	graphicalView.newListTours(listTours);
    	textualView.newListTours(listTours);
    	setModifyTourMode(null);
    	
    }
    
    /**
     * Sets the mode for loading a city map.
     */
    public void setLoadCityMapMode() {
    
    	setIndicationLabel("<html><font size='6'>Welcome on DeliverIF ! </font></html>");
    	
		graphicalView.setCityMapMode();
		textualView.setCityMapMode();
		
		createDeliveryRequestPanel.setVisible(false);
		initializeTourPanel.setVisible(false);
		modifyDeliveryRequestsPanel.setVisible(false);
		manageCourierPanel.setVisible(false);
		modifyTourPanel.setVisible(false);
		selectTourPanel.setVisible(false);
		
		buttonLoadCityMap.setEnabled(true);
		buttonLoadDeliveryRequests.setEnabled(false);
		buttonManageDeliveryRequests.setEnabled(false);
		buttonAssignDeliveryRequest.setEnabled(false);
		buttonComputeBestTour.setEnabled(false);
		buttonGenerateRoadMap.setEnabled(false);
		buttonManageCourier.setEnabled(true);
		
		repaint();
		setVisible(true);
    	
    }
    
    /**
     * Sets the mode for loading delivery requests.
     */
    public void setLoadDeliveryRequestsMode() {
		
    	displaySelectedRoadSegment(null);
    	setIndicationLabel("<html> Legend <ul style=\"list-style-type: none\">"+
			    			"<li style=\"font-display: none;\"> <span style=\"color: rgb(0, 255, 51);\">■</span> : selected intersection once clicked</li>"+
			    			"<li> <span style=\"color: rgb(0, 255, 51);\">―</span> : selected road segment once clicked </span></li>"+
			    			"</ul></html>");

		graphicalView.setLoadDeliveryRequestsMode();
		textualView.setCityMapMode();
		
		createDeliveryRequestPanel.setVisible(false);
		initializeTourPanel.setVisible(false);
		modifyDeliveryRequestsPanel.setVisible(false);
		manageCourierPanel.setVisible(false);
		modifyTourPanel.setVisible(false);
		selectTourPanel.setVisible(false);
		
		buttonLoadCityMap.setEnabled(true);
		buttonLoadDeliveryRequests.setEnabled(true);
		buttonManageDeliveryRequests.setEnabled(true);
		buttonAssignDeliveryRequest.setEnabled(false);
		buttonComputeBestTour.setEnabled(false);
		buttonGenerateRoadMap.setEnabled(false);
		buttonManageCourier.setEnabled(true);
		
		repaint();
		setVisible(true);
		
	}
	
	/**
	 * Sets the mode for creating a new delivery request.
	 */
	public void setCreateDeliveryRequestMode() {
		
		displaySelectedRoadSegment(null);
		setIndicationLabel("<html> Legend <ul style=\"list-style-type: none\">"+
							"<li style=\"font-display: none;\"> <span style=\"color: rgb(0, 255, 51);\">■</span> : selected intersection once clicked</li>"+
							"<li> <span style=\"color: rgb(0, 255, 51);\">―</span> : selected road segment once clicked </span></li>"+
							"<li>  <span style=\"color: rgb(255, 105, 180);\">■</span> : warehouse </span></li>"+
							"<li>  <span style=\"color: blue;\">■</span> : non-assigned delivery request to a tour </span></li>"+
							"</ul></html>");


		graphicalView.setLoadDeliveryRequestsMode();
		textualView.setCityMapMode();
		
		createDeliveryRequestPanel.setVisible(true);
		initializeTourPanel.setVisible(false);
		modifyDeliveryRequestsPanel.setVisible(false);
		manageCourierPanel.setVisible(false);
		modifyTourPanel.setVisible(false);
		selectTourPanel.setVisible(false);
		
		buttonLoadCityMap.setEnabled(true);
		buttonLoadDeliveryRequests.setEnabled(true);
		buttonManageDeliveryRequests.setEnabled(true);
		buttonAssignDeliveryRequest.setEnabled(false);
		buttonComputeBestTour.setEnabled(false);
		buttonGenerateRoadMap.setEnabled(false);
		buttonManageCourier.setEnabled(true);
		
		timeWindowList.setSelectedIndex(0);
		yesWarehouseRadioButton.setSelected(false);
		noWarehouseRadioButton.setSelected(true);
		
		repaint();
		setVisible(true);
		
	}
	
	/**
	 * Sets the mode for initializing a tour.
	 */
	public void setInitializeTourMode() {
		
		displaySelectedRoadSegment(null);
		setIndicationLabel("<html> Legend <ul style=\"list-style-type: none\">"+
							"<li style=\"font-display: none;\"> <span style=\"color: rgb(0, 255, 51);\">■</span> : selected intersection once clicked</li>"+
							"<li> <span style=\"color: rgb(0, 255, 51);\">―</span> : selected road segment once clicked </span></li>"+
							"<li>  <span style=\"color: rgb(255, 105, 180);\">■</span> : warehouse </span></li>"+
							"<li>  <span style=\"color: blue;\">■</span> : non-assigned delivery request to a tour </span></li>"+
							"</ul></html>");

	
		graphicalView.setInitializeTourMode();
		textualView.setDeliveryRequestsMode();
		
		createDeliveryRequestPanel.setVisible(false);
		initializeTourPanel.setVisible(false);
		modifyDeliveryRequestsPanel.setVisible(true);
		manageCourierPanel.setVisible(false);
		modifyTourPanel.setVisible(false);
		selectTourPanel.setVisible(false);
		
		buttonLoadCityMap.setEnabled(true);
		buttonLoadDeliveryRequests.setEnabled(true);
		buttonManageDeliveryRequests.setEnabled(true);
		buttonAssignDeliveryRequest.setEnabled(true); 
		buttonComputeBestTour.setEnabled(false);
		buttonGenerateRoadMap.setEnabled(false);
		buttonManageCourier.setEnabled(true);
		
		repaint();
		setVisible(true);
		
	}
	
	/**
	 * Sets the mode for computing the best tour.
	 */
	public void setComputeBestTourMode() {
		
		displaySelectedRoadSegment(null);
		setIndicationLabel("<html> Legend <ul style=\"list-style-type: none\">"+
							"<li style=\"font-display: none;\"> <span style=\"color: rgb(0, 255, 51);\">■</span> : selected intersection once clicked</li>"+
							"<li> <span style=\"color: rgb(0, 255, 51);\">―</span> : selected road segment once clicked </span></li>"+
							"<li>  <span style=\"color: rgb(255, 105, 180);\">■</span> : warehouse </span></li>"+
							"<li>  <span style=\"color: blue;\">■</span> : non-assigned delivery request to a tour </span></li>"+
							"<li>  <span style=\"color: black;\">■</span> : assigned delivery request to a tour </span></li>"+
							"</ul></html>");

		
		graphicalView.setInitializeTourMode();
		textualView.setInitializeTourMode();
		
		createDeliveryRequestPanel.setVisible(false);
		initializeTourPanel.setVisible(true);
		modifyDeliveryRequestsPanel.setVisible(false);
		manageCourierPanel.setVisible(false);
		modifyTourPanel.setVisible(false);
		selectTourPanel.setVisible(false);
		
		buttonLoadCityMap.setEnabled(true);
		buttonLoadDeliveryRequests.setEnabled(true);
		buttonManageDeliveryRequests.setEnabled(true);
		buttonAssignDeliveryRequest.setEnabled(true); 
		buttonComputeBestTour.setEnabled(true);
		buttonGenerateRoadMap.setEnabled(false);
		buttonManageCourier.setEnabled(true);
		
		if(courierNameList.getSelectedIndex() == -1) {
			
			courierNameList.setSelectedIndex(0);
			
		}
		
		repaint();
		setVisible(true);
		
	}
	
	/**
	 * Sets the mode for modifying a tour.
	 * 
	 * @param state The state in which the controller is.
	 */
	public void setModifyTourMode(String state) {
		
		graphicalView.setDisplayTourMode();
		textualView.setDisplayTourMode();
		
		displaySelectedRoadSegment(null);
		
		createDeliveryRequestPanel.setVisible(false);
		initializeTourPanel.setVisible(false);
		modifyDeliveryRequestsPanel.setVisible(false);
		modifyTourPanel.setVisible(true);
		manageCourierPanel.setVisible(false);
		selectTourPanel.setVisible(true);
		selectTimeWindowTourPanel.setVisible(false);
		selectCourierTourPanel.setVisible(false);
		selectDisplayCourierTourPanel.setVisible(true);
		courierNameListDisplayTour.setSelectedIndex(0);
		
		if(state != null && (state.equals("AddDeliveryRequest") || state.equals("RemoveDeliveryRequest"))) {
		
			buttonLoadCityMap.setEnabled(false);
			buttonLoadDeliveryRequests.setEnabled(false);
			buttonManageDeliveryRequests.setEnabled(false);
			buttonAssignDeliveryRequest.setEnabled(false); 
			buttonComputeBestTour.setEnabled(false);
			buttonGenerateRoadMap.setEnabled(false);
			buttonManageCourier.setEnabled(false);
			
			buttonAddDeliveryRequestToComputedTour.setEnabled(false);
			buttonRemoveDeliveryRequestToComputedTour.setEnabled(false);
			buttonUndo.setEnabled(true);
			buttonRedo.setEnabled(false);
			
			
			if(state.equals("AddDeliveryRequest")) {
				
				selectTimeWindowTourPanel.setVisible(true);
				selectCourierTourPanel.setVisible(true);
				courierNameListTour.setSelectedIndex(0);
				timeWindowListTour.setSelectedIndex(0);
				
			}
			
		} else {
			
			setIndicationLabel("<html> Legend <ul style=\"list-style-type: none\">"+
								"<li style=\"font-display: none;\"> <span style=\"color: rgb(0, 255, 51);\">■</span> : selected intersection once clicked</li>"+
								"<li> <span style=\"color: rgb(0, 255, 51);\">―</span> : selected road segment once clicked </span></li>"+
								"<li>  <span style=\"color: rgb(255, 105, 180);\">■</span> : warehouse </span></li>"+
								"<li>  <span style=\"color: blue;\">■</span> : non-assigned delivery request to a tour </span></li>"+
								"<li>  <span style=\"color: black;\">■</span> : assigned delivery request to a tour </span></li>"+
								"</ul></html>");

			
			buttonLoadCityMap.setEnabled(true);
			buttonLoadDeliveryRequests.setEnabled(true);
			buttonManageDeliveryRequests.setEnabled(true);
			buttonAssignDeliveryRequest.setEnabled(true); 
			buttonComputeBestTour.setEnabled(true);
			buttonGenerateRoadMap.setEnabled(true);
			buttonManageCourier.setEnabled(true);
			
			buttonAddDeliveryRequestToComputedTour.setEnabled(true);
			buttonRemoveDeliveryRequestToComputedTour.setEnabled(true);
			buttonUndo.setEnabled(true);
			buttonRedo.setEnabled(true);
			
		}
		
		repaint();
		setVisible(true);
		
	}
	
	/**
	 * Sets the mode for managing the couriers.
	 */
	public void setManageCourierMode(List<Courier> listCouriers) {
		
		setIndicationLabel("<html><font size='6'>Courier manager </font><html>");

		textualView.setCourierManagementMode(listCouriers);

		createDeliveryRequestPanel.setVisible(false);
		initializeTourPanel.setVisible(false);
		modifyDeliveryRequestsPanel.setVisible(false);
		modifyTourPanel.setVisible(false);
		manageCourierPanel.setVisible(true);
		selectTourPanel.setVisible(false);
		
		buttonLoadCityMap.setEnabled(true);
		
	}
	
	
}
