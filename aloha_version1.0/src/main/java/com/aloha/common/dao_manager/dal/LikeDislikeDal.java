package com.aloha.common.dao_manager.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
import com.aloha.common.entities.Dislike;
import com.aloha.common.entities.Like;
import com.aloha.common.entities.LikeDislike;
import com.aloha.common.entities.LikeType;
import com.aloha.common.entities.Post;

public class LikeDislikeDal {

	Connection con = null;
	// write the queries for Post table
	private String SELECT;
	private String INSERT;
	private String DELETE;
	private String INSERT_OR_UPDATE;

	public LikeDislikeDal() {
		SELECT = "SELECT  likedislike.like_type, likedislike.user_id, likedislike.post_id FROM likedislike";
		INSERT = "INSERT INTO likedislike ( like_type, user_id, post_id) VALUES ( ?, ?, ?);";
		DELETE = "DELETE FROM likedislike";
		INSERT_OR_UPDATE = "INSERT INTO likedislike (like_type, user_id, post_id) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE like_type = ?;";
		
		con = DatabaseHandlerSingleton.getDBConnection();

	}

	public LikeDislike GetPostLikeDislike(int postid) throws SQLException {

		ArrayList<Like> likes = new ArrayList<Like>();
		ArrayList<Dislike> dislikes = new ArrayList<Dislike>();

		String getStmt = SELECT
				+ " where likedislike.post_id=? and  likedislike.like_type in (1,2);";
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(getStmt);
			ps.setInt(1, postid);
			rSet = ps.executeQuery();

			while (rSet.next()) {

				int likeType = rSet.getInt("like_type");

				if (likeType == 1) {
					Like like = new Like();
					like.setType(rSet.getInt("like_type"));
					like.setPostId(rSet.getInt("post_id"));
					like.setUserId(rSet.getInt("user_id"));
					//like.setType(LikeType.Like);
					likes.add(like);
				} else if (likeType == 2) {
					Dislike like = new Dislike();
					like.setType(rSet.getInt("like_type"));
					like.setPostId(rSet.getInt("post_id"));
					like.setUserId(rSet.getInt("user_id"));
					//like.setType(LikeType.Dislike);
					dislikes.add(like);
				}
			}
			return new LikeDislike(likes, dislikes);

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rSet != null)
				rSet.close();
			if (ps != null)
				ps.close();
			//con.close();
		}
	}

		
	public int insertLike(Like like) throws SQLException {

		String insertStmt = INSERT;

		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(insertStmt);
			ps.setInt(1, 1);
			ps.setInt(2, like.getUserId());
			ps.setInt(3, like.getPostId());
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			//con.close();
		}
	}

	public int insertDislike(Dislike like) throws SQLException {

		String insertStmt = INSERT;

		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(insertStmt);
			ps.setInt(1, 2);
			ps.setInt(2, like.getUserId());
			ps.setInt(3, like.getPostId());
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			//con.close();
		}
	}

	public int deleteAllDataOfPost(int postId) throws SQLException{
		String deleteStmt = DELETE + "where post_id = ?";

		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(deleteStmt);
			ps.setInt(1, postId);
			result = ps.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			//con.close();
		}
	}
	
	public int insertOrUpdate(int likeType, int postId, int userId) throws SQLException{
		String insertStmt = INSERT_OR_UPDATE;

		PreparedStatement ps = null;
		int result = likeType;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(insertStmt);
			ps.setInt(1, likeType);
			ps.setInt(2, userId);
			ps.setInt(3, postId);
			ps.setInt(4, likeType);
			ps.executeUpdate();
			
			result = GetPostLikeTypeById(postId, userId);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			//con.close();
		}

	}
	
	public int GetPostLikeTypeById(int postid, int userid) throws SQLException {

		String getStmt = SELECT
				+ " where likedislike.post_id=? and  likedislike.user_id = ?;";
		PreparedStatement ps = null;
		int likeType = 0;
		
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(getStmt);
			ps.setInt(1, postid);
			ps.setInt(2, userid);
			rSet = ps.executeQuery();
			if (rSet.next()) {

				likeType = rSet.getInt("like_type");

				
		} 
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rSet != null)
				rSet.close();
			if (ps != null)
				ps.close();
			//con.close();
		}
		return likeType;
	}
}
