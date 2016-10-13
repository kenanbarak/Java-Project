package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.File;
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
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import presenter.Properties;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomSelectionPop;
import algorithms.mazeGenerators.SimpleGenerator;
import algorithms.search.Solution;

/**
 * defines a specific model
 * @author Barak Kenan
 */

public class MyModel extends CommonModel
{
	private HashMap<String, Maze3d> generatedMazes; //key-maze's name, value-maze3d object
	private HashMap<String, String> savedMazes; //key-maze's name ,value-the file's name where the maze is saved
	private HashMap<Maze3d, Solution<Position>> solutions; //key-maze3d object, value-maze's solution
	private ExecutorService threadPool;
	private Properties prop;

	/**
	 * constructor that initialize the hash maps and load solutions
	 */
	public MyModel()
	{		
		this.prop = new Properties();
		this.prop = prop.readPropertiesFromFile("properties.xml");

		generatedMazes=new HashMap<String, Maze3d>();
		savedMazes=new HashMap<String,String>();
		solutions=new HashMap<Maze3d, Solution<Position>>();

		try
		{
			loadSolutionFromFile();
		} 
		catch (ClassNotFoundException | IOException e)
		{
			System.out.println("load solutions from zip file faild");
		}
		threadPool = Executors.newFixedThreadPool(prop.getNumOfThreads());
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
			System.out.println("file not found");
		} 
		catch (IOException e)
		{
			System.out.println("load solution from zip file failed");			
		}
	}

	@Override
	public void loadPropertiesFromFile(String fileName)
	{	
		try
		{
			XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));
			prop = (Properties)xmlDecoder.readObject();
			xmlDecoder.close();
			setChanged();
			notifyObservers(prop);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("reading properties from file failed");
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelDirPath(String path) 
	{
		String str="";

		File f=new File(path);

		if(f.isDirectory())
		{
			File[] listOfFiles = f.listFiles();

			if (listOfFiles.length == 0) //folder is empty
			{
				setChanged();
				notifyObservers("folder is empty");
			}

			for(File file: listOfFiles) //for each file
			{
				if(file.isFile())
					str+="File "+ file.getName()+"\n";

				else if(file.isDirectory())
					str+="Folder "+ file.getName()+"\n";
			}	
		}
		else //there is no such folder
			str+=path+" is not an existing folder";

		setChanged();
		notifyObservers(str);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelGenerate3dMaze(String name, int x, int y, int z, String alg)
	{		

		if (generatedMazes.containsKey(name)) //maze's name is unavailable
		{
			setChanged();
			notifyObservers("maze's name already exists");
			return;
		}

		if ((!alg.equals("Simple")) && (!alg.equals("GrowingTree"))) //invalid algorithm name
		{
			setChanged();
			notifyObservers("invalid algorithm name");
			return;
		}


		Future<Maze3d> futureMaze = threadPool.submit(new Callable<Maze3d>()
				{
			@Override
			public Maze3d call() throws Exception
			{
				Maze3dGenerator maze3dGenerator;

				if (alg.equals("Simple")) //generate 3d maze by simple algorithm
					maze3dGenerator = new SimpleGenerator();

				else //generate 3d maze by growing tree algorithm
					maze3dGenerator = new GrowingTreeGenerator(new RandomSelectionPop());

				Maze3d maze3d = maze3dGenerator.generate(x,y,z);

				return maze3d;
			}
				});

		//futureMaze.get() wait for maze3d until it will be ready
		try
		{
			generatedMazes.put(name, futureMaze.get()); //add the maze with it's name to the hash map
		}

		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}

		setChanged();
		notifyObservers(name + " is ready");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelDisplayMaze(String name)
	{
		if (!generatedMazes.containsKey(name)) //the maze does not exist
		{
			setChanged();
			notifyObservers("there is no such maze");
			return;
		}

		byte[] b=generatedMazes.get(name).toByteArray(); //find the wanted maze 
		setChanged();
		notifyObservers(b);		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelDisplayCrossSectionBy(String axis, int index, String tempFor,String name)
	{
		if (!generatedMazes.containsKey(name)) //the maze does not exist
		{
			setChanged();
			notifyObservers("there is no such maze");
			return;
		}


		if (axis.equals("x")) //cross section by x
		{
			Maze3d maze3d=generatedMazes.get(name);
			try
			{
				int[][] crossSectionByX=maze3d.getCrossSectionByX(index);
				setChanged();
				notifyObservers(crossSectionByX);
			}

			catch (IndexOutOfBoundsException e) //out of bounds
			{
				setChanged();
				notifyObservers("index is out of maze's bounds");
				return;
			}	
		}

		else if (axis.equals("y")) //cross section by y
		{
			Maze3d maze3d=generatedMazes.get(name);
			try
			{
				int[][] crossSectionByY=maze3d.getCrossSectionByY(index);
				setChanged();
				notifyObservers(crossSectionByY);
			}


			catch (IndexOutOfBoundsException e) //out of bounds
			{
				setChanged();
				notifyObservers("index is out of maze's bounds");
				return;
			}	
		}

		else if (axis.equals("z")) //cross section by z
		{
			Maze3d maze3d=generatedMazes.get(name);
			try
			{
				int[][] crossSectionByZ=maze3d.getCrossSectionByZ(index);
				setChanged();
				notifyObservers(crossSectionByZ);
			}

			catch (IndexOutOfBoundsException e) //out of bounds
			{
				setChanged();
				notifyObservers("index is out of maze's bounds");
				return;
			}	
		}	

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelSaveMaze(String name, String file)
	{

		if(!generatedMazes.containsKey(name)) //the maze does not exist
		{
			setChanged();
			notifyObservers("there is no such maze");
			return;
		}

		try
		{
			Maze3d maze3d = generatedMazes.get(name); //search the maze in the generated mazes hash map

			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(file)); //open txt file for saving
			out.write(maze3d.toByteArray()); //write the maze to the file
			out.flush();
			out.close();

			savedMazes.put(name, file); //add the maze with it's file to the hash map

			setChanged();
			notifyObservers("maze has been saved in "+file); //save succeeded
		}

		catch (FileNotFoundException e) //the file does not exist
		{
			setChanged();
			notifyObservers("there is no such file");
			return;
		} 

		catch (IOException e) //save failed
		{
			setChanged();
			notifyObservers("save failed");
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelLoadMaze(String file, String name)
	{
		try 
		{
			InputStream in = new MyDecompressorInputStream(new FileInputStream(file+".txt"));
			byte[] bArr = new byte[50000]; //read some bytes from the input stream and stores them into the buffer array
			int numByte = in.read(bArr);
			in.close();
			byte[] newbArr = new byte[numByte];
			for (int i = 0; i < newbArr.length; i++)
				newbArr[i] = bArr[i];

			Maze3d maze3d = new Maze3d(bArr); //build the maze from the file's data			
			generatedMazes.put(name, maze3d); //add the maze with it's name to the hash map

			setChanged();
			notifyObservers(name + " has been loaded");
		} 

		catch (FileNotFoundException e) //the file does not exist
		{
			setChanged();
			notifyObservers("there is no such file");
			return;
		} 

		catch (IOException e) //load failed
		{
			setChanged();
			notifyObservers("load failed");
			return;		
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelSolve(String name, String alg)
	{
		if (!generatedMazes.containsKey(name)) //the maze does not exist
		{
			setChanged();
			notifyObservers("there is no such maze");
			return;
		}

		if ((!alg.equals("BFS")) && (!alg.equals("DFS"))) //invalid algorithm name
		{
			setChanged();
			notifyObservers("invalid algorithm name");
			return;
		}

		Future<Solution<Position>> futureSolution = threadPool.submit(new Callable<Solution<Position>>(){
			@Override
			public Solution<Position> call() throws Exception
			{
				//asking the server for solution
				Solution<Position> solution = getSolutionFromServer(alg,generatedMazes.get(name));
				return solution;
			}
		});

		//futureMaze.get() wait for solution until it will be ready
		try
		{
			Solution<Position> sol = futureSolution.get();
			solutions.put(generatedMazes.get(name), sol); //add the maze with it's solution to the hash map
			setChanged();
			notifyObservers("solution for "+ name+ " is ready");
		}

		catch (InterruptedException | ExecutionException e)
		{
			setChanged();
			//notifyObservers("the server failed to solve the maze");
			notifyObservers("server might be close");
			return;
		}
	}


	/**
	 * ask the server for solve this maze
	 * @param alg algorithms to solve the maze
	 * @param maze the maze for solving
	 * @return the solution
	 */
	@SuppressWarnings("unchecked")
	private Solution<Position> getSolutionFromServer(String alg, Maze3d maze)
	{
		try
		{
			//connecting to the server on this ip&port
			Socket theServer = new Socket(prop.getIp(),prop.getPort());

			//server input&output streams (taken from the connection socket)
			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));

			//writing "hi" to the server
			outToServer.println("hi\n");
			outToServer.flush();

			//reading "ok" from the server
			inFromServer.readLine(); //="ok"

			//array list with the maze we, as a client, want to solve, and the algorithm for doing that
			ArrayList<Object> packetToServer = new ArrayList<Object>();
			packetToServer.add(alg);
			packetToServer.add(maze.toByteArray());

			//sending the array list to the server
			ObjectOutputStream mazeToServer = new ObjectOutputStream(theServer.getOutputStream());
			mazeToServer.writeObject(packetToServer);
			mazeToServer.flush();

			//reading the solution from the server
			ObjectInputStream solutionFromServer = new ObjectInputStream(theServer.getInputStream());
			Solution<Position> sol = (Solution<Position>)solutionFromServer.readObject();

			//closing streams
			mazeToServer.close();
			solutionFromServer.close();
			outToServer.close();
			inFromServer.close();
			theServer.close();

			return sol;
		} 
		catch (IOException | ClassNotFoundException e)
		{
			setChanged();
			notifyObservers("server might be close or failed to solve the maze");
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelDisplaySolution(String name)
	{
		if (!generatedMazes.containsKey(name)) //the maze does not exist
		{
			setChanged();
			notifyObservers("there is no such maze");
			return;
		}

		Maze3d maze3d=generatedMazes.get(name);
		if (solutions.containsKey(maze3d)) //the maze has solution
		{
			setChanged();
			notifyObservers(solutions.get(maze3d));
		}

		else //the maze has no solution
		{
			setChanged();
			notifyObservers("there is no solution for this maze, try first the solve command");
			return;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelExit()
	{
		threadPool.shutdown();
		try
		{
			saveSolutionToFile();
		} 
		catch (IOException e1)
		{
			System.out.println("save solution failed");	
		}
		try 
		{
			while(!(threadPool.awaitTermination(10, TimeUnit.SECONDS)));
		} 

		catch (InterruptedException e) 
		{
			System.out.println("exit failed");
		}		
	}

}
