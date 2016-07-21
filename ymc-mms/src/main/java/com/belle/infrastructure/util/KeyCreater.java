package com.belle.infrastructure.util;

import java.util.UUID;

/**
 * 主键生成器
 * 
 * @author luosm
 * 
 */
public class KeyCreater {

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String key = uuid.toString();
		key = key.replaceAll("-", "");
		return key;
	}
}
