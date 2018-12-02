package clientCommunication;

import static org.junit.Assert.*;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.*;

import org.junit.Before;
import org.junit.Test;

import clientUI.CreateAccountPanel;
import database.Database;
import serverCommunication.GameServer;
import serverUI.ServerGUI;

public class TestCreateAccount {
	GameClient client;
	CreateAccountPanel cap;
	CreateAccountControl cac;
	Database db;
	ServerGUI server;

	@Before
	public void setUp() {
		JPanel container = new JPanel(new CardLayout());
		server = new ServerGUI("server");
		client = new GameClient();
		JPanel view1 = new JPanel();
		JPanel view2 = new JPanel();
		cac = new CreateAccountControl(container, client);
		cap = new CreateAccountPanel(cac);
		container.add(view1, "1");
	    container.add(view2, "2");
	    container.add(cap, "3");
		
		db = new Database();
	}

	@Test
	public void testSubmit() {
		JButton submitBtn = null;
		JTextField usernameField = null;
		JTextField passwordField = null;
		JTextField verifyPasswordField = null;
		for (int i = 0; i < cap.getComponentCount(); i++) {
			Component c = cap.getComponent(i);
			for (int j = 0; j < ((JPanel) c).getComponentCount(); j++) {
				Component c2 = ((JPanel) c).getComponent(j);
				for (int k = 0; k < ((JPanel) c2).getComponentCount(); k++) {
					Component c3 = ((JPanel)c2).getComponent(k);
					if (c3 instanceof JButton && (((JButton) c3).getText()).equals("Submit")) {
						submitBtn = (JButton) c3;
					} else if (c3 instanceof JLabel && (((JLabel) c3).getText()).equals("Username:")) {
						usernameField = (JTextField) ((JPanel)c2).getComponent(k+1);
					} else if (c3 instanceof JLabel && (((JLabel) c3).getText()).equals("Password:")) {
						passwordField = (JTextField) (((JPanel) c2).getComponent(k + 1));
					} else if (c3 instanceof JLabel && (((JLabel) c3).getText()).equals("Verify Password:")) {
						verifyPasswordField = (JTextField) (((JPanel) c2).getComponent(k + 1));
					}
				}
			}
		}

		usernameField.setText("newName");
		passwordField.setText("newPassword");
		verifyPasswordField.setText("newPassword");
		
		ArrayList<String> result = db.query("select * from blackjackdata where username='newName'");
		if (result.size() != 0) {
			fail("Account already exists");
		}
		
		submitBtn.doClick();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = db.query("select * from blackjackdata where username='newName'");


		boolean testResult;
		if (result.size() == 0) {
			testResult = false;
		} else {
			testResult = true;
		}
		assertTrue("Unsuccessful", testResult);
	}

}
