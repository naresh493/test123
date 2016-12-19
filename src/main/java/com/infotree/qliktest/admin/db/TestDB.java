package com.infotree.qliktest.admin.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class TestDB {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		try
		{
		//String url = "jdbc:mysql://localhost:3307/test";
		String url = "jdbc:mysql://192.168.0.132:3306/qliktest_live";
		String user = "root";
		String password = "";

		// Load the Connector/J driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		// Establish connection to MySQL
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
