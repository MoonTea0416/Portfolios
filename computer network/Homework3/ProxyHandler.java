/**
 * Handler class containing the logic for parsing date between client and server
 *
 * @author Wanjing Han
 */
import java.io.*;
import java.net.*;

public class ProxyHandler {

	public final int CHUNK = 1024;
    //this method will return path name, if the url didn't request any specific item, it will return null
	public String getPathName(String url) {
		String result = url;
		for (int i = 0; i < 2; i++) {
			
			int index = result.indexOf("/");
            if(index==-1) {
            	result=null;
            	break;
            }
			result = result.substring(index + 1);

		}
		return result;
	}

	/**
	 * this method is invoked by a separate thread
	 */
	public void process(Socket client) throws IOException {

		InputStream fromServer = null;// the reader from the real server
		OutputStream toServer = null; // the writer to the real server
		BufferedReader fromClient = null; // the reader from the client
		OutputStream toClient = null;// the writer to the client
		Socket proxySock = null;

		try {

			// Now enter an infinite loop, waiting for connections and handling them.

			// Get input and output streams to talk to the client and server from the socket
			fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			toClient = new BufferedOutputStream(client.getOutputStream());

			// Now, read the HTTP request from the client, for example
			// http://localhost:8080/cs.westminstercollege.edu/~greg/fall2020/352/handouts/hw3/index.html
			// http://localhost:8080/www.amazon.com
			String line = fromClient.readLine();
			line = line.substring(0, line.indexOf("HTTP/1.1"));// get rid of HTTP/1.1
			System.out.println(line);
			String[] message = line.split("/");
			String hostName = message[1];
			System.out.println(hostName);
			String pathName = getPathName(line);
			System.out.println(pathName);
            // Connect to real server
			proxySock = new Socket(hostName.trim(), 80);
			fromServer = new BufferedInputStream(proxySock.getInputStream());
			 
			toServer = new BufferedOutputStream(proxySock.getOutputStream());
            //Set HTTP request to the real server
			if(pathName==null) {
				toServer.write(("GET / HTTP/1.1" + "\r\n").getBytes());
			}
			else {
				toServer.write(("GET /" + pathName.trim() + " HTTP/1.1" + "\r\n").getBytes());
			}
			 
			toServer.write(("Host: " + hostName + "\r\n").getBytes());
			toServer.write(("Connection: close" + "\r\n\r\n").getBytes());
			toServer.flush();

			// read the response from the original server
			byte[] bytesTwo = new byte[CHUNK];
			int bytesRead = 0;
            // send the response to the client
			while ((bytesRead = fromServer.read(bytesTwo)) > 0) {
				toClient.write(bytesTwo, 0, bytesRead);
				toClient.flush();
			}
		}
		// If anything goes wrong, print an error message
		catch (Exception e) {
			System.err.println(e);
		} finally {
			// close streams and socket
			if (fromClient != null)
				fromClient.close();
			if (toClient != null)
				toClient.close();
			if (fromServer != null)
				fromServer.close();
			if (toServer != null)
				toServer.close();
			if (proxySock != null)
				proxySock.close();
		}
	}
	
}