package serverCommunication;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;

import clientCommunication.CreateAccountData;
import clientCommunication.LoginData;
import database.Database;
import gameplay.BetData;
import gameplay.GameData;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class GameServer extends AbstractServer
{
	private JTextArea log;
	private JLabel status;
	private Database database;
	private ArrayList<Integer> deck = new ArrayList<Integer>();
	private int currentNum = 0;
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<ConnectionToClient> clients = new ArrayList<ConnectionToClient>();
	private int currentChair = 0;
	private int timerCheck = 1;
	private int cardNum = 0;
	private Boolean gameStarted = false;

	public GameServer()
	{
		super(12345);
	}

	public GameServer(int port)
	{
		super(port);
	}
	
	public void setDatabase(Database db) 
	{
		database = db;
	}

	public void setLog(JTextArea log)
	{
		this.log = log;
	}

	public JTextArea getLog()
	{
		return log;
	}

	public void setStatus(JLabel status)
	{
		this.status = status;
	}

	public JLabel getStatus()
	{
		return status;
	}
	
	private Boolean addUser(String username)
	{
		if(names.size() <= 3)
		{
			if(!names.contains(username))
			{
				names.add(username);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	private Boolean removeUser(String username)
	{
		if(names.size() >= 0)
		{
			if(names.contains(username))
			{
				names.remove(username);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	private Boolean addClient(ConnectionToClient client)
	{
		if(clients.size() <= 3)
		{
			if(!clients.contains(client))
			{
				clients.add(client);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	private Boolean removeClient(ConnectionToClient client)
	{
		if(clients.size() >= 0)
		{
			if(clients.contains(client))
			{
				clients.remove(client);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		// TODO Auto-generated method stub
		// log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(arg0.toString() + " thread name:" + arg1.getName());
		if (arg0 instanceof LoginData)
		{
			LoginData loginData = (LoginData) arg0;

			// verify username/password
			try
			{
				if (database.getDataByUsername(loginData.getUsername()).equals("user not found"))
				{
					System.out.println("usernameNotFound");
					arg1.sendToClient("Invalid username");
				} else if (!database.getUserPassword(loginData.getUsername()).equals(loginData.getPassword()))
				{
					arg1.sendToClient("Invalid password");
				}
				else 
				{
					arg1.sendToClient("Balance: " + database.getUserBalance(loginData.getUsername()));
					arg1.sendToClient("Login success," + loginData.getUsername());
				}
				// store it in database file


			} catch (IOException e)
			{
				e.printStackTrace();
			}

		} else if (arg0 instanceof CreateAccountData)
		{
			CreateAccountData createAccountData = (CreateAccountData) arg0;
			try
			{
				if (database.getDataByUsername(createAccountData.getUsername()).equals("username not found"))
				{
					database.addData(createAccountData.getUsername() + "," + createAccountData.getPassword());
					arg1.sendToClient("Balance: " + database.getUserBalance(createAccountData.getUsername()));
					arg1.sendToClient("Create account success," + createAccountData.getUsername());
				} else
				{
					arg1.sendToClient("Username already exists");
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (arg0 instanceof BetData)
		{
			BetData betData = (BetData) arg0;
			//verification of betData, then game starts
			//TODO verify bet data add user to list, if name is added and first user canPlay = true, else canPlay = false
			String username = betData.getUsername();
			if(gameStarted)
			{
				try {
					arg1.sendToClient("gameInProgress");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(addUser(username))
			{
				currentChair++;
				timerCheck++;
				addClient(arg1);
				try {
					arg1.sendToClient("updateBet");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(names.size() == 1)
				{
					try {
						arg1.sendToClient("canPlay");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					try {
						arg1.sendToClient("chairNum," + currentChair);
				
					arg1.sendToClient("Stay");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					arg1.sendToClient("wait");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					arg1.sendToClient("fullGame");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(arg0.toString().equals("checkStart"))
		{
			timerCheck++;
			if(timerCheck >= names.size() + 1)
			{
				if(!gameStarted)
				{
				currentChair = 0;
				//TODO maybe who knows
				gameStarted = true;
				this.notifyAll();
				this.sendToAllClients("GameStart");
				}
			}
		}
		else if(arg0.toString().contains("updateNames"))
		{
			try {
				arg1.sendToClient("updateNames=" + names);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(arg0.toString().contains("initialCards"))
		{
			cardNum++;
			System.out.println("currentChair: " + currentChair);
			System.out.println("cardNum:" + cardNum);
			if(cardNum > 2)
			{
				currentChair++;
				cardNum = 0;
				this.sendToAllClients("chairIncrease");
				try {
					clients.get(currentChair - 1).sendToClient("Stay");
					System.out.println((currentChair >= clients.size()) + " client.size");
					if(currentChair >= clients.size())
					{
						clients.get(0).sendToClient("canPlay");
					}
					else
					{
						clients.get(currentChair).sendToClient("canPlay");
						cardNum++;
						int temp = nextCard();
						this.sendToAllClients(("initialCards=" + temp));
						return;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				int temp = nextCard();
				this.sendToAllClients("initialCards=" + temp);
			}
			
			System.out.println((currentChair >= clients.size()) + " names.size");
			if(currentChair >= names.size())
			{
				currentChair = 0;
				int temp = nextCard();
				this.sendToAllClients("dealerInitial=" + temp);
			}
		}
		else if(arg0.toString().contains("dealerInitial"))
		{
			int temp = nextCard();
			this.sendToAllClients("dealerInitial=" + temp);
			currentChair = 0;
			this.sendToAllClients("dealerDone");
			try {
				clients.get(0).sendToClient("canPlay");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(arg0.toString().equals("nextCard"))
		{
			int temp = nextCard();
			this.sendToAllClients("nextCard=" + temp);
		}
		else if(arg0.toString().contains("Stay"))
		{			
			currentChair += 1;
			this.sendToAllClients("chairIncrease");
			try {
				if(arg0.equals("Stay" + (names.size() - 1)))
				{
					clients.get(0).sendToClient("canPlay");
					int temp = nextCard();
					this.sendToAllClients("DealerMove=" + temp);
					return;
				}
				arg1.sendToClient("Stay");
				if(currentChair < clients.size())
				{
					clients.get(currentChair).sendToClient("canPlay");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(arg0.toString().equals("DealerMove"))
		{
			int temp = nextCard();
			this.sendToAllClients("DealerMove=" + temp);
		}
		else if(arg0.toString().equals("DealerDone"))
		{
			System.out.println("Checkum");
			try {
				arg1.sendToClient("CheckResults");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(arg0.toString().equals("resetGame"))
		{
			this.sendToAllClients("gameReset");
			shuffleDeck();
			currentChair = 0;
			gameStarted = false;
			cardNum = 0;
			timerCheck = 1;
			System.out.println(names.toString());
			if(names.removeAll(names))
			{
				clients.removeAll(clients);
				System.out.println("Game Reset");
			}
		}
		else if(arg0.toString().contains("updateBalance"))
		{
			String temp[] = arg0.toString().split(",");
			String username = temp[1];
			String betAmount = temp[2];
			
			try {
				ArrayList<String> res = database.query("select balance from BlackJackData where username='" + username + "'");
				Double newBalance = Double.parseDouble(res.get(0)) + Double.parseDouble(betAmount);
				database.executeDML("update blackjackdata set balance = '" + newBalance + "' where username='" + username + "'");
				try {
					
					arg1.sendToClient("updateBalance=" + newBalance);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(arg0.toString().contains("addMoney"))
		{
			String temp[] = arg0.toString().split("=");
			String username = temp[1];
			
			try {
				ArrayList<String> res = database.query("select balance from BlackJackData where username='" + username + "'");
				System.out.println(res);
				Double newBalance = Double.parseDouble(res.get(0)) + 50.0;
				System.out.println(newBalance);
				database.executeDML("update blackjackdata set balance = '" + newBalance + "' where username='" + username + "'");
				try {
					
					arg1.sendToClient("updateBalance=" + newBalance);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void shuffleDeck()
	{
		currentNum = 0;
		Random seed = new Random();
		int deckSize = 52;
		deck = new ArrayList<Integer>();
		for(int i = 0; i < deckSize; i++)
		{
			int temp = seed.nextInt(deckSize);
			while(deck.contains(temp))
			{
				temp = seed.nextInt(deckSize);
			}
			deck.add(temp);
		}
	}
	
	public int nextCard()
	{
		int temp = deck.get(currentNum);
		currentNum += 1;
		return temp;
	}
	

	protected void listeningException(Throwable exception)
	{
		// Display info about the exception
		System.out.println("Listening Exception:" + exception);
		exception.printStackTrace();
		System.out.println(exception.getMessage());

		/*
		 * if (this.isListening()) { log.append("Server not Listening\n");
		 * status.setText("Not Connected"); status.setForeground(Color.RED); }
		 */

	}

	protected void serverStarted()
	{
		System.out.println("Server Started");
		log.append("Server Started\n");
		status.setForeground(Color.GREEN);
		status.setText("Listening");
	}

	protected void serverStopped()
	{
		System.out.println("Server Stopped");
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
	}

	protected void serverClosed()
	{
		System.out.println("Server and all current clients are closed - Press Listen to Restart");
		log.append("Server and all current clients are closed - Press Listen to Restart\n");
	}

	protected void clientConnected(ConnectionToClient client)
	{
		System.out.println("Client Connected");
		log.append("Client Connected\n");
	}

}
