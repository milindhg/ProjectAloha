package com.aloha.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.aloha.common.model.GetCommentUI;
import com.aloha.common.model.GetLikeStatusUI;
import com.aloha.common.model.GetPostUI;
import com.aloha.common.model.GetPostsUI;
import com.aloha.common.model.GetStatusUI;

public interface IPostController {

	
	public GetPostsUI getAllPosts(String datetime, Model model, HttpSession session);
	
	public GetPostUI addPost(String post, HttpSession session);
	
	public GetCommentUI addComment(String comm, int postId, HttpSession session) ;
	
	public GetStatusUI deletePost( int postId, HttpSession session);
	
	public GetStatusUI deleteComment(int commId, HttpSession session);
	
	public GetLikeStatusUI likePost( int postId, int likeType, HttpSession session );

	public GetLikeStatusUI dislikePost( int postId, int dislikeType, HttpSession session);
	
	
}
