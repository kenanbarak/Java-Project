package algorithms.mazeGenerators;

public abstract class CommonMaze3dGenerator implements Maze3dGenerator
{
	@Override
	public abstract Maze3d generate(int x_size, int y_size, int z_size);


	@Override
	public String measureAlgorithmTime(int x_size, int y_size, int z_size) 
	{
		long start= System.nanoTime();
		generate(x_size, y_size, z_size);
		long end = System.nanoTime();
		return String.valueOf(end-start);
	}
}
