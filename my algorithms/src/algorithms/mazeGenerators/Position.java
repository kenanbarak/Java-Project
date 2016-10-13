package algorithms.mazeGenerators;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Position implements Serializable
{
	private int x,y,z;

	//default constructor
	public Position() 
	{
		this.x=0;
		this.y=0;
		this.z=0;
	}

	//constructor
	public Position(int x, int y, int z) 
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}

	//copy constructor
	public Position (Position p)
	{
		this.x=p.getX();
		this.y=p.getY();
		this.z=p.getZ();
	}

	//getters & setters
	public void setPosition(int x, int y, int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}

	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() 
	{
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}

	public int getZ() 
	{
		return z;
	}

	public void setZ(int z) 
	{
		this.z = z;
	}

	//to string
	@Override
	public String toString()
	{
		return "{"+x+","+y+","+z+"}";
	}

	//equals
	@Override
	public boolean equals(Object obj)
	{
		if((obj instanceof Position)==false)
			return false;

		Position p=(Position)obj;
		if (this.x==p.getX() && this.y==p.getY() && this.z==p.getZ())
			return true;

		return false;
	}

	@Override
	public int hashCode() 
	{	
		return this.toString().hashCode();
	}

}
