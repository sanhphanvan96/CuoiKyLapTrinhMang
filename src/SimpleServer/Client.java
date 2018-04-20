package SimpleServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static Scanner scan;
	private static Socket soc;
	private static String sendData;
	private static String receivedData;

	public static void main(String[] args) {
		try {
			if (args.length == 1) {
				soc = new Socket("localhost", Integer.parseInt(args[0]));
			} else {
				soc = new Socket("localhost", 9090);
			}
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			while (true) {
				scan = new Scanner(System.in);
				System.out.println("Sending data: ");
				sendData = scan.nextLine();

				System.out.println("Sending data: " + sendData);
				dos.writeUTF(sendData);

				DataInputStream dis = new DataInputStream(soc.getInputStream());
				receivedData = dis.readUTF();
				System.out.println("Received data: " + receivedData);
				System.out.println("=======================================================");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}