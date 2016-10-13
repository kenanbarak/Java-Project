package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;

/**
 * defines the CLI
 * @author Barak Kenan
 */

public class CLI
{
	BufferedReader in;
	PrintWriter out;
	HashMap<String, Command> commands = new HashMap<String,Command>();
	
	/**
	 * constructor that initialize the CLI with in and out
	 * @param in input source
	 * @param put output source
	 */
	public CLI(BufferedReader in, PrintWriter out) 
	{
		this.in=in;
		this.out=out;
	}
	
	/**
	 * getter
	 * @return the input stream
	 */
	public BufferedReader getIn() 
	{
		return in;
	}

	/**
	 * setter
	 * @param in the new input stream
	 */
	public void setIn(BufferedReader in) 
	{
		this.in = in;
	}

	/**
	 * getter
	 * @return the output stream
	 */
	public PrintWriter getOut() 
	{
		return out;
	}

	/**
	 * setter
	 * @param out the new output stream
	 */
	public void setOut(PrintWriter out) 
	{
		this.out = out;
	}

	/**
	 * getter
	 * @return the commands hash map
	 */
	public HashMap<String, Command> getCommands()
	{
		return commands;
	}

	/**
	 * setter
	 * @param commands the new commands hash map
	 */
	public void setCommands(HashMap<String, Command> commands) 
	{
		this.commands = commands;
	}


	/**
	 * start the CLI
	 */
	
	public void start ()
	{
		Thread thread=new Thread(new Runnable() 
		{		
			@Override
			public void run() 
			{
				String line="";
				String[]args=null;
				do
				{
					try
					{
						line=in.readLine();
						args=line.split("-");
						if(commands.containsKey(args[0]))
						{
							Command command=commands.get(args[0]);
							String[]args1=new String[args.length-1];
							for (int i=0; i<args.length-1;i++)
								args1[i]=args[i+1];
							command.doCommand(args1); //doCommand gets just the parameters
						}
						else
						{
							out.write("invalid command\n");
							out.flush();
						}
					}
					catch(IOException e)
					{
						System.out.println("read input from cli failed");
					}
				}while(!line.equalsIgnoreCase("exit"));
				
			}
		});
		thread.start();
	}
}
