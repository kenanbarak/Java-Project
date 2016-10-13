package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import presenter.Properties;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * defines a view in mvp
 * @author Barak Kenan
 */
public interface View
{
	/**
	 * start the CLI
	 */
	public void start() throws FileNotFoundException, IOException;
	
	
	/**
	 * display all the commands
	 */
	public void viewMenu();

	/**
	 * display the given string
	 * @param msg the message
	 */
	public void viewDisplayMessage(String msg);

	/**
	 * display the maze
	 * @param byteArr byte array representing the maze
	 */
	public void viewDisplayMaze(byte[] byteArr);
	
	/**
	 * display the maze's cross section by x/y/z in this index
	 * @param crossSection 2d array of the cross section the user asked 
	 */
	public void viewDisplayCrossSectionBy(int[][] crossSection);
	
	/**
	 * display the solution of the maze
	 * @param solution maze's solution
	 */
	public void viewDisplaySolution(Solution<Position> solution);
	
	public void resetProperties(Properties pro);
}
