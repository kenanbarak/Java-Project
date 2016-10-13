package model;

/**
 * defines a model in mvp
 * @author Barak Kenan
 */
public interface Model 
{
	/**
	 * open the server
	 */
	public void openTheServer();
	
	/**
	 * close the server
	 */
	public void closeServer();
	
//	/**
//	 * get open code
//	 * @return the string "the server is open"
//	 */
//	public String getOpenCode();
//	
//	/**
//	 * get close code 
//	 * @return the string "the server is closed"
//	 */
//	public String getCloseCode();
//	
//	/**
//	 * return the messages that come from the server
//	 * @return a string with the message
//	 */
//	public String getMessageCode();
}
