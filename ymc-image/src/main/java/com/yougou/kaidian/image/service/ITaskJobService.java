package com.yougou.kaidian.image.service;

public interface ITaskJobService {

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
}
