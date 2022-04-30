/**
 * This is the separate thread that services each
 * incoming echo client request.
 *
 * @author Wanjing Han
 */
import java.net.*;
public class ProxyConnection implements Runnable{
	private Socket client;
	private static ProxyHandler handler = new ProxyHandler();
	
	public ProxyConnection(Socket client) {
		this.client = client;
	}
    
	/**
     * This method runs in a separate thread.
     */	
	public void run() { 
		try {
			handler.process(client);
		}
		catch (java.io.IOException ioe) {
			System.err.println(ioe);
		}
	}

}
