package view;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import controller.Controller;

/**
 * MouseListeners is a class that implements various mouse-related event listeners.
 * It handles mouse wheel rotation, dragging, pressing, clicking, and movement events.
 * @author H4114
 */
public class MouseListeners implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	private Point startPoint;
	private Point endPoint;
	private Controller controller;
	
	/**
	 * Constructs a MouseListeners object with default values.
	 */
    public MouseListeners() {
    }

    /**
	 * Constructs a MouseListeners object with the specified controller.
	 *
	 * @param controller The controller responsible for handling mouse events.
	 */
    public MouseListeners(Controller controller) {
        
    	this.startPoint = new Point();
		this.endPoint = new Point();
		this.controller = controller;
    	
    }
    
    /**
	 * Invoked when the mouse wheel is rotated.
	 * 
	 * @param e The MouseWheelEvent object containing information about the event.
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		controller.mouseWheel(e.getWheelRotation(), e.getPoint());
		
	}
	
	/**
	 * Invoked when the mouse is dragged.
	 * 
	 * @param e The MouseEvent object containing information about the event.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		
		endPoint = e.getPoint();
		controller.mouseDrag(new Point(endPoint.x - startPoint.x, endPoint.y - startPoint.y));
		startPoint = endPoint;	
		
	}
	
	/**
	 * Invoked when a mouse button is pressed.
	 * 
	 * @param e The MouseEvent object containing information about the event.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		startPoint = e.getPoint();
		
	}
	
	/**
	 * Invoked when the mouse is clicked.
	 * 
	 * @param e The MouseEvent object containing information about the event.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {	
		
		controller.leftClick(e.getPoint());
		
	}
	
	/**
	 * Invoked when the mouse is moved.
	 * 
	 * @param e The MouseEvent object containing information about the event.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Invoked when a mouse button is released.
	 * 
	 * @param e The MouseEvent object containing information about the event.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Invoked when the mouse enters a component.
	 * 
	 * @param e The MouseEvent object containing information about the event.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Invoked when the mouse exits a component.
	 * 
	 * @param e The MouseEvent object containing information about the event.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
