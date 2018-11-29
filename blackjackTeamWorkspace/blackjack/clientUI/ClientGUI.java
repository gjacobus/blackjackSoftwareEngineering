package clientUI;

import javax.swing.*;

import clientCommunication.CreateAccountControl;
import clientCommunication.GameClient;
import clientCommunication.InitialControl;
import clientCommunication.LoginControl;
import clientCommunication.StartGameControl;
import gameplay.BetControl;
import gameplay.GameControl;

import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{
 
  
  // Constructor that creates the client GUI.
  public ClientGUI()
  {
    // Set the title and default close operation.
    this.setTitle("Client");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    // Create the card layout container.
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);
    
    //Create the Controllers next
    //Next, create the Controllers
    InitialControl ic = new InitialControl(container); 
    GameClient client = new GameClient();
    LoginControl lc = new LoginControl(container, client); //Probably will want to pass in ChatClient here
    CreateAccountControl cac = new CreateAccountControl(container, client);
    StartGameControl sgc = new StartGameControl();
    GameControl gc = new GameControl(container, client);
    BetControl bc = new BetControl(container, client);
    client.setCreateAccountControl(cac);
    client.setLoginControl(lc);
    client.setGameControl(gc);
    
    // Create the four views. (need the controller to register with the Panels
    JPanel view1 = new InitialPanel(ic);
    JPanel view2 = new LoginPanel(lc);
    JPanel view3 = new CreateAccountPanel(cac);
    JPanel view4 = new StartGamePanel(sgc);
    JPanel view5 = new BetPanel(bc);
    JPanel view6 = new GamePanel(gc);
    
    
    // Add the views to the card layout container.
    container.add(view1, "1");
    container.add(view2, "2");
    container.add(view3, "3");
    container.add(view4, "4");
    container.add(view5, "5");
    container.add(view6, "6");
   
    
    // Show the initial view in the card layout.
    cardLayout.show(container, "5");
    
    // Add the card layout container to the JFrame.
    this.add(container, BorderLayout.CENTER);

    // Show the JFrame.
    this.setSize(1000, 750);
    this.setVisible(true);
  }

  // Main function that creates the client GUI when the program is started.
  public static void main(String[] args)
  {
    new ClientGUI();
  }
}
