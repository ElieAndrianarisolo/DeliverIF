package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import algo.Dijkstra;
import algo.Pair;
import algo.TSP;
import algo.TSP3;
import algo.TimeWindowGraph;
import algo.Graph;

/**
 * Represents a tour for delivering goods.
 * @author H4114
 */
public class Tour {

    private static final int MAX_TIME = 10000;
    private static final double SPEED_OF_COURIER = 15/3.6; // 15 km/h
	private static final int START_OF_TOUR = 8*60*60;      // 8 hours
	private static final int TIME_TO_DELIVER = 5*60;       // 5 minutes

    private Integer idTour;
    private double length;
    private Courier courier;
    private List<RoadSegment> listRoadSegments;
    private List<DeliveryRequest> listDeliveryRequests;
    private Warehouse warehouse;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Default constructor for creating an empty tour.
     */
    public Tour() {
    	
        this.listDeliveryRequests = new ArrayList<DeliveryRequest>();
        this.listRoadSegments = new ArrayList<RoadSegment>();
        
    }
    
    /**
     * Creates a tour with a specified warehouse.
     * 
	 * @param idTour 	The unique identifier of the tour.
     * @param warehouse The warehouse associated with the tour.
     */
    public Tour(Integer idTour, Warehouse warehouse) {
    	
    	this.idTour = idTour;
        this.warehouse = warehouse;
        this.courier = null;
        this.listRoadSegments = new ArrayList<RoadSegment>();
        this.listDeliveryRequests = new ArrayList<DeliveryRequest>();
        this.length = 0;
        
    }
    
    /**
     * Creates a tour with a specified warehouse and courier.
     * 
	 * @param idTour 		The unique identifier of the tour.
     * @param warehouse 	The warehouse associated with the tour.
     * @param courier 		The courier assigned to the tour.
     */
    public Tour(Integer idTour, Warehouse warehouse, Courier courier) {
    	
    	this.idTour = idTour;
        this.warehouse = warehouse;
        this.courier = courier;
        this.listRoadSegments = new ArrayList<RoadSegment>();
        this.listDeliveryRequests = new ArrayList<DeliveryRequest>();
        this.length = 0;
        
    }

    /**
     * Creates a tour with specified parameters.
     *
	 * @param idTour 				The unique identifier of the tour.
     * @param warehouse 			The warehouse associated with the tour.
     * @param listDeliveryRequests 	List of delivery requests in the tour.
     * @param courier 				The courier assigned to the tour.
     */
    public Tour(Integer idTour, Warehouse warehouse, List<DeliveryRequest> listDeliveryRequests, Courier courier) {
    	
    	this.idTour = idTour;
        this.warehouse = warehouse;
        this.courier = courier;
        this.listDeliveryRequests = listDeliveryRequests;
        this.listRoadSegments = new ArrayList<RoadSegment>();
        this.length = 0;
        
    }
    

    /**
     * Resets the tour by clearing the list of road segments and delivery requests.
     */
    public void reset() {
    	
        listRoadSegments.clear();
        listDeliveryRequests.clear();
        warehouse = null;
        
    }

    /**
     * Gets the total length of the tour.
     *
     * @return The total length of the tour.
     */
    public double getLength() {
    	
        return length;
        
    }

    /**
     * Gets the warehouse associated with the tour.
     *
     * @return The warehouse associated with the tour.
     */
    public Warehouse getWarehouse() {
    	
        return warehouse;
        
    }

    /**
     * Gets the list of delivery requests in the tour.
     *
     * @return List of delivery requests in the tour.
     */
    public List<DeliveryRequest> getListDeliveryRequests() {
    	
        return listDeliveryRequests;
        
    }

    /**
     * Gets the list of road segments in the tour.
     *
     * @return List of road segments in the tour.
     */
    public List<RoadSegment> getListRoadSegments() {
    	
        return listRoadSegments;
        
    }

    /**
     * Gets the courier assigned to the tour.
     *
     * @return The courier assigned to the tour.
     */
    public Courier getCourier() {
    	
        return courier;
        
    }
    
    /**
     * Gets the unique identifier of the tour.
     *
     * @return The unique identifier associated with the tour.
     */
    public Integer getIdTour() {
    	
        return idTour;
        
    }

    /** 
	 * Gets the starting time of the tour.
	 * 
	 * @return endTime The starting time of the tour.
	 */
    public LocalTime getStartTime() {

        return startTime;
        
    }

    /** 
	 * Gets the ending time of the tour.
	 * 
	 * @return endTime The ending time of the tour.
	 */
    public LocalTime getEndTime() {

        return endTime;
        
    }

    /**
     * Sets the total length of the tour.
     *
     * @param length The total length of the tour.
     */
    public void setLength(double length) {
    	
        this.length = length;
        
    }

    /**
     * Sets the list of delivery requests in the tour.
     *
     * @param listDeliveryRequests List of delivery requests in the tour.
     */
    public void setDeliveryRequests(List<DeliveryRequest> listDeliveryRequests) {
    	
        this.listDeliveryRequests = listDeliveryRequests;
        
    }

    /**
     * Sets the list of road segments in the tour.
     *
     * @param listRoadSegments List of road segments in the tour.
     */
    public void setListRoadSegments(List<RoadSegment> listRoadSegments) {
    	
        this.listRoadSegments = listRoadSegments;
        
    }

    /**
     * Sets the courier assigned to the tour.
     *
     * @param courier The courier assigned to the tour.
     */
    public void setCourier(Courier courier) {
    	
        this.courier = courier;
        
    }
    
    /**
     * Sets the starting time of the tour
     *
     * @param startTime The starting time of a tour to set.
     */
    public void setStartTime(LocalTime startTime) {
    	
        this.startTime = startTime;
        
    }
    
    /**
     * Sets the ending time of the tour
     *
     * @param endTime The ending time of a tour to set.
     */
    public void setEndTime(LocalTime endTime) {
    	
        this.endTime = endTime;
        
    }
    
    /**
     * Sets the warehouse of the tour
     *
     * @param warehouse The warehouse to set.
     */
    public void setWarehouse(Warehouse warehouse) {
    	
        this.warehouse = warehouse;
        
    }

    /**
     * Adds a delivery request to the tour.
     *
     * @param deliveryRequest The delivery request to be added.
     */
    public void addDeliveryRequest(DeliveryRequest deliveryRequest) {
    	
        listDeliveryRequests.add(deliveryRequest);
        
    }

    /**
     * Removes a delivery request from the tour based on its position in the list.
     *
     * @param position The position of the delivery request to be removed.
     */
    public void removeDeliveryRequest(int position) {
    	
        listDeliveryRequests.remove(position);
        
    }
    
    /**
     * Removes all the road segments from the tour.
     *
     */
    public void clearListRoadSegments() {
		
    	listRoadSegments.clear();
		
	}

    /**
     * Provides a string representation of the tour.
     *
     * @return A string representation of the tour.
     */
    @Override
    public String toString() {
    	
        return "Tour [length="+ length + ", courier=" + courier + ", listRoadSegments="+ listRoadSegments + ", listDeliveryRequests="+ listDeliveryRequests + ",warehouse=" + warehouse + "]";
    
    }

    /** Finds an optimized tour to visit all the delivery points.
     * 
	 * @param map The used city map.
     * @return boolean state to say if it worked or not.
	 */
    public Boolean computeBestPath(CityMap map) {
        
    	Graph graph = new Graph();
		
        for (RoadSegment rs : map.getListRoadSegments()) {
			
        	graph.addEdge(rs);
            
		}

        // Create target list (input)
		List<Integer> selectedN = new ArrayList<Integer>();
		List<Integer> timeWindowList = new ArrayList<Integer>();
		Intersection intersection;
		
		intersection = new Intersection(warehouse.getIdIntersection(), warehouse.getLatitude(), warehouse.getLongitude());
        selectedN.add(graph.R_2_i.get(intersection));
        timeWindowList.add(START_OF_TOUR/60/60 - 1);
        
        for (DeliveryRequest dr : this.listDeliveryRequests) {
        	
        	timeWindowList.add(dr.getTimeWindow());
        	intersection = new Intersection(dr.getIdIntersection(), dr.getLatitude(), dr.getLongitude());
            selectedN.add(graph.R_2_i.get(intersection));
            
        }
  
		// Calculate Dijkstra for every target
		Integer[][] matrixMap = new Integer[graph.getSize()][graph.getSize()];
		Double[][] costMatrix = new Double[selectedN.size()][selectedN.size()];
        Pair<Integer[],Double[]> res;
        int i = 0;
        
        for (Integer n : selectedN) {

			res = Dijkstra.dijkstra(graph.data, n);
            matrixMap[n] = res.getFirst().clone(); 

			int j = 0;
			for (Integer m : selectedN) {
				
				costMatrix[i][j] = res.getSecond()[m]; 
				j++;
				
			}
			
			i++;
			
		}

        Integer[] orderedN = new Integer[costMatrix.length];

        /* *** STEP 1 : GENERATE COMPLETE DIRECTED GRAPH ***

            * vertices = delivery locations 
            * arcs = quickest path between each vertex
            
            * memorize the segments of each quickest path
         */

        int[] timeWindows = new int[timeWindowList.size()]; 
        
        for(int k = 0; k<timeWindows.length; k++)  timeWindows[k] = timeWindowList.get(k);
        
        TimeWindowGraph graph2 = new TimeWindowGraph(costMatrix, timeWindows);

        /* *** STEP 2 : CUT THE ARCS THAT DON'T RESPECT TIME WINDOW COSTRAINTS *** */
        
        graph2.removeArcsToSatisfyTimeConstraints();
        
        /* *** STEP 3 : FIND THE OPTIMIZED TOUR TO VISIT EVERY DELIVERY LOCATION *** */
        
        TSP tsp = new TSP3();
        tsp.searchSolution(MAX_TIME, graph2);

        length = tsp.getSolutionCost();

        int[] bestTour = new int[graph2.getNbVertices()];
        List<DeliveryRequest> deliveryTour = new ArrayList<DeliveryRequest>();
        int[] timeWindowsSorted = new int[timeWindowList.size()];
        timeWindowsSorted[0] = START_OF_TOUR/60/60 - 1;
       
        for(i = 0; i < graph2.getNbVertices(); i++) {
        	
			bestTour[i] = tsp.getSolution(i);
			orderedN[i] = selectedN.get(bestTour[i]);

			if(bestTour[i] != 0) {
				
	            deliveryTour.add(listDeliveryRequests.get(bestTour[i]-1));
	            timeWindowsSorted[i] =  listDeliveryRequests.get(bestTour[i]-1).getTimeWindow();
				
			}
			
		}
       
        listDeliveryRequests = deliveryTour;

        /* *** STEP 4 : CALCULATE THE TIME TO ARRIVE WAIT AND LEAVE EVERY DELIVERY LOCATION *** */
        int time = START_OF_TOUR; // Time passing
        int wait;                 // Time a courier has to wait
        boolean isValid = true;   // Validity of a tour = if it respects time constraints
       
        // Starting at the warehouse
		startTime = LocalTime.ofSecondOfDay(time);
		for (i = 1; i < bestTour.length; i++){ //for each delivery point
            
			wait = 0;
			
            // Time for the courier to go from one place to another
			time += graph2.getCost(bestTour[i-1], bestTour[i])/SPEED_OF_COURIER;

            // Checking time window constraints
            if (time < timeWindowsSorted[i]*60*60) {        // Arriving before the time window -> wait
            	
                wait = timeWindowsSorted[i]*60*60 - time;
                
            } else if (time >= timeWindowsSorted[i]*60*60) { // Arriving after the time window -> tour invalid
               
            	isValid = false;
            	
            }
            
            listDeliveryRequests.get(i-1).setDepartureTime(LocalTime.ofSecondOfDay(time));
            listDeliveryRequests.get(i-1).setWaitingTime(LocalTime.ofSecondOfDay(wait));
            
            // Time for the courier to (wait and) deliver
			time += wait + TIME_TO_DELIVER;
			listDeliveryRequests.get(i-1).setArrivalTime(LocalTime.ofSecondOfDay(time));
			
		}
		
		time += graph2.getCost(bestTour[bestTour.length-1], bestTour[0])/SPEED_OF_COURIER;
		endTime = LocalTime.ofSecondOfDay(time);


        
		// Get the ordered list of the nodes to go to make a tour
		List<Integer> fullPath = new ArrayList<>();
		List<Integer> intermediateN = new ArrayList<>();
		Integer source, target;
		int j;
		
		for (i=0; i<orderedN.length; ++i) { 
			
			source = orderedN[i];
			
			if (i == orderedN.length-1) {
				
				target = orderedN[0];
				
			} else {
				
				target = orderedN[i+1];
				
			}
			
			j=0;
			intermediateN.clear();
			
			while (source != target && j<10000) {
				
				target = matrixMap[source][target]; // Follow the predecessor
				intermediateN.add(target);
				j++;
				
			}

			Collections.reverse(intermediateN);
			
			for (Integer n : intermediateN) {
				
				fullPath.add(n);
				
			}

		}

        Intersection startingIntersection = null;
        Intersection endingIntersection = null;
        
        listRoadSegments.clear();
        
        for (int position = 0; position < fullPath.size(); position++) {
            
        	
        	if (position == fullPath.size()-1) {
				
        		startingIntersection = graph.i_2_R.get(fullPath.get(position));
				endingIntersection = graph.i_2_R.get(fullPath.get(0));
				listRoadSegments.add(map.findCorrespondingRoadSegment(startingIntersection, endingIntersection));
				
			} else {
				
				startingIntersection = graph.i_2_R.get(fullPath.get(position));
				endingIntersection = graph.i_2_R.get(fullPath.get(position+1));
				RoadSegment roadSegmentToAdd = map.findCorrespondingRoadSegment(startingIntersection, endingIntersection);

				listRoadSegments.add(roadSegmentToAdd);

			}
        	
        }
        
        return isValid;
        
    }

}





