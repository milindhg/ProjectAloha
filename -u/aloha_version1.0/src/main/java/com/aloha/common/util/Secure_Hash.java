package com.aloha.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Secure_Hash implements Hash {
	
	@Override
	public String getHash(String attempted_pwd) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(attempted_pwd.getBytes());
        byte h1[] = md.digest();
        
		return h1.toString();
	}

	@Override
	public boolean isMatch(byte[] h1, byte[] h2) {
		// TODO Auto-generated method stub
		return Arrays.equals(h1, h2);
	}

}
