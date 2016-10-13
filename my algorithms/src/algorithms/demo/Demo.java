
package algorithms.demo;

import algorithms.mazeGenerators.SimpleGenerator;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.RandomSelectionPop;
import algorithms.mazeGenerators.LastSelectionPop;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS_Searcher;
import algorithms.search.DFS_Searcher;
import algorithms.search.Searcher;
import algorithms.search.Solution;


/**
 * @author Barak Kenan
 */

public class Demo
{
	public void Run()
	{
		//generate 3d maze
		Maze3dGenerator mg=new SimpleGenerator();
		//Maze3dGenerator mg=new GrowingTreeGenerator(new LastSelectionPop());
		//Maze3dGenerator mg=new GrowingTreeGenerator(new RandomSelectionPop());

		Maze3d maze = mg.generate(4,4,4);

		//print 3d maze
		System.out.println("the maze:\n"+maze+"\nstart: "+maze.getStartPosition()+"\ngoal: "+maze.getGoalPosition());

		//our maze become a searchable
		Maze3dSearchable ms = new Maze3dSearchable(maze);

		//solve the maze by BFS algorithm  and print solution
		Searcher<Position> sBFS = new BFS_Searcher<Position>();
		Solution<Position> solution = sBFS.search(ms);
		System.out.println("\n\nBFS Solution: \n"+solution);

		//solve the maze by DFS algorithm and print solution
		Searcher<Position> sDFS= new DFS_Searcher<Position>();
		solution=sDFS.search(ms);
		System.out.println("\n\nDFS Solution: \n"+solution);

		//print how much modes ware evaluated by each
		System.out.println("\nNodes evaluated by BFS search: "+sBFS.getNumberOfNodesEvaluated());
		System.out.println("Nodes evaluated by DFS search: "+sDFS.getNumberOfNodesEvaluated());
	}


	public static void main(String[] args)
	{
		Demo demo = new Demo();
		demo.Run();
	}
}



