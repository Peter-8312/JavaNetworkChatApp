package ie.atu.sw;

import java.io.*;
import java.net.*;

public class ConnectionWrapper {
	public Socket socket;
    public BufferedReader input;
    public PrintWriter output;
    
    // Simple utility class so keeping public
    public ConnectionWrapper(Socket socket, BufferedReader input, PrintWriter output) {
        this.socket = socket;
        this.input = input;
        this.output = output;
    }

}
