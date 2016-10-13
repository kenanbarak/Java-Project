package io;

import java.io.IOException;
import java.io.InputStream;

/**
 * decompress information from the input stream source
 * @author Barak Kenan
 */
public class MyDecompressorInputStream extends InputStream
{
	private InputStream in;
	private int prevSign;
	private int count;
	
	/**
	 * constructor
	 * @param in input stream source
	 */
	public MyDecompressorInputStream(InputStream in)
	{
		this.in=in;
		this.prevSign=0;
		this.count=0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int read() throws IOException
	{
		in.read();
		return -1;
	}


	public int read(byte[] arr) throws IOException
	{
		int index=0;
		prevSign=in.read();
		count=in.read();
		
		while(prevSign!=-1 && count!=-1) //while we is it not the end
		{
			for(int i = 0; i<count; i++)
			{
				arr[index] = (byte)prevSign;
				index++;
			}
			
			prevSign=in.read();
			count=in.read();
		}
		
		in.close();

		return 0;
	}
	
	
	/**
	 * getter
	 * @return the input stream source
	 */
	public InputStream getIn() 
	{
		return in;
	}

	
	/**
	 * setter
	 * @param in the new input stream source
	 */
	public void setIn(InputStream in) 
	{
		this.in = in;
	}



}
