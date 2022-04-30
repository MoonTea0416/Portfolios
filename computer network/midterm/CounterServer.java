import java.net.*;
import java.util.concurrent.*;
import java.io.*;

public class CounterServer {

	public static final int PORT = 10000;
	 
	// construct a thread pool for concurrency	
		private static final Executor exec = Executors.newCachedThreadPool();
		
		public static void main(String[] args) throws IOException {
			ServerSocket sock = null;
			
			try {
				// establish the socket
				sock = new ServerSocket(PORT);
				
				while (true) {
					/**
					 * now listen for connections
					 * and service the connection in a separate thread.
					 */
					Runnable task = new CounterConnection(sock.accept());
					exec.execute(task);
				}
			}
			catch (IOException ioe) { System.err.println(ioe); }
			finally {
				if (sock != null)
					sock.close();
			}
		}
	}
