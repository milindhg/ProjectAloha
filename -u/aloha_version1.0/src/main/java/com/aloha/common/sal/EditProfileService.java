package com.aloha.common.sal;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.common.dao_manager.dal.ImageDal;
import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.dao_manager.dal.UserEducationDal;
import com.aloha.common.dao_manager.dal.UserPersonalDal;
import com.aloha.common.entities.user.User;
import com.aloha.common.entities.user.UserEducation;
import com.aloha.common.entities.user.UserPersonal;
import com.aloha.common.model.UserUI;
import com.aloha.common.util.FileUploadBean;
import com.aloha.common.util.ProfileImage;

public class EditProfileService {
	
	public String save_account_details(String fname,String lname,String cnum,Date dob, UserUI u) {
		User update_u = new User();
		UserDal ud = new UserDal();

		u.setFirstName(fname);
		u.setLastName(lname);
		u.setContactNumber(cnum);		
		u.setDateOfBirth(dob);
		update_u.updatefromUI(u);
		int res =0;
		try {
			res = ud.updateAccountInfo(update_u);
			if(res==1)
				return "you have successfully updated";
			else
				return "something went wrong, could not save your details";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "Something went wrong";
		}
			
	}
	
	public int saveeducation(String school, String area, UserUI u){
		
		UserEducation ed = null;
		UserEducationDal edal = new UserEducationDal();
		int res = 0;

		try {
				ed = edal.selectUserEducationById(u.getUserId());
				if(ed!=null)
		 		{
					ed.setEducation(school,area);
					res = edal.updateUserEducation(u.getUserId(), ed);					
				}
				else
				{
					ed = new UserEducation();
					ed.setEducation(school,area);
					res = edal.addEducationInfo(u.getUserId(), ed);
				}
				if(res!=0)
					return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int savepersonal(String aboutme, String city, UserUI u){
		UserPersonal up = null;
		UserPersonalDal pdal = new UserPersonalDal();
		int res =0;
			try {
				
				up = pdal.selectUserPersonalById(u.getUserId());
				if(up!=null)
				{
					up.setAboutme(aboutme);
					up.setCity(city);
					res = pdal.updateUserPersonal(u.getUserId(), up);
				}
				else{
					up = new UserPersonal();
					up.setAboutme(aboutme);
					up.setCity(city);
					up = pdal.addPersonalInfo(u.getUserId(), up);
					if(up!=null)
						return 1;
				}
				if(res!=0)
					return 1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return 0;
	}

	public int setPrivacy(UserUI u) {
		// TODO Auto-generated method stub
		UserDal ud = new UserDal();
		try {
			int res = ud.setPrivacy(u.getUserId(), 1-u.getPrivacy());
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
