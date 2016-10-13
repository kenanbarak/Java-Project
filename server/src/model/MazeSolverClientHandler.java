package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.Maze3dSearchable;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS_Searcher;
import algorithms.search.DFS_Searcher;
import algorithms.search.Searcher;
import algorithms.search.Solution;

/**
 * defines a specific client handler - maze solver
 * @author Barak Kenan
 */
public class MazeSolverClientHandler implements ClientHandler
{
	private HashMap<Maze3d, Solution<Position>> solutions;

	/**
	 * constructor
	 */
	public MazeSolverClientHandler()
	{
		this.solutions=new HashMap<Maze3d,Solution<Position>>();
		try
		{
			loadSolutionFromFile();
		} 
		catch (ClassNotFoundException | IOException e)
		{
			System.out.println("load solutions from zip file faild");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) 
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToClient);
			String line;

			//wait for a "hi" from the client
			if((line=in.readLine()).equals("hi"))
			{
				//return "ok" to the client
				out.println("ok");
				out.flush();

				//reading client's request (array list with the maze he ask to solve and the wanted algorithm)
				ObjectInputStream packetFromClient = new ObjectInputStream(inFromClient);
				ArrayList<Object> request = (ArrayList<Object>)packetFromClient.readObject();
				String alg = (String)request.get(0);
				Maze3d maze = new Maze3d((byte[])request.get(1));


				ObjectOutputStream solutionToClient = new ObjectOutputStream(outToClient);

				Maze3dSearchable ms=new Maze3dSearchable(maze);
				Solution<Position> sol;

				if(solutions.containsKey(maze)) //the solution is already in the solutions hash map
				{
					sol=solutions.get(maze);
				}

				if(alg.equals("BFS")) //solve the maze by BFS algorithm
				{
					Searcher<Position> bfs=new BFS_Searcher<Position>();
					sol=bfs.search(ms);
				}

				else //solve the maze by DFS algorithm
				{
					Searcher<Position> dfs=new DFS_Searcher<Position>();
					sol=dfs.search(ms);
				}

				solutions.put(maze, sol); //add the maze with it's solution to the hash map


				//write the solution to the client
				solutionToClient.writeObject(sol);
				solutionToClient.flush();

				//close streams
				packetFromClient.close();
				solutionToClient.close();
			}				

			//close streams
			in.close();
			out.close();
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
	}


	/**
	 * {@inheritDoc}
	 */
	public void close()
	{
		try
		{
			saveSolutionToFile();
		}
		catch (IOException e)
		{
			System.out.println("save solution to zip file failed");
		}
	}

	/**
	 * save solutions to zip file
	 */
	public void saveSolutionToFile() throws IOException
	{
		if(solutions.isEmpty())
		{
			System.out.println("there are no solution for saving");
			return;
		}

		ObjectOutputStream out = null;
		try 
		{
			out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions.zip")));
			out.writeObject(solutions);
			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("file not found");
		} 
		catch (IOException e) 
		{
			System.out.println("save solution to zip file failed");
		}
	}

	/**
	 * load solution from zip file
	 */
	@SuppressWarnings("unchecked")
	public void loadSolutionFromFile() throws ClassNotFoundException, IOException
	{
		ObjectInputStream in = null;
		try
		{
			in = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions.zip")));
			solutions = (HashMap<Maze3d, Solution<Position>>) in.readObject();
			in.close();
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("zip file not found");
		} 
		catch (IOException e)
		{
			System.out.println("load solution from zip file failed");			
		}
	}
}
