package chatroom;

// call by telnet: o localhost 9999
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatRoomServer {
	private static String message = "";
	private static ServerSocket svr;

	public static void main(String[] args) {
		try {
			svr = new ServerSocket(9999);
			System.out.println("Chatroom server starting...");
			final ArrayList<Socket> listSoc = new ArrayList<Socket>();
			while (true) {
				final Socket soc = svr.accept();
				listSoc.add(soc);
				System.out.println("Number of client is " + listSoc.size());
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Scanner in = new Scanner(soc.getInputStream());
							while (in.hasNextLine()) {
								message = in.nextLine();
								for (Socket s : listSoc) {
									if (s == null)
										listSoc.remove(s);
									else if (!s.equals(soc)) {
										PrintWriter out = new PrintWriter(s.getOutputStream(), true);
										out.println(Thread.currentThread().getId() + ": " + message);
									}
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
