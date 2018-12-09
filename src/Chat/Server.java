package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(2345);
		System.out.println("Server is started...");
		Socket socket = server.accept();
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		Scanner scanner = new Scanner(System.in);

		while (true) {
			String str = dis.readUTF();
			System.out.println(str);
			System.out.print("Server: ");
			String msg = scanner.nextLine();
			dos.writeUTF("Server: " + msg);
			dos.flush();
			scanner.reset();
		}

	}

}
