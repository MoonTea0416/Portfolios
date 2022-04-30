
/**
 * This thread is passed a socket that it reads from. Whenever it gets input
 * it writes it to the ChatScreen text area using the displayMessage() method.
 * 
 * @author Wanjing, Koi
 *
 */

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.regex.*;

public class ReaderThread implements Runnable {
	Socket server;
	BufferedReader fromServer;
	ChatClient client;

	public ReaderThread(Socket server, ChatClient client) {
		this.server = server;
		this.client = client;
	}

	public void run() {
		try {
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));

			while (true) {
				String message = fromServer.readLine();
				String pattern = ".*STATUS <0>.*";
				if (Pattern.matches(pattern, message)) {
					System.exit(0);
				} else {
					// now display it on the display area
					client.displayMessage(message);
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe);

		}
	}
}