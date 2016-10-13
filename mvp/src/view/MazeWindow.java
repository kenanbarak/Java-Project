package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * abstract class which defines any maze window
 * @author Barak Kenan
 */
public abstract class MazeWindow extends Canvas
{
	Position playerPosition;
	int[][] mazeData;
	Maze3d maze3d;


	/**
	 * constructor
	 */
	public MazeWindow(Composite parent, int style)
	{
		super(parent, style);
		playerPosition = new Position(0,0,0);
	}

	

	/**
	 * getter
	 * @return maze3d
	 */
	public Maze3d getMaze3d()
	{
		return maze3d;
	}

	/**
	 * setter
	 * @param maze3d the maze3d
	 */
	public void setMaze3d(Maze3d maze3d)
	{
		this.maze3d = maze3d;
	}

	/**
	 * getter
	 * @return maze2d (cross section)
	 */
	public int[][] getMazeData()
	{
		return mazeData;
	}

	/**
	 * setter
	 * @param mazedata the maze2d (cross section)
	 */
	public void setMazeData(int[][] mazeData)
	{
		this.mazeData = mazeData;
	}
	
	
	/**
	 * getter
	 * @return player's position
	 */
	public Position getPlayer()
	{
		return this.playerPosition;
	}
	
	/**
	 * setter
	 * @param p the player's position
	 */
	public void setPlayerPosition(Position p)
	{
		playerPosition.setPosition(p.getX(),p.getY(),p.getZ());
	}


	/**
	 * move up
	 */
	public abstract void moveUp();
	
	/**
	 * move down
	 */
	public abstract void moveDown();
	
	/**
	 * move left
	 */
	public abstract void moveLeft();
	
	/**
	 * move right
	 */
	public abstract void moveRight();
	
	/**
	 * move with page up (floor)
	 */
	public abstract void pageUp();
	
	/**
	 * move with page down (floor)
	 */
	public abstract void pageDown();

	/**
	 * display solution
	 */
	public abstract void printSolution(Solution<Position> sol); 
}
