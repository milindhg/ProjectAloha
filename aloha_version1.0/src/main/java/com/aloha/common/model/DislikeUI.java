package com.aloha.common.model;

import java.sql.SQLException;
import java.util.ArrayList;

import com.aloha.common.entities.Dislike;
import com.aloha.common.entities.Like;

public class DislikeUI {
	private int dislikeId;
	private int postId;
	private int userId;
	
	public DislikeUI(int userId, int postId) {
		super();
		this.userId = userId;
		this.postId = postId;
		
	}

	public DislikeUI() {
		// TODO Auto-generated constructor stub
	}

	
	public int getDislikeId() {
		return dislikeId;
	}
	public void setDislikeId(int dislikeId) {
		this.dislikeId = dislikeId;
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
	
	public ArrayList<DislikeUI> getDislikesData(ArrayList<Dislike> dislikes) {

		ArrayList<DislikeUI> dislikeData = new ArrayList<DislikeUI>();
		for (Dislike dl : dislikes) {
			DislikeUI lui = new DislikeUI();
			lui.setPostId(dl.getPostId());
			lui.setUserId(dl.getUserId());
			dislikeData.add(lui);
		}
		return dislikeData;
	}

	
	public int toggleDislike(int dislikeType, int postId, int userId) throws SQLException{
		Dislike dislike = new Dislike(dislikeType, userId,postId);
		int status = dislike.dislikeOrUndislikePost(dislike);
		return status;
	}
}
