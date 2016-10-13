package view;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import presenter.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * defines a specific view - GUI
 * @author Barak Kenan
 */
public class GUI_View extends BasicWindow implements View
{
	Maze3d maze3d;
	Maze2dWindow maze2d;
	Game game;

	private boolean askedAnimations; // true - solution by animation, false - solution by traces

	private Text mazeName;

	private Spinner SpinnerFloorsX, SpinnerRowsY, SpinnerColumnsZ;

	private Button generate, solve, traces;

	private Combo comboGenerate;
	String[] algorithmsGenerate = {"Growing Tree","Simple"};

	private Combo comboSolve;
	String[] algorithmsSolve = {"BFS","DFS"};

	private Menu menuBar, fileMenu, PropertiesMenu;
	private MenuItem fileMenuHeader,PropertiesMenuHeader;
	private MenuItem fileSaveItem, fileLoadItem, exitItem;
	private MenuItem fileSavePropertiesItem, fileLoadPropertiesItem;

	private Properties properties;
	private	MouseWheelListener mouseZoomlListener;



	/**
	 * constructor
	 */
	public GUI_View(String title, int width, int height)
	{
		super(title, width, height);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	void initWidgets()
	{
		shell.setLayout(new GridLayout(2, false));

		//generate group
		Group groupGenerate = new Group(shell, SWT.BORDER);
		groupGenerate.setLayout(new GridLayout(1,false));
		groupGenerate.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
	


		//maze name - label&text
		Group groupTextName = new Group(groupGenerate, SWT.BORDER);
		groupTextName.setLayout(new GridLayout(2,false));
		groupTextName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		new Label(groupTextName, SWT.NONE).setText("Maze Name:");
		mazeName = new Text(groupTextName, SWT.BORDER);


		//maze window (game borad)
		maze2d=new Maze2dWindow(shell, SWT.BORDER | SWT.DOUBLE_BUFFERED);
		maze2d.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		//maze2d.setFocus();

		//floors spinner
		Group groupSizeMaze = new Group(groupGenerate, SWT.BORDER);
		groupSizeMaze.setLayout(new GridLayout(6,false));
		groupSizeMaze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		new Label(groupSizeMaze, SWT.NONE).setText("Floors:");
		SpinnerFloorsX = new Spinner(groupSizeMaze, SWT.BORDER);
		SpinnerFloorsX.setMinimum(3);
		SpinnerFloorsX.setMaximum(30);
		SpinnerFloorsX.setSelection(3);
		SpinnerFloorsX.setIncrement(1);


		//rows spinner
		new Label(groupSizeMaze, SWT.NONE).setText("Rows:");
		SpinnerRowsY = new Spinner(groupSizeMaze, SWT.BORDER);
		SpinnerRowsY.setMinimum(3);
		SpinnerRowsY.setMaximum(30);
		SpinnerRowsY.setSelection(3);
		SpinnerRowsY.setIncrement(1);


		//columns spinner
		new Label(groupSizeMaze, SWT.NONE).setText("Columns:");
		SpinnerColumnsZ = new Spinner(groupSizeMaze, SWT.BORDER);
		SpinnerColumnsZ.setMinimum(3);
		SpinnerColumnsZ.setMaximum(30);
		SpinnerColumnsZ.setSelection(3);
		SpinnerColumnsZ.setIncrement(1);


		//generate algorithms
		Group groupGenerateAlg = new Group(groupGenerate, SWT.BORDER);
		groupGenerateAlg.setLayout(new GridLayout(1,true));
		groupGenerateAlg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,false, 1, 1));
		new Label(groupGenerateAlg, SWT.NONE).setText("Maze Generate Algorithm:");

		comboGenerate = new Combo(groupGenerateAlg, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboGenerate.setItems(algorithmsGenerate);
		comboGenerate.select(0);
		comboGenerate.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		//generate botton
		generate=new Button(groupGenerate, SWT.PUSH);
		generate.setText("Generate Maze");
		generate.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		
		//solve algorithms
		Group groupSolve = new Group(shell, SWT.BORDER);
		groupSolve.setLayout(new GridLayout(1,true));
		groupSolve.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,false, 1, 1));
		new Label(groupSolve, SWT.NONE).setText("Maze Solution Algorithm:");


		comboSolve = new Combo(groupSolve, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboSolve.setItems(algorithmsSolve);
		comboSolve.select(0);
		comboSolve.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,1,1));

		//solve button
		solve = new Button(groupSolve, SWT.PUSH);
		solve.setText("Solve Maze");
		solve.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,1,1));
		solve.setEnabled(false);

		//show traces button
		traces = new Button(groupSolve, SWT.PUSH);
		traces.setText("Show Traces");
		traces.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,1,1));
		traces.setEnabled(false);

		
//		//arrows
//		Group groupArrows = new Group(shell, SWT.BORDER);
//		groupArrows.setLayout(new GridLayout(1,true));
//		groupArrows.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,false, 1, 2));
//
//		
//		//up button - this arrow will be disabled if the user can't do up
//		upButton = new Button(groupArrows, SWT.ARROW | SWT.UP);
//		upButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
//		//upButton.setEnabled(false);
//
//		//down button - this arrow will be disabled if the user can't do down
//		downButton = new Button(groupArrows, SWT.ARROW | SWT.DOWN);
//		downButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
//		//downButton.setEnabled(false);


		//menu
		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);

		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Save Maze");

		fileLoadItem = new MenuItem(fileMenu, SWT.PUSH);
		fileLoadItem.setText("&Load Maze");

		exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("&Exit");


		PropertiesMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		PropertiesMenuHeader.setText("&Properties");

		PropertiesMenu = new Menu(shell, SWT.DROP_DOWN);
		PropertiesMenuHeader.setMenu(PropertiesMenu);

		fileLoadPropertiesItem = new MenuItem(PropertiesMenu, SWT.PUSH);
		fileLoadPropertiesItem.setText("&Load Properties");

		fileSavePropertiesItem = new MenuItem(PropertiesMenu, SWT.PUSH);
		fileSavePropertiesItem.setText("&Save Properties");


		//..................................................................

		//load properties listener
		fileLoadPropertiesItem.addSelectionListener(new SelectionListener()
		{
			private String fileName;

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				FileDialog fd = new FileDialog(shell,SWT.OPEN); 
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml"}; 
				fd.setFilterExtensions(filterExt);
				fileName = fd.open();
				setChanged();
				notifyObservers("load properties "+fileName);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});


		//save properties listener
		fileSavePropertiesItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				Properties temp = properties;
				if(mazeName.getText().equals(""))
					displayMessageBox("please enter name","Error");
				else
				{
					if (comboSolve.getSelectionIndex()==0 && comboGenerate.getSelectionIndex()==0) //GrowingTree&BFS
						properties = new Properties(temp.getNumOfThreads(), SpinnerFloorsX.getSelection(), SpinnerRowsY.getSelection(), SpinnerColumnsZ.getSelection(), mazeName.getText(), "GrowingTree", "BFS", temp.getViewType(), temp.getIp(), temp.getPort());					

					else if (comboSolve.getSelectionIndex()==0 && comboGenerate.getSelectionIndex()==1) //Simple&BFS
						properties = new Properties(temp.getNumOfThreads(), SpinnerFloorsX.getSelection(), SpinnerRowsY.getSelection(), SpinnerColumnsZ.getSelection(), mazeName.getText(), "Simple", "BFS", temp.getViewType(), temp.getIp(), temp.getPort());

					else if (comboSolve.getSelectionIndex()==1 && comboGenerate.getSelectionIndex()==0) //GrowingTree&DFS
						properties = new Properties(temp.getNumOfThreads(), SpinnerFloorsX.getSelection(), SpinnerRowsY.getSelection(), SpinnerColumnsZ.getSelection(), mazeName.getText(), "GrowingTree", "DFS", temp.getViewType(), temp.getIp(), temp.getPort());					

					else if (comboSolve.getSelectionIndex()==1 && comboGenerate.getSelectionIndex()==1) //Simple&DFS
						properties = new Properties(temp.getNumOfThreads(), SpinnerFloorsX.getSelection(), SpinnerRowsY.getSelection(), SpinnerColumnsZ.getSelection(), mazeName.getText(), "Simple", "DFS", temp.getViewType(), temp.getIp(), temp.getPort());	

					try
					{
						XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream("properties.xml"));
						xmlEncoder.writeObject(properties);
						xmlEncoder.close();
						displayMessageBox("Properties saved","Success");
					} 
					catch (FileNotFoundException ef)
					{
						displayMessageBox("saving properties failed","Error");
					} 
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});


		//exit listener
		exitItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				setChanged();
				notifyObservers("exit");
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});


		//generate listener
		generate.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{	
				if(mazeName.getText().equals(""))
					displayMessageBox("please type maze name","Error");

				else
				{
					String inst;
					if(comboGenerate.getSelectionIndex()==0) //Growing Tree
						inst = "generate 3d maze-"+mazeName.getText()+" "+SpinnerFloorsX.getText()+" "+SpinnerRowsY.getText()+" "+SpinnerColumnsZ.getText()+" GrowingTree";
					else //Simple
						inst = "generate 3d maze-"+mazeName.getText()+" "+SpinnerFloorsX.getText()+" "+SpinnerRowsY.getText()+" "+SpinnerColumnsZ.getText()+" Simple";

					solve.setEnabled(true);
					traces.setEnabled(true);
					generate.setEnabled(false);
					setChanged();
					notifyObservers(inst);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});


		//solve listener
		solve.addSelectionListener(new SelectionListener()
		{	
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				askedAnimations = true;
				String inst = "solve-"+mazeName.getText()+" "+comboSolve.getText();
				setChanged();
				notifyObservers(inst);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		//traces listener
		traces.addSelectionListener(new SelectionListener()
		{	
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				askedAnimations = false;
				String inst = "solve-"+mazeName.getText()+" "+comboSolve.getText();
				setChanged();
				notifyObservers(inst);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});


		//save maze listener
		fileSaveItem.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{				
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				String[] filterExt = {"*.txt"}; 
				dialog.setFilterExtensions(filterExt);		                                    
				dialog.setFilterPath("");
				dialog.setFileName("MyMaze");
				String fileName = dialog.open();

				String inst = "save maze-"+mazeName.getText()+" "+fileName;
				setChanged();
				notifyObservers(inst);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});


		//load maze listener
		fileLoadItem.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				Shell sh = new Shell(shell);
				sh.setLayout(new GridLayout(1, false));
				sh.setText("Maze Name");
				new Label(sh, SWT.NONE).setText("Loaded Maze Name:");
				Text loadedMazeName = new Text(sh, SWT.BORDER);

				Button ok = new Button(sh, SWT.PUSH);
				ok.setText("OK");
				ok.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

				ok.addSelectionListener(new SelectionListener()
				{

					@Override
					public void widgetSelected(SelectionEvent arg0)
					{
						sh.setVisible(false);

						FileDialog dialog = new FileDialog(shell, SWT.OPEN);
						dialog.setText("Load");
						dialog.setFilterPath("");
						String[] filterExt = {"*.txt"}; 
						dialog.setFilterExtensions(filterExt);   	
						String fileName = dialog.open();

						String inst = "load maze-"+fileName.substring(0, fileName.lastIndexOf(".txt"))+" "+loadedMazeName.getText();
						sh.close();
						solve.setEnabled(true);
						traces.setEnabled(true);
						generate.setEnabled(false);
						setChanged();
						notifyObservers(inst);			
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {}
				});

				sh.pack();
				sh.open();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});


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
					notifyObservers("exit");
					arg0.doit=true;

				}

				else
					arg0.doit=false;
			}
		});

		//mouse wheel listener 
		mouseZoomlListener = new MouseWheelListener()
		{
			@Override
			public void mouseScrolled(MouseEvent e)
			{
				if ((e.stateMask & SWT.CTRL) != 0) //ctrl+mouse wheel
					maze2d.setSize(maze2d.getSize().x + e.count, maze2d.getSize().y + e.count);
			}
		};
		shell.addMouseWheelListener(mouseZoomlListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() throws FileNotFoundException, IOException {}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewMenu() {}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplayMessage(String msg)
	{
		displayMessageBox(msg,"error");		
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplayMaze(byte[] byteArr)
	{		
		Maze3d m = new Maze3d(byteArr);
		//System.out.println(m);
		setMaze3d(m);
		maze2d.setMaze3d(m);
		maze2d.setFocus();
		game=new Game(maze2d,m);
		game.startGame();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplayCrossSectionBy(int[][] crossSection) {}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void viewDisplaySolution(Solution<Position> solution)
	{
		game.solveGame(solution, askedAnimations);		
	}

	/**
	 * getter
	 * @return maze3d
	 */
	public Maze3d getMaze3d()
	{
		return maze3d;
	}


	/**
	 * setter
	 * @param maze3d the maze3d
	 */
	public void setMaze3d(Maze3d maze3d)
	{
		this.maze3d = maze3d;
	}

	/**
	 * display a message in a message box
	 * @param msg the message
	 * @return title the message box's title
	 */
	private void displayMessageBox(String msg, String title)
	{
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WORKING | SWT.OK);
		messageBox.setText(title);
		messageBox.setMessage(msg);
		messageBox.open();	
	}


	/**
	 * update widgets values according to the properties
	 * @param pro holds all the properties we read from the xml file 
	 */
	@Override
	public void resetProperties(Properties pro)
	{
		mazeName.setText(pro.getMazeName());

		SpinnerFloorsX.setSelection(pro.getX());
		SpinnerRowsY.setSelection(pro.getY());
		SpinnerColumnsZ.setSelection(pro.getZ());

		if(pro.getGeneratorAlg().equals("GrowingTree"))
			comboGenerate.select(0);
		else if(pro.getGeneratorAlg().equals("Simple"))
			comboGenerate.select(1);	

		if(pro.getSolverAlg().equals("BFS"))
			comboSolve.select(0);
		else if(pro.getSolverAlg().equals("DFS"))
			comboSolve.select(1);	
	}

}
