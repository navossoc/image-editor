package filters;

import java.awt.image.BufferedImage;

public class ChannelFilter extends BaseFilter {

	public ChannelFilter(BufferedImage image) {
		super(image);
	}

	public BufferedImage red() {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[G] = rgb[R];
				rgb[B] = rgb[R];

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage green() {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[R] = rgb[G];
				rgb[B] = rgb[G];

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage blue() {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[R] = rgb[B];
				rgb[G] = rgb[B];

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

}
