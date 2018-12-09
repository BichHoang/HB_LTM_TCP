package XuLyChuoi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1111);
		System.out.println("Server is started...");
		while(true) {
			Socket socket = server.accept();
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			
			String st = dis.readUTF(); 
			String msg = "Chuỗi thường: " + st.toLowerCase() + "\nChuỗi hoa: " + st.toUpperCase();
			dos.writeUTF(msg);
			
			dos.writeUTF("" + hvHoaThuong(st));
			
			dos.writeUTF("" + demSoTu(st));
			dos.flush();
		}
	}

	private static int demSoTu(String st) {
		st = st.trim();
		while(st.indexOf("  ") > 0) {
			st = st.replaceAll("  ", " ");
		}
		
		return st.split(" ").length;
	}
	private static String hvHoaThuong(String s) {
		String rs = "";
		char[] c = s.toCharArray();
		for (char d : c) {
			String tmp = "" + d;
			if(tmp.equals(tmp.toLowerCase())) {
				rs += tmp.toUpperCase();
			}else rs += tmp.toLowerCase();
		}
		return rs;
	}

}
