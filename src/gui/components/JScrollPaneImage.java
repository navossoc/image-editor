package gui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import filters.GrayscaleFilter;

@SuppressWarnings("serial")
public class JScrollPaneImage extends JScrollPane {

	final private int[] histogram;

	private BufferedImage image;

	public JScrollPaneImage(BufferedImage image) {
		this.image = image;
		setViewportView(new JViewportImage());

		// apply grayscale filter
		GrayscaleFilter filter = new GrayscaleFilter(image);
		histogram = filter.histogram();
	}

	public int[] getHistogram() {
		return histogram;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	class JViewportImage extends JViewport {

		public JViewportImage() {
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(image, 0, 0, null);
		}
	}

}
