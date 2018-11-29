import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class BetGuiPopupWindow extends JPanel {
	 private JTextField textField;
	 private JTextField textField_1;
	public BetGuiPopupWindow()
	{
			setLayout(null);
		
			JPanel inner = new JPanel( );
			inner.setBounds(24, 14, 1, 1);
			
			inner.setLayout(null);
			this.add(inner);
		
		JLabel lblPlaceYourBets = new JLabel("Place Your Bet");
		lblPlaceYourBets.setBounds(183, 14, 69, 14);
		add(lblPlaceYourBets);
		
		textField = new JTextField();
		textField.setBounds(183, 110, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(183, 151, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblAccountBalance = new JLabel("Account Balance");
		lblAccountBalance.setBounds(183, 85, 79, 14);
		add(lblAccountBalance);
		
		JLabel lblBetAmount = new JLabel("Bet Amount");
		lblBetAmount.setBounds(183, 133, 56, 14);
		add(lblBetAmount);
		
		JButton btnNewButton_1 = new JButton("$20");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_1.setBounds(270, 192, 51, 23);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("$100");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_2.setBounds(335, 192, 57, 23);
		add(btnNewButton_2);
		
		JButton button = new JButton("$!");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(85, 192, 43, 23);
		add(button);
		
		JButton button_1 = new JButton("$5");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_1.setBounds(145, 192, 45, 23);
		add(button_1);
		
		JButton btnNewButton = new JButton("$10");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(205, 192, 51, 23);
		add(btnNewButton);
		
		JButton btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCheck.setBounds(85, 227, 61, 23);
		add(btnCheck);
		
		JButton btnFold = new JButton("Fold");
		btnFold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnFold.setBounds(199, 227, 53, 23);
		add(btnFold);
		
		JButton btnWatchHand = new JButton("Watch Hand");
		btnWatchHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnWatchHand.setBounds(286, 227, 91, 23);
		add(btnWatchHand);
	}
	public static void main(String[] args)
	  {
	    new BetGuiPopupWindow();
	  }
}
