package com.yougou.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Encryptor {

	public static String encrypt(String s) {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(s.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support SHA-1 algorithm.");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System doesn't support UTF-8 encoding.");
		}

		byte[] bytes = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i) {
			sb.append(Integer.toHexString((bytes[i] & 0xff) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
}
