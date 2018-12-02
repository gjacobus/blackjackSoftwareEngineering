package clientCommunication;

import java.awt.*;
import javax.swing.*;

import clientUI.LoginPanel;

import java.awt.event.*;
import java.io.IOException;

public class LoginControl implements ActionListener
{
	// Private data fields for the container and chat client.
	private JPanel container;
	private GameClient client;
	private JFrame frame;

	// Constructor for the login controller.
	public LoginControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;

		try
		{
			client.openConnection();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void setFrameSize(int width, int height) {
		this.frame = (JFrame) SwingUtilities.windowForComponent(container);
		frame.setSize(width, height);
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Cancel button takes the user back to the initial panel.
		if (command == "Cancel")
		{
			CardLayout cardLayout = (CardLayout) container.getLayout();
			cardLayout.show(container, "1");
		}

		// The Submit button submits the login information to the server.
		else if (command == "Submit")
		{
			// Get the username and password the user entered.
			LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
			LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());

			// Check the validity of the information locally first.
			if (data.getUsername().equals("") || data.getPassword().equals(""))
			{
				displayError("You must enter a username and password.");
				return;
			}

			// Submit the login information to the server.
			try
			{
				client.sendToServer(data);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// After the login is successful, set the User object and display the contacts
	// screen. - this method would be invoked by
	// the ChatClient
	public void loginSuccess()
	{
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, "4");
	}

	// Method that displays a message in the error - could be invoked by ChatClient
	// or by this class (see above)
	public void displayError(String error)
	{
		LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
		loginPanel.setError(error);

	}
}
