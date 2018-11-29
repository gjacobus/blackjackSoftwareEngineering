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
	private int currentChair = 0;

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

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		// TODO Auto-generated method stub
		// log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");

		System.out.println(arg0.toString());
		if (arg0 instanceof LoginData)
		{
			LoginData loginData = (LoginData) arg0;

			// verify username/password
			try
			{
				if (database.getDataByUsername(loginData.getUsername()).equals("user not found"))
				{
					arg1.sendToClient("Invalid username");
				} else if (!database.getUserPassword(loginData.getUsername()).equals(loginData.getPassword()))
				{
					arg1.sendToClient("Invalid password");
				}
				else 
				{
					arg1.sendToClient("Balance: " + database.getUserBalance(loginData.getUsername()));
					arg1.sendToClient("Login success");
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
				if (database.getDataByUsername(createAccountData.getUsername()).equals("user not found"))
				{
					database.addData(createAccountData.getUsername() + "," + createAccountData.getPassword());
					arg1.sendToClient("Create account success");
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
			//TODO verify bet data add user to list, if name is added and first user canPLay = true, else canPlay = false
			if(names.size() == 1)
			{
				try {
					arg1.sendToClient("canPlay");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String message = "GameStart";
			try {
				arg1.sendToClient(message);
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
			//TODO currentScore needs to be calculated based on username and the cards underneath
			currentChair += 1;
			this.sendToAllClients("chairIncrease");
			try {
				if(arg0.equals("Stay" + (names.size())))
				{
					int temp = nextCard();
					this.sendToAllClients("DealerMove=" + temp);
				}
				arg1.sendToClient("Stay");
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
			shuffleDeck();
			System.out.println("CHeckum");
			this.sendToAllClients("CheckResults");
		}
	}
	
	public void shuffleDeck()
	{
		currentNum = 0;
		Random seed = new Random();
		int deckSize = 52;
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
