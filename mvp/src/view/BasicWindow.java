package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * defines abstract and basic window, contains the main loop and initWidgets abstract method
 * @author Barak Kenan
 */
public abstract class BasicWindow extends Observable implements Runnable
{
	
	protected Display display;
	protected Shell shell;
	
	public BasicWindow(String title,int width,int height)
	{
		display=new Display();
		
		shell=new Shell(display);
		shell.setText(title);
		shell.setSize(width, height);		
	}
	
	
	abstract void initWidgets();
	
	
	@Override
	public void run()
	{
		initWidgets();
		
		shell.open();
		
		// main event loop
		 while(!shell.isDisposed()) //while window isn't closed
		 { 
		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()) 	// if the queue is empty, sleep until an event occurs 
		       display.sleep();
		 } //shell is disposed

		 display.dispose(); //dispose OS components
	}
}
