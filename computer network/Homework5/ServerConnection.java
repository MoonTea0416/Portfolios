/**
 * Connection class containing the logic for parsing date to the client 
 *
 * @author Wanjing Han
 */

import java.io.*;
import java.net.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class ServerConnection implements Runnable {
	/**
	 * this method is invoked by a separate thread
	 */
	private Socket client;
	private Configuration configutor;
	public ServerConnection(Socket client,Configuration configutor) {
		this.client = client;
		this.configutor=configutor;
	}
	
	public String getType(String fileName) {
		String result = null;
		fileName=fileName.trim();
		int index = fileName.indexOf(".");
		String type = fileName.substring(index + 1);
		if(type.equals("html")) {
			result ="text/html";
		}else if(type.equals("gif")) {
			result="image/gif";
		}else if(type.equals("jpeg")) {
			result="image/jpeg";
		}else if(type.equals("png")) {
			result="image/png";
		}else{
			result="text/plain";
		}
		return result;
	}
	public void process(Socket client,Configuration configutor) throws IOException {
		final int CHUNK = 1024;
		BufferedReader fromClient = null;
		BufferedOutputStream toClient = null;
		InputStream inFile = null;
	    

		String line = null;
		File file=null;
		String status = null;
        Long length = null;
        String type = null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat greenwichDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
       
		try {
			/**
			 * get the input and output streams associated with the socket.
			 */
			fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));

			toClient = new BufferedOutputStream(client.getOutputStream());

			 

			line = fromClient.readLine();// transfer input stream into String
			
			line = line.substring(0, line.indexOf("HTTP/1.1"));
			System.out.println(line);

			int index = line.indexOf("/");
			line = line.substring(index + 1);
			String pathName = line.replace("/", "\\\\");
			
			if (pathName.equals(" ")) {
				file = new File(configutor.getDefaultDocument());	 
				status="HTTP/1.1 200 OK";
				length=file.length();
				type="text/html";
			} else {
				String filePath = configutor.getDocumentRoot()+pathName;
				file = new File(filePath);
				 
				if(!file.exists()) {
					file=new File(configutor.getFourOhFourDocument());
					status="HTTP/1.1 404 Not Found";
					length=file.length();
					type="text/html";
				}else {
					status="HTTP/1.1 200 OK";
					length=file.length();
					// determine the file type
					type=getType(file.getName());
					System.out.println(file.getName());
					System.out.println(type);
					 
				}
				
			}
			 
			
			//send header to client
			toClient.write((status+"\r\n").getBytes());
			toClient.write(("Content-Type: "+type+"\r\n").getBytes());
			toClient.write(("Date: "+greenwichDate.format(cal.getTime())+"\r\n").getBytes());
			toClient.write(("Server: "+configutor.getServerName()+"\r\n").getBytes());
			toClient.write(("Content-Length: "+length+"\r\n").getBytes());
			toClient.write(("Connection: close"+"\r\n\r\n").getBytes());
			
			inFile = new BufferedInputStream(new FileInputStream(file));
			byte[] bytesTwo = new byte[CHUNK];
			int bytesRead = 0;

			while ( (bytesRead = inFile.read(bytesTwo)) > 0) {
				toClient.write(bytesTwo,0,bytesRead);
			}
			
			

			 

			
			// "flushing" the stream writes the contents of the data to the network.
			toClient.flush();

		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		finally {
			// close streams
			if (fromClient != null)
				fromClient.close();
			if (toClient != null)
				toClient.close();
			if (inFile != null)
				inFile.close();

		}
	}
	
	public void run() { 
		try {
			process(client,configutor);
		}
		catch (java.io.IOException ioe) {
			System.err.println(ioe);
		}
	}
}
