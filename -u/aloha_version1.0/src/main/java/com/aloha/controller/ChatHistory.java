package com.aloha.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aloha.common.dao_manager.dal.ChatDal;
import com.aloha.common.entities.Chat;
import com.aloha.common.model.ChatUI;

@Controller
@SessionAttributes("sessionUser")
public class ChatHistory {
	// Getting DAL
	ChatUI cDal = new ChatUI();
	
	@RequestMapping(value = "chathistory", method=RequestMethod.POST)
	public @ResponseBody ArrayList<ChatUI> home(@RequestParam("touser") int ToUserID,
			@RequestParam("fromuser") int FromUserID) throws SQLException {

		// Getting Info from Database
			ArrayList<ChatUI> recentFivechat = cDal.getChatsForUser(ToUserID, FromUserID);
			
				
		return recentFivechat;
	}

}
