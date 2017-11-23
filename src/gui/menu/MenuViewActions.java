package gui.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gui.ImageEditor;

public class MenuViewActions {

	private final ImageEditor jfImageEditor;

	public MenuViewActions(ImageEditor jFrame) {
		this.jfImageEditor = jFrame;
	}

	@SuppressWarnings("serial")
	public class HistogramAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			jfImageEditor.toogleHistogram();
		}

	}

}
