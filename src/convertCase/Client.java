package convertCase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private static Socket socket;
	private static Scanner scanner;
	private static DataInputStream dis;
	private static DataOutputStream dos;
	private static String receivedData;
	private static String sendingData;

	public static void main(String[] args) {
		try {
			socket = new Socket("localhost", 9090);
			scanner = new Scanner(System.in);
			while (true) {
				System.out.println("Type Sending Data: ");
				sendingData = scanner.nextLine();

				dos = new DataOutputStream(socket.getOutputStream());
				System.out.println("Client Sending Data: " + sendingData);
				dos.writeUTF(sendingData);

				dis = new DataInputStream(socket.getInputStream());
				receivedData = dis.readUTF();
				System.out.println("Client Received Data: " + receivedData);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
