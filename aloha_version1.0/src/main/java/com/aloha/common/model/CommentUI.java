package com.aloha.common.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.aloha.common.dao_manager.dal.CommentDal;
import com.aloha.common.entities.Comment;
import com.aloha.common.entities.Post;
import com.aloha.common.entities.User;

public class CommentUI {
	private int CommentId;
	private String CommentData;
	private Date CommentDate;
	private String UserName;
	private int UserId;
	private int PostId;

	// region Getter Setter method
	public int getCommentId() {
		return CommentId;
	}

	public void setCommentId(int commentId) {
		CommentId = commentId;
	}

	public String getCommentData() {
		return CommentData;
	}

	public void setCommentData(String commentData) {
		CommentData = commentData;
	}

	public Date getCommentDate() {
		return CommentDate;
	}

	public void setCommentDate(Date commentDate) {
		CommentDate = commentDate;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getPostId() {
		return PostId;
	}

	public void setPostId(int postId) {
		PostId = postId;
	}

	// endregion

	public ArrayList<CommentUI> getCommentsForPost(User user, Post post) throws SQLException {

		ArrayList<CommentUI> comments = new ArrayList<CommentUI>();
		
		
		for (Comment comment : post.getComments()) {
			CommentUI cui = new CommentUI();
			cui.setCommentId(comment.getCommenttId());
			cui.setCommentData(comment.getComment());
			cui.setCommentDate(comment.getCommentDate());
			cui.setUserName(user.getFirstName() + user.getLastName());
			cui.setUserId(user.getUserId());
			cui.setPostId(post.getPostId());
			comments.add(cui);
		}

		return comments;
	}

	}
