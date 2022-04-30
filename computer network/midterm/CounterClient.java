/* Client class
The client will write this phrase (as a String) to the socket
then read and then output the response from the server
*/
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class CounterClient {
	// the default port
	public static final int PORT = 10000;

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Usage: java CounterClient <counter server>");
			System.exit(0);
		}

		BufferedReader fromServer = null; // the reader from the network
		OutputStream toServer = null; // the writer to the network
		Socket sock = null;

		try {
			sock = new Socket(args[0], PORT);
            
			// set up the necessary communication channels
			fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			toServer = new BufferedOutputStream(sock.getOutputStream());
            
			Scanner s1 = new Scanner(System.in);
			System.out.println("Please enter a phrase");
			String phrase = s1.nextLine();
			
			toServer.write((phrase + "\r\n").getBytes());
			toServer.flush();

			//read result from stream
			String line = fromServer.readLine();
			 
			System.out.println("There are "+line+ " words in total");

		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			//close stream and sock
			if (fromServer != null)
				fromServer.close();
			if (toServer != null)
				toServer.close();
			if (sock != null)
				sock.close();
		}
	}
}
