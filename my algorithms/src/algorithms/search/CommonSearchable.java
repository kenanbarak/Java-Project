package algorithms.search;

import java.util.ArrayList;

/**
 * abstract class which defines the searching problem
 * @author Barak Kenan
 * @param <T> the type of the representation of a state
 */

public abstract class CommonSearchable<T> implements Searchable<T>
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract State<T> getStartState();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract State<T> getGoalState();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract ArrayList<State<T>> getPossibleStates(State<T> s);
}
