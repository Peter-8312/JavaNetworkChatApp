package ie.atu.sw;

import java.io.*;
import java.net.*;

public class ChatServer {
	public static void main(String[] args) {
		final int SERVER_PORT = 12345; // Server port number

		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
			System.out.println("Server is listening on port " + SERVER_PORT);

			while (true) {
				Socket clientSocket = serverSocket.accept(); // Accept a single client
				System.out.println("Client connected");

				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

				ConnectionWrapper clientConnection = new ConnectionWrapper(clientSocket, input, output);

				// Start the send thread to allow server-side user input
				new Thread(() -> sendToClient(clientConnection)).start();

				// Handle the client connection in a separate thread
				new Thread(() -> handleClient(clientConnection)).start();
			}
		} catch (IOException e) {
			System.out.println("Error in server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void handleClient(ConnectionWrapper clientConnection) {
		try {
			String message;
			while ((message = clientConnection.input.readLine()) != null) {
				System.out.println("Client: " + message);
				clientConnection.output.println("Server received: " + message);

				if (message.equals("\\q")) {
					System.out.println("Client has disconnected.");
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Connection with client lost: " + e.getMessage());
		} finally {
			try {
				if (clientConnection.socket != null)
					clientConnection.socket.close();
			} catch (IOException e) {
				System.out.println("Error closing client connection: " + e.getMessage());
			}
		}
	}

	private static void sendToClient(ConnectionWrapper clientConnection) {
		try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
			String message;
			while ((message = console.readLine()) != null) {
				clientConnection.output.println(message); // Send message to client
				if (message.equals("\\q")) {
					System.out.println("Exiting chat...");
					clientConnection.socket.close(); // Gracefully close connection
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Error sending message to client: " + e.getMessage());
		}
	}
}
