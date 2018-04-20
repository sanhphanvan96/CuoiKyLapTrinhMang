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

public class MultithreadServer {
	private static Xuly process;
	public long serverStartTime;

	public MultithreadServer(int port) {
		try {
			ServerSocket server = new ServerSocket(port);
			this.serverStartTime = this.getCurrentTime();
			System.out.println("Server is running at port " + server.getLocalPort());
			System.out.println("Server start at: " + this.serverStartTime);
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
		if (args.length == 1) {
			new MultithreadServer(Integer.parseInt(args[0]));
		} else {
			new MultithreadServer(9090);
		}
	}

	private long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public void turnOffServer() {
		System.exit(0);
	}
}

class Xuly extends Thread {
	private MultithreadServer server;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private String receivedData;
	private String sendingData;
	private String msg = null;
	private String data = null;;
	private String[] splitReceivedData;
	private boolean closeServer = false;

	public Xuly(MultithreadServer server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		this.dis = new DataInputStream(socket.getInputStream());
		this.dos = new DataOutputStream(socket.getOutputStream());
	}

	public void run() {
		try {
			while (!closeServer) {
				receivedData = dis.readUTF();
				System.out.println("Thread ID: " + Thread.currentThread().getId());
				System.out.println("Received Data: " + receivedData);

				splitReceivedData();
				handleMessage(msg);
				System.out.println("=======================================================");
			}

		} catch (IOException e) {
			System.out.println("Thread id: " + Thread.currentThread().getId() + " was unconnected!");
			// e.printStackTrace();
		}
	}

	private void handleMessage(String msg) throws IOException {
		if (msg.equals("gettime")) {
			// Nếu MSG=”gettime” thì trả cho client thời gian tính bằng giây
			// từ lúc mở cổng server
			long difference = System.currentTimeMillis() - server.serverStartTime;
			difference = difference / 1000;
			sendingData = "" + difference;
			System.out.println("Client call 'gettime' messsage => " + sendingData);
		} else if (msg.equals("calculate")) {
			// Nếu MSG=”calculate” và DATA là một chuỗi ký tự thì hãy tìm
			// ký tự có tần số xuất hiện lớn nhất và báo về
			// cho client theo đúng định dang: KYTU+TANSO
			sendingData = getMaxOfCharacter(data);
			System.out.println("Client call 'calculate' messsage => " + sendingData);
		} else if (msg.equals("stop")) {
			System.out.println("Server is closed.");
			sendingData = "Server is closed.";
			closeServer = true;
			System.out.println("Client call 'stop' messsage => " + sendingData);
			server.turnOffServer();
		} else {
			sendingData = "Message of request is not correct!";
		}
		System.out.println("Sending Data: " + sendingData);
		dos.writeUTF(sendingData);
		sendingData = null;
	}

	private void splitReceivedData() {
		try {
			this.splitReceivedData = receivedData.split("@@@");
			this.msg = splitReceivedData[0];
			this.data = splitReceivedData[1];
		} catch (Exception e) {
			// e.printStackTrace();
			sendingData = "Message of request is not correct! Must be 'msg@@@data'";
		}
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
				System.out.println(entry.getKey() + ":" + entry.getValue());
				res += entry.getKey() + ":" + entry.getValue() + " ";
			}
		}
		return res;
	}
}
