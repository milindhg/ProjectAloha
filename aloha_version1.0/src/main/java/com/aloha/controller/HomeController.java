package com.aloha.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aloha.common.entities.user.User;
import com.aloha.common.model.UserUI;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes("sessionUser")
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession session) {
		UserUI u = new UserUI();
		if (null == session.getAttribute("sessionUser")) {
			model.addAttribute("globalstatus", "login");
			model.addAttribute("globalstatuslink", "login");
			return "Login";
		} else {
			u = (UserUI) session.getAttribute("sessionUser");
			model.addAttribute("globalstatus", "logout");
			model.addAttribute("globalstatuslink", "logout");
		}
		model.addAttribute("user", u);
		return "user_profile";
	}

}
