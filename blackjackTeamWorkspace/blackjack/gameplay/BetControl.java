package gameplay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import clientCommunication.GameClient;
import clientUI.BetPanel;
import clientUI.CreateAccountPanel;

public class BetControl implements ActionListener{
	private GameClient client;
	private JPanel container;
	
	public BetControl(JPanel container, GameClient client) {
		this.client = client;
		this.container = container;
	}
	
	public void setBet(double bet) {
		
	}
	
	public void setTime(String time) {
		
	}
	
	public void updateBalance() 
	{
		BetPanel betPanel = (BetPanel)container.getComponent(4);
		JTextField betTextField = betPanel.getBalanceTextField();
		betTextField.setText("$" + client.getBalance());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		BetPanel betPanel = (BetPanel)container.getComponent(4); 
		JTextField betTextField = betPanel.getBetTextField();
		
		double oldAmount;
		double newAmount;
		switch (command) {
		case "$1":
			updateBet(1);
			break;
		case "$5":
			updateBet(5);
			break;
		case "$10":
			updateBet(10);
			break;
		case "$20":
			updateBet(20);
			break;
		case "$100":
			updateBet(100);
			break;
		case "Place Bet":
			BetData data = new BetData(client.getUsername(), betPanel.getBet());
			System.out.println(data.toString());
			betPanel.displayError("");
			try {
				client.sendToServer(data);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		}
	}
	
	private void updateBet(double amtToAdd) {
		BetPanel betPanel = (BetPanel)container.getComponent(4); 
		JTextField betTextField = betPanel.getBetTextField();
		double balance = Double.parseDouble(betPanel.getBalanceTextField().getText().substring(1));
		double oldAmount = Double.parseDouble(betTextField.getText().substring(1));
		double newAmount = Math.rint(oldAmount + amtToAdd);
		if (newAmount <= balance) {
			betTextField.setText("$" + Double.toString(newAmount));
		}
	}
	
	public void displayError(String message)
	{
		BetPanel betPanel = (BetPanel)container.getComponent(4); 
		betPanel.displayError("Error:" + message);
	}
	
	public double getBalance() {
		return client.getBalance();
	}
	
	
}
