package controller;

/**
 * defines a command interface
 * @author Barak Kenan
 */

public interface Command 
{
	/**
	 * defines what each command should do
	 * @param args array of string parameters
	 */
	public void doCommand(String[]args);
}
