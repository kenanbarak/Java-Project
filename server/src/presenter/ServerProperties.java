package presenter;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * defines a communication properties class
 * @author Barak Kenan
 */
@SuppressWarnings("serial")
public class ServerProperties implements Serializable 
{
	protected int numOfClients;
	protected int port;
	private String viewType;
	
	/**
	 * default constructor
	 */
	public ServerProperties()
	{
	}
	
	/**
	 * constructor
	 */
	public ServerProperties(int port, int numOfClients, String viewType)
	{
		this.port=port;
		this.numOfClients=numOfClients;
		this.viewType=viewType;
	}
	
	/**
	 * read properties from file (load xml)
	 */
	public ServerProperties readPropertiesFromFile(String fileName)
	{	
		try
		{
			XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));
			ServerProperties pro = (ServerProperties)xmlDecoder.readObject();
			xmlDecoder.close();
			return pro;
		}
		catch (FileNotFoundException e)
		{
			System.out.println("reading properties from file failed");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * getters & setters
	 */
	public int getNumOfClients()
	{
		return numOfClients;
	}

	public void setNumOfClients(int numOfClients)
	{
		this.numOfClients = numOfClients;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getViewType()
	{
		return viewType;
	}

	public void setViewType(String viewType)
	{
		this.viewType = viewType;
	}
}
