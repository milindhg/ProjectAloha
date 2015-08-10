package com.aloha.common.sal;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.entities.OnlineUsers;
import com.aloha.common.model.UserUI;

public class LogoutService {
	public int setLastActive(UserUI u)
	{
		Timestamp lastActive = new Timestamp(System.currentTimeMillis());
		UserDal ud = new UserDal();
		int res = 0;
		try {
			res = ud.setLastActive(u.getUserId(), lastActive);
			OnlineUsers olUsers = new OnlineUsers();
			olUsers.deleteUserWhoIsOnline(u.getUserId());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		u.setLastActive(lastActive);

		

		return res;
	}
}
