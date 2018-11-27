package gameplay;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import clientCommunication.GameClient;
import clientCommunication.LoginData;
import clientUI.GamePanel;
import clientUI.LoginPanel;

import javax.swing.JPanel;

public class GameControl implements ActionListener
{
	// Private data fields for the container and chat client.
	  private JPanel container;
	  private GameClient game;
	  private ArrayList<String> users  = new ArrayList<String>(4);
	  private int currentChair;
	  private boolean canPlay = true;
	  private boolean busted;
	  
	  
	  // Constructor for the Game controller.
	  public GameControl(JPanel container, GameClient game)
	  {
	    this.container = container;
	    this.currentChair = 0;
	    this.game = game; 
	    users.add("default");
	    users.add("default");
	    users.add("default");
	    users.add("default");
	  }
	  
	  public void chairIncrease()
	  {
		  currentChair += 1;
	  }
	  
	  // Handle button clicks.
	  public void actionPerformed(ActionEvent ae)
	  {
	    // Get the name of the button clicked.
	    String command = ae.getActionCommand();

	    if(canPlay)
	    {
		    if (command == "Hit")
		    {
		    	try {
					game.sendToServer("nextCard");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    else if (command == "Stay")
		    {
		    	try {
					game.sendToServer("Stay" + currentChair);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
	    }
	  }
	  
	  public void canPlay(boolean play)
	  {
		  this.canPlay = play;
	  }
	  
	  public void startGame(String message)
	  {
		  CardLayout cardLayout = (CardLayout) container.getLayout();
		  cardLayout.show(container, "6");
		  
		  String[] temp = message.split(",");
		  if(temp.length >= 2)
		  {
		    GamePanel gamePanel = (GamePanel) container.getComponent(5);
		    gamePanel.updateDealerScore(0);
		    gamePanel.updateUserScore(0);
		  }

	  }
	  
	  public void updateGame(String message)
	  {
		  String[] temp = message.split("=");
		  if(message.contains("nextCard"))
		  {
			  String cardPath = "/Card_Images/";
			  String type = "H";
			  int cardNum = Integer.parseInt(temp[1]);
			  if(cardNum <= 12)
			  {
				  type = "H";
			  }
			  else if(cardNum <= 25)
			  {
				  type = "S";
			  }
			  else if(cardNum <= 38)
			  {
				  type = "C";
			  }
			  else if(cardNum <= 51)
			  {
				  type = "D";
			  }
			  cardNum = cardNum % 13 + 1;
			  String num = Integer.toString(cardNum);
			  cardPath += num + type + ".png";
			  GamePanel gamePanel = (GamePanel) container.getComponent(5);
			  gamePanel.addUserCards(currentChair, cardPath);
			  if(canPlay)
			  {
				  String tempScore = gamePanel.getUserScore();
				  String[] array = tempScore.split(":");
				  int newScore = Integer.parseInt(array[1].trim());
				  if(cardNum > 10)
				  {
					  cardNum = 10;
				  }
				  else if(cardNum == 1)
				  {
					  if(newScore + cardNum > 21)
					  {
						  cardNum = 1;
					  }
					  else
					  {
						  cardNum = 11;
					  }
				  }
				  newScore += cardNum;
				  gamePanel.updateUserScore(newScore);
				  if(newScore > 21)
				  {
					  gamePanel.setError("You Busted");
					  this.canPlay = false;
					  this.busted = true;
				    	try {
							game.sendToServer("Stay" + currentChair);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				  }
			  }
		  }
		  if(message.contains("DealerMove"))
		  {
			  System.out.println(message);
			  String cardPath = "/Card_Images/";
			  String type = "H";
			  int cardNum = Integer.parseInt(temp[1]);
			  if(cardNum <= 12)
			  {
				  type = "H";
			  }
			  else if(cardNum <= 25)
			  {
				  type = "S";
			  }
			  else if(cardNum <= 38)
			  {
				  type = "C";
			  }
			  else if(cardNum <= 51)
			  {
				  type = "D";
			  }
			  cardNum = cardNum % 13 + 1;
			  String num = Integer.toString(cardNum);
			  cardPath += num + type + ".png";
			  GamePanel gamePanel = (GamePanel) container.getComponent(5);
			  gamePanel.addDealerCards(cardPath);
			  String tempScore = gamePanel.getDealerScore();
			  String[] array = tempScore.split(":");
			  int newScore = Integer.parseInt(array[1].trim());
			  if(cardNum > 10)
			  {
				  cardNum = 10;
			  }
			  else if(cardNum == 1)
			  {
				  if(newScore + cardNum > 21)
				  {
					  cardNum = 1;
				  }
				  else
				  {
					  cardNum = 11;
				  }
			  }
			  newScore += cardNum;
			  gamePanel.updateDealerScore(newScore);
			  if(newScore > 21)
			  {
				  gamePanel.setError("Dealer Busted");
			    	try {
						game.sendToServer("DealerDone");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  }
			  else if(newScore <= 16)
			  {
				  try {
					game.sendToServer("DealerMove");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  else if(newScore >= 17)
			  {
				  try {
					game.sendToServer("DealerDone");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			 
		  }
		  System.out.println("UPDATE GAME");

	  }
	  
	  public void addUser(String username)
	  {

		Iterator userIt = users.iterator();
			  
		while(userIt.hasNext())
		{
			String temp = userIt.next().toString();
			if(temp.contains("default"))
			{
				temp = username;
				break;
			}
			if(!userIt.hasNext())
			{
				System.out.println("Game is full, wait for a member to leave");
			}
		}
	  }
	  
	  public void removeUser(String username)
	  {
		 Iterator userIt = users.iterator();
		 int i = 0;
		 
		 while(userIt.hasNext())
		 {
			 String temp = userIt.next().toString();
			 System.out.println(temp.toString());
			 if(temp.toString().equals(username))
			 {
				 temp = "User has Left";
				 temp = "username" + i;
				 break;
			 }
			 i++;
		 }
	  }
	  
	  public void checkResults()
	  {
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  String dealer = gamePanel.getDealerScore();
		  String[] array = dealer.split(":");
		  int dealerScore = Integer.parseInt(array[1].trim());
		  String user = gamePanel.getUserScore();
		  array = user.split(":");
		  int userScore = Integer.parseInt(array[1].trim());
		  
		  String bet = gamePanel.getBetAmount();
		  array = bet.split(":");
		  int betAmount = Integer.parseInt(array[1].trim());
		  
		  System.out.println(dealerScore + " " + userScore);
		  if(this.busted)
		  {
			  displayError("You busted by going over 21, you lose $" + betAmount);
		  }
		  else
		  {
			  if(dealerScore > 21)
			  {
				  displayError("You Won, the dealer busted, you win double $" + betAmount);
			  }
		      else if(dealerScore > userScore)
			  {
				  displayError("Dealer Won, you lose $" + betAmount);
			  }
			  else if(dealerScore == userScore)
			  {
				  displayError("You tied, you keep $" + betAmount);
			  }
			  else if(userScore > dealerScore)
			  {
				  displayError("You Won, you win double $" + betAmount);
			  }
		  }
	  }

	  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
	  public void displayError(String error)
	  {
	    GamePanel GamePanel = (GamePanel)container.getComponent(5);
	    GamePanel.setError(error);
	  }
}

