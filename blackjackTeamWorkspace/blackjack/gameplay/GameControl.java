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
	  private int currentChair = 0;
	  private int dealerSecondCard = 0;
	  private String dealerSecondCardPath = "";
	  private boolean canPlay = false;
	  private boolean busted = false;
	  private boolean dealerInitial = true;
	  private boolean extraDealerCard = false;
	  private boolean extraUserCard = false;
	  private boolean blackJack = false;
	  private boolean ace1 = false;
	  private boolean ace2 = false;
	  private boolean ace3 = false;
	  private boolean ace4 = false;
	  private boolean dealerace1 = false;
	  private boolean dealerace2 = false;
	  private boolean dealerace3 = false;
	  private boolean dealerace4 = false;
	  private int initialCards = 0;
	  
	  
	  // Constructor for the Game controller.
	  public GameControl(JPanel container, GameClient game)
	  {
	    this.container = container;
	    this.currentChair = 0;
	    this.game = game; 
	  }
	  
	  public void chairIncrease()
	  {
		  currentChair += 1;
	  }
	  
	  public void chairReset()
	  {
		  currentChair = 0;
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
		  
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  gamePanel.resetGame();
		  gamePanel.updateDealerScore(0);
		  gamePanel.updateUserScore(0);
		  
		  if(canPlay)
		  {
			  try {
				game.sendToServer("initialCards");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	  }
	  
	  public void updateGame(String message)
	  {
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  gamePanel.updateGame();
		  if(blackJack)
		  {
			  if(canPlay)
			  {
				  try {
					game.sendToServer("Stay" + currentChair);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  return;
			  }
		  }
		  String[] temp = message.split("=");
		  if(message.contains("initialCards"))
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
			  gamePanel.addUserCards(currentChair, cardPath);  
			  initialCards++;
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
					  aceUser();
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
				  if(newScore == 21)
				  {
					  blackJack = true;
				  }
				  try {
					game.sendToServer("initialCards");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		  }
		  else if(message.contains("nextCard"))
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
					  aceUser();
					  if(newScore + 11 > 21)
					  {
						  cardNum = 1;
					  }
					  else
					  {
						  cardNum = 11;
					  }
				  }
				  if(newScore + cardNum > 21 && (ace1 || ace2 || ace3 || ace4))
				  {
					  userAceFalse();
					  newScore -= 10;
				  }
				  newScore += cardNum;
				  if(!extraUserCard)
				  {
					  extraUserCard = true;
				  }
				  gamePanel.updateUserScore(newScore);
				  if(newScore == 21)
				  {
					  if(canPlay)
					  {
						  this.canPlay = false;
						  try {
							game.sendToServer("Stay" + currentChair);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  }
				  }
				  else if(newScore > 21)
				  {
					  if(canPlay)
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
		  }
		  else if(message.contains("DealerMove"))
		  {
			  System.out.println(canPlay);
			  String dealer[] = gamePanel.getDealerScore().split(":");
			  int dealerScore = Integer.parseInt(dealer[1].trim());
			  if(!dealerInitial)
			  {
				  dealerInitial = true;
				  gamePanel.removeDealerFlipCard();
				  gamePanel.addDealerCards(dealerSecondCardPath);
				  gamePanel.updateDealerScore(dealerSecondCard + dealerScore);
				  System.out.println("dealerSecondCard Score:" + dealerSecondCard);
			  }
			  String dealerNew[] = gamePanel.getDealerScore().split(":");
			  dealerScore = Integer.parseInt(dealerNew[1].trim());
			  
			  System.out.println(dealerScore);
			  if(dealerScore == 21 && !extraDealerCard)
			  {
				  System.out.println("DealerDone");
				  System.out.println(canPlay);
				  if(canPlay)
				  {
					  try {
						game.sendToServer("DealerDone");
						return;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
			  }
			  if(dealerScore>= 17)
			  {
				  System.out.println("DealerDone");
				  System.out.println(canPlay);
				  if(canPlay)
				  {
					  try {
						game.sendToServer("DealerDone");
						return;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
				  else
				  {
					  return;
				  }
			  }
			  extraDealerCard = true;
			  
			  
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
				  aceDealer();
				  if(newScore + 11 > 21)
				  {
					  cardNum = 1;
				  }
				  else
				  {
					  cardNum = 11;
				  }
			  }
			  
			  if(newScore + cardNum > 21 && (dealerace1 || dealerace2 || dealerace3 || dealerace4))
			  {
				  dealerAceFalse();
				  newScore -= 10;
			  }
			  
			  newScore += cardNum;
			  gamePanel.updateDealerScore(newScore);
			  System.out.println(newScore);
			  if(newScore > 21)
			  {
				  gamePanel.setError("Dealer Busted");
				  System.out.println(canPlay);
				  if(canPlay)
				  {
			    	try {
						game.sendToServer("DealerDone");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
			  }
			  else if(newScore <= 16)
			  {
				  if(canPlay)
				  {
					  try {
						game.sendToServer("DealerMove");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
			  }
			  else if(newScore >= 17)
			  {
				  
				  System.out.println(canPlay);
				  if(canPlay)
				  {
					  try {
						  System.out.println("DealerDone >=17");
						game.sendToServer("DealerDone");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
			  }
			 
		  }
		  System.out.println("UPDATE GAME");

	  }
	  
	  public void checkResults()
	  {
		  if(game.getWatchHand())
		  {
			  return;
		  }
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  gamePanel.updateGame();
		  String dealer = gamePanel.getDealerScore();
		  String[] array = dealer.split(":");
		  int dealerScore = Integer.parseInt(array[1].trim());
		  String user = gamePanel.getUserScore();
		  array = user.split(":");
		  int userScore = Integer.parseInt(array[1].trim());
		  
		  String bet = gamePanel.getBetAmount();
		  array = bet.split(":");
		  double betAmount = Double.parseDouble(array[1].trim());
		  
		  System.out.println(dealerScore + " " + userScore);
		  if(this.busted)
		  {
			  displayError("You busted by going over 21, you lose $" + betAmount);
			  try {
				game.sendToServer("updateBalance," + game.getUsername() + "," + (game.getBetAmount() * -1));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  else
		  {
			  if(dealerScore == 21 && !extraDealerCard && !blackJack)
			  {
				  displayError("You Lost, the dealer got BlackJack, you lose $" + betAmount);
				  try {
					game.sendToServer("updateBalance," + game.getUsername() + "," + (game.getBetAmount() * -1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  else if (!extraDealerCard && blackJack)
			  {
				  displayError("You tied, you keep $" + betAmount);
				  try {
					game.sendToServer("updateBalance," + game.getUsername() + "," + (game.getBetAmount() * 1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  else if(dealerScore > 21)
			  {
				  displayError("You Won, the dealer busted, you win double $" + betAmount);
				  try {
					game.sendToServer("updateBalance," + game.getUsername() + "," + (game.getBetAmount() * 1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		      else if(dealerScore > userScore)
			  {
				  displayError("Dealer Won, you lose $" + betAmount);
				  try {
					game.sendToServer("updateBalance," + game.getUsername() + "," + (game.getBetAmount() * -1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  else if(dealerScore == userScore)
			  {
				  displayError("You tied, you keep $" + betAmount);
				  try {
					game.sendToServer("updateBalance," + game.getUsername() + "," + (game.getBetAmount() * 1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  else if(userScore > dealerScore)
			  {
				  displayError("You Won, you win double $" + betAmount);
				  try {
					game.sendToServer("updateBalance," + game.getUsername() + "," + (game.getBetAmount() * 1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		  }
	  }
	  
	  public void dealerInitial(String message)
	  {
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  gamePanel.updateGame();
		  String[] temp = message.split("=");
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
		  dealerSecondCardPath = cardPath;
		  String tempScore = gamePanel.getDealerScore();
		  String[] array = tempScore.split(":");
		  int newScore = Integer.parseInt(array[1].trim());
		  if(cardNum > 10)
		  {
			  cardNum = 10;
		  }
		  else if(cardNum == 1)
		  {
			  aceDealer();
			  if(newScore + cardNum > 21)
			  {
				  cardNum = 1;
			  }
			  else
			  {
				  cardNum = 11;
			  }
		  }
		  if(dealerInitial)
		  {
			  gamePanel.addDealerCards(cardPath);
			  newScore += cardNum;
			  gamePanel.updateDealerScore(newScore);  
		  }	
		  else
		  {
			  dealerSecondCard = cardNum;
			  gamePanel.addDealerCards("/Card_Images/blue_back.png");
			  newScore += 0;
			  gamePanel.updateDealerScore(newScore); 
		  }
		  
		  if(dealerInitial)
		  {
			  dealerInitial = false;
			  if(canPlay)
			  {
				  try {
					game.sendToServer("dealerInitial");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		  }
	  }
	  
	  public void resetGame()
	  {
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  gamePanel.updateGame();
		  gamePanel.resetGame();
		  dealerInitial = true;
		  busted = false;
		  extraDealerCard = false;
		  extraUserCard = false;
		  blackJack = false;
		  dealerSecondCardPath = "";
		  userAceFalse();
		  userAceFalse();
		  userAceFalse();
		  userAceFalse();
		  dealerAceFalse();
		  dealerAceFalse();
		  dealerAceFalse();
		  dealerAceFalse();
		  try {
			game.sendToServer("resetGame");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  CardLayout cardLayout = (CardLayout) container.getLayout();
		  cardLayout.show(container, "5");
	  }
	  
	  public void updateBet()
	  {
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  gamePanel.setBetAmount(game.getBetAmount());
	  }
	  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
	  public void displayError(String error)
	  {
	    GamePanel GamePanel = (GamePanel)container.getComponent(5);
	    GamePanel.setError(error);
	  }
	  
	  private void aceUser()
	  {
		  if(ace1)
		  {
			  ace2 = true;
		  }
		  else if(ace2)
		  {
			  ace3 = true;
		  }
		  else if(ace3)
		  {
			  ace4 = true;
		  }
		  ace1 = true;
	  }
	  
	  private void userAceFalse()
	  {
		  if(ace4)
		  {
			  ace4 = false;
		  }
		  else if(ace3)
		  {
			  ace3 = false;
		  }
		  else if(ace2)
		  {
			  ace2 = false;
		  }
		  else if(ace1)
		  {
			ace1 = false;  
		  }
	  }
	  
	  private void aceDealer()
	  {
		  if(dealerace1)
		  {
			  dealerace2 = true;
		  }
		  else if(dealerace2)
		  {
			  dealerace3 = true;
		  }
		  else if(dealerace3)
		  {
			  dealerace4 = true;
		  }
		  dealerace1 = true;
	  }
	  
	  private void dealerAceFalse()
	  {
		  if(dealerace4)
		  {
			  dealerace4 = false;
		  }
		  else if(dealerace3)
		  {
			  dealerace3 = false;
		  }
		  else if(dealerace2)
		  {
			  dealerace2 = false;
		  }
		  else if(dealerace1)
		  {
			  dealerace1 = false;  
		  }
	  }
	  
	  public void checkScore()
	  {
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  String temp[] = gamePanel.getUserScore().split(":");
		  
		  if(Integer.parseInt(temp[1].trim()) == 21)
		  {
			  this.canPlay = false;
			  try {
				game.sendToServer("Stay" + currentChair);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
		  }
	  }
	  
	  public void setBalance(Double balance)
	  {
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  gamePanel.setBalance(balance);
	  }
	  
	  public void updateNames(String names)
	  {
		  GamePanel gamePanel = (GamePanel) container.getComponent(5);
		  String temp[] = names.split(",");
		  String namesFull = "";
		  for(int i = 0; i < temp.length; i++)
		  {
			  namesFull += temp[i] + ",";
		  }
		  gamePanel.updateNames(namesFull.replaceAll("\\[|\\]", ""));
	  }
}

