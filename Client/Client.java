import java.io.*;
import java.net.*;


public class Client {
    private Socket _socket = null;
    private PrintWriter _socketOutput = null;
    private BufferedReader _socketInput = null;

    private void makeConnection() {
        try {
            _socket = new Socket("localhost", 7777);
            _socketOutput = new PrintWriter(_socket.getOutputStream(), true);
            _socketInput = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        } catch (Exception e) {
            System.err.println("Couldn't reach host.");
            System.exit(1);
        }
    }

    private void closeConnection() {
        try {
            _socketOutput.close();
            _socketInput.close();
            _socket.close();
        } catch (IOException e) {
            System.err.println("I/O exception during execution");
            System.exit(1);
        }
    }

    private void startGame(String[] args) {
        try {
            if (args[0].equals("join")) {
                _socketOutput.println("join " + args[1]);
                String response = _socketInput.readLine();
                if (!response.equals("ACCEPT")) {
                    System.err.println(response);
                    System.exit(1);
                }
            } else if (args[0].equals("create")) {
                _socketOutput.println("create " + args[1]);
                String response = _socketInput.readLine();
                if (!response.equals("ACCEPT")) {
                    System.err.println(response);
                    System.exit(1);
                }
            } else {
                System.err.println("Invalid command. Join or create game.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("I/O exception during execution");
            System.exit(1);
        }
    }

    private void runGame() {
        try {
            System.out.println("Game!");
            System.out.println(_socketInput.readLine());
        } catch (IOException e) {
            System.err.println("I/O exception during execution");
            System.exit(1);
        }
    }

    private void run(String[] args) {
        makeConnection();
        startGame(args);
        runGame();
        closeConnection();
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            Client client = new Client();
            client.run(args);
        } else {
            System.err.println("You must either join or create game");
            System.exit(1);
        }
    }
}