package com.aloha.common.entities.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.dao_manager.dal.UserEducationDal;
import com.aloha.common.dao_manager.dal.UserPersonalDal;
import com.aloha.common.model.UserUI;

public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String contactNumber;
	private String email;
	private String password;
	private Date dateOfBirth;
	private UserEducation u_ed;
	private UserPersonal pu;
	private int isVerified;
	private int isLocked;
	private Date lastActive;
	private int privacy;
	private UserDal ud;
	private UserEducationDal edal;
	private UserPersonalDal pdal;

	public User() {
		this.userId = 0;
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.password = null;
		this.dateOfBirth = null;
		this.isVerified = -1;
		this.isLocked = -1;
		this.lastActive = null;
		ud = new UserDal();
		edal = new UserEducationDal();
		pdal = new UserPersonalDal();
	}

	public User(int id, String fName, String lName, String email, String pwd,
			String contactNumber, Date dob, int isVerified, int isLocked,
			Date lastActive) {
		this.userId = id;
		this.firstName = fName;
		this.lastName = lName;
		this.email = email;
		this.password = pwd;
		this.dateOfBirth = dob;
		this.isVerified = isVerified;
		this.isLocked = isLocked;
		this.lastActive = lastActive;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
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
	 * @param firstName
	 *            the firstName to set
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
	 * @param lastName
	 *            the lastName to set
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
	 * @param contactNumber
	 *            the contactNumber to set
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
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
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
	 * @param isVerified
	 *            the isVerified to set
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
	 * @param isLocked
	 *            the isLocked to set
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
	 * @param lastActive
	 *            the lastActive to set
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

	public UserPersonal getPersonalInfo() throws SQLException {
		pu = new UserPersonal();
		pu = pdal.selectUserPersonalById(userId);
		return pu;
	}

	public int addPersonalInfo(String aboutme, String city) {
		pu = new UserPersonal();
		pu.setAboutme(aboutme);
		pu.setCity(city);
		int res = -1;
		try {
			pu = pdal.addPersonalInfo(userId, pu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pu.getP_id();
	}

	public int modifyPersonalInfo(String aboutme, String city) {
		pu.setAboutme(aboutme);
		pu.setCity(city);
		int res = -1;
		try {
			res = pdal.updateUserPersonal(userId, pu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public int deletePersonal() {
		int res = -1;
		try {
			res = pdal.deleteUserPersonal(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public UserEducation getEducationInfo() throws SQLException {
		u_ed = new UserEducation();
		u_ed = edal.selectUserEducationById(userId);
		return u_ed;
	}

	public int addEducationInfo(String school, String area) {
		u_ed = new UserEducation();
		u_ed.setEducation(school, area);
		int res = -1;
		try {
			res = edal.addEducationInfo(userId, u_ed);
			u_ed.setID(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public int modifyEducationInfo(String school, String area) {
		u_ed.setEducation(school, area);
		int res = -1;
		try {
			res = edal.updateUserEducation(userId, u_ed);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public int deleteEducation() {
		int res = -1;
		try {
			res = edal.deleteUserEducation(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public User getUser(int id) throws SQLException {
		User u_new = ud.selectUserByPrimaryKey(id);
		// Add code to get user from DAL layer
		return u_new;

	}

	public int deleteUser(int id) throws SQLException {
		// Add code to delete user by calling DAL method
		int res = ud.deleteUser(id);
		return res;
	}

	public int updateUser(User user) throws SQLException {
		// Add code to call DAL method
		int u_new = ud.updateUser(user);
		return u_new;
	}

	public int addUser(User user) throws SQLException {
		// call to DAL
		int res = ud.insertUser(user);
		return res;
	}

	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(new StringBuilder().append("[userId=")
				.append(userId).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[firstName=")
				.append(firstName).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[lastName=")
				.append(lastName).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[contactNumber=")
				.append(contactNumber).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[email=").append(email)
				.append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[password=")
				.append(password).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[dateOfBirth=")
				.append(dateOfBirth).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[isVerified=")
				.append(isVerified).append(" items]\n").toString());
		stringBuffer.append(new StringBuilder().append("[isLocked=")
				.append(isLocked).append("]\n").toString());
		stringBuffer.append(new StringBuilder().append("[lastActive=")
				.append(lastActive).append("]\n").toString());
		return stringBuffer.toString();
	}

	public void updatefromUI(UserUI u) {
		// TODO Auto-generated method stub
		this.contactNumber = u.getContactNumber();
		this.dateOfBirth = u.getDateOfBirth();
		this.userId = u.getUserId();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
	}

	/**
	 * 
	 * Method to get users given their firstname as input.
	 * @param name
	 * @return
	 */
	public ArrayList<User> getUsersByName(String name) {
		ArrayList<User> users = null;
		try {
			users = ud.selectUsersByName(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;

	}

}
