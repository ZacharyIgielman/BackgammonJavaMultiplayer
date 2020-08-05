import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Polygon;
import java.io.*;
import javax.swing.JFrame;


public class Board extends JPanel {
    private PrintWriter _socketOutput = null;
    private BufferedReader _socketInput = null;

	private final int HEIGHT = 720;
	private final int WIDTH = 800;
    private final int SPIKE_WIDTH = WIDTH / 14;
	private final int SPIKE_HEIGHT = (int)(0.45f * HEIGHT);
	
	static final long serialVersionUID = 1l;
	
	private int[] state = null;
	private int player;

	private Shape[] shapes = new Shape[24];

	public Board(BufferedReader in, PrintWriter out) {
		_socketInput = in;
		_socketOutput = out;

		state = new int[] {2,0,0,0,0,-5,0,-3,0,0,0,5,-5,0,0,0,3,0,5,0,0,0,0,-2};

		setFocusable(true);
		setBackground(new Color(193,154,107));

		for (int i = 0; i < 6; i++ ) {
			shapes[5 - i] = new Polygon(new int[] {(i + 8) * SPIKE_WIDTH, (i + 9) * SPIKE_WIDTH, (int)((i + ((float)17 / 2)) * SPIKE_WIDTH)},
											new int[] {HEIGHT, HEIGHT, HEIGHT - SPIKE_HEIGHT}, 3);
			shapes[11 - i] = new Polygon(new int[] {i * SPIKE_WIDTH, (i + 1) * SPIKE_WIDTH, (int)((i + ((float)1 / 2)) * SPIKE_WIDTH)},
											new int[] {HEIGHT, HEIGHT, HEIGHT - SPIKE_HEIGHT}, 3);
			shapes[12 + i] = new Polygon(new int[] {i * SPIKE_WIDTH, (i + 1) * SPIKE_WIDTH, (int)((i + ((float)1 / 2)) * SPIKE_WIDTH)},
											new int[] {0, 0, SPIKE_HEIGHT}, 3);
			shapes[18 + i] = new Polygon(new int[] {(i + 8) * SPIKE_WIDTH, (i + 9) * SPIKE_WIDTH, (int)((i + ((float)17 / 2)) * SPIKE_WIDTH)},
											new int[] {0, 0, SPIKE_HEIGHT}, 3);
		}

		addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
				super.mouseClicked(me);
				int i = 0;
                for (Shape s : shapes) {
                    if (s.contains(me.getPoint())) {
						System.out.println("Pressed " + i);
					}
					i++;
                }
            }
        });
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.white);

		int i = 0;
		for (Shape s : shapes) {
			g2d.fill(s);

			Color previous = g2d.getColor();
			if (state[i] > 0)
			 	g2d.setColor(new Color(233,220,211));
			else
			 	g2d.setColor(new Color(173,134,87));
			for (int j = 0; j < Math.abs(state[i]); j++) {
			 	if (i < 12)
			 		g2d.fillOval((int)s.getBounds2D().getX(), (int)HEIGHT - (int)((1.5 + j) * SPIKE_WIDTH), SPIKE_WIDTH, SPIKE_WIDTH);
			 	else
			 		g2d.fillOval((int)s.getBounds2D().getX(), j * SPIKE_WIDTH, SPIKE_WIDTH, SPIKE_WIDTH);
			}
			g2d.setColor(previous);

			if (g2d.getColor().equals(Color.black)) {
				g2d.setColor(Color.white);
			} else {
				g2d.setColor(Color.black);
			}
			i++;
		}
	}
	
	public void gameOver() {
		JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	}

	public void run() throws Exception {
		player = Integer.parseInt(_socketInput.readLine());
		infoBox("Welcome player " + player);
		JFrame frame = (JFrame)getTopLevelAncestor();
		frame.setTitle("Player " + player);

		while (true) {
			repaint();
			Thread.sleep(10);
		}
	}

	private static void infoBox(String infoMessage) {
        JOptionPane.showMessageDialog(null, infoMessage, "Backgammon", JOptionPane.INFORMATION_MESSAGE);
    }
}
