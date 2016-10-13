package view;

import java.util.Scanner;


/**
 * defines a specific view - CLI
 * @author Barak Kenan
 */
public class CLI_View extends CommonView
{	

//	/**
//	 * {@inheritDoc}
//	 */
//	public void start() 
//	{
//		System.out.println("----- Server -----\nPossible commands are:");
//		System.out.println("open the server");
//		System.out.println("close the server");
//		String line ="";
//
//
//		Scanner scanner = new Scanner(System.in);
//		new Thread(new Runnable() 
//		{
//			public void run() 
//			{		
//				while(!canBeClosed)
//				{
//					line = scanner.nextLine();
//					setChanged();
//					notifyObservers(line);
//				}
//				scanner.close();
//			}
//		}).start();	
//	}

	@Override
	public void start()
	{	
		System.out.println("----- Server -----\nPossible commands are:");
		System.out.println("open the server");
		System.out.println("close the server");
		String line ="";
		Scanner scanner = new Scanner(System.in);
		do
		{
			line = scanner.nextLine();
			this.setChanged();
			this.notifyObservers(line);

		} while (!(line.equals("close the server")));
		scanner.close();
	}


	/**
	 * {@inheritDoc}
	 */
	public void viewDisplayMessage(String message)
	{
		System.out.println(message);
	}
}
