package controller;

import java.util.LinkedList;

/**
 * This class represents a list of commands that can be executed, undone, redone, and reset. 
 * @author H4114
 */
public class CommandList {
	
	private LinkedList<Command> listCommands;
	private int indiceCrt;
	
	/**
	 * Creates a list of commands.
	 */
	public CommandList() {
		
		indiceCrt = -1;
		listCommands = new LinkedList<Command>();
		
	}
	
	/**
	 * Adds a command to the list of commands.
	 * 
	 * @param command Command to be added.
	 */
	public void add(Command command) {
		
		int i = indiceCrt + 1;
		
		while (i < listCommands.size()) {
			
			listCommands.remove(i);
			
		}
		
		indiceCrt++;
		listCommands.add(indiceCrt, command);
		command.doCommand();
		
	}
	
	/**
	 * Cancel temporarily the last added command.
	 */
	public void undo() {
		
		if (indiceCrt >= 0) {
			
			Command command = listCommands.get(indiceCrt);
			indiceCrt--;
			command.undoCommand();
			
		}
		
	}
	
	
	/**
	 * Cancel definitely the last added command.
	 */
	public void cancel() {
		
		if (indiceCrt >= 0) {
			
			Command command = listCommands.get(indiceCrt);
			listCommands.remove(indiceCrt);
			indiceCrt--;
			command.undoCommand();
			
		}
		
	}
	
	/**
	 * Put back in the list of commands the last undone command.
	 */
	public void redo() {
		
		if (indiceCrt < listCommands.size() - 1) {
			
			indiceCrt++;
			Command command = listCommands.get(indiceCrt);
			command.doCommand();
			
		}
		
	}
	
	/**
	 * Remove definitely all the commands from the list of commands.
	 */
	public void reset() {
		
		indiceCrt = -1;
		listCommands.clear();
		
	}
	
}
