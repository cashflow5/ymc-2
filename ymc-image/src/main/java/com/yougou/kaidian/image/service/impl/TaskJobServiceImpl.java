package com.yougou.kaidian.image.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.yougou.kaidian.image.service.ITaskJobService;

@Service
public class TaskJobServiceImpl implements ITaskJobService {

	private Logger logger = LoggerFactory.getLogger(TaskJobServiceImpl.class);

	/**
	 * 清理目录中过期的文件
	 * 
	 * @param dayCount
	 *            ：保存时间天数
	 * @param dirPath
	 *            ：目录路径
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteFiles(String dirPath, String backupPath, int days,
			boolean isBackup) {

		// 计算备份日期，备份该日期之前的文件
		Date pointDate = new Date();
		long timeInterval = pointDate.getTime()
				- convertDaysToMilliseconds(days);
		pointDate.setTime(timeInterval);

		// 是否进行备份
		if (isBackup) {
			if (!backUpFiles(dirPath, backupPath, pointDate)) {
				logger.info(" Backup failed: " + dirPath);
				return false;
			}
		}

		// 设置文件过滤条件
		IOFileFilter timeFileFilter = FileFilterUtils.ageFileFilter(pointDate,
				true);
		IOFileFilter fileFiles = FileFilterUtils.andFileFilter(
				FileFileFilter.FILE, timeFileFilter);

		// 删除符合条件的文件
		File deleteRootFolder = new File(dirPath);
		Iterator<File> itFile = FileUtils.iterateFiles(deleteRootFolder,
				fileFiles, TrueFileFilter.INSTANCE);
		while (itFile.hasNext()) {
			File file = itFile.next();
			boolean result = file.delete();
			if (!result) {
				logger.error("Failed to delete file of :" + file);
				return false;
			}
		}

		// 清理空的文件夹
		File[] forderList = deleteRootFolder.listFiles();
		if (forderList != null && forderList.length > 0) {
			for (int i = 0; i < forderList.length; i++) {
				deleteEmptyDir(forderList[i]);
			}
		}

		return true;
	}

	/**
	 * 备份删除文件到指定的目录 ，目录格式：yyyy_MM_dd_bak
	 * 
	 * @param srcDir
	 *            ：源文件路径
	 * @param destDir
	 *            ：目标文件路径
	 * @param dayCount
	 *            ：时间间隔，备份该时间之前的数据
	 * @return
	 */
	private boolean backUpFiles(String srcDir, String destDir, Date pointDate) {
		try {
			// 设置备份文件夹格式YYYY_MM_dd_bak
			SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
			String folderName = format.format(new Date()) + "_bak";

			File resFile = new File(srcDir);
			File distFile = new File(destDir + File.separator + folderName);

			// 文件过滤条件
			IOFileFilter timeFileFilter = FileFilterUtils.ageFileFilter(
					pointDate, true);
			IOFileFilter fileFiles = FileFilterUtils.andFileFilter(
					FileFileFilter.FILE, timeFileFilter);

			// 复制文件目录
			FileFilter filter = FileFilterUtils.orFileFilter(
					DirectoryFileFilter.DIRECTORY, fileFiles);
			FileUtils.copyDirectory(resFile, distFile, filter, true);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Failed to backupFile:" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 天与毫秒的转换
	 * 
	 * @param days
	 * @return
	 */
	private long convertDaysToMilliseconds(int days) {
		return days * 24L * 3600 * 1000;
	}

	/**
	 * 循环删除空的文件夹
	 * 
	 * @param dir
	 */
	private void deleteEmptyDir(File dir) {
		if (dir.isDirectory()) {
			File[] fs = dir.listFiles();
			if (fs != null && fs.length > 0) {
				for (int i = 0; i < fs.length; i++) {
					File tmpFile = fs[i];
					if (tmpFile.isDirectory()) {
						deleteEmptyDir(tmpFile);
					}
					if (tmpFile.isDirectory()
							&& tmpFile.listFiles().length <= 0) {
						tmpFile.delete();
					}
				}
			}
			if (dir.isDirectory() && dir.listFiles().length == 0) {
				dir.delete();
			}
		}
	}
}
