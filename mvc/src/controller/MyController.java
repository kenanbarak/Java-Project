package controller;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * defines our specific controller and commands
 * @author Barak Kenan
 */
public class MyController extends CommonController
{
	public MyController() 
	{
		super();
	}
	/**
	 * the command's classes
	 */
	public class exitCommand implements Command
	{
		@Override
		public void doCommand(String[] args)
		{
				m.modelExit();
		}
	}
	
	public class menuCommand implements Command
	{
		@Override
		public void doCommand(String[] args)
		{
				v.viewMenu();
		}
	}
	
	public class dirCommand implements Command
	{
		@Override
		public void doCommand(String[] args)
		{
			m.modelDirPath(args);
		}
	}

	public class generateCommand implements Command
	{
		@Override
		public void doCommand(String[] args)
		{
			m.modelGenerate3dMaze(args);
		}
	}
	
	public class displayCommand implements Command
	{
		@Override
		public void doCommand(String[] args)
		{
			m.modelDisplayName(args);
		}
	}
	
	public class displayCrossCommand implements Command
	{
		@Override
		public void doCommand(String[] args)
		{
			m.modelDisplayCrossSectionBy(args);
		}
	}
	
	public class saveCommand implements Command
	{
		@Override
		public void doCommand(String[] args) 
		{
			m.modelSaveMaze(args);
		}	
	}
	
	public class loadCommand implements Command
	{
		@Override
		public void doCommand(String[] args) 
		{
			m.modelLoadMaze(args);
		}
	}
	
	public class solveCommand implements Command
	{
		@Override
		public void doCommand(String[] args) 
		{
			m.modelSolve(args);
		}
	}
	
	public class displaySolutionCommand implements Command
	{
		@Override
		public void doCommand(String[] args) 
		{
			m.modelDisplaySolution(args);
		}
	}

	/**
	 * initialize the commands
	 */
	public void initCommands()
	{
		commands.put("menu", new menuCommand());
		commands.put("dir", new dirCommand());
		commands.put("generate 3d maze", new generateCommand());
		commands.put("display", new displayCommand());
		commands.put("display cross section by", new displayCrossCommand());
		commands.put("save maze",new saveCommand());
		commands.put("load maze",new loadCommand());
		commands.put("solve", new solveCommand());
		commands.put("display solution", new displaySolutionCommand());
		commands.put("exit", new exitCommand()); 	
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contError(String error) 
	{
		v.viewError(error);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contDirPath(String dirContent) 
	{
		v.viewDirPath(dirContent);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contGenerate3dMaze(String message) 
	{
		v.viewGenerate3dMaze(message);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contDisplayName(byte[] byteArr) 
	{
		v.viewDisplayName(byteArr);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contsDisplayCrossSectionBy(int[][] crossSection) 
	{
		v.viewDisplayCrossSectionBy(crossSection);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contSaveMaze(String message) 
	{
		v.viewSaveMaze(message);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contLoadMaze(String message) 
	{
		v.viewLoadMaze(message);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contSolve(String message) 
	{	
		v.viewSolve(message);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contDisplaySolution(Solution<Position> solution) 
	{
		v.viewDisplaySolution(solution);
	}

}
