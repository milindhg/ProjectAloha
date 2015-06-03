package com.aloha.controller;


import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.entities.user.User;
import com.aloha.common.model.CommentUI;
import com.aloha.common.model.DislikeUI;
import com.aloha.common.model.GetCommentUI;
import com.aloha.common.model.GetLikeStatusUI;
import com.aloha.common.model.GetPostUI;
import com.aloha.common.model.GetPostsUI;
import com.aloha.common.model.GetStatusUI;
import com.aloha.common.model.LikeUI;
import com.aloha.common.model.PostUI;
import com.aloha.common.model.UserUI;
import com.mysql.jdbc.StringUtils;


@Controller
@SessionAttributes("sessionUser")
public class PostController implements IPostController {
	
	//@RequestMapping(value = "post/getAll", method=RequestMethod.POST)
	public @ResponseBody GetPostsUI getAllPosts(@RequestParam("datetime") String searchKey, Model model, HttpSession session) {
		
		GetPostsUI gui = new GetPostsUI();
		UserUI userUIInSession = (UserUI) session.getAttribute("sessionUser");
		
		PostUI pui = new PostUI();
		
		ArrayList<PostUI> posts;
		try {
			
			posts = pui.getPostsForUserAndFriends(userUIInSession, searchKey);
			gui.setPosts(posts);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gui;
	}
	
//	@RequestMapping("post")
//	public String setup(Locale locale, Model model) throws SQLException{
//		
//		return "postContainer";
//	}
	
	//@RequestMapping(value="post/add", method=RequestMethod.POST)
	public @ResponseBody GetPostUI addPost(@RequestParam("postData") String post, HttpSession session) {
		
		User u = new User();
		GetPostUI gui = new GetPostUI();
		
		try {
		UserUI userUIInSession = (UserUI) session.getAttribute("sessionUser");
			
		PostUI pui = new PostUI();
		pui = pui.addPost(post, userUIInSession);
		gui.setPost(pui);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gui;
	}
	
	//@RequestMapping(value="comm/add", method=RequestMethod.POST)
	public @ResponseBody GetCommentUI addComment(@RequestParam("commentData") String comm, @RequestParam("postId") int postId, HttpSession session) {
		
		UserUI userUIInSession = (UserUI) session.getAttribute("sessionUser");
		
		CommentUI cui = new CommentUI(comm,userUIInSession.getUserId(),postId);
		GetCommentUI gui = new GetCommentUI();
		try {
			cui = cui.addComment(cui);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui.setComment(cui);
		return gui;
	}
	
	//@RequestMapping(value="post/del", method=RequestMethod.POST)
	public @ResponseBody GetStatusUI deletePost(@RequestParam("postId") int postId, HttpSession session){
		PostUI pui = new PostUI();
		GetStatusUI gui = new GetStatusUI();
		boolean result = false;
		try {
			result = pui.deletePost(postId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui.setActionStatus(result);
		return gui;
	}
	
	//@RequestMapping(value="comm/del", method=RequestMethod.POST)
	public @ResponseBody GetStatusUI deleteComment(@RequestParam("commId") int commId, HttpSession session){
		CommentUI cui = new CommentUI();
		GetStatusUI gui = new GetStatusUI(); 

		boolean result = false;
		try {
			result = cui.deleteComment(commId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui.setActionStatus(result);
		return gui;
	}
	
	//@RequestMapping(value="post/like", method=RequestMethod.POST)
	public @ResponseBody GetLikeStatusUI likePost(@RequestParam("postId") int postId,@RequestParam("likeType") int likeType,HttpSession session ){
		LikeUI lui = new LikeUI();
		UserUI userUIInSession = (UserUI) session.getAttribute("sessionUser");
		
		GetLikeStatusUI gui = new GetLikeStatusUI();
		int result = -1;
		try {
			result = lui.toggleLike(likeType, postId, userUIInSession.getUserId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui.setLikeType(result);
		return gui;
	}
	
	//@RequestMapping(value="post/dislike", method=RequestMethod.POST)
	public @ResponseBody GetLikeStatusUI dislikePost(@RequestParam("postId") int postId,@RequestParam("likeType") int dislikeType, HttpSession session){
		DislikeUI lui = new DislikeUI();
		UserUI userUIInSession = (UserUI) session.getAttribute("sessionUser");
		
		GetLikeStatusUI gui = new GetLikeStatusUI();
		int result = -1;
		try {
			result = lui.toggleDislike(dislikeType, postId, userUIInSession.getUserId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui.setLikeType(result);
		return gui;
	}
}
