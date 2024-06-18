package model;

/**
 * Represents a warehouse, a special type of intersection.
 * @author H4114
 */
public class Warehouse extends Intersection {
	
	/**
	 * Default constructor.
	 */
    public Warehouse() {
    }
    
    /**
     * Creates a warehouse with specified attributes.
     *
     * @param idIntersection 	The unique identifier of the intersection.
     * @param latitude 			The latitude coordinate of the intersection.
     * @param longitude 		The longitude coordinate of the intersection.
     */
    public Warehouse(Long idIntersection, Double latitude, Double longitude) {
    	
    	super(idIntersection, latitude, longitude);
    	
    }

    /**
     * Returns a string representation of the warehouse.
     *
     * @return A string containing warehouse details.
     */
    @Override
    public String toString() {
    	
        return "Warehouse [idIntersection=" + getIdIntersection() + ", latitude=" + getLatitude() + ", longitude=" + getLongitude() + "]";
        
    }

}


