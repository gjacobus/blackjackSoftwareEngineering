package clientCommunication;

import javax.swing.JPanel;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
	private LoginControl lc;
	private CreateAccountControl cac;

	public GameClient()
	{
		super("localhost", 8300);
	}

	public void setLoginControl(LoginControl lc)
	{
		this.lc = lc;
	}

	public void setCreateAccountControl(CreateAccountControl cac)
	{
		this.cac = cac;
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
