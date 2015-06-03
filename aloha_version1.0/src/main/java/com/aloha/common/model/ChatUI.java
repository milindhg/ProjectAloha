package com.aloha.common.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.aloha.common.entities.Chat;

public class ChatUI {
	private String chatContent;
	private String timestamp;
	private int userID1;
	private int userID2;
	public String getChatContent() {
		return chatContent;
	}
	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
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
	
public ArrayList<ChatUI> getChatsForUser(int user1,int user2){
		
		ArrayList<ChatUI> userChats = new ArrayList<ChatUI>();
		Chat p = new Chat();
		ArrayList<Chat> chats;
		try {
			chats = p.getRecentFiveForChatUser(user1,user2);
			for (Chat chat : chats) {
				ChatUI cui = new ChatUI();
				cui.setUserID1(chat.getUserID1());
				cui.setUserID2(chat.getUserID2());
				cui.setTimestamp(Helper.getLocalDate(chat.getTimestamp()));
				cui.setChatContent(chat.getChatContent());
				userChats.add(cui);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Collections.sort(userChats, new Comparator<ChatUI>() {
		    public int compare(ChatUI a, ChatUI b) {
		    	String time1 = a.getTimestamp();
				String time2 = b.getTimestamp();

				// ascending order (descending order would be: name2.compareTo(name1))
				return time1.compareTo(time2);
		    }
		   
		});
		
		return userChats;
		
	}

public int addChat(Chat chat) throws SQLException {
	Chat p = new Chat();
	int success = p.addChat(chat);
	return success;
}

public String getNameById(int uid) throws SQLException {
	Chat p = new Chat();
	String success = p.getFullNameByUID(uid);
	return success;
}

}
