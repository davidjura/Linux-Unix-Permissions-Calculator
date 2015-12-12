/**
 * Author:	David Jura
 * File:	PermissionsCalc.java
 * Date:	12/11/15
 * Desc:	
 * 			A GUI application tool designed to create
 * 			generate UNIX file permissions in octal
 * 			and symbolic format to help students in
 * 			CIS-1450
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

public class PermissionsCalc extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PermissionsCalc frame = new PermissionsCalc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Generate octal notation permissions based on the selected swing radio
	 * buttons
	 * 
	 * @param owner
	 *            Selected owner JRadioButtons
	 * @param group
	 *            Selected group JRadioButtons
	 * @param other
	 *            Selected other JRadioButtons
	 * @return The calculated octal notation permissions
	 */
	static String calcOctalPermissions(JRadioButton owner[], JRadioButton group[], JRadioButton other[]) {
		int perOwner = 0;
		int perGroup = 0;
		int perOther = 0;

		// Calculate Owner permissions
		if (owner[0].isSelected())
			perOwner += 4;
		if (owner[1].isSelected())
			perOwner += 2;
		if (owner[2].isSelected())
			perOwner += 1;

		// Calculate Group permissions
		if (group[0].isSelected())
			perGroup += 4;
		if (group[1].isSelected())
			perGroup += 2;
		if (group[2].isSelected())
			perGroup += 1;

		// Calculate Other permissions
		if (other[0].isSelected())
			perOther += 4;
		if (other[1].isSelected())
			perOther += 2;
		if (other[2].isSelected())
			perOther += 1;

		String octal = String.valueOf(perOwner) + String.valueOf(perGroup) + String.valueOf(perOther);
		return octal;
	}

	/**
	 * Generate symbolic permissions based on given octal value
	 * 
	 * @param octal
	 *            The octal value required to generate symbolic notation
	 *            permissions
	 * @return The generated symbolic notation permissions
	 */
	static String calcSymbolicPermissions(String octal) {
		String symbArr[] = { "0", "x", "w", "wx", "r", "rx", "rw", "rwx" };
		String symb = "";
		String symbConn[] = new String[3];

		char[] octalArr = octal.toCharArray();

		symbConn[0] = symbArr[Integer.parseInt(String.valueOf(octalArr[0]))];
		symbConn[1] = symbArr[Integer.parseInt(String.valueOf(octalArr[1]))];
		symbConn[2] = symbArr[Integer.parseInt(String.valueOf(octalArr[2]))];

		if (symbConn[0] == "0" && symbConn[1] == "0" && symbConn[2] == "0")
			return "";

		if (symbConn[0] == symbConn[1] && symbConn[1] == symbConn[2]) {
			symb = "a+" + symbConn[0];
			return symb;
		}

		if (symbConn[0] == symbConn[1]) {
			if (symbConn[0] != "0")
				symb += "ug+" + symbConn[0] + ",";
			if (symbConn[2] != "0")
				symb += "o+" + symbConn[2];
		} else if (symbConn[0] == symbConn[2]) {
			if (symbConn[2] != "0")
				symb += "uo+" + symbConn[0] + ",";
			if (symbConn[1] != "0")
				symb += "g+" + symbConn[1];
		} else if (symbConn[1] == symbConn[2]) {
			if (symbConn[1] != "0")
				symb += "go+" + symbConn[1] + ",";
			if (symbConn[0] != "0")
				symb += "u+" + symbConn[0];
		} else {
			if (symbConn[0] != "0")
				symb += "u+" + symbConn[0] + ",";
			if (symbConn[1] != "0")
				symb += "g+" + symbConn[1] + ",";
			if (symbConn[2] != "0")
				symb += "o+" + symbConn[2];
		}

		if (symb.charAt(symb.length() - 1) == ',')
			symb = symb.substring(0, symb.length() - 1);
		return symb;
	}

	/**
	 * Create and setup the GUI interface layout.
	 */
	public PermissionsCalc() {
		setTitle("Permissions Calculator By David Jura");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 434, 344);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);

		JButton btnNewButton = new JButton("Calculate Permissions");
		btnNewButton.setBounds(10, 127, 398, 23);
		layeredPane.add(btnNewButton);

		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(UIManager.getBorder("TextField.border"));
		layeredPane_1.setBounds(10, 11, 126, 105);
		layeredPane_1.setBorder(BorderFactory.createTitledBorder("Owner"));
		layeredPane.add(layeredPane_1);

		JRadioButton rbOwnerRead = new JRadioButton("Read");
		rbOwnerRead.setBounds(6, 18, 114, 23);
		layeredPane_1.add(rbOwnerRead);

		JRadioButton rbOwnerWrite = new JRadioButton("Write");
		rbOwnerWrite.setBounds(6, 44, 114, 23);
		layeredPane_1.add(rbOwnerWrite);

		JRadioButton rbOwnerExecute = new JRadioButton("Execute");
		rbOwnerExecute.setBounds(6, 70, 114, 23);
		layeredPane_1.add(rbOwnerExecute);

		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBorder(UIManager.getBorder("TextField.border"));
		layeredPane_2.setBounds(146, 11, 126, 105);
		layeredPane_2.setBorder(BorderFactory.createTitledBorder("Group"));
		layeredPane.add(layeredPane_2);

		JRadioButton rbGroupRead = new JRadioButton("Read");
		rbGroupRead.setBounds(6, 18, 114, 23);
		layeredPane_2.add(rbGroupRead);

		JRadioButton rbGroupWrite = new JRadioButton("Write");
		rbGroupWrite.setBounds(6, 44, 114, 23);
		layeredPane_2.add(rbGroupWrite);

		JRadioButton rbGroupExecute = new JRadioButton("Execute");
		rbGroupExecute.setBounds(6, 70, 114, 23);
		layeredPane_2.add(rbGroupExecute);

		JLayeredPane layeredPane_3 = new JLayeredPane();
		layeredPane_3.setToolTipText("");
		layeredPane_3.setBorder(UIManager.getBorder("TextField.border"));
		layeredPane_3.setBounds(282, 11, 126, 105);
		layeredPane_3.setBorder(BorderFactory.createTitledBorder("Other"));
		layeredPane.add(layeredPane_3);

		JRadioButton rbOtherRead = new JRadioButton("Read");
		rbOtherRead.setBounds(6, 19, 114, 23);
		layeredPane_3.add(rbOtherRead);

		JRadioButton rbOtherWrite = new JRadioButton("Write");
		rbOtherWrite.setBounds(6, 45, 114, 23);
		layeredPane_3.add(rbOtherWrite);

		JRadioButton rbOtherExecute = new JRadioButton("Execute");
		rbOtherExecute.setBounds(6, 71, 114, 23);
		layeredPane_3.add(rbOtherExecute);

		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("TextField.border"));
		panel.setBounds(10, 161, 398, 62);
		panel.setBorder(BorderFactory.createTitledBorder("Octal Notation"));
		layeredPane.add(panel);

		JLabel lblResult = new JLabel("000");
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblResult);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(UIManager.getBorder("TextField.border"));
		panel_1.setBounds(10, 232, 398, 62);
		panel_1.setBorder(BorderFactory.createTitledBorder("Symbolic Notation"));
		layeredPane.add(panel_1);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel_1.add(label);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton Owners[] = { rbOwnerRead, rbOwnerWrite, rbOwnerExecute };
				JRadioButton Groups[] = { rbGroupRead, rbGroupWrite, rbGroupExecute };
				JRadioButton Others[] = { rbOtherRead, rbOtherWrite, rbOtherExecute };

				String octal = calcOctalPermissions(Owners, Groups, Others);
				lblResult.setText(octal);
				label.setText(calcSymbolicPermissions(octal));
			}
		});
	}
}