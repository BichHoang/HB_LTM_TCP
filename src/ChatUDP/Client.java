package ChatUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws IOException {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		while(true) {
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			Scanner scanner = new Scanner(System.in);
			String msg = "Client: " + scanner.nextLine();
			sendData = msg.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String str = new String(receivePacket.getData());
			System.out.println(str);
			scanner.reset();
		}
	}
}
