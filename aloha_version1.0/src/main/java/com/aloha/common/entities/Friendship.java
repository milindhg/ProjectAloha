package com.aloha.common.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aloha.common.dao_manager.dal.FriendshipDal;
import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.entities.user.User;

/**
 * @author Milind
 *
 */
public class Friendship {

	private int friendshipId;
	private User user1;
	private User user2;
	private FriendshipStatus status;
	private User blocked_by;
	private User req_sent_by;
	public FriendshipDal fdal = new FriendshipDal();
	public UserDal udal = new UserDal();

	/**
	 * 
	 */
	public Friendship() {
		this.friendshipId = 0;
		this.user1 = new User();
		this.user2 = new User();
		this.status = FriendshipStatus.Default;
		this.req_sent_by = user1;
	}

	/**
	 * @param fshipId
	 * @param u1
	 * @param u2
	 * @param fStatus
	 */
	public Friendship(int fshipId, User u1, User u2, FriendshipStatus fStatus) {
		this.friendshipId = fshipId;
		this.user1 = u1;
		this.user2 = u2;
		this.status = fStatus;
	}

	/**
	 * @return the friendshipId
	 */
	public int getFriendshipId() {
		return friendshipId;
	}

	/**
	 * @param friendshipId
	 *            the friendshipId to set
	 */
	public void setFriendshipId(int friendshipId) {
		this.friendshipId = friendshipId;
	}

	/**
	 * @return the user1
	 */
	public User getUser1() {
		return user1;
	}

	/**
	 * @param user1
	 *            the user1 to set
	 */
	public void setUser1(User user1) {
		this.user1 = user1;
	}

	/**
	 * @return the user2
	 */
	public User getUser2() {
		return user2;
	}

	/**
	 * @param user2
	 *            the user2 to set
	 */
	public void setUser2(User user2) {
		this.user2 = user2;
	}

	/**
	 * @return the status
	 */
	public FriendshipStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(FriendshipStatus status) {
		this.status = status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status.setStatus(status);
	}

	/**
	 * @return the blocked_by
	 */
	public User getBlocked_by() {
		return blocked_by;
	}

	/**
	 * @param blocked_by
	 *            the blocked_by to set
	 */
	public void setBlocked_by(User blocked_by) {
		this.blocked_by = blocked_by;
	}

	/**
	 * @return the req_sent_by
	 */
	public User getReq_sent_by() {
		return req_sent_by;
	}

	/**
	 * @param req_sent_by
	 *            the req_sent_by to set
	 */
	public void setReq_sent_by(User req_sent_by) {
		this.req_sent_by = req_sent_by;
	}

	/**
	 * @param fshipId
	 * @return
	 */
	public boolean deleteFriendship(int fshipId) {
		return false;
	}

	/**
	 * @param friendship
	 * @return
	 */
	public boolean addFriendship(Friendship friendship) {
		return false;
	}

	/**
	 * @param friendship
	 * @return
	 */
	public boolean addFriendship(int requestorId, int requesteeId) {
		boolean result = false;
		Friendship f = null;
		try {
			f = fdal.selectFriendshipByUsers(requestorId, requesteeId);

			if (f == null) {
				f=new Friendship();
				f.getUser1().setUserId(requestorId);
				f.getUser2().setUserId(requesteeId);
				f.setStatus(FriendshipStatus.RequestSent);
				int res = fdal.insertFriendship(f);

				if (res == 1)
					result = true;
				else
					result = false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param userid
	 * @return
	 */
	public ArrayList<User> getUserFriends(User u) {
		try {
			ArrayList<Friendship> flist = fdal.selectFriendshipByUserId(u);
			ArrayList<User> ulist = new ArrayList<User>();
			for (Friendship f : flist) {
				ulist.add(f.getUser2());
			}
			return ulist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param friendship
	 * @return
	 */
	public boolean updateFriendship(Friendship friendship) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("Friendship ");
		stringBuffer.append(new StringBuilder().append("[friendshipId=")
				.append(friendshipId).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[user1=").append(user1)
				.append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[user2=").append(user2)
				.append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[status=")
				.append(status).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[blocked_by=")
				.append(blocked_by).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[req_sent_by=")
				.append(req_sent_by).append("]\n").toString());

		return stringBuffer.toString();

	}

}
