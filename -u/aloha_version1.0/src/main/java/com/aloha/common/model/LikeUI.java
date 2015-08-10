package com.aloha.common.model;

import java.sql.SQLException;
import java.util.ArrayList;

import com.aloha.common.dao_manager.dal.LikeDislikeDal;
import com.aloha.common.entities.Like;

public class LikeUI {
	private int likeId;
	private int postId;
	private int userId;
	
	public LikeUI(int userId, int postId) {
		super();
		this.userId = userId;
		this.postId = postId;
		
	}

	public LikeUI() {
		// TODO Auto-generated constructor stub
	}

	public int getLikeId() {
		return likeId;
	}

	public void setLikeId(int likeId) {
		this.likeId = likeId;
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

	public ArrayList<LikeUI> getLikesData(ArrayList<Like> likes) {

		ArrayList<LikeUI> likeData = new ArrayList<LikeUI>();
		for (Like like : likes) {
			LikeUI lui = new LikeUI();
			lui.setPostId(like.getPostId());
			lui.setUserId(like.getUserId());
			likeData.add(lui);
		}
		return likeData;
	}

	
	public int toggleLike(int likeType, int postId, int userId) throws SQLException{
		Like like = new Like(likeType,userId,postId);
		int status = like.likeOrUnlikePost(like);
		return status;
	}
}
