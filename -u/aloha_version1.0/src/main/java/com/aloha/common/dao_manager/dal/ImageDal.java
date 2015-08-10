package com.aloha.common.dao_manager.dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
import com.aloha.common.entities.user.UserEducation;
import com.aloha.common.util.ProfileImage;

import java.sql.*;

public class ImageDal {
	Connection con = null;
	private String SELECT;
	private String INSERT;
	private String UPDATE;
	private String DELETE;
	public ImageDal()
	{
		SELECT = "select images.image_id, images.image, images.size from images where images.user_id=?;";
		INSERT = "insert into images(images.user_id, images.image, images.size) values(?, ?, ?);";
		UPDATE = "update images set images.image = ?, images.size = ? where images.user_id=?;";
		DELETE = "delete from images where images.user_id=?;";
		con = DatabaseHandlerSingleton.getDBConnection();
	}
	public ProfileImage insertImage(int user_id, byte []image) throws SQLException
	{
		ProfileImage pi = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		int res = 0;
		
		try{
			con = DatabaseHandlerSingleton.getDBConnection();
			FileOutputStream fos = new FileOutputStream("tempPic.jpg");
			fos.write(image);
			fos.close();
			File imgfile = new File("tempPic.jpg");			  
			FileInputStream fin = new FileInputStream(imgfile);
			ps = con.prepareStatement(INSERT);
			ps.setInt(1, user_id);
			ps.setBinaryStream(2,(InputStream)fin,imgfile.length());
			ps.setLong(3,imgfile.length());
			res = ps.executeUpdate();
			if(res!=0)
			{				
				ps1 = con.prepareStatement(SELECT);
				ps1.setInt(1, user_id);
				rs = ps1.executeQuery();
				if(rs.first())
				{
					pi= new ProfileImage();
					pi.setImg_id(rs.getInt(1));
					pi.setImg(image);
					pi.setSize(imgfile.length());
					pi.setUser_id(user_id);
				}
			}
			return pi;
		}
		catch(SQLException ex)
		{
			throw ex;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			//con.close();
		}
		return pi;
	}
	public boolean modifyImage(int user_id, byte []image, int image_id) throws SQLException{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		int res = 0;
		
		try{
			con = DatabaseHandlerSingleton.getDBConnection();
			FileOutputStream fos = new FileOutputStream("G:\\tempPic.jpg");
			fos.write(image);
			fos.close();
			File imgfile = new File("G:\\tempPic.jpg");			  
			FileInputStream fin = new FileInputStream(imgfile);
			ps = con.prepareStatement(UPDATE);
	
			ps.setBinaryStream(1,(InputStream)fin,(int)imgfile.length());
			//ps.setBytes(1, image);
			ps.setLong(2,imgfile.length());
			ps.setInt(3, user_id);
			res = ps.executeUpdate();
			if(res!=0)
			{				
				return true;
			}
		}
		catch(SQLException ex)
		{
			throw ex;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			//con.close();
		}
		return false;
	}
	public boolean deleteImage(int user_id) throws SQLException{
		PreparedStatement ps = null;
		con = DatabaseHandlerSingleton.getDBConnection();
		try {
			ps = con.prepareStatement(DELETE);
			ps.setInt(1, user_id);
			int res = ps.executeUpdate();
			if(res!=0)
			{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(ps!=null)
				ps.close();
			//con.close();
		}
		return false;
	}
	public ProfileImage getProfileImage(int user_id) throws SQLException
	{
		PreparedStatement ps = null;
		con = DatabaseHandlerSingleton.getDBConnection();
		ResultSet rs = null;
		ProfileImage pi = null;
		try {
			ps = con.prepareStatement(SELECT);
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			if(rs.first())
			{
				pi = new ProfileImage();
				pi.setImg_id(rs.getInt(1));
				InputStream in = rs.getBinaryStream(2);
				byte image[] = new byte[in.available()];
				in.read(image);
				pi.setImg(image);
				pi.setSize(rs.getInt(3));
				return pi;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(ps!=null)
				ps.close();
			//con.close();
		}
		return pi;
	}
}
