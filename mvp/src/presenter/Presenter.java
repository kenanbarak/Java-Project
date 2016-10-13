package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * defines the presenter in mvp
 * @author Barak Kenan
 */
public class Presenter implements Observer
{
	private Model m;
	private View v;
	private HashMap<String, Command> commands;
	
	/**
	 * constructor
	 */
	public Presenter(Model m, View v)
	{
		this.v = v;
		this.m = m;
		commands = new HashMap<String, Command>();
		initCommands();
	}
	

	/**
	 * the command's classes
	 */
	public class menuCommand implements Command
	{
		@Override
		public void doCommand(String args) 
		{
			v.viewMenu();
		}
	}
	
	class dirCommand implements Command
	{
		@Override
		public void doCommand(String args)
		{
			if (args!=null)
				m.modelDirPath(args);
			else
				v.viewDisplayMessage("invalid input");
		}
	}
	
	public class generateCommand implements Command
	{
		@Override
		public void doCommand(String args)
		{			
			String[] params = args.split(" ");
			
			if (params.length==5)
			{
				String name = params[0];
				int x = Integer.parseInt(params[1]);
				int y = Integer.parseInt(params[2]);
				int z = Integer.parseInt(params[3]);
				String alg = params[4];
				
				m.modelGenerate3dMaze(name,x,y,z,alg);

			}
			else
				v.viewDisplayMessage("invalid input");				
		}
	}
	
	public class displayCommand implements Command
	{
		@Override
		public void doCommand(String args)
		{
			if (args!=null)
				m.modelDisplayMaze(args);
			else
				v.viewDisplayMessage("invalid input");
		}
	}
	
	public class displayCrossCommand implements Command
	{
		@Override
		public void doCommand(String args)
		{			
			String[] params = args.split(" ");

			if (params.length==4)
			{
				String axis = params[0];
				int index = Integer.parseInt(params[1]);
				String tempFor = params[2];
				String name = params[3];
				
				if ((tempFor.equals("for")) && ((axis.equals("x")) || (axis.equals("y")) || (axis.equals("z")))) //input validation
					m.modelDisplayCrossSectionBy(axis,index,tempFor,name);
				else
					v.viewDisplayMessage("invalid input");	
			}
			else
				v.viewDisplayMessage("invalid input");				
		}
	}
	
	public class saveCommand implements Command
	{
		@Override
		public void doCommand(String args) 
		{
			String[] params = args.split(" ");
			
			if (params.length==2)
			{
				String name = params[0];
				String file = params[1];
				
				m.modelSaveMaze(name, file);
			}
			else
				v.viewDisplayMessage("invalid input");				
		}	
	}
	
	public class loadCommand implements Command
	{
		@Override
		public void doCommand(String args) 
		{
			String[] params = args.split(" ");
			
			if (params.length==2)
			{
				String file = params[0];
				String name = params[1];
				
				m.modelLoadMaze(file, name);
			}
			else
				v.viewDisplayMessage("invalid input");	
		}
	}
	
	public class solveCommand implements Command
	{
		@Override
		public void doCommand(String args) 
		{
			String[] params = args.split(" ");
			
			if (params.length==2)
			{
				String name = params[0];
				String alg = params[1];
				
				m.modelSolve(name,alg);
			}
			else
				v.viewDisplayMessage("invalid input");	
		}
	}
	
	public class displaySolutionCommand implements Command
	{
		@Override
		public void doCommand(String args) 
		{
			if (args!=null)
				m.modelDisplaySolution(args);
			else
				v.viewDisplayMessage("invalid input");
		}
	}
	
	class exitCommand implements Command
	{
		@Override
		public void doCommand(String args)
		{
			m.modelExit();
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
	 * update
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object args)
	{		
		if (o instanceof View) 
		{
			if(args.toString().startsWith("load properties "))
			{
				String fileName=args.toString().substring(16);
				m.loadPropertiesFromFile(fileName);
			}
			
			else //commands
			{
				String[] arr = ((String) args).split("-"); // arr[0] is the command
			
				if (commands.containsKey(arr[0]))
				{
					String params = null;
					if (arr.length > 1)
						params = arr[1]; //string of all the parameters separated by space
					
					Command command = commands.get(arr[0]);
					command.doCommand(params); //doCommand gets just the parameters by string
					
				}
				else
					v.viewDisplayMessage("Invalid command");
			}
		}
			
		else if (o instanceof Model) 
		{
			if (args.toString().startsWith("solution for "))
			{
				int endIndex = args.toString().indexOf(" is ready");
				String mazeName = args.toString().substring(13 , endIndex);
				m.modelDisplaySolution(mazeName);
			}
			
			else if (args.toString().endsWith(" is ready"))
			{
				int endIndex = args.toString().indexOf(" is ready");
				String mazeName = args.toString().substring(0, endIndex);
				m.modelDisplayMaze(mazeName);
			}
			
			else if (args.toString().endsWith(" has been loaded"))
			{
				int endIndex = args.toString().indexOf(" has been loaded");
				String mazeName = args.toString().substring(0, endIndex);
				m.modelDisplayMaze(mazeName);
			}
			
			else if (args instanceof String) 
			{
				if (!args.equals("exit"))
					v.viewDisplayMessage((String) args);
			}
			
			else if (args instanceof byte[])
				v.viewDisplayMaze((byte[]) args);
			
			else if (args instanceof int[][])
				v.viewDisplayCrossSectionBy((int[][]) args);
			
			else if (args instanceof Solution<?>)
				v.viewDisplaySolution((Solution<Position>) args);	
			
			else if (args instanceof Properties)
				v.resetProperties((Properties) args);				
		}
	}
}
