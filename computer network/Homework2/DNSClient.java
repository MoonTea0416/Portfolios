
import java.net.*;
import java.io.*;

public class DNSClient {
	// the default port
	public static final int PORT = 6052;

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println("Usage: java EchoClient <echo server> <ip name>");
			System.exit(0);
		}

		BufferedReader networkBin = null; // the reader from the network
		PrintWriter networkPout = null; // the writer to the network
		Socket sock = null;

		try {
			sock = new Socket(args[0], PORT);
            
			// set up the necessary communication channels
			networkBin = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			 
			/**
			 * a PrintWriter allows us to use println() with ordinary socket I/O. "true"
			 * indicates automatic flushing of the stream. The stream is flushed with an
			 * invocation of println()
			 */
			networkPout = new PrintWriter(sock.getOutputStream(), true);
            
			//write args[1], which is ipname into stream
			networkPout.println(args[1]);
			//read ip address from stream
			String line = networkBin.readLine();
			 
			System.out.println(line);

		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			//close stream and sock
			if (networkBin != null)
				networkBin.close();
			if (networkPout != null)
				networkPout.close();
			if (sock != null)
				sock.close();
		}
	}
}