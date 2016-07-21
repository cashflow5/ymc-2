/**
 * 
 */
package com.belle.yitiansystem.merchant.util;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.belle.infrastructure.util.FtpUtils;

/**
 * 商家帮助中心辅助类
 * 
 * @author huang.tao
 *
 */
@Component
public class HelpCenterUtil {
	
	private static Logger logger = Logger.getLogger(HelpCenterUtil.class);
	
	@Resource
	private FtpTools ftp;
	
	private final static String path = "/help";
	
	/**
	 * 上传图片
	 * 
	 * @param in
	 * @return
	 */
	public boolean uploadImage(InputStream in, String fileName) {
		FtpUtils ftpUtil = ftp.getFtpUtilsIntance(path);
		boolean result = false;
		try {
			String utf_name = new String(fileName.getBytes("utf-8"), "iso-8859-1");
			result = ftpUtil.uploadContract(in, utf_name);
		} catch (IOException e) {
			logger.info("帮助中心|上传图片失败.", e);
		} finally {
			ftpUtil.closeConnect();
		}
		
		return result;
	}
	
}
