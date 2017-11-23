package filters;

import java.awt.image.BufferedImage;

public class GeometricFilter extends BaseFilter {

	public GeometricFilter(BufferedImage image) {
		super(image);
	}

	public BufferedImage translate(int x, int y) {
		newimage = new BufferedImage(width, height, type);

		int sx = clamp(x, 0, x);
		int sy = clamp(y, 0, y);

		int fx = clamp(x, x, 0);
		int fy = clamp(y, y, 0);

		for (int j = sy; j < height + fy; j++) {
			for (int i = sx; i < width + fx; i++) {
				int px = image.getRGB(i - x, j - y);
				newimage.setRGB(i, j, px);
			}
		}

		return newimage;
	}

	public BufferedImage rotate(double angle) {
		newimage = new BufferedImage(width, height, type);

		final int x0 = width / 2;
		final int y0 = height / 2;

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);

				long x = Math.round(Math.cos(angle) * (i - x0) - Math.sin(angle) * (j - y0) + x0);
				long y = Math.round(Math.sin(angle) * (i - x0) + Math.cos(angle) * (j - y0) + y0);

				if ((x > 0 && y > 0) && (x < width && y < height)) {
					newimage.setRGB((int) x, (int) y, px);
				}
			}
		}

		return newimage;
	}

	public BufferedImage scale(double scale) {
		int newwidth = (int) Math.ceil(width * scale);
		int hewheight = (int) Math.ceil(height * scale);

		newimage = new BufferedImage(newwidth, hewheight, type);

		if (scale < 1) {
			scale = 1 / scale;

			int x = 0, y = 0;
			for (int j = 0; j < height; j += scale) {
				x = 0;
				for (int i = 0; i < width; i += scale) {
					int px = image.getRGB(i, j);
					newimage.setRGB(x, y, px);
					x++;
				}
				y++;
			}
		} else {
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {
					int px = image.getRGB(i, j);

					for (int y = 0; y < scale; y++) {
						for (int x = 0; x < scale; x++) {
							newimage.setRGB((i * (int) scale) + x, (j * (int) scale) + y, px);
						}
					}
				}
			}
		}

		return newimage;
	}

	public BufferedImage flipHorizontal() {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);

				int y = height - j - 1;
				newimage.setRGB(i, y, px);
			}
		}

		return newimage;
	}

	public BufferedImage flipVertical() {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);

				int x = width - i - 1;
				newimage.setRGB(x, j, px);
			}
		}

		return newimage;
	}

}
