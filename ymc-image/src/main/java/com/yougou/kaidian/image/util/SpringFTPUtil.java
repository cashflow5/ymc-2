package com.yougou.kaidian.image.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

/**
 * spring ftp工具类
 * 
 * @author li.m
 *
 */

public class SpringFTPUtil {

	private final static Logger logger = LoggerFactory.getLogger(SpringFTPUtil.class);
	
	/**
	 * 图片上传封装类,上传图片名称使用imgfile名称
	 * @param ftpChannel
	 * @param imgfile
	 * @param ftpServerPath
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean ftpUpload(MessageChannel ftpChannel,File imgfile, String ftpServerPath) throws UnsupportedEncodingException{
		if (StringUtils.isNotBlank(ftpServerPath)
				&& ftpServerPath.startsWith("/")) {
			ftpServerPath = StringUtils.substringAfter(ftpServerPath, "/");
		}
		Message<File> message = MessageBuilder.withPayload(imgfile)
				.setHeader("remote_dir", new String(ftpServerPath.getBytes(Charset.forName("UTF-8")),"ISO-8859-1"))
				.setHeader("remote_filename", new String(imgfile.getName().getBytes(Charset.forName("UTF-8")),"ISO-8859-1")).build();
		return ftpChannel.send(message);
	}
	
	/**
	 * 图片上传封装类
	 * @param uploadFileMap
	 * @param ftpServerPath
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String,File> ftpUpload(MessageChannel ftpChannel,Map<String,File> uploadFileMap,String ftpServerPath) throws UnsupportedEncodingException{
		if (StringUtils.isNotBlank(ftpServerPath) && ftpServerPath.startsWith("/")) 
			ftpServerPath = StringUtils.substringAfter(ftpServerPath, "/");
		Message<File> message=null;
		Map<String,File> failFileMap=new HashMap<String,File>();
		boolean result=true;
		for(String uploadFileStr:uploadFileMap.keySet()){
			message = MessageBuilder.withPayload(uploadFileMap.get(uploadFileStr)).setHeader("remote_dir", ftpServerPath).setHeader("remote_filename", new String(uploadFileStr.getBytes("UTF-8"),"ISO-8859-1")).build();
			result=ftpChannel.send(message);
			if(!result){
				failFileMap.put(uploadFileStr, uploadFileMap.get(uploadFileStr));
			}
		}
		return failFileMap;
	}
	
	/**
	 * 图片上传封装类,上传图片名称使用ftpfilename名称(重命名)
	 * @param ftpChannel
	 * @param imgfile
	 * @param ftpfilename
	 * @param ftpServerPath
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean ftpUpload(MessageChannel ftpChannel,File imgfile,String ftpfilename, String ftpServerPath) throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(ftpServerPath)
				&& ftpServerPath.startsWith("/")) {
			ftpServerPath = StringUtils.substringAfter(ftpServerPath, "/");
		}
		Message<File> message = MessageBuilder.withPayload(imgfile)
				.setHeader("remote_dir", new String(ftpServerPath.getBytes(Charset.forName("UTF-8")),"ISO-8859-1"))
				.setHeader("remote_filename", new String(ftpfilename.getBytes(Charset.forName("UTF-8")),"ISO-8859-1")).build();
		return ftpChannel.send(message);
	}
	
	/**
	 * 图片上传封装类,上传图片名称使用ftpfilename名称(重命名)
	 * @param ftpChannel
	 * @param fileInputStream
	 * @param ftpfilename
	 * @param ftpServerPath
	 * @return
	 * @throws Exception
	 */
	public static boolean ftpUpload(MessageChannel ftpChannel,InputStream fileInputStream,String ftpfilename, String ftpServerPath) throws Exception {
		if (StringUtils.isNotBlank(ftpServerPath)
				&& ftpServerPath.startsWith("/")) {
			ftpServerPath = StringUtils.substringAfter(ftpServerPath, "/");
		}
		Message<byte[]> message=null;
		try {
			message = MessageBuilder.withPayload(IOUtils.toByteArray(fileInputStream))
					.setHeader("remote_dir", new String(ftpServerPath.getBytes(Charset.forName("UTF-8")),"ISO-8859-1"))
					.setHeader("remote_filename", new String(ftpfilename.getBytes(Charset.forName("UTF-8")),"ISO-8859-1")).build();
		} catch (IOException e) {
			logger.error("ftp上传图片产生异常",e);
			throw new Exception(e);
		} finally{
			IOUtils.closeQuietly(fileInputStream);
		}
		return ftpChannel.send(message);
	}
}
