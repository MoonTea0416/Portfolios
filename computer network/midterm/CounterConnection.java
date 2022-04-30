import java.net.*;
 

public class CounterConnection implements Runnable
{
	private Socket	client;
	private static CounterHandler handler = new CounterHandler();
	
	public CounterConnection(Socket client) {
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