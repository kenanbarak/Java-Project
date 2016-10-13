package boot;


import java.io.FileNotFoundException;
import java.io.IOException;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.CLI_View;
import view.GUI_View;
import view.View;

/**
 * @author Barak Kenan
 */
public class Run
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		MyModel m = new MyModel();
		View v;
		Presenter p;
		
		
		Properties prop = new Properties();
		prop = prop.readPropertiesFromFile("properties.xml");
	
		
		if (prop.getViewType().equals("gui"))
		{
			v = new GUI_View("Welcome To Barak's Maze!", 800, 550);
			p = new Presenter(m, v);
			
			m.addObserver(p);
			((GUI_View)v).addObserver(p);
			
			((GUI_View)v).run();
		}
		
		else if (prop.getViewType().equals("cli"))
		{
			v = new CLI_View();
			p = new Presenter(m, v);
			
			m.addObserver(p);
			((CLI_View)v).addObserver(p);
			
			((CLI_View)v).start();
		}
		
		else
			System.out.println("please choose cli or gui in the xml properties file");
	}
}
