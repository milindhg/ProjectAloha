package com.aloha.common.entities;

import java.sql.SQLException;
import java.util.ArrayList;

import com.aloha.common.dao_manager.dal.LikeDislikeDal;

public class Dislike {
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dislike [userId=" + userId + ", postId=" + postId + ", lDal="
				+ lDal + "]";
	}


	private int type;
	private int userId;
	private int postId;
	private LikeDislikeDal lDal;
	
	//region
	
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}
	
	//endregion

	/**
	 * @param dislikeId
	 * @param type
	 * @param userId
	 * @param postId
	 * @param likeCount
	 * @param dislikeCount
	 */
	public Dislike(int type, int userId, int postId) {
		super();
		this.type = type;
		this.userId = userId;
		this.postId = postId;
		lDal = new LikeDislikeDal();
	}
	
	public Dislike( int userId, int postId) {
		super();
		this.userId = userId;
		this.postId = postId;
		lDal = new LikeDislikeDal();
	}

	public Dislike() {
		// TODO Auto-generated constructor stub
		lDal = new LikeDislikeDal();
	}

	public int dislikeOrUndislikePost(Dislike dislike) throws SQLException{
		return lDal.insertOrUpdate(dislike.getType(), dislike.getPostId(), dislike.getUserId());
	}
}
