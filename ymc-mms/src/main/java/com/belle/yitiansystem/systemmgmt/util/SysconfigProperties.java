/**
 * 
 */
package com.belle.yitiansystem.systemmgmt.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * sysconfig.properties配置文件
 * 
 * @author huang.tao
 * 
 */
@Component
public class SysconfigProperties {

	@Value("${properPath}")
	private String properPath;

	@Value("${redisIp}")
	private String redisIp;

	@Value("${redisPort}")
	private String redisPort;

	@Value("${oms.host}")
	private String omshost;

	@Value("${bms.host}")
	private String bmshost;

	@Value("${cms.host}")
	private String cmshost;

	@Value("${mms.host}")
	private String mmshost;
	
	@Value("${fms.host}")
	private String fmshost;
	
	@Value("${dms.host}")
	private String dmshost;
	
	@Value("${work.order.host}")
	private String workorderhost;
	
	@Value("${wms.host}")
	private String wmshost;
	
	@Value("${outside.host}")
	private String outsidehost;
	
	@Value("${commodity.host}")
	private String commodityhost;
	
	@Value("${job.host}")
	private String jobhost;

	
	//======================VPN=================================
	@Value("${vpn.host}")
	private String vpnhost;
	
	@Value("${oms.host.vpn}")
	private String omshostvpn;

	@Value("${bms.host.vpn}")
	private String bmshostvpn;

	@Value("${cms.host.vpn}")
	private String cmshostvpn;

	@Value("${mms.host.vpn}")
	private String mmshostvpn;
	
	@Value("${fms.host.vpn}")
	private String fmshostvpn;
	
	@Value("${dms.host.vpn}")
	private String dmshostvpn;
	
	@Value("${work.order.host.vpn}")
	private String workorderhostvpn;
	
	@Value("${wms.host.vpn}")
	private String wmshostvpn;
	
	@Value("${outside.host.vpn}")
	private String outsidehostvpn;
	
	@Value("${commodity.host.vpn}")
	private String commodityhostvpn;
	
	//图片服务参数
    @Value("${image.ftp.accessory.abnormal.order}")
    public String imageFtpAccessoryAbnormal;
    @Value("${image.accessory.preview.domain}")
    public String imageAccessoryPreviewDomain;
    
    @Value("${kaidian.host}")
    private String kaidianIp; 
    
    @Value("${kaidian.port}")
    private String kaidianPort;
    
    //=========================ftp==============================
  	@Value("${training.ftp.path}")
	private String trainingFtpPath;
    @Value("${image.ftp.server}")
    private String ftpServerIp;   // 待删除,已不用
    @Value("${image.ftp.port}")
    private String ftpPort;   // 待删除,已不用
    @Value("${training.ftp.path.read}")
    private String trainingFtpPathRead;  
    @Value("${springMVC.file.path.temp}")
    private String springMVCFilePathTemp;
    @Value("${relative.pic.path}")
    private String relativePicPath;
    @Value("${relative.vedio.path}")
    private String relativeVedioPath;
    @Value("${relative.doc.path}")
    private String relativeDocPath;
    
    
  //合同附件FTP
    @Value("${contract.ftp.server}")
    private String contractFtpServer;
    
    @Value("${contract.ftp.port}")
    private String contractFtpPort;
    
    @Value("${contract.ftp.username}")
    private String contractFtpUsername;
    
    @Value("${contract.ftp.password}")
    private String contractFtpPassword;
    
    @Value("${contract.ftp.path}")
    private String contractFtpPath;
    
    @Value("${contract.ftp.maxFileSize}")
    private String contractFtpMaxFileSize;
    
    @Value("${contract.ftp.path.read}")
    private String contractFtpPathRead;
    
    // 权限组IDs
    @Value("${BASE_ROLE_IDS}")
    private String baseRoleIds;
    
    // 自动清理商家草稿天数
    @Value("${auto.clean.days.for.draft}")
    private String autoCleanDaysForDraft;
    
    public String getBaseRoleIds() {
		return baseRoleIds;
	}

	public void setBaseRoleIds(String baseRoleIds) {
		this.baseRoleIds = baseRoleIds;
	}

	public  String[] getBaseRoleIdArray(){
		String[] result = null;
		if( StringUtils.isNotBlank(this.baseRoleIds) ){
			result = this.baseRoleIds.trim().split(",");
		}
		return result;
	}
	
	public String getContractFtpPathRead() {
		return contractFtpPathRead;
	}

	public void setContractFtpPathRead(String contractFtpPathRead) {
		this.contractFtpPathRead = contractFtpPathRead;
	}

	public String getImageFtpAccessoryAbnormal() {
        return imageFtpAccessoryAbnormal;
    }

    public void setImageFtpAccessoryAbnormal(String imageFtpAccessoryAbnormal) {
        this.imageFtpAccessoryAbnormal = imageFtpAccessoryAbnormal;
    }

    public String getImageAccessoryPreviewDomain() {
        return imageAccessoryPreviewDomain;
    }

    public void setImageAccessoryPreviewDomain(String imageAccessoryPreviewDomain) {
        this.imageAccessoryPreviewDomain = imageAccessoryPreviewDomain;
    }

    public String getProperPath() {
		return properPath;
	}

	public void setProperPath(String properPath) {
		this.properPath = properPath;
	}

	public String getRedisIp() {
		return redisIp;
	}

	public void setRedisIp(String redisIp) {
		this.redisIp = redisIp;
	}

	public String getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(String redisPort) {
		this.redisPort = redisPort;
	}

	public String getOmshost() {
		return omshost;
	}

	public void setOmshost(String omshost) {
		this.omshost = omshost;
	}

	public String getBmshost() {
		return bmshost;
	}

	public void setBmshost(String bmshost) {
		this.bmshost = bmshost;
	}

	public String getCmshost() {
		return cmshost;
	}

	public void setCmshost(String cmshost) {
		this.cmshost = cmshost;
	}

	public String getMmshost() {
		return mmshost;
	}

	public void setMmshost(String mmshost) {
		this.mmshost = mmshost;
	}

	public String getFmshost() {
		return fmshost;
	}

	public void setFmshost(String fmshost) {
		this.fmshost = fmshost;
	}

	public String getDmshost() {
		return dmshost;
	}

	public void setDmshost(String dmshost) {
		this.dmshost = dmshost;
	}

	public String getWmshost() {
		return wmshost;
	}

	public void setWmshost(String wmshost) {
		this.wmshost = wmshost;
	}

	public String getOutsidehost() {
		return outsidehost;
	}

	public void setOutsidehost(String outsidehost) {
		this.outsidehost = outsidehost;
	}

	public String getCommodityhost() {
		return commodityhost;
	}

	public void setCommodityhost(String commodityhost) {
		this.commodityhost = commodityhost;
	}

	public String getJobhost() {
		return jobhost;
	}

	public void setJobhost(String jobhost) {
		this.jobhost = jobhost;
	}

	public String getVpnhost() {
		return vpnhost;
	}

	public void setVpnhost(String vpnhost) {
		this.vpnhost = vpnhost;
	}

	public String getOmshostvpn() {
		return omshostvpn;
	}

	public void setOmshostvpn(String omshostvpn) {
		this.omshostvpn = omshostvpn;
	}

	public String getBmshostvpn() {
		return bmshostvpn;
	}

	public void setBmshostvpn(String bmshostvpn) {
		this.bmshostvpn = bmshostvpn;
	}

	public String getCmshostvpn() {
		return cmshostvpn;
	}

	public void setCmshostvpn(String cmshostvpn) {
		this.cmshostvpn = cmshostvpn;
	}

	public String getMmshostvpn() {
		return mmshostvpn;
	}

	public void setMmshostvpn(String mmshostvpn) {
		this.mmshostvpn = mmshostvpn;
	}

	public String getFmshostvpn() {
		return fmshostvpn;
	}

	public void setFmshostvpn(String fmshostvpn) {
		this.fmshostvpn = fmshostvpn;
	}

	public String getDmshostvpn() {
		return dmshostvpn;
	}

	public void setDmshostvpn(String dmshostvpn) {
		this.dmshostvpn = dmshostvpn;
	}

	public String getWmshostvpn() {
		return wmshostvpn;
	}

	public void setWmshostvpn(String wmshostvpn) {
		this.wmshostvpn = wmshostvpn;
	}

	public String getOutsidehostvpn() {
		return outsidehostvpn;
	}

	public void setOutsidehostvpn(String outsidehostvpn) {
		this.outsidehostvpn = outsidehostvpn;
	}

	public String getCommodityhostvpn() {
		return commodityhostvpn;
	}

	public void setCommodityhostvpn(String commodityhostvpn) {
		this.commodityhostvpn = commodityhostvpn;
	}

	public String getKaidianIp() {
		return kaidianIp;
	}

	public void setKaidianIp(String kaidianIp) {
		this.kaidianIp = kaidianIp;
	}

	public String getKaidianPort() {
		return kaidianPort;
	}

	public void setKaidianPort(String kaidianPort) {
		this.kaidianPort = kaidianPort;
	}

	public String getTrainingFtpPath() {
		return trainingFtpPath;
	}

	public void setTrainingFtpPath(String trainingFtpPath) {
		this.trainingFtpPath = trainingFtpPath;
	}

	public String getFtpServerIp() {
		return ftpServerIp;
	}

	public void setFtpServerIp(String ftpServerIp) {
		this.ftpServerIp = ftpServerIp;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getContractFtpServer() {
		return contractFtpServer;
	}

	public void setContractFtpServer(String contractFtpServer) {
		this.contractFtpServer = contractFtpServer;
	}

	public String getContractFtpPort() {
		return contractFtpPort;
	}

	public void setContractFtpPort(String contractFtpPort) {
		this.contractFtpPort = contractFtpPort;
	}

	public String getContractFtpUsername() {
		return contractFtpUsername;
	}

	public void setContractFtpUsername(String contractFtpUsername) {
		this.contractFtpUsername = contractFtpUsername;
	}

	public String getContractFtpPassword() {
		return contractFtpPassword;
	}

	public void setContractFtpPassword(String contractFtpPassword) {
		this.contractFtpPassword = contractFtpPassword;
	}

	public String getContractFtpPath() {
		return contractFtpPath;
	}

	public void setContractFtpPath(String contractFtpPath) {
		this.contractFtpPath = contractFtpPath;
	}

	public String getTrainingFtpPathRead() {
		return trainingFtpPathRead;
	}

	public void setTrainingFtpPathRead(String trainingFtpPathRead) {
		this.trainingFtpPathRead = trainingFtpPathRead;
	}

	public String getSpringMVCFilePathTemp() {
		return springMVCFilePathTemp;
	}

	public void setSpringMVCFilePathTemp(String springMVCFilePathTemp) {
		this.springMVCFilePathTemp = springMVCFilePathTemp;
	}

	public String getRelativePicPath() {
		return relativePicPath;
	}

	public void setRelativePicPath(String relativePicPath) {
		this.relativePicPath = relativePicPath;
	}

	public String getRelativeVedioPath() {
		return relativeVedioPath;
	}

	public void setRelativeVedioPath(String relativeVedioPath) {
		this.relativeVedioPath = relativeVedioPath;
	}

	public String getRelativeDocPath() {
		return relativeDocPath;
	}

	public void setRelativeDocPath(String relativeDocPath) {
		this.relativeDocPath = relativeDocPath;
	}

	public String getContractFtpMaxFileSize() {
		//没有配置
		if("${contract.ftp.maxFileSize}".equals(contractFtpMaxFileSize)){
			return "";
		}
		return contractFtpMaxFileSize;
	}

	public void setContractFtpMaxFileSize(String contractFtpMaxFileSize) {
		this.contractFtpMaxFileSize = contractFtpMaxFileSize;
	}

	public String getAutoCleanDaysForDraft() {
		return autoCleanDaysForDraft;
	}

	public void setAutoCleanDaysForDraft(String autoCleanDaysForDraft) {
		this.autoCleanDaysForDraft = autoCleanDaysForDraft;
	}

	public String getWorkorderhost() {
		return workorderhost;
	}

	public void setWorkorderhost(String workorderhost) {
		this.workorderhost = workorderhost;
	}

	public String getWorkorderhostvpn() {
		return workorderhostvpn;
	}

	public void setWorkorderhostvpn(String workorderhostvpn) {
		this.workorderhostvpn = workorderhostvpn;
	}
	
}
