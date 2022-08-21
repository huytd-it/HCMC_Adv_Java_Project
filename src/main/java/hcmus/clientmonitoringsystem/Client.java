package hcmus.clientmonitoringsystem;

import java.io.*;
import java.net.*;

public class Client {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private BufferedWriter bw;
	private BufferedReader br;
	private String sentMessage = "";
	private String receivedMessage;

	public void connect(String ip, int port) throws IOException {

		try {
			this.clientSocket = new Socket(ip, port);
			System.out.println(clientSocket.getPort());
			System.out.println("Talking to Server");

			do {
				DataInputStream din = new DataInputStream(System.in);
				sentMessage = din.readLine();
				send(sentMessage);
				if (sentMessage.equalsIgnoreCase("quit"))
					break;
				else {
					receivedMessage = br.readLine();
					System.out.println("Received : " + receivedMessage);
				}

			} while (true);

			bw.close();
			br.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("There're some error");
		}

	}

	public void send(String msg) throws IOException {
		InputStream is = clientSocket.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));

		OutputStream os = clientSocket.getOutputStream();
		bw = new BufferedWriter(new OutputStreamWriter(os));

		bw.write(sentMessage);
		bw.newLine();
		bw.flush();
	}

	public void disconnect() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	public static void main(String args[]) throws IOException {
		Client client = new Client();

		client.connect("localhost", 5000);

	}
}
