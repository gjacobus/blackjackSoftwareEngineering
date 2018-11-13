package gameplay;

public class GameData 
{
	private String username;
	private Integer cardTotal;
	private Integer betAmount;
	  
	  // Getters for the username and password.
	public String getUsername()
	{
	  return username;
	}
	
	public Integer getCardTotal()
	{
	  return cardTotal;
	}
	
	public Integer getBetAmount()
	{
	  return betAmount;
	}
	  
	// Setters for the username and password.
	public void setUsername(String username)
	{
	  this.username = username;
	}
	
	public void setCardTotal(Integer cardTotal)
	{
	  this.cardTotal = cardTotal;
	}
	
	public void setBetAmount(Integer betAmount)
	{
	  this.betAmount = betAmount;
	}
	  
	// Constructor that initializes the username and password.
	public GameData(String username, Integer cardTotal, Integer betAmount)
	{
	  setUsername(username);
	  setCardTotal(cardTotal);
	  setBetAmount(betAmount);
	}

}
