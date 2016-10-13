package algorithms.search;

import java.util.ArrayList;


/**
 * defines a searching problem
 * @author Barak Kenan
 * @param <T> the type of the representation of a state
 */
public interface Searchable <T>
{	
	/**
	 * getter
	 * @return start state
	 */
	public State<T> getStartState();
	
	/**
	 * getter
	 * @return goal state
	 */
	public State<T> getGoalState();
	
	/**
	 * finds the possible moves from the current state
	 * @param s the current state
	 * @return array list of possible states
	 */
	public ArrayList<State<T>> getPossibleStates(State<T> s);

}
