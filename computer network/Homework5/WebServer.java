/**
 * A simple server which services each request in a separate thread.
 *
 * @author Wanjing Han
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WebServer {
	public static final int PORT = 8080;
	// construct a thread pool for concurrency
	private static final Executor exec = Executors.newCachedThreadPool();

	public static void main(String args[]) throws IOException {

		ServerSocket serverSock = null;
		String location = args[0];
		try {
			// Create a ServerSocket to listen on that port.
			Configuration configurator = new Configuration(location);
			serverSock = new ServerSocket(PORT);
			while (true) {
				/**
				 * now listen for connections and service the connection in a separate thread.
				 */
				Runnable task = new ServerConnection(serverSock.accept(), configurator);
				exec.execute(task);
			}
		} catch (ConfigurationException ce) {
			System.out.println(ce);
			System.exit(0);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			if (serverSock != null)
				serverSock.close();
		}
	}
}
