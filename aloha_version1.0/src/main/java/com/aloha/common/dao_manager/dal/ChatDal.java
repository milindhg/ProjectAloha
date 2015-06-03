package com.aloha.common.dao_manager.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
import com.aloha.common.entities.Chat;

public class ChatDal {
	Connection con = null;
	// write the queries for Post table
	private String SELECT;
	private String INSERT_CHAT;
	private String DELETE_CHAT;
	private String GET_NAME;

	public ChatDal() {
		SELECT = "SELECT chat.chat_id,chat.chatContent,chat.timestamp,chat.user_id1,chat.user_id2 FROM chat WHERE (chat.user_id1=? AND chat.user_id2=?)OR (chat.user_id1=? AND chat.user_id2=?) order by chat_id desc limit 10;";
		INSERT_CHAT = "INSERT INTO chat (chat_id,chatContent,timestamp,user_id1,user_id2) VALUES ( ?, ?, ?,?,?);";
		DELETE_CHAT = "DELETE chat post WHERE chat.chat_id = ?;";
		GET_NAME = "SELECT user.fname,user.lname FROM user WHERE user.user_id=?;";
		con = DatabaseHandlerSingleton.getDBConnection();
	}

	/**
	 * @return List of users
	 * @throws SQLException
	 */
	public ArrayList<Chat> selectRecentFive(int userId1, int userId2)
			throws SQLException {
		String SelectUsersAllStatement = SELECT;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersAllStatement);
			ps.setInt(1, userId1);
			ps.setInt(2, userId2);
			ps.setInt(3, userId2);
			ps.setInt(4, userId1);
			rSet = ps.executeQuery();
			ArrayList<Chat> fiveChats = new ArrayList<Chat>();
			while (rSet.next()) {
				Chat u = new Chat(rSet.getInt("chat_id"),
						rSet.getString("chatContent"),
						rSet.getTimestamp("timestamp"),
						rSet.getInt("user_id1"), rSet.getInt("user_id2"));
				fiveChats.add(u);
			}
			return fiveChats;
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

	public int insertChat(Chat u) throws SQLException {
		con = DatabaseHandlerSingleton.getDBConnection();
		String insertUserStatement = INSERT_CHAT;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(insertUserStatement);
			ps.setInt(1, u.getChatID());
			ps.setString(2, u.getChatContent());
			ps.setDate(3, (java.sql.Date) u.getTimestamp());
			ps.setInt(4, u.getUserID1());
			ps.setInt(5, u.getUserID2());

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

	public String getChatUserByID(int id) throws SQLException {
		String getUserStatement = GET_NAME;
		PreparedStatement ps = null;
		ResultSet result = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(getUserStatement);
			ps.setInt(1, id);
			result = ps.executeQuery();
			String fullname=null;
			if (result.next()) {
				fullname = result.getString("fname") +" "+result.getString("lname") ;
			}
			return fullname;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			//con.close();
		}
	}

	public int deleteChat(int id) throws SQLException {
		String updateUserStatement = DELETE_CHAT;
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
			//con.close();
		}
	}
}
