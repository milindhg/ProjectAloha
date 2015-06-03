package com.aloha.common.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.aloha.common.entities.Comment;
import com.aloha.common.entities.Post;
import com.aloha.common.entities.user.User;
import com.aloha.common.entities.user.UserInfo;

public class CommentUI {
	private int commentId;
	private String commentData;
	private String commentDate;
	private String userName;
	private int userId;
	private int canDelete;
	private int postId;
	
	

	/**
	 * @param commentData
	 * @param userId
	 * @param postId
	 */
	public CommentUI(String commentData, int userId, int postId) {
		super();
		this.commentData = commentData;
		this.userId = userId;
		this.postId = postId;
	}

	// region Getter Setter method
	
	// endregion

	public CommentUI() {
		// TODO Auto-generated constructor stub
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentData() {
		return commentData;
	}

	public void setCommentData(String commentData) {
		this.commentData = commentData;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPostId() {
		return postId;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public int getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(int canDelete) {
		this.canDelete = canDelete;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public ArrayList<CommentUI> getCommentsForPost( Post post, UserUI user) throws SQLException {

		ArrayList<CommentUI> comments = new ArrayList<CommentUI>();
		
		if(post.getComments()==null){
			return new ArrayList<CommentUI>();
		}
		for (Comment comment : post.getComments()) {
			CommentUI cui = new CommentUI();
			cui.setCommentId(comment.getCommenttId());
			cui.setCommentData(comment.getComment());
			cui.setCommentDate(Helper.getLocalDate(comment.getCommentDate()));
			cui.setUserName(comment.getUserName());
			cui.setUserId(comment.getUserId());
			cui.setPostId(comment.getPostId());
			
			if((user.getUserId() == post.getUserId()) || (comment.getUserId() == user.getUserId()))
				cui.setCanDelete(1);
			else 	cui.setCanDelete(2);
			
			comments.add(cui);
		}

		return comments;
	}

	public CommentUI commentToCommentUI(Comment comment) throws SQLException{
		CommentUI cui = new CommentUI();
		
		User user = new User();
		user = user.getUser(comment.getUserId());
		
		cui.setCommentId(comment.getCommenttId());
		cui.setCommentData(comment.getComment());
		cui.setCommentDate(Helper.getLocalDate(comment.getCommentDate()));
		cui.setUserName(user.getFirstName() + user.getLastName());
		cui.setUserId(comment.getUserId());
		cui.setPostId(comment.getPostId());
		
		cui.setCanDelete(1);
		
		return cui;
	}
	
	public CommentUI addComment(CommentUI comment) throws SQLException{
		
		Comment c = new Comment(-1,comment.getCommentData(),null, comment.getPostId(),comment.getUserId());
		Comment res = c.addComment(c);
		return commentToCommentUI(res);
		
	}
	
	public boolean deleteComment(int commId) throws SQLException{
		Comment c = new Comment();
		boolean result = c.deleteComment(commId);
		return result;
	}

}
