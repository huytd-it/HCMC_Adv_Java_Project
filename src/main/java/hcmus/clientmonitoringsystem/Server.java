package hcmus.clientmonitoringsystem;

import java.io.*;
import java.lang.System.Logger;
import java.net.*;

public class Server {

	private ServerSocket serverSocket;
	private Socket connectedSocket;
	private PrintWriter out;
	private BufferedReader in;
	private InetAddress ip;
	private String port;

	public void startServer(int port) throws IOException {
		try {
			serverSocket = new ServerSocket(port);
		
			do {
				System.out.println("Waiting for a Client");

				connectedSocket = serverSocket.accept(); // synchronous
				InetSocketAddress socketAddress = (InetSocketAddress) connectedSocket.getRemoteSocketAddress();
				String clientIpAddress = socketAddress.getAddress()
						.getHostAddress();
			
				System.out.println("Talking to client");
				ip = InetAddress.getLocalHost();
				System.out.println(ip.getHostAddress());

				InputStream is = connectedSocket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				OutputStream os = connectedSocket.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

				String receivedMessage;

				do {
					receivedMessage = br.readLine();
					System.out.println("Received : " + receivedMessage);
					if (receivedMessage.equalsIgnoreCase("quit")) {
						System.out.println("Client has left !");
						break;
					} else {
						DataInputStream din = new DataInputStream(System.in);
						String k = din.readLine();
						bw.write(k);
						bw.newLine();
						bw.flush();
					}
				} while (true);
				bw.close();
				br.close();
			} while (true);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("There're some error");
		}

	}

	private void closeIO() throws IOException {
		in.close();
		out.close();
	}

	private void stopServer() throws IOException {
		connectedSocket.close();
		serverSocket.close();
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.startServer(5000);
	}
}
