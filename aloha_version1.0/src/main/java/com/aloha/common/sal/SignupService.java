package com.aloha.common.sal;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.entities.user.User;

public class SignupService {
	
	public int perform_sign_up(String fname,String lname, String cnum, String email, Date dob, String pwd, String cpwd) {
		
		UserDal ud = new UserDal();
		User u = new User();
	
		/*String hashed_pwd = "";
		try {
			hashed_pwd = getHash(pwd);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			model.addAttribute("Something went wrong please try again");
		}*/
		boolean is_uniq = true;
		try {
			is_uniq = ud.checkIfUniqueEmail(email);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(!is_uniq)
		{
			u.setFirstName(fname);
			u.setLastName(lname);
			u.setContactNumber(cnum);		
			//u.setPassword(hashed_pwd);
			u.setDateOfBirth(dob);
			u.setPassword(pwd);
			u.setIsLocked(0);
			u.setIsVerified(0);
			u.setEmail(email);
			u.setLastActive(new Timestamp(System.currentTimeMillis()));
			int res =0;
			try {
				res = ud.insertUser(u);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return 0;
			}
			return res;
			//System.out.println("success");
		}
		else
			return -1;
		
	}


}
