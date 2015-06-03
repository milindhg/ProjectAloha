package com.aloha.common.entities;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.aloha.common.dao_manager.dal.PostDal;

public class Post {
	private int postId;
	private String post;
	private Date postDate;
	private ArrayList<Comment> comments;
	private LikeDislike likeStatistics;
	private int userId;
	private String userName;
	private String userSurname;
	private PostDal dal;

	/**
	 * @param postId
	 * @param post
	 * @param postDate
	 * @param comments
	 * @param likeStatistics
	 */
	public Post(int postId, String post, Date postDate,
			ArrayList<Comment> comments, LikeDislike likeStatistics, int userId) {
		super();
		this.postId = postId;
		this.post = post;
		this.postDate = postDate;
		this.comments = comments;
		this.likeStatistics = likeStatistics;
		this.userId = userId;
		this.dal = new PostDal();
	}

	// region gettersetter

	public Post() {
		// TODO Auto-generated constructor stub
		this.dal = new PostDal();
	}

	public int getPostId() {
		return postId;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public LikeDislike getLikeStatistics() {
		return likeStatistics;
	}

	public void setLikeStatistics(LikeDislike likeStatistics) {
		this.likeStatistics = likeStatistics;
	}


	// endregion gettersetter

	public Post addPost(Post post) throws SQLException {

		return dal.insertPost(post);
		
	}

	public boolean deletePost(int postId) throws SQLException {
		int result = dal.deletePost(postId);
		if (result == 1){
			//Comment comm = new Comment();
			//comm.deleteAllCommentsOnPost(postId);
			return true;}
		else
			return false;
	}

	public ArrayList<Post> getPostsUser(int userId) throws SQLException {
		ArrayList<Post> posts = new ArrayList<Post>();
		posts = dal.getPostsForUser(userId);
		
		for (Post post : posts) {
			//if(post.hasComments = true)
			//{
				Comment comm = new Comment();
				post.comments = comm.getCommentsPost(post.getPostId());
				LikeDislike ld = new LikeDislike();
				post.likeStatistics = ld.getPostLikeDislike(post.getPostId());
			//}
			
		}
		return posts;
	}

	public ArrayList<Post> getPostsFriends(int userId, String tstamp) throws SQLException {
		ArrayList<Post> posts = new ArrayList<Post>();
		
		java.util.Date date= new java.util.Date();
		 Timestamp ts = new Timestamp(date.getTime());
		 if(!tstamp.isEmpty())
			  ts = Timestamp.valueOf(tstamp);
		posts = dal.getPostsForUserAndFriends(userId, ts);
		
		for (Post post : posts) {
				Comment comm = new Comment();
				post.comments = comm.getCommentsPost(post.getPostId());
				LikeDislike ld = new LikeDislike();
				post.likeStatistics = ld.getPostLikeDislike(post.getPostId());
			
		}
		return posts;
	}

	public boolean updatePost(Post post) throws SQLException {
		int success = dal.updatePost(post);
		if (success == 1)
			return true;
		else
			return false;

	}

	public Post getPost(int postId) throws SQLException {
		Post post = dal.getPostByPrimaryKey(postId);

		Comment comm = new Comment();
		post.comments = comm.getCommentsPost(postId);
		LikeDislike ld = new LikeDislike();
		post.likeStatistics = ld.getPostLikeDislike(post.getPostId());
		
		return post;
	}

	public ArrayList<Comment> getComments(int postId) {
		
		return null;
	}

	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(new StringBuilder().append("[postid=")
				.append(postId).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[post=")
				.append(post).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[postdate=")
				.append(postDate).append("]\n").toString());


		return stringBuffer.toString();
	}

}
