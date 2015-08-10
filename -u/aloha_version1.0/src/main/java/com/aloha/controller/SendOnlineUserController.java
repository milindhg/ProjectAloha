package com.aloha.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aloha.common.entities.OnlineUsers;
import com.aloha.common.entities.user.User;
import com.aloha.common.model.UserUI;

@Controller
@SessionAttributes("sessionUser")
public class SendOnlineUserController {

	
	@RequestMapping(value = "onlineUsers", method=RequestMethod.POST)
	public @ResponseBody ArrayList<User> getOnlineUsers(Model model,HttpSession session) throws SQLException { 
		
		/*if(null==session.getAttribute("sessionUser")){
			model.addAttribute("globalstatus","login");
			model.addAttribute("globalstatuslink","login");
			
			return "redirect:" + "login";
		}*/
		UserUI curSessionUser = (UserUI) session.getAttribute("sessionUser");
		
		
		ArrayList<User> onlineUsers = null;
		OnlineUsers olUsers = new OnlineUsers();
		if(curSessionUser!=null){
		try{
		onlineUsers = olUsers.getOnlineFriends(curSessionUser.getUserId());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
		/*
		 * onlineUsers.add(u1); onlineUsers.add(u2); onlineUsers.add(u3);
		 */
		/*model.addAttribute("onlineUsers", onlineUsers);
		model.addAttribute("globalstatus","logout");
		model.addAttribute("globalstatuslink","logout");*/
		
		return onlineUsers;
	}
	
	
	/*@RequestMapping(value = "removeUserFromSession", method=RequestMethod.POST)
	public @ResponseBody void removeUserOnBrowserClose(Model model,HttpSession session) throws SQLException { 
		
		UserUI curSessionUser = (UserUI) session.getAttribute("sessionUser");
		
		OnlineUsers olUsers = new OnlineUsers();
		olUsers.deleteUserWhoIsOnline(curSessionUser.getUserId());
		
	
	}*/
}
