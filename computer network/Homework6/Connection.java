/*
 * @author Wanjing, Koi
 */
import java.net.*;
import java.util.*;
import java.io.*;

public class Connection implements Runnable {
	
	private Socket	client;
	private Map<String,BufferedOutputStream> users;
 
    public Connection(Socket client, Map<String,BufferedOutputStream>  users){
		this.client = client;
		this.users = users;
	}
    
    /**
     * This method runs in a separate thread.
     */	
	public void run() { 
        BufferedReader fromClient = null;
		BufferedOutputStream toClient = null;
		String clientRequest;
				
		try {
			fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			toClient = new BufferedOutputStream(client.getOutputStream());
			
			while ((clientRequest = fromClient.readLine()) != null) {
			String[] parsedInfo = clientRequest.split(" ");
			int parsedInfoLen = parsedInfo.length;
			String fromUser = parsedInfo[1];
			String toUser;
			String message = "";
			String finalMessage;
			String exitMessage;

			Collection<BufferedOutputStream> connections = users.values();
			Iterator<BufferedOutputStream> itr = connections.iterator();
			BufferedOutputStream myClient;
				
			if (parsedInfo[0].equals("LOGIN")){

				String status1 = "STATUS <1>. " + fromUser + " has successfully logged in.\r\n";
				String status0 = "STATUS <0>. " + fromUser + " has unsuccessfully tried to log in.\r\n";
				
				if (users.get(fromUser) != null){
						toClient.write(status0.getBytes());
						toClient.flush();
				}
				else {
				while (itr.hasNext()) {
					myClient = itr.next();

					myClient.write(status1.getBytes());
					myClient.flush();
				}
				users.put(fromUser, toClient);
			}
			}
			else if (parsedInfo[0].equals("MSG")) {		
				for (int i = 2; i < parsedInfoLen; i++){
					message = message + parsedInfo[i] + " ";
				}
				message.trim();
				finalMessage = "MSG From " + fromUser + " - " + message + "\r\n";
					
				while (itr.hasNext()) {
					myClient = itr.next();
				
					myClient.write(finalMessage.getBytes());
					myClient.flush();
				}

			}
		    else if (parsedInfo[0].equals("PRIVMSG")) {
				toUser = parsedInfo[2];
				for (int i = 3; i < parsedInfoLen; i++){
					message = message + parsedInfo[i] + " ";
				}

				finalMessage = "PRIVMSG From " + fromUser + " To " + toUser + " - " +  message + "\r\n";

				myClient = users.get(toUser);

				myClient.write(finalMessage.getBytes());
				myClient.flush();
				
			}
			else if (parsedInfo[0].equals("EXIT")){	
				exitMessage = "EXIT " + fromUser + " has exited the chat" + "\r\n";
				
				while (itr.hasNext()) {
					myClient = itr.next();
				
					myClient.write(exitMessage.getBytes());
					myClient.flush();
				}
				users.remove(fromUser);
			}
			}
		}
		catch (java.io.IOException ioe) {
			System.err.println(ioe);
		}
	}
}
