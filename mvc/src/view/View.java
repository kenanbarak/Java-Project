package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;

/**
 * defines a view in mvc
 * @author Barak Kenan
 */
public interface View 
{
	/**
	 * start the CLI
	 */
	public void start();
	
	/**
	 * initialize the commands
	 */
	public void setCommands(HashMap<String, Command> commands);
	
	
	
	/**
	 * display the errors the user made, like invalid input
	 * @param error a sting describe what is the error
	 */
	public void viewError(String error);
	
	/**
	 * display all the commands
	 */
	public void viewMenu();

	
	/**
	 * display the files and directories in this path
	 * @param dirContent string of the files and directories in this path
	 */
	public void viewDirPath(String dirContent);
	
	/**
	 * display "maze is ready" after generate it
	 * @param str "maze is ready"
	 */
	public void viewGenerate3dMaze(String str);
	
	/**
	 * display the maze
	 * @param byteArr byte array representing the maze
	 */
	public void viewDisplayName(byte[] byteArr);
	
	/**
	 * display the maze's cross section by x/y/z in this index
	 * @param crossSection 2d array of the cross section the user asked 
	 */
	public void viewDisplayCrossSectionBy(int[][] crossSection);
	
	/**
	 * display "maze has been saved" after save it
	 * @param str "maze has been saved"
	 */
	public void viewSaveMaze(String str);
	
	/**
	 * display "maze has been loaded" after load it
	 * @param str "maze has been loaded"
	 */
	public void viewLoadMaze(String str);
	
	/**
	 * display "solution is ready" after solve it
	 * @param str "solution is ready"
	 */
	public void viewSolve(String str);
	
	/**
	 * display the solution of the maze
	 * @param solution maze's solution
	 */
	public void viewDisplaySolution(Solution<Position> solution);

}
