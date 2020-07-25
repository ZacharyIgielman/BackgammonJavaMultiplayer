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
			drawSpike(g2d, new int[] {i * SPIKE_WIDTH, (i + 1) * SPIKE_WIDTH, (int)((i + ((float)1 / 2)) * SPIKE_WIDTH)},
						new int[] {0, 0, SPIKE_HEIGHT}, false, 12 + i);
			drawSpike(g2d, new int[] {(i + 8) * SPIKE_WIDTH, (i + 9) * SPIKE_WIDTH, (int)((i + ((float)17 / 2)) * SPIKE_WIDTH)},
						new int[] {0, 0, SPIKE_HEIGHT}, false, 18 + i);
			if (g2d.getColor().equals(Color.black)) {
				g2d.setColor(Color.white);
			} else {
				g2d.setColor(Color.black);
			}
			drawSpike(g2d, new int[] {i * SPIKE_WIDTH, (i + 1) * SPIKE_WIDTH, (int)((i + ((float)1 / 2)) * SPIKE_WIDTH)},
						new int[] {HEIGHT, HEIGHT, HEIGHT - SPIKE_HEIGHT}, true, 11 - i);
			drawSpike(g2d, new int[] {(i + 8) * SPIKE_WIDTH, (i + 9) * SPIKE_WIDTH, (int)((i + ((float)17 / 2)) * SPIKE_WIDTH)},
						new int[] {HEIGHT, HEIGHT, HEIGHT - SPIKE_HEIGHT}, true, 5 - i);
		}
	}

	private void drawSpike(Graphics2D g2d, int[] x, int[] y, boolean up, int number) {
		g2d.fillPolygon(x, y, 3);
		Color previous = g2d.getColor();
		if (state[number] > 0)
			g2d.setColor(new Color(233,220,211));
		else
			g2d.setColor(new Color(173,134,87));
		
		for (int i = 0; i < Math.abs(state[number]); i++) {
			if (up)
				g2d.fillOval(x[0], (int)HEIGHT - (int)((1.5 + i) * SPIKE_WIDTH), SPIKE_WIDTH, SPIKE_WIDTH);
			else
				g2d.fillOval(x[0], i * SPIKE_WIDTH, SPIKE_WIDTH, SPIKE_WIDTH);
		}
		g2d.setColor(previous);

	}
	
	public void gameOver() {
		JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	}
}
