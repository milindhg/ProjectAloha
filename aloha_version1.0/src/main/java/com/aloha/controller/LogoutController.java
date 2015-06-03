package com.aloha.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
import com.aloha.common.model.UserUI;
import com.aloha.common.sal.LogoutService;

@Controller
@SessionAttributes("sessionUser")
public class LogoutController {
	@RequestMapping("logout")
	public String sign_up(Model model, HttpSession session) {
		LogoutService logoutService = new LogoutService();
		if(null==session.getAttribute("sessionUser")){
			
		}	
		else
		{
			UserUI ui = (UserUI)session.getAttribute("sessionUser");
			logoutService.setLastActive(ui);
			model.addAttribute("globalstatus","login");
			model.addAttribute("globalstatuslink","login");
		}
		//UserUI ui=null;
		//session.setAttribute("sessionUser", null);
		session.invalidate();
		model.asMap().clear();
		DatabaseHandlerSingleton.close();
		return "Login";
	}
}
