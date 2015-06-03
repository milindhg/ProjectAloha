package com.aloha.common;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aloha.common.dao_manager.dal.ChatDal;
import com.aloha.common.entities.Chat;
import com.aloha.common.model.ChatUI;

import java.sql.*;

public class TestChatDBJHandler {

	
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		//addChatTest();
		//selectChatTest();
		ChatUI yiee=new ChatUI();
		System.out.println(yiee.getNameById(6));
		ArrayList<ChatUI> ar=new ArrayList<ChatUI>();
		ar=yiee.getChatsForUser(4, 5);
		for (ChatUI chat2 : ar) {
			System.out.println(chat2.getChatContent());
		}
		
	}
	
	public static void addChatTest()
	{
		ChatDal pDal = new ChatDal();
		
		try{
		Chat chat = new Chat(1,"Hello Vivek",new Date(new java.util.Date().getTime()),4,5);
		int success = pDal.insertChat(chat);
		if(success == 1)
			System.out.println("Chat 1 added !!");
		else System.out.println("Chat 1 add failed !!");
		
		Chat chat1= new Chat(2,"Hello Renuka",new Date(new java.util.Date().getTime()),5,4);
		success = pDal.insertChat(chat1);
		if(success == 1)
			System.out.println("Chat 2 added !!");
		else System.out.println("Chat 2 add failed !!");
		
		Chat chat3 = new Chat(3,"Where are you?",new Date(new java.util.Date().getTime()),4,5);
		success = pDal.insertChat(chat3);
		if(success == 1)
			System.out.println("Chat 3 added !!");
		else System.out.println("Chat 3 add failed !!");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void selectChatTest()
	{
		ChatDal pDal = new ChatDal();
		
		try{
			ArrayList<Chat> chat = pDal.selectRecentFive(5, 8);
			for (Chat chat2 : chat) {
				System.out.println(chat2.getChatContent());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
