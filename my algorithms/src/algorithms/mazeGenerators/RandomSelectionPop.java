package algorithms.mazeGenerators;


import java.util.ArrayList;
import java.util.Random;


public class RandomSelectionPop extends CommonPop 
{
	@Override
	//select random cell
	public Position selectPop(ArrayList<Position> p) 
	{
		Random rand=new Random();
		int randomNum=rand.nextInt(p.size());
		return p.get(randomNum);
	}

}