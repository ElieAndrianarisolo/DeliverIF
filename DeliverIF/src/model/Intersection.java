package model;

import java.util.Objects;

/**
 * Represents an intersection in the city map.
 * @author H4114
 */
public class Intersection {

    private Long idIntersection;
    private Double latitude;
    private Double longitude;
    
    /**
     * Default constructor for creating an intersection.
     */
    public Intersection() {
    }

    /**
     * Creates an intersection with specified parameters.
     *
     * @param idIntersection 	The unique identifier of the intersection.
     * @param latitude 			The latitude coordinate of the intersection.
     * @param longitude 		The longitude coordinate of the intersection.
     */
    public Intersection(Long idIntersection, Double latitude, Double longitude) {
    	
        this.idIntersection = idIntersection;
        this.latitude = latitude;
        this.longitude = longitude;
        
    }
    
    /**
     * Gets the unique identifier of the intersection.
     *
     * @return The unique identifier of the intersection.
     */
    public Long getIdIntersection() {
    	
        return idIntersection;
        
    }

    /**
     * Gets the latitude coordinate of the intersection.
     *
     * @return The latitude coordinate.
     */
    public Double getLatitude() {
    	
        return latitude;
        
    }
    

    /**
     * Gets the longitude coordinate of the intersection.
     *
     * @return The longitude coordinate.
     */
    public Double getLongitude() {
    	
        return longitude;
        
    }

    /**
     * Sets the unique identifier of the intersection.
     *
     * @param idIntersection The unique identifier to set.
     */
    public void setIdIntersection(Long idIntersection) {
    	
        this.idIntersection = idIntersection;
        
    }

    /**
     * Sets the latitude coordinate of the intersection.
     *
     * @param latitude The latitude coordinate to set.
     */
    public void setLatitude(Double latitude) {
    	
        this.latitude = latitude;
        
    }

    /**
     * Sets the longitude coordinate of the intersection.
     *
     * @param longitude The longitude coordinate to set.
     */
    public void setLongitude(Double longitude) {
    	
        this.longitude = longitude;
        
    }
    
    /**
     * Computes the hash code for an intersection based on its unique identifier, latitude, and longitude.
     *
     * @return The hash code for an intersection.
     */
    @Override
    public int hashCode() {
    	
        return Objects.hash(idIntersection, latitude, longitude);
        
    }
    
    /**
     * Indicates whether some other object is "equal to" this one.
     * Two intersections are considered equal if they have the same unique identifier.
     *
     * @param obj 			The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
    	
        if (this == obj) return true;
        
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Intersection other = (Intersection) obj;
        
        return Objects.equals(idIntersection, other.idIntersection);
        
    }
    
    @Override
    public String toString() {
    	
        return "Intersection [idIntersection=" + idIntersection + ", latitude=" + latitude + ", longitude=" + longitude + "]";
        
    }
    
}
