package convertCase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static Xuly process;

	public Server() {
		try {
			ServerSocket server = new ServerSocket(9090);
			System.out.println("Server is running at port " + server.getLocalPort());
			while (true) {
				process = new Xuly(this, server.accept());
				process.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Server();
	}

}

class Xuly extends Thread {
	private Server server;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private String receivedData;
	private String sendingData;

	public Xuly(Server server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	}

	public void run() {
		try {
			while (true) {
				receivedData = dis.readUTF();
				System.out.println("Thread ID: " + Thread.currentThread().getId());
				System.out.println("Received Data: " + receivedData);

				sendingData = receivedData.toUpperCase();
				System.out.println("Sending Data: " + sendingData);
				dos.writeUTF(sendingData);
			}

		} catch (IOException e) {
			System.out.println("Thread id: " + Thread.currentThread().getId() + " was unconnected!");
			// e.printStackTrace();
		}

	}
}
