package gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class JConvolutionDialog extends JDialog {

	private Matrix matrix = null;

	private JTextField txtDivisor;
	private JTextArea txtrMatrix;
	private JCheckBox chckbxNormalize;

	public JConvolutionDialog(final Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setResizable(false);
		setSize(new Dimension(400, 300));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		JLabel lblFilter = new JLabel("Filtro:");
		lblFilter.setBounds(10, 11, 46, 14);
		getContentPane().add(lblFilter);

		final JComboBox<String> cbFilter = new JComboBox<String>();
		cbFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int item = cbFilter.getSelectedIndex();
				switch (item) {
				case 1: // blur
					txtrMatrix.setText("1 2 1\n" + "2 4 2\n" + "1 2 1");
					txtDivisor.setText("16");
					break;
				case 2: // gaussian blur
					txtrMatrix.setText(
							"1 4 7 4 1\n" + "4 16 26 16 4\n" + "7 26 41 26 7\n" + "4 16 26 16 4\n" + "1 4 7 4 1");
					txtDivisor.setText("273");
					break;
				default:
					break;
				}
			}
		});
		cbFilter.setModel(new DefaultComboBoxModel<String>(new String[] { "Personalizado", "Desfoque", "Gaussiano" }));
		cbFilter.setBounds(66, 8, 150, 20);
		getContentPane().add(cbFilter);

		final JPanel panelArea = new JPanel();
		panelArea.setBounds(10, 36, 424, 199);
		getContentPane().add(panelArea);
		panelArea.setLayout(new GridLayout(1, 0, 0, 0));

		txtrMatrix = new JTextArea();
		txtrMatrix.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (chckbxNormalize.isSelected()) {
					Matrix temp = new Matrix(txtrMatrix.getText(), true);
					txtDivisor.setText(String.valueOf(temp.getDivisor()));
				}
			}
		});
		txtrMatrix.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelArea.add(txtrMatrix);

		JLabel lblDivisor = new JLabel("Divisor:");
		lblDivisor.setBounds(10, 246, 46, 14);
		getContentPane().add(lblDivisor);

		txtDivisor = new JTextField();
		txtDivisor.setEditable(false);
		txtDivisor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				JTextField component = (JTextField) e.getComponent();
				component.selectAll();
			}
		});
		txtDivisor.setHorizontalAlignment(SwingConstants.CENTER);
		txtDivisor.setText("1");
		txtDivisor.setBounds(66, 243, 50, 20);
		getContentPane().add(txtDivisor);
		txtDivisor.setColumns(10);

		JButton btnApply = new JButton("Aplicar");
		btnApply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String values = txtrMatrix.getText();
				boolean divider = chckbxNormalize.isSelected();

				// create matrix
				matrix = new Matrix();
				if (matrix.parseText(values, divider)) {
					dispose();
				} else {
					matrix = null;
					JOptionPane.showMessageDialog(owner, "A matriz digitada não é válida!", "Matriz inválida",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnApply.setBounds(345, 242, 89, 23);
		getContentPane().add(btnApply);

		chckbxNormalize = new JCheckBox("Normalizar");
		chckbxNormalize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtDivisor.setEditable(!chckbxNormalize.isSelected());
			}
		});
		chckbxNormalize.setSelected(true);
		chckbxNormalize.setBounds(119, 242, 97, 23);
		getContentPane().add(chckbxNormalize);

		// dialog properties
		setLocationRelativeTo(owner);
		setVisible(true);
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public class Matrix {
		private int rows, cols;
		private int[][] data;
		private int divisor;

		public Matrix() {
		}

		public Matrix(String text, boolean normalize) {
			parseText(text, normalize);
		}

		public int[][] getData() {
			return data;
		}

		public int getDivisor() {
			return divisor;
		}

		public int getRows() {
			return rows;
		}

		public int getCols() {
			return cols;
		}

		private boolean parseText(String text, boolean normalize) {
			String[] lines = text.split("\n");

			boolean assign = true;
			int cols = 0, rows = 0;

			// parse size
			for (String line : lines) {
				line = line.trim();
				// ignore blank lines
				if (line.isEmpty()) {
					continue;
				}

				// parse tokens
				StringTokenizer st = new StringTokenizer(line);
				int temp = st.countTokens();

				if (assign) {
					cols = temp;
					assign = false;
				} else {
					// check size of each row
					if (temp != cols) {
						return false;
					}
				}

				rows++;
			}

			// empty
			if (rows == 0 || cols == 0) {
				return false;
			}

			// matrix data
			this.data = new int[rows][cols];

			StringTokenizer st = new StringTokenizer(text);
			int r = 0, c = 0;

			while (st.hasMoreTokens()) {
				int n;
				try {
					n = Integer.parseInt(st.nextToken());
				} catch (NumberFormatException e) {
					n = 0;
				}

				// new row
				if (c % cols == 0 && c > 0) {
					r++;
					c = 0;
				}
				this.data[r][c++] = n;
			}

			// matrix size
			setSize(rows, cols);

			// should normalize?
			if (normalize) {
				this.divisor = normalize();
			} else {
				// divisor
				String divisor = txtDivisor.getText();
				try {
					int n = Integer.parseInt(divisor);
					if (n < 1) {
						n = 1;
					}
					this.divisor = n;
				} catch (NumberFormatException e) {
					this.divisor = 1;
				}
			}

			return true;
		}

		private int normalize() {
			int sum = 0;
			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < cols; y++) {
					sum += data[x][y];
				}
			}

			return sum > 0 ? sum : 1;
		}

		private void setSize(int rows, int cols) {
			this.rows = rows;
			this.cols = cols;
		}
	}
}
