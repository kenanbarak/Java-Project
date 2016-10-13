package boot;

import model.Model;
import model.MyModel;
import view.MyView;
import view.View;
import controller.Controller;
import controller.MyController;

/**
 * @author Barak Kenan
 */
public class Run
{
	public static void main(String[] args) 
	{
		Controller c=new MyController();
		Model m=new MyModel(c);
		View v=new MyView(c);
		c.setM(m);
		c.setV(v);
		v.start();
	}

}
