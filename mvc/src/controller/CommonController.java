package controller;

import java.util.HashMap;
import model.Model;
import view.View;

/**
 * abstract class which defines any controller
 * @author Barak Kenan
 */

public abstract class CommonController implements Controller
{
	
	Model m;
	View v;
	HashMap<String, Command> commands;
	
	/**
	 * constructor that initialize the controller
	 */
	public CommonController() 
	{
		this.commands=new HashMap<String, Command>();
		initCommands();
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void setM(Model m) 
	{
		this.m=m;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setV(View v) 
	{
		this.v=v;
		v.setCommands(commands);
		v.viewMenu();
	}
	
	
	public abstract void initCommands();
}
