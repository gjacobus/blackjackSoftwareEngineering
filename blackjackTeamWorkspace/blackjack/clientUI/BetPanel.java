//package clientUI;
//
//import javax.swing.*;
//
//import gameplay.BetControl;
//
//public class BetPanel extends JPanel {
//
//	public BetPanel(BetControl bc)
//	{
//		
//	}
//}
package clientUI;

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

import gameplay.BetControl;

public class BetPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JLabel errorLabel;

	public BetPanel(BetControl bc) {
		setLayout(null);

		JPanel inner = new JPanel();
		inner.setBounds(24, 14, 1, 1);

		inner.setLayout(null);
		this.add(inner);

		JLabel lblPlaceYourBets = new JLabel("Place Your Bet");
		lblPlaceYourBets.setBounds(183, 14, 200, 14);
		add(lblPlaceYourBets);
		
		errorLabel  = new JLabel("Error");
		errorLabel.setBounds(183, 50, 500, 50);
		errorLabel.setForeground(Color.red);
		errorLabel.setVisible(false);
		add(errorLabel);

		textField = new JTextField();
		textField.setBounds(183, 110, 200, 20);
		textField.setEditable(false);
		textField.setText("$0");
		add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(183, 151, 200, 20);
		textField_1.setText("$0");
		textField_1.setEditable(false);
		add(textField_1);
		textField_1.setColumns(10);

		JLabel lblAccountBalance = new JLabel("Account Balance");
		lblAccountBalance.setBounds(183, 85, 200, 14);
		add(lblAccountBalance);

		JLabel lblBetAmount = new JLabel("Bet Amount");
		lblBetAmount.setBounds(183, 133, 200, 14);
		add(lblBetAmount);

		JButton button = new JButton("$1");
		button.addActionListener(bc);
		button.setBounds(85, 192, 75, 23);
		add(button);

		JButton button_1 = new JButton("$5");
		button_1.addActionListener(bc);
		button_1.setBounds(160, 192, 75, 23);
		add(button_1);

		JButton btnNewButton = new JButton("$10");
		btnNewButton.addActionListener(bc);
		btnNewButton.setBounds(235, 192, 75, 23);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("$20");
		btnNewButton_1.addActionListener(bc);
		btnNewButton_1.setBounds(305, 192, 75, 23);
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("$100");
		btnNewButton_2.addActionListener(bc);
		btnNewButton_2.setBounds(380, 192, 75, 23);
		add(btnNewButton_2);

		JButton btnPlaceBet = new JButton("Place Bet");
		btnPlaceBet.addActionListener(bc);
		btnPlaceBet.setBounds(200, 227, 150, 23);
		add(btnPlaceBet);
		
		JButton btnAddMoney = new JButton("Add Money");
		btnAddMoney.addActionListener(bc);
		btnAddMoney.setBounds(200, 260, 150, 23);
		add(btnAddMoney);
		
//		JLabel timerLabel = new JLabel("Timer: ");
//		add(timerLabel);
	}

	public double getBet() {
		double toReturn = Double.parseDouble(textField_1.getText().substring(1));
		return toReturn;
	}
	
	public void displayError(String message)
	{
		if(message.equals(""))
		{
			errorLabel.setVisible(false);
		}
		else
		{
			errorLabel.setVisible(true);
			errorLabel.setText(message);
		}
	}

	public JTextField getBetTextField() {
		return textField_1;
	}
	public JTextField getBalanceTextField() {
		return textField;
	}

}
