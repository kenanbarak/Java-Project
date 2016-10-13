package network;

import algorithms.mazeGenerators.Maze3d;

public class Problem
{
	Maze3d maze;
	String alg;

	public Problem(Maze3d maze,String alg)
	{
		this.maze = maze;
		this.alg = alg;
	}

	public Maze3d getMaze()
	{
		return maze;
	}

	public void setMaze(Maze3d maze)
	{
		this.maze = maze;
	}

	public String getAlg()
	{
		return alg;
	}

	public void setArgs(String alg)
	{
		this.alg = alg;
	}



}
