package eighteen;

import java.lang.*;
import java.io.*;
import java.net.*;

public class Server {
	public static void main(String args[]) {
		try {
			ServerSocket server = new ServerSocket(1225);
			Socket socket = server.accept();
			System.out.println("Server has connected.");
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			String data = "A 2 3 5 6";
			System.out.print("Sending: " + data);
			out.print(data);
			out.close();
			socket.close();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
