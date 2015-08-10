package com.aloha.common.dao_manager.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
import com.aloha.common.entities.user.User;

/**
 * @author Milind
 *
 */
public class UserDal {

	Connection con = null;
	// write the queries for User table
	private String SELECT;
	private String INSERT_USER;
	private String UPDATE_USER;
	private String DELETE_USER;
	private String SELECT_LOGIN;

	/**
	 * Constructor
	 */
	public UserDal() {
		SELECT = "SELECT user.user_id, user.fname, user.lname, user.contact_number, user.email, user.password, user.bdate, user.isVerified, user.isLocked, user.lastactive, user.privacy FROM user";
		INSERT_USER = "INSERT INTO user(fname, lname, contact_number, email, password, bdate, isVerified, isLocked, lastactive) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		UPDATE_USER = "UPDATE user SET fname = ?, lname = ?, contact_number = ?, email = ?, password = ?, bdate = ?, isVerified = ?, isLocked = ?, lastactive = ? WHERE user_id = ?;";
		DELETE_USER = "DELETE FROM user WHERE user_id = ?;";
		con = DatabaseHandlerSingleton.getDBConnection();
	}

	/**
	 * @return List of users
	 * @throws SQLException
	 */
	public ArrayList<User> selectUserAll() throws SQLException {
		String SelectUsersAllStatement = SELECT;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersAllStatement);
			rSet = ps.executeQuery();
			ArrayList<User> users = new ArrayList<User>();
			if (rSet != null) {
				while (rSet.next()) {
					User u = new User();
					u.setUserId(rSet.getInt("user_id"));
					u.setFirstName(rSet.getString("fname"));
					u.setLastName(rSet.getString("lname"));
					u.setContactNumber(rSet.getString("contact_number"));
					u.setEmail(rSet.getString("email"));
					u.setPassword(rSet.getString("password"));
					u.setDateOfBirth(rSet.getTimestamp("bdate"));
					u.setIsVerified(rSet.getInt("isVerified"));
					u.setIsLocked(rSet.getInt("isLocked"));
					u.setLastActive(rSet.getDate("lastActive"));
					users.add(u);
				}
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
			/*con.close();*/
		}

	}

	/**
	 * @return List of users
	 * @throws SQLException
	 */
	public ArrayList<User> selectMutlipleUsersByPrimaryKey(int[] ids) throws SQLException {
		String SelectUsersByPrimaryKeyStatement = SELECT;
		SelectUsersByPrimaryKeyStatement = SelectUsersByPrimaryKeyStatement + " where user.user_id in (?,";
		for(int i=1; i<ids.length; i++){
			SelectUsersByPrimaryKeyStatement = SelectUsersByPrimaryKeyStatement + "?,";
		}
		int lastIndexOfComma = SelectUsersByPrimaryKeyStatement.lastIndexOf(",");
		SelectUsersByPrimaryKeyStatement=SelectUsersByPrimaryKeyStatement.substring(0, lastIndexOfComma);
		SelectUsersByPrimaryKeyStatement = SelectUsersByPrimaryKeyStatement + ");";
				
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersByPrimaryKeyStatement);
			for(int i=0; i<ids.length; i++){
				ps.setInt(i+1, ids[i]);
			}
			rSet = ps.executeQuery();
			ArrayList<User> users = new ArrayList<User>();
			if (rSet != null) {
				while (rSet.next()) {
					User u = new User();
					u.setUserId(rSet.getInt("user_id"));
					u.setFirstName(rSet.getString("fname"));
					u.setLastName(rSet.getString("lname"));
					u.setContactNumber(rSet.getString("contact_number"));
					u.setEmail(rSet.getString("email"));
					u.setPassword(rSet.getString("password"));
					u.setDateOfBirth(rSet.getTimestamp("bdate"));
					u.setIsVerified(rSet.getInt("isVerified"));
					u.setIsLocked(rSet.getInt("isLocked"));
					u.setLastActive(rSet.getDate("lastActive"));
					u.setPrivacy(rSet.getInt("privacy"));
					users.add(u);
				}
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
			/*con.close();*/
		}

	}

	public User selectUserByPrimaryKey(int id) throws SQLException {
		String SelectUsersByPrimaryKeyStatement = SELECT
				+ " where user.user_id=?;";
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersByPrimaryKeyStatement);
			ps.setInt(1, id);
			rSet = ps.executeQuery();
			if (rSet.next()) {
				User u = new User();
				u.setUserId(rSet.getInt("user_id"));
				u.setFirstName(rSet.getString("fname"));
				u.setLastName(rSet.getString("lname"));
				u.setContactNumber(rSet.getString("contact_number"));
				u.setEmail(rSet.getString("email"));
				u.setPassword(rSet.getString("password"));
				u.setDateOfBirth(rSet.getDate("bdate"));
				u.setIsVerified(rSet.getInt("isVerified"));
				u.setIsLocked(rSet.getInt("isLocked"));
				u.setLastActive(rSet.getDate("lastActive"));
				u.setPrivacy(rSet.getInt("privacy"));
				return u;
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(rSet != null)
				rSet.close();
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
	}

	public int insertUser(User u) throws SQLException {
		con = DatabaseHandlerSingleton.getDBConnection();
		String insertUserStatement = INSERT_USER;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(insertUserStatement);
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3, u.getContactNumber());
			ps.setString(4, u.getEmail());
			ps.setString(5, u.getPassword());
			long mili = 0;
			mili = u.getDateOfBirth().getTime();
			Timestamp ts = new Timestamp(mili);
			ps.setTimestamp(6, ts);
			ps.setInt(7, u.getIsVerified());
			ps.setInt(8, u.getIsLocked());			
			ps.setTimestamp(9, new Timestamp(u.getLastActive().getTime()));

			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
	}

	public int updateUser(User u) throws SQLException {
		String updateUserStatement = UPDATE_USER;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(updateUserStatement);
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3, u.getContactNumber());
			ps.setString(4, u.getEmail());
			ps.setString(5, u.getPassword());
			ps.setDate(6, (java.sql.Date) u.getDateOfBirth());
			ps.setInt(7, u.getIsVerified());
			ps.setInt(8, u.getIsLocked());
			ps.setDate(9, (java.sql.Date) u.getLastActive());
			ps.setInt(10, u.getUserId());
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
	}

	public int deleteUser(int id) throws SQLException {
		String updateUserStatement = DELETE_USER;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(updateUserStatement);
			ps.setInt(1, id);
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
	}

	public User getPasswordByEmail(String email, String pwd)
			throws SQLException {
		String query = "select * from user where user.email = ? and user.password = ?;";
		PreparedStatement ps = null;
		ResultSet res = null;
		User u = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, pwd);
			res = ps.executeQuery();
			if (res.first()) {
				u = new User();
				u.setUserId(res.getInt("user_id"));
				u.setFirstName(res.getString("fname"));
				u.setLastName(res.getString("lname"));
				u.setContactNumber(res.getString("contact_number"));
				u.setEmail(res.getString("email"));
				u.setPassword(res.getString("password"));
				u.setDateOfBirth(res.getDate("bdate"));
				u.setIsVerified(res.getInt("isVerified"));
				u.setIsLocked(res.getInt("isLocked"));
				u.setLastActive(res.getDate("lastActive"));
				u.setPrivacy(res.getInt("privacy"));
			} else
				u = null;
		} catch (SQLException ex) {
			u = null;
			System.out.println("" + ex.getMessage());
			throw ex;
		} finally {
			/*if (ps != null)
				ps.close();
			con.close();*/
		}

		return u;
	}

	public ArrayList<User> selectUsersByName(String name) throws SQLException {
		String SelectUsersByPrimaryKeyStatement = SELECT
				+ " where user.fname like ? or user.lname like ? ;";
		PreparedStatement ps = null;
		String likeClause = "%" + name + "%";
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersByPrimaryKeyStatement);
			ps.setString(1, likeClause);
			ps.setString(2, likeClause);
			rSet = ps.executeQuery();
			ArrayList<User> users = new ArrayList<User>();
			if (rSet != null) {
				while (rSet.next()) {
					User u = new User();
					u.setUserId(rSet.getInt("user_id"));
					u.setFirstName(rSet.getString("fname"));
					u.setLastName(rSet.getString("lname"));
					u.setContactNumber(rSet.getString("contact_number"));
					u.setEmail(rSet.getString("email"));
					u.setPassword(rSet.getString("password"));
					u.setDateOfBirth(rSet.getTimestamp("bdate"));
					u.setIsVerified(rSet.getInt("isVerified"));
					u.setIsLocked(rSet.getInt("isLocked"));
					u.setLastActive(rSet.getTimestamp("lastActive"));
					u.setPrivacy(rSet.getInt("privacy"));
					users.add(u);
				}
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
			/*con.close();*/
		}
	}

	public boolean checkIfUniqueEmail(String email) throws SQLException {
		String selectemail = "select * from user where user.email = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(selectemail);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
		return false;
	}

	public void lockaccount(String email, String newpwd) throws SQLException {
		// TODO Auto-generated method stub
		String update = "UPDATE user SET isLocked = ? , password = ? WHERE email = ?;";
		PreparedStatement ps = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(update);
			ps.setInt(1, 1);
			ps.setString(2, newpwd);
			ps.setString(3, email);
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
	}

	public int updateAccountInfo(User u) throws SQLException {
		// TODO Auto-generated method stub
		String updateUserStatement = UPDATE_USER;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement("UPDATE user SET fname = ?, lname = ?, contact_number = ?, bdate = ? WHERE user_id = ?;");
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3, u.getContactNumber());
			long mili = u.getDateOfBirth().getTime();
			Timestamp ts = new Timestamp(mili);
			ps.setTimestamp(4,ts);
			ps.setInt(5, u.getUserId());
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
	}

	public int setLastActive(int userId, Timestamp lastactive) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement("UPDATE user SET lastactive=? WHERE user_id = ?;");
			ps.setTimestamp(1,lastactive);
			ps.setInt(2, userId);
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
	}

	public int isEmailLocked(String email) throws SQLException {
		// TODO Auto-generated method stub
		String select = "Select isLocked from user WHERE email = ?;";
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(select);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.first())
			{
				return rs.getInt(1);
			}
			else
				return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			
			if (ps != null)
				ps.close();
			/*con.close();*/
		}

		return -1;
	}

	public User getUserIdByEmail(String email) throws SQLException {
		// TODO Auto-generated method stub
		String select = "SELECT user.user_id, user.fname, user.lname, user.contact_number, user.email, user.password, user.bdate, user.isVerified, user.isLocked, user.lastactive, user.privacy FROM user WHERE email = ?;";
		ResultSet rSet = null;
		PreparedStatement ps = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(select);
			ps.setString(1, email);
			rSet = ps.executeQuery();
			if(rSet.first())
			{
				User u = new User();
				u.setUserId(rSet.getInt("user_id"));
				u.setFirstName(rSet.getString("fname"));
				u.setLastName(rSet.getString("lname"));
				u.setContactNumber(rSet.getString("contact_number"));
				u.setEmail(rSet.getString("email"));
				u.setPassword(rSet.getString("password"));
				u.setDateOfBirth(rSet.getTimestamp("bdate"));
				u.setIsVerified(rSet.getInt("isVerified"));
				u.setIsLocked(rSet.getInt("isLocked"));
				u.setLastActive(rSet.getTimestamp("lastActive"));
				u.setPrivacy(rSet.getInt("privacy"));
				return u;
			}
			else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rSet != null)
				rSet.close();
			
			if (ps != null)
				ps.close();
			/*con.close();*/
		}

		return null;
	}

	public int setUserPassword(int userId, String pwd) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement("UPDATE user SET password=? WHERE user_id = ?;");
			ps.setString(1,pwd);
			ps.setInt(2, userId);
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
		
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
	}

	public void unlockAccount(int userId) throws SQLException {
		// TODO Auto-generated method stub
		String update = "UPDATE user SET isLocked = ? WHERE user_id = ?;";
		PreparedStatement ps = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(update);
			ps.setInt(1, 0);
			ps.setInt(2, userId);
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null)
				ps.close();
			/*con.close();*/
		}

	}
	public int setPrivacy(int userId, int privacy) throws SQLException{
		String update = "UPDATE user SET privacy = ? WHERE user_id = ?;";
		PreparedStatement ps = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(update);
			ps.setInt(1, privacy);
			ps.setInt(2, userId);
			int res = ps.executeUpdate();
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			if (ps != null)
				ps.close();
			/*con.close();*/
		}
		return 0;
	}
}
