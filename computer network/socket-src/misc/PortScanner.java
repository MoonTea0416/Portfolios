/**
 * A simple port scanner.
 *
 * @author Greg Gagne.
 */

import java.net.*;

public class PortScanner
{
	public static final int PORT_MAX = 1024;
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java PortScanner <host>");
			System.exit(0);
		}
		
		// make sure the host is valid
		InetAddress host = null;
		try {
			host = InetAddress.getByName(args[0]);
		}
		catch (UnknownHostException uhe) {
			System.err.println("Invalid Host " + args[0]);
			System.exit(0);
		}
		
		for (int i = 1; i <= PORT_MAX; i++) {
			try {
				Socket sock = new Socket(host,i);

				// if no exception is thrown, the server is listening to the given port
				System.out.println("Listening at port: " + i);
			}
			catch (java.net.ConnectException ce) {
				/**
				 * no port here
				 * java.net.Socket throws this exception
				 * if the connection is refused
				 */
			}
			catch (java.io.IOException ioe) {
				// no port here
			}
		}
	}
}
