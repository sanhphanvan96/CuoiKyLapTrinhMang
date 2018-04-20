package chatroom;

import javax.swing.*;

public class LoginFrame {
	public JFrame frame;

	public LoginFrame(String ms) {
		frame = new JFrame("Login");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		JLabel lname = new JLabel("Name");
		lname.setBounds(50, 50, 50, 50);
		frame.add(lname);
		final JTextField Name = new JTextField("");
		Name.setBounds(120, 50, 200, 50);
		frame.add(Name);
		final JLabel msg = new JLabel("Msg:" + ms);
		msg.setBounds(120, 200, 200, 50);
		frame.add(msg);
		JButton OK = new JButton("Login");
		OK.setBounds(120, 120, 200, 50);
		frame.add(OK);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new LoginFrame("");
	}
}
