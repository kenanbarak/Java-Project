package model;

/**
 * defines a model in mvp
 * @author Barak Kenan
 */

public interface Model
{
	/**
	 * load properties from the given file
	 * @param path the asked path
	 */
	public void loadPropertiesFromFile(String fileName);
	
	/**
	 * display the files and folder in a given path
	 * @param path the asked path
	 */
	public void modelDirPath(String path);

	/**
	 * generate a maze
	 * @param name maze's name
	 * @param x x_size
	 * @param y y_size
	 * @param z z_size
	 * @param alg algorithm to generate a maze
	 */
	public void modelGenerate3dMaze(String name, int x, int y, int z, String alg);

	/**
	 * display the maze
	 * @param name maze's name
	 */
	public void modelDisplayMaze(String name);

	/**
	 * display the maze's cross section
	 * @param axis x/y/z
	 * @param index index
	 * @param tempFor constant string "for"
	 * @param name maze's name
	 */
	public void modelDisplayCrossSectionBy(String axis, int index, String tempFor,String name);

	/**
	 * save a maze in a file
	 * @param name maze's name
	 * @param file the destination file for saving
	 */
	public void modelSaveMaze(String name, String file);

	/**
	 * load a maze from a file
	 * @param file the source file for loading
	 * @param name the new maze's name
	 */
	public void modelLoadMaze(String file, String name);

	/**
	 * solve the maze
	 * @param name maze's name
	 * @param alg algorithms to solve the maze
	 */
	public void modelSolve(String name, String alg);

	/**
	 * display the solution of the maze
	 * @param name maze's name 
	 */
	public void modelDisplaySolution(String name);

	/**
	 * exit program
	 */
	public void modelExit();
}

