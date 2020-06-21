import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class Server {
	private static final int kThreads = 20;
	private static ServerSocket server = null;
	private static ExecutorService service = null;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(7777);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 7777.");
            System.exit(1);
        }

		service = Executors.newFixedThreadPool(kThreads);
		
		while (true) {
			try {
				Socket client = server.accept();
				service.submit(new ClientHandler(client));
			} catch (IOException e) {
				System.err.println("Problem retrieving client");
				System.exit(1);
			}
		}
    }
}