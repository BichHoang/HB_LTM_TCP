package Calculator;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Server implements Runnable {
	static Socket socket;
	static ServerSocket server;
	static int port;
	static String input, output;
	static DataOutputStream dos;
	static DataInputStream dis;
	static ScriptEngineManager sem;
	static ScriptEngine engine;
	static boolean isRunning = false;
	static JLabel activeThread;
	static JButton btnS;
	static JTextField portTf;
	static JLabel portLabel;
	private static JLabel lblThcHnhLp;

	public Server() {
		sem = new ScriptEngineManager();
		engine = sem.getEngineByName("JavaScript");
	}

	public static void main(String[] args) throws IOException {
		JFrame frmServer = new JFrame("Server - Hoàng Thị Bích");
		frmServer.setBounds(200, 200, 479, 124);
		frmServer.setResizable(false);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		portLabel = new JLabel("Cổng");
		portLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		portLabel.setBounds(23, 46, 44, 31);
		portTf = new JTextField("1234");
		portTf.setBounds(84, 48, 115, 31);
		activeThread = new JLabel("0");
		activeThread.setBounds(231, 48, 85, 31);
		btnS = new JButton();
		btnS.setBounds(337, 46, 96, 31);
		btnS.setText("Start");
		frmServer.getContentPane().setLayout(null);
		frmServer.getContentPane().add(portLabel);
		frmServer.getContentPane().add(portTf);
		frmServer.getContentPane().add(activeThread);
		frmServer.getContentPane().add(btnS);

		lblThcHnhLp = new JLabel("Thực Hành Lập Trình Mạng - Calculator");
		lblThcHnhLp.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		lblThcHnhLp.setBounds(65, 10, 338, 22);
		frmServer.getContentPane().add(lblThcHnhLp);
		frmServer.setVisible(true);
		Thread thread = new Thread(new Server());

		btnS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (portTf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Bạn chưa nhập Port");
				} else {
					if (isRunning) {
						isRunning = false;
						thread.interrupt();
						try {
							server.close();
							JOptionPane.showMessageDialog(null, "Đã ngừng Server");
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Lỗi khi ngừng Server");
						}
					} else {
						port = Integer.parseInt(portTf.getText());
						try {
							server = new ServerSocket(port);
							JOptionPane.showMessageDialog(null, "Đã khởi động Server");
						} catch (IOException e2) {
							JOptionPane.showMessageDialog(null, "Lỗi khi khởi động Server");
						}
						isRunning = true;
						thread.start();
					}
					portTf.setEditable(true);
					btnS.setText("Start");
					activeThread.setText("Luồng: " + Thread.activeCount());
				}
			}
		});
	}

	@Override
	public void run() {
		while (isRunning) {
			btnS.setText("Stop");
			portTf.setEditable(false);
			activeThread.setText("Luồng: " + Thread.activeCount());
			try {
				socket = server.accept();
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
				input = dis.readUTF();
				output = String.valueOf(engine.eval(input));
				dos.writeUTF(output);
				dos.close();
				dis.close();
				socket.close();
			} catch (ScriptException e) {
				output = "Lỗi cú pháp";
				try {
					dos.writeUTF(output);
				} catch (IOException e1) {
				}
				continue;
			} catch (IOException e) {
				output = "Lỗi kết nối";
				try {
					dos.writeUTF(output);
				} catch (IOException e1) {
				}
				continue;
			}
		}
	}

}
