package gui.menu;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;

import filters.ArithmeticFilter;
import filters.BitwiseFilter;
import filters.BlendingFilter;
import filters.ChannelFilter;
import filters.ConvolutionFilter;
import filters.GrayscaleFilter;
import filters.MedianFilter;
import filters.SearchFilter;
import gui.ImageEditor;
import gui.dialogs.JConvolutionDialog;
import gui.dialogs.JConvolutionDialog.Matrix;

@SuppressWarnings("serial")
public class MenuFilterActions extends MenuBaseAction {

	public static final int BLEND = 0;
	public static final int SEARCH = 1;
	public static final int THRESHOULD = 2;
	public static final int MEDIAN = 3;

	public MenuFilterActions(ImageEditor jFrame) {
		super(jFrame, NONE);
	}

	public MenuFilterActions(ImageEditor jFrame, int action) {
		super(jFrame, action);
	}

	public class Channel extends AbstractAction {

		public static final int RED = 0;
		public static final int GREEN = 1;
		public static final int BLUE = 2;

		private final int action;

		public Channel(int channel) {
			this.action = channel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isValidTab()) {
				return;
			}

			BufferedImage temp = jfImageEditor.getImageTab();
			ChannelFilter filter = new ChannelFilter(temp);

			switch (action) {
			case RED:
				temp = filter.red();
				break;
			case GREEN:
				temp = filter.green();
				break;
			case BLUE:
				temp = filter.blue();
				break;
			}

			jfImageEditor.addImageTab(temp);
		}

	}

	public class Arithmetic extends AbstractAction {

		public static final int ADDITION_CONST = 0;
		public static final int SUBTRACTION_CONST = 1;
		public static final int MULTIPLICATION_CONST = 2;
		public static final int DIVISION_CONST = 3;
		public static final int ADDITION_IMAGE = 4;
		public static final int SUBTRACTION_IMAGE = 5;
		public static final int MULTIPLICATION_IMAGE = 6;
		public static final int DIVISION_IMAGE = 7;

		private final int action;

		public Arithmetic(int action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isValidTab()) {
				return;
			}

			BufferedImage temp = jfImageEditor.getImageTab();
			ArithmeticFilter filter = new ArithmeticFilter(temp);

			// constants
			int ki = 0;
			double kd = 0;
			switch (action) {
			case ADDITION_CONST:
			case SUBTRACTION_CONST: {
				ki = promptInteger();
				if (ki == -1) {
					return;
				}
				break;
			}
			case MULTIPLICATION_CONST:
			case DIVISION_CONST: {
				kd = promptDouble();
				if (ki == -1) {
					return;
				}
				break;
			}
			}

			// images
			BufferedImage temp2 = null;
			switch (action) {
			case ADDITION_IMAGE:
			case SUBTRACTION_IMAGE:
			case MULTIPLICATION_IMAGE:
			case DIVISION_IMAGE: {
				temp2 = promptImage();
				if (temp2 == null) {
					return;
				}
				break;
			}
			}

			// apply filter
			switch (action) {
			case ADDITION_CONST: {
				temp = filter.add(ki);
				break;
			}
			case SUBTRACTION_CONST: {
				temp = filter.sub(ki);
				break;
			}
			case MULTIPLICATION_CONST: {
				temp = filter.mul(kd);
				break;
			}
			case DIVISION_CONST: {
				temp = filter.div(kd);
				break;
			}
			case ADDITION_IMAGE: {
				temp = filter.add(temp2);
				break;
			}
			case SUBTRACTION_IMAGE: {
				temp = filter.sub(temp2);
				break;
			}
			case MULTIPLICATION_IMAGE: {
				temp = filter.mul(temp2);
				break;
			}
			case DIVISION_IMAGE: {
				temp = filter.div(temp2);
				break;
			}
			}

			jfImageEditor.addImageTab(temp);
		}

	}

	public class Logic extends AbstractAction {

		public static final int AND_CONST = 0;
		public static final int OR_CONST = 1;
		public static final int NOT = 2;
		public static final int XOR_CONST = 3;
		public static final int AND_IMAGE = 4;
		public static final int OR_IMAGE = 5;
		public static final int XOR_IMAGE = 6;

		private final int action;

		public Logic(int action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isValidTab()) {
				return;
			}

			BufferedImage temp = jfImageEditor.getImageTab();
			BitwiseFilter filter = new BitwiseFilter(temp);

			switch (action) {
			case AND_CONST: {
				int k = promptInteger();
				temp = filter.and(k);
				break;
			}
			case OR_CONST: {
				int k = promptInteger();
				temp = filter.or(k);
				break;
			}
			case NOT: {
				temp = filter.not();
				break;
			}
			case XOR_CONST: {
				int k = promptInteger();
				temp = filter.xor(k);
				break;
			}
			case AND_IMAGE: {
				BufferedImage temp2 = promptImage();
				if (temp2 == null) {
					return;
				}
				temp = filter.and(temp2);
				break;
			}
			case OR_IMAGE: {
				BufferedImage temp2 = promptImage();
				if (temp2 == null) {
					return;
				}
				temp = filter.or(temp2);
				break;
			}
			case XOR_IMAGE: {
				BufferedImage temp2 = promptImage();
				if (temp2 == null) {
					return;
				}
				temp = filter.xor(temp2);
				break;
			}
			}

			jfImageEditor.addImageTab(temp);
		}

	}

	public class Grayscale extends AbstractAction {

		public static final int AVERAGE = 0;
		public static final int SDTV = 1;
		public static final int HDTV = 2;

		private final int action;

		public Grayscale(int action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isValidTab()) {
				return;
			}

			BufferedImage temp = jfImageEditor.getImageTab();
			GrayscaleFilter filter = new GrayscaleFilter(temp);

			switch (action) {
			case AVERAGE: {
				temp = filter.average();
				break;
			}
			case SDTV: {
				temp = filter.sdtv();
				break;
			}
			case HDTV: {
				temp = filter.hdtv();
				break;
			}
			}

			jfImageEditor.addImageTab(temp);
		}

	}

	public class Convolution extends AbstractAction {

		public static final int GENERIC = 0;
		public static final int ROBERTS = 1;
		public static final int SOBEL = 2;

		private final int action;

		public Convolution(int action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isValidTab()) {
				return;
			}

			BufferedImage temp = jfImageEditor.getImageTab();
			ConvolutionFilter filter = new ConvolutionFilter(temp);

			switch (action) {
			case GENERIC: {
				JConvolutionDialog jConvolutionMatrix = new JConvolutionDialog(jfImageEditor, "Matriz Convolução",
						true);

				Matrix matrix = jConvolutionMatrix.getMatrix();
				if (matrix == null) {
					return;
				}

				int[][] kernel = matrix.getData();
				int divisor = matrix.getDivisor();

				temp = filter.convolution(kernel, divisor);
				break;
			}
			case ROBERTS: {
				temp = filter.roberts();
				break;
			}
			case SOBEL: {
				temp = filter.sobel();
				break;
			}
			}

			jfImageEditor.addImageTab(temp);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isValidTab()) {
			return;
		}

		BufferedImage temp = jfImageEditor.getImageTab();

		switch (action) {
		case BLEND: {
			BufferedImage temp2 = promptImage();
			if (temp2 == null) {
				return;
			}
			double k = promptDouble(100);

			BlendingFilter filter = new BlendingFilter(temp);
			temp = filter.blend(temp2, k);
			break;
		}
		case SEARCH: {
			BufferedImage temp2 = promptImage();
			if (temp2 == null) {
				return;
			}

			SearchFilter filter = new SearchFilter(temp);
			temp = filter.findPattern(temp2);
			break;
		}
		case THRESHOULD: {
			int k = promptInteger(255);
			if (k == -1) {
				return;
			}

			GrayscaleFilter filter = new GrayscaleFilter(temp);
			temp = filter.threshold(k);
			break;
		}
		case MEDIAN: {
			int k = promptInteger(1, 5);
			if (k == -1) {
				return;
			}

			MedianFilter filter = new MedianFilter(temp);
			temp = filter.median(k);
			break;
		}

		}

		jfImageEditor.addImageTab(temp);
	}

}
