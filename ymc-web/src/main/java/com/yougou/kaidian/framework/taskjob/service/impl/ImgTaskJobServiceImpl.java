package com.yougou.kaidian.framework.taskjob.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.commodity.dao.MctImageJmsMapper;
import com.yougou.kaidian.commodity.model.pojo.ImgJmsCountBean;
import com.yougou.kaidian.framework.taskjob.service.IImgTaskJobService;
import com.yougou.kaidian.framework.util.LocalConfigBean;
import com.yougou.kaidian.order.dao.MerchantOrderMapper;

@Service
public class ImgTaskJobServiceImpl implements IImgTaskJobService {

	private Logger LogEx = LoggerFactory.getLogger(ImgTaskJobServiceImpl.class);
	@Resource
	private MerchantOrderMapper merchantOrderMapper;
	@Resource
	private MctImageJmsMapper mctImageJmsMapper;
	@Resource
	private JavaMailSender mailSender;
	@Resource
	private LocalConfigBean localConfig;

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
				LogEx.info(" Backup failed: " + dirPath);
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
				LogEx.error("Failed to delete file of :" + file);
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
			LogEx.error("Failed to backupFile:" + e.getMessage());
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

	
	public int getNotMerchantOrderCount(){
		return merchantOrderMapper.queryNotMerchantOrderCount();
	}

	@Override
	public void ImgJmsCount(String ip) throws MessagingException {
		Calendar calendar = Calendar.getInstance();    
		DateTime dateTime = new DateTime(calendar); 
		dateTime=dateTime.minusDays(1);
		List<ImgJmsCountBean> imgJmsCountList=mctImageJmsMapper.countImgJms(dateTime.toString("yyyy-MM-dd 00:00:00"), dateTime.toString("yyyy-MM-dd 23:59:59"));
		String title=dateTime.toString("yyyy-MM-dd")+" jms图片处理统计,请求ip:"+ip;
		int sum_t=0,sum_l=0,sum_other=0,sum=0;
		if(imgJmsCountList!=null&&imgJmsCountList.size()>0){
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'></head><body><h1>商家中心商品异步处理jms统计</h1><div><table border='1px' style='border-collapse:collapse;border:1px solid;'><tbody>");
			for(ImgJmsCountBean imgJmsCountBean:imgJmsCountList){
				stringBuilder.append("<tr>");
				if(imgJmsCountBean.getStatus1Count()!=imgJmsCountBean.getCount()){
					stringBuilder.append("<td><span style='color:red;font-weight:bold'>商家:"+imgJmsCountBean.getMerchantName()).append("(").append(imgJmsCountBean.getMerchantCode()).append(")</span></td>");
				}else{
					stringBuilder.append("<td>商家:"+imgJmsCountBean.getMerchantName()).append("(").append(imgJmsCountBean.getMerchantCode()).append(")</td>");
				}
				stringBuilder.append("<td>图片类型:"+imgJmsCountBean.getPicType()).append("</td>");
				stringBuilder.append("<td>已处理数量:"+imgJmsCountBean.getStatus1Count()).append("</td>");
				stringBuilder.append("<td>未处理数量:"+imgJmsCountBean.getStatus0Count()).append("</td>");
				stringBuilder.append("<td>总数:"+imgJmsCountBean.getCount()).append("</td>");
				stringBuilder.append("</tr>");
				if("l".equalsIgnoreCase(imgJmsCountBean.getPicType())){
					sum_l=sum_l+imgJmsCountBean.getCount();
				}else if("t".equalsIgnoreCase(imgJmsCountBean.getPicType())){
					sum_t=sum_t+imgJmsCountBean.getCount();
				}else{
					sum_other=sum_other+imgJmsCountBean.getCount();
				}
				sum=sum+imgJmsCountBean.getCount();
			}
			stringBuilder.append("</tbody></table></div></body></html>");
			stringBuilder.append("\r\n");
			stringBuilder.append("总数量(全):"+sum).append("  [(l类型)总数:"+sum_l).append("----(t类型)总数:"+sum_t).append("----(其他类型)总数:"+sum_other).append("]");;
			this.sandMail(stringBuilder.toString(), title);
		}else{
			this.sandMail("今天风平浪静,没有数据统计", title);
		}
	}
	
	/**
	 * 邮件发送处理图片失败信息
	 * @param message
	 * @throws MessagingException 
	 */
	public void sandMail(String message,String title) throws MessagingException{
		MimeMessage mailMessage = mailSender.createMimeMessage(); 
		MimeMessageHelper mail = new MimeMessageHelper(mailMessage,true,"GBK"); 
		LogEx.info("切图不成功发送邮件.");
		try {
			if(localConfig.getReceiveMailAddress()!=null&&localConfig.getReceiveMailAddress().length>0){
				mail.setTo(localConfig.getReceiveMailAddress());// 接受者
				mail.setFrom("yougou_kaidian@163.com");// 发送者,这里还可以另起Email别名，不用和xml里的username一致
				mail.setSubject(title);// 主题
				mail.setText(message,true);// 邮件内容
				mailSender.send(mailMessage);
			}else{
				LogEx.error("邮件发送列表为空!");
			}
		} catch (Exception e) {
			LogEx.error("邮件发送失败:",e);
		}
	}
}
