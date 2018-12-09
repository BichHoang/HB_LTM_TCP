package Time;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1234);
		System.out.println("Server is started...");
		while(true) {
			Socket socket = server.accept();
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			String time = new Date().toString();
			dos.writeUTF(time);
			socket.close();
		}
	}
}
