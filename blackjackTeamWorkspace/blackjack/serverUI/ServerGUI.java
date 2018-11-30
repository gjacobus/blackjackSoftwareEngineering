package serverUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.w3c.dom.Document;

import database.Database;
import serverCommunication.GameServer;

//import lab1out.ClientGUI.EventHandler;

public class ServerGUI extends JFrame
{
	private JLabel statusLabel; 
	private String[] labels = {"Port #", "Timeout"};
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea log;
	private GameServer server;
	private JLabel status; //Initialized to “Not Connected”

	public ServerGUI(String title) {
		  this.setTitle(title);
		  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  server = new GameServer();
		  Database db = new Database();
		  server.setDatabase(db);
		  server.setLog(log);
		  server.setStatus(status);
		  
		  server.shuffleDeck();

		  
		  status = new JLabel("Not Connected");
		  status.setForeground(Color.RED);
		  statusLabel = new JLabel ("Status: ");
		  
		  JPanel north = new JPanel(new FlowLayout());
		  north.add(statusLabel);
		  north.add(status);
		  
		  JPanel outer = new JPanel(new FlowLayout());
		  JPanel textFieldsPanel = new JPanel(new GridLayout(labels.length, 1,10,10));
		  
		  
		  for (int i = 0; i < labels.length; i++) {
			  JLabel label = new JLabel(labels[i]);
			  textFields[i] = new JTextField("", 10);
			  //textFields[i].setEditable(false);
			  
			  textFieldsPanel.add(label);
			  textFieldsPanel.add(textFields[i]);
		  
		  }
		  textFields[0].setText("12345");
		  textFields[1].setText("500");
		  
		  JPanel textAreas = new JPanel(new BorderLayout());
		  textAreas.setBorder(BorderFactory.createEmptyBorder(70, 0, 0, 0));
		  JLabel serverLogLabel = new JLabel("Server Log Below");
		  serverLogLabel.setHorizontalAlignment(JLabel.CENTER);
		  log = new JTextArea("");
		  log.setLineWrap(true);
		  log.setWrapStyleWord(true);
		  log.setEditable(false);
		  JScrollPane serverLogScroll = new JScrollPane(log);
		  serverLogScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		  serverLogScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		  serverLogScroll.setPreferredSize(new Dimension(400,150));
		  textAreas.add(serverLogLabel, BorderLayout.NORTH);
		  textAreas.add(serverLogScroll, BorderLayout.SOUTH);
		  
		  
		  //buttons
		  JPanel south = new JPanel(new FlowLayout());
		  JButton listen = new JButton("Listen");
		  listen.addActionListener(new EventHandler());
		  JButton close = new JButton("Close");
		  close.addActionListener(new EventHandler());
		  JButton stop = new JButton("Stop");
		  stop.addActionListener(new EventHandler());
		  JButton quit = new JButton("Quit");
		  quit.addActionListener(new EventHandler());
		  south.add(listen);
		  south.add(close);
		  south.add(stop);
		  south.add(quit);

		  
		  outer.add(textFieldsPanel);
		  outer.add(textAreas);
		  
		  
		  add(north, BorderLayout.NORTH);
		  add(outer, BorderLayout.CENTER);
		  add(south, BorderLayout.SOUTH);
		  
		  this.setSize(500, 410);
		  this.setVisible(true);
		  
		  server.setLog(log);
		  server.setStatus(status);
		  try {
			server.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e)
		{
			switch (e.getActionCommand()) {
			case "Listen":
				if (textFields[GetIndexByName("Port #")].getText().equals("")   || textFields[GetIndexByName("Timeout")].getText().equals("")) {
					log.setText(log.getText() + "Port Number/timeout not entered before pressing Listen\n");
				} else {
					server.setPort(Integer.parseInt(textFields[GetIndexByName("Port #")].getText()));
					server.setTimeout(Integer.parseInt(textFields[GetIndexByName("Timeout")].getText()));
					try
					{
						server.listen();
						
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			case "Close":
				if (!server.isListening()) {
					log.setText(log.getText() + "Server not currently started\n");
				} else {
					try
					{
						server.close();
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			case "Stop":
				if (!server.isListening()) {
					log.setText(log.getText() + "Server not currently started\n");
				} else {
					server.stopListening();
				}
				break;
			case "Quit":
				System.exit(0);
				break;
			
			
			}
		
		}
		
	}

	
	private int GetIndexByName(String name) {
		for (int i = 0; i < labels.length; i++) {
			if (labels[i].equals(name))
				return i;
		}
		return -1;
	}
	
	

	
	public static void main(String[] args)
	{
		new ServerGUI("Server GUI");
	}
}
