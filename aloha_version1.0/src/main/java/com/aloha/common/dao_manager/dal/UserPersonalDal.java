package com.aloha.common.dao_manager.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
import com.aloha.common.entities.user.UserPersonal;

public class UserPersonalDal {
	Connection con = null;
	private String SELECT;
	private String INSERT;
	private String UPDATE;
	private String DELETE;
	public UserPersonalDal() {
		SELECT = "SELECT info_id, user_id, about_me, current_city FROM personalinfo;";
		INSERT = "INSERT INTO personalinfo(user_id,about_me,current_city) VALUES (?, ?, ?);";
		UPDATE = "UPDATE personalinfo SET about_me = ?, current_city = ? WHERE user_id = ?;";
		DELETE = "DELETE FROM personal_info WHERE user_id = ?;";
		con = DatabaseHandlerSingleton.getDBConnection();
	}
	public UserPersonal selectUserPersonalById(int user_id) throws SQLException {
		String SelectUsersPersonalAllStatement = "select info_id, about_me,current_city from personalinfo where user_id=?;";
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersPersonalAllStatement);
			ps.setInt(1, user_id);
			rSet = ps.executeQuery();
			UserPersonal u = null;
			if (rSet.first()) {
				u  = new UserPersonal();
				u.setP_id(rSet.getInt("info_id"));
				u.setAboutme(rSet.getString("about_me"));
				u.setCity(rSet.getString("current_city"));
				
			}
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rSet != null)
				rSet.close();
			if (ps != null)
				ps.close();
			//con.close();
		}
	}

	public UserPersonal addPersonalInfo(int user_id, UserPersonal pu) throws SQLException{
		con = DatabaseHandlerSingleton.getDBConnection();
		String insertstatement = INSERT;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		UserPersonal p = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(insertstatement);
			ps.setInt(1, user_id);
			ps.setString(2, pu.getAboutme());
			ps.setString(3, pu.getCity());
			result = ps.executeUpdate();
			if(result!=0)
			{
				ps1 = con.prepareStatement("SELECT info_id from personalinfo where user_id=?;");
				ps1.setInt(1, user_id);
				rs = ps1.executeQuery();
				if(rs.first())
				{
					pu.setP_id(rs.getInt(1));
				}	
			}
			return pu;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			//con.close();
		}		
	}
	public int deleteUserPersonal(int id) throws SQLException {
		String deletestatement = DELETE;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(deletestatement);
			ps.setInt(1, id);
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			//con.close();
		}
	}
	public int updateUserPersonal(int id, UserPersonal pu) throws SQLException {
		String updatestatement = UPDATE;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(updatestatement);
			ps.setString(1, pu.getAboutme());
			ps.setString(2,  pu.getCity());
			ps.setInt(3, id);
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {			
			if (ps != null)
				ps.close();
			//con.close();
		}
	}
}
