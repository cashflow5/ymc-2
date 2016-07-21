/**
 * 
 */
package com.belle.yitiansystem.merchant.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.belle.infrastructure.util.FtpUtils;

/**
 * ftp辅助类
 * 
 * @author huang.tao
 *
 */
@Component
public class FtpTools {
	
	@Value("${merchant.ftp.ip}")
	private String ftpIp = "10.10.80.181";
	
	@Value("${merchant.img.domain.name}")
	private String domainName = "http://img.yougou.com/pics/merchant/";
	
	/** FTP服务端口 */
	private static int PORT = 9999;
	/** FTP用户名 */
	private static String USERNAME = "merchant";
	/** FTP密码 */
	private static String PASSWORD = "ScU{{vPRc)X<";
	
	public FtpUtils getFtpUtilsIntance(String path) {
		FtpUtils ftpUtil = new FtpUtils();
		ftpUtil.connectServer(ftpIp, PORT, USERNAME, PASSWORD, "");
		ftpUtil.createDir(path);
		return ftpUtil;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
}
