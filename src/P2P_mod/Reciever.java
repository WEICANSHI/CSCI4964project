package P2P_mod;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import UI.UI;


/**
 * @author canshi wei
 * Receiver: Each node own its own server for receiving incoming message. 
 * Receiver get the message sent and parse out and notify Logger to save the data .
 */

public class Reciever implements Runnable{
	private int port;	// the port number of the Receiver
	private Socket incoming; // incoming socket
	private ServerSocket server; // server socket
	private UI ref;
	
	/**
	 * Constructor: initialize the receiver and start to listen using one thread
	 * @param port
	 * @param logger
	 */
	public Reciever(int port, UI ref) {
		this.ref = ref;
		this.port = port;		// port number
		
	}
	
	/**
	 * Run the server as running other hardware in the system
	 * Whenever a new connection is made, start a new channel
	 * for listening the message using 'TaskManager'
	 */
	@Override
	public void run() {
		try {
			server = new ServerSocket(this.port);	// start a new socket for listening
			System.out.println("Reciver start to listening...");
			// open the listening for all time
			while(true) {
				// create socket and accept connection
				incoming = server.accept(); // incoming socket accept
				// Task manager could assign a channel to the commnunication
				new Thread(new Task(incoming)).start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Task Manager: assign a commnication channel to the incoming socket.
	 */
	private class Task implements Runnable{
		private Socket socket;	// check the incoming socket

		/**
		 * Constructor: Initialize a Runnable task manager
		 * @param socket: incoming socket
		 * @param logger: the reference to logger
		 */
		public Task(Socket socket) {
			this.socket = socket;
		}
		
		/**
		 * Start a channel for listening the incoming socket
		 */
		@Override
		public void run() {
			try {
				// input stream, output stream define
				InputStream inStream = socket.getInputStream();
				OutputStream outStream = socket.getOutputStream();
				// init scanner, out put printer
				Scanner in = new Scanner(inStream, "UTF-8");
				PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true);
				// waiting connection and stay in connection			
				while(in.hasNextLine()) {
					this.event(in.nextLine());	
				}
				
				// close the resources
				in.close();
				out.close();
				socket.close();
				inStream.close();
				outStream.close();
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/**
		 * Parse and send the data to the logger within the hardware
		 * @param output: the message received
		 */
		public void event(String output) {
			ref.control.setWarning(output);
		}
	}
}
