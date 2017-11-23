package filters;

import java.awt.image.BufferedImage;

public class ArithmeticFilter extends BaseFilter {

	public ArithmeticFilter(BufferedImage image) {
		super(image);
	}

	public BufferedImage add(int k) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[R] = clamp(rgb[R] + k);
				rgb[G] = clamp(rgb[G] + k);
				rgb[B] = clamp(rgb[B] + k);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage sub(int k) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[R] = clamp(rgb[R] - k);
				rgb[G] = clamp(rgb[G] - k);
				rgb[B] = clamp(rgb[B] - k);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage mul(double k) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[R] = clamp(rgb[R] * k);
				rgb[G] = clamp(rgb[G] * k);
				rgb[B] = clamp(rgb[B] * k);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage div(double k) {
		newimage = new BufferedImage(width, height, type);

		if (k == 0) {
			k = 1;
		}

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[R] = clamp(rgb[R] / k);
				rgb[G] = clamp(rgb[G] / k);
				rgb[B] = clamp(rgb[B] / k);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage add(BufferedImage image2) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int px2 = image2.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int[] rgb2 = getRGBComponents(px2);

				rgb[R] = clamp(rgb[R] + rgb2[R]);
				rgb[G] = clamp(rgb[G] + rgb2[G]);
				rgb[B] = clamp(rgb[B] + rgb2[B]);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage sub(BufferedImage image2) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int px2 = image2.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int[] rgb2 = getRGBComponents(px2);

				rgb[R] = clamp(rgb[R] - rgb2[R]);
				rgb[G] = clamp(rgb[G] - rgb2[G]);
				rgb[B] = clamp(rgb[B] - rgb2[B]);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage mul(BufferedImage image2) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int px2 = image2.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int[] rgb2 = getRGBComponents(px2);

				rgb[R] = clamp(rgb[R] * rgb2[R]);
				rgb[G] = clamp(rgb[G] * rgb2[G]);
				rgb[B] = clamp(rgb[B] * rgb2[B]);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage div(BufferedImage image2) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int px2 = image2.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int[] rgb2 = getRGBComponents(px2);

				// prevent division by zero
				if (rgb2[R] == 0) {
					rgb2[R] = 1;
				}
				if (rgb2[G] == 0) {
					rgb2[G] = 1;
				}
				if (rgb2[B] == 0) {
					rgb2[B] = 1;
				}

				rgb[R] = clamp(rgb[R] / rgb2[R]);
				rgb[G] = clamp(rgb[G] / rgb2[G]);
				rgb[B] = clamp(rgb[B] / rgb2[B]);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

}
