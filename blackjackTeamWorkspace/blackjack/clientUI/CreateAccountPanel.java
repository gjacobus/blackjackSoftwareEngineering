package clientUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.*;

import clientCommunication.CreateAccountControl;

public class CreateAccountPanel extends JPanel
{
	  // Private data fields for the important GUI components.
	  private JTextField usernameField;
	  private JPasswordField passwordField;
	  private JPasswordField verifyPasswordField;
	  private JLabel errorLabel;
	  
	  
	  public String getUsername()
	  {
	    return usernameField.getText();
	  }
	  
	  // Getter for the text in the password field.
	  public String getPassword()
	  {
	    return new String(passwordField.getPassword());
	  }
	  
	  public void setError(String error)
	  {
	    errorLabel.setText(error);
	  }
	  
	  public CreateAccountPanel(CreateAccountControl cac) 
	  {
		    // Create the controller and set it in the chat client.
		    //LoginControl controller = new LoginControl(container, client);
		    //client.setLoginControl(controller);
		        
		    // Create a panel for the labels at the top of the GUI.
		    JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		    errorLabel = new JLabel("", JLabel.CENTER);
		    errorLabel.setForeground(Color.RED);
		    JLabel instructionLabel = new JLabel("Enter a username and password to create an account.", JLabel.CENTER);
		    labelPanel.add(errorLabel);
		    labelPanel.add(instructionLabel);

		    // Create a panel for the login information form.
		    JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		    JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
		    usernameField = new JTextField(10);
		    JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
		    passwordField = new JPasswordField(10);
		    JLabel verifyPasswordLabel = new JLabel("Verify Password:", JLabel.RIGHT);
		    verifyPasswordField = new JPasswordField(10);
		    loginPanel.add(usernameLabel);
		    loginPanel.add(usernameField);
		    loginPanel.add(passwordLabel);
		    loginPanel.add(passwordField);
		    loginPanel.add(verifyPasswordLabel);
		    loginPanel.add(verifyPasswordField);
		    
		    
		    // Create a panel for the buttons.
		    JPanel buttonPanel = new JPanel();
		    JButton submitButton = new JButton("Submit");
		    submitButton.addActionListener(cac);
		    JButton cancelButton = new JButton("Cancel");
		    cancelButton.addActionListener(cac);    
		    buttonPanel.add(submitButton);
		    buttonPanel.add(cancelButton);

		    // Arrange the three panels in a grid.
		    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
		    grid.add(labelPanel);
		    grid.add(loginPanel);
		    grid.add(buttonPanel);
		    this.add(grid);
		    
	  }

	public String getVerifyPassword()
	{
		// TODO Auto-generated method stub
		return new String(verifyPasswordField.getPassword());
	}
}
