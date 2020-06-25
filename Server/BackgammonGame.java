import java.io.*;

public class BackgammonGame extends Thread {

    private ClientConnection player1;

    private ClientConnection player2;

    public BackgammonGame(ClientConnection player1, ClientConnection player2) throws IOException {
        super("BackgammonGame");
        this.player1 = player1;
        this.player2 = player2;
    }

    public void run() {
        player1.out.println("Hello Player 1");
        player2.out.println("Hello Player 2");
        try {
            player1.close();
            player2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}