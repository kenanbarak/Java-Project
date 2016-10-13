package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.RandomSelectionPop;

/**
 * @author Barak Kenan
 */
public class Run
{

	public static void main(String[] args) throws IOException
	{
		//generate maze
		Maze3d maze = new GrowingTreeGenerator(new RandomSelectionPop()).generate(3, 3, 3);
		
		//save the maze
		OutputStream out=new MyCompressorOutputStream(new FileOutputStream("1.maz"));
		out.write(maze.toByteArray());
		out.flush();
		out.close();
		
		//load the maze
		InputStream in=new MyDecompressorInputStream(new FileInputStream("1.maz"));
		byte b[]=new byte[maze.toByteArray().length];
		in.read(b);
		in.close();
		Maze3d loaded=new Maze3d(b);
		
		//check if the mazes are equals
		System.out.println(loaded.equals(maze));
	}
}
