package model;

/**
 * Represents a road segment connecting two intersections.
 * @author H4114
 */
public class RoadSegment {

    private String streetName;
    private Intersection startingIntersection;
    private Intersection endingIntersection;
    private double length;
    
    /**
     * Default constructor for creating a road segment.
     */
    public RoadSegment() {
    }

    /**
     * Creates a road segment with specified parameters.
     *
     * @param streetName 			The name of the street the road segment is on.
     * @param startingIntersection 	The starting intersection of the road segment.
     * @param endingIntersection 	The ending intersection of the road segment.
     * @param length 				The length of the road segment.
     */
    public RoadSegment(String streetName, Intersection startingIntersection, Intersection endingIntersection, double length) {
    	
        this.streetName = streetName;
        this.startingIntersection = startingIntersection;
        this.endingIntersection = endingIntersection;
        this.length = length;
        
    }

    /**
     * Gets the name of the street the road segment is on.
     *
     * @return The street name.
     */
    public String getStreetName() {
    	
        return streetName;
        
    }

    /**
     * Gets the starting intersection of the road segment.
     *
     * @return The starting intersection.
     */
    public Intersection getStartingIntersection() {
    	
        return startingIntersection;
        
    }

    /**
     * Gets the ending intersection of the road segment.
     *
     * @return The ending intersection.
     */
    public Intersection getEndingIntersection() {
    	
        return endingIntersection;
        
    }

    /**
     * Gets the length of the road segment.
     *
     * @return The length of the road segment.
     */
    public double getLength() {
    	
        return length;
        
    }

    /**
     * Sets the name of the street the road segment is on.
     *
     * @param streetName The street name to set.
     */
    public void setStreetName(String streetName) {
    	
        this.streetName = streetName;
        
    }

    /**
     * Sets the starting intersection of the road segment.
     *
     * @param startingIntersection The starting intersection to set.
     */
    public void setStartingIntersection(Intersection startingIntersection) {
    	
        this.startingIntersection = startingIntersection;
        
    }

    /**
     * Sets the ending intersection of the road segment.
     *
     * @param endingIntersection The ending intersection to set.
     */
    public void setEndingIntersection(Intersection endingIntersection) {
    	
        this.endingIntersection = endingIntersection;
        
    }

    /**
     * Sets the length of the road segment.
     *
     * @param length The length to set.
     */
    public void setLength(double length) {
    	
        this.length = length;
        
    }

    /**
     * Provides a string representation of the road segment.
     *
     * @return A string representation of the road segment.
     */
    @Override
    public String toString() {
    	
        return "RoadSegment [streetName=" + streetName + ", startingIntersection=" + startingIntersection + ", endingIntersection="
				+ endingIntersection + ", length=" + length + "]";
        
    }

}