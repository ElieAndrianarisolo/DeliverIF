package controller;

/**
 * Represents a command.
 * @author H4114
 */
public interface Command {
	
	/**
	 * Execute the command.
	 */
	public void doCommand();

	/**
	 * Execute the opposite command.
	 */
	public void undoCommand();
	
}
