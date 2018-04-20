package SimpleServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Server {
	private static Socket soc;
	private static String receivedData;
	private static String sendData;
	private long serverStartTime;
	private static String msg;
	private static String data;

	public static void main(String[] args) throws IOException {
		System.out.println(System.currentTimeMillis());
		Server s = new Server();
		ServerSocket server = new ServerSocket(9090);
		s.serverStartTime = s.getCurrentTime();
		System.out.println("ServerStartTime: " + s.serverStartTime);
		System.out.println("Server is running at port " + server.getLocalPort());
		try {
			while (true) {
				soc = server.accept();
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				receivedData = dis.readUTF();

				//
				System.out.println("receivedData: " + receivedData);
				String[] arr = receivedData.split("@@@");
				msg = arr[0];
				data = arr[1];

				//
				if (msg.equals("gettime")) {
					// Nếu MSG=”gettime” thì trả cho client thời gian tính bằng giây
					// từ lúc mở cổng server
					long difference = s.getCurrentTime() - s.serverStartTime;
					System.out.println("difference: " + difference);
					sendData = "" + difference;
				} else if (msg.equals("calculate")) {
					// Nếu MSG=”calculate” và DATA là một chuỗi ký tự thì hãy tìm
					// ký tự có tần số xuất hiện lớn nhất và báo về
					// cho client theo đúng định dang: KYTU+TANSO
					sendData = s.getMaxOfCharacter(data);
				} else if (msg.equals("stop")) {
					System.out.println("Server is closed.");
					sendData = "Server is closed.";
					server.close();
					break;
				}

				DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
				// send data back to client, data string format:
				// upperCaseString|lowerCaseString|numberOfWord

				dos.writeUTF(sendData);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			server.close();
		}

	}

	private long getCurrentTime() {
		return System.currentTimeMillis();
	}

	private String getMaxOfCharacter(String str) {
		String res = "";
		int len = str.length();
		Map<Character, Integer> numChars = new HashMap<Character, Integer>(Math.min(len, 26));

		for (int i = 0; i < len; ++i) {
			char charAt = str.charAt(i);

			if (!numChars.containsKey(charAt)) {
				numChars.put(charAt, 1);
			} else {
				numChars.put(charAt, numChars.get(charAt) + 1);
			}
		}

		System.out.println(numChars);
		int maxValue = Collections.max(numChars.values());
		for (Entry<Character, Integer> entry : numChars.entrySet()) {
			if (entry.getValue() == maxValue) {
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
				res = entry.getKey() + ": " + entry.getValue();
			}
		}
		return res;
	}
}
