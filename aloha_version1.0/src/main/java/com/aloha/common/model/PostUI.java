package com.aloha.common.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.aloha.common.entities.*;
import com.aloha.common.entities.user.User;

public class PostUI {
	private String userName;
	private int userId;
	private String postDate;
	private String postData;
	private int postId;
	private int canDelete;
	private ArrayList<CommentUI> comments;
	private ArrayList<LikeUI> likes;
	private ArrayList<DislikeUI> dislikes;
	private int userLikeType;
	private int numLikes;
	private int numDislikes;

	// region Getter Setter Method

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getPostData() {
		return postData;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public ArrayList<CommentUI> getComments() {
		return comments;
	}

	public void setComments(ArrayList<CommentUI> comments) {
		this.comments = comments;
	}

	public int getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(int numLikes) {
		this.numLikes = numLikes;
	}

	public int getNumDislikes() {
		return numDislikes;
	}

	public void setNumDislikes(int numDislikes) {
		this.numDislikes = numDislikes;
	}

	public int getUserLikeType() {
		return userLikeType;
	}

	public void setUserLikeType(int userLikeType) {
		this.userLikeType = userLikeType;
	}

	public int getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(int canDelete) {
		this.canDelete = canDelete;
	}

	public ArrayList<LikeUI> getLikes() {
		return likes;
	}

	public void setLikes(ArrayList<LikeUI> likes) {
		this.likes = likes;
	}

	// endregion

	public ArrayList<DislikeUI> getDislikes() {
		return dislikes;
	}

	public void setDislikes(ArrayList<DislikeUI> dislikes) {
		this.dislikes = dislikes;
	}

	public ArrayList<PostUI> getPostsForUser(UserUI user, String tstamp) {

		ArrayList<PostUI> userPosts = new ArrayList<PostUI>();
		Post p = new Post();
		CommentUI comm = new CommentUI();
		ArrayList<Post> posts;
		try {
			posts = p.getPostsUser(user.getUserId());
			for (Post post : posts) {
				LikeDislike ld = post.getLikeStatistics();
				PostUI pui = new PostUI();
				pui.setUserName(user.getFirstName() + " " + user.getLastName());
				pui.setUserId(user.getUserId());
				pui.setPostDate(post.getPostDate().toString());
				pui.setPostData(post.getPost());
				pui.setPostId(post.getPostId());
				
				if (user.getUserId() == post.getUserId())
					pui.setCanDelete(1);
				else
					pui.setCanDelete(2);
				pui.setComments(comm.getCommentsForPost(post, user));

				if (ld != null) {
					if (ld.getLikes() != null) 
					pui.setNumLikes(ld.getLikes().size());
					else pui.setNumLikes(0);
					if (ld.getDislikes() != null) 
						pui.setNumDislikes(ld.getDislikes().size());
					else pui.setNumDislikes(0);
					
					pui.setUserLikeType(0);
					if (ld.getLikes() != null) {
						for (Like like : ld.getLikes()) {
							if (like.getUserId() == userId)
								pui.setUserLikeType(1);
						}
					}
					if (ld.getDislikes() != null) {
						for (Dislike dislike : ld.getDislikes()) {
							if (dislike.getUserId() == userId)
								pui.setUserLikeType(2);
						}
					}

				}
				userPosts.add(pui);
			}

			return userPosts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<PostUI> getPostsForUserAndFriends(UserUI user, String tstamp) throws ParseException {

		ArrayList<PostUI> userPosts = new ArrayList<PostUI>();
		Post p = new Post();
		CommentUI comm = new CommentUI();
		ArrayList<Post> posts;
		try {
			posts = p.getPostsFriends(user.getUserId(), tstamp);
			for (Post post : posts) {
				LikeDislike ld = post.getLikeStatistics();
				PostUI pui = new PostUI();
				pui.setUserName(post.getUserName() + " "
						+ post.getUserSurname());
				pui.setUserId(post.getUserId());
				pui.setPostDate(post.getPostDate().toString());
				pui.setPostData(post.getPost());
				pui.setPostId(post.getPostId());
				if (user.getUserId() == post.getUserId())
					pui.setCanDelete(1);
				else
					pui.setCanDelete(2);

				pui.setComments(comm.getCommentsForPost(post, user));

				if (ld != null) {
					if (ld.getLikes() != null) 
						pui.setNumLikes(ld.getLikes().size());
						else pui.setNumLikes(0);
						if (ld.getDislikes() != null) 
							pui.setNumDislikes(ld.getDislikes().size());
						else pui.setNumDislikes(0);
						
						pui.setUserLikeType(0);
						if (ld.getLikes() != null) {
							for (Like like : ld.getLikes()) {
								if (like.getUserId() == user.getUserId())
									pui.setUserLikeType(1);
							}
						}
						if (ld.getDislikes() != null) {
							for (Dislike dislike : ld.getDislikes()) {
								if (dislike.getUserId() == user.getUserId())
									pui.setUserLikeType(2);
							}
						}
				}
				userPosts.add(pui);
			}

			return userPosts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public PostUI addPost(String post, UserUI u) throws SQLException {
		Post p = new Post(-1, post, null, null, null, u.getUserId());
		p = p.addPost(p);
		return getPostUI(p, u);
	}

	public PostUI getPostUI(Post post, UserUI u) throws SQLException {
		PostUI pui = new PostUI();
		CommentUI cui = new CommentUI();
		LikeDislike ld = post.getLikeStatistics();
		pui.setComments(cui.getCommentsForPost(post, u));

		pui.setPostData(post.getPost());
		pui.setPostDate(Helper.getLocalDate(post.getPostDate()));
		pui.setPostId(post.getPostId());
		pui.setUserId(u.getUserId());
		pui.setUserName(u.getFirstName() + " " + u.getLastName());
		if (u.getUserId() == post.getUserId())
			pui.setCanDelete(1);
		else
			pui.setCanDelete(2);
		pui.setUserLikeType(0);
		if (ld != null) {
			if (ld.getLikes() != null) 
				pui.setNumLikes(ld.getLikes().size());
				else pui.setNumLikes(0);
				if (ld.getDislikes() != null) 
					pui.setNumDislikes(ld.getDislikes().size());
				else pui.setNumDislikes(0);
				
				pui.setUserLikeType(0);
				if (ld.getLikes() != null) {
					for (Like like : ld.getLikes()) {
						if (like.getUserId() == userId)
							pui.setUserLikeType(1);
					}
				}
				if (ld.getDislikes() != null) {
					for (Dislike dislike : ld.getDislikes()) {
						if (dislike.getUserId() == userId)
							pui.setUserLikeType(2);
					}
				}
		}
		return pui;
	}

	public ArrayList<LikeUI> getLikes(ArrayList<Like> likes) {
		LikeUI lui = new LikeUI();
		return lui.getLikesData(likes);
	}

	public ArrayList<DislikeUI> getDislikes(ArrayList<Dislike> dislikes) {
		DislikeUI lui = new DislikeUI();
		return lui.getDislikesData(dislikes);
	}

	public boolean deletePost(int posId) throws SQLException {
		Post p = new Post();
		boolean result = p.deletePost(posId);
		return result;
	}
}
