package com.aloha.common.sal;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.common.dao_manager.dal.ImageDal;
import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.dao_manager.dal.UserEducationDal;
import com.aloha.common.dao_manager.dal.UserPersonalDal;
import com.aloha.common.entities.OnlineUsers;
import com.aloha.common.entities.user.User;
import com.aloha.common.entities.user.UserEducation;
import com.aloha.common.entities.user.UserPersonal;
import com.aloha.common.model.UserUI;
import com.aloha.common.util.ProfileImage;
import com.aloha.common.util.Secure_Hash;

public class LoginService extends Secure_Hash{
	
	public int perform_login(String email, String pwd, UserUI ui, int count) {
		UserDal ud = new UserDal();
		User res= null;
		int accountStatus = 0;
		String newpwd = "ThisIsTest1";
		try {
			newpwd = getHash(pwd);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(count>=8)
			{
				ud.lockaccount(email,newpwd);
				return 0;
			}
			accountStatus = ud.isEmailLocked(email);
			if(accountStatus == 0) //not locked
			{
				res = ud.getPasswordByEmail(email,pwd);
			}
			else if(accountStatus == 1)	//locked
				return 0; //locked
			else
				return -1;	//exception
		} catch (SQLException e) {
			return -1;
		}
		if(res!=null)	
		{
			ui.setUser(res);
			OnlineUsers olUsers = new OnlineUsers();
			olUsers.addUserAsOnline(res.getUserId());
			return 1;					//success
		}
		return -2; //account or password does not match
	}
	
	public UserPersonal get_personal(UserUI u){
			UserPersonalDal updal = new UserPersonalDal();						
			UserPersonal up = new UserPersonal();
			try{
				up = updal.selectUserPersonalById(u.getUserId());
			}
			catch(SQLException ex){
				ex.printStackTrace();
			}
		
		return up;
	}

	public UserEducation getEducation(UserUI u) {
		// TODO Auto-generated method stub
		UserEducationDal ed = new UserEducationDal();
		UserEducation u_ed = new UserEducation();
		try {
			u_ed = ed.selectUserEducationById(u.getUserId());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u_ed;
	}
	
	public ProfileImage display_image(int user_id) {

        ImageDal imd = new ImageDal();
        ProfileImage pi = null;
		try {
			pi = imd.getProfileImage(user_id);
			
			    
		} catch (SQLException e) {
	
			e.printStackTrace();
		    
		}
         return pi;    
    }
	
	@Autowired
    private JavaMailSender mailSender;
	public User forgot_password(String email){
		UserUI u = new UserUI();
		User user = new User();
		UserDal ud = new UserDal();
		int userId=0;
		User res = null;
		try {
			if(ud.checkIfUniqueEmail(email))
			{	
				String newpwd = "ThisIsTest1";
				try {
					newpwd = getHash(newpwd);
					
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ud.lockaccount(email,newpwd);
				user = ud.getUserIdByEmail(email);
				return user;
			}
			else
			{
				
				res = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
		
	}

	public int checkIfUser(int userId) {
		// TODO Auto-generated method stub
		UserDal ud = new UserDal();
		try {
			if(ud.selectUserByPrimaryKey(userId)!=null)
				return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int verifyCode(int userId, String vpwd) {
		// TODO Auto-generated method stub
		UserDal ud = new UserDal();
		User u = new User();
		
		try {
			u = ud.selectUserByPrimaryKey(userId);
			if(u.getPassword().equals(vpwd))
					return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int setUserPassword(int userId, String pwd) {
		// TODO Auto-generated method stub
		UserDal ud = new UserDal();
		try {
			int res = ud.setUserPassword(userId,pwd);
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public String unlockAccount(int userId) {
		// TODO Auto-generated method stub
		UserDal ud = new UserDal();
		String email = "";
		try {
			ud.unlockAccount(userId);
			User u = ud.selectUserByPrimaryKey(userId);
			if(u!=null)
				email = u.getEmail();
			return email;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;
	}
	
}
