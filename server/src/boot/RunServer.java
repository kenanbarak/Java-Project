package boot;

import java.util.Observable;

import model.MyModel;
import presenter.Presenter;
import presenter.ServerProperties;
import view.CLI_View;
import view.GUI_View;
import view.View;

/**
 * @author Barak Kenan
 */
public class RunServer {

	public static void main(String[] args) 
	{
		MyModel m = new MyModel();
		View v;
		Presenter p;
				
		ServerProperties prop = new ServerProperties();
		prop = prop.readPropertiesFromFile("serverProperties.xml");
	
		
		if (prop.getViewType().equals("gui"))
		{
			v = new GUI_View(550, 250, "Welcome To Server!");
			p = new Presenter(m, v);
			
			m.addObserver(p);
			((Observable) v).addObserver(p);
			
			v.start();
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
