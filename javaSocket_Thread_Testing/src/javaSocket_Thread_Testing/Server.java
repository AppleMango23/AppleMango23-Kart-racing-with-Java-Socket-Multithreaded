//Server
package javaSocket_Thread_Testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private static final int PORT = 5000;
	
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(2);
	
	public static void main(String[] args) throws IOException{
		
		ServerSocket listener = new ServerSocket(PORT);
		
		int player=0;
		
		while(true)
		{
			System.out.println("[Server] is waiting for client connection");
			Socket client = listener.accept();
			player++;
			System.out.println("[Server] Connected to client!");
			
			//This is where the runnable and thread are
			ClientHandler clientThread = new ClientHandler(client,clients,player);
			clients.add(clientThread);
			
			if(player == 2)
				player=0;
			
			pool.execute(clientThread);
		}
	}

}
