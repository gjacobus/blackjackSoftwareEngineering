package clientCommunication;

import java.io.IOException;

import javax.swing.JPanel;

import gameplay.BetControl;
import gameplay.GameControl;
import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
	private LoginControl lc;
	private CreateAccountControl cac;
	private GameControl gc;
	private String username;
	private double balance;
	private double betAmount;
	private BetControl bc;
	private int chairNum = 0;

	public GameClient()
	{
		super("localhost", 12345);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getBetAmount() {
		return this.betAmount;
	}
	public void setBetAmount(double amt) {
		betAmount = amt;
	}

	public void setLoginControl(LoginControl lc)
	{
		this.lc = lc;
	}

	public void setCreateAccountControl(CreateAccountControl cac)
	{
		this.cac = cac;
	}
	
	public void setGameControl(GameControl gc)
	{
		this.gc = gc;
	}
	
	public void setBetControl(BetControl bc)
	{
		this.bc = bc;
	}

	@Override
	public void handleMessageFromServer(Object arg0)
	{
		System.out.println("Server Message sent to Client " + (String) arg0);
		String message = (String) arg0;

		switch (message)
		{
		case "Invalid username":
			lc.displayError("Username is invalid");
			break;
		case "Invalid password":
			lc.displayError("Password is invalid");
			break;
		case "Login success":
			lc.loginSuccess();
			break;
		case "Username already exists":
			cac.displayError("Username already exists");
			break;
		case "Create account success":
			cac.createAccountSuccess();
			break;
		}
		if(message.contains("fullGame"))
		{
		}
		else if(message.contains("chairNum"))
		{
			String temp[] = message.split(",");
			chairNum = Integer.parseInt(temp[1]);
		}
		else if(message.contains("wait"))
		{
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.sendToServer("checkStart");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(message.contains("GameStart"))
		{
			gc.startGame(message);
		}
		else if(message.contains("GameUpdate"))
		{
			gc.updateGame(message);
		}
		else if(message.contains("initialCards"))
		{
			gc.updateGame(message);
		}
		else if(message.contains("dealerInitial"))
		{
			gc.dealerInitial(message);
		}
		else if(message.contains("dealerDone"))
		{
			chairNum = 0;
		}
		else if(message.contains("nextCard"))
		{
			System.out.println(message);
			gc.updateGame(message);
		}
		else if(message.contains("Stay"))
		{
			gc.canPlay(false);
		}
		else if(message.contains("canPlay"))
		{
			gc.canPlay(true);
			chairNum = 0;
		}
		else if(message.contains("DealerMove"))
		{
			gc.updateGame(message);
		}
		else if(message.equals("CheckResults"))
		{
			gc.checkResults();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Sleep");
			gc.resetGame();
		}
		else if(message.equals("chairIncrease"))
		{
			gc.chairIncrease();
		}
		else if (message.contains("Balance: ")) {
			this.balance = Double.parseDouble(message.substring(9));
		}

	}

	public void connectionException(Throwable exception)
	{
		// Add your code here
	}

	public void connectionEstablished()
	{
		// Add your code here
	}

}
