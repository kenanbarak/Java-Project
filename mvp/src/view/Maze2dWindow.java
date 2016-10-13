package view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * defines a specific maze window - maze 2d window
 * @author Barak Kenan
 */
public class Maze2dWindow extends MazeWindow
{
	private Text t;
	private int floor;
	private boolean isGameOver;
	private Image arrowUpImg, arrowDownImg, arrowBothImg,goalImg, playerImg, wallImg, traceImg;


	/**
	 * constructor
	 */
	public Maze2dWindow(Composite parent, int style)
	{
		super(parent, SWT.DOUBLE_BUFFERED); //DOUBLE_BUFFERED is for prevent flickering when we do complex calculations while drawing
		this.floor=0;
		this.isGameOver=false;

		//images
		arrowUpImg = new Image(getDisplay(), "images/arrowUp.png");
		arrowDownImg = new Image(getDisplay(), "images/arrowDown.png");
		arrowBothImg = new Image(getDisplay(), "images/arrowBoth.png");
		goalImg = new Image(getDisplay(), "images/goal.png");
		playerImg = new Image(getDisplay(), "images/player.png");
		wallImg = new Image(getDisplay(), "images/wall.png");
		traceImg = new Image(getDisplay(), "images/trace.png");
	}

	/**
	 * set player position and redraw it
	 * @param row the wanted row
	 * @param col the wanted column 
	 */
	public void setPlayerPosition(int row, int col)
	{
		playerPosition.setY(row);
		playerPosition.setZ(col);
		redraw();
	}


	/**
	 * initialize the game board by drawing walls, arrows, goal and player
	 */
	public void startGame()
	{
		t=new Text(getShell(), SWT.READ_ONLY | SWT.BORDER);
		t.setText("Floor: " +floor);

		redraw();

		addPaintListener(new PaintListener()
		{		
			@Override
			public void paintControl(PaintEvent e)
			{
				setBackground(new Color(null,254,241,181));
				setForeground(new Color(null,112,76,41));

				int width=getSize().x;
				int height=getSize().y;

				int w=width/mazeData[0].length;
				int h=height/mazeData.length;

				for(int i=0;i<mazeData.length;i++)
				{
					for(int j=0;j<mazeData[i].length;j++)
					{
						int x=j*w;
						int y=i*h;


						//draw walls where there is 1
						if(mazeData[i][j]==1)
							e.gc.drawImage(wallImg, 0, 0, wallImg.getBounds().width, wallImg.getBounds().height, x, y, w, h);


						//						else if (!playerPosition.equals(maze3d.getGoalPosition()))//draw arrows
						//						{
						//							//up&down
						//							if((playerPosition.getX()+2 < maze3d.getX_size() && maze3d.getPositionValue(playerPosition.getX(), i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() +1, i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() + 2, i, j) == 0
						//									&& (playerPosition.getX()-2 > 0 && maze3d.getPositionValue(playerPosition.getX(), i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() - 1, i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() - 2, i, j) == 0)))
						//								e.gc.drawImage(arrowBothImg, 0, 0, 60, 60, x, y, w, h);
						//
						//							else
						//							{
						//								//up
						//								if(playerPosition.getX()+2 < maze3d.getX_size() && maze3d.getPositionValue(playerPosition.getX(), i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() + 1, i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() + 2, i, j) == 0)
						//									e.gc.drawImage(arrowUpImg, 0, 0, 60, 60, x, y, w, h);
						//
						//								//down
						//								if((playerPosition.getX()-2 > 0 && maze3d.getPositionValue(playerPosition.getX(), i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() - 1, i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() - 2, i, j) == 0)
						//										|| (playerPosition.equals(new Position(maze3d.getStartPosition().getX()-2,maze3d.getStartPosition().getY(),maze3d.getStartPosition().getZ()))))
						//									e.gc.drawImage(arrowDownImg, 0, 0, 60, 60, x, y, w, h);
						//							} 
						//						}

						else if (!playerPosition.equals(maze3d.getGoalPosition()))//draw arrows
						{
								//up
								boolean up = (playerPosition.getX()+2 < maze3d.getX_size() && maze3d.getPositionValue(playerPosition.getX(), i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() + 1, i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() + 2, i, j) == 0);

								//down
								boolean down = (playerPosition.getX()-2 > 0 && maze3d.getPositionValue(playerPosition.getX(), i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() - 1, i, j) == 0 && maze3d.getPositionValue(playerPosition.getX() - 2, i, j) == 0);

								if (up && down)
									e.gc.drawImage(arrowBothImg, 0, 0, 60, 60, x, y, w, h);
								else if (up)
									e.gc.drawImage(arrowUpImg, 0, 0, 60, 60, x, y, w, h);
								else if (down)
									e.gc.drawImage(arrowDownImg, 0, 0, 60, 60, x, y, w, h);
						}
					} 
				}

				//draw goal image 
				if(playerPosition.getX() == maze3d.getGoalPosition().getX())  
					e.gc.drawImage(goalImg, 0, 0, 200, 350, maze3d.getGoalPosition().getZ()*w, maze3d.getGoalPosition().getY()*h, w, h);				

				//show winning message
				if(playerPosition.equals(maze3d.getGoalPosition()))
				{
					setGameOver(true);
					MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK);
					messageBox.setText("Maze Solved");
					messageBox.setMessage("You did it!");
					messageBox.open();
				}

				//draw the player
				if(playerPosition.getX() != maze3d.getGoalPosition().getX())
					e.gc.drawImage(playerImg, 0, 0, 260, 280 ,playerPosition.getZ()*w, playerPosition.getY()*h, w, h);			   
			}

		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveUp()
	{
		setPlayerPosition(playerPosition.getY()-1, playerPosition.getZ());		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveDown()
	{
		setPlayerPosition(playerPosition.getY()+1, playerPosition.getZ());			
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveLeft()
	{
		setPlayerPosition(playerPosition.getY(), playerPosition.getZ()-1);		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveRight()
	{
		setPlayerPosition(playerPosition.getY(), playerPosition.getZ()+1);		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pageUp()
	{
		playerPosition.setX(playerPosition.getX()+2);
		floor+=2;
		t.setText("Floor: "+floor);
		redraw();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pageDown()
	{
		playerPosition.setX(playerPosition.getX()-2);
		floor-=2;
		t.setText("Floor: "+floor);
		redraw();
	}

	public void move(Position p)
	{
		this.mazeData = maze3d.getCrossSectionByX(p.getX());
		this.playerPosition.setPosition(p.getX(),p.getY(),p.getZ());
		setPlayerPosition(p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void printSolution(Solution<Position> sol)
	{
		//converting ArrayList of State<Position> to ArrayList of Position
		ArrayList<Position> arr = new ArrayList<Position>();
		for (int i=0; i<sol.getStates().size(); i++)
		{
			Position p = sol.getStates().get(i).getState();
			arr.add(p);
		}


		if (!isGameOver())
		{
			getDisplay().timerExec(400, new Runnable()
			{
				int index=1;

				@Override
				public void run()
				{
					if(playerPosition.getX()<arr.get(index).getX())
					{
						setMazeData(maze3d.getCrossSectionByX(playerPosition.getX()+2));
						pageUp();
						redraw();
					}

					else if(playerPosition.getX()>arr.get(index).getX())
					{
						setMazeData(maze3d.getCrossSectionByZ(playerPosition.getX()-2));
						pageDown();
						redraw();
					}
					else
					{
						if(playerPosition.getZ()<arr.get(index).getZ())
							setPlayerPosition(playerPosition.getY(),playerPosition.getZ()+1);


						if(playerPosition.getZ()>arr.get(index).getZ())
							setPlayerPosition(playerPosition.getY(),playerPosition.getZ()-1);


						if(playerPosition.getY()>arr.get(index).getY())				
							setPlayerPosition(playerPosition.getY()-1,playerPosition.getZ());


						if(playerPosition.getY()<arr.get(index).getY())				
							setPlayerPosition(playerPosition.getY()+1,playerPosition.getZ());
					}
					getDisplay().timerExec(900,this);

					index++;

					if (index == (arr.size())) 
						getDisplay().timerExec(-1, this);
				}
			});
		}	
		setGameOver(true); //setting game over to true after the animation took us to the goal
	}


	public void printSolutionTraces(Solution<Position> sol)
	{
		addPaintListener(new PaintListener()
		{		
			@Override
			public void paintControl(PaintEvent e)
			{
				ArrayList<Position> arr = new ArrayList<Position>();
				for (int i=0; i<sol.getStates().size(); i++)
				{
					Position p = sol.getStates().get(i).getState();
					arr.add(p);
				}


				int width=getSize().x;
				int height=getSize().y;

				int w=width/mazeData[0].length;
				int h=height/mazeData.length;

				for(int i=0;i<mazeData.length;i++)
				{
					for(int j=0;j<mazeData[i].length;j++)
					{
						int x=j*w;
						int y=i*h;

						if (arr.contains(new Position(playerPosition.getX(),i,j)))
							e.gc.drawImage(traceImg, 0, 0, traceImg.getBounds().width, traceImg.getBounds().height, x, y, w, h);
					}
				}
			}
		});
		
		startGame();
	}


	public boolean isGameOver()
	{
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver)
	{
		this.isGameOver = isGameOver;
	}
}


