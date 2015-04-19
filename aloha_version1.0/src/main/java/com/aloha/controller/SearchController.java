package com.aloha.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.common.dao_manager.dal.FriendshipDal;
import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.entities.Friendship;
import com.aloha.common.entities.user.User;
import com.aloha.common.model.UserUI;
import com.aloha.common.util.CommonUtils;

@Controller
public class SearchController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserSignupController.class);
	CommonUtils commonUtils = new CommonUtils();

	@RequestMapping(value = "search/users", method = RequestMethod.GET)
	public String searchUsers(Locale locale, Model model, HttpSession session) {
		logger.info("Entered Search Users GET");

		return "search/users";
	}

	/*
	 * @RequestMapping(value = "To be changed to later > search/users", method =
	 * RequestMethod.POST) public @ResponseBody String OldsearchUsers(
	 * 
	 * @RequestParam("searchKey") String searchKey, Model model) {
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * StringBuilder string = searchUserResultBuilder(ulist); return
	 * string.toString(); }
	 */
	@RequestMapping(value = "search/users", method = RequestMethod.POST)
	public @ResponseBody ArrayList<User> searchUsers(
			@RequestParam("searchKey") String searchKey, Model model) {
		logger.info("Entered Search Users POST method");
		ArrayList<User> ulist = null;
		ArrayList<UserUI> uiList = null;
		UserDal ud = new UserDal();
		try {
			ulist = ud.selectUsersByName(searchKey);

			model.addAttribute("users", ulist);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO then create a global user view which will also fetch the users
		// from the DB and their friendship from the DB. If friendship exists
		// then add friend button should not be shown.
		// return ulist;
		return ulist;
	}

	@RequestMapping(value = "profile", method = RequestMethod.GET)
	public String display_user_profile(@RequestParam("userId") int id,
			Model model, HttpSession session) throws SQLException {
		User u = new User();
		FriendshipDal fdal = new FriendshipDal();
		Friendship f = new Friendship();
		int userInSessionId = -1;
		if (null == session.getAttribute("sessionUser")) {
			return "redirect:" + "login";
		}

		UserUI userUIInSession = (UserUI) session.getAttribute("sessionUser");
		User userInSession = commonUtils.convertUserUIToUser(userUIInSession);
		if (userInSession != null) {
			userInSessionId = userInSession.getUserId();
		}
		if (userInSessionId == id) {
			try {
				u = u.getUser(userInSessionId);
				model.addAttribute("user", u);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ArrayList<Friendship> pendingRequests = null;
			pendingRequests = f.getPendingFriendshipRequest(userInSession);
			model.addAttribute("pendingFriends", pendingRequests);
			return "user_profile";
		}
		f = f.getExistingFriendship(userInSessionId, id);
		if (null != f && f.getFriendshipId() != -1) {
			if (userInSessionId == f.getUser1().getUserId()) {
				u = f.getUser2();
			} else if (userInSessionId == f.getUser2().getUserId()) {
				u = f.getUser1();
			}
		} else {
			try {
				u = u.getUser(id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("user", u);
		model.addAttribute("friendship", f);
		return "profile";
	}

	public StringBuilder searchUserResultBuilder(ArrayList<User> ulist) {
		StringBuilder returnString = new StringBuilder();
		returnString.append("<div class=\"bcol-member-block\">");
		returnString.append("<div class=\"member-image\">");
		returnString.append("<a href=\"http://feedstack.asia/milpas999\">");
		returnString
				.append("<img src=\"http://feedstack.asia/img/user.jpg\" class=\"member\">");
		returnString.append("</a>");
		returnString.append("</div>");
		returnString.append("<div class=\"member-name\">");
		returnString
				.append("<a href=\"http://feedstack.asia/milpas999\">Milind</a>");
		returnString.append("</div>");
		returnString.append("</div>");
		returnString.append("<div class=\"clear\"></div>");

		return returnString;
	}

}
