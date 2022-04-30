/**
 * An echo server listening on port 6007. 
 * This server reads from the client
 * and echoes back the result. 
 *
 * This is a single-threaded server that can only serve one client
 * at a time.
 *
 * This conforms to RFC 862 for echo servers.
 *
 * @author - Greg Gagne.
 */

import java.net.*;
import java.io.*;

public class  EchoServerST 
{
	public static final int DEFAULT_PORT = 6007;
	
	public static void main(String[] args) throws IOException {
		ServerSocket sock = null;
		Handler handler = new Handler();
		
		try {
			// establish the socket
			sock = new ServerSocket(DEFAULT_PORT);
			
			while (true) {
				/**
				 * A single-threaded server ....
				 */
				Socket client = sock.accept();
				System.out.println("We have a connection");
				handler.process(client);
			}
		}
		catch (IOException ioe) { }
		finally {
			if (sock != null)
				sock.close();
		}
	}
}
