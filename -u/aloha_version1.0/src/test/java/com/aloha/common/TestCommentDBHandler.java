package com.aloha.common;

import java.util.ArrayList;

import com.aloha.common.dao_manager.dal.CommentDal;
import com.aloha.common.entities.Comment;

public class TestCommentDBHandler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 insertComment();
		// getComment();
		//updateComment();
		//getCommentsForPost();
		//deleteComment();
	}

	public static void insertComment() {
		try {
			CommentDal cDal = new CommentDal();
			Comment c = new Comment(-1, "User 4 Commenting on Post 2", null, 2, 4);
			Comment success = cDal.insertComment(c);
			if (success != null)
				System.out.println("Comment 1 added !!");
			else
				System.out.println("Comment 1 add failed !!");

			c = new Comment(-1, "User 1 Replying on Post 2", null, 2, 1);
			success = cDal.insertComment(c);
			if (success != null)
				System.out.println("Comment 2 added !!");
			else
				System.out.println("Comment 2 add failed !!");

			c = new Comment(-1, "User 6 Commenting on Post 2", null, 2, 6);
			success = cDal.insertComment(c);
			if (success != null)
				System.out.println("Comment 3 added !!");
			else
				System.out.println("Comment 3 add failed !!");

			c = new Comment(-1, "User 6 Commenting on Post 4", null, 4, 6);
			success = cDal.insertComment(c);
			if (success != null)
				System.out.println("Comment 4 added !!");
			else
				System.out.println("Comment 14 add failed !!");

			c = new Comment(-1, "User 4 Replying on Post 2", null, 4, 4);
			success = cDal.insertComment(c);
			if (success != null)
				System.out.println("Comment 5 added !!");
			else
				System.out.println("Comment 5 add failed !!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getComment() {
		try {
			CommentDal cDal = new CommentDal();
			Comment c = cDal.getCommentByPrimaryKey(2);
			System.out.println(c.toString());

			Comment c1 = cDal.getCommentByPrimaryKey(4);
			System.out.println(c1.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateComment() {
		try {
			CommentDal cDal = new CommentDal();
			Comment c = cDal.getCommentByPrimaryKey(2);
			System.out.println(c.toString());

			c.setComment("This is update of comment");
			cDal.updateComment(c);
			System.out.println(c.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getCommentsForPost() {
		try {
			CommentDal cDal = new CommentDal();
			ArrayList<Comment> comms = cDal.getCommentForPost(1);

			if (comms != null) {
				for (Comment comm : comms) {
					System.out.println(comm.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteComment() {
		try {
			CommentDal cDal = new CommentDal();
int success = cDal.deleteComment(3);
			
			if(success == 1){
				System.out.println("Deleted !!");
			}
			else System.out.println("Delete failed !!");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
