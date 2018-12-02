package database;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.*;
import org.junit.Test;

public class DatabaseTest {

	private Database db;
	private int rand;
	private String[] users = { "gjacobus", "mdodd" };
	private String[] passwords = { "hello", "hello123" };
	
	private String[] badusers = {"test", "notAUser"};

	@Before
	public void setUp() throws Exception {
		db = new Database();
		rand = ((int) Math.random() * users.length);
		db.executeDML("delete from blackjackdata where username='mking'");
		db.executeDML("delete from blackjackdata where username='ajordan'");

	}
	
	@Test
	public void testQuery() {
		String username = users[rand];
		ArrayList<String> queryResult = db
				.query("select aes_decrypt(password, 'blackjack') from blackjackdata where username='" + username + "'");

		assertEquals("Password is incorrect", queryResult.get(0), passwords[rand]);
	}
	
	@Test
	public void textExecuteDML() {
		String dml = "insert into blackjackdata values('mking', aes_encrypt('hellothere', 'blackjack'), 50)";
		try
		{
			db.executeDML(dml);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertFalse("Error occurred", true);
		}
		
		assertTrue("It worked!", true);
	}

	@Test
	public void testGetDataByUsername() {
		String username = users[rand];
		String data = db.getDataByUsername(username);
		
		boolean result;
		if (data.equals("username not found")) {
			result = false;
		} else {
			result = true;
		}
		
		assertTrue("User not found", result);
	}

	@Test
	public void testGetUserPassword() {
		String username = users[rand];
		String expected = passwords[rand];
		String actual = db.getUserPassword(username);
		assertEquals("Incorrect password", expected, actual);
	}

	@Test
	public void testGetUserBalance() {
		String username = users[rand];
		String balance = db.getUserBalance(username);
		
		boolean result;
		if (balance.equals("username not found")) {
			result = false;
		}
		else {
			result = true;
		}
		
		assertTrue("User not found", result);
	}

	@Test
	public void testAddData() {
		String data = "ajordan,mypassword";
		boolean flag = false;
		db.addData(data);
		flag = true;
		assertTrue("Error", flag);
	}

}
