package XuLyChuoi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("localhost", 1111);
		DataInputStream dis = new DataInputStream(client.getInputStream());
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Nhập chuỗi cần biến đổi: ");
		String msg = scanner.nextLine();
		dos.writeUTF(msg);
		dos.flush();
		
		String rs = dis.readUTF();
		System.out.println("Chuỗi sau khi biến đổi: \n" + rs);
		System.out.println("Chuỗi đổi hoa thành thường, thường thành hoa: " + dis.readUTF());
		System.out.println("Số ký tự của chuỗi : " + dis.readUTF());
		scanner.reset();
	}
}
