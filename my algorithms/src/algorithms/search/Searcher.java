package algorithms.search;

/**
 * defines a algorithm which searches in the searching problem
 * @author Barak Kenan
 * @param <T> the type of the representation of a state
 */
public interface Searcher<T> 
{
	/**
	 * finds the solution of a searching problem
	 * @param s the searching problem
	 * @return the solution of the problem
	 */
    public Solution<T> search(Searchable<T> s);

	/**
	 * getter
	 * @return the number of evaluated nodes
	 */
    public int getNumberOfNodesEvaluated();

}

