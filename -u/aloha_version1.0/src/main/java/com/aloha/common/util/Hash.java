package com.aloha.common.util;

import java.security.NoSuchAlgorithmException;

public interface Hash {
	public String getHash(String attempted_pwd) throws NoSuchAlgorithmException;
	public boolean isMatch(byte[] h1, byte[] h2);
}
