package com.aloha.common.model;

import java.util.ArrayList;

public class GetPostsUI extends ResponseUI {
	private ArrayList<PostUI> posts;

	/**
	 * @return the posts
	 */
	public ArrayList<PostUI> getPosts() {
		return posts;
	}

	/**
	 * @param posts the posts to set
	 */
	public void setPosts(ArrayList<PostUI> posts) {
		this.posts = posts;
	}
	 
}
