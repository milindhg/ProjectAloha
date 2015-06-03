package com.aloha.common.model;

import java.util.Date;

import com.aloha.common.entities.user.User;
import com.aloha.common.entities.user.UserEducation;

public class UserUI {
	int userId;
	String firstName;
	String lastName;
	String contactNumber;
	String email;
	Date dateOfBirth;
	int isVerified;
	int isLocked;
	Date lastActive;
	int privacy;
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}


	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	/**
	 * @return the isVerified
	 */
	public int getIsVerified() {
		return isVerified;
	}


	/**
	 * @param isVerified the isVerified to set
	 */
	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}


	/**
	 * @return the isLocked
	 */
	public int getIsLocked() {
		return isLocked;
	}


	/**
	 * @param isLocked the isLocked to set
	 */
	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}


	/**
	 * @return the lastActive
	 */
	public Date getLastActive() {
		return lastActive;
	}


	/**
	 * @param lastActive the lastActive to set
	 */
	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}
	/**
	 * @return the privacy
	 */
	public int getPrivacy() {
		return privacy;
	}

	/**
	 * @param privacy
	 *            
	 */
	public void setPrivacy(int value) {
		this.privacy = value;
	}


	public void setUser(User res) {
		// TODO Auto-generated method stub
		this.firstName = res.getFirstName();
		this.lastName = res.getLastName();
		this.contactNumber = res.getContactNumber();
		this.dateOfBirth = res.getDateOfBirth();
		this.email = res.getEmail();
		this.isLocked = res.getIsLocked();
		this.isVerified = res.getIsVerified();
		this.userId = res.getUserId();
		this.lastActive = res.getLastActive();
		this.privacy = res.getPrivacy();
	}

}
