package com.belle.memcached.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 
 * <p>Title:</p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: 
 * </p>
 * 
 * @Author 
 * @Create 2011-5-1
 * @desc Hash算法类
 * @Modify 
 * @Version 1.0
 */
public class HashFunction {

	private MessageDigest md5 = null;
	
	/**
	 * 
	 * @author wuxi
	 * @description 获得key的hash值
	 * @param key 为cache的键
	 * @return hash值
	 * @modified
	 */
	public int hash(String key) {
		if(md5 == null) {
			try {
				md5 = MessageDigest.getInstance("MD5");
			}catch (NoSuchAlgorithmException  e) {
				throw new IllegalStateException("no md5 algorythm found");   
			}
		}
		
		md5.reset();
		md5.update(key.getBytes());
		byte[] bKey = md5.digest();
		int res = ((int) (bKey[3] & 0xFF) << 24)   
        | ((int) (bKey[2] & 0xFF) << 16)   
        | ((int) (bKey[1] & 0xFF) << 8) | (int) (bKey[0] & 0xFF);   
		return res;   

	}
	
}
