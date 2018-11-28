import java.sql.*;  .

class MysqlCon
{  
	private String Data;
	public static void main(String args[])
	{  

		try
		{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1/score", user, psw); 
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from BlackJackData");  
			while(rs.next())  
			Data = System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)); 
			con.close();  
		}catch(Exception e){ System.out.println(e);}  
	}  
	public getData()
	{
		return Data;
	}
	public setData(String new)
	{
		Data = new;
	}
}  