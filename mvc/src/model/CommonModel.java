package model;

import controller.Controller;

/**
 * abstract class which defines any model
 * @author Barak Kenan
 */

public abstract class CommonModel implements Model
{
	Controller c;
	
	/**
	 * constructor that initialize the controller
	 * @param c the controller
	 */
	public CommonModel(Controller c) 
	{
		this.c=c;
	}
}
