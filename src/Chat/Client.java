package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("localhost", 2345);
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		DataInputStream dis = new DataInputStream(client.getInputStream());

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Client: ");
			String msg = scanner.nextLine();
			dos.writeUTF("Client : " + msg);
			dos.flush();

			String str = dis.readUTF();
			System.out.println(str);
			scanner.reset();
		}
	}

}
