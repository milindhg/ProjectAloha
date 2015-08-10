package com.aloha.common.dao_manager.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
import com.aloha.common.entities.Friendship;
import com.aloha.common.entities.FriendshipStatus;
import com.aloha.common.entities.user.User;

/**
 * @author Milind
 *
 */
public class FriendshipDal {

	Connection con = null;
	// write the queries for User table
	private String SELECTALL;
	private String SELECTALL_FRIENDSHIPS_OF_USER;
	private String SELECT_FRIENDSHIP_BY_PKEY;
	private String SELECT_FRIENDSHIP_BY_USERIDS;
	private String INSERT_FRIENDSHIP;
	private String UPDATE_FRIENDSHIP;
	private String DELETE_FRIENDSHIP;

	private String SELECT_ACTIVE_FRIENDSHIP_BY_USER1ID;
	private String SELECT_ACTIVE_FRIENDSHIP_BY_USER2ID;
	private String SELECT_PENDING_FRIEND_REQUESTS;
	private static final Logger logger = Logger.getLogger(FriendshipDal.class);

	/**
	 * Constructor
	 */
	public FriendshipDal() {
		SELECTALL = "SELECT friendship.friendship_id, friendship.user_id1, friendship.user_id2, friendship.friend_status_id, friendship.blocked_by, friendship.req_sent_by FROM friendship;";
		SELECTALL_FRIENDSHIPS_OF_USER = "SELECT friendship.friendship_id, friendship.user_id1, friendship.user_id2, friendship.friend_status_id, friendship.blocked_by, friendship.req_sent_by FROM friendship where friendship.user_id1 = ? or friendship.user_id2 = ? and friendship.friend_status_id=2;";
		SELECT_FRIENDSHIP_BY_PKEY = "SELECT friendship.friendship_id, friendship.user_id1, friendship.user_id2, friendship.friend_status_id, friendship.blocked_by, friendship.req_sent_by FROM friendship WHERE friendship.friendship_id = ?;";
		SELECT_FRIENDSHIP_BY_USERIDS = "SELECT friendship.friendship_id, friendship.user_id1, friendship.user_id2, friendship.friend_status_id, friendship.blocked_by, friendship.req_sent_by FROM friendship WHERE (friendship.user_id1=? && friendship.user_id2=?) or  (friendship.user_id2=? && friendship.user_id1=?);";
		INSERT_FRIENDSHIP = "INSERT INTO friendship(user_id1, user_id2, friend_status_id, blocked_by, req_sent_by) VALUES(?, ?, ?, ?, ?);";
		UPDATE_FRIENDSHIP = "UPDATE friendship SET user_id1 = ? , user_id2 = ? , friend_status_id = ? , blocked_by = ? , req_sent_by = ? WHERE friendship_id = ?;";
		DELETE_FRIENDSHIP = "DELETE FROM friendship WHERE friendship.friendship_id = ?;";

		SELECT_ACTIVE_FRIENDSHIP_BY_USER1ID = "SELECT F.*,U.* FROM friendship F inner join user U on F.user_id2=U.user_id where F.user_id1=? and F.friend_status_id=2;";
		SELECT_ACTIVE_FRIENDSHIP_BY_USER2ID = "SELECT F.*,U.* FROM friendship F inner join user U  on F.user_id1=U.user_id where F.user_id2=? and F.friend_status_id=2;";
		SELECT_PENDING_FRIEND_REQUESTS = "SELECT F.*,U.* FROM friendship F inner join user U  on F.user_id1=U.user_id where F.user_id2=? and F.friend_status_id=1;";
		con = DatabaseHandlerSingleton.getDBConnection();
	}

	/**
	 * @return List of Friendships
	 * @throws SQLException
	 */
	public ArrayList<Friendship> selectFriendshipAll() throws SQLException {
		String SelectFriendshipAllStatement = SELECTALL;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectFriendshipAllStatement);
			rSet = ps.executeQuery();
			ArrayList<Friendship> friendships = new ArrayList<Friendship>();
			while (rSet.next()) {
				Friendship friendship = new Friendship();
				friendship.setUser1(new User());
				friendship.setUser2(new User());
				friendship.setFriendshipId(rSet.getInt("friendship_id"));
				friendship.getUser1().setUserId(rSet.getInt("user_id1"));
				friendship.getUser2().setUserId(rSet.getInt("user_id2"));
				friendship.setStatus(rSet.getInt("friend_status_id"));
				int request_sent_by = rSet.getInt("req_sent_by");
				friendship
						.setReq_sent_by(friendship.getUser1().getUserId() == request_sent_by ? friendship
								.getUser1() : friendship.getUser2());
				int blocked_by = rSet.getInt("blocked_by");
				friendship
						.setBlocked_by(friendship.getUser1().getUserId() == blocked_by ? friendship
								.getUser1() : friendship.getUser2());
				friendships.add(friendship);
			}
			return friendships;
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

	/**
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Friendship> selectFriendshipAllByUser(int userId)
			throws SQLException {
		String SelectFriendshipAllByUserStatement = SELECTALL_FRIENDSHIPS_OF_USER;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectFriendshipAllByUserStatement);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			rSet = ps.executeQuery();
			ArrayList<Friendship> friendships = new ArrayList<Friendship>();
			while (rSet.next()) {
				Friendship friendship = new Friendship();
				friendship.setUser1(new User());
				friendship.setUser2(new User());
				friendship.setFriendshipId(rSet.getInt("friendship_id"));
				friendship.getUser1().setUserId(rSet.getInt("user_id1"));
				friendship.getUser2().setUserId(rSet.getInt("user_id2"));
				friendship.setStatus(rSet.getInt("friend_status_id"));
				int request_sent_by = rSet.getInt("req_sent_by");
				friendship
						.setReq_sent_by(friendship.getUser1().getUserId() == request_sent_by ? friendship
								.getUser1() : friendship.getUser2());
				int blocked_by = rSet.getInt("blocked_by");
				friendship
						.setBlocked_by(friendship.getUser1().getUserId() == blocked_by ? friendship
								.getUser1() : friendship.getUser2());
				friendships.add(friendship);
			}
			return friendships;
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

	public Friendship selectFriendshipByUsers(int userId1, int userId2)
			throws SQLException {
		String SelectFriendhipByUsersStatement = SELECT_FRIENDSHIP_BY_USERIDS;
		UserDal ud = new UserDal();
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectFriendhipByUsersStatement);
			ps.setInt(1, userId1);
			ps.setInt(2, userId2);
			ps.setInt(3, userId1);
			ps.setInt(4, userId2);
			logger.info(SelectFriendhipByUsersStatement);
			rSet = ps.executeQuery();
			if (rSet != null) {
				if (rSet.next()) {
					int friendshipId = rSet.getInt("friendship_id");
					int user_id1 = rSet.getInt("user_id1");
					int user_id2 = rSet.getInt("user_id2");
					int friendshipStatus = rSet.getInt("friend_status_id");
					int request_sent_by = rSet.getInt("req_sent_by");
					int blocked_by = rSet.getInt("blocked_by");

					Friendship friendship = new Friendship();
					friendship.setFriendshipId(friendshipId);
					friendship.setUser1(ud.selectUserByPrimaryKey(user_id1));
					friendship.setUser2(ud.selectUserByPrimaryKey(user_id2));
					friendship.setStatus(friendshipStatus);
					friendship
							.setReq_sent_by(friendship.getUser1().getUserId() == request_sent_by ? friendship
									.getUser1() : friendship.getUser2());
					friendship
							.setBlocked_by(friendship.getUser1().getUserId() == blocked_by ? friendship
									.getUser1() : friendship.getUser2());
					return friendship;
				}
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rSet != null)
				rSet.close();
			if (ps != null)
				ps.close();
			//con.close();
		}
		return null;
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("resource")
	public ArrayList<Friendship> selectFriendshipByUserId(User u)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rSet = null;
		String SelectUsersByUID1 = SELECT_ACTIVE_FRIENDSHIP_BY_USER1ID;
		String SelectUsersByUID2 = SELECT_ACTIVE_FRIENDSHIP_BY_USER2ID;
		Friendship f;
		ArrayList<Friendship> flist = new ArrayList<Friendship>();
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersByUID1);
			ps.setInt(1, u.getUserId());
			rSet = ps.executeQuery();
			while (rSet.next()) {
				f = populateFriendship(rSet, u);
				flist.add(f);
			}

			ps = con.prepareStatement(SelectUsersByUID2);
			ps.setInt(1, u.getUserId());
			rSet = ps.executeQuery();
			while (rSet.next()) {
				f = populateFriendship(rSet, u);
				flist.add(f);
			}
			return flist;
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

	public Friendship selectFriendshipByPrimaryKey(int id) throws SQLException {
		String SelectUsersByPrimaryKeyStatement = SELECT_ACTIVE_FRIENDSHIP_BY_USER1ID;
		PreparedStatement ps = null;
		ResultSet rSet = null;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersByPrimaryKeyStatement);
			ps.setInt(1, id);
			rSet = ps.executeQuery();
			if (rSet.next()) {
				Friendship friendship = new Friendship();
				friendship.setUser1(new User());
				friendship.setUser2(new User());
				friendship.setFriendshipId(rSet.getInt("friendship_id"));
				friendship.getUser1().setUserId(rSet.getInt("user_id1"));
				friendship.getUser2().setUserId(rSet.getInt("user_id2"));
				friendship.setStatus(rSet.getInt("friend_status_id"));
				int request_sent_by = rSet.getInt("req_sent_by");
				friendship
						.setReq_sent_by(friendship.getUser1().getUserId() == request_sent_by ? friendship
								.getUser1() : friendship.getUser2());
				int blocked_by = rSet.getInt("blocked_by");
				friendship
						.setBlocked_by(friendship.getUser1().getUserId() == blocked_by ? friendship
								.getUser1() : friendship.getUser2());
				return friendship;
			} else
				return null;
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

	/**
	 * @param f
	 * @return
	 * @throws SQLException
	 */
	public int insertFriendship(Friendship f) throws SQLException {
		con = DatabaseHandlerSingleton.getDBConnection();
		String insertFriendshipStatement = INSERT_FRIENDSHIP;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(insertFriendshipStatement);
			ps.setInt(1, f.getUser1().getUserId());
			ps.setInt(2, f.getUser2().getUserId());
			ps.setInt(3, f.getStatus().getStatus());
			ps.setInt(4, -1);
			ps.setInt(5, f.getReq_sent_by().getUserId());

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

	/**
	 * @param f
	 * @return
	 * @throws SQLException
	 */
	public int updateFriendship(Friendship f) throws SQLException {
		String updateFriendshipStatement = UPDATE_FRIENDSHIP;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(updateFriendshipStatement);
			ps.setInt(1, f.getUser1().getUserId());
			ps.setInt(2, f.getUser2().getUserId());
			ps.setInt(3, f.getStatus().getStatus());
			ps.setInt(4, f.getBlocked_by().getUserId());
			ps.setInt(5, f.getReq_sent_by().getUserId());
			ps.setInt(6, f.getFriendshipId());
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

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int deleteFriendship(int id) throws SQLException {
		String deleteFriendshipStatement = DELETE_FRIENDSHIP;
		PreparedStatement ps = null;
		int result = -1;
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(deleteFriendshipStatement);
			ps.setInt(1, id);
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

	private Friendship populateFriendship(ResultSet rSet, User u)
			throws SQLException {
		Friendship friendship = new Friendship();
		friendship.setFriendshipId(rSet.getInt("friendship_id"));
		friendship.setStatus(rSet.getInt("friend_status_id"));
		int request_sent_by = rSet.getInt("req_sent_by");
		friendship
				.setReq_sent_by(friendship.getUser1().getUserId() == request_sent_by ? friendship
						.getUser1() : friendship.getUser2());
		int blocked_by = rSet.getInt("blocked_by");
		friendship
				.setBlocked_by(friendship.getUser1().getUserId() == blocked_by ? friendship
						.getUser1() : friendship.getUser2());

		friendship.setUser1(u);
		User otherUser = new User(rSet.getInt("user_id"),
				rSet.getString("fname"), rSet.getString("lname"),
				rSet.getString("contact_number"), rSet.getString("email"),
				rSet.getString("password"), rSet.getDate("bdate"),
				rSet.getInt("isVerified"), rSet.getInt("isLocked"),
				rSet.getDate("lastActive"));
		friendship.setUser2(otherUser);
		return friendship;
	}

	public ArrayList<Friendship> selectPendingFriendRequests(User u)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rSet = null;
		String SelectUsersByUID2 = SELECT_PENDING_FRIEND_REQUESTS;
		Friendship f;
		ArrayList<Friendship> flist = new ArrayList<Friendship>();
		try {
			con = DatabaseHandlerSingleton.getDBConnection();
			ps = con.prepareStatement(SelectUsersByUID2);
			ps.setInt(1, u.getUserId());
			rSet = ps.executeQuery();
			while (rSet.next()) {
				f = populateFriendship(rSet, u);
				
				//add friend to return list only if it is a pending friends request.
				if (f.getStatus() == FriendshipStatus.RequestSent) {
					flist.add(f);
				}
			}
			return flist;
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
}