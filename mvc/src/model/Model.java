package model;

/**
 * defines a model in mvc
 * @author Barak Kenan
 */

public interface Model 
{
	/**
	 * display the files and directories in this path
	 * @param path the asked path
	 */
	public void modelDirPath(String[] args);

	/**
	 * generate a maze with the this name and xyz sizes according to simple or dfs algorithm
	 * @param args array of strings with the parameter/s: maze's name, xyz sises, simple/growing tree algorithm
	 */
	public void modelGenerate3dMaze(String[] args);

	/**
	 * display the maze
	 * @param name maze's name
	 */
	public void modelDisplayName(String[] args);

	/**
	 * display the maze's cross section by x/y/z in this index
	 * @param args array of strings with the parameter/s: {x,y,z}, index, maze's name 
	 */
	public void modelDisplayCrossSectionBy(String[] args);

	/**
	 * save the maze in this file
	 * @param args array of strings with the parameter/s: maze's name, file's name 
	 */
	public void modelSaveMaze(String[] args);

	/**
	 * load the maze from this file
	 * @param args array of strings with the parameter/s: file's name, maze's name 
	 */
	public void modelLoadMaze(String[] args);

	/**
	 * solve the maze
	 * @param args array of strings with the parameter/s: maze's name, bfs/dfs tree algorithm
	 */
	public void modelSolve(String[] args);

	/**
	 * display the solution of the maze
	 * @param name maze's name 
	 */
	public void modelDisplaySolution(String[] args);

	/**
	 * exit program
	 */
	public void modelExit();
}
