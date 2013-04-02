package eighteen;

import java.io.*;
import java.net.*;

public class Client {
	public static void main(String args[]) {
		try {
			Socket socket = new Socket("localhost", 1225);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Received string: ");
			
			while(!in.ready()) {}
			System.out.println(in.readLine());
			
			System.out.println();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
