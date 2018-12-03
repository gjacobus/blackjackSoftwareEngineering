package clientUI;

import javax.swing.*;

import clientCommunication.CreateAccountControl;
import clientCommunication.GameClient;
import clientCommunication.InitialControl;
import clientCommunication.LoginControl;
import clientCommunication.StartGameControl;
import gameplay.BetControl;
import gameplay.GameControl;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

public class ClientGUI extends JFrame {
	private static String ip = "";
	private static boolean usingLocalHost = true;

	// Constructor that creates the client GUI.
	public ClientGUI() {
		// Set the title and default close operation.
		this.setTitle("Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the card layout container.
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);

		// Create the Controllers next
		// Next, create the Controllers
		InitialControl ic = new InitialControl(container);
		GameClient client;
		if (!usingLocalHost) {
			client = new GameClient(ip);
		} else {
			client = new GameClient();
		}
		LoginControl lc = new LoginControl(container, client); // Probably will want to pass in ChatClient here
		CreateAccountControl cac = new CreateAccountControl(container, client);
		StartGameControl sgc = new StartGameControl(container, client);
		GameControl gc = new GameControl(container, client);
		BetControl bc = new BetControl(container, client);
		client.setCreateAccountControl(cac);
		client.setLoginControl(lc);
		client.setGameControl(gc);
		client.setStartGameControl(sgc);
		client.setBetControl(bc);

		// Create the four views. (need the controller to register with the Panels
		JPanel view1 = new InitialPanel(ic);
		JPanel view2 = new LoginPanel(lc);
		JPanel view3 = new CreateAccountPanel(cac);
		JPanel view4 = new StartGamePanel(sgc);
		JPanel view5 = new BetPanel(bc);
		JPanel view6 = new GamePanel(gc);

		view2.addComponentListener(new ResizeFrame(container, 550, 350));
		view3.addComponentListener(new ResizeFrame(container, 550, 350));
		view4.addComponentListener(new ResizeFrame(container, 475, 600));
		view5.addComponentListener(new ResizeFrame(container, 600, 400));
		view6.addComponentListener(new ResizeFrame(container, 800, 750));
		

		// Add the views to the card layout container.
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view3, "3");
		container.add(view4, "4");
		container.add(view5, "5");
		container.add(view6, "6");
		
		sgc.updateBalance();
		sgc.updateUsername();

		// Show the initial view in the card layout.
		cardLayout.show(container, "1");
		
		JScrollPane scrollPane = new JScrollPane(container);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(800,750));

		// Add the card layout container to the JFrame.
		this.add(scrollPane, BorderLayout.CENTER);

		// Show the JFrame.
		this.setSize(550, 350);
		this.setVisible(true);
	
		

	}

	public class ResizeFrame implements ComponentListener {
		private JPanel container;
		int width;
		int height;

		public ResizeFrame(JPanel container, int width, int height) {
			super();
			this.container = container;
			this.width = width;
			this.height = height;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void componentShown(ComponentEvent e) {
			JFrame frame = (JFrame) SwingUtilities.windowForComponent(container);
			frame.setSize(width, height);

		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

	}

	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args) {
		if (args.length > 1) {
			ip = args[1];
			usingLocalHost = false;
		}
		new ClientGUI();
	}
}
