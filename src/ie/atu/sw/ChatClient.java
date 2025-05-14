package ie.atu.sw;

import java.io.*;
import java.net.*;


public class ChatClient {
	 public static void main(String[] args) {
	        final String SERVER_IP = "127.0.0.1"; // Server IP hard-coded for simplicity and testing.
	        final int SERVER_PORT = 12345;       // Server port
	        
	        /* Note: Options for command-line arguments or a configuration file are possible
	         *  but not used for simplicity in this project
	         */
	        
	      
	       ConnectionWrapper connection = null;
	        
	        // Attempt connection to server
	        while (connection == null) {
	        	try { 
	        		Socket socket = new Socket(SERVER_IP, SERVER_PORT);
	        		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
	        		connection = new ConnectionWrapper(socket, input, output);
	        		System.out.println("Connected to the server at " + SERVER_IP + ":" + SERVER_PORT);
	        	} catch (IOException e) {
	        		System.out.println("Unable to connect to server. Retrying in 5 seconds...");
	        		try {
	        			Thread.sleep(5000); // Wait 5 seconds before retry
	        		} catch (InterruptedException ex) {
	        			Thread.currentThread().interrupt();
	        		}
	        	}
	        }
	        
	        ConnectionWrapper finalConnection = connection;
	            
	            // Start threads for communication
	            Thread sendThread = new Thread(() -> {
	                try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
	                    String message;
	                    while ((message = console.readLine()) != null) {
	                        finalConnection.output.println(message);
	                        if (message.equals("\\q")) {
	                        	System.out.println("Exiting chat...");
	                        	break;
	                        }
	                    }
	                } catch (IOException e) {
	                    System.out.println("Error reading from console: " + e.getMessage());
	                }
	            });

	            Thread receiveThread = new Thread(() -> {
	                try {
	                    String message;
	                    while ((message = finalConnection.input.readLine()) != null) {
	                        System.out.println("Server: " + message);
	                        if (message.equals("\\q")) {
	                        	System.out.println("Server has ended chat.");
	                        	break;
	                        }
	                    }
	                } catch (IOException e) {
	                    System.out.println("Connection to server was lost.");
	                    try {
	                    Socket reconnectedSocket = handleReconnection(SERVER_IP, SERVER_PORT);
	                    finalConnection.socket = reconnectedSocket;
	                    finalConnection.input = new BufferedReader(new InputStreamReader(reconnectedSocket.getInputStream()));
	                    finalConnection.output = new PrintWriter(reconnectedSocket.getOutputStream(), true);
	                   } catch (IOException ex) {
	                	   System.out.println("Failed to reconnect: " + ex.getMessage());
	                	   System.exit(0);
	                   }
	                }
	            });

	            sendThread.start();
	            receiveThread.start();
	            
	            // wait for threads to finish
	            try {
	            	sendThread.join();
		            receiveThread.join();
	            } catch (InterruptedException e) {
	            	Thread.currentThread().interrupt();
	            } finally {
	            	try {
	            		if (connection.socket != null) connection.socket.close();
	            		System.out.println("Chat session ended");
	            	} catch (IOException e) {
	            		System.out.println("Error closing socket: " + e.getMessage());
	            	}
	            }
	        }
	        private static Socket handleReconnection(String serverIp, int serverPort) throws IOException {
	        	int retries = 0;
	            final int maxRetries = 5;
	            

	            while (retries < maxRetries) {
	                try {
	                    System.out.println("Attempting to reconnect... (Attempt " + (retries + 1) + "/" + maxRetries + ")");
	                    Socket socket = new Socket(serverIp, serverPort);
	                    System.out.println("Reconnected to the server.");
	                    return socket; // Return the reconnected socket
	                } catch (IOException e) {
	                    retries++;
	                    System.out.println("Reconnection attempt failed. Retrying in 5 seconds...");
	                    try {
	                        Thread.sleep(5000);
	                    } catch (InterruptedException ex) {
	                        Thread.currentThread().interrupt();
	                    }
	                }
	            }

	            throw new IOException("Failed to reconnect after " + maxRetries + " attempts.");
	        }
	    }
	            
