import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ClientHandler extends Thread {

	private static Map<String, ClientConnection> waitingGames = new HashMap<String, ClientConnection>();
	private ClientConnection client = null;
	private ExecutorService service = null;

	public ClientHandler(Socket socket, ExecutorService service) {
		super("ClientHandler");
		this.service = service;
		try {
			this.client = new ClientConnection(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void create(String gameCode) throws IOException {
		if (waitingGames.get(gameCode) == null) {
			client.out.println("ACCEPT");
			waitingGames.put(gameCode, client);
		} else {
			client.out.println("Game code not available");
			client.close();
		}
	}

	private void join(String gameCode) throws IOException {
		if (waitingGames.get(gameCode) == null) {
			client.out.println("Game code not available");
			client.close();
		} else {
			client.out.println("ACCEPT");
			service.submit(new BackgammonGame(client, waitingGames.remove(gameCode)));
		}
	}

    public void run() {
		try {
			String request[] = client.in.readLine().split("\\s+");
			if (request.length == 2) {
				if (request[0].equals("join")) {
					join(request[1]);
				} else if (request[0].equals("create")) {
					create(request[1]);
				} else {
					client.out.println("Invalid request");
					client.close();
				}
			} else {
				client.out.println("Invalid request");
				client.close();
			}
		} catch (IOException e) {
	    	e.printStackTrace();
		}
    }
}