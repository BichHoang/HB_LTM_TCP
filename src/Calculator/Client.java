package Calculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client {
	static String serverAddress;
	static int serverPort;
	static Socket socket;
	static DataInputStream inStream;
	static DataOutputStream outStream;
	static JFrame frClient, frame;
	JPanel outputPanel, inputPanel;
	static JTextField output, input;

	public static void main(String[] args) {
		frClient = new JFrame("Client - Hoàng Thị Bích");
		frClient.setBounds(200, 200, 489, 275);
		frClient.setResizable(false);
		frClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frClient.getContentPane().setLayout(null);
		JLabel lblThcHnhLp = new JLabel("Thực Hành Lập Trình Mạng - Calculator");
		lblThcHnhLp.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		lblThcHnhLp.setBounds(94, 13, 342, 21);
		frClient.getContentPane().add(lblThcHnhLp);
		JTextField host, port;
		host = new JTextField("localhost");
		host.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		host.setBounds(94, 59, 74, 26);
		port = new JTextField("1234");
		port.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		port.setBounds(267, 59, 52, 26);
		frClient.getContentPane().add(host);
		frClient.getContentPane().add(port);
		JButton enterBtn = new JButton("Enter");
		enterBtn.setBounds(356, 58, 80, 27);
		frClient.getContentPane().add(enterBtn);

		JLabel lblaCh = new JLabel("Địa chỉ:");
		lblaCh.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblaCh.setBounds(10, 60, 69, 23);
		frClient.getContentPane().add(lblaCh);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblPort.setBounds(198, 57, 45, 28);
		frClient.getContentPane().add(lblPort);

		JLabel lblClientConnected = new JLabel();
		lblClientConnected.setForeground(Color.RED);
		lblClientConnected.setBackground(Color.WHITE);
		lblClientConnected.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		lblClientConnected.setBounds(82, 94, 331, 26);
		frClient.getContentPane().add(lblClientConnected);
		lblClientConnected.hide();

		JLabel lblBiuThcCn = new JLabel("Biểu thức cần tính: ");
		lblBiuThcCn.setEnabled(false);
		lblBiuThcCn.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblBiuThcCn.setBounds(10, 130, 141, 26);
		frClient.getContentPane().add(lblBiuThcCn);

		JLabel lblKQ = new JLabel("Kết quả Server trả về:");
		lblKQ.setEnabled(false);
		lblKQ.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblKQ.setBounds(10, 183, 249, 26);
		frClient.getContentPane().add(lblKQ);

		input = new JTextField();
		input.setEnabled(false);
		input.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		input.setBounds(161, 129, 220, 27);
		frClient.getContentPane().add(input);
		input.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String expresion = input.getText();
				try {
					socket = new Socket(serverAddress, serverPort);
					socket.setSoTimeout(1000);
					inStream = new DataInputStream(socket.getInputStream());
					outStream = new DataOutputStream(socket.getOutputStream());
					outStream.writeUTF(expresion);
					String result = inStream.readUTF();
					lblKQ.setText("Kết quả Server trả về: " + result);
					inStream.close();
					outStream.close();
					socket.close();
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "Không tìm thấy Server.");
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Lỗi kết nối vào ra khi truyền dữ liệu.");
					e.printStackTrace();
				}
			}
		});
		btnSend.setEnabled(false);
		btnSend.setBounds(391, 129, 74, 27);
		frClient.getContentPane().add(btnSend);
		frClient.setVisible(true);
		enterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				serverAddress = host.getText();
				serverPort = Integer.parseInt(port.getText());
				lblClientConnected.setVisible(true);
				lblClientConnected.setText("Client connected! - " + serverAddress + ": " + serverPort);
				lblBiuThcCn.setEnabled(true);
				input.setEnabled(true);
				lblKQ.setEnabled(true);
				btnSend.setEnabled(true);
				new Client();
			}
		});
	}
}