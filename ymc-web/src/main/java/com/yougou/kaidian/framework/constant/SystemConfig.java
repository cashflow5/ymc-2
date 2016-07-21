package com.yougou.kaidian.framework.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

   @Value("#{configProperties['email.setpassword.src']}")
   private String restPwdEmailSrc;
   @Value("#{configProperties['email.activate.src']}")
   private String emailActivateSrc;
   @Value("#{configProperties['email.updatepassword.src']}")
   private String updatePwdEmailSrc;
   
   /**
    * 每天每个手机发送短信的上限
    */
   @Value("#{configProperties['everyday.send.mobile.code.size']}")
   private int everydaySendMobileCodeSize = 5;
   /**
    * 每天每个账号发送短信的上限
    */
   @Value("#{configProperties['everyday.send.mobile.code.size.loginbean']}")
   private int everydaySendMobileCodeSizeOfLoginBean = 25;
   
   /**
    * 每个短信验证码的最大的输错次数，超过此最大次数，验证码过期失效
    */
   @Value("#{configProperties['mobile.code.error.size']}")
   private int mobileCodeErrorSize = 3;
   /**
    * 每个短信验证过期时长，单位分钟
    */
   @Value("#{configProperties['mobile.code.expire.time']}")
   private int mobileCodeExpireTime = 10;
   /**
    * 从前一步到下一步的相隔时间，默认10分钟，表示上一步验证身份通过之后到第二步，第二步可以10分钟之内通过链接直接访问，不需要再次身份验证
    * 超过这个时间，就要再次进行身份验证
    */
   @Value("#{configProperties['first.to.second.time']}")
   private int firstToSecondTime = 10;
   /**
    * 短信验证码的长度
    */
   @Value("#{configProperties['mobile.code.length']}")
   private int mobileCodeLength = 6;
   
	public String getRestPwdEmailSrc() {
		return restPwdEmailSrc;
	}
	public String getEmailActivateSrc() {
		return emailActivateSrc;
	}
	public String getUpdatePwdEmailSrc() {
		return updatePwdEmailSrc;
	}
	/**
    * 每天每个手机发送短信的上限
    */
	public int getEverydaySendMobileCodeSize() {
		return everydaySendMobileCodeSize;
	}
	/**
    * 每天每个账号发送短信的上限
    */
	public int getEverydaySendMobileCodeSizeOfLoginBean() {
		return everydaySendMobileCodeSizeOfLoginBean;
	}
	/**
    * 每个短信验证码的最大的输错次数，验证码过期失效
    */
	public int getMobileCodeErrorSize() {
		return mobileCodeErrorSize;
	}
	/**
    * 每个短信验证过期时长，单位分钟
    */
	public int getMobileCodeExpireTime() {
		return mobileCodeExpireTime;
	}
	/**
    * 从前一步到下一步的相隔时间，默认10分钟，表示上一步验证身份通过之后到第二步，第二步可以10分钟之内通过链接直接访问，不需要再次身份验证
    * 超过这个时间，就要再次进行身份验证
    */
	public int getFirstToSecondTime() {
		return firstToSecondTime;
	}
	/**
    * 短信验证码的长度
    */
	public int getMobileCodeLength() {
		return mobileCodeLength;
	}
	
}
