package com.aloha.common.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.aloha.common.dao_manager.dal.CommentDal;

public class Comment {
	private int commenttId;
	private String comment;
	private Date commentDate;
	private int postId;
	private int userId;
	private String userName;
	private CommentDal dal;

	// region Getter Setter methods
	public int getCommenttId() {
		return commenttId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCommenttId(int commenttId) {
		this.commenttId = commenttId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	// endregion

	/**
	 * @param commenttId
	 * @param comment
	 * @param commentDate
	 */
	public Comment(){
		this.dal = new CommentDal();
	}
	
	public Comment(int commenttId, String comment, Date commentDate,
			int postId, int userId) {
		super();
		this.commenttId = commenttId;
		this.comment = comment;
		this.commentDate = commentDate;
		this.postId = postId;
		this.userId = userId;
		this.dal = new CommentDal();
	}

	public Comment addComment(Comment comment) throws SQLException {
		Comment result = dal.insertComment(comment);
		return result;
		
	}

	public boolean deleteComment(int commId) throws SQLException {
		int result = dal.deleteComment(commId);
		if (result == 1)
			return true;
		return false;
	}

	public ArrayList<Comment> getCommentsPost(int postId) throws SQLException {
		ArrayList<Comment> comments = dal.getCustomCommentForPost(postId);
		return comments;
	}

	public Comment updateComment(Comment comment) throws SQLException {
		
		int result = dal.updateComment(comment);
		if(result == 1)
			return comment;
		return null;
	}

	public Comment getComment(int commId) throws SQLException {
		Comment comm = dal.getCommentByPrimaryKey(commId);
		return comm;
	}

	public int deleteAllCommentsOnPost(int postId) throws SQLException{
		
		int result = dal.deleteCommentsForPost(postId);
		return result;
	}
	
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(new StringBuilder().append("[commenttId=")
				.append(commenttId).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[comment=")
				.append(comment).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[commentDate=")
				.append(commentDate).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[postId=")
				.append(postId).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[userId=")
				.append(userId).append("]\n").toString());

		return stringBuffer.toString();
	}
}
