import java.net.*;
import java.io.*;

public class BackgammonGame {

    private Socket player1;
	private PrintWriter out1 = null;
    private BufferedReader in1 = null;
    
    private Socket player2;
	private PrintWriter out2 = null;
	private BufferedReader in2 = null;

    public BackgammonGame(Socket player1, Socket player2) throws IOException {
        this.player1 = player1;
        out1 = new PrintWriter(this.player1.getOutputStream(), true);
		in1 = new BufferedReader(new InputStreamReader(this.player1.getInputStream()));
        this.player2 = player2;
        out2 = new PrintWriter(this.player2.getOutputStream(), true);
		in2 = new BufferedReader(new InputStreamReader(this.player2.getInputStream()));
    }
    
    public void play() {
        out1.println("Hello Player 1");
        out2.println("Hello Player 2");
    }
}