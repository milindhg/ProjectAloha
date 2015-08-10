package com.aloha.common;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.aloha.common.dao_manager.dal.PostDal;
import com.aloha.common.entities.Post;

public class TestPostDBHandler {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//addPostTest();
		//selectPost();
		//updatePost();
		//getUserPostsTest();
		//deletePost();
		//getUserAndFriendsPostsTest();
	}
	
	public static void addPostTest()
	{
		PostDal pDal = new PostDal();
		
		try{
		Post post = new Post(-1, "User 6 just joined Aloha !!", null, null, null,6);
		Post p1 = pDal.insertPost(post);
		if(p1 != null)
			System.out.println(p1.toString());
		else System.out.println("P1 null !!");
		
		post = new Post(-1, "User 6 says hi to everyone !!", null, null, null,6);
		Post p2 = pDal.insertPost(post);
		if(p2 != null)
			System.out.println(p2.toString());
		else System.out.println("p2 null");
		
		post = new Post(-1, "User 6 hates working on Friday evening !!", null, null, null,6);
		Post p3 = pDal.insertPost(post);
		if(p3 != null)
			System.out.println(p3.toString());
		else System.out.println("p3 null !!");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void selectPostTest()
	{
		PostDal pDal = new PostDal();
		
		try{
			Post post = pDal.getPostByPrimaryKey(2);
			System.out.println( post.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void updatePostTest(){
		PostDal pDal = new PostDal();
		
		try {
			Post post = new Post(2,"This is update post",new Date(new java.util.Date().getTime()),null,null,4);
			int success = pDal.updatePost(post);
			if(success == 1)
				System.out.println("Updated");
			else System.out.println("Update failed !!");
			
			post = pDal.getPostByPrimaryKey(2);
			System.out.println( post.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getUserPostsTest(){
		PostDal pDal = new PostDal();
		
		try {
			ArrayList<Post> posts = pDal.getPostsForUser(1);
			
			if(posts != null){
				for (Post post : posts) {
					System.out.println( post.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void getUserAndFriendsPostsTest(){
//		PostDal pDal = new PostDal();
//		
//		try {
//			ArrayList<Post> posts = pDal.getPostsForUserAndFriends(1, new );
//			
//			if(posts != null){
//				for (Post post : posts) {
//					System.out.println( post.toString());
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public static void deletePost(){
PostDal pDal = new PostDal();
		
		try {
			int success = pDal.deletePost(3);
			
			if(success == 1){
				System.out.println("Deleted !!");
			}
			else System.out.println("Delete failed !!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
