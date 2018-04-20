package convertCase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Scanner;

public class ConverCaseClient {

	private static Scanner scan;
	private static Socket soc;
	private static String sendData;
	private static String receivedData;

	public static void main(String[] args) {
		try {
			soc = new Socket("localhost", 9090);
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());

			scan = new Scanner(System.in);
			System.out.println("Sending data: ");
			sendData = scan.nextLine();

			System.out.println("Sending data: " + sendData);
			dos.writeUTF(sendData);

			DataInputStream dis = new DataInputStream(soc.getInputStream());
			receivedData = dis.readUTF();
			System.out.println("Received data: " + receivedData);

			String[] arrReceivedData = receivedData.split("\\|");
			System.out.println("Uppercase: " + arrReceivedData[0]);
			System.out.println("Lowercase: " + arrReceivedData[1]);
			System.out.println("Number of words: " + arrReceivedData[2]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
