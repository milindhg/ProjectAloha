package com.aloha.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aloha.common.entities.Friendship;
import com.aloha.common.entities.FriendshipStatus;
import com.aloha.common.entities.user.User;
import com.aloha.common.model.UserUI;
import com.aloha.common.util.CommonUtils;
import com.sun.mail.smtp.SMTPAddressFailedException;

/**
 * @author Milind FriendsController to handle the controls and flow of the
 *         friends module
 */
@Controller
@SessionAttributes("sessionUser")
public class FriendsController {

	private static final Logger logger = LoggerFactory
			.getLogger(FriendsController.class);
	CommonUtils commonUtils = new CommonUtils();
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Index page of friends for testing purposes
	 * 
	 * @param locale
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("friends/index")
	public String index(Locale locale, Model model, HttpSession session) {
		// Now is being fetched from the current logged in session user.
		UserUI testUser = null;
		// testuser = ud.selectUserByPrimaryKey(4);
		testUser = (UserUI) session.getAttribute("sessionUser");
		model.addAttribute("globalstatus", "logout");
		model.addAttribute("globalstatuslink", "logout");

		return "friends/index";
	}

	/**
	 * 
	 * friends listing Page
	 * 
	 * @param locale
	 * @param model
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "friends", method = RequestMethod.GET)
	public String displayFriends(@RequestParam("userId") String userId, Locale locale, Model model, HttpSession session)
			throws SQLException {
		User u = new User();
		Friendship f = new Friendship();

		ArrayList<User> ulist;
		if (null == session.getAttribute("sessionUser")) {
			model.addAttribute("globalstatus", "login");
			model.addAttribute("globalstatuslink", "login");

			return "redirect:" + "login";
		} else {
/*			UserUI sessionUserUI = (UserUI) session.getAttribute("sessionUser");
			ulist = f.getUserFriends(commonUtils
					.convertUserUIToUser(sessionUserUI));*/
			User user = new User();
			user.setUserId(Integer.valueOf(userId));
			user=user.getUser(Integer.valueOf(userId));
			ulist = f.getUserFriends(user);
			model.addAttribute("user", user);
			model.addAttribute("globalstatus", "logout");
			model.addAttribute("globalstatuslink", "logout");

		}
		model.addAttribute("users", ulist);

		return "friends/friends";

	}

	/**
	 * 
	 * Function to send friend request to an existing aloha user
	 * 
	 * @param userId
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "friends/add", method = RequestMethod.POST)
	public @ResponseBody int addFriend(@RequestParam("userIdToAdd") int userId,
			Model model, HttpSession session) {
		logger.info("Entered addFriend POST");
		Friendship f = new Friendship();
		int requestorId = -1;
		UserUI requestor = (UserUI) session.getAttribute("sessionUser");
		if (requestor != null) {
			requestorId = requestor.getUserId();
		} else {
			// since user is not in session do nothing. Simply reject the
			// friendship request.
			return -1;
		}
		int requesteeId = userId;
		if (f.addFriendship(requestorId, requesteeId))
			return 1;
		return -1;
	}

	/**
	 * Function to accept pending friend request
	 * 
	 * @param userId
	 * @param acceptorId
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "friends/accept", method = RequestMethod.POST)
	public @ResponseBody int acceptFriend(
			@RequestParam("userIdToAccept") int userId,
			@RequestParam("acceptor") int acceptorId, Model model,
			HttpSession session) {
		logger.info("Entered addFriend POST");
		Friendship f = new Friendship();
		f = f.getExistingFriendship(userId, acceptorId);
		f.setStatus(FriendshipStatus.Friends);
		if (f.updateFriendship(f))
			return 1;
		return -1;
	}

	/**
	 * Function to accept pending friend request
	 * 
	 * @param userId
	 * @param acceptorId
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "friends/ignore", method = RequestMethod.POST)
	public @ResponseBody int ignoreFriend(
			@RequestParam("userIdToIgnore") int userId,
			@RequestParam("acceptor") int acceptorId, Model model,
			HttpSession session) {
		logger.info("Entered ignoreFriend POST");
		Friendship f = new Friendship();
		f = f.getExistingFriendship(userId, acceptorId);
		f.setStatus(FriendshipStatus.FriendshipRequestSent);
		if (f.updateFriendship(f))
			return 1;
		return -1;
	}

	/**
	 * 
	 * Function to remove friend from friendslist
	 * 
	 * @param fId
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "friends/remove", method = RequestMethod.POST)
	public @ResponseBody int removeFriend(
			@RequestParam("friendshipIdToRemove") int fId, Model model,
			HttpSession session) {
		logger.info("Entered addFriend POST");
		Friendship f = new Friendship();
		if (f.deleteFriendship(fId))
			return 1;
		return -1;
	}

	@RequestMapping(value = "friendsinvite", method = RequestMethod.GET)
	public String inviteFriend(Locale locale, Model model, HttpSession session) {
		UserUI u = new UserUI();
		if (null == session.getAttribute("sessionUser")) {
			return "redirect:" + "login";
		} else {
			u = (UserUI) session.getAttribute("sessionUser");
		}
		model.addAttribute("user", u);

		return "friends/invite";
	}

	@RequestMapping(value = "friendsinvite", method = RequestMethod.POST)
	public @ResponseBody boolean inviteFriend(
			@RequestParam("email") String email, Model model,
			HttpSession session) {
		UserUI u = new UserUI();
		if (null == session.getAttribute("sessionUser")) {
			return false;
		} else {

			u = (UserUI) session.getAttribute("sessionUser");
			String mailContent = "Hey Checkout Aloha - A brand new social networking portal \n Click Here to Go to Aloha.com";
			if (email.contains(",")) {
				String[] emails = email.split(",");
				for (String eachEmailAddr : emails) {
					eachEmailAddr.trim();
					try {
						commonUtils.mailSendUtil(mailSender, eachEmailAddr,
								u.getEmail(), mailContent);
					} catch (SMTPAddressFailedException e) {
						e.printStackTrace();
						return false;
					}
				}
			} else {
				try{
				commonUtils.mailSendUtil(mailSender, email, u.getEmail(),
						mailContent);
				}catch(SMTPAddressFailedException e){
					e.printStackTrace();
					return false;					
				}
			}
			return true;
		}
	}

	@RequestMapping("friendsuggestions")
	public String displayFriendsSuggestions(Locale locale, Model model,
			HttpSession session) throws SQLException {
		// hashmap to store user id and count.
		HashMap<Integer, Integer> immediateFriends = new HashMap<Integer, Integer>();
		
		User u = new User();
		Friendship f = new Friendship();
		ArrayList<User> friendSuggestionList =null;
		

		ArrayList<User> ulist;
		if (null == session.getAttribute("sessionUser")) {
			model.addAttribute("globalstatus", "login");
			model.addAttribute("globalstatuslink", "login");

			return "redirect:" + "login";
		} else {
			UserUI sessionUserUI = (UserUI) session.getAttribute("sessionUser");
			int sessionUserId = sessionUserUI.getUserId();
			ulist = f.getUserFriends(commonUtils
					.convertUserUIToUser(sessionUserUI));
			for (User friend : ulist) {
				immediateFriends.putIfAbsent(friend.getUserId(), 1);
				/*
				 * if (!immediateFriends.containsKey(friend.getUserId())) {
				 * immediateFriends.put(friend.getUserId(), 1); } else {
				 * immediateFriends.put(friend.getUserId(),
				 * immediateFriends.get(friend.getUserId() + 1)); }
				 */
			}

			HashMap<Integer, Integer> totalFriendSuggestions = new HashMap<Integer, Integer>();
			for (User friend : ulist) {
				ArrayList<User> friendsOfFriends = f.getUserFriends(friend);
				for (User eachFriend : friendsOfFriends) {
					if (!immediateFriends.containsKey(eachFriend.getUserId()) && sessionUserId!=eachFriend.getUserId()) {
						if (!totalFriendSuggestions.containsKey(eachFriend
								.getUserId())) {
							totalFriendSuggestions.put(eachFriend.getUserId(),
									1);
						} else {
							totalFriendSuggestions.put(eachFriend.getUserId(),
									totalFriendSuggestions.get(eachFriend
											.getUserId() + 1));
						}
					}
				}
			}
			//get the ids of the suggested friends.
			int[] ids = friendListSorter(totalFriendSuggestions);
			friendSuggestionList = f.getFriendsSuggestions(ids);
			
			model.addAttribute("globalstatus", "logout");
			model.addAttribute("globalstatuslink", "logout");

		}
		model.addAttribute("users", friendSuggestionList);

		return "friends/suggestions";

	}

	public static int[] friendListSorter(HashMap h) {
		// HashMap h = new HashMap<Integer, Integer>();
		// h.put(1, 1);
		// h.put(2, 4);
		// h.put(3, 2);
		int[] returnList = new int[h.size()];

		Map<Integer, Integer> reversedMap = sortByValues(h);
		Set s1 = reversedMap.entrySet();
		Iterator iterator1 = s1.iterator();
		int i = 0;
		while (iterator1.hasNext()) {
			Map.Entry<Integer, Integer> me2 = (Map.Entry<Integer, Integer>) iterator1
					.next();
			returnList[i] = me2.getKey();
			i++;

			// System.out.print(me2.getKey() + ": " );
			// System.out.println(me2.getValue());
		}
		return returnList;
	}

	private static HashMap sortByValues(HashMap map) {
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

}
