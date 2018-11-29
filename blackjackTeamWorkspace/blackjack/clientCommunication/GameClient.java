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
	private BetControl bc;
	private int chairNum = 0;

	public GameClient()
	{
		super("localhost", 12345);
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
			bc.startGame(message);
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
