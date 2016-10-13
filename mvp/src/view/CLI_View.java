package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import presenter.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * defines a specific view - CLI
 * @author Barak Kenan
 */

public class CLI_View extends Observable implements View
{
	private String line; //line for reading input from user

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start()
	{
		
		viewMenu();
		line ="";
		Scanner scanner = new Scanner(System.in);
		do
		{
			line = scanner.nextLine();
			this.setChanged();
			this.notifyObservers(line);

		} while (!(line.equals("exit")));
		scanner.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewMenu()
	{
		System.out.println("-MVP-\nPossible commands are:");
		
		System.out.println("dir-<directory path>");
		System.out.println("generate 3d maze-<name> <x> <y> <z> <algorithm={Simple,GrowingTree}>");
		System.out.println("display-<maze name>");
		System.out.println("display cross section by-<{X,Y,Z}> <index> for <name>");
		System.out.println("save maze-<maze name> <file name>");
		System.out.println("load maze-<file name> <new maze name>");
		System.out.println("solve-<maze name> <algorithm={BFS,DFS}>");
		System.out.println("display solution-<maze name>");
		
		System.out.println("menu");
		System.out.println("exit");		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplayMessage(String string)
	{
		System.out.println(string);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplayMaze(byte[] byteArr)
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
		System.out.println(str);		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplayCrossSectionBy(int[][] crossSection)
	{
		System.out.println("cross section:");
		
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
	public void viewDisplaySolution(Solution<Position> solution)
	{
		ArrayList<Position> arr = new ArrayList<Position>();
		for (int i=0; i<solution.getStates().size(); i++)
		{
			Position p = solution.getStates().get(i).getState();
			arr.add(p);
		}

		System.out.println(arr);

	}

	@Override
	public void resetProperties(Properties pro) {}
}
