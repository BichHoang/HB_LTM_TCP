package ChatFrame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JTextField userTf;

	public Client(String title) {
		super(title);
		setTitle("Chat Room - Login");
		JPanel userPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JLabel userLb = new JLabel("Username:");
		userLb.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		userLb.setBounds(10, 19, 90, 20);
		userTf = new JTextField(30);
		userTf.setBounds(110, 19, 243, 20);
		JButton loginBtn = new JButton("Login");
		loginBtn.setBounds(50, 10, 90, 20);
		JButton resetBtn = new JButton("Reset");
		resetBtn.setBounds(150, 10, 90, 20);
		JButton exitBtn = new JButton("Exit");
		exitBtn.setBounds(250, 10, 90, 20);
		loginBtn.addActionListener(this);
		resetBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		userPanel.setLayout(null);
		userPanel.add(userLb);
		userPanel.add(userTf);
		buttonPanel.setLayout(null);
		buttonPanel.add(loginBtn);
		buttonPanel.add(resetBtn);
		buttonPanel.add(exitBtn);
		getContentPane().add(userPanel);
		getContentPane().add(buttonPanel);
		
		JLabel lblSvthHongTh = new JLabel("SVTH: Hoàng Thị Bích");
		lblSvthHongTh.setFont(new Font("Times New Roman", Font.ITALIC, 11));
		lblSvthHongTh.setBounds(250, 40, 116, 13);
		buttonPanel.add(lblSvthHongTh);
		setBounds(200, 200, 390, 150);
		getContentPane().setLayout(new GridLayout(2, 1));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String button = e.getActionCommand();
		if (button.equals("Login")) {
			String user = userTf.getText();
			int port = 1234;
			new ChatRoom(user, port);
			dispose();
		}
		if (button.equals("Reset")) {
			userTf.setText("");
		}
		if (button.equals("Exit")) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new Client("Đăng nhập");
	}

	class ChatRoom extends JFrame implements Runnable {
		private static final long serialVersionUID = 1L;
		JTextArea messageTa, allMessagesTa;
		JPanel titlePanel, messagePanel, inputPanel;
		JLabel titleLb;
		JButton sendBtn;
		JList<String> userList = new JList<String>();
		String user;
		int port;
		private Socket socket;
		private DataInputStream inStream;
		private DataOutputStream outStream;

		public ChatRoom(String user, int port) {
			super("Chat Room TCP - Hoàng Bích");
			this.user = user;
			this.port = port;
			this.initGUI();
			try {
				System.out.println("localhost" + " " + port);
				socket = new Socket("localhost", port);
				inStream = new DataInputStream(socket.getInputStream());
				outStream = new DataOutputStream(socket.getOutputStream());
				outStream.writeUTF(this.user);
				new Thread(this).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void initGUI() {
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			getContentPane().setLayout(new BorderLayout());
			titlePanel = new JPanel();
			titleLb = new JLabel("Client >> " + user);
			titlePanel.add(titleLb);
			messagePanel = new JPanel();
			messagePanel.setLayout(new BorderLayout());
			allMessagesTa = new JTextArea();
			allMessagesTa.setWrapStyleWord(true);
			allMessagesTa.setLineWrap(true);
			allMessagesTa.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(allMessagesTa);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			messagePanel.add(scrollPane);
			inputPanel = new JPanel();
			inputPanel.setLayout(new BorderLayout());
			messageTa = new JTextArea(2, 35);
			messageTa.setWrapStyleWord(true);
			messageTa.setLineWrap(true);
			JScrollPane spMessage = new JScrollPane(messageTa);
			inputPanel.add(spMessage);
			sendBtn = new JButton("Send");
			sendBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					try {
						outStream.writeUTF(messageTa.getText());
						messageTa.setText("");
					} catch (IOException e) {
					}
				}
			});
			inputPanel.add(sendBtn, BorderLayout.EAST);
			add(titlePanel, BorderLayout.NORTH);
			add(messagePanel, BorderLayout.CENTER);
			add(inputPanel, BorderLayout.SOUTH);
			setSize(400, 300);
			setLocation(400, 100);
			setVisible(true);
		}

		@Override
		public void run() {
			try {
				while (true) {
					String message = inStream.readUTF();
					allMessagesTa.append(message + "\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}



