package com.belle.yitiansystem.merchant.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.belle.infrastructure.util.UUIDGenerator;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTraining;
import com.belle.yitiansystem.merchant.model.pojo.MerchantTrainingFailLog;
/** 线程处理结果  */
public class ProcessResult {
	
	private MerchantTraining merchantTraining;
	private String ftpFilePath;
	private String localFilePath;//本地临时文件全路径
	private String type;
	private boolean isSuccess;//是否成功
	private String message;//异常信息
	private MerchantTrainingFailLog failLog;
	
	public ProcessResult(MerchantTraining merchantTraining, String ftpFilePath,
			String localTempPath, String type, boolean isSuccess, String message) {
		super();
		this.merchantTraining = merchantTraining;
		this.ftpFilePath = ftpFilePath;
		this.localFilePath = localTempPath;
		this.type = type;
		this.isSuccess = isSuccess;
		this.message = message;
		this.failLog = new MerchantTrainingFailLog();
	}
	
	public void writeToLogFile(){
		// TODO
	}

	public MerchantTraining getMerchantTraining() {
		return merchantTraining;
	}

	public void setMerchantTraining(MerchantTraining merchantTraining) {
		this.merchantTraining = merchantTraining;
	}

	public String getFtpFilePath() {
		return ftpFilePath;
	}

	public void setFtpFilePath(String ftpFilePath) {
		this.ftpFilePath = ftpFilePath;
	}

	public String getLocalTempPath() {
		return localFilePath;
	}

	public void setLocalTempPath(String localTempPath) {
		this.localFilePath = localTempPath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MerchantTrainingFailLog getFailLog() {
		if( !this.isSuccess ){
			failLog.setId(UUIDGenerator.getUUID());
			failLog.setTrainingId(merchantTraining.getId());
			failLog.setDesc( message.length()>500?message.substring(0,500):message );
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			failLog.setOperated(date);
			failLog.setOperator(merchantTraining.getOperator());
			failLog.setFilePath(localFilePath);
			return failLog;
		}else{
			return null;
		}
	}

		
}
