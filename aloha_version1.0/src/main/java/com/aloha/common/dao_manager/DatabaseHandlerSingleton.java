package com.aloha.common.dao_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandlerSingleton {

	private static volatile DatabaseHandlerSingleton db;
	private static Connection con = null;
	//private Statement stmt = null;

	private DatabaseHandlerSingleton() {
		try {
			// instantiate com.mysql.jdbc.Driver. This object registers itself
			// with the DriverManager
			Class.forName("com.mysql.jdbc.Driver");

			// ask the DriverManager for a connection to the schema mysql with
			// root privileges
			/*con = DriverManager.getConnection(
					"jdbc:mysql://sql3.freemysqlhosting.net:3306/sql373425",
					"sql373425", "zB8*dV3%");*/
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/testdb", "root", "root");
			con.createStatement();

		} catch (SQLException ex) {
			System.out.println("cannot query the database: " + ex.getMessage());
		} catch (ClassNotFoundException ex) {
			System.out.println("cannot connect to MySQL database" + ex.getMessage());
			//ex.printStackTrace();
		}
	}

	public static Connection getDBConnection() {
		if(db == null){
		synchronized(DatabaseHandlerSingleton.class){
		if (db == null) {
			 db = new DatabaseHandlerSingleton();

		}}}
		return con;
	}

	public static void close() {
		if (con != null) {
			try {
				db=null;
				con.close();
			} catch (Exception e) {
			}
		}
	}

	public void finalize() {
		close();
	}

}
