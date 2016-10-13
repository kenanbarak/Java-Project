package io;

import java.io.IOException;
import java.io.OutputStream;


/**
 * compress information and write it to the output stream source
 * @author Barak Kenan
 */
public class MyCompressorOutputStream extends OutputStream
{
	private OutputStream out;
	private int count;
	private int prevSign;

	/**
	 * constructor
	 * @param out output stream source
	 */
	public MyCompressorOutputStream(OutputStream out)
	{
		this.out=out;
		this.count=0;
		this.prevSign=0;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(int b) throws IOException
	{

		if(count==0) //the first time we are writing
		{
			prevSign=b;
			count++;
		}

		else if(b==prevSign) //if it's the same sign, we count it
		{
			count++;
		}

		else
		{
			while(count>255) //if there are more than 255 times the same sign, we will restart the counter (modulo)
			{
				out.write(prevSign);
				out.write(255);
				count-=255;
			}
			
			//there is a new sign - so write the previous with it's counter and start again to count
			out.write(prevSign);
			out.write(count);
			prevSign=b;
			count=1;
		}

	}
	
	public void write(byte[] arr)throws IOException
	{	
		super.write(arr);
		out.write(prevSign); //writing the last sign
		out.write(count); //writing it's counter
		out.close();
	}


	/**
	 * getter
	 * @return the output stream source
	 */
	public OutputStream getOut()
	{
		return out;
	}

	
	/**
	 * setter
	 * @param out the new output stream source
	 */
	public void setOut(OutputStream out)
	{
		this.out = out;
	}
}
