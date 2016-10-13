package model;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * defines a client handler
 * @author Barak Kenan
 */
public interface ClientHandler 
{
	/**
	 * handle client request 
	 * (if the request can be solved by the server, I will send this request to the corresponding method)
	 * @param inFromClient input from a client to the server
	 * @param outToClient output to a client from the server
	 */
	void handleClient(InputStream inFromClient, OutputStream outToClient);
	
	/**
	 * close the connection with the client
	 */
	public void close();
}
