package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleGenerator extends CommonMaze3dGenerator
{
	private Maze3d maze3d;
	private Random rnd=new Random();


	@Override
	public Maze3d generate(int x_size, int y_size, int z_size)
	{
		maze3d=new Maze3d(x_size*2+1, y_size*2+1, z_size*2+1);
		maze3d.GetRandomStartPosition();
		maze3d.GetRandomGoalPosition();
		maze3d.createLimits();
		createMaze(maze3d);
		return maze3d;
	}


	private void createMaze(Maze3d arr)
	{
		int num;
		for(int i=2;i<maze3d.getX_size()-2;i++)
			for(int j=1;j<maze3d.getY_size()-1;j++)
				for(int k=1;k<maze3d.getY_size()-1;k++)
				{
					num=rnd.nextInt(2);
					maze3d.setPostionValue(i,j,k,num); //put 0 or 1, randomly
				}


		if(maze3d.getStartPosition().getZ()<maze3d.getGoalPosition().getZ())
		{
			for(int i=maze3d.getStartPosition().getZ();i<maze3d.getGoalPosition().getZ()+1;i++)
				maze3d.setPostionValue(2,0,i,0);

			if(maze3d.getStartPosition().getZ()>maze3d.getGoalPosition().getZ())
				for(int i=maze3d.getGoalPosition().getZ();i<maze3d.getStartPosition().getZ()+1;i++)
					maze3d.setPostionValue(2,0,i,0);
		}
		
		for(int i=2;i<maze3d.getGoalPosition().getX();i++)
		{
			for(int j=0;j<maze3d.getY_size();j++)
				maze3d.setPostionValue(i,j,maze3d.getGoalPosition().getZ(),0);
		}
		maze3d.setPostionValue(maze3d.getStartPosition().getX(),maze3d.getStartPosition().getY(),maze3d.getStartPosition().getZ(),0);
		maze3d.setPostionValue(maze3d.getStartPosition().getX()+1,maze3d.getStartPosition().getY(),maze3d.getStartPosition().getZ(),0);
		maze3d.setPostionValue(maze3d.getGoalPosition().getX(),maze3d.getGoalPosition().getY(),maze3d.getGoalPosition().getZ(),0);
	}

}
