package com.aloha.common.entities;

import java.sql.SQLException;
import java.util.ArrayList;

import com.aloha.common.dao_manager.dal.OnlineUserDal;
import com.aloha.common.entities.user.User;

public class OnlineUsers {
	private OnlineUserDal dal;

	public OnlineUsers() {
		dal = new OnlineUserDal();
	}

	public boolean isUserOnline(int userId) {
		try {
			if (dal.isUserOnline(userId) == -1)
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public int addUserAsOnline(int userId) {
		try {
			return dal.insertOnlineUser(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public int deleteUserWhoIsOnline(int userId) {
		try {
			return dal.deleteUserFromOnlineTable(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public ArrayList<User> getOnlineFriends(int userId) {
		ArrayList<User> onlineFriends = null;
		try {
			onlineFriends = dal.selectOnlineFriends(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return onlineFriends;
	}

	public ArrayList<User> getOnlineUsers() {
		ArrayList<User> onlineUsers = null;
		try {
			onlineUsers = dal.selectOnlineUsers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return onlineUsers;
	}

}
