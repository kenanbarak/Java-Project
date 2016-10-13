package algorithms.mazeGenerators;

public interface Maze3dGenerator
{
	public Maze3d generate(int x_size, int y_size, int z_size);
	public String measureAlgorithmTime(int x_size, int y_size, int z_size);
}
