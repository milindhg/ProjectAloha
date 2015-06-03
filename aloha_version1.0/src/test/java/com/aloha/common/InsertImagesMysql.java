package com.aloha.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
public class InsertImagesMysql{
	public static void main(String[] args){
		System.out.println("Insert Image Example!");
		Connection con = null;
		
		try{
		   con = DatabaseHandlerSingleton.getDBConnection();
		   Statement st = con.createStatement();
		   File imgfile = new File("E://iu acad//pic.jpg");
		  
		  FileInputStream fin = new FileInputStream(imgfile);
		 
		   PreparedStatement pre =
		   con.prepareStatement("insert into images values(?, ?,?,?)");
		 
		   pre.setInt(1,1);
		   pre.setInt(2,1);
		   pre.setBinaryStream(3,(InputStream)fin,(int)imgfile.length());
		   pre.setInt(4,3);
		   pre.executeUpdate();
		   System.out.println("Successfully inserted the file into the database!");

		   pre.close();
		   con.close(); 
		}catch (Exception e1){
			System.out.println(e1.getMessage());
		}
	}
}