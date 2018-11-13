package blackjackPkg;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

public class GameControl implements ActionListener
{
	// Private data fields for the container and chat client.
	  private JPanel container;
	  private GameClient game;
	  
	  
	  // Constructor for the login controller.
	  public GameControl(JPanel container, GameClient game)
	  {
	    this.container = container;
	    
	    this.game = game;  
	  }
	  
	  // Handle button clicks.
	  public void actionPerformed(ActionEvent ae)
	  {
	    // Get the name of the button clicked.
	    String command = ae.getActionCommand();

	    // The Cancel button takes the user back to the initial panel.
	    if (command == "Hit")
	    {
	    	
	    }
	    // The Submit button submits the login information to the server.
	    else if (command == "Stay")
	    {
	     
	    }
	  }

	  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
	  public void displayError(String error)
	  {
	    GamePanel GamePanel = (GamePanel)container.getComponent(1);
	    GamePanel.setError(error);
	  }
}
