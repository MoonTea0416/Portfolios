/**
 * A simple proxy server which services each request in a separate thread.
 *
 * @author Wanjing Han
 */

import java.net.*;
import java.util.concurrent.*;
import java.io.*;

public class ProxyServer {
	public static final int PORT = 8080;
	// construct a thread pool for concurrency	
	private static final Executor exec = Executors.newCachedThreadPool();

	public static void main(String args[]) throws IOException {

		ServerSocket serverSock = null;
		 
		try {
			// Create a ServerSocket to listen on that port.
			 
			serverSock = new ServerSocket(PORT);
			while (true) {
				/**
				 * now listen for connections
				 * and service the connection in a separate thread.
				 */
				Runnable task = new ProxyConnection(serverSock.accept());
				exec.execute(task);
			}
		}
		catch (IOException ioe) { System.err.println(ioe); }
		finally {
			if (serverSock != null)
				serverSock.close();
		}
	}
}