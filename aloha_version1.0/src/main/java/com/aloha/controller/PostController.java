package com.aloha.controller;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.entities.Comment;
import com.aloha.common.entities.Post;
import com.aloha.common.entities.user.User;
import com.aloha.common.entities.user.UserInfo;
import com.aloha.common.model.CommentUI;
import com.aloha.common.model.DislikeUI;
import com.aloha.common.model.LikeUI;
import com.aloha.common.model.PostUI;
import com.aloha.common.model.UserUI;


@Controller
public class PostController {
	
	//@RequestMapping(value = "post/getAll", method=RequestMethod.POST)
	public @ResponseBody ArrayList<PostUI> getAllPosts(@RequestParam("searchKey") String searchKey, Model model, HttpSession session) {
		
		UserUI userUIInSession = (UserUI) session.getAttribute("sessionUser");
		
		PostUI pui = new PostUI();
		
		ArrayList<PostUI> posts = pui.getPostsForUserAndFriends(userUIInSession.getUserId());
		return posts;
	}
	
//	@RequestMapping("post")
//	public String setup(Locale locale, Model model) throws SQLException{
//		
//		return "postContainer";
//	}
	
	@RequestMapping(value="post/add", method=RequestMethod.POST)
	public @ResponseBody PostUI addPost(@RequestParam("postData") String post) throws SQLException{
		
		User u = new User();
		UserDal ud = new UserDal();
		u = ud.selectUserByPrimaryKey(1);
		
		PostUI pui = new PostUI();
		pui = pui.addPost(post, u);
		return pui;
	}
	
	@RequestMapping(value="comm/add", method=RequestMethod.POST)
	public @ResponseBody CommentUI addComment(@RequestParam("commentData") String comm,@RequestParam("userId") int userId,@RequestParam("postId") int postId) throws SQLException{
		CommentUI cui = new CommentUI(comm,userId,postId);
		
		cui = cui.addComment(cui);
		return cui;
	}
	
	@RequestMapping(value="post/del", method=RequestMethod.POST)
	public @ResponseBody boolean deletePost(@RequestParam("postId") int postId){
		PostUI pui = new PostUI();
		boolean result = false;
		try {
			result = pui.deletePost(postId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="comm/del", method=RequestMethod.POST)
	public @ResponseBody boolean deleteComment(@RequestParam("commId") int commId){
		CommentUI cui = new CommentUI();
		boolean result = false;
		try {
			result = cui.deleteComment(commId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="post/like", method=RequestMethod.POST)
	public @ResponseBody int likePost(@RequestParam("postId") int postId,@RequestParam("userId") int userId,@RequestParam("likeType") int likeType ){
		LikeUI lui = new LikeUI();
		int result = -1;
		try {
			result = lui.toggleLike(likeType, postId, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="post/dislike", method=RequestMethod.POST)
	public @ResponseBody int dislikePost(@RequestParam("postId") int postId,@RequestParam("userId") int userId, @RequestParam("likeType") int dislikeType){
		DislikeUI lui = new DislikeUI();
		int result = -1;
		try {
			result = lui.toggleDislike(dislikeType, postId, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
