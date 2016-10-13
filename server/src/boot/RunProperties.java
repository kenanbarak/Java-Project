package boot;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.ServerProperties;

/**
 * a class with main method for creating the properties xml file
 * @author Barak Kenan
 */
public class RunProperties
{
	public static void main(String[] args)
	{
		ServerProperties properties = new ServerProperties(5400, 10, "gui");
		
		try
		{
			XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream("serverProperties.xml"));
			xmlEncoder.writeObject(properties);
			xmlEncoder.close();
			
		} 
		catch (FileNotFoundException e)
		{
			System.out.println("saving properties in file failed");
			e.printStackTrace();
		} 
	}

}
