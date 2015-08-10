package com.aloha.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aloha.common.entities.Friendship;
import com.aloha.common.entities.user.User;
import com.aloha.common.entities.user.UserEducation;
import com.aloha.common.entities.user.UserPersonal;
import com.aloha.common.model.UserUI;
import com.aloha.common.sal.LoginService;
import com.aloha.common.util.CommonUtils;
import com.aloha.common.util.ProfileImage;
import com.aloha.common.util.Secure_Hash;

@Controller
@SessionAttributes("sessionUser")
public class LoginController extends Secure_Hash{
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static HashMap<String,Integer> userMap = new HashMap<String,Integer>();
	CommonUtils commonUtils = new CommonUtils();

	/*
	 * renders the login page
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String Login(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome login! The client locale is {}.", locale);
		UserUI u = new UserUI();
		Friendship f = new Friendship(); 
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
		ArrayList<Friendship> pendingRequests = null;
		User userInSession = commonUtils.convertUserUIToUser(u);
		pendingRequests = f.getPendingFriendshipRequest(userInSession);
		model.addAttribute("pendingFriends", pendingRequests);

		return "redirect:"+"user_profile";
	}
	
	/*
	 * calls the service layer methods and delegates the task of login to login service layer
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String perform_login(@RequestParam("email") String email, @RequestParam("pwd") String pwd, Model model, HttpSession session){
		logger.info("Welcome login! The client locale is {}.");
		UserUI ui = new UserUI();
		LoginService login_service = new LoginService();

		if(null==session.getAttribute("sessionUser")){
			int value = 0;
			if(userMap.containsKey(email))
			{
				value = userMap.get(email);
				
			}
			value++;
			userMap.put(email, value);
			
			int res = login_service.perform_login(email, pwd, ui, value);

			model.addAttribute("globalstatus","login");
			model.addAttribute("globalstatuslink","login");
			if(res==1)
			{
				userMap.put(email,0);
				model.addAttribute("sessionUser",ui);
				return "redirect:"+"user_profile";	
			}
			else if(res==0)
			{
				model.addAttribute("headerMessage","your account is locked due incorrect attempts. Unlock using forgot password");
				return "forgotpassword";
			}
			else if(res==-1)
			{
				model.addAttribute("headerMessage","something went wrong, please try again");
				return "Login";
			}
			else
			{
				model.addAttribute("headerMessage","Invalid Credentials.");
				return "Login";
			}
		}else{			
			ui = (UserUI)session.getAttribute("sessionUser");
			model.addAttribute("globalstatus","logout");
			model.addAttribute("globalstatuslink","logout");
			model.addAttribute("user",ui);
			
			return "redirect:"+"user_profile";	
		}
		
	}

	
	
	@RequestMapping(value = "user_profile", method = RequestMethod.GET)
	public String display_user_profile(Model model, HttpSession session){
		UserUI u = new UserUI();
		Friendship f = new Friendship();
		UserEducation u_ed = null;
		UserPersonal up = null;
		LoginService login_service = new LoginService();
		if(null==session.getAttribute("sessionUser")){
			model.addAttribute("globalstatus","login");
			model.addAttribute("globalstatuslink","login");
			return "redirect:"+"login";
		}else{
			u = (UserUI)session.getAttribute("sessionUser");
			u_ed = login_service.getEducation(u);
			up = login_service.get_personal(u);		
			model.addAttribute("globalstatus","logout");
			model.addAttribute("globalstatuslink","logout");
		}
		model.addAttribute("education",u_ed);
		model.addAttribute("personal",up);
		model.addAttribute("user",u);	
		
		ArrayList<Friendship> pendingRequests = null;
		User userInSession = commonUtils.convertUserUIToUser(u);
		pendingRequests = f.getPendingFriendshipRequest(userInSession);
		model.addAttribute("pendingFriends", pendingRequests);

		return "user_profile";
	}
	
	@RequestMapping(value = "displayimage", method = RequestMethod.GET)
	void display_image(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        int user_id = Integer.parseInt(id);
        ProfileImage pi = null;
        LoginService login_service = new LoginService();
		try {
			pi = login_service.display_image(user_id);
			if(pi!=null)
			{	
				response.setContentType("image/jpeg");
	            response.getOutputStream().write( pi.img);
	            response.getOutputStream().close();
			}
			else
			{
				response.getWriter().println("No Image");
	            response.getWriter().close();
			}
       
		} catch (Exception e) {
	
			e.printStackTrace();
		    response.getWriter().println("Sample text");
            response.getWriter().close();
		}
             
    }
	
	@RequestMapping(value = "forgotpassword", method = RequestMethod.GET)
	public String forgot_password_display(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome login! The client locale is {}.", locale);
		UserUI u = new UserUI();
		if(null==session.getAttribute("sessionUser")){
			model.addAttribute("globalstatus","login");
			model.addAttribute("globalstatuslink","login");
			return "forgotpassword";
		}else{
			u = (UserUI)session.getAttribute("sessionUser");
			model.addAttribute("globalstatus","logout");
			model.addAttribute("globalstatuslink","logout");
		}
		model.addAttribute("user",u);
		return "user_profile";
	}
	
	
	@Autowired
    private JavaMailSender mailSender;
	@RequestMapping(value = "forgotpassword", method = RequestMethod.POST)
	public String forgot_password(@RequestParam("email") String email , Model model, HttpSession session){
		logger.info("Welcome login! The client locale is {}.");
		UserUI u = new UserUI();
		String res = "";
		String sub = "";
		String message = "your verification password is:  ";
		String message2 = "\nPlease click the following link to reset it:\n ";
		String link = "localhost:8080/common/changepassword?id="; String end = "copy this link and paste it in your browser";
		LoginService login_service = new LoginService();
		int userId = 0;
		if(null==session.getAttribute("sessionUser")){
			User out = login_service.forgot_password(email);
			if(out!=null)
			{
				link+=out.getUserId();
				link+=end;
				message+=out.getPassword();
				SimpleMailMessage emailobj = new SimpleMailMessage();
		        emailobj.setTo(email);
		        emailobj.setSubject("Password Reset Link");
		        emailobj.setText(message+message2+link);
		         
		        // sends the e-mail
		        mailSender.send(emailobj);
		        res = "Check your inbox, A password reset link has been sent to "+email;
				
			}
			else
			{
				res = email+"no such user exists";
			}
			model.addAttribute("headerMessage",res);
			model.addAttribute("globalstatus","login");
			model.addAttribute("globalstatuslink","login");
			return "forgotpassword";
		}
		else{
			u = (UserUI)session.getAttribute("sessionUser");
			model.addAttribute("headerMessage","you are already logged in");
			model.addAttribute("globalstatus","logout");
			model.addAttribute("globalstatuslink","logout");
		}
		model.addAttribute("user",u);
		return "redirect:"+"logout";
	}
	@RequestMapping(value = "changepassword", method = RequestMethod.GET)
	public String changePasswordDisplay(@RequestParam("id") String id, Locale locale, Model model, HttpSession session) {
		int userId = Integer.parseInt(id);
		LoginService loginService = new LoginService();
		int isUser = loginService.checkIfUser(userId);
		if(isUser==1)
		{
			model.addAttribute("id",id);
			return "changepassword";
		}
		return "redirect:"+"error";
	}
	@RequestMapping(value = "changepassword", method = RequestMethod.POST)
	public String changePassword(@RequestParam("id") String id, @RequestParam("vpwd") String vpwd, @RequestParam("pwd") String pwd, @RequestParam("cpwd") String cpwd,  Model model, HttpSession session){
		int userId = Integer.parseInt(id);
		LoginService loginService = new LoginService();
		int verify = loginService.verifyCode(userId,vpwd);
		if(verify==1)
		{
			int isSet = loginService.setUserPassword(userId,pwd);
			if(isSet!=1)
			{
				model.addAttribute("headerMessage","could not perform action! Try again");
				return "changepassword";
			}
			String email = loginService.unlockAccount(userId);
			userMap.put(email, 0);
			return "redirect:"+"login";
		}
		//System.out.println(userId);
		else
			return "changepassword";
	}
}
