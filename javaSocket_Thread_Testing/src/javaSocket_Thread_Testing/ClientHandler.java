//ClientHandler
package javaSocket_Thread_Testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<ClientHandler> clients;
	private int playerCounter=0;
	
	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients, int player) throws IOException
	{
		this.playerCounter = player;
		this.client = clientSocket;
		this.clients = clients;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(),true);
	}
	
	@Override
	public void run() {
		int counter=0;
		// TODO Auto-generated method stub
		try {
			while(true)
			{
				counter++;
				if(counter == 1)
				{
					out.println("Player " + playerCounter);
				}
				else {
					out.println("null");
				}

				String request = in.readLine();
				
				if(request.startsWith("say")){
					int firstSpace = request.indexOf(" ");
					if(firstSpace != -1)
					{
						outToAll(request.substring(firstSpace+1));
					}
				}
				
				
			}
		} catch (IOException e) {
			System.out.print(e);
		}
		
		finally{
			out.close();
			try {
				in.close();
			} catch (IOException e)
			{
				System.out.print(e);
			}
			
		}
		
	}


	private void outToAll(String msg) {
		// TODO Auto-generated method stub
		for(ClientHandler aClient:clients)
		{
			aClient.out.println(msg);
		}
	}
	

}
