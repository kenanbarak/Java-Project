package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
/**
 * defines a specific view - GUi
 * @author Barak Kenan
 */
public class GUI_View extends CommonView
{
	Display display;
	Shell shell;
	HashMap<String,Listener> listeners;
	int numOfClients;
	Button openButton, closeButton;
	Label message;

	/**
	 * constructor
	 * @param width
	 * @param length
	 * @param title
	 * @param listeners
	 */
	public GUI_View(int width, int length, String title)
	{
		// Check if display already exist
		if(Display.getCurrent() != null)
			this.display = Display.getCurrent();		
		else
			this.display = new Display();

		shell = new Shell(this.display);
		shell.setSize(width,length);
		shell.setText(title);

		this.numOfClients = 5;
	}

	public void initWidgets()
	{
		shell.setLayout(new GridLayout(1,false));

		//open button
		openButton=new Button(shell, SWT.PUSH);
		openButton.setText("Open Server");
		openButton.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		
		openButton.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				//call the presenter to open the server
				setChanged();
				notifyObservers("open the server");
				
				//disable the open button and enable the close button
				setButtons(true);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		//close button
		closeButton=new Button(shell, SWT.PUSH);
		closeButton.setText("Close Server");
		closeButton.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false, 1, 1));
		closeButton.setEnabled(false);
		
		closeButton.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				setChanged();
				notifyObservers("close the server");
				setButtons(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		
		//set appropriate titles
		Label title1 = new Label(shell, SWT.CENTER);
		title1.setText("Hello, welcome to my server!");
		GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		data.horizontalSpan = 3;
		title1.setLayoutData(data);

		Label title2 = new Label(shell, SWT.CENTER);
		title2.setText("Message from the server:");
		data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		title2.setLayoutData(data);

		//show messages from the server
		message = new Label(shell,SWT.CENTER);
		data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		message.setLayoutData(data);
		message.setText("");
		
		//X button listener (different from the exit listener)
		shell.addListener(SWT.Close,new Listener()
		{

			@Override
			public void handleEvent(Event arg0)
			{
				MessageBox messageBox = new MessageBox(shell,  SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBox.setMessage("Do you really want to exit?");
				messageBox.setText("Exit");
				if(messageBox.open()==SWT.YES)
				{
					setChanged();
					notifyObservers("close the server");
					shell.dispose();
					arg0.doit=true;
				}

				else
					arg0.doit=false;
			}
		});
		
	}

	/**
	 * display messages from server by changing the label text
	 * @param msg
	 */
	@Override
	public void viewDisplayMessage(String msg)
	{
		if(!shell.isDisposed())
			display.asyncExec(new Runnable()
			{
				@Override
				public void run()
				{
					message.setText(msg);
					message.setBackground(new Color(null,255,255,255));
				}
			});
	}


	/**
	 * Open shell and initialize widgets.
	 */
	public void start()
	{
		initWidgets();
		shell.open();

		while(!shell.isDisposed())
		{ 
			if(!display.readAndDispatch())
			{ 	
				display.sleep(); 			
			}

		} 
		display.dispose();
	}

	/**
	 * set buttons enable/disable by the server state
	 * @param isOpen
	 */
	public void setButtons(boolean isOpen)
	{
		display.asyncExec(new Runnable()
		{
			@Override
			public void run()
			{
				openButton.setEnabled(!isOpen);
				closeButton.setEnabled(isOpen);
			}
		});
	}
}