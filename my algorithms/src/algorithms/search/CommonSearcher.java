package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * abstract class which defines the the algorithm that searches
 * @author Barak Kenan
 * @param <T>
 * @param <T> the type of the representation of a state
 */
public abstract class CommonSearcher<T> implements Searcher<T>
{
	protected PriorityQueue<State<T>> open;
	private int numberOfEvaluatedNodes;

	/**
	 * constructor that set the number of evaluated nodes to zero, and initialize the open list
	 */
	public CommonSearcher()
	{
		this.numberOfEvaluatedNodes = 0;
		open = new PriorityQueue<State<T>>(new Comparator<State<T>>(){
			@Override
			public int compare(State<T> s1,State <T> s2)
			{
				return (int) (s1.getCost() - s2.getCost());
			}
		});
	}


	/**
	 * pop the state with the lowest cost from the open list and return it
	 * @return the state with the lowest cost
	 */
	public State<T> popFromOpenList()
	{
		this.numberOfEvaluatedNodes++;
		return open.poll();
	}

	/**
	 * push the state to the open list
	 * @param s the state we want to add to the open list
	 */
	public void addToOpenList(State<T> s)
	{
		open.add(s);
	}

	/**
	 * calculates the cost between two states
	 * @param currentState the current state
	 * @param neighborState the state we want to calculate till it
	 * @return the basic cost between currentState to neighborState
	 */
	public double CalculateCost(State<T> currentState,State<T> neighborState)
	{
		return 1;
	}


	/**
	 * reverse an array list
	 * @param list the array list we want to reverse
	 * @return the reversed array list
	 */
	public ArrayList<State <T>> reverse(ArrayList<State<T>> list)
	{
		for(int i = 0, j = list.size() - 1; i < j; i++) 
			list.add(i, list.remove(j));
		return list;
	}

	/**
	 * goes from the goal state to the start state to find the solution path
	 * @param goal the the goal state
	 * @param start the start state
	 * @return the solution
	 */
	public Solution<T> backTrace(State<T>goal, State<T>start)
	{
		State <T>temp=goal;

		ArrayList<State<T>>backtrace=new ArrayList<State<T>>();
		while(temp.getCameFrom()!=null)
		{
			backtrace.add(temp);
			temp=temp.getCameFrom(); 
		}

		backtrace.add(temp);
		Collections.reverse(backtrace);
		return new Solution<T>(backtrace);
	}


	/**
	 * getter
	 * @return the number of evaluated nodes
	 */
	@Override
	public int getNumberOfNodesEvaluated()
	{
		return numberOfEvaluatedNodes;		
	}
	
	/**
	 * setter
	 * @param a number of evaluated nodes
	 */
	public int getNumberOfEvaluatedNodes()
	{
		return numberOfEvaluatedNodes;
	}


	public void setNumberOfEvaluatedNodes(int numberOfEvaluatedNodes) {
		this.numberOfEvaluatedNodes = numberOfEvaluatedNodes;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract Solution<T> search(Searchable<T> s);

}
