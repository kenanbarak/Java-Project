package view;

import controller.Controller;

/**
 * abstract class which defines any view
 * @author Barak Kenan
 */

public abstract class CommonView implements View
{
	Controller c;
	
	/**
	 * constructor that initialize the controller
	 * @param c the controller
	 */
	public CommonView(Controller c) 
	{
		this.c=c;
	}

}
