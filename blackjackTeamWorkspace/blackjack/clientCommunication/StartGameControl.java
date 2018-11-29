package clientCommunication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import clientUI.GamePanel;
import clientUI.StartGamePanel;

public class StartGameControl implements ActionListener {
	JPanel container;
	GameClient client;
	
	public StartGameControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
	}
	
	public void gameFound() {
		
	}
	
	public void updateUsername() {
		StartGamePanel sgp = (StartGamePanel) container.getComponent(3);
		JLabel usernameField = sgp.getUsername();
		System.out.println(client.getUsername());
		usernameField.setText("Username: " + client.getUsername());
	}
	
	public void updateBalance() {
		StartGamePanel sgp = (StartGamePanel) container.getComponent(3);
		JLabel balanceLabel = sgp.getBalance();
		System.out.println(client.getBalance());
		balanceLabel.setText("Balance: $" + client.getBalance());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = e.getActionCommand();
	}

}
