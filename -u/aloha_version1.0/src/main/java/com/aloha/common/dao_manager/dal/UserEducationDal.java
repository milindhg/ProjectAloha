package com.aloha.common.dao_manager.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
import com.aloha.common.entities.user.User;
import com.aloha.common.entities.user.UserEducation;

public class UserEducationDal {
	Connection con = null;
	private String SELECT;
	private String INSERT_education;
	private String UPDATE_education;
	private String DELETE_education;
	public UserEducationDal() {
		SELECT = "SELECT e_id, user_id, school, area FROM users_education";
		INSERT_education = "INSERT INTO users_education(user_id,school,area) VALUES (?, ?, ?);";
		UPDATE_education = "UPDATE users_education SET school = ?, area = ? WHERE user_id = ?;";
		DELETE_education = "DELETE FROM user_education WHERE user_id = ?;";
		con = DatabaseHandlerSingleton.getDBConnection();
	}
	public UserEducation selectUserEducationById(int user_id) throws SQLException {
		String SelectUsersEducationAllStatement = "select school,area from users_education where user_id=?;";
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersEducationAllStatement);
			ps.setInt(1, user_id);
			rSet = ps.executeQuery();
			UserEducation u = null;
			if (rSet.first()) {
				u = new UserEducation();
				u.setEducation(rSet.getString("school"), rSet.getString("area"));
				
			}
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			/*if (rSet != null)
				rSet.close();
			if (ps != null)
				ps.close();
			//con.close();*/
		}

	}
	public List<User> selectUserByEducation(UserEducation ed) throws SQLException {
		String SelectUsersByEducation = "select user from users_education where school = ? and area = ?";
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersByEducation);
			ps.setString(1, ed.getSchool());
			ps.setString(2, ed.getArea());
			rSet = ps.executeQuery();
			List<User> users = new ArrayList<User>();
			while (rSet.next()) {
				User u = new User(rSet.getInt("user_id"),
						rSet.getString("fname"), rSet.getString("lname"),
						rSet.getString("contact_number"),
						rSet.getString("email"), rSet.getString("password"),
						rSet.getDate("bdate"), rSet.getInt("isVerified"),
						rSet.getInt("isLocked"), rSet.getDate("lastActive"));
				users.add(u);
			}
			return users;
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
	public int addEducationInfo(int user_id, UserEducation ed) throws SQLException{
		con = DatabaseHandlerSingleton.getDBConnection();
		String insertstatement = INSERT_education;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(insertstatement);
			ps.setInt(1, user_id);
			ps.setString(2, ed.getSchool());
			ps.setString(3, ed.getArea());
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
	public int deleteUserEducation(int id) throws SQLException {
		String deletestatement = DELETE_education;
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
	public int updateUserEducation(int id, UserEducation ed) throws SQLException {
		String updatestatement = UPDATE_education;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(updatestatement);
			ps.setString(1, ed.getSchool());
			ps.setString(2, ed.getArea());
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
