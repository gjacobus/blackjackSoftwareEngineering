package serverCommunication;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;

import clientCommunication.CreateAccountData;
import clientCommunication.LoginData;
import database.Database;

import java.awt.*;
import java.io.IOException;

public class GameServer extends AbstractServer
{
	private JTextArea log;
	private JLabel status;
	private Database database;

	public GameServer()
	{
		super(12345);
	}

	public GameServer(int port)
	{
		super(port);
	}
	
	public void setDatabase(Database db) {
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

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		// TODO Auto-generated method stub
		// log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");

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
		}

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
