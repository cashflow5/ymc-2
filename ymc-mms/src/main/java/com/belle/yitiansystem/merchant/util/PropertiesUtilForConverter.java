package com.belle.yitiansystem.merchant.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * ��ȡ�����ļ�
 * @author huang.gx
 *
 */
public class PropertiesUtilForConverter {

	private static Properties prop = null;
	static {
		if(prop == null){
			prop = new Properties();
		}
		InputStream in =  null;
		try {
			in = PropertiesUtilForConverter.class.getResourceAsStream("/config/file_view.properties");
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String getValue(String key){
		String str = prop.getProperty(key);
		return str == null ? null : str.trim();
	}
}
