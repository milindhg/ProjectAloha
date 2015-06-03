package com.aloha.common.entities;

public enum LikeType {
	Like (1),
	Dislike (2),
	None (0);
	
	private int likeType;
	
	private LikeType(int likeType){
		this.likeType =likeType;
	}

	public int getLikeType() {
		return likeType;
	}

	public void setLikeType(int likeType) {
		this.likeType = likeType;
	}
	
	
}
