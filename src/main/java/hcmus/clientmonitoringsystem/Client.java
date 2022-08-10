package hcmus.clientmonitoringsystem;

import java.io.*;
import java.net.*;

public class Client {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void connect(String ip, int port) throws IOException {

		try {
			Socket s = new Socket(ip, port);
			System.out.println(s.getPort());

			InputStream is = s.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			OutputStream os = s.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

			String sentMessage = "";
			String receivedMessage;

			System.out.println("Talking to Server");

			do {
				DataInputStream din = new DataInputStream(System.in);
				sentMessage = din.readLine();
				bw.write(sentMessage);
				bw.newLine();
				bw.flush();

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
		System.out.println(msg);
		String reply = in.readLine();
		out.println("Replay receive from Server" + reply);
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
