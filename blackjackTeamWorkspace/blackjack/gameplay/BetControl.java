package gameplay;

public class BetControl 
{
	private boolean startGame = true;
	
	public void startGame(String message)
	{
		if(message.equals("fullGame"))
		{
			startGame = false;
		}
	}

}
