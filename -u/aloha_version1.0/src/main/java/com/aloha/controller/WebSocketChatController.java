package com.aloha.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aloha.common.entities.Chat;
import com.aloha.common.entities.ChatToken;
import com.aloha.common.entities.OnlineUsers;
import com.aloha.common.entities.user.User;
import com.aloha.common.model.ChatUI;
import com.aloha.common.model.UserUI;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*import com.aloha.chat.Data.MessageDecoder;
 import com.aloha.chat.Data.MessageEncoder;*/

/**
 * @ServerEndpoint gives the relative name for the end point This will be
 *                 accessed via ws://localhost:8080/EchoChamber/echo Where
 *                 "localhost" is the address of the host, "EchoChamber" is the
 *                 name of the package and "echo" is the address to access this
 *                 class from the server
 */

@SessionAttributes("sessionUser")
@Controller
@ServerEndpoint(value = "/websocket/{clientId}")
public class WebSocketChatController {

	// private static Set<Session> clients = Collections.synchronizedSet(new
	// HashSet<Session>());
	private static volatile Map<Integer, Session> userIDToSessionMap = new ConcurrentHashMap<Integer, Session>();
	//static int uID = 100;
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * @OnOpen allows us to intercept the creation of a new session. The session
	 *         class allows us to send data to the user. In the method onOpen,
	 *         we'll let the user know that the handshake was successful.
	 */
	@OnOpen
	public void onOpen(@PathParam("clientId") int clientId, Session session) {
		System.out.println(session.getId() + " has opened a connection");
		System.out.println(clientId + ": =>" + session);
		userIDToSessionMap.put(clientId, session);
		// System.out.println(sesi.getAttribute("sessionUser"));
		/*
		 * try { session.getBasicRemote().sendText("Connection Established"); }
		 * catch (IOException ex) { ex.printStackTrace(); }
		 */
	}

	/**
	 * When a user sends a message to the server, this method will intercept the
	 * message and allow us to react to it. For now the message is read as a
	 * String.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws SQLException
	 * @throws JSONException
	 * 
	 * @throws ParseException
	 */
	@OnMessage
	public void onMessage(String message, Session session)
			throws JsonParseException, JsonMappingException, IOException,
			SQLException {

		// JSON Decoding
		ChatToken userChat;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		userChat = mapper.readValue(message, ChatToken.class);

		// Putting JSON to Local Variables For Manipulation
		String chatMessage = userChat.getChatMsg();
		int ToUserID = userChat.getToUserID();
		int FromUserID = userChat.getUserID();
		System.out.println(chatMessage + "+" + ToUserID + "+" + FromUserID);
		System.out.println(userIDToSessionMap.get(ToUserID));

		// Map to JSON to Send
		ChatUI chatty = new ChatUI();
		Map<String, String> map = new HashMap<String, String>();
		map.put("userID", Integer.toString(FromUserID));
		map.put("toUserID", Integer.toString(ToUserID));
		map.put("chatMsg", chatMessage);
		map.put("Sendername", chatty.getNameById(FromUserID));
		String sendMsgJson = mapper.writeValueAsString(map);

		// Setting Chat Object to insert into database
		Chat cobj = new Chat();
		cobj.setUserID1(ToUserID);
		cobj.setUserID2(FromUserID);
		cobj.setChatContent(chatMessage);

		ChatUI cui = new ChatUI();
		cui.addChat(cobj);
		// Routing Starts
		System.out.println(userIDToSessionMap.keySet());
		System.out.println(userIDToSessionMap.values());
		if (userIDToSessionMap.containsKey(ToUserID)) {
			try {

				userIDToSessionMap.get(ToUserID).getBasicRemote()
						.sendText(sendMsgJson);
				System.out.println("Message sent to " + ToUserID + ": "
						+ sendMsgJson);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	/**
	 * The user closes the connection.
	 * 
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(@PathParam("clientId") int clientId, Session session) {
		userIDToSessionMap.remove(clientId);
		System.out.println("Socket Session " + session.getId() + " has ended");

	}
	
	
	 @OnError
	    public void onError(Session session, Throwable t) {
		 	
		 System.out.println("Browser Closed");
	    }
}
