package UnitTesting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import algorithms.mazeGenerators.Position;
import algorithms.search.BFS_Searcher;
import algorithms.search.Solution;

/**
 * unit testing for BFS_Searcher class
 * @author Barak Kenan
 */

public class BFS_SearcherTest
{

	@Test
	public void test() 
	{
		//searchable is null
		BFS_Searcher<Position> bfs = new BFS_Searcher<Position>();		
		Solution<Position> output = bfs.search(null);		
		assertEquals(null,output);	
	}

}
