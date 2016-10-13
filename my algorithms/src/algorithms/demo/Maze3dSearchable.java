package algorithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.CommonSearchable;
import algorithms.search.State;

/**
 * object adapter, from maze3d to search problem
 * @author Barak Kenan
 */

public class Maze3dSearchable extends CommonSearchable<Position>
{

	private Maze3d maze;

	/**
	 * constructor that initialize the maze
	 */
	public Maze3dSearchable(Maze3d maze)
	{
		super();
		this.maze=maze;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public State<Position> getStartState()
	{
		return new State<Position>(maze.getStartPosition());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public State<Position> getGoalState()
	{
		return new State<Position>(maze.getGoalPosition());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<State<Position>> getPossibleStates(State<Position> s)
	{
		ArrayList<State<Position>> sol = new ArrayList<State<Position>>();

		Position pos=new Position(s.getState().getX(),s.getState().getY(),s.getState().getZ());

		String[] arr = maze.getPossibleMoves(pos);

		for(int i=0;i<arr.length;i++)
		{
			switch(arr[i])
			{
			case "Up":
				State<Position> up = new State<Position>(new Position(pos.getX()+2,pos.getY(),pos.getZ()));
				sol.add(up);
				break;

			case "Down":
				State<Position> down = new State<Position>(new Position(pos.getX()-2,pos.getY(),pos.getZ()));
				sol.add(down);
				break;

			case "Forward":
				State<Position> forward = new State<Position>(new Position(pos.getX(),pos.getY()+1,pos.getZ()));
				sol.add(forward);
				break;

			case "Backward":
				State<Position> backward =new State<Position>(new Position(pos.getX(),pos.getY()-1,pos.getZ()));
				sol.add(backward);
				break;

			case "Right":
				State<Position> right = new State<Position>(new Position(pos.getX(),pos.getY(),pos.getZ()+1));
				sol.add(right);
				break;

			case "Left":
				State<Position> left = new State<Position>(new Position(pos.getX(),pos.getY(),pos.getZ()-1));
				sol.add(left);
				break;
			}
		}
		return sol;
	}

}
