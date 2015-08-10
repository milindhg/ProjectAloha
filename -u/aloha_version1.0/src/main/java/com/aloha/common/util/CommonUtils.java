package com.aloha.common.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.aloha.common.entities.user.User;
import com.aloha.common.model.UserUI;
import com.sun.mail.smtp.SMTPAddressFailedException;

public class CommonUtils {

	public User convertUserUIToUser(UserUI userUI) {
		User user = new User();
		user.setUserId(userUI.getUserId());
		user.setFirstName(userUI.getFirstName());
		user.setLastName(userUI.getLastName());
		user.setContactNumber(userUI.getContactNumber());
		user.setDateOfBirth(userUI.getDateOfBirth());
		user.setEmail(userUI.getEmail());
		user.setIsLocked(userUI.getIsLocked());
		user.setIsVerified(userUI.getIsVerified());
		user.setLastActive(userUI.getLastActive());
		return user;
	}

	public void mailSendUtil(JavaMailSender mailSender, String emailTo, String emailFrom,String mailContent) throws SMTPAddressFailedException{
		SimpleMailMessage emailobj = new SimpleMailMessage();
		emailobj.setTo(emailTo);
		emailobj.setSubject("Check out Aloha");
		emailobj.setText(mailContent);
		emailobj.setFrom(emailFrom);
		mailSender.send(emailobj);
	}
}
