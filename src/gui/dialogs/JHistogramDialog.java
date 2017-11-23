package gui.dialogs;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

import gui.ImageEditor;
import gui.components.JScrollPaneImage;

@SuppressWarnings("serial")
public class JHistogramDialog extends JDialog {

	private final double scale = 1;
	private final int width = 256;
	private final int height = 200;

	public JHistogramDialog(final ImageEditor jFrame) {
		super(jFrame);

		// screen size
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int x = gd.getDisplayMode().getWidth();

		// histogram
		setLocation(x - (int) (width * scale), 30);
		setModalityType(ModalityType.MODELESS);
		setSize((int) (width * scale), (int) (height * scale));

		setResizable(false);
		setTitle("Histograma");

		// events
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				jFrame.toogleHistogram();
			}
		});
	}

	public void draw(JScrollPaneImage image) {
		if (image == null) {
			setContentPane(new JPanel());
			return;
		}

		final int[] values = image.getHistogram();

		int max = 0;
		for (int value : values) {
			if (max < value) {
				max = value;
			}
		}

		for (int i = 0; i < values.length; i++) {
			values[i] = (values[i] * height) / max;
		}

		setContentPane(new JPanel() {

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.scale(scale, scale);

				for (int x = 0; x < width; x++) {
					g.drawLine(x, height, x, height - values[x]);
				}
			}
		});

		validate();
	}
}
