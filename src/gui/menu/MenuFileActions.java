package gui.menu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.ImageEditor;

public class MenuFileActions {

	private final ImageEditor jfImageEditor;

	public MenuFileActions(ImageEditor jFrame) {
		this.jfImageEditor = jFrame;
	}

	@SuppressWarnings("serial")
	public class OpenAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes()));
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setMultiSelectionEnabled(true);

			// set current working directory
			fileChooser.setCurrentDirectory(new File(".\\data\\"));

			// open action
			if (fileChooser.showOpenDialog(jfImageEditor) == JFileChooser.APPROVE_OPTION) {
				for (File file : fileChooser.getSelectedFiles())
					try {
						String[] filename = file.getName().split("\\.(?=[^\\.]+$)");
						BufferedImage img = ImageIO.read(file);
						if (img != null) {
							jfImageEditor.addImageTab(filename[0], img);
						} else {
							JOptionPane.showMessageDialog(jfImageEditor, "Não foi possível abrir o arquivo!",
									"Formato inválido", JOptionPane.ERROR_MESSAGE);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		}

	}

	@SuppressWarnings("serial")
	public class SaveAsAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			// no image
			BufferedImage temp = jfImageEditor.getImageTab();
			if (temp == null) {
				return;
			}

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("bmp", "bmp"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("gif", "gif"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("png", "png"));

			// save action
			if (fileChooser.showSaveDialog(jfImageEditor) == JFileChooser.APPROVE_OPTION) {

				File file = fileChooser.getSelectedFile();
				String filename = file.getName();
				String extension = "." + fileChooser.getFileFilter().getDescription();

				// check if need to append extension
				if (!filename.endsWith(extension)) {
					file = new File(file + extension);
				}

				// check if file already exists
				if (file.exists()) {
					if (JOptionPane.showConfirmDialog(jfImageEditor, "O arquivo já existe, deseja sobreescrever?",
							"Confirmar salvar como", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
				}

				// save it
				try {
					String format = fileChooser.getFileFilter().getDescription();
					ImageIO.write(temp, format, file);
					jfImageEditor.setTabTitle(filename);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	@SuppressWarnings("serial")
	public class ExitAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					jfImageEditor.dispose();
				}
			});

		}
	}
}
