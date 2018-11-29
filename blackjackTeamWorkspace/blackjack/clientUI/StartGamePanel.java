package clientUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

import clientCommunication.StartGameControl;

public class StartGamePanel extends JPanel 
{
	
	private Color background = new Color(10, 100, 35);
	private JLabel nameLabel;
	private JLabel balanceLabel;
	private JPanel box;
	
	public StartGamePanel(StartGameControl sgc) 
	{
	        
	    JPanel namePanel = new JPanel(new GridLayout(1, 1, 0, 0));
	    namePanel.setBackground(background);
	    JLabel blackJack = new JLabel("BlackJack");
	    blackJack.setForeground(Color.YELLOW);
	    namePanel.add(blackJack);
	    
	    
	    JPanel userDataPanel = new JPanel(new GridLayout(2, 1, 0, 30));
	    userDataPanel.setBackground(background);
	    nameLabel = new JLabel("Username:", JLabel.CENTER);
	    balanceLabel = new JLabel("Balance:", JLabel.CENTER);
	    userDataPanel.add(nameLabel);
	    userDataPanel.add(balanceLabel);
	       
	    
	    
	    JPanel startGamePanel = new JPanel(new GridLayout(1, 1, 0, 30));
	    JButton startGameButton  = new JButton("StartGame");
	    startGamePanel.add(startGameButton);
	   
	    
	    box = new JPanel();
	    box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
	    
	    box.add(namePanel);
	    box.add(Box.createRigidArea(new Dimension(0, 100)));
	    
	    box.add(userDataPanel);
	    box.add(Box.createRigidArea(new Dimension(0, 100)));
	    
	    box.add(startGamePanel);
	    box.add(Box.createRigidArea(new Dimension(0, 50)));
	    
	    box.setBackground(background);
	    this.add(box);
	    this.setBackground(background);
	}
	
	public JLabel getUsername()
	{
		return nameLabel;
	}
	
	public void setUsername(String name)
	{
		nameLabel.setText("Username:" + name);
	}
	
	public void setBalance(String balance)
	{
		balanceLabel.setText("Username:" + balance);
	}
	
	public JLabel getBalance()
	{
		return nameLabel;
	}
}
