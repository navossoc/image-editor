package gui;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.components.JScrollPaneImage;
import gui.dialogs.JHistogramDialog;
import gui.menu.MenuFileActions;
import gui.menu.MenuFilterActions;
import gui.menu.MenuFilterActions.Arithmetic;
import gui.menu.MenuFilterActions.Channel;
import gui.menu.MenuFilterActions.Convolution;
import gui.menu.MenuFilterActions.Grayscale;
import gui.menu.MenuFilterActions.Logic;
import gui.menu.MenuGeometricActions;
import gui.menu.MenuGeometricActions.Flip;
import gui.menu.MenuViewActions;

@SuppressWarnings("serial")
public class ImageEditor extends JFrame {

	private int id = 1;
	private final JTabbedPane jImagesTab = new JTabbedPane();

	public static JHistogramDialog jHistogram;
	public JCheckBoxMenuItem jMenuViewHistogram;

	public void addImageTab(BufferedImage image) {
		addImageTab("Imagem", image);
	}

	public void addImageTab(String name, BufferedImage image) {
		jImagesTab.add(name + "_" + (id++), new JScrollPaneImage(image));
		jImagesTab.setSelectedIndex(jImagesTab.getTabCount() - 1);
	}

	public BufferedImage getImageTab() {
		Component c = jImagesTab.getSelectedComponent();
		if (c != null) {
			JScrollPaneImage image = (JScrollPaneImage) c;
			return image.getImage();
		}
		return null;
	}

	public JScrollPaneImage getJScrollPaneImageTab() {
		Component c = jImagesTab.getSelectedComponent();
		if (c != null) {
			JScrollPaneImage image = (JScrollPaneImage) c;
			return image;
		}
		return null;
	}

	public BufferedImage getImageTab(int i) {
		try {
			Component c = jImagesTab.getComponentAt(i);
			JScrollPaneImage image = (JScrollPaneImage) c;
			return image.getImage();
		} catch (IndexOutOfBoundsException e) {
		}
		return null;
	}

	public void setTabTitle(String title) {
		jImagesTab.setTitleAt(jImagesTab.getSelectedIndex(), title);
	}

	public ImageEditor() {
		// frame properties
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setSize(800, 600);
		setTitle("Image Editor");

		// create menus
		JMenuBar jMenuBar = new JMenuBar();
		createMenus(jMenuBar);
		setJMenuBar(jMenuBar);

		// image tabs
		jImagesTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (e.getButton() == MouseEvent.BUTTON2) {
					int index = jImagesTab.getSelectedIndex();
					if (index != -1) {
						jImagesTab.remove(index);
					}
				}
			}
		});

		// histogram
		jImagesTab.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				jHistogram.draw(getJScrollPaneImageTab());
				jHistogram.repaint();
			}
		});
		add(jImagesTab);
	}

	public static void main(String[] args) {
		ImageEditor jfImageEditor = new ImageEditor();
		jfImageEditor.setVisible(true);
		jHistogram = new JHistogramDialog(jfImageEditor);
		jHistogram.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	// create menu
	private void createMenus(JMenuBar jMenuBar) {

		// file
		JMenu jMenuFile = new JMenu("Arquivo");

		JMenuItem jMenuFileOpen = new JMenuItem("Abrir");
		JMenuItem jMenuFileSave = new JMenuItem("Salvar como");
		Separator jMenuFileSeparator = new JPopupMenu.Separator();
		JMenuItem jMenuFileExit = new JMenuItem("Sair");

		MenuFileActions menuFileActions = new MenuFileActions(this);
		jMenuFileOpen.addActionListener(menuFileActions.new OpenAction());
		jMenuFileSave.addActionListener(menuFileActions.new SaveAsAction());
		jMenuFileExit.addActionListener(menuFileActions.new ExitAction());

		jMenuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		jMenuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		jMenuBar.add(jMenuFile);
		jMenuFile.add(jMenuFileOpen);
		jMenuFile.add(jMenuFileSave);
		jMenuFile.add(jMenuFileSeparator);
		jMenuFile.add(jMenuFileExit);

		// view
		JMenu jMenuView = new JMenu("Exibir");

		jMenuViewHistogram = new JCheckBoxMenuItem("Histograma");

		MenuViewActions menuViewActions = new MenuViewActions(this);
		jMenuViewHistogram.addActionListener(menuViewActions.new HistogramAction());

		jMenuBar.add(jMenuView);
		jMenuView.add(jMenuViewHistogram);

		// filters
		JMenu jMenuFilters = new JMenu("Filtros");

		MenuFilterActions menuFilterActions = new MenuFilterActions(this);

		// filter -> arithmetic
		JMenu jMenuFiltersArithmetic = new JMenu("Aritmético");

		// filter -> arithmetic -> add
		JMenu jMenuFiltersArithmeticAdd = new JMenu("Soma");
		JMenuItem jMenuFiltersArithmeticAddConst = new JMenuItem("Constante");
		JMenuItem jMenuFiltersArithmeticAddImage = new JMenuItem("Imagem");

		jMenuFiltersArithmeticAddConst.addActionListener(menuFilterActions.new Arithmetic(Arithmetic.ADDITION_CONST));
		jMenuFiltersArithmeticAddImage.addActionListener(menuFilterActions.new Arithmetic(Arithmetic.ADDITION_IMAGE));

		jMenuFiltersArithmeticAdd.add(jMenuFiltersArithmeticAddConst);
		jMenuFiltersArithmeticAdd.add(jMenuFiltersArithmeticAddImage);
		jMenuFiltersArithmetic.add(jMenuFiltersArithmeticAdd);

		// filter -> arithmetic -> sub
		JMenu jMenuFiltersArithmeticSub = new JMenu("Subtração");
		JMenuItem jMenuFiltersArithmeticSubConst = new JMenuItem("Constante");
		JMenuItem jMenuFiltersArithmeticSubImage = new JMenuItem("Imagem");

		jMenuFiltersArithmeticSubConst
				.addActionListener(menuFilterActions.new Arithmetic(Arithmetic.SUBTRACTION_CONST));
		jMenuFiltersArithmeticSubImage
				.addActionListener(menuFilterActions.new Arithmetic(Arithmetic.SUBTRACTION_IMAGE));

		jMenuFiltersArithmeticSub.add(jMenuFiltersArithmeticSubConst);
		jMenuFiltersArithmeticSub.add(jMenuFiltersArithmeticSubImage);
		jMenuFiltersArithmetic.add(jMenuFiltersArithmeticSub);

		// filter -> arithmetic -> mul
		JMenu jMenuFiltersArithmeticMul = new JMenu("Multiplicação");
		JMenuItem jMenuFiltersArithmeticMulConst = new JMenuItem("Constante");
		JMenuItem jMenuFiltersArithmeticMulImage = new JMenuItem("Imagem");

		jMenuFiltersArithmeticMulConst
				.addActionListener(menuFilterActions.new Arithmetic(Arithmetic.MULTIPLICATION_CONST));
		jMenuFiltersArithmeticMulImage
				.addActionListener(menuFilterActions.new Arithmetic(Arithmetic.MULTIPLICATION_IMAGE));

		jMenuFiltersArithmeticMul.add(jMenuFiltersArithmeticMulConst);
		jMenuFiltersArithmeticMul.add(jMenuFiltersArithmeticMulImage);
		jMenuFiltersArithmetic.add(jMenuFiltersArithmeticMul);

		// filter -> arithmetic -> div
		JMenu jMenuFiltersArithmeticDiv = new JMenu("Divisão");
		JMenuItem jMenuFiltersArithmeticDivConst = new JMenuItem("Constante");
		JMenuItem jMenuFiltersArithmeticDivImage = new JMenuItem("Imagem");

		jMenuFiltersArithmeticDivConst.addActionListener(menuFilterActions.new Arithmetic(Arithmetic.DIVISION_CONST));
		jMenuFiltersArithmeticDivImage.addActionListener(menuFilterActions.new Arithmetic(Arithmetic.DIVISION_IMAGE));

		jMenuFiltersArithmeticDiv.add(jMenuFiltersArithmeticDivConst);
		jMenuFiltersArithmeticDiv.add(jMenuFiltersArithmeticDivImage);
		jMenuFiltersArithmetic.add(jMenuFiltersArithmeticDiv);

		jMenuFilters.add(jMenuFiltersArithmetic);

		// filter -> channel
		JMenu jMenuFiltersChannel = new JMenu("Canal");

		JMenuItem jMenuFiltersChannelRed = new JMenuItem("Vermelho");
		JMenuItem jMenuFiltersChannelGreen = new JMenuItem("Verde");
		JMenuItem jMenuFiltersChannelBlue = new JMenuItem("Azul");

		jMenuFiltersChannelRed.addActionListener(menuFilterActions.new Channel(Channel.RED));
		jMenuFiltersChannelGreen.addActionListener(menuFilterActions.new Channel(Channel.GREEN));
		jMenuFiltersChannelBlue.addActionListener(menuFilterActions.new Channel(Channel.BLUE));

		jMenuFiltersChannel.add(jMenuFiltersChannelRed);
		jMenuFiltersChannel.add(jMenuFiltersChannelGreen);
		jMenuFiltersChannel.add(jMenuFiltersChannelBlue);

		jMenuFilters.add(jMenuFiltersChannel);

		// filter -> logic
		JMenu jMenuFiltersLogic = new JMenu("Lógico");

		// filter -> logic -> and
		JMenu jMenuFiltersLogicAnd = new JMenu("E");
		JMenuItem jMenuFiltersLogicAndConst = new JMenuItem("Constante");
		JMenuItem jMenuFiltersLogicAndImage = new JMenuItem("Imagem");

		jMenuFiltersLogicAndConst.addActionListener(menuFilterActions.new Logic(Logic.AND_CONST));
		jMenuFiltersLogicAndImage.addActionListener(menuFilterActions.new Logic(Logic.AND_IMAGE));

		jMenuFiltersLogicAnd.add(jMenuFiltersLogicAndConst);
		jMenuFiltersLogicAnd.add(jMenuFiltersLogicAndImage);
		jMenuFiltersLogic.add(jMenuFiltersLogicAnd);

		// filter -> logic -> or
		JMenu jMenuFiltersLogicOr = new JMenu("Ou");
		JMenuItem jMenuFiltersLogicOrConst = new JMenuItem("Constante");
		JMenuItem jMenuFiltersLogicOrImage = new JMenuItem("Imagem");

		jMenuFiltersLogicOrConst.addActionListener(menuFilterActions.new Logic(Logic.OR_CONST));
		jMenuFiltersLogicOrImage.addActionListener(menuFilterActions.new Logic(Logic.OR_IMAGE));

		jMenuFiltersLogicOr.add(jMenuFiltersLogicOrConst);
		jMenuFiltersLogicOr.add(jMenuFiltersLogicOrImage);
		jMenuFiltersLogic.add(jMenuFiltersLogicOr);

		// filter -> logic -> not
		JMenuItem jMenuFiltersLogicNot = new JMenuItem("Negação");
		jMenuFiltersLogicNot.addActionListener(menuFilterActions.new Logic(Logic.NOT));
		jMenuFiltersLogic.add(jMenuFiltersLogicNot);

		// filter -> logic -> xor
		JMenu jMenuFiltersLogicXor = new JMenu("Ou lógico");
		JMenuItem jMenuFiltersLogicXorConst = new JMenuItem("Constante");
		JMenuItem jMenuFiltersLogicXorImage = new JMenuItem("Imagem");

		jMenuFiltersLogicXorConst.addActionListener(menuFilterActions.new Logic(Logic.XOR_CONST));
		jMenuFiltersLogicXorImage.addActionListener(menuFilterActions.new Logic(Logic.XOR_IMAGE));

		jMenuFiltersLogicXor.add(jMenuFiltersLogicXorConst);
		jMenuFiltersLogicXor.add(jMenuFiltersLogicXorImage);
		jMenuFiltersLogic.add(jMenuFiltersLogicXor);

		jMenuFilters.add(jMenuFiltersLogic);

		// filters -> grayscale
		JMenu jMenuFiltersGrayscale = new JMenu("Nível de cinza");

		JMenuItem jMenuFiltersGrayscaleAverage = new JMenuItem("Média");
		JMenuItem jMenuFiltersGrayscaleSDTV = new JMenuItem("SDTV");
		JMenuItem jMenuFiltersGrayscaleHDTV = new JMenuItem("HDTV");

		jMenuFiltersGrayscaleAverage.addActionListener(menuFilterActions.new Grayscale(Grayscale.AVERAGE));
		jMenuFiltersGrayscaleSDTV.addActionListener(menuFilterActions.new Grayscale(Grayscale.SDTV));
		jMenuFiltersGrayscaleHDTV.addActionListener(menuFilterActions.new Grayscale(Grayscale.HDTV));

		jMenuFiltersGrayscale.add(jMenuFiltersGrayscaleAverage);
		jMenuFiltersGrayscale.add(jMenuFiltersGrayscaleSDTV);
		jMenuFiltersGrayscale.add(jMenuFiltersGrayscaleHDTV);

		jMenuFilters.add(jMenuFiltersGrayscale);

		// filters -> convolution
		JMenu jMenuFiltersConvolution = new JMenu("Convolução");

		JMenuItem jMenuFiltersConvolutionGeneric = new JMenuItem("Genérica");
		JMenuItem jMenuFiltersConvolutionRoberts = new JMenuItem("Roberts");
		JMenuItem jMenuFiltersConvolutionSobel = new JMenuItem("Sobel");

		jMenuFiltersConvolutionGeneric.addActionListener(menuFilterActions.new Convolution(Convolution.GENERIC));
		jMenuFiltersConvolutionRoberts.addActionListener(menuFilterActions.new Convolution(Convolution.ROBERTS));
		jMenuFiltersConvolutionSobel.addActionListener(menuFilterActions.new Convolution(Convolution.SOBEL));

		jMenuFiltersConvolution.add(jMenuFiltersConvolutionGeneric);
		jMenuFiltersConvolution.add(jMenuFiltersConvolutionRoberts);
		jMenuFiltersConvolution.add(jMenuFiltersConvolutionSobel);

		jMenuFilters.add(jMenuFiltersConvolution);

		// filters
		JMenuItem jMenuFiltersBlend = new JMenuItem("Blend");
		jMenuFiltersBlend.addActionListener(new MenuFilterActions(this, MenuFilterActions.BLEND));
		jMenuFilters.add(jMenuFiltersBlend);

		JMenuItem jMenuFiltersSearch = new JMenuItem("Busca");
		jMenuFiltersSearch.addActionListener(new MenuFilterActions(this, MenuFilterActions.SEARCH));
		jMenuFilters.add(jMenuFiltersSearch);

		JMenuItem jMenuFiltersThreshold = new JMenuItem("Limiar");
		jMenuFiltersThreshold.addActionListener(new MenuFilterActions(this, MenuFilterActions.THRESHOULD));
		jMenuFilters.add(jMenuFiltersThreshold);

		JMenuItem jMenuFiltersMedian = new JMenuItem("Mediana");
		jMenuFiltersMedian.addActionListener(new MenuFilterActions(this, MenuFilterActions.MEDIAN));
		jMenuFilters.add(jMenuFiltersMedian);

		jMenuBar.add(jMenuFilters);

		// geometric
		JMenu jMenuGeometric = new JMenu("Geométrico");

		MenuGeometricActions menuGeometricActions = new MenuGeometricActions(this);

		JMenuItem jMenuGeometricTranslation = new JMenuItem("Translação");
		JMenuItem jMenuGeometricRotation = new JMenuItem("Rotação");
		JMenuItem jMenuGeometricScale = new JMenuItem("Escala");

		jMenuGeometricTranslation.addActionListener(new MenuGeometricActions(this, MenuGeometricActions.TRANSLATION));
		jMenuGeometricRotation.addActionListener(new MenuGeometricActions(this, MenuGeometricActions.ROTATION));
		jMenuGeometricScale.addActionListener(new MenuGeometricActions(this, MenuGeometricActions.SCALE));

		jMenuGeometric.add(jMenuGeometricTranslation);
		jMenuGeometric.add(jMenuGeometricRotation);
		jMenuGeometric.add(jMenuGeometricScale);

		// menu -> geometric -> mirror
		JMenu jMenuGeometricMirror = new JMenu("Espelhamento");

		JMenuItem jMenuGeometricMirrorHorizontal = new JMenuItem("Horizontal");
		JMenuItem jMenuGeometricMirrorVertical = new JMenuItem("Vertical");

		jMenuGeometricMirrorHorizontal.addActionListener(menuGeometricActions.new Flip(Flip.HORIZONTAL));
		jMenuGeometricMirrorVertical.addActionListener(menuGeometricActions.new Flip(Flip.VERTICAL));

		jMenuGeometricMirror.add(jMenuGeometricMirrorHorizontal);
		jMenuGeometricMirror.add(jMenuGeometricMirrorVertical);

		jMenuGeometric.add(jMenuGeometricMirror);

		jMenuBar.add(jMenuGeometric);

	}

	// view -> histogram
	public boolean toogleHistogram() {
		boolean visible = !jHistogram.isVisible();
		jMenuViewHistogram.setSelected(visible);
		jHistogram.setVisible(visible);
		return visible;
	}

}
