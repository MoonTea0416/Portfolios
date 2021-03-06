/**
 * HttpHeader.java 
 * This program returns the header output from a web server.
 *
 * Usage: java HttpHeader <web server> [document name] 
 *
 * @author Greg Gagne, Ocotber 2011.
 */

import java.io.*;
import java.net.*;

public class HttpHeader  { 
	public static void main(String args[]) throws java.io.IOException {
		if (args.length < 1) {
			System.err.println("Usage: java HttpHeader <web server> [document name]");
			System.exit(0);
		}
		
		Socket sock = null;
		BufferedReader in = null;
		PrintWriter out = null;
		String requestedDocument = "";
		
		if (args.length == 2)
			requestedDocument = args[1];
		
		
		try {
			// connect to port 80 (HTTP)
			sock = new Socket(args[0],80);
			
			// get the input and output streams
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out =  new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			
			// make a request for the document specified in args[1]
			// notice that we provide the equialent of TWO enters. 
			//String message = "GET /" + requestedDocument + " HTTP/1.0 \r\n\r\n";
			String message = "GET /" + requestedDocument + " HTTP/1.1\r\n" + "Host: " + args[0] + "\r\n\r\n";
			out.print(message);                             
			out.flush();
			
			// now read what the web server responds with. 
			// This may involve multiple lines.
			String line;
			while((line = in.readLine()) != null) {
				if (line.length() == 0) 
					break;
				System.out.println(line);
			}
		}
		// If anything goes wrong, print an error message
		catch (Exception e) {
			System.err.println(e);
		}
		finally {   // close all streams
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (sock != null)
				sock.close();
		}
	}
}