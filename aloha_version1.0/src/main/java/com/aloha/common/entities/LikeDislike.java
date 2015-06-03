package com.aloha.common.entities;

import java.sql.SQLException;
import java.util.ArrayList;

import com.aloha.common.dao_manager.dal.LikeDislikeDal;

public class LikeDislike {
	private ArrayList<Like> likes;
	private ArrayList<Dislike> dislikes;
	private LikeDislikeDal lDal;
	public ArrayList<Like> getLikes() {
		return likes;
	}

	public void setLikes(ArrayList<Like> likes) {
		this.likes = likes;
	}

	public ArrayList<Dislike> getDislikes() {
		return dislikes;
	}

	public void setDislikes(ArrayList<Dislike> dislikes) {
		this.dislikes = dislikes;
	}

	/**
	 * @param likes
	 * @param dislikes
	 */
	public LikeDislike(ArrayList<Like> likes, ArrayList<Dislike> dislikes) {
		super();
		this.likes = likes;
		this.dislikes = dislikes;
		this.lDal = new LikeDislikeDal();
	}
	
	public LikeDislike() {
		// TODO Auto-generated constructor stub
		this.lDal = new LikeDislikeDal();
	}

	public LikeDislike getPostLikeDislike(int postId) throws SQLException{

		LikeDislike likeDislike = lDal.GetPostLikeDislike(postId);
		return likeDislike;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LikeDislike [likes=" + likes + ", dislikes=" + dislikes
				+ ", lDal=" + lDal + "]";
	}
}
