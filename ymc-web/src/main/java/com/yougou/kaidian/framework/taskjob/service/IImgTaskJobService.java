package com.yougou.kaidian.framework.taskjob.service;

import javax.mail.MessagingException;

public interface IImgTaskJobService {

	/**
	 * 清理目录中过期的文件
	 * 
	 * @param dayCount
	 *            ：保存时间天数
	 * @param dirPath
	 *            ：目录路径
	 * @return
	 */
	public boolean deleteFiles(String dirPath, String backupPath, int days,boolean isBackup);
	
	public int getNotMerchantOrderCount();
	
	public void ImgJmsCount(String ip) throws MessagingException;
}
