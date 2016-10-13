package algorithms.search;

import java.util.ArrayList;


/**
 * a specific implementation of searcher - BFS algorithm
 * @author Barak Kenan
 * @param <T> the type of the representation of a state
 */

public class BFS_Searcher<T> extends CommonSearcher<T>
{

	/**
	 * finds the solution by BFS algorithm
	 * @param s the searching problem
	 */
	@Override
	public Solution<T> search(Searchable<T> s)
	{
		if (s==null)
			return null;

		open.add(s.getStartState()); //add the start state to the open list
		ArrayList<State<T>> closedSet=new ArrayList<State<T>>();


		while(open.size()>0)
		{
			State<T> n=popFromOpenList(); //remove state from the open list
			closedSet.add(n); //add n to the close list

			if(n.equals(s.getGoalState())) //if we finish (n is the goal) - return the backtrace
				return backTrace(n, s.getStartState()); 


			ArrayList<State<T>> successors=s.getPossibleStates(n);
			for(State<T> state : successors)
			{
				if(!closedSet.contains(state) && !open.contains(state))
				{
					state.setCameFrom(n);
					state.setCost(n.getCost()+CalculateCost(n,state));
					addToOpenList(state);
				} 

				else
				{
					if(n.getCost()+CalculateCost(n, state)<state.getCost())
					{
						if(!(open.contains(state)))
							open.add(state);

						else
						{
							open.remove(state);
							open.add(state);
						}
					}
				}
			}
		}
		return null;
	}
}
