package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;

/**
 * defines our specific view and commands
 * @author Barak Kenan
 */

public class MyView extends CommonView
{
	CLI cli;
	HashMap<String, Command> commands;
	
	
	/**
	 * constructor that initialize the controller
	 * @param c the controller
	 */
	public MyView(Controller c) 
	{
		super(c);
		cli=new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out));
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() 
	{
		this.cli.start();		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) 
	{
		this.commands=commands;
		cli.setCommands(commands);	
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewMenu() 
	{
		System.out.println("Possible commands are:");
		
		System.out.println("dir-<path> ");
		System.out.println("generate 3d maze-<name>-<x>-<y>-<z>-<algorithm-Simple/Growing Tree>");
		System.out.println("display-<name>");
		System.out.println("display cross section by-<{X,Y,Z}>-<index>-for-<name>n");
		System.out.println("save maze-<maze name>-<file name>");
		System.out.println("load maze-<file name>-<new maze name>");
		System.out.println("solve-<maze name>-<algorithm-BFS/DFS>");
		System.out.println("display solution-<maze name>");
		
		System.out.println("menu");
		System.out.println("exit");	
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDirPath(String dirContent) 
	{
		cli.getOut().print(dirContent);
		cli.getOut().flush();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewGenerate3dMaze(String str) 
	{
		cli.getOut().println(str);
		cli.getOut().flush();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplayName(byte[] byteArr) 
	{
		Maze3d maze3d = new Maze3d(byteArr);
		int x_size=maze3d.getX_size();
		int y_size=maze3d.getY_size();
		int z_size=maze3d.getZ_size();
		int[][][] m=maze3d.getMaze3d();
		
		String s="";
		for (int i=0; i<x_size;i++)
		{
			s+="floor "+i+"\n";
		
			for (int j=0; j<y_size;j++)
			{
				s+="";
				for (int k=0; k<z_size; k++)
				{
					if (k==z_size-1)
						s+=m[i][j][k];
					else
						s+=m[i][j][k]+" ";
				}
				s+="\n";
			}
			s+="\n";
		}
		
		String str="the maze:\n"+s+"\nstart: "+maze3d.getStartPosition()+"\ngoal: "+maze3d.getGoalPosition();
		cli.getOut().println(str);
		cli.getOut().flush();		
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplayCrossSectionBy(int[][] crossSection) 
	{
		cli.getOut().println("cross section:");
		cli.getOut().flush();
		
		for (int i=0; i<crossSection.length; i++)
		{
			for (int j=0; j<crossSection[i].length; j++)
				System.out.println(crossSection[i][j]);
			System.out.println();
		}
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewSaveMaze(String str) 
	{
		cli.getOut().println(str);
		cli.getOut().flush();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewLoadMaze(String str) 
	{
		cli.getOut().println(str);
		cli.getOut().flush();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewSolve(String str) 
	{
		System.out.println(str);	
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplaySolution(Solution<Position> solution) 
	{
		String str="solution: "+solution.toString();
		cli.getOut().println(str);
		cli.getOut().flush();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewError(String error) 
	{
		System.out.println(error);
	}

}
