package chatroom;

import javax.swing.*;

public class ChatRoom {
	public JFrame frame;
	public JTextArea Room;
	public JTextField msg;
	public JTextArea Joiners;
	public String NickName;

	public ChatRoom(String NickName) {
		this.NickName = NickName;
		this.frame = new JFrame("Chat Room!");
		this.frame.setSize(480, 400);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(null);
		JLabel lr = new JLabel("Chat room! Hello " + this.NickName);
		lr.setBounds(20, 10, 300, 25);
		this.frame.add(lr);
		this.Room = new JTextArea("");
		this.Room.setBounds(20, 50, 300, 250);
		this.Room.setEditable(false);
		this.frame.add(Room);
		JLabel lsd = new JLabel("Send");
		lsd.setBounds(20, 325, 50, 25);
		this.frame.add(lsd);
		this.msg = new JTextField("");
		this.msg.setBounds(100, 325, 200, 25);
		this.frame.add(msg);
		JButton OK = new JButton("Send");
		OK.setBounds(320, 325, 80, 25);
		this.frame.add(OK);
		JLabel lj = new JLabel("Joiners");
		lj.setBounds(620, 10, 50, 50);
		this.frame.add(lj);
		this.Joiners = new JTextArea("");
		this.Joiners.setBounds(330, 50, 120, 250);
		this.Joiners.setEditable(false);
		this.frame.add(Joiners);
		frame.setVisible(true);
	}
}