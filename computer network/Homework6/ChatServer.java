/*
 * @author Wanjing, Koi
 */
import java.net.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class ChatServer {
    // initialize default port
	public static final int DEFAULT_PORT = 63546;	
	
	
    public static void main(String[] args) throws IOException {
        ServerSocket sock = null;
		Executor exec = Executors.newCachedThreadPool();
		java.util.Map<String, BufferedOutputStream> users = new HashMap<String,BufferedOutputStream>();
       
        try {
			// establish the socket
			sock = new ServerSocket(DEFAULT_PORT);
			
			while (true) {
				/**
				 * now listen for connections
				 * and service the connection in a separate thread.
				 */
				Runnable task = new Connection(sock.accept(), users);
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
