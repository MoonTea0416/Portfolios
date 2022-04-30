/*
 * @author Wanjing, Koi
 */
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;

public class ChatClient extends JFrame implements ActionListener, KeyListener {
	private JButton sendButton;
	private JButton exitButton;
	private JTextField sendText;
	private JTextArea displayArea;
	private Socket server;
	private String username;
	public static final int PORT = 63546;
	private static BufferedWriter toServer;

	public ChatClient(Socket server, String username) {
		this.username = username;
		this.server = server;
		try {
			this.toServer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
		/**
		 * a panel used for placing components
		 */
		JPanel p = new JPanel();

		Border etched = BorderFactory.createEtchedBorder();
		Border titled = BorderFactory.createTitledBorder(etched, "Enter Message Here ...");
		p.setBorder(titled);

		/**
		 * set up all the components
		 */
		sendText = new JTextField(30);
		sendButton = new JButton("Send");
		exitButton = new JButton("Exit");

		/**
		 * register the listeners for the different button clicks
		 */
		sendText.addKeyListener(this);
		sendButton.addActionListener(this);
		exitButton.addActionListener(this);

		/**
		 * add the components to the panel
		 */
		p.add(sendText);
		p.add(sendButton);
		p.add(exitButton);

		/**
		 * add the panel to the "south" end of the container
		 */
		getContentPane().add(p, "South");

		/**
		 * add the text area for displaying output. Associate a scrollbar with this text
		 * area. Note we add the scrollpane to the container, not the text area
		 */
		displayArea = new JTextArea(15, 40);
		displayArea.setEditable(false);
		displayArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JScrollPane scrollPane = new JScrollPane(displayArea);
		getContentPane().add(scrollPane, "Center");

		/**
		 * set the title and size of the frame
		 */
		setTitle("GUI Demo");
		pack();

		setVisible(true);
		sendText.requestFocus();

		/** anonymous inner class to handle window closing events */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});

	}

	public void displayMessage(String message) {
		displayArea.append(message + "\n");
	}

	public String getUsername() {
		return this.username;
	}

	/**
	 * This method get the text the user entered and transfer it into protocol
	 * style.
	 */
	public String transferToProtocol(String message) {
		String protocol = null;
		String from = this.username;
		String text = null;
		if (message.charAt(0) == '@') {
			int index = message.indexOf(" ");
			String to = message.substring(1, index);
			text = message.substring(index + 1);
			protocol = "PRIVMSG " + from + " " + to + " " + text + "\r\n";

		} else {
			text = message;
			protocol = "MSG " + from + " " + text + "\r\n";
		}

		return protocol;
	}

	/**
	 * This gets the text the user entered and outputs it in the display area.
	 */
	public void displayText() throws IOException {
		String message = sendText.getText().trim();
		// System.out.println(message);
		toServer.write(transferToProtocol(message));
		toServer.flush();
		StringBuffer buffer = new StringBuffer(message.length());

		for (int i = 0; i < message.length(); i++)
			buffer.append(message.charAt(i));

		displayArea.append(buffer.toString() + "\n");

		sendText.setText("");
		sendText.requestFocus();
	}

	/**
	 * This method responds to action events .... i.e. button clicks and fulfills
	 * the contract of the ActionListener interface.
	 */
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == sendButton)
			try {
				displayText();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1);
			}
		else if (source == exitButton) {
			try {
				toServer.write("EXIT " + username + "\r\n");
				toServer.flush();
				toServer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e);
			}
			System.exit(0);
		}
	}

	/**
	 * These methods responds to keystroke events and fulfills the contract of the
	 * KeyListener interface.
	 */

	/**
	 * This is invoked when the user presses the ENTER key.
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			try {
				displayText();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1);
			}
	}

	/** Not implemented */
	public void keyReleased(KeyEvent e) {
	}

	/** Not implemented */
	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java EchoClient <chat server> <user name>");
			System.exit(0);
		}

		try {
			Socket chatServer = new Socket(args[0], PORT);
			ChatClient win = new ChatClient(chatServer, args[1]);
			win.displayMessage("My name is " + args[1]);
			toServer.write("LOGIN " + args[1] + "\r\n");
			toServer.flush();
			Thread ReaderThread = new Thread(new ReaderThread(chatServer, win));

			ReaderThread.start();
		} catch (UnknownHostException uhe) {
			System.out.println(uhe);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}
}
