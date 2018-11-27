package gameplay;

import java.util.ArrayList;

public class GameData 
{
	ArrayList<String> bigData = new ArrayList<String>();
	private String usernames;
	private String cardTotals;
	private String betAmounts;
	  
	  // Getters for the username and password.
	public String getUsernames()
	{
	  return usernames;
	}
	
	public String getCardTotals()
	{
	  return cardTotals;
	}
	
	public String getBetAmounts()
	{
	  return betAmounts;
	}

	  
	public void setUsernames(String usernames)
	{
	  this.usernames = usernames;
	}
	
	public void setCardTotals(String cardTotals)
	{
	  this.cardTotals = cardTotals;
	}
	
	public void setBetAmounts(String betAmounts)
	{
	  this.betAmounts = betAmounts;
	}	
	
	  
	// Constructor that initializes the username and password.
	public GameData(String usernames, String cardTotals, String betAmounts)
	{
	  setUsernames(usernames);
	  setCardTotals(cardTotals);
	  setBetAmounts(betAmounts);
	  
	  bigData.add(this.usernames);
	  bigData.add(this.cardTotals);
	  bigData.add(this.betAmounts);
	  
	}

}
