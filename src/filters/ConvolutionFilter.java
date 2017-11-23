package filters;

import java.awt.image.BufferedImage;

public class ConvolutionFilter extends BaseFilter {

	public ConvolutionFilter(BufferedImage image) {
		super(image);
	}

	public BufferedImage convolution(final int[][] kernel, final float divider) {
		newimage = new BufferedImage(width, height, type);

		final int kh = kernel.length;
		final int kw = kernel[0].length;

		final int kx = (kw - 1) / 2;
		final int ky = (kh - 1) / 2;

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {

				int r = 0, g = 0, b = 0;

				for (int kr = 0; kr < kh; kr++) {
					for (int kc = 0; kc < kw; kc++) {

						int tx = (i - kx + kc);
						int ty = (j - ky + kr);

						if (tx < 0) {
							tx = 0;
						} else if (tx >= width) {
							tx = width - 1;
						}

						if (ty < 0) {
							ty = 0;
						} else if (ty >= height) {
							ty = height - 1;
						}
						int px = image.getRGB(tx, ty);
						int[] rgb = getRGBComponents(px);

						r += rgb[R] * kernel[kr][kc];
						g += rgb[G] * kernel[kr][kc];
						b += rgb[B] * kernel[kr][kc];
					}

				}

				r = clamp(Math.round(r / divider));
				g = clamp(Math.round(g / divider));
				b = clamp(Math.round(b / divider));

				newimage.setRGB(i, j, colorRGB(r, g, b));
			}
		}

		return newimage;
	}

	public BufferedImage roberts() {
		final int[][] robertsX = new int[][] { { 1, 0 }, { 0, -1 } };
		final int[][] robertsY = new int[][] { { 0, 1 }, { -1, 0 } };

		return convolution(robertsX, robertsY);
	}

	public BufferedImage sobel() {
		final int[][] sobelX = new int[][] { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } };
		final int[][] sobelY = new int[][] { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

		return convolution(sobelX, sobelY);
	}

	private BufferedImage convolution(final int[][] kernelX, final int[][] kernelY) {
		newimage = new BufferedImage(width, height, type);

		final int kh = kernelX.length;
		final int kw = kernelX[0].length;

		final int kx = (kw - 1) / 2;
		final int ky = (kh - 1) / 2;

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {

				int rx = 0, gx = 0, bx = 0;
				int ry = 0, gy = 0, by = 0;

				for (int kr = 0; kr < kh; kr++) {
					for (int kc = 0; kc < kw; kc++) {

						int tx = (i - kx + kc);
						int ty = (j - ky + kr);

						if (tx < 0) {
							tx = 0;
						} else if (tx >= width) {
							tx = width - 1;
						}

						if (ty < 0) {
							ty = 0;
						} else if (ty >= height) {
							ty = height - 1;
						}
						int px = image.getRGB(tx, ty);
						int[] rgb = getRGBComponents(px);

						final int kvx = kernelX[kr][kc];
						rx += rgb[R] * kvx;
						gx += rgb[G] * kvx;
						bx += rgb[B] * kvx;

						final int kvy = kernelY[kr][kc];
						ry += rgb[R] * kvy;
						gy += rgb[G] * kvy;
						by += rgb[B] * kvy;
					}

				}

				final int r, g, b;
				r = clamp(Math.sqrt(rx * rx + ry * ry));
				g = clamp(Math.sqrt(gx * gx + gy * gy));
				b = clamp(Math.sqrt(bx * bx + by * by));

				newimage.setRGB(i, j, colorRGB(r, g, b));
			}
		}

		return newimage;
	}

}
