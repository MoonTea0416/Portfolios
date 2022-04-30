/**
 * The client-side of the date server
 *
 * @author Greg Gagne 
 */

import java.net.*;
import java.io.*;

public class DateClient
{
	// the default port
	public static final int PORT = 6013;
	
	public static void main(String[] args) throws java.io.IOException {
		if (args.length != 1) {
			System.err.println("usage: java DateClient <host>");
			System.exit(0);
		}

		BufferedReader fromServer = null;
		Socket server = null;
		
		try {
			// create socket and connect to default port 
			server = new Socket(args[0], PORT);
			
			// "Readers" are used for reading text characters
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));

			String line;

			while ( (line = fromServer.readLine()) != null)
				System.out.println(line);
		} catch (java.io.IOException ioe) {
			System.err.println(ioe);
		}
		finally {
			// let's close streams and sockets
			// closing the input stream closes the socket as well
			if (fromServer!= null)
				fromServer.close();
			if (server != null)
				server.close();
		}
	}
}
