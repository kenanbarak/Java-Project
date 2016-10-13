
package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * defines a solution in the searching problem
 * @author Barak Kenan
 * @param <T> the type of the representation of a state
 */
@SuppressWarnings("serial")
public class Solution<T> implements Serializable
{
	ArrayList<State<T>> states;

	/**
	 * constructor that initialize the array list
	 * @param states the solution array list
	 */
	public Solution(ArrayList<State<T>> states)
	{
		this.states=states;
	}

	/**
	 * getter
	 * @return the solution (al)
	 */
	public ArrayList<State<T>> getStates()
	{
		return states;
	}


	/**
	 * setter
	 * @param al the new solution (al))
	 */
	public void setStates(ArrayList<State<T>> states)
	{
		this.states = states;
	}

	
	/**
	 * override toString function
	 * @return the solution by string
	 */
	@Override
	public String toString() 
	{
		String s="";
		for(int i=0;i<states.size();i++)
		{
			if(i==states.size()-1)
				s=s+states.get(i).getState()+"\n ";
			else
				s=s+states.get(i).getState()+", ";	
		}
		return s;
	}
	

}
