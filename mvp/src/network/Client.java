package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class Client
{
	private String serverAddress;
	private int port;

	public Client(int port,String serverAddress)
	{
		this.port = port;
		this.serverAddress = serverAddress;
	}


	/**
	 * Solve server.
	 *
	 * @param maze the maze
	 * @param args the args
	 * @return the solution
	 */
	@SuppressWarnings("unchecked")
	public Solution<Position> solveServer(Maze3d maze,String alg)
	{
		Problem p = new Problem(maze, alg);
		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;

		try 
		{
			socket = new Socket(serverAddress,port); //the server
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(p);
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
			return (Solution<Position>)in.readObject();
		} 
		
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				out.close();
				in.close();
				socket.close();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
