package boot;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.Properties;

/**
 * a class with main method for creating the properties xml file
 * @author Barak Kenan
 */
public class RunProperties
{
	public static void main(String[] args)
	{
		Properties properties = new Properties(10, 4, 4, 4, "maze", "Simple", "BFS", "gui","127.0.0.1",5400);
		
		try
		{
			XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream("properties.xml"));
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
