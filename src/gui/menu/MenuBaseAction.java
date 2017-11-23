package gui.menu;

import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import gui.ImageEditor;

@SuppressWarnings("serial")
public abstract class MenuBaseAction extends AbstractAction {

	public static final int NONE = -1;

	protected final ImageEditor jfImageEditor;
	protected final int action;

	public MenuBaseAction(ImageEditor jFrame) {
		this(jFrame, NONE);
	}

	public MenuBaseAction(ImageEditor jFrame, int action) {
		this.jfImageEditor = jFrame;
		this.action = action;
	}

	protected boolean isValidTab() {
		return jfImageEditor.getImageTab() != null ? true : false;
	}

	protected double promptDouble() {
		return promptDouble(0, 255);
	}

	protected double promptDouble(double max) {
		return promptDouble(0, max);
	}

	protected double promptDouble(double min, double max) {
		String input = JOptionPane.showInputDialog("Digite o valor da constante: [" + min + " - " + max + "]");

		// cancel
		if (input == null) {
			return -1;
		}

		// parse number
		double number;
		try {
			number = Double.parseDouble(input);
			// clamp
			if (number < min) {
				number = min;
			} else if (number > max) {
				number = max;
			}
		} catch (NumberFormatException e) {
			number = 0;
		}

		return number;
	}

	protected BufferedImage promptImage() {
		String input = JOptionPane.showInputDialog("Digite o número da aba:");

		// cancel
		if (input == null) {
			return null;
		}

		// parse number
		int number;
		try {
			number = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			number = 1;
		}

		return jfImageEditor.getImageTab(number - 1);
	}

	protected int promptInteger() {
		return promptInteger(0, 255);
	}

	protected int promptInteger(int max) {
		return promptInteger(0, max);
	}

	protected int promptInteger(int min, int max) {
		String input = JOptionPane.showInputDialog("Digite o valor da constante: [" + min + " - " + max + "]");

		// cancel
		if (input == null) {
			return -1;
		}

		// parse number
		int number;
		try {
			number = Integer.parseInt(input);
			// clamp
			if (number < min) {
				number = min;
			} else if (number > max) {
				number = max;
			}
		} catch (NumberFormatException e) {
			number = 0;
		}

		return number;
	}

	protected int[] promptNumberXY() {
		String inputX = JOptionPane.showInputDialog("Digite o valor de X:", 0);
		String inputY = JOptionPane.showInputDialog("Digite o valor de Y:", 0);

		// cancel
		if (inputX == null || inputY == null) {
			return new int[] { 0, 0 };
		}

		// parse number
		int numberX, numberY;
		try {
			numberX = Integer.parseInt(inputX);
			numberY = Integer.parseInt(inputY);

		} catch (NumberFormatException e) {
			numberX = 0;
			numberY = 0;
		}

		return new int[] { numberX, numberY };
	}

	protected double promptNumberDegree() {
		String input = JOptionPane.showInputDialog("Digite o ângulo (sentido horário):", 0);

		// cancel
		if (input == null) {
			return 0;
		}

		// parse number
		double degree;
		try {
			degree = Double.parseDouble(input);
			// clamp
			if (degree < -360) {
				degree = -360;
			} else if (degree > 360) {
				degree = 360;
			}
		} catch (NumberFormatException e) {
			degree = 0;
		}

		return (degree * Math.PI) / 180;
	}

}
