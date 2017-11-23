package filters;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MedianFilter extends BaseFilter {

	public MedianFilter(BufferedImage image) {
		super(image);
	}

	public BufferedImage median(int k) {
		newimage = new BufferedImage(width, height, type);

		final int size = k + k + 1;
		final int center = (size * size - 1) / 2;

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {

				int c = 0;
				int[][] colors = new int[3][size * size];

				for (int y = 0; y < size; y++) {
					for (int x = 0; x < size; x++) {
						int tx = (i - k + x);
						int ty = (j - k + y);

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

						colors[R][c] = rgb[R];
						colors[G][c] = rgb[G];
						colors[B][c] = rgb[B];
						c++;
					}
				}

				Arrays.sort(colors[R]);
				Arrays.sort(colors[G]);
				Arrays.sort(colors[B]);
				c = 0;

				newimage.setRGB(i, j, colorRGB(colors[R][center], colors[G][center], colors[B][center]));

			}
		}

		return newimage;
	}

}
