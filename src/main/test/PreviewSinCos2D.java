import javax.swing.*;
import java.awt.*;

public class PreviewSinCos2D extends JComponent implements Runnable {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Preview");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.add(new PreviewSinCos2D(), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	private int angle;

	private PreviewSinCos2D() {
		setSize(256, 256);
		setPreferredSize(getSize());
		setMinimumSize(getSize());
		setMaximumSize(getSize());
	}

	@Override
	public void addNotify() {
		super.addNotify();
		Thread t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(20,40,60));
		g.fillRect(0, 0, getWidth(), getHeight());

		int sin = Draw3D.sin[angle];
		int cos = Draw3D.cos[angle];

		g.setColor(Color.WHITE);
		g.drawString(String.format("angle: %d", angle), 8, 16);
		g.drawString(String.format("sin: %d", sin), 8, 16+15);
		g.drawString(String.format("cos: %d", cos), 8, 16+15+15);

		int midX = 128;
		int midY = 128;

		g.setColor(Color.LIGHT_GRAY);

		final int radius = 64;

		g.drawOval(midX - radius, midY - radius, 2 * radius, 2 * radius);
		g.drawLine(midX, 32, midX, 256 - 32); // vertical line
		g.drawLine(32, midY, 256 - 32, midY); // horizontal line

		g.drawString("0°", 256 - 24, midY);
		g.drawString("90°", midX - 7, 256 - 16);
		g.drawString("180°", 8, midY);
		g.drawString("270°", midX - 12, 28);

		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));
		g.setColor(new Color(122, 134, 255,200));

		int x0 = midX + ((radius * Draw3D.cos[angle]) >> 16);
		int y0 = midY + ((radius * Draw3D.sin[angle]) >> 16);

		// hypontenuse
		g.drawLine(midX, midY, x0, y0);

		// opposite
		g.setColor(new Color(0,255,0,200));
		g.drawLine(x0, y0, x0, midY);

		// adjacent
		g.setColor(new Color(255,0, 0,200));
		g.drawLine(x0, y0, midX, y0);

		angle += 2;
		angle &= 0x7FF;
	}

	@Override
	public void run() {
		while (this.isVisible()) {
			repaint();

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
