package baitap2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer {
	private RequestProcessing process;
	public static Integer subserverPort = null;

	public MasterServer(int port) {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Master Server is running at port " + server.getLocalPort());
			// run new subserver
			new SubServerManager().startNewSubServer();
			while (true) {
				process = new RequestProcessing(this, server.accept());
				process.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MasterServer(9090);
	}
}

class RequestProcessing extends Thread {
	private MasterServer server;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private String receivedData;
	private String sendingData;
	private String msg = null;
	private String data = null;;
	private String[] splitReceivedData;
	private boolean closeServer = false;
	private boolean isSubServerRequest = false;

	public RequestProcessing(MasterServer server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		this.dis = new DataInputStream(socket.getInputStream());
		this.dos = new DataOutputStream(socket.getOutputStream());
	}

	public void run() {
		try {
			receivedData = dis.readUTF();
			System.out.println("=======================================================");
			System.out.println("Thread ID: " + Thread.currentThread().getId());
			System.out.println("Received Data: " + receivedData);
			handleReceivedData(receivedData);

			// Neu la request cua subserver: luu lai port, nhan request moi 2 phut,
			// neu khong nhan duoc report sau 2 phut thi chay lai server con
			if (isSubServerRequest) {
				System.out.println("This is subserver request");
				MasterServer.subserverPort = Integer.parseInt(data);
				SubServerManager subserver = new SubServerManager(MasterServer.subserverPort, dis);
				subserver.monitor();
			} else {
				System.out.println("This is client request");
				ClientManager client = new ClientManager(server, socket, dos);
				client.replyPortOfSubServer();
			}
			System.out.println("=======================================================");

		} catch (IOException e) {
			System.out.println("Thread id: " + Thread.currentThread().getId() + " was unconnected!");
		}
	}

	private void handleReceivedData(String receivedData) {
		try {
			// msg@@@data
			this.splitReceivedData = receivedData.split("@@@");
			this.msg = splitReceivedData[0];
			this.data = splitReceivedData[1];
			// System.out.println("handleReceivedData: " + msg + ", " + data);
			isSubServerRequest = this.msg.equals("subserver") ? true : false;
		} catch (Exception e) {
			System.out.println("Message of request is not correct!");
			e.printStackTrace();
		}
	}
}

class SubServerManager {
	private int port;
	private DataInputStream dis;
	private long recentReportTime;

	public SubServerManager(Integer port, DataInputStream dis) {
		this.port = port;
		this.dis = dis;
		this.recentReportTime = System.currentTimeMillis();
	}

	public SubServerManager() {
	}

	public void monitor() {
		while (true) {
			if (!this.isSubserverStillWork()) {
				this.startNewSubServer();
				this.stopCurrentThread();
			}
			try {
				if (dis.available() != 0) {
					System.out.println("Subserver Report: " + dis.readUTF());
					System.out.println("=> Reset recent report time.");
					this.recentReportTime = System.currentTimeMillis();
				}
			} catch (IOException e) {
				System.out.println("Master server cannot received data from SubServer!");
				e.printStackTrace();
			}
		}
	}

	private void stopCurrentThread() {
		System.out.println("Interrupt this thread: " + Thread.currentThread().getId());
		Thread.currentThread().interrupt();
	}

	public void startNewSubServer() {
		System.out.println("=> Starting new subserver!");
		ProcessBuilder processBuilder = new ProcessBuilder("C:\\Users\\Hi-XV\\Desktop\\test.bat");
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			System.out.println("Process output: " + reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isSubserverStillWork() {
		long period = System.currentTimeMillis() - recentReportTime;
		// kiem tra da sau 2 phut chua
		if (period >= 120000) {
			System.out.println("=> After 2 minute but not received report!");
			recentReportTime = System.currentTimeMillis();
			return false;
		}
		return true;
	}
}

class ClientManager {
	private MasterServer server;
	private Socket socket;
	private DataOutputStream dos;

	public ClientManager(MasterServer server, Socket socket, DataOutputStream dos) {
		this.server = server;
		this.socket = socket;
		this.dos = dos;
	}

	public void replyPortOfSubServer() {
		try {
			String sendingData = "subserver@@@" + server.subserverPort;
			System.out.println("Reply to client: " + sendingData);
			System.out.println("Close the socket connected with client");
			dos.writeUTF(sendingData);
			dos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}