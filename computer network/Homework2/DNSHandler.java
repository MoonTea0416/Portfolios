
/**
 * Handler class containing the logic for echoing results back
 * to the client. 
 *
 * @author Greg Gagne 
 */

import java.io.*;
import java.net.*;

public class DNSHandler {
	public static final int PORT = 6052;

	/**
	 * this method is invoked by a separate thread
	 */
	public void process(Socket client) throws java.io.IOException {

		BufferedInputStream fromClient = null;
		BufferedOutputStream toClient = null;
		InetAddress hostAddress = null;
		BufferedReader networkBin = null;
		String ipname = null;
		String ipAddress = null;
		try {
			/**
			 * get the input and output streams associated with the socket.
			 */
			fromClient = new BufferedInputStream(client.getInputStream());
			networkBin = new BufferedReader(new InputStreamReader(fromClient));
			toClient = new BufferedOutputStream(client.getOutputStream());
			ipname = networkBin.readLine();// transfer input stream into String

			hostAddress = InetAddress.getByName(ipname); // get ip address

			ipAddress = hostAddress.getHostAddress();// transfer ip address into String type

			toClient.write((ipAddress + "\r\n").getBytes());
			// "flushing" the stream writes the contents of the data to the network.
			toClient.flush();

		} catch (UnknownHostException uhe) {
			toClient.write(("Unknown host: " + ipname + "\r\n").getBytes()); // return to client "Unknown host" when
			System.err.println("Unknown host: " + ipname); // ipname is unrecognized
			toClient.flush(); // "flushing" the stream writes the contents of the data to the network.
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			// close streams
			if (fromClient != null)
				fromClient.close();
			if (toClient != null)
				toClient.close();

		}
	}
}
