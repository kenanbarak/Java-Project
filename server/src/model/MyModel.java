package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import presenter.ServerProperties;

/**
 * defines a specific model
 * @author Barak Kenan
 */
public class MyModel extends CommonModel 
{
	private int port;
	private int numOfClients;
	private int clientsCounter;
	private ClientHandler clinetHandler;
	private ExecutorService threadPool;

	private volatile boolean stop;

	private ServerSocket server;
	private Thread mainServerThread;

	private ServerProperties prop;


	/**
	 * constructor
	 */
	public MyModel() 
	{
		this.prop = new ServerProperties();
		this.prop = prop.readPropertiesFromFile("serverProperties.xml");

		try
		{
			this.numOfClients = prop.getNumOfClients();
			this.threadPool = Executors.newFixedThreadPool(this.numOfClients);
			this.port = prop.getPort();
			this.server=new ServerSocket(this.port); //ask OP to control this port
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}


		this.clinetHandler=new MazeSolverClientHandler();
		this.clientsCounter=0;
		this.stop=false;
	}


	/**
	 * {@inheritDoc}
	 */
	public void openTheServer()
	{
		try 
		{
			server.setSoTimeout(10*1000); //the server waits for client 10 seconds (prevent busy waiting)
		}
		catch (SocketException e) 
		{
			setChanged();
			notifyObservers(e.getMessage());
		}

		mainServerThread=new Thread(new Runnable()
		{			
			@Override
			public void run()
			{
				while(!stop)
				{
					try
					{
						final Socket someClient = server.accept(); //wait and listen for some client to connect

						if(someClient!=null) //there is a connected client
						{
							threadPool.execute(new Runnable()
							{									
								@Override
								public void run()
								{
									try
									{	
										//increase counter and display a message about handling this client
										clientsCounter++;
										setChanged();
										notifyObservers("\thandling client #"+clientsCounter);


										//send client's steams to handle his request and display a corresponding message
										clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										someClient.close();
										setChanged();
										notifyObservers("\tdone handling client #"+clientsCounter);
									}
									
									catch(IOException e)
									{
										setChanged();
										notifyObservers(e.getMessage());
									}									
								}
							});								
						}
					}

					catch (SocketTimeoutException e) //10 seconds passed and no client connected
					{
						setChanged();
						notifyObservers("no clinet connected...");
					} 
					catch (IOException e)
					{
						setChanged();
						notifyObservers(e.getMessage());
					}
				}

				setChanged();
				notifyObservers("done accepting new clients.");
			}
		});

		mainServerThread.start();
		setChanged();
		notifyObservers("server is open");
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unused")
	public void closeServer()
	{
		stop=true;
		clinetHandler.close();

		try 
		{
			//orderly shutdown - do not execute jobs in queue, continue to execute running threads
			setChanged();
			notifyObservers("shutting down...");
			threadPool.shutdown();

			//wait 10 seconds over and over again until all running jobs have finished
			boolean allTasksCompleted=false;
			while(!(allTasksCompleted=threadPool.awaitTermination(10, TimeUnit.SECONDS)));
			setChanged();
			notifyObservers("all the tasks have finished");

			//wait for the main server thread to die
			if(mainServerThread!=null)
			{
				mainServerThread.join();		
				setChanged();
				notifyObservers("main server thread is done");
			}

			server.close();
			setChanged();
			notifyObservers("server is safely closed");
		} 
		catch (InterruptedException e) 
		{
			setChanged();
			notifyObservers(e.getMessage());
		}
		catch (IOException e) 
		{
			setChanged();
			notifyObservers(e.getMessage());
		}

		setChanged();
		notifyObservers("server is close");
	}
}
