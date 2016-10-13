package algorithms.mazeGenerators;

import java.util.ArrayList;


public class LastSelectionPop extends CommonPop
{
	@Override
	//select the last cell
	public Position selectPop(ArrayList<Position> p) 
	{
		return p.get(0);
	}
}