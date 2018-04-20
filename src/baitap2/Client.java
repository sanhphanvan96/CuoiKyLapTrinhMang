package baitap2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static Scanner scan;
	private static Socket soc;
	private static String sendingData;
	private static String receivedData;
	private static Integer masterServerPort = 9090;
	private static Integer subServerPort = null;
	private DataOutputStream dos;
	private DataInputStream dis;

	public static void main(String[] args) {
		Client client = new Client();
		client.askForSubServerPort(masterServerPort);
		client.connectToSubServer(subServerPort);
	}

	private void askForSubServerPort(Integer port) {
		try {
			soc = new Socket("localhost", port);

			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeUTF("client@@@askSubserverPort");

			dis = new DataInputStream(soc.getInputStream());
			receivedData = dis.readUTF();

			System.out.println("Received data from Master Server: " + receivedData);

			subServerPort = Integer.parseInt(receivedData.split("@@@")[1]);
			System.out.println("SubServer port: " + subServerPort);

			dis.close();
			dos.close();
			soc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void connectToSubServer(Integer port) {
		try {
			soc = new Socket("localhost", port);
			dos = new DataOutputStream(soc.getOutputStream());
			dis = new DataInputStream(soc.getInputStream());
			scan = new Scanner(System.in);
			while (true) {
				System.out.println("Type sending data: ");
				sendingData = scan.nextLine();

				System.out.println("Sending data: " + sendingData);
				dos.writeUTF(sendingData);

				receivedData = dis.readUTF();
				System.out.println("Received data: " + receivedData);
				System.out.println("=======================================================");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}