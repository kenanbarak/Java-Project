package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;



/**
 * defines a specific maze window - maze 2d window
 * @author Barak Kenan
 */
public class Game
{
	protected Maze2dWindow maze2d;
	protected Maze3d maze3d;
	protected Position position;

	public Game(Maze2dWindow maze2d, Maze3d maze3d)
	{
		this.maze2d = maze2d;
		this.maze3d = maze3d;
		this.position = new Position(0,0,0);
	}


	/**
	 * listen to keyboard and react correspondingly
	 */
	public void startGame()
	{
		//begin from start floor
		maze2d.setMazeData(maze3d.getCrossSectionByX(maze3d.getStartPosition().getX()));

		//set player position to start position
		maze2d.setPlayerPosition(maze3d.getStartPosition());
		maze2d.playerPosition.setPosition(maze3d.getStartPosition().getX(), maze3d.getStartPosition().getY(), maze3d.getStartPosition().getZ());

		setPosition(maze3d.getStartPosition());

		//draw maze board
		maze2d.startGame();


		KeyListener keyListener = new KeyListener()
		{
			@Override
			public void keyReleased(KeyEvent keyEvent) {}

			@Override
			public void keyPressed(KeyEvent keyEvent)
			{

				int key = keyEvent.keyCode;
				String [] moves=maze3d.getPossibleMoves(position);
				switch(key)
				{
				case SWT.ARROW_UP:
				{	
					//String [] moves=maze3d.getPossibleMoves(position);
					for(String move:moves)
					{
						if(move.equals("BackWard") && !maze2d.isGameOver())
						{
							position.setPosition(position.getX(),position.getY()-1,position.getZ());
							maze2d.moveUp();
						}
					}
				}
				break;

				case SWT.ARROW_DOWN:
				{	
					//String [] moves=maze3d.getPossibleMoves(position);
					for(String move:moves)
					{
						if(move.equals("Forward") && !maze2d.isGameOver())
						{
							position.setPosition(position.getX(),position.getY()+1,position.getZ());
							maze2d.moveDown();
						}
					}
				}
				break;

				case SWT.ARROW_RIGHT:
				{	
					//String [] moves=maze3d.getPossibleMoves(position);
					for(String move:moves)
					{
						if(move.equals("Right") && !maze2d.isGameOver())
						{
							position.setPosition(position.getX(),position.getY(),position.getZ()+1);
							maze2d.moveRight();
						}
					}
				}
				break;

				case SWT.ARROW_LEFT:
				{	
					//String [] moves=maze3d.getPossibleMoves(position);
					for(String move:moves)
					{
						if(move.equals("Left") && !maze2d.isGameOver())
						{
							position.setPosition(position.getX(),position.getY(),position.getZ()-1);
							maze2d.moveLeft();
						}
					}
				}
				break;

				case SWT.PAGE_UP:
				{	
					//String [] moves=maze3d.getPossibleMoves(position);
					for(String move:moves)
					{
						if(move.equals("Up") && !maze2d.isGameOver())
						{
							maze2d.setMazeData(maze3d.getCrossSectionByX(position.getX()+2));
							position.setPosition(position.getX()+2,position.getY(),position.getZ());
							maze2d.pageUp();
						}
					}
				}
				break;

				case SWT.PAGE_DOWN:
				{	

					//String [] moves=maze3d.getPossibleMoves(position);
					for(String move:moves)
					{
						if(move.equals("Down") && !maze2d.isGameOver())
						{
							maze2d.setMazeData(maze3d.getCrossSectionByX(position.getX()-2));
							position.setPosition(position.getX()-2,position.getY(),position.getZ());
							maze2d.pageDown();
						}
					}
				}
				break;
				}
			}
		};

		maze2d.addKeyListener(keyListener);
	}


	/**
	 * solve maze
	 */
	public void solveGame(Solution<Position> sol, boolean askedAnimations)
	{
		if (askedAnimations)
			maze2d.printSolution(sol);
		else
			maze2d.printSolutionTraces(sol);
	}


	/**
	 * getter
	 * @return position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * setter
	 * @param position
	 */
	public void setPosition(Position position)
	{
		this.position = position;
	}
}
