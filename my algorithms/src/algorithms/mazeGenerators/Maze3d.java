package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("serial")
public class Maze3d implements Serializable
{
	private int[][][] maze3d;
	private int x_size;
	private int y_size;
	private int z_size;
	private Position startPosition;
	private Position goalPosition;
	private Random rand=new Random();

	//constructor
	public Maze3d(int x_size, int y_size, int z_size)
	{
		this.x_size = x_size;
		this.y_size = y_size;
		this.z_size = z_size;
		this.maze3d=new int[x_size][y_size][z_size];

		for (int i=0; i<x_size;i++)
			for (int j=0; j<y_size;j++)
				for (int k=0; k<z_size; k++)
					maze3d[i][j][k]=0;		
	}

	//put 1 in every cell
	public void buildWalls() 
	{
		for (int i=0; i<x_size;i++)
			for (int j=0; j<y_size;j++)
				for (int k=0; k<z_size; k++)
					maze3d[i][j][k]=1;
	}

	public void setPostionValue(int x, int y, int z, int value) //set value, 0 or 1, in the position
	{
		if ((x>=0) && (y>=0) && (z>=0) && (x<x_size) && (y<y_size) && (z<z_size))
			maze3d[x][y][z]=value;
	}

	public void setPostionValue(Position p, int value) //set value, 0 or 1, in the position
	{
		if ((p.getX()>=0) && (p.getY()>=0) && (p.getZ()>=0) && (p.getX()<x_size) && (p.getY()<y_size) && (p.getY()<z_size))
			maze3d[p.getX()][p.getY()][p.getZ()]=value;
	}


	public int getPositionValue(int x,int y,int z) //get value
	{
		if ((x>=0) && (y>=0) && (z>=0) && (x<x_size) && (y<y_size) && (z<z_size))
			return maze3d[x][y][z];
		return -1;
	}

	public int getPositionValue(Position p) //get value
	{
		if ((p.getX()>=0) && (p.getY()>=0) && (p.getZ()>=0) && (p.getX()<x_size) && (p.getY()<y_size) && (p.getZ()<z_size))
			return maze3d[p.getX()][p.getY()][p.getZ()];
		return -1;
	}


	public Position getStartPosition()
	{
		return startPosition;
	}

	public void setStartPosition(Position startPosition)
	{
		this.startPosition = startPosition;
	}

	public Position getGoalPosition()
	{
		return goalPosition;
	}

	public void setGoalPosition(Position goalPosition)
	{
		this.goalPosition = goalPosition;
	}

	//return string array with all possible moves from position pos
	public String [] getPossibleMoves(Position pos)
	{
		ArrayList<String> moves = new ArrayList<String>();

		if(pos.getX()+1 < x_size && (maze3d[pos.getX()+1][pos.getY()][pos.getZ()]==0))
			moves.add("Up");


		if(pos.getX() > 0 && (maze3d[pos.getX()-1][pos.getY()][pos.getZ()]==0))
			moves.add("Down");


		if(pos.getY()+1 <y_size && (maze3d[pos.getX()][pos.getY()+1][pos.getZ()]==0))
			moves.add("Forward");


		if(pos.getY() > 0 && (maze3d[pos.getX()][pos.getY()-1][pos.getZ()]==0))
			moves.add("BackWard");


		if(pos.getZ()+1 <z_size && (maze3d[pos.getX()][pos.getY()][pos.getZ()+1]==0))
			moves.add("Right");


		if(pos.getZ() > 0 && (maze3d[pos.getX()][pos.getY()][pos.getZ()-1]==0))
			moves.add("Left");

		String[] movesArray = moves.toArray(new String[moves.size()]);
		return movesArray;
	}

	//toString
	@Override
	public String toString()
	{
		String s="";
		for (int i=0; i<x_size;i++)
		{
			s+="floor "+i+"\n";

			for (int j=0; j<y_size;j++)
			{
				s+="";
				for (int k=0; k<z_size; k++)
				{
					if (k==z_size-1)
						s+=maze3d[i][j][k];
					else
						s+=maze3d[i][j][k]+" ";
				}
				s+="\n";
			}
			s+="\n";
		}
		return s;
	}

	//cross section by x
	public int [][] getCrossSectionByX (int index) throws IndexOutOfBoundsException
	{
		if((index<0) || (index>=x_size))
			throw new IndexOutOfBoundsException("out of bounds");

		int[][] maze2d=new int[y_size][z_size];
		for(int j=0; j<y_size; j++)
			for(int k=0; k<z_size; k++)
				maze2d[j][k]=maze3d[index][j][k];

		return maze2d;
	}

	//cross section by y
	public int [][] getCrossSectionByY (int index) throws IndexOutOfBoundsException
	{
		if((index<0) || (index>=y_size))
			throw new IndexOutOfBoundsException("out of bounds");

		int[][] maze2d=new int[x_size][z_size];
		for(int i=0;i<x_size;i++)
			for(int k=0;k<x_size;k++)
				maze2d[i][k]=maze3d[i][index][k];

		return maze2d;
	}

	//cross section by z
	public int [][] getCrossSectionByZ (int z) throws IndexOutOfBoundsException
	{
		if((z<0) || (z>=z_size))
			throw new IndexOutOfBoundsException("out of bounds");

		int[][] maze2d=new int[x_size][y_size];
		for(int i=0; i<x_size; i++)
			for(int j=0; j<y_size ;j++)
				maze2d[i][j]=maze3d[i][j][z];

		return maze2d;
	}

	//print cross section
	public void printCrossSection(int[][]maze2d) 
	{
		for (int i=0; i<maze2d.length; i++)
		{
			if (i!=0)
				System.out.println();
			for (int j=0; j<maze2d[i].length; j++)
				System.out.print(maze2d[i][j]);
		}

	}

	//get random start position
	public void GetRandomStartPosition() 
	{ 
		int temp=0;

		do
		{
			temp=rand.nextInt(z_size-2);
		}
		while(temp%2==0);

		startPosition = new Position(0,0,temp+1);
	}   

	//get random goal position
	public void GetRandomGoalPosition() 
	{ 
		int temp=0;

		do
		{
			temp=rand.nextInt(z_size-1);
		}while(temp%2!=0||temp==0);

		goalPosition = new Position(x_size-1,y_size-1,temp);
	}

	//create limits in the maze
	public void createLimits()
	{
		for (int i=0;i<y_size;i++)
		{
			for(int j=0;j<z_size;j++)
			{
				maze3d[i][0][j]=1; //put wall at first row
				maze3d[i][y_size-1][j]=1; //put wall at last row


				maze3d[i][j][0]=1; //put wall at first column
				maze3d[i][j][z_size-1]=1; //put wall at last column


				maze3d[0][i][j]=1; //put wall at first floor
				maze3d[1][i][j]=1; //put wall at second floor
				maze3d[x_size-2][i][j]=1; //put wall at one before last floor
				maze3d[x_size-1][i][j]=1; //put wall at last floor
			}
		}
	}
	


	public byte[] toByteArray()
	{
		int index = 9; //9 is for x,y,z three times
		byte[] b = new byte[x_size * y_size * z_size + 9]; //byte array's size is maze's size + index

		//maze's sizes
		b[0] = (byte) x_size;
		b[1] = (byte) y_size;
		b[2] = (byte) z_size;

		//start position
		b[3] = (byte) getStartPosition().getX();
		b[4] = (byte) getStartPosition().getY();
		b[5] = (byte) getStartPosition().getZ();

		//goal position
		b[6] = (byte) getGoalPosition().getX();
		b[7] = (byte) getGoalPosition().getY();
		b[8] = (byte) getGoalPosition().getZ();

		//maze content
		int i = index;
		for (int x = 0; x < x_size; x++) 
			for (int y = 0; y < y_size; y++) 
				for (int z = 0; z < z_size; z++)
				{
					b[i] = (byte) maze3d[x][y][z];
					i++;
				}
		return b;
	}

	//constructor
	public Maze3d(byte[] b)
	{
		this.x_size = b[0];
		this.y_size = b[1];
		this.z_size = b[2];

		this.maze3d=new int[x_size][y_size][z_size];


		this.startPosition = new Position(b[3],b[4],b[5]);
		this.goalPosition = new Position(b[6],b[7],b[8]);

		int index=9;
		for (int i = 0; i < x_size; i++) 
			for (int j = 0; j < y_size; j++) 
				for (int k = 0; k < z_size; k++)
				{
					this.maze3d[i][j][k]=b[index];
					index++;
				}
	}

	//equals
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) //the same object
			return true;

		if (obj == null) //obj is null
			return false;

		if (getClass() != obj.getClass()) //obj is not instance of Maze3d
			return false;


		Maze3d other = (Maze3d) obj;

		if (!Arrays.deepEquals(maze3d, other.maze3d)) //deep equals return false (checking array)
			return false;
		

		else if (x_size!=other.x_size ||y_size!=other.y_size || z_size!=other.z_size) //sizes are different
			return false;
		
		
		if (goalPosition == null) //our maze has goal position while other maze has not
		{
			if (other.goalPosition != null)
				return false;
		} 
		else if (!goalPosition.equals(other.goalPosition)) //goal positions are different
			return false;
		
		if (startPosition == null) //our maze has start position while other maze has not
		{
			if (other.startPosition != null)
				return false;
		} 
		else if (!startPosition.equals(other.startPosition)) //start positions are different
			return false;

		return true;
	}

	
	//getters&setters
	public int[][][] getMaze3d()
	{
		return maze3d;
	}

	public void setMaze3d(int[][][] maze3d)
	{
		this.maze3d = maze3d;
	}

	public int getX_size()
	{
		return x_size;
	}

	public void setX_size(int x_size)
	{
		this.x_size = x_size;
	}

	public int getY_size()
	{
		return y_size;
	}

	public void setY_size(int y_size)
	{
		this.y_size = y_size;
	}

	public int getZ_size()
	{
		return z_size;
	}

	public void setZ_size(int z_size)
	{
		this.z_size = z_size;
	}
}