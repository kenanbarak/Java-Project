package presenter;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * defines a properties class
 * @author Barak Kenan
 */

@SuppressWarnings("serial")
public class Properties implements Serializable
{
	private int numOfThreads;
	private int x,y,z;
	private String mazeName;
	private String generatorAlg;	
	private String solverAlg;
	private String viewType;
	private String ip;
	private int port;

	/**
	 * default constructor
	 */
	public Properties()
	{
	}
	
	/**
	 * constructor
	 */
	public Properties(int numOfThreads, int x, int y, int z, String mazeName, String generatorAlg, String solverAlg, String viewType, String ip, int port)
	{
		this.numOfThreads=numOfThreads;
		this.x=x;
		this.y=y;
		this.z=z;
		this.mazeName=mazeName;
		this.generatorAlg=generatorAlg;
		this.solverAlg=solverAlg;
		this.viewType=viewType;
		this.ip=ip;
		this.port=port;
	}

	
	/**
	 * read properties from file (load xml)
	 */
	public Properties readPropertiesFromFile(String fileName)
	{	
		try
		{
			XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(fileName));
			Properties pro = (Properties)xmlDecoder.readObject();
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
	public int getNumOfThreads()
	{
		return numOfThreads;
	}

	public void setNumOfThreads(int numOfThreads)
	{
		this.numOfThreads = numOfThreads;
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getZ()
	{
		return z;
	}

	public void setZ(int z)
	{
		this.z = z;
	}

	public String getMazeName()
	{
		return mazeName;
	}

	public void setMazeName(String mazeName)
	{
		this.mazeName = mazeName;
	}

	public String getGeneratorAlg()
	{
		return generatorAlg;
	}

	public void setGeneratorAlg(String generatorAlg)
	{
		this.generatorAlg = generatorAlg;
	}

	public String getSolverAlg()
	{
		return solverAlg;
	}

	public void setSolverAlg(String solverAlg)
	{
		this.solverAlg = solverAlg;
	}

	public String getViewType()
	{
		return viewType;
	}

	public void setViewType(String viewType)
	{
		this.viewType = viewType;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	
}
