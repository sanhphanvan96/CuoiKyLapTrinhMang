package datetime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DateTimeClient {

	public static void main(String[] args) {
		try {
			Socket soc = new Socket("localhost", 9090);
			DataInputStream dis = new DataInputStream(soc.getInputStream());
			String str = dis.readUTF();
			System.out.println("Received data: " + str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
