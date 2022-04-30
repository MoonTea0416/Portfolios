
/**
 * Handler class containing the logic for sending how many words back
 * to the client. 
 *
 * @author Wanjing Han
 */

import java.io.*;
import java.net.*;

public class CounterHandler {
	public static final int PORT = 10000;

	/**
	 * this method is invoked by a separate thread
	 */
	public void process(Socket client) throws java.io.IOException {

		BufferedReader fromClient = null;
		OutputStream toClient = null;
		 
		String line = null;
		 
		try {
			/**
			 * get the input and output streams associated with the socket.
			 */
			fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			 
			toClient = new BufferedOutputStream(client.getOutputStream());
			line = fromClient.readLine();// transfer input stream into String

			String[] delims = line.split("\\s+");
			int counter = 0;
			for(int i = 0;i<delims.length;i++) {
				for(int k=0;k<delims[i].length();k++) {
					counter ++;
				}
			}

			toClient.write((counter + "\r\n").getBytes());
			// "flushing" the stream writes the contents of the data to the network.
			toClient.flush();

		}  catch (IOException ioe) {
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
