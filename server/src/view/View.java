package view;

/**
 * defines a view in mvp
 * @author Barak Kenan
 */
public interface View 
{
	/**
	 * start
	 */
	public void start();
	
	
	/**
	 * display the given string
	 * @param msg the message
	 */
	public void viewDisplayMessage(String message);
}
