package algorithms.search;

import java.util.ArrayList;

/**
 * a specific implementation of searcher - DFS algorithm
 * @author Barak Kenan
 * @param <T> the type of the representation of a state
 */
public class DFS_Searcher<T> extends CommonSearcher<T>
{
	private Solution<T> solution;
	private ArrayList<State<T>> visited  =new ArrayList<State<T>>();
	private int counter=0;


	/**
	 * finds the solution by DFS algorithm
	 * @param s the searching problem
	 */
	@Override
	public Solution<T> search(Searchable<T> s)
	{
		dfs(s,s.getStartState());
		return solution;
	}
	
	
	public void dfs(Searchable<T>searchable,State<T> s)
	{
		if (s.equals(searchable.getGoalState())) //if we finish (s is the goal) - put the backtrace unto solution
		{
			solution = backTrace(s,searchable.getStartState());
			setNumberOfEvaluatedNodes(counter);
			return;
		}

		visited.add(s); //add s to the visited states list
		
		ArrayList<State<T>>successors=searchable.getPossibleStates(s);
		for(State<T> state : successors)
		{
			counter++;
			if(!visited.contains(state))
			{
				state.setCameFrom(s);
				dfs(searchable,state);
			}
		}
		return;
	}

}
