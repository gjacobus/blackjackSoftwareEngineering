package clientUI;

import java.awt.*;
import javax.swing.*;

import gameplay.GameControl;

//Author mdodd
public class GamePanel extends JPanel
{	
	// Private data fields for the important GUI components.
	  private JTextField usernameField;
	  private JLabel errorLabel;
	  private Color background = new Color(10, 100, 35);
	  private JPanel dealerCards = new JPanel();
	  private JPanel user1Cards = new JPanel();
	  private JPanel user2Cards = new JPanel();
	  private JPanel user3Cards = new JPanel();
	  private JPanel user4Cards = new JPanel();
	  private int maxCardNum = 0;
	  private int cardBuffer = 400;
	  private JLabel dealerScore;
	  private JLabel userScore;
	  private JLabel betAmt;
	  private JPanel box;
	  
	  // Getter for the text in the username field.
	  public String getUsername()
	  {
	    return usernameField.getText();
	  }
	  
	  public String getBetAmount()
	  {
		  return betAmt.getText();
	  }
	  
	  public void setBetAmount(int amount)
	  {
		  betAmt.setText("Bet: " + amount);
	  }
	  
	  // Setter for the error text.
	  public void setError(String error)
	  {
		  if(error.equals(""))
		  {
			  errorLabel.setVisible(false);
		  }
		  else
		  {
			  errorLabel.setVisible(true);
			  errorLabel.setText(error);
		  }

	  }
	  
	  // Constructor for the Game panel initial.
	  public GamePanel(GameControl gc)
	  {
		dealerCards.setLayout(new BoxLayout(dealerCards, BoxLayout.X_AXIS));
		dealerCards.setBackground(background);
		user1Cards.setLayout(new BoxLayout(user1Cards, BoxLayout.Y_AXIS));
		user1Cards.setBackground(background);
		user2Cards.setLayout(new BoxLayout(user2Cards, BoxLayout.Y_AXIS));
		user2Cards.setBackground(background);
		user3Cards.setLayout(new BoxLayout(user3Cards, BoxLayout.Y_AXIS));
		user3Cards.setBackground(background);
		user4Cards.setLayout(new BoxLayout(user4Cards, BoxLayout.Y_AXIS));
		user4Cards.setBackground(background);
	        
	    JPanel dealerPanel = new JPanel(new GridLayout(1, 1, 0, 0));
	    dealerPanel.setBackground(background);
	    dealerScore = new JLabel("Dealer Score: 0");
	    dealerScore.setForeground(Color.YELLOW);
	    dealerCards.add(dealerScore);
	    dealerCards.add(Box.createRigidArea(new Dimension(60, 0)));
	    dealerPanel.add(dealerCards);
	    
	    
	    JPanel errorPanel = new JPanel(new GridLayout(1, 1, 5, 5));
	    errorPanel.setBackground(background);
	    errorLabel = new JLabel("Not visible", JLabel.CENTER);
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
	    userCardPanel.add(user1Cards);
	    userCardPanel.add(user2Cards);
	    userCardPanel.add(user3Cards);
	    userCardPanel.add(user4Cards);
	    
	    JPanel infoAndButtonsPanel = new JPanel(new GridLayout(1, 4, 70, 10));
	    infoAndButtonsPanel.setBackground(background);
	    betAmt = new JLabel("Bet: 20");
	    betAmt.setForeground(Color.YELLOW);
	    JButton hitButton = new JButton("Hit");
	    hitButton.addActionListener(gc);
	    JButton stayButton = new JButton("Stay");
	    stayButton.addActionListener(gc);
	    JLabel balAmt = new JLabel("Balance: ");
	    balAmt.setForeground(Color.YELLOW);
	    infoAndButtonsPanel.add(betAmt);
	    infoAndButtonsPanel.add(hitButton);
	    infoAndButtonsPanel.add(stayButton);
	    infoAndButtonsPanel.add(balAmt);
	    
	    JPanel userScorePanel = new JPanel(new GridLayout(1, 1, 0, 0));
	    userScorePanel.setBackground(background);
	    userScore = new JLabel("Current Score: 0");
	    userScore.setForeground(Color.YELLOW);
	    userScorePanel.add(userScore);
	    
	    box = new JPanel();
	    box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
	    
	    box.add(dealerPanel);
	    box.add(Box.createRigidArea(new Dimension(0, 60)));
	    
	    box.add(errorPanel);
	    box.add(Box.createRigidArea(new Dimension(0, 10)));
	    
	    box.add(namePanel);
	    box.add(Box.createRigidArea(new Dimension(0, 5)));
	    
	    box.add(userCardPanel);
	    Component cardBufferBox = Box.createRigidArea(new Dimension(0, 50));
	    box.add(cardBufferBox);
	    
	    box.add(infoAndButtonsPanel);
	    box.add(Box.createRigidArea(new Dimension(0, 10)));
	    
	    box.add(userScorePanel);
	    
	    box.setBackground(background);
	    this.add(box);
	    this.setBackground(background);
	  }
	  
	  public void updateDealerScore(int score)
	  {
		  dealerScore.setText("DealerScore: " + score);
	  }
	  
	  public String getDealerScore()
	  {
		  return dealerScore.getText();
	  }
	  
	  public void addDealerCards(String cardPath)
	  {
		  JLabel newJLabel = new JLabel("", new ImageIcon(this.getClass().getResource(cardPath)), JLabel.CENTER);
		  dealerCards.add(newJLabel);
		  this.repaint();
	  }
	  
	  private void resetDealerCards()
	  {
		  dealerCards.removeAll();
		  dealerCards.add(dealerScore);
		  dealerCards.add(Box.createRigidArea(new Dimension(60, 0)));
		  this.repaint();
	  }
	  
	  public void addUserCards(int chairNum, String cardPath)
	  {
		  JLabel newJLabel = new JLabel("", new ImageIcon(this.getClass().getResource(cardPath)), JLabel.CENTER);
		  switch (chairNum)
		  {
			  case 0:
			  {
				  user1Cards.add(newJLabel);
				  if(user1Cards.getComponentCount() > maxCardNum)
				  {
					  maxCardNum = user1Cards.getComponentCount();
				  }
				  break;
			  }
			  case 1:
			  {
				  user2Cards.add(newJLabel);
				  if(user2Cards.getComponentCount() > maxCardNum)
				  {
					  maxCardNum = user2Cards.getComponentCount();
				  }
				  break;
			  }
			  case 2:
			  {
				  user3Cards.add(newJLabel);
				  if(user3Cards.getComponentCount() > maxCardNum)
				  {
					  maxCardNum = user3Cards.getComponentCount();
				  }
				  break;
			  }
			  case 3:
			  {
				  user4Cards.add(newJLabel);
				  if(user4Cards.getComponentCount() > maxCardNum)
				  {
					  maxCardNum = user4Cards.getComponentCount();
				  }
				  break;
			  }
		  }
		  cardBuffer = 400 - (100 * maxCardNum);
		  System.out.println(box.getComponent(7).getSize());
		  box.getComponent(7).invalidate();
		  box.getComponent(7).setSize(0, cardBuffer);
		  box.getComponent(7).validate();
		  System.out.println(box.getComponent(7).getSize());
		  this.repaint();
	  }
	  
	  public void updateUserScore(int score)
	  {
		  userScore.setText("Current Score: " + score);
	  }
	  
	  public String getUserScore()
	  {
		  return userScore.getText();
	  }
	  
	  private void resetUserCards()
	  {
		  user1Cards.removeAll();
		  user2Cards.removeAll();
		  user3Cards.removeAll();
		  user4Cards.removeAll();
	  }
	  
	  private void resetText()
	  {
		  userScore.setText("Current Score: 0");
		  dealerScore.setText("DealerScore: 0");
		  setError("");
		  
	  }
	  
	  public void resetGame()
	  {
		  resetText();
		  resetUserCards();
		  resetDealerCards();
	  }
}
