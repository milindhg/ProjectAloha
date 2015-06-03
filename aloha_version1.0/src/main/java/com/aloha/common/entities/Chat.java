package com.aloha.common.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.aloha.common.dao_manager.dal.ChatDal;

public class Chat {
	private int chatID;
	private String chatContent;
	private Date timestamp;
	private int userID1;
	private int userID2;
	private ChatDal dal;
	
	public Chat(int chatID, String chatContent, Date timestamp, int userID1,
			int userID2) {
		this.chatID = chatID;
		this.chatContent = chatContent;
		this.timestamp = timestamp;
		this.userID1 = userID1;
		this.userID2 = userID2;
		this.dal=new ChatDal();
	}
	public Chat() {
		this.chatID = 0;
		this.chatContent = null;
		this.timestamp = null;
		this.userID1 = 0;
		this.userID2 = 0;
		this.dal=new ChatDal();
	}
	public int getChatID() {
		return chatID;
	}
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
	public String getChatContent() {
		return chatContent;
	}
	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getUserID1() {
		return userID1;
	}
	public void setUserID1(int userID1) {
		this.userID1 = userID1;
	}
	public int getUserID2() {
		return userID2;
	}
	public void setUserID2(int userID2) {
		this.userID2 = userID2;
	}
	
	public int addChat(Chat chat) throws SQLException {
		
		int success = dal.insertChat(chat);
		return success;
	}

	public boolean deleteChat(int chatId) throws SQLException {
		int result = dal.deleteChat(chatId);
		if (result == 1)
			return true;
		else
			return false;
	}
	
	public String getFullNameByUID(int id) throws SQLException {
		String result = dal.getChatUserByID(id);
		
			return result;
	}

	public ArrayList<Chat> getRecentFiveForChatUser(int userId1,int userId2) throws SQLException {
		ArrayList<Chat> chats = new ArrayList<Chat>();
		chats = dal.selectRecentFive(userId1,userId2);

		return chats;
	}
}
