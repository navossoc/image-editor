package filters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SearchFilter extends BaseFilter {

	public SearchFilter(BufferedImage image) {
		super(image);
	}

	public BufferedImage findPattern(BufferedImage pattern) {
		newimage = new BufferedImage(width, height, type);

		// pattern
		final int pwidth = pattern.getWidth();
		final int pheight = pattern.getHeight();

		GrayscaleFilter pfilter = new GrayscaleFilter(pattern);
		final int[] phist = pfilter.histogram();

		// image
		int[] hist;
		GrayscaleFilter original = new GrayscaleFilter(image);

		boolean found = false;
		int lower = 0;

		Region region = new Region();

		// search
		for (int j = 0; j < height - pheight; j++) {
			for (int i = 0; i < width - pwidth; i++) {
				hist = original.histogram(i, j, pwidth, pheight);

				int result = compareHistogram(hist, phist);
				if (!found || result < lower) {
					lower = result;
					region.set(i, j, pwidth, pheight);

					found = true;
				}
			}
		}

		// copy image
		newimage.setData(image.getData());

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				newimage.setRGB(i, j, image.getRGB(i, j));
			}
		}

		// draw a circle
		Graphics2D graphics = newimage.createGraphics();
		graphics.setColor(Color.RED);
		graphics.drawOval(region.x, region.y, region.w, region.h);

		return newimage;
	}

	private int compareHistogram(int[] hist, int[] hist2) {
		int sum = 0;
		for (int i = 0; i < hist.length; i++) {
			sum += Math.abs(hist[i] - hist2[i]);
		}
		return sum;
	}

	private class Region {
		private int x, y, w, h;

		public Region() {
			set(0, 0, 0, 0);
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getWidth() {
			return w;
		}

		public int getHeight() {
			return h;
		}

		public void set(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		@Override
		public String toString() {
			return String.format("x: %d, y: %d, w: %d, h: %d\n", getX(), getY(), getWidth(), getHeight());
		}

	}

}
