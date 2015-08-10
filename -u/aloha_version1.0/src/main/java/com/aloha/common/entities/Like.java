package com.aloha.common.entities;

import java.sql.SQLException;
import java.util.*;

import com.aloha.common.dao_manager.dal.LikeDislikeDal;

public class Like {
	private int type;
	private int userId;
	private int postId;
	private LikeDislikeDal lDal;

	/**
	 * @param likeId
	 * @param type
	 * @param userId
	 * @param postId
	 * @param likeCount
	 * @param dislikeCount
	 */
	public Like(int type,  int userId, int postId) {
		super();
		this.type = type;
		this.userId = userId;
		this.postId = postId;
		lDal = new LikeDislikeDal();
	}
	
	public Like(int userId, int postId) {
		super();
		this.userId = userId;
		this.postId = postId;
		lDal = new LikeDislikeDal();
	}

	// region

		public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	// endregion

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Like() {
		// TODO Auto-generated constructor stub
	}



	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Like [ userId="
				+ userId + ", postId=" + postId + ", lDal=" + lDal + "]";
	}
	
	public int likeOrUnlikePost(Like like) throws SQLException{
		return lDal.insertOrUpdate(like.getType(), like.getPostId(), like.getUserId());
	}

	
}
