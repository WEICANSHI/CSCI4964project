package P2P_mod;

import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author canshi wei
 * Requester aim at sending message to a particular port and socket
 */
public class Requester {
	private String ip; 			// the ip of the client provided
	private int port;				// port number of the intended socket
	
	/**
	 * Constructor: Initialize a requester
	 * @param ip: the ip address 
	 * @param port: the port number
	 */
	public Requester(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * Send message to the dns and port number specify
	 * @param message: the message needs to be sent
	 */
	public void sendMessage(String message) {
		try {
			// create socket and accept connection
			Socket socket = new Socket(ip, port);
			// input stream, output stream define
			InputStream inStream = socket.getInputStream();
			OutputStream outStream = socket.getOutputStream();
			
			// initialize scanner, out put printer
			Scanner in = new Scanner(inStream, "UTF-8");
			//input = new Scanner(System.in);
			PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true);
			out.println(message);	// send the message out
			// close the connection and writer
			socket.close();
			in.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

