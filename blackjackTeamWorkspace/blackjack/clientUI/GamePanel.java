package clientUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import gameplay.GameControl;

//Author Mdodd
public class GamePanel extends JPanel
{	
	// Private data fields for the important GUI components.
	  private JTextField usernameField;
	  private JPasswordField passwordField;
	  private JLabel errorLabel;
	  private Color background = new Color(10, 100, 35);
	  private JPanel dealerCards = new JPanel();
	  private JPanel user1Cards = new JPanel();
	  private JPanel user2Cards = new JPanel();
	  private JPanel user3Cards = new JPanel();
	  private JPanel user4Cards = new JPanel();
	  
	  // Getter for the text in the username field.
	  public String getUsername()
	  {
	    return usernameField.getText();
	  }
	  
	  // Setter for the error text.
	  public void setError(String error)
	  {
	    errorLabel.setText(error);
	  }
	  
	  // Constructor for the login panel.
	  public GamePanel(GameControl gc)
	  {
		dealerCards.setLayout(new BoxLayout(dealerCards, BoxLayout.X_AXIS));
		dealerCards.setBackground(background);
		user1Cards.setLayout(new BoxLayout(user1Cards, BoxLayout.Y_AXIS));
		user2Cards.setLayout(new BoxLayout(user2Cards, BoxLayout.Y_AXIS));
		user3Cards.setLayout(new BoxLayout(user3Cards, BoxLayout.Y_AXIS));
		user4Cards.setLayout(new BoxLayout(user4Cards, BoxLayout.Y_AXIS));
		Image cards[] = new Image[52];
	        
	    JPanel dealerPanel = new JPanel(new GridLayout(1, 1, 5, 5));
	    dealerPanel.setBackground(background);
	    JLabel dealerScoreLabel = new JLabel("Dealer Score:");
	    dealerScoreLabel.setForeground(Color.YELLOW);
	    //dealerCards.add(new JLabel("", new ImageIcon("/Card_Images/10C.png"), JLabel.CENTER));
	    dealerPanel.add(dealerScoreLabel);
	    dealerPanel.add(dealerCards);
	    
	    
	    JPanel errorPanel = new JPanel(new GridLayout(1, 1, 5, 5));
	    errorPanel.setBackground(background);
	    JLabel errorLabel = new JLabel("Error:", JLabel.CENTER);
	    errorLabel.setForeground(Color.red);
	    errorLabel.setVisible(false);
	    errorPanel.add(errorLabel);
	    
	    
	    
	    JPanel namePanel = new JPanel(new GridLayout(1, 4, 70, 10));
	    namePanel.setBackground(background);
	    JLabel name1 = new JLabel("User1");
	    name1.setForeground(Color.YELLOW);
	    JLabel name2 = new JLabel("User2");
	    name2.setForeground(Color.YELLOW);
	    JLabel name3 = new JLabel("User3");
	    name3.setForeground(Color.YELLOW);
	    JLabel name4 = new JLabel("User4");
	    name4.setForeground(Color.YELLOW);
	    namePanel.add(name1);
	    namePanel.add(name2);
	    namePanel.add(name3);
	    namePanel.add(name4);
	    
	    
	    JPanel userCardPanel = new JPanel(new GridLayout(1, 4, 70, 0));
	    userCardPanel.setBackground(background);
	    JLabel user1Card = new JLabel("", new ImageIcon(this.getClass().getResource("/Card_Images/10C.png")), JLabel.CENTER);
	    user1Cards.add(user1Card);
	    //user1Cards.setLayout(new BoxLayout(user1Cards, BoxLayout.Y_AXIS));
	    JLabel user2Card = new JLabel("", new ImageIcon(this.getClass().getResource("/Card_Images/10C.png")), JLabel.CENTER);
	    user2Cards.add(user2Card);
	    //user2Cards.setLayout(new BoxLayout(user2Cards, BoxLayout.Y_AXIS));
	    JLabel user3Card = new JLabel("", new ImageIcon(this.getClass().getResource("/Card_Images/10C.png")), JLabel.CENTER);
	    user3Cards.add(user3Card);
	    //user3Cards.setLayout(new BoxLayout(user3Cards, BoxLayout.Y_AXIS));
	    JLabel user4Card = new JLabel("", new ImageIcon(this.getClass().getResource("/Card_Images/10C.png")), JLabel.CENTER);
	    user4Cards.add(user4Card);
	    //user4Cards.setLayout(new BoxLayout(user4Cards, BoxLayout.Y_AXIS));
	    userCardPanel.add(user1Cards);
	    userCardPanel.add(user2Cards);
	    userCardPanel.add(user3Cards);
	    userCardPanel.add(user4Cards);
	    
	    JPanel infoAndButtonsPanel = new JPanel(new GridLayout(1, 4, 70, 10));
	    infoAndButtonsPanel.setBackground(background);
	    JLabel betAmt = new JLabel("Bet: ");
	    betAmt.setForeground(Color.YELLOW);
	    JButton hitButton = new JButton("Hit");
	    JButton stayButton = new JButton("Stay");
	    JLabel balAmt = new JLabel("Balance: ");
	    balAmt.setForeground(Color.YELLOW);
	    infoAndButtonsPanel.add(betAmt);
	    infoAndButtonsPanel.add(hitButton);
	    infoAndButtonsPanel.add(stayButton);
	    infoAndButtonsPanel.add(balAmt);
	    
	    JPanel userScorePanel = new JPanel(new GridLayout(1, 1, 0, 0));
	    userScorePanel.setBackground(background);
	    JLabel userScore = new JLabel("Current Score: ");
	    userScore.setForeground(Color.YELLOW);
	    userScorePanel.add(userScore);
	    
	    JPanel box = new JPanel();
	    box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
	    
	    box.add(dealerPanel);
	    box.add(Box.createRigidArea(new Dimension(0, 50)));
	    
	    box.add(errorPanel);
	    box.add(Box.createRigidArea(new Dimension(0, 50)));
	    
	    box.add(namePanel);
	    box.add(Box.createRigidArea(new Dimension(0, 5)));
	    
	    box.add(userCardPanel);
	    box.add(Box.createRigidArea(new Dimension(0, 200)));
	    
	    box.add(infoAndButtonsPanel);
	    box.add(Box.createRigidArea(new Dimension(0, 30)));
	    
	    box.add(userScorePanel);
	    
	    box.setBackground(background);
	    this.add(box);
	    this.setBackground(background);
	  }
}
