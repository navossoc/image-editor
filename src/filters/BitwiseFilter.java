package filters;

import java.awt.image.BufferedImage;

public class BitwiseFilter extends BaseFilter {

	public BitwiseFilter(BufferedImage image) {
		super(image);
	}

	public BufferedImage and(int k) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[R] &= clamp(k);
				rgb[G] &= clamp(k);
				rgb[B] &= clamp(k);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage or(int k) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[R] |= clamp(k);
				rgb[G] |= clamp(k);
				rgb[B] |= clamp(k);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage not() {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);

				newimage.setRGB(i, j, ~px);
			}
		}

		return newimage;
	}

	public BufferedImage xor(int k) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int[] rgb = getRGBComponents(px);

				rgb[R] ^= clamp(k);
				rgb[G] ^= clamp(k);
				rgb[B] ^= clamp(k);

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage and(BufferedImage image2) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int px2 = image2.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int[] rgb2 = getRGBComponents(px2);

				rgb[R] &= rgb2[R];
				rgb[G] &= rgb2[G];
				rgb[B] &= rgb2[B];

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage or(BufferedImage image2) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int px2 = image2.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int[] rgb2 = getRGBComponents(px2);

				rgb[R] |= rgb2[R];
				rgb[G] |= rgb2[G];
				rgb[B] |= rgb2[B];

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}

	public BufferedImage xor(BufferedImage image2) {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);
				int px2 = image2.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int[] rgb2 = getRGBComponents(px2);

				rgb[R] ^= rgb2[R];
				rgb[G] ^= rgb2[G];
				rgb[B] ^= rgb2[B];

				newimage.setRGB(i, j, colorRGB(rgb));
			}
		}

		return newimage;
	}
}
