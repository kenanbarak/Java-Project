package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

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
	public Presenter(Model m,View v) 
	{		
		this.m=m;
		this.v=v;
		commands=new HashMap<String, Command>();
		initCommands();
	}
	
	/**
	 * the command's classes
	 */
	public class openServerViewCommand implements Command
	{
		@Override
		public void doCommand() 
		{
			m.openTheServer();
		}
	}
	
	public class closeServerViewCommand implements Command
	{
		@Override
		public void doCommand() 
		{
			m.closeServer();
		}
	}
	

	/**
	 * initialize the commands
	 */
	public void initCommands()
	{
		commands.put("open the server", new openServerViewCommand());
		commands.put("close the server",new closeServerViewCommand());
	}

	
	/**
	 * update
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		if (o instanceof View)
		{			
			if (commands.containsKey((String) arg))
			{			
				Command command = commands.get((String) arg);
				command.doCommand();				
			}
			else
				v.viewDisplayMessage("Invalid command");
		}
		
		else if(o instanceof Model)
		{
			if (arg instanceof String) 
			{
				v.viewDisplayMessage((String) arg);
			}
		}
	}	
}
