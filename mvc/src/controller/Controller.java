package controller;

import model.Model;
import view.View;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * defines a controller in mvc
 * @author Barak Kenan
 */

public interface Controller 
{
	/**
	 * setter
	 * @param m the model we work with
	 */
	public void setM(Model m);
	
	/**
	 * setter
	 * @param v the view we work with
	 */
	public void setV(View v);
	

	public void contError(String error);	
	
	public void contDirPath(String dirContent);

	public void contGenerate3dMaze(String str); 

	public void contDisplayName(byte[] byteArr);

	public void contsDisplayCrossSectionBy(int[][] crossSection);

	public void contSaveMaze(String str); 
	
	public void contLoadMaze(String str);

	public void contSolve(String str);

	public void contDisplaySolution(Solution<Position> solution);
}
