package ChatUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException {
		DatagramSocket server = new DatagramSocket(9876);
		System.out.println("Server started");
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			server.receive(receivePacket);
			InetAddress ipAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String msg = new String(receivePacket.getData());
			System.out.println(msg);
			Scanner scanner = new Scanner(System.in);
			String s = "Server: " + scanner.nextLine();
			sendData = s.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
			server.send(sendPacket);
			scanner.reset();
		}
	}

}
