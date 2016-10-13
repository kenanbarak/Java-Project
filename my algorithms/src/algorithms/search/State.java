
package algorithms.search;

import java.io.Serializable;

/**
 * defines a state in the searching problem
 * @author Barak Kenan
 * @param <T> the type of the representation of a state
 */
@SuppressWarnings("serial")
public class State<T> implements Serializable
{
	private T state;
	private State<T> cameFrom;
	private double cost;


	public State(T state)
	{
		this.cost=0;
		this.state=state;
	}

	/**
	 * getter
	 * @return the state (representation)
	 */
	public T getState() 
	{
		return state;
	}

	/**
	 * setter
	 * @param  state the new state (representation)
	 */
	public void setState(T state) 
	{
		this.state = state;
	}

	/**
	 * getter
	 * return the state's cost
	 */
	public double getCost() 
	{
		return cost;
	}

	/**
	 * setter
	 * @param cost the new cost
	 */
	public void setCost(double cost) 
	{
		this.cost = cost;
	}

	/**
	 * getter
	 * @return the state we came from to our current state
	 */
	public State<T> getCameFrom() 
	{
		return cameFrom;
	}

	/**
	 * setter
	 * @param  cameFrom the new state we came from to our current state
	 */
	public void setCameFrom(State<T> cameFrom) 
	{
		this.cameFrom = cameFrom;
	}


	/**
	 * compare between two states
	 * @param obj the state we compare to
	 * @return return true if equal otherwise false
	 */
	@Override
	public boolean equals(Object obj)
	{ 	
		if((obj instanceof State<?>)==false)
			return false;

		State<?> s = (State<?>)obj;
		return this.state.equals(s.getState());	
	}


	/**
	 * to string
	 * @return the representation by string
	 */
	@Override
	public String toString()
	{
		if(cameFrom==null)
			return state.toString()+" {"+cost+",null}";

		return state.toString()+" {"+cost+","+cameFrom.getState()+"}";
	}

}
