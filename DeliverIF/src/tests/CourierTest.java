package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import model.Courier;

/***
 * @author H4114
 * @see model.Courier
 */
public class CourierTest {
	
	//Fields
	Courier courier;

	//Initialize the courier
	@Before
	public void setUp(){
		courier=new Courier();
		assertNotNull(courier);
		courier = new Courier(1, "Tony");
		assertNotNull(courier);
	}
	
	/***
	 * Test Courier's getters
	 * 
	 * @see model.Courier#getFirstName()
	 * @see model.Courier#getColor()
	 * @see model.Courier#getIdCourier()
	 */
	@Test
	public void gettersTest(){

		assertEquals("The name getter don't work","Tony",courier.getFirstName());
		assertEquals("The Color's getter don't work",(new Color(1)).getClass(),courier.getColor().getClass());
		assertEquals("The id getter don't work",Integer.valueOf(1),courier.getIdCourier());
		
	}
	
	/***
	 * Test Courier's setters
	 * 
	 * @see model.Courier#setFirstName(String)
	 * @see model.Courier#setColor()
	 * @see model.Courier#setIdCourier(Integer)
	 */
	@Test
	public void settersTest(){

		Color oldColor = courier.getColor();
		courier.setFirstName("Nicolas");
		courier.setColor();
		courier.setIdCourier(2);

		assertEquals("The name setting did not happen","Nicolas",courier.getFirstName());
		assertNotEquals("The color setting did not happen",oldColor, courier.getColor());
		assertEquals("The color setting did not happen",Integer.valueOf(2), courier.getIdCourier());
		
	}
	
	/**
	 * Test the string representation of a Courier.
	 * <p>
	 * This method tests the correctness of the string representation of a Courier by comparing
	 * it to the expected result. It checks that the generated string matches the specified format.
	 *
	 * @see Courier
	 */
	@Test
	public void toStringTest() {
		String color=courier.getColor().toString();
		// Asserting that the generated string matches the expected result
		assertEquals("The method toString don't work","Courier [idCourier=1, name=Tony, color="+color+"]", courier.toString());
	}

}





