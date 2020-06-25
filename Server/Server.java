import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class Server {
	private static ServerSocket server = null;
	private static ExecutorService service = null;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(7777);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 7777.");
            System.exit(1);
        }

		service = Executors.newCachedThreadPool();
		
		while (true) {
			try {
				Socket client = server.accept();
				service.submit(new ClientHandler(client, service));
			} catch (IOException e) {
				System.err.println("Problem retrieving client");
				System.exit(1);
			}
		}
    }
}