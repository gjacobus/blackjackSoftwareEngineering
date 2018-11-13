package blackjackPkg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Database
{
	private Connection conn;

	// Add any other data fields you like – at least a Connection object is
	// mandatory
	public Database()
	{
		// Add your code here
		Properties prop = new Properties();
		FileInputStream fis;

		try
		{
			fis = new FileInputStream("blackjackPkg/db.properties");
			prop.load(fis);
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String pass = prop.getProperty("password");
			conn = DriverManager.getConnection(url, user, pass);

		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<String> query(String query)
	{
		// Add your code here
		ArrayList<String> toReturn = new ArrayList<String>();

		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rmd = rs.getMetaData();

			int no_columns = rmd.getColumnCount();

			String name = rmd.getColumnName(1);

			while (rs.next())
			{
				String toAdd = "";
				for (int i = 1; i < no_columns + 1; i++)
				{
					if (i != no_columns)
						toAdd += rs.getString(i) + ",";
					else
						toAdd += rs.getString(i);
				}
				toReturn.add(toAdd);
			}

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;
	}

	public void executeDML(String dml) throws SQLException
	{
		// Add your code here
		Statement stmt = conn.createStatement();
		stmt.execute(dml);
	}

	public ArrayList<String> getIdList()
	{
		ArrayList<String> ids = new ArrayList<String>();

		// more code here

		return ids;
	}

	public String getDataByUsername(String username)
	{
		String query = "select * from user";
		ArrayList<String> data = this.query(query);
		for (String str : data)
		{
			String[] split = str.split(",");
			if (split[0].equals(username))
			{
				return str;
			}
		}
		return "user not found";
	}

	public String getUserPassword(String username)
	{
		String query = "select username, aes_decrypt(password, 'hello') from user";
		ArrayList<String> data = this.query(query);
		for (String str : data)
		{
			String[] split = str.split(",");
			if (split[0].equals(username))
			{
				return split[1];
			}
		}
		return "user not found";
	}

	public void addData(String data)
	{
		String[] dataArray = data.split(",");

		String stmt = "insert into user values('" + dataArray[0] + "', " + encryptPassword(dataArray[1]) + ")";

		try
		{
			this.executeDML(stmt);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private String encryptPassword(String password) {
		return("aes_encrypt('" + password + "', 'hello')");
	}

}
