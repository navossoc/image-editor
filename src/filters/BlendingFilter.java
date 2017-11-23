package filters;

import java.awt.image.BufferedImage;

public class BlendingFilter extends BaseFilter {

	public BlendingFilter(BufferedImage image) {
		super(image);
	}

	public BufferedImage blend(BufferedImage image2, double level) {
		newimage = new BufferedImage(width, height, type);

		level /= 100f;
		double level2 = 1f - level;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int px2 = image2.getRGB(i, j);

				int rgb[] = getRGBComponents(px);
				int rgb2[] = getRGBComponents(px2);

				int r = clamp(rgb[R] * level + rgb2[R] * level2);
				int g = clamp(rgb[G] * level + rgb2[G] * level2);
				int b = clamp(rgb[B] * level + rgb2[B] * level2);

				newimage.setRGB(i, j, colorRGB(r, g, b));
			}
		}

		return newimage;
	}

}
