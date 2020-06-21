import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler extends Thread {
	private static Map<String,Socket> waitingGames = new HashMap<String,Socket>();

	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

    public ClientHandler(Socket socket) {
		super("ClientHandler");
		this.socket = socket;
	}

	private void close() throws IOException {
		out.close();
	    in.close();
		socket.close();
	}

	private void create(String gameCode) throws IOException {
		if (waitingGames.get(gameCode) == null) {
			out.println("ACCEPT");
			waitingGames.put(gameCode, socket);
			out.close();
	    	in.close();
		} else {
			out.println("Game code not available");
			socket.close();
		}
	}

	private void join(String gameCode) throws IOException {
		if (waitingGames.get(gameCode) == null) {
			out.println("Game code not available");
			close();
		} else {
			out.println("ACCEPT");
			out.close();
	    	in.close();
			BackgammonGame game = new BackgammonGame(socket, waitingGames.get(gameCode));
			game.play();
		}
	}

    public void run() {
		try {
	    	out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String request[] = in.readLine().split("\\s+");

			if (request.length == 2) {
				if (request[0].equals("join")) {
					join(request[1]);
				} else if (request[0].equals("create")) {
					create(request[1]);
				} else {
					out.println("Invalid request");
					close();
				}
			} else {
				out.println("Invalid request");
				close();
			}
		} catch (IOException e) {
	    	e.printStackTrace();
		}
    }
}