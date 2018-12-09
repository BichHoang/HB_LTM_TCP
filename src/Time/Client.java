package Time;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("localhost", 1234);
		DataInputStream dis = new DataInputStream(client.getInputStream());
		String time = dis.readUTF();
		System.out.println("Client receive data from serve. Time : " + time);
	}

}
