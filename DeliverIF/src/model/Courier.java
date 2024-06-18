package model;

import java.util.Objects;
import java.util.Random;
import java.awt.Color;	

/**
 * Represents a courier involved in a tour.
 * @author H4114
 */
public class Courier {

    private Integer idCourier;
    private String name;
	private Color color;					 

    /**
     * Default constructor for creating a courier.
     */
    public Courier() {
    }

    /**
     * Creates a courier with specified parameters.
     *
     * @param idCourier The unique identifier of the courier.
     * @param name The first name of the courier.
     */
    public Courier(Integer idCourier, String name) {
        
        this.idCourier = idCourier;
        this.name = name;
        setColor();
        
    }

    /**
     * Gets the unique identifier of the courier.
     *
     * @return The unique identifier.
     */
    public Integer getIdCourier() {
        
        return idCourier;
        
    }

    /**
     * Gets the first name of the courier.
     *
     * @return The first name.
     */
    public String getFirstName() {
        
        return name;
        
    }
    
    /**
     * Gets the color associated with the courier.
     *
     * @return The color.
     */
    public Color getColor() {
    	
		return color;
		
	}

    /**
     * Sets the unique identifier of the courier.
     *
     * @param idCourier The unique identifier to set.
     */
    public void setIdCourier(Integer idCourier) {
        
        this.idCourier = idCourier;
        
    }

    /**
     * Sets the first name of the courier.
     *
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        
        this.name = firstName;
        
    }
    
    /**
     * Sets the color of the courier.
     */
    public void setColor() {
    	
		// Ensure the color is neither too dark nor too bright
        int minBrightness = 64;  // Adjust as needed
        int maxBrightness = 192; // Adjust as needed

    	Random random = new Random();

        // Ensure that at least one component has a high value to make the color bright
        int brightComponent = random.nextInt(256);
        int lowComponent1 = random.nextInt(128);
        int lowComponent2 = random.nextInt(128);

        // Adjust brightness
        int brightness = (brightComponent + lowComponent1 + lowComponent2) / 3;
        
        while (brightness < minBrightness || brightness > maxBrightness) {
        	
            brightComponent = random.nextInt(256);
            lowComponent1 = random.nextInt(128);
            lowComponent2 = random.nextInt(128);
            brightness = (brightComponent + lowComponent1 + lowComponent2) / 3;
            
        }

        // Randomly swap the positions of the components to add variety
        switch (random.nextInt(6)) {
        
            case 0 -> this.color =  new Color(brightComponent, lowComponent1, lowComponent2);
            case 1 -> this.color = new Color(brightComponent, lowComponent2, lowComponent1);
            case 2 -> this.color =new Color(lowComponent1, brightComponent, lowComponent2);
            case 3 -> this.color = new Color(lowComponent1, lowComponent2, brightComponent);
            case 4 -> this.color = new Color(lowComponent2, brightComponent, lowComponent1);
            default -> this.color = new Color(lowComponent2, lowComponent1, brightComponent);
            
        }
        
    }	
	
    /**
     * Computes the hash code for a courier based on its unique identifier, name, and color.
     *
     * @return The hash code for a courier.
     */
    @Override
    public int hashCode() {
    	
        return Objects.hash(idCourier, name, color);
        
    }
    
    /**
     * Indicates whether some other object is "equal to" this one.
     * Two couriers are considered equal if they have the same unique identifier.
     *
     * @param obj 			The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
    	
        if (this == obj) return true;
        
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Courier other = (Courier) obj;
        
        return Objects.equals(idCourier, other.idCourier);
        
    }
    
    /**
     * Provides a string representation of the courier.
     *
     * @return A string representation of the courier.
     */
    @Override
    public String toString() {

        return "Courier [idCourier=" + idCourier + ", name=" + name + ", color=" + color + "]";
        
    }

}
