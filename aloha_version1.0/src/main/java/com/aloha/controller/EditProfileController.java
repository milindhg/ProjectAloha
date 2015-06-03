package com.aloha.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.aloha.common.dao_manager.dal.ImageDal;
import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.dao_manager.dal.UserEducationDal;
import com.aloha.common.dao_manager.dal.UserPersonalDal;
import com.aloha.common.entities.user.User;
import com.aloha.common.entities.user.UserEducation;
import com.aloha.common.entities.user.UserPersonal;
import com.aloha.common.model.UserUI;
import com.aloha.common.sal.EditProfileService;
import com.aloha.common.util.FileUploadBean;
import com.aloha.common.util.ProfileImage;

@SuppressWarnings("deprecation")
@Controller
@SessionAttributes("sessionUser")
public class EditProfileController{
	
	@RequestMapping(value = "editprofile", method = RequestMethod.GET)
	public String edit_profile(Locale locale, Model model, HttpSession session) {
		UserUI u = new UserUI();
		if(null==session.getAttribute("sessionUser")){
			model.addAttribute("globalstatus","login");
			model.addAttribute("globalstatuslink","login");
			return "Login";
		}else{
			u = (UserUI)session.getAttribute("sessionUser");
			model.addAttribute("globalstatus","logout");
			model.addAttribute("globalstatuslink","logout");
		}
		model.addAttribute("user",u);
		return "editprofile";
	}
	
	@RequestMapping(value = "uploadform", method = RequestMethod.POST)
	public String save(FileUploadBean uploadForm, Model map, HttpSession session) {
		UserUI u = new UserUI();
		if(null==session.getAttribute("sessionUser")){
			map.addAttribute("globalstatus","login");
			map.addAttribute("globalstatuslink","login");
			return "Login";
		}else{
			u = (UserUI)session.getAttribute("sessionUser");
			map.addAttribute("globalstatus","logout");
			map.addAttribute("globalstatuslink","logout");
			map.addAttribute("user",u);
			MultipartFile file = uploadForm.getFile();
	        byte[] bytefile;
	        ProfileImage pi = null;
	        ImageDal pdal = new ImageDal();
	        if (file != null) {
	        	System.out.println("uploaded");
	        	try {
	        		bytefile = file.getBytes();
	        		pi = pdal.getProfileImage(u.getUserId());
		        	if(pi!=null)
		        	{
		        		pdal.modifyImage(u.getUserId(), bytefile, pi.getImg_id());
		        		pi.setImg(bytefile);
		        	}
		        	else
		        		pi = pdal.insertImage(u.getUserId(), bytefile);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					map.addAttribute("headerMessage","failed to upload file");
					return "redirect:"+"user_profile";
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					map.addAttribute("headerMessage","failed to upload file");
					return "redirect:"+"user_profile";
				}
	        	  
	       	 	map.addAttribute("headerMessage","success");
	       	 }
	       	 else{
	       		System.out.println(" not uploaded");
	       		map.addAttribute("headerMessage","failed to upload file");
	       }
	        map.addAttribute("pi",pi);
	        return "redirect:"+"user_profile";
		}
		  
	        
    }
	@RequestMapping(value = "editaccountdetails", method = RequestMethod.GET)
	public String show_page(Model map,HttpSession session){
		UserUI u = new UserUI();
 		if(null==session.getAttribute("sessionUser")){
			map.addAttribute("globalstatus","login");
			map.addAttribute("globalstatuslink","login");
			return "Login";
		}
		else
		{
			u = (UserUI)session.getAttribute("sessionUser");
			map.addAttribute("globalstatus","logout");
			map.addAttribute("globalstatuslink","logout");
		}
		map.addAttribute("user",u);
		return "editaccountdetails";
	}
	@RequestMapping(value = "editaccountdetails", method = RequestMethod.POST)
	public String save_account_details(@RequestParam("fname") String fname,@RequestParam("lname") String lname, @RequestParam("cnum") String cnum, @RequestParam("dob") Date dob, Model map, HttpSession session) {
		UserUI u = new UserUI();
		String res = "";
		EditProfileService eps = new EditProfileService();
		if(null==session.getAttribute("sessionUser")){
			map.addAttribute("globalstatus","login");
			map.addAttribute("globalstatuslink","login");
			return "Login";
		}else{
			u = (UserUI)session.getAttribute("sessionUser");
			res = eps.save_account_details(fname, lname, cnum, dob, u);
			map.addAttribute("globalstatus","logout");
			map.addAttribute("globalstatuslink","logout");
			map.addAttribute("user",u);
		}
	    return "redirect:"+"user_profile";		
	}
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	
	@RequestMapping(value="educationinfo", method=RequestMethod.POST)
	public String saveeducation(@RequestParam("school") String school, @RequestParam("area") String area, Model model, HttpSession session){
		UserUI u = new UserUI();
		EditProfileService eps = new EditProfileService();
		
		if(null==session.getAttribute("sessionUser")){
			model.addAttribute("globalstatus","login");
			model.addAttribute("globalstatuslink","login");
			return "Login";
		}
		else
		{
			u = (UserUI)session.getAttribute("sessionUser");
			model.addAttribute("globalstatus","logout");
			model.addAttribute("globalstatuslink","logout");
			int res = eps.saveeducation(school, area, u);
		}
		model.addAttribute("user",u);
		return "redirect:"+"user_profile";
	}
	@RequestMapping(value="personalinfo", method=RequestMethod.POST)
	public String savepersonal(@RequestParam("aboutme") String aboutme, @RequestParam("livesin") String city, Model model, HttpSession session){
		UserUI u = new UserUI();
		EditProfileService eps = new EditProfileService();
		
		if(null==session.getAttribute("sessionUser")){
			model.addAttribute("globalstatus","login");
			model.addAttribute("globalstatuslink","login");
			
			return "Login";
		}
		else
		{
			u = (UserUI)session.getAttribute("sessionUser");
			model.addAttribute("globalstatus","logout");
			model.addAttribute("globalstatuslink","logout");
			
			int res = eps.savepersonal(aboutme, city, u);
			
		}
		model.addAttribute("user",u);
		return "redirect:"+"user_profile";
	}
	@RequestMapping(value="privacysetting", method=RequestMethod.POST)
	public @ResponseBody boolean editPrivacy(Model model, HttpSession session){
		UserUI u = new UserUI();
		EditProfileService eps = new EditProfileService();
		
		if(null==session.getAttribute("sessionUser")){
		}
		else
		{
			u = (UserUI)session.getAttribute("sessionUser");
			
			int res = eps.setPrivacy(u);
			if(res==0)
				return false;
		}
		return true;
	}	
	
}
