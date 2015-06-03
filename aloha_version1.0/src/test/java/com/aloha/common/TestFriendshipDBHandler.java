package com.aloha.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import com.aloha.common.dao_manager.DatabaseHandlerSingleton;
import com.aloha.common.dao_manager.dal.FriendshipDal;
import com.aloha.common.dao_manager.dal.ImageDal;
import com.aloha.common.dao_manager.dal.UserDal;
import com.aloha.common.entities.Friendship;
import com.aloha.common.entities.FriendshipStatus;
import com.aloha.common.entities.user.User;
import com.aloha.common.util.ProfileImage;

public class TestFriendshipDBHandler {
	static final Logger logger = Logger.getLogger(TestFriendshipDBHandler.class
			.toString());
	UserDal ud;
	FriendshipDal fd;
	@Autowired
	private JavaMailSender mailSender;

	public TestFriendshipDBHandler() {
		ud = new UserDal();
		fd = new FriendshipDal();
	}

	public static void main(String[] args) {
		Connection con = DatabaseHandlerSingleton.getDBConnection();
		logger.info("OKOK");
		logger.debug("OKOK_DEBUG");
		logger.error("ERROR OCCURRED");
		try {
			TestFriendshipDBHandler test = new TestFriendshipDBHandler();
			// test.insertFriendship();
			// test.selectFriendship(3);
			// test.updateFriendship(3);
			//test.selectFriendship(4);
			// test.deleteFriendship(3);
			// test.selectAllFriendships();
			// test.sendMailToFriend(5,
			// "Online friends has been integrated with chat :D :D ");
			treeMapSOrtingTest();
			UserDal udal = new UserDal();
			int[] ids = new int[4];
			ids[0] = 5;
			ids[1] = 4;
			ids[2] = 6;
			ids[3] = 7;

			System.out.println(udal.selectMutlipleUsersByPrimaryKey(ids));
			try {
				uploadDefaultImage();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void selectAllFriendships() throws SQLException {

		ArrayList<Friendship> flist = fd.selectFriendshipAll();

		for (Friendship friendship : flist) {
			System.out.println(friendship.toString());
		}

	}

	private void selectFriendship(int id) throws SQLException {
		Friendship f = new Friendship();
		f = fd.selectFriendshipByPrimaryKey(id);
		System.out.println(f.toString());

	}

	private void insertFriendship() throws SQLException {
		int res;
		Friendship f = new Friendship();
		User u1 = ud.selectUserByPrimaryKey(6);
		User u2 = ud.selectUserByPrimaryKey(8);
		f.setUser1(u1);
		f.setUser2(u2);
		f.setReq_sent_by(u1);
		f.setStatus(FriendshipStatus.RequestSent);

		res = fd.insertFriendship(f);
		if (res == 1)
			System.out.println("row inserted successfully");
	}

	private void updateFriendship(int id) throws SQLException {
		int res;
		Date dob = Date.valueOf("1999-12-31");
		User u = new User();

		u = ud.selectUserByPrimaryKey(id);
		u.setFirstName("Milind");
		u.setLastName("Gokhale");
		u.setContactNumber("8123697654");
		u.setEmail("mgokhale@indiana.edu");
		u.setPassword("root");
		u.setDateOfBirth(dob);
		u.setIsVerified(0);
		u.setIsLocked(0);
		u.setLastActive(dob);
		res = ud.updateUser(u);
		if (res == 1)
			System.out.println("row updated successfully");

	}

	private void deleteFriendship(int id) throws SQLException {

		int res = fd.deleteFriendship(id);
		if (res == 1)
			System.out.println("row deleted successfully");
	}

	public void mailTest() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "my-mail-server");
		props.put("mail.from", "me@example.com");
		Session session = Session.getInstance(props, null);

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom();
			msg.setRecipients(Message.RecipientType.TO, "you@example.com");
			msg.setSubject("JavaMail hello world example");
			msg.setSentDate(new Date(System.currentTimeMillis()));
			msg.setText("Hello, world!\n");
			Transport.send(msg);
		} catch (MessagingException mex) {
			System.out.println("send failed, exception: " + mex);
		}
	}

	public static void treeMapSOrtingTest() {
		HashMap h = new HashMap<Integer, Integer>();
		h.put(1, 1);
		h.put(2, 4);
		h.put(3, 2);

		Map<Integer, Integer> reversedMap = sortByValues(h);
		Set s1 = reversedMap.entrySet();
		Iterator iterator1 = s1.iterator();

		while (iterator1.hasNext()) {
			Map.Entry<Integer, Integer> me2 = (Map.Entry<Integer, Integer>) iterator1
					.next();
			System.out.print(me2.getKey() + ": ");
			System.out.println(me2.getValue());
		}
	}

	private static HashMap sortByValues(HashMap map) {
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	private static void uploadDefaultImage() throws FileNotFoundException {
		FileInputStream file = new FileInputStream(
				"F:/Users/Milind/Documents/GitHub/ALOHA_CodeBase/aloha_version1.0/src/main/webapp/resources/img/user.jpg");
		byte[] bytefile;
		ProfileImage pi = null;
		ImageDal pdal = new ImageDal();
		if (file != null) {
			System.out.println("uploaded");
			try {
				// bytefile = file.getBytes();
				bytefile = IOUtils.toByteArray(file);
				/*
				 * pi = pdal.getProfileImage(u.getUserId()); if(pi!=null) {
				 * pdal.modifyImage(u.getUserId(), bytefile, pi.getImg_id());
				 * pi.setImg(bytefile); } else
				 */
				pi = pdal.insertImage(42, bytefile);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}