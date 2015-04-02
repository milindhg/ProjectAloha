package com.aloha.common.entities;

import java.util.ArrayList;
import java.util.Date;

public class Post {
	private int postId;
	private String post; 
	private Date postDate;
	private ArrayList<Comment> comments;
	
	/**
	 * @param postId
	 * @param post
	 * @param postDate
	 */
	public Post(int postId, String post, Date postDate) {
		super();
		this.postId = postId;
		this.post = post;
		this.postDate = postDate;
		this.comments = null;
	}
	
	public Post addPost(Post post){return null;}
	
	public boolean deletePost(int postId){return false;}
	
	public ArrayList<Post> getPostsUser(int userId){return null;}
	
	public ArrayList<Post> getPostsFriends(int userId){return null;}
	
	public Post updatePost(Post post){return null;}
	
	public Post getPost(int postId){return null;}
	
	public boolean hasComments(int postId){return false;}
	
	public ArrayList<Comment> getComments(int postId){
		//delegate to comment class
		return null;}
	

}
