package model;

import java.time.LocalTime;

/**
 * Represents a delivery request, a special type of intersection.
 * @author H4114
 * 
 */
public class DeliveryRequest extends Intersection {
    
    private int timeWindow;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalTime waitingTime;
    final private int unloadingTime = 300; // in seconds
    
    /**
     * Default constructor for creating a delivery request.
     */
    public DeliveryRequest() {
    }

    /**
     * Creates a delivery request with specified parameters.
     *
     * @param idIntersection 	The unique identifier of the intersection.
     * @param latitude 			The latitude coordinate of the intersection.
     * @param longitude 		The longitude coordinate of the intersection.
     * @param timeWindow 		The time window for the delivery request.
     */
    public DeliveryRequest(Long idIntersection, Double latitude, Double longitude, int timeWindow) {
        
        super(idIntersection, latitude, longitude);
        
        this.departureTime = null;
        this.arrivalTime = null;
        this.waitingTime = null;
        this.timeWindow = timeWindow;
        
    }
    
    /**
     * Creates a delivery request with specified parameters, including departure and arrival times.
     *
     * @param idIntersection 	The unique identifier of the intersection.
     * @param latitude 			The latitude coordinate of the intersection.
     * @param longitude 		The longitude coordinate of the intersection.
     * @param timeWindow 		The time window for the delivery request.
     * @param departureTime 	The departure time of the delivery request.
     * @param arrivalTime 		The arrival time of the delivery request.
     */
    public DeliveryRequest(Long idIntersection, Double latitude, Double longitude, int timeWindow, LocalTime departureTime, LocalTime arrivalTime) {
        
        super(idIntersection, latitude, longitude);
        
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.waitingTime = null;
        this.timeWindow = timeWindow;
        
    }

    /**
     * Gets the departure time of the delivery request.
     *
     * @return The departure time.
     */
    public LocalTime getDepartureTime() {
        
        return departureTime;
        
    }

    /**
     * Gets the arrival time of the delivery request.
     *
     * @return The arrival time.
     */
    public LocalTime getArrivalTime() {
        
        return arrivalTime;
        
    }
    
    /**
     * Gets the waiting time of the delivery request.
     *
     * @return The waiting time.
     */
    public LocalTime getWaitingTime() {
        
        return waitingTime;
        
    }
    
    /**
     * Gets the time window for the delivery request.
     *
     * @return The time window.
     */
    public int getTimeWindow() {
        
        return timeWindow;
        
    }
    
    /**
     * Gets the unloading time for the delivery request.
     *
     * @return The unloading time in seconds.
     */
    public int getUnloadingTime() {
        
        return unloadingTime;
        
    }

    /**
     * Sets the departure time of the delivery request.
     *
     * @param departureTime The departure time to set.
     */
    public void setDepartureTime(LocalTime departureTime) {
        
        this.departureTime = departureTime;
        
    }

    /**
     * Sets the arrival time of the delivery request.
     *
     * @param arrivalTime The arrival time to set.
     */
    public void setArrivalTime(LocalTime arrivalTime) {
        
        this.arrivalTime = arrivalTime;
        
    }
    
    /**
     * Sets the waiting time of the delivery request.
     *
     * @param waitingTime The waiting time to set.
     */
    public void setWaitingTime(LocalTime waitingTime) {
        
        this.waitingTime = waitingTime;
        
    }
    
    /**
     * Sets the time window for the delivery request.
     *
     * @param timeWindow The time window to set.
     */
    public void setTimeWindow(int timeWindow) {
        
        this.timeWindow = timeWindow;
        
    }

    /**
     * Provides a string representation of the delivery request.
     *
     * @return A string representation of the delivery request.
     */
    @Override
    public String toString() {
        
        return "DeliveryRequest [idIntersection=" + getIdIntersection() + ", latitude=" + getLatitude() + ", longitude=" + getLongitude() + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", timeWindow=" + timeWindow + ", deliveringTime=" + unloadingTime +" s, waitingTime=" + waitingTime +"]";
    	
    }

}
