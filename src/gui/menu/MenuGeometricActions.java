package gui.menu;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;

import filters.GeometricFilter;
import gui.ImageEditor;

@SuppressWarnings("serial")
public class MenuGeometricActions extends MenuBaseAction {

	public static final int TRANSLATION = 0;
	public static final int ROTATION = 1;
	public static final int SCALE = 2;

	public MenuGeometricActions(ImageEditor jFrame) {
		super(jFrame, NONE);
	}

	public MenuGeometricActions(ImageEditor jFrame, int action) {
		super(jFrame, action);
	}

	public class Flip extends AbstractAction {

		public static final int HORIZONTAL = 0;
		public static final int VERTICAL = 1;

		private final int action;

		public Flip(int action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isValidTab()) {
				return;
			}

			BufferedImage temp = jfImageEditor.getImageTab();
			GeometricFilter filter = new GeometricFilter(temp);

			switch (action) {
			case HORIZONTAL: {
				temp = filter.flipHorizontal();
				break;
			}
			case VERTICAL: {
				temp = filter.flipVertical();
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
		GeometricFilter filter = new GeometricFilter(temp);

		switch (action) {
		case TRANSLATION: {
			int pos[] = promptNumberXY();
			temp = filter.translate(pos[0], pos[1]);
			break;
		}
		case ROTATION: {
			double angle = promptNumberDegree();
			temp = filter.rotate(angle);
			break;
		}
		case SCALE: {
			double value = promptDouble();
			temp = filter.scale(value);
			break;
		}
		}

		jfImageEditor.addImageTab(temp);
	}

}
