package database;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Test;

public class DatabaseTest {

	private Database db;
	private int rand;
	private String[] users = { "gjacobus", "mdodd" };
	private String[] passwords = {"hello", "hello123"};

	@Before
	public void setUp() throws Exception {
		db = new Database();
		rand = ((int) Math.random() * users.length);
	}
	

}
