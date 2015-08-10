/**
 * 
 */
package com.aloha.common.entities;

/**
 * @author Milind
 *
 */
public enum FriendshipStatus {
	RequestSent(1), Friends(2), Blocked(3), Default(0), FriendshipRequestSent(4);

	private int status;

	private FriendshipStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
}
