package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import algorithms.demo.Maze3dSearchable;
import algorithms.mazeGenerators.CommonPop;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.LastSelectionPop;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleGenerator;
import algorithms.search.BFS_Searcher;
import algorithms.search.DFS_Searcher;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import controller.Controller;

/**
 * defines our specific model and commands
 * @author Barak Kenan
 */

public class MyModel extends CommonModel
{
	private HashMap<String, Maze3d> generatedMazes; //key-maze's name, value-maze3d object
	private HashMap<String, String> savedMazes; //key-maze's name ,value-the file's name where the maze is saved
	private HashMap<Maze3d, Solution<Position>> solutions; //key-maze3d object, value-maze's solution
	private ExecutorService threadPool;

	/**
	 * constructor that initialize the controller and the hash maps
	 * @param c the controller
	 */
	public MyModel(Controller c) 
	{
		super(c);
		generatedMazes=new HashMap<String, Maze3d>();
		savedMazes=new HashMap<String,String>();
		solutions=new HashMap<Maze3d, Solution<Position>>();
		threadPool = Executors.newFixedThreadPool(10);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelDirPath(String[]args) 
	{	
		if (args.length!=1) //input validation
		{
			c.contError("invalid input");
			return;
		}
		
		String str="", path=args[0];
		
		File f=new File(path); 
		
		if(f.isDirectory())
		{
			File[] listOfFiles= f.listFiles();
			for(File file: listOfFiles)
			{
				if(file.isFile())
					str+="File "+ file.getName()+"\n";
				
				else if(file.isDirectory())
					str+="Directory "+ file.getName()+"\n";
			}	
		}
		else
			str+=path+" is not an existing directory\n";
		
		c.contDirPath(str);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelGenerate3dMaze(String[] args) 
	{
		if (args.length!=5) //input validation
		{
			c.contError("invalid input");
			return;
		}
		
		threadPool.execute(new Runnable() 
		{
			public void run() 
			{
				Maze3dGenerator maze3d;
				
				if (args[4].equals("Simple")) //generate 3d maze by simple algorithm
					maze3d = new SimpleGenerator();
				
				else if (args[4].equals("Growing Tree")) //generate 3d maze by growing tree algorithm
				{
					CommonPop abp = new LastSelectionPop();
					maze3d=new GrowingTreeGenerator(abp);
				}
				
				else //invalid algorithm name
				{
					c.contError("invalid algorithm name");
					return;
				}


				if (generatedMazes.containsKey(args[0])) 
				{
					c.contError("maze's name already exists"); //maze's name is unavailable
					return;
				}

				//generate maze according to the sizes and the algorithm
				generatedMazes.put(args[0], maze3d.generate(Integer.parseInt(args[1]),Integer.parseInt(args[2]), Integer.parseInt(args[3])));
			
				c.contGenerate3dMaze("maze " + args[0] + " is ready");	
			}
		});
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelDisplayName(String[] args) 
	{	
		if (args.length!=1) //input validation
		{
			c.contError("invalid input");
			return;
		}
		
		String name = args[0];
		if (!generatedMazes.containsKey(name)) //the maze does not exist
		{
			c.contError("there is no such maze");
			return;
		}
		
		byte[] b=generatedMazes.get(name).toByteArray(); //find the wanted maze 
		c.contDisplayName(b);	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelDisplayCrossSectionBy(String[] args) 
	{
		if ((args.length!= 4) || (!args[2].equals("for")) || ((!args[0].equals("X")) && (!args[0].equals("Y")) && (!args[0].equals("Z")))) //input validation
		{
			c.contError("invalid input");
			return;
		}
		
		if (!generatedMazes.containsKey(args[3])) //the maze does not exist
		{
			c.contError("there is no such maze");
			return;
		}
		
		
		if (args[0].equals("X")) //cross section by x
		{
			Maze3d maze3d=generatedMazes.get(args[3]);
			try
			{
				int[][] crossSectionByX=maze3d.getCrossSectionByX(Integer.parseInt(args[1]));
				c.contsDisplayCrossSectionBy(crossSectionByX);
			}

			catch (IndexOutOfBoundsException e)
			{
				c.contError("index is out of maze's bounds");
				return;
			}	
		}
		
		else if (args[0].equals("Y")) //cross section by y
		{
			Maze3d maze3d=generatedMazes.get(args[3]);
			try
			{
				int[][] crossSectionByY=maze3d.getCrossSectionByY(Integer.parseInt(args[1]));
				c.contsDisplayCrossSectionBy(crossSectionByY);
			}

			catch (IndexOutOfBoundsException e)
			{
				c.contError("index is out of maze's bounds");
				return;
			}	
		}
				
		else if (args[0].equals("Z")) //cross section by z
		{
			Maze3d maze3d=generatedMazes.get(args[3]);
			try
			{
				int[][] crossSectionByZ=maze3d.getCrossSectionByZ(Integer.parseInt(args[1]));
				c.contsDisplayCrossSectionBy(crossSectionByZ);
			}

			catch (IndexOutOfBoundsException e)
			{
				c.contError("index is out of maze's bounds");
				return;
			}	
		}	
	}

	@Override
	public void modelSaveMaze(String[] args) 
	{
		if (args.length!=2) //input validation
		{
			c.contError("invalid input");
			return;
		}
		
		if(!generatedMazes.containsKey(args[0])) //the maze does not exist
		{
			c.contError("there is no such maze");
			return;
		}

		try
		{
			Maze3d maze3d = generatedMazes.get(args[0]); //search the maze in the generated mazes hash map

			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(args[1]+".txt")); //open txt file for saving
			out.write(maze3d.toByteArray()); //write the maze to the file
			out.flush();
			out.close();
			
			savedMazes.put(args[0], args[1]); //add the maze with it's file to the hash map

			c.contSaveMaze("maze "+args[0]+" has been saved in "+args[1]+".txt file"); //save succeeded
		}
		
		catch (FileNotFoundException e) //the file does not exist
		{
			c.contError("there is no such file"); 
		} 
		
		catch (IOException e) //save failed
		{
			c.contError("save failed"); 
		}
	}

	

	@Override
	public void modelLoadMaze(String[] args) 
	{
		if (args.length!=2) //input validation
		{
			c.contError("invalid input");
			return;
		}
		
		try 
		{
			InputStream in = new MyDecompressorInputStream(new FileInputStream(args[0]+".txt"));
			byte[] bArr = new byte[50000]; //read some bytes from the input stream and stores them into the buffer array
			int numByte = in.read(bArr);
			in.close();
			byte[] newbArr = new byte[numByte];
			for (int i = 0; i < newbArr.length; i++)
				newbArr[i] = bArr[i];
			
			Maze3d maze3d = new Maze3d(bArr); //build the maze from the file's data			
			generatedMazes.put(args[1], maze3d); //add the maze with it's name to the hash map
			
			c.contLoadMaze(args[1] + " has been loaded from file " + args[0]+".txt");
		} 
		
		catch (FileNotFoundException e) //the file does not exist
		{
			c.contError("there is no such file"); 
		} 
		
		catch (IOException e) //load failed
		{
			c.contError("load failed"); 
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelSolve(String[] args) 
	{
		if (args.length!=2) //input validation
		{
			c.contError("invalid input");
			return;
		}
		
		
		threadPool.execute(new Runnable() 
		{
			public void run() 
			{
				if (!generatedMazes.containsKey(args[0])) //the maze does not exist
				{
					c.contError("there is no such maze");
					return;
				}
				
				if(solutions.containsKey(generatedMazes.get(args[0]))) //the solution is already in the solutions hash map
				{
					c.contSolve("solution for "+ args[0]+ " is ready");
					return;
				}
				
				
				if (args[1].equals("BFS")) //solve the maze by BFS algorithm
				{
					Maze3dSearchable maze3dS = new Maze3dSearchable(generatedMazes.get(args[0]));
					
					Searcher<Position> bfs = new BFS_Searcher<Position>();
					
					Solution<Position> solution = bfs.search(maze3dS);
					
					solutions.put(generatedMazes.get(args[0]), solution); //save the solution in the solutions hash map
					
					c.contSolve("solution for "+ args[0]+ " is ready");
					
					return;
				}
				
				else if (args[1].equals("DFS")) //solve the maze by DFS algorithm
				{
					Maze3dSearchable maze3dS=new Maze3dSearchable(generatedMazes.get(args[0]));
					
					Searcher<Position> dfs = new DFS_Searcher<Position>();
					
					Solution<Position> solution= dfs.search(maze3dS);
					
					solutions.put(generatedMazes.get(args[0]), solution); //save the solution in the solutions hash map
					
					c.contSolve("Solution for "+ args[0]+ " is ready");
					return;
				}
				
				else //invalid algorithm name
				{
					c.contError("invalid algorithm name");
					return;
				}
			}
		});
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modelDisplaySolution(String[] args) 
	{
		if (args.length!=1) //input validation
		{
			c.contError("invalid input");
			return;
		}
		
		String name = args[0];
		
		if (!generatedMazes.containsKey(name)) //the maze does not exist
		{
			c.contError("there is no such maze");
			return;
		}
		
		Maze3d maze3d=generatedMazes.get(name);
		if (solutions.containsKey(maze3d)) //if the maze has solution
			c.contDisplaySolution(solutions.get(maze3d));
		
		else
		{
			c.contError("there is no solution for this maze, try first the solve command");
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
			while(!(threadPool.awaitTermination(10, TimeUnit.SECONDS)));
		} 
		
		catch (InterruptedException e) 
		{
			System.out.println("exit faild");
		}	
	}

}
