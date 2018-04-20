package convertCase;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConvertCaseServer {
	private static Socket soc;
	private static String receivedData;
	private static String sendData;

	public static void main(String[] args) throws IOException {
		System.out.println(args[0]);
		ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
		System.out.println("Server is running at port " + server.getLocalPort());
		try {
			while (true) {
				soc = server.accept();
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				receivedData = dis.readUTF();

				DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
				// send data back to client, data string format:
				// upperCaseString|lowerCaseString|numberOfWord
				sendData = receivedData.toUpperCase() + "|" + receivedData.toLowerCase() + "|"
						+ numberOfWord(receivedData);

				dos.writeUTF(sendData);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			server.close();
		}

	}

	private static int numberOfWord(String str) {
		return str.split("\\s+").length;
	}

}
