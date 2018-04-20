package datetime;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateTimeServer {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(9090);
		System.out.println("Server is running at port " + server.getLocalPort());
		try {
			while (true) {
				Socket soc = server.accept();
				DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
				dos.writeUTF(new Date().toString());
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			server.close();
		}
	}
}
