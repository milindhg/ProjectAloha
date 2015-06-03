
package com.aloha.common.entities;

import java.util.List;

public class ChatToken{
   	private String chatMsg;
   	private int toUserID;
   	private int userID;
   	
	public String getChatMsg(){
		return this.chatMsg;
	}
	public void setChatMsg(String chatMsg){
		this.chatMsg = chatMsg;
	}
 	public int getToUserID(){
		return this.toUserID;
	}
	public void setToUserID(int toUserID){
		this.toUserID = toUserID;
	}
 	public int getUserID(){
		return this.userID;
	}
	public void setUserID(int userID){
		this.userID = userID;
	}
}
