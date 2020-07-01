import java.io.*;
import java.net.*;

import javax.swing.JFrame;


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
            //System.out.println(_socketInput.readLine());
            
            JFrame frame = new JFrame("Backgammon");
            Board board = new Board();
		    frame.add(board);
		    frame.setSize(800, 720);
		    frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            int[] state = new int[] {2,0,0,0,0,-5,0,-3,0,0,0,5,5,0,0,0,-3,0,-5,0,0,0,0,2};
            
            while (true) {
                board.updateState(state);
                board.repaint();
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run(String[] args) {
        //makeConnection();
        //startGame(args);
        runGame();
        //closeConnection();
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