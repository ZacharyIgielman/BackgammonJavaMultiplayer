import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import javax.swing.JOptionPane;
import java.awt.Color;


public class Board extends JPanel {

	private final int HEIGHT = 720;
	private final int WIDTH = 800;
    private final int SPIKE_WIDTH = WIDTH / 14;
	private final int SPIKE_HEIGHT = (int)(0.45f * HEIGHT);
	
	static final long serialVersionUID = 1l;
	
	private int[] state = null;

	public Board() {
		setFocusable(true);
		setBackground(new Color(193,154,107));
	}

	public void updateState(int[] state) {
		this.state = state;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.white);

		for (int i = 0; i < 6; i++ ) {
			g2d.fillPolygon(new int[] {i * SPIKE_WIDTH, (i + 1) * SPIKE_WIDTH, (int)((i + ((float)1 / 2)) * SPIKE_WIDTH)},
							new int[] {0, 0, SPIKE_HEIGHT}, 3);
			g2d.fillPolygon(new int[] {(i + 8) * SPIKE_WIDTH, (i + 9) * SPIKE_WIDTH, (int)((i + ((float)17 / 2)) * SPIKE_WIDTH)},
							new int[] {0, 0, SPIKE_HEIGHT}, 3);
			if (g2d.getColor().equals(Color.black)) {
				g2d.setColor(Color.white);
			} else {
				g2d.setColor(Color.black);
			}
			g2d.fillPolygon(new int[] {i * SPIKE_WIDTH, (i + 1) * SPIKE_WIDTH, (int)((i + ((float)1 / 2)) * SPIKE_WIDTH)},
							new int[] {HEIGHT, HEIGHT, HEIGHT - SPIKE_HEIGHT}, 3);
			g2d.fillPolygon(new int[] {(i + 8) * SPIKE_WIDTH, (i + 9) * SPIKE_WIDTH, (int)((i + ((float)17 / 2)) * SPIKE_WIDTH)},
							new int[] {HEIGHT, HEIGHT, HEIGHT - SPIKE_HEIGHT}, 3);
		}

		int x = (int)(((float)27 / 2) * SPIKE_WIDTH);
		int y = 

		for (int i = 0; i < 24; i++) {

		}
	}
	
	public void gameOver() {
		JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	}
}
