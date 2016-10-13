package algorithms.mazeGenerators;

import java.util.ArrayList;

public abstract class CommonPop implements Pop
{
	@Override
	public abstract Position selectPop(ArrayList<Position> pos);
}
