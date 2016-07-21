package com.yougou.kaidian.user.web;  

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.component.email.model.SubjectIdType;
import com.yougou.component.sms.api.ISmsApi;
import com.yougou.component.sms.constant.ModelType;
import com.yougou.component.sms.model.SmsVo;
import com.yougou.kaidian.commodity.util.CommodityUtilNew;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.constant.SystemConfig;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.order.util.GetAddressByIpUtil;
import com.yougou.kaidian.user.model.pojo.MerchantCenterTrustIp;
import com.yougou.kaidian.user.model.pojo.Message;
import com.yougou.kaidian.user.service.IMerchantCenterTrustIpService;
import com.yougou.kaidian.user.service.IMerchantUsers;
import com.yougou.kaidian.user.service.IMessageService;
import com.yougou.tools.common.utils.CommonUtil;
import com.yougou.tools.common.utils.DateUtil;

@Controller
@RequestMapping("merchants/security")
public class MerchantSecurityController {
	private Logger logger = LoggerFactory.getLogger(MerchantSecurityController.class);
	@Resource
	private IMerchantUsers merchantUsers;
	@Resource
	private IMerchantCenterTrustIpService merchantCenterTrustIpService;
	@Resource
	private ISmsApi smsApi;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private SystemConfig systemConfig;
	@Resource
	private IMessageService messageService;
	
	/**
	 * checkMobilephoneInMasterAccount:检查当前登录账号是否主账号，是否绑定手机号码 
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/checkMobilephone")
	public String checkMobilephoneInMasterAccount (){
		JSONObject jsObject = new JSONObject();
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		Map<String,Object> map = merchantUsers.getMobilePhoneOfLoginName(loginName);
		//1 判断当前账号是否主账号
		//2 主账号与子账号不一样对待
		//根据商家编码、登录账号查询手机是否绑定
		if(StringUtils.isNotBlank(MapUtils.getString(map, "mobile_code"))){
			//已绑定
			jsObject.put("result", 1);
		}else{
			//未绑定
			jsObject.put("result", 0);
			if(NumberUtils.toInt(String.valueOf(MapUtils.getInteger(map, "is_administrator")))==1){
				//主账号
				jsObject.put("master", 1);
			}else{
				//子账号
				jsObject.put("master", 0);
			}
		}
		return jsObject.toString();
	}
	
	/**
	 * checkMobilephone:检查当前登录账号是否绑定手机号码 
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6
	 */
	private boolean checkMobilephone(){
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		//根据商家编码、登录账号查询手机是否绑定
		Map<String,Object> map = merchantUsers.getMobilePhoneOfLoginName(loginName);
		if(StringUtils.isNotBlank(MapUtils.getString(map, "mobile_code"))){
			return true;
		}
		return false;
	}
	
	/**
	 * bandingMobile:绑定手机页面
	 * @author li.n1 
	 * @param map
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/bandingMobile")
	public String bandingMobile(ModelMap map){
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		//防止已绑定手机号码通过url直接访问
		if(checkMobilephone()){
			//已绑定，跳转到首页
			return "redirect:/merchants/login/to_index.sc";
		}else{
			//未绑定
			map.put("loginName", loginName);
			return "manage/security/mobile_banding";
		}
	}
	
	/**
	 * getMobileVerificateCode:获取短信验证码 
	 * @author li.n1 
	 * @param mobileCode
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/getMobileCode")
	public String getMobileVerificateCode(String mobileCode){
		//商家编码
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		//判断是修改手机号码还是绑定手机号码，如果mobile不为空，则是修改手机，否则为绑定手机
		//mobileCode包含有*好，则认为是修改绑定手机
		if(mobileCode.contains("*")){
			mobileCode = MapUtils.getString(merchantUsers.getMobilePhoneOfLoginName(loginName),"mobile_code");
		}
		//后台再次验证手机号码
		if(com.yougou.kaidian.common.util.CommonUtil.isMobile(mobileCode)){
			//每天每个手机号码最多发送短信5次
			int phoneCount = NumberUtils.toInt(
					String.valueOf(redisTemplate.opsForValue()
					.get(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+mobileCode)));
    		if(phoneCount>=systemConfig.getEverydaySendMobileCodeSize()){
    			return "-1";
    		}else{
    			redisTemplate.opsForValue().set(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+mobileCode, phoneCount+1);
    			//设置生存期为当天23:59:59
    			redisTemplate.expireAt(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+mobileCode, 
    					DateUtil.parseDate(DateUtil.format(new Date(), "yyyy-MM-dd")+" 23:59:59", 
    							DateUtil.LONG_DATE_TIME_PATTERN));
    		}
    		//每天每个账号最多发送短信25次
    		int userNameCount = NumberUtils.toInt(
    				String.valueOf(redisTemplate.opsForValue()
    						.get(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName)));
    		if(userNameCount>=systemConfig.getEverydaySendMobileCodeSizeOfLoginBean()){
    			return "-2";
    		}else{
    			redisTemplate.opsForValue().set(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName,userNameCount+1);
    			//设置生存期为当天23:59:59
    			redisTemplate.expireAt(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName, 
    					DateUtil.parseDate(DateUtil.format(new Date(), "yyyy-MM-dd")+" 23:59:59", 
    							DateUtil.LONG_DATE_TIME_PATTERN));
    		}
			String code = CommonUtil.buildNumber(systemConfig.getMobileCodeLength());
			//先删除
			redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode +":"+loginName);
			//验证码加入redisTemplate
    		redisTemplate.opsForValue().set(
    				CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode +":"+loginName, code);
    		//验证码设置过期时间10分钟
    		redisTemplate.expire(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode+":"+loginName, 
    				systemConfig.getMobileCodeExpireTime(), TimeUnit.MINUTES);
			SmsVo smsVo = new SmsVo();
			smsVo.setPhone(new String[] { mobileCode });
			smsVo.setContent("验证码为"+code+"（"+systemConfig.getMobileCodeExpireTime()+"分钟内有效）。工作人员不会向您索要，请勿向任何人泄露！【优购时尚商城】");
			smsVo.setModelType(ModelType.MODEL_TYPE_MERCHANT);
			smsApi.sendNowByYouGou(smsVo);
			logger.warn("商家编码{}，登录账号：{}，手机号码前6位{}成功发送短信！！",
					//取前6位，信息敏感性
					new Object[]{merchantCode,loginName,mobileCode.substring(0, 6)});
			//短信记录
			Message message = new Message();
			message.setId(UUIDGenerator.getUUID());
			message.setOperated(new Date());
			message.setLoginName(loginName);
			message.setTo(mobileCode);
			//信息的类型   0短信  1邮件
			message.setType("0");
			message.setContent(smsVo.getContent());
			message.setComment("获取短信验证码");
			messageService.saveMessage(message);
			return "1";
		}else{
			logger.warn("商家编码{}，登录账号：{}，手机号码前6位{}发送短信失败，手机号码格式不正确！！",
					new Object[]{merchantCode,loginName,mobileCode.substring(0, 6)});
			return "0";
		}
	}
	
	
	/**
	 * bandingMobile:绑定手机处理 
	 * @author li.n1 
	 * @param mobileCode
	 * @param verifyCode
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping(value="/bandingMobile",method=RequestMethod.POST)
	public String bandingMobile(String mobileCode,String verifyCode,HttpServletRequest request){
		//商家编码
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		String code = (String)redisTemplate.opsForValue().get(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode +":"+loginName);
		if(StringUtils.isNotBlank(code) && 
				StringUtils.isNotBlank(verifyCode)){
			if(code.equals(verifyCode)){
//				1 先判断该手机号是否已绑定其他账号，如已绑定，则此处不能绑定；未绑定才可以绑定
//				if(merchantUsers.exitsTelePhoneOfMerchantUsers(mobileCode,null)){
					if(checkMobilephone()){
						//修改绑定手机
						//判断是否通过身份验证
						//防止直接通过url访问，必须先经过身份验证才能访问该页面
						Integer enterFlag = (Integer)redisTemplate.opsForValue()
								.get(CacheConstant.C_USER_ENTER_METHOD+":updatemobile"+":"+loginName);
						if(enterFlag!=null && enterFlag==1){
							//2 绑定登录账号
							if(merchantUsers.updateMobilePhone(loginName,mobileCode)){
								redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode +":"+loginName);
								logger.warn("商家{}，账号{}，绑定手机号码成功！",new Object[]{merchantCode,loginName});
								return "1";
							}
							logger.error("商家{}，账号{}，绑定手机号码失败，绑定时数据库发生错误！",new Object[]{merchantCode,loginName});
							return "-2";
						}else{
							//已绑定手机，未经过身份验证
							logger.warn("商家{}，账号{}，未经过身份验证强行进入修改绑定手机！",new Object[]{merchantCode,loginName});
							return "-3";
						}
					}else{
						//2 绑定登录账号
						boolean b = merchantUsers.updateMobilePhone(loginName,mobileCode);
						if(b){
							redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode +":"+loginName);
							logger.warn("商家{}，账号{}，绑定手机号码成功！",new Object[]{merchantCode,loginName});
							//把当前登录ip记录在白名单，防止绑定手机号之后重新登录再次出现身份验证的情况
							MerchantCenterTrustIp trustIp = new MerchantCenterTrustIp();
							trustIp.setId(UUIDGenerator.getUUID());
							trustIp.setLoginIp(GetAddressByIpUtil.getIPAddress(request));
							trustIp.setCreateDate(new Date());
							trustIp.setLoginName(YmcThreadLocalHolder.getOperater());
							trustIp.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
							merchantCenterTrustIpService.insert(trustIp);
							logger.warn("商家{}，账号{}，ip{}记录在白名单！",new Object[]{merchantCode,loginName,trustIp.getLoginIp()});
							return "1";
						}
						logger.error("商家{}，账号{}，绑定手机号码失败，绑定时数据库发生错误！",new Object[]{merchantCode,loginName});
						return "-2";
					}
//				}else{
//					logger.warn("商家{}，账号{}，绑定手机号码{}失败，该手机号码已绑定其他账号！",new Object[]{merchantCode,loginName,mobileCode});
//					return "-4";
//				}
			}else{
				//记录输错次数
				int errorCount = NumberUtils.toInt(
						String.valueOf(redisTemplate.opsForValue()
								.get(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+code+":errorcount")));
				//单个验证码输入错误次数为3，把最近一个短信验证码作失效
				if(errorCount>=systemConfig.getMobileCodeErrorSize()-1){
					redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode +":"+loginName);
					redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+code+":errorcount");
				}else{
					redisTemplate.opsForValue().set(
							CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+code+":errorcount",
							errorCount+1);
					redisTemplate.expire(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+code+":errorcount", 
							systemConfig.getMobileCodeExpireTime(), TimeUnit.MINUTES);
				}
				logger.error("商家{}，账号{}，绑定手机号码失败，输入验证码不准确！",new Object[]{merchantCode,loginName});
				return "0";
			}
		}else{
			logger.error("商家{}，账号{}，绑定手机号码失败，验证码为空或已过期！",new Object[]{merchantCode,loginName});
			return "-1";
		}
	}
	
	/**
	 * accountSecurity:账号管理页面 
	 * @author li.n1 
	 * @param map
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/accountSecurity")
	public String accountSecurity(ModelMap map){
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		//安全等级，1低 2中 3高
		int grade = 1;
		try{
			//根据登录账号查询手机、邮箱
			Map<String, Object> loginUser = merchantUsers.merchantLoginByUserName(loginName);
			String email = (String)loginUser.get("email");
			String mobilePhone = (String)loginUser.get("mobile_code");
			//密码强度
			String strength = (String)loginUser.get("strength");
			map.put("emailstatus", (loginUser.get("email")==null||"".equals(loginUser.get("email")))
					?-1:(MapUtils.getInteger(loginUser, "emailstatus")==0?0:1));
			if(StringUtils.isNotBlank(mobilePhone)){
				map.put("mobilePhone", mobilePhone.substring(0, 3)+"******"+mobilePhone.substring(9));
			}
			//邮箱需要激活才算绑定
			Integer status = (Integer)map.get("emailstatus");
			if(status==1){
				email = com.yougou.kaidian.common.util.CommonUtil.hideEmailMiddleLetter(email);
				map.put("email", email);
			}
			map.put("loginName", loginName);
			if(map.containsKey("mobilePhone")){
				grade = grade + 1;
			}
			if(map.containsKey("email")){
				grade = grade + 1;
			}
			map.put("grade", grade);
			map.put("strength", strength);
		}catch(Exception e){
			logger.error("根据账号查询用户信息发生异常",e);
		}
		return "manage/security/account_manage";
	}
	
	/**
	 * updatePwdUI:修改密码页面
	 * @author li.n1 
	 * @param map
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/updatePwd")
	public String updatePwdUI(ModelMap map){
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		try{
			//根据登录账号查询手机、邮箱
			Map<String, Object> loginUser = merchantUsers.merchantLoginByUserName(loginName);
			//获取手机号
			String mobilePhone = (String)loginUser.get("mobile_code");
			if(StringUtils.isNotBlank(mobilePhone)){
				map.put("mobilePhone", mobilePhone.substring(0, 3)+"******"+mobilePhone.substring(9));
			}
			//获取邮箱账号
			String email = (String)loginUser.get("email");
			//邮箱需要激活才算绑定
			Integer status = (loginUser.get("email")==null||"".equals(loginUser.get("email")))
					?-1:(MapUtils.getInteger(loginUser, "emailstatus")==0?0:1);
			if(status==1){
				email = com.yougou.kaidian.common.util.CommonUtil.hideEmailMiddleLetter(email);
				map.put("email", email);
			}
		}catch(Exception e){
			logger.error("根据账号查询用户信息发生异常",e);
		}
		return "manage/security/update_password";
	}
	
	/**
	 * checkPicCode:验证图形验证码是否正确
	 * @author li.n1 
	 * @param code
	 * @param request
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCode",method = RequestMethod.POST)
	public String checkPicCode(String code, HttpServletRequest request){
		String imagevalidate = (String) request.getSession().getAttribute("login_validate_image");
		// 判断验证码是否正确
		if (!code.equalsIgnoreCase(imagevalidate)) {
			return "-1";
		}
		return "1";
	}
	
	/**
	 * sentUpdatePasswordEmail:发送修改密码邮件 
	 * @author li.n1 
	 * @param code
	 * @param request
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/sentupdatepwdemail")
	public String sentUpdatePasswordEmail(String code,HttpServletRequest request){
		//查询用户名
		Map<String, Object> user;
		JSONObject jsObject = new JSONObject();
		try {
			if("1".equals(checkPicCode(code,request))){
				user = merchantUsers.merchantLoginByUserName(YmcThreadLocalHolder.getOperater());
				//发送邮件
				String updatePasswordUrl=systemConfig.getUpdatePwdEmailSrc()+"?code="+MapUtils.getString(user, "id");
				//邮件内容
				StringBuffer strB=new StringBuffer();
				strB.append("<img src='http://s1.ygimg.cn/template/common/images/logo-yg.png'>");
				strB.append("<div>--------------------------------------------------------------------------------------------------</div>");
				strB.append("<div><strong>亲爱的优购商家： </strong></div>");
				strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 您通过邮件<font color='#ff0000'><strong>修改密码</strong></font>，请妥善保管。请点击以下链接修改您的密码!若无法点击，请将链接复制到浏览器打开。</div>");
				strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 提示：如果您不需要修改密码，或者您从未申请密码修改，请忽略本邮件!如你已登录系统，可直接访问该路径，否则需要登录后才能在浏览器打开。该链接只能在收到邮件后访问1次，超过1个小时或者访问超过1次，链接将自动失效。</div>");
				strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='"+updatePasswordUrl+"'>"+updatePasswordUrl+"</a></div>");
				merchantUsers.sendMail(MapUtils.getString(user, "email"),
						"优购-商家中心-修改密码",
						strB.toString(),
						SubjectIdType.SUBJECT_ID_MERCHANT_FINDPWD,
						com.yougou.component.email.model.ModelType.MODEL_TYPE_MERCHANT_FINDPWD,
						YmcThreadLocalHolder.getOperater());
				redisTemplate.delete(CacheConstant.C_USER_UPDATEPASSWORD_ID_TIME+":"+MapUtils.getString(user, "id"));
				redisTemplate.opsForValue().set(CacheConstant.C_USER_UPDATEPASSWORD_ID_TIME+":"+MapUtils.getString(user, "id"), System.currentTimeMillis());
				//1小时过期
				redisTemplate.expire(CacheConstant.C_USER_UPDATEPASSWORD_ID_TIME+":"+MapUtils.getString(user, "id"), 1, TimeUnit.HOURS);
				jsObject.put("success", true);
				String email = (String)user.get("email");
				email = com.yougou.kaidian.common.util.CommonUtil.hideEmailMiddleLetter(email);
				jsObject.put("message", "修改密码链接已发送到邮箱："+email+",请查收!");
				logger.warn("修改密码链接已发送到邮箱：{}!",email);
			}else{
				jsObject.put("success", false);
				jsObject.put("message","图形验证码不正确!");
				logger.warn("修改密码链接发送失败，图形验证码不正确!");
			}
		} catch (Exception e) {
			jsObject.put("success", false);
			jsObject.put("message","邮件发送失败，请稍后再试!");
			logger.error("根据账号查询用户信息发生异常",e);
		}
		return jsObject.toString();
	}
	
	/**
	 * sentupdatepwdsms:通过手机修改密码，校验图形淹验证码与短信验证码 
	 * @author li.n1 
	 * @param code
	 * @param verifyCode
	 * @param request
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/sentupdatepwdsms")
	public String sentupdatepwdsms(String code, String verifyCode, HttpServletRequest request){
		JSONObject jsObject = new JSONObject();
		try {
			if("1".equals(checkPicCode(code,request))){
				String result = updatePre(verifyCode,"mobile");
				if("1".equals(result)){
					jsObject.put("success", true);
					jsObject.put("message", "身份验证通过");
					logger.warn("验证码匹配正确，进入下一步!");
				}else if("0".equals(result)){
					jsObject.put("success", false);
					jsObject.put("message","验证码不正确，请输入正确的短信验证码！");
					logger.warn("验证码输入不正确!");
				}else{
					jsObject.put("success", false);
					jsObject.put("message","验证码已失效，请重新获取短信验证码！");
					logger.warn("验证码已失效！!");
				}
			}else{
				jsObject.put("success", false);
				jsObject.put("message","图形验证码不正确!");
				logger.warn("身份验证失败，图形验证码不正确!");
			}
		}catch(Exception e){
			jsObject.put("success", false);
			jsObject.put("message","身份验证发生错误，请稍后再试!");
			logger.error("根据账号查询用户信息发生异常",e);
		}
		return jsObject.toString();
	}
	
	/**
	 * updatepasswordUI:通过邮件链接修改密码页面
	 * @author li.n1 
	 * @param code
	 * @param map
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/updatepassword")
	public String updatepasswordUI(String code,ModelMap map){
		Long sandTime = (Long) this.redisTemplate.opsForValue().get(CacheConstant.C_USER_UPDATEPASSWORD_ID_TIME+":"+code);
		if(sandTime!=null){
			if((System.currentTimeMillis()-sandTime)>(1000*60*60)){
				map.put("isScuess", false);
				map.put("message", "修改密码链接超过期限，请重新发送!");
			}else{
				//已使用，删除该缓存
				redisTemplate.delete(CacheConstant.C_USER_UPDATEPASSWORD_ID_TIME+":"+code);
				return "manage/security/update_password_next";
			}
		}else{
			map.put("isScuess", false);
			map.put("message", "无效的密码修改请求，该链接可能已使用过，请重新操作!");	
		}
		return "manage/security/update_password_fail";
	}
	
	/**
	 * updatePassowordByMobile: 通过手机修改密码
	 * @author li.n1 
	 * @param map
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/updatepasswordByMobile")
	public String updatePassowordByMobile(ModelMap map){
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		//防止直接通过url访问，必须先经过身份验证才能访问该页面
		Integer enterFlag = (Integer)redisTemplate.opsForValue()
				.get(CacheConstant.C_USER_ENTER_METHOD+":updatemobile"+":"+loginName);
		if(enterFlag!=null && enterFlag==1){
			return "manage/security/update_password_next";
		}else{
			map.put("isScuess", false);
			map.put("message", "无效的密码修改请求，请验证身份后重新操作!");	
		}
		return "manage/security/update_password_fail";
	}
	
	/**
	 * updateMobile:修改手机页面 
	 * @author li.n1 
	 * @param map
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/updateMobile")
	public String updateMobile(ModelMap map){
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		String mobile = MapUtils.getString(merchantUsers.getMobilePhoneOfLoginName(loginName),"mobile_code");
		map.put("loginName", loginName);
		if(StringUtils.isNotBlank(mobile)){
			map.put("mobilePhone", mobile.substring(0, 3)+"******"+mobile.substring(9));
		}else{
			map.put("isScuess", false);
			map.put("message", "手机号码为空，请先绑定手机号码!");
			return "manage/security/update_mobile_fail";
		}
		return "manage/security/update_mobile";
	}
	
	/**
	 * updateMobilePre:修改手机验证短信验证码是否正确 
	 * @author li.n1 
	 * @param verifyCode
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMobilePre",method = RequestMethod.POST)
	public String updateMobilePre(String verifyCode){
		return updatePre(verifyCode,"mobile");
	}
	
	/**
	 * updatePre:通用的修改前发短信方法：
	 * 1、短信验证码校验
	 * 2、失败次数记录
	 * 3、缓存进入该方法10分钟后过期
	 * @author li.n1 
	 * @param verifyCode
	 * @param type
	 * @return 
	 * @since JDK 1.6
	 */
	private String updatePre(String verifyCode,String type){
		//商家编码
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		String code = (String)redisTemplate.opsForValue()
				.get(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode +":"+loginName);
		if(StringUtils.isNotBlank(code) && 
				StringUtils.isNotBlank(verifyCode)){
			if(code.equals(verifyCode)){
				redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":update"+type+":"+loginName);
				//缓存10分钟，10分钟之后再次刷新，身份验证失效，需要退回到第一步，跟短信保持一样的过期时间
				redisTemplate.opsForValue()
					.set(CacheConstant.C_USER_ENTER_METHOD+":update"+type+":"+loginName, 1, systemConfig.getFirstToSecondTime(), TimeUnit.MINUTES);
				//验证成功之后，把该短信验证码作废
				redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode +":"+loginName);
				//该短信验证码错误次数也作废
				redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+code+":errorcount");
				return "1";
			}else{
				//记录输错次数
				int errorCount = NumberUtils.toInt(
						String.valueOf(redisTemplate.opsForValue()
								.get(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+code+":errorcount")));
				//单个验证码输入错误次数为3，把最近一个短信验证码作失效
				if(errorCount>=systemConfig.getMobileCodeErrorSize()-1){
					redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +merchantCode +":"+loginName);
					//该短信验证码错误次数也作废
					redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+code+":errorcount");
				}else{
					redisTemplate.opsForValue().set(
							CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+code+":errorcount",
							errorCount+1);
					redisTemplate.expire(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+code+":errorcount", 
							systemConfig.getMobileCodeExpireTime(), TimeUnit.MINUTES);
				}
				logger.error("商家{}，账号{}，修改绑定{}身份验证失败，输入验证码不准确！",new Object[]{merchantCode,loginName,type});
				return "0";
			}
		}else{
			logger.error("商家{}，账号{}，修改绑定{}份验证失败，验证码为空或已过期！",new Object[]{merchantCode,loginName,type});
			return "-1";
		}
	}
	
	/**
	 * updateEmailPre:修改邮箱验证短信验证码是否正确
	 * @author li.n1 
	 * @param verifyCode
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping(value="/updateEmailPre",method = RequestMethod.POST)
	public String updateEmailPre(String verifyCode){
		return updatePre(verifyCode,"email");
	}
	
	/**
	 * updateEmailNext:修改/绑定邮箱页面 
	 * @author li.n1 
	 * @param map
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping(value = "/updateEmailNext",method = RequestMethod.GET)
	public String updateEmailNext(ModelMap map){
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		//防止直接通过url访问，必须先经过身份验证才能访问该页面
		Integer enterFlag = (Integer)redisTemplate.opsForValue()
				.get(CacheConstant.C_USER_ENTER_METHOD+":updateemail"+":"+loginName);
		if(enterFlag!=null && enterFlag==1){
			map.put("loginName", loginName);
			return "manage/security/update_email_next";
		}else{
			//跳转到身份验证页面
			return "forward:/merchants/security/bandingemail.sc";
		}
	}
	
	/**
	 * updateMobileNext:修改手机页面 
	 * @author li.n1 
	 * @param map
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping(value = "/updateMobileNext",method = RequestMethod.GET)
	public String updateMobileNext(ModelMap map){
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		//防止直接通过url访问，必须先经过身份验证才能访问该页面
		Integer enterFlag = (Integer)redisTemplate.opsForValue()
				.get(CacheConstant.C_USER_ENTER_METHOD+":updatemobile"+":"+loginName);
		if(enterFlag!=null && enterFlag==1){
			map.put("loginName", loginName);
			//上一步使用的短信验证码缓存清除
			redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" 
					+YmcThreadLocalHolder.getMerchantCode() +":"+loginName);
			return "manage/security/update_mobile_next";
		}else{
			//跳转到身份验证页面
			return "forward:/merchants/security/updateMobile.sc";
		}
	}
	
	/**
	 * bandingemail:绑定邮箱 身份验证
	 * @author li.n1 
	 * @param map
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/bandingemail")
	public String bandingemail(ModelMap map){
		//登录账号
		String loginName = YmcThreadLocalHolder.getOperater();
		String mobile = MapUtils.getString(merchantUsers.getMobilePhoneOfLoginName(loginName),"mobile_code");
		if(StringUtils.isNotBlank(mobile)){
			map.put("mobilePhone", mobile.substring(0, 3)+"******"+mobile.substring(9));
		}else{
			map.put("isScuess", false);
			map.put("message", "手机号码为空，请先绑定手机号码!");
			return "manage/security/update_email_fail";
		}
		return "manage/security/update_email";
	}
	
	/**
	 * sendUpdateEmail:发送邮箱激活邮箱 
	 * @author li.n1 
	 * @param code
	 * @param email
	 * @param request
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/sendUpdateEmail", method = RequestMethod.POST)
	public String sendUpdateEmail(String code, String email, HttpServletRequest request){
		//查询用户名
		Map<String, Object> user;
		JSONObject jsObject = new JSONObject();
		try {
			if("1".equals(checkPicCode(code,request))){
				user = merchantUsers.merchantLoginByUserName(YmcThreadLocalHolder.getOperater());
				//发送邮件
				String activaturl=systemConfig.getEmailActivateSrc()+"?code="+MapUtils.getString(user, "id");
				//邮件内容
				StringBuffer strB=new StringBuffer();
				strB.append("<img src='http://s1.ygimg.cn/template/common/images/logo-yg.png'>");
				strB.append("<div>--------------------------------------------------------------------------------------------------</div>");
				strB.append("<div><strong>亲爱的优购商家： </strong></div>");
				strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 您设置的邮件地址已经与你的账号绑定，请妥善保管。请点击以下链接激活邮箱绑定!若无法点击，请将链接复制到浏览器打开。</div>");
				strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 提示：该链接只能在收到邮件后访问1次，超过2个小时或者访问超过1次，链接将自动失效。</div>");
				strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='"+activaturl+"'>"+activaturl+"</a></div>");
				if(StringUtils.isBlank(email)){
					email = MapUtils.getString(user, "email");
					if(StringUtils.isBlank(email)){
						jsObject.put("success", false);
						jsObject.put("message","邮箱为空!");
						logger.warn("邮箱激活链接发送失败，邮箱为空!");
						return jsObject.toString();
					}
				}
//				//1 先判断该邮箱是否绑定账号
//				if(merchantUsers.exitsEmailOfMerchantUsers(email)){
					//2 数据库保存未激活的邮箱
					merchantUsers.updateEmail(MapUtils.getString(user, "id"),email,0);
					//发送邮件
					merchantUsers.sendMail(email,
							"优购-商家中心-激活账户邮箱",
							strB.toString(),
							SubjectIdType.SUBJECT_ID_MALL_USERCENTER_EMAIL_BIND,
							com.yougou.component.email.model.ModelType.MODEL_TYPE_MALL_USERCENTER_EMAIL_BIND,
							YmcThreadLocalHolder.getOperater());
					this.redisTemplate.opsForHash()
								.put(CacheConstant.C_USER_ACTIVATEPASSWORD_ID_TIME, 
										MapUtils.getString(user, "id"), System.currentTimeMillis());
					//2小时过期
					redisTemplate.expire(CacheConstant.C_USER_ACTIVATEPASSWORD_ID_TIME, 2, TimeUnit.HOURS);
					jsObject.put("success", true);
					email = com.yougou.kaidian.common.util.CommonUtil.hideEmailMiddleLetter(email);
					jsObject.put("message", "邮箱激活链接已发送到邮箱："+email+",请查收!");
					logger.warn("邮箱激活链接已发送到邮箱：{}",email);
//				}else{
//					jsObject.put("success", false);
//					jsObject.put("message", "邮箱："+email+"已绑定账号，请更换后进行绑定!");
//					logger.warn("邮箱{}已绑定账号，邮件发送失败！",email);
//				}
			}else{
				jsObject.put("success", false);
				jsObject.put("message","图形验证码不正确!");
				logger.warn("邮箱激活链接发送失败，图形验证码不正确!");
			}
		} catch (Exception e) {
			jsObject.put("success", false);
			jsObject.put("message","邮件发送失败，请稍后再试!");
			logger.error("根据账号查询用户信息发生异常",e);
		}
		return jsObject.toString();
	}
	
	/**
	 * doUpdatePassword:修改密码提交 
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/doUpdatePassword",method = RequestMethod.POST)
	public String doUpdatePassword(String password1,
			String password2,
			String code,
			HttpServletRequest request){
		if(password1.length()>=6 && password2.length()<=20 && password1.equals(password2)){
			//验证code
			if("1".equals(checkPicCode(code,request))){
				String power = com.yougou.kaidian.common.util.CommonUtil.getPasswordPower(password1);
				if(merchantUsers.update_passwordAndPower(password1, power, request)){
					//短信验证码缓存清除
					redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" 
					+YmcThreadLocalHolder.getMerchantCode() +":"+YmcThreadLocalHolder.getOperater());
					return "1";
				}
				//保存错误
				return "-2";
			}
			//验证码错误
			return "0";
		}
		//密码格式不对
		return "-1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/authentication",method = RequestMethod.POST)
	public String authentication(String code, 
			String verifyCode, 
			String from,
			HttpServletRequest request){
		JSONObject jsObject = new JSONObject();
		if("1".equals(checkPicCode(code,request))){
			String result = updatePre(verifyCode,"mobile");
			if("1".equals(result)){
				boolean b = true;
				SessionUtil.setSaveSession("isAuthentication", Constant.SUCCESS, request);
				//ip保存到可信任ip表里面
				if(StringUtils.isNotBlank(from) && "index".equals(from)){
					//先缓存身份验证通过
					redisTemplate.opsForValue().set(YmcThreadLocalHolder.getOperater()+":isAuthentication", Constant.SUCCESS);
					MerchantCenterTrustIp trustIp = new MerchantCenterTrustIp();
					trustIp.setId(UUIDGenerator.getUUID());
					trustIp.setLoginIp(GetAddressByIpUtil.getIPAddress(request));
					trustIp.setCreateDate(new Date());
					trustIp.setLoginName(YmcThreadLocalHolder.getOperater());
					trustIp.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
					b = merchantCenterTrustIpService.insert(trustIp);
				}
				if(b){
					jsObject.put("success", true);
					jsObject.put("message", "身份验证通过");
					logger.warn("验证码匹配正确，进入下一步!");
				}else{
					jsObject.put("success", false);
					jsObject.put("message", "身份验证出现错误，请稍后再试！");
					logger.warn("身份验证出现错误");
				}
			}else if("0".equals(result)){
				jsObject.put("success", false);
				jsObject.put("message","验证码不正确，请输入正确的短信验证码！");
				logger.warn("验证码输入不正确!");
			}else{
				jsObject.put("success", false);
				jsObject.put("message","验证码已失效，请重新获取短信验证码！");
				logger.warn("验证码已失效！!");
			}
		}else{
			jsObject.put("success", false);
			jsObject.put("message","图形验证码不正确!");
			logger.warn("身份验证失败，图形验证码不正确!");
		}
		return jsObject.toString();
	}
	
	/**
	 * authentication:身份验证UI 
	 * @author li.n1 
	 * @param map
	 * @param from 从哪里来的，目前两个地方，1首页  from为index  2子账号管理  from为null
	 * @param response
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/authentication")
	public String authentication(ModelMap map,String from,HttpServletResponse response){
		String loginName = YmcThreadLocalHolder.getOperater();
		Map<String,Object> mobileMap = merchantUsers.getMobilePhoneOfLoginName(loginName);
		String mobileCode = MapUtils.getString(mobileMap, "mobile_code");
		if(StringUtils.isNotBlank(mobileCode)){
			map.put("mobileCode", mobileCode.substring(0, 3)+"******"+mobileCode.substring(9));
			map.put("from", from);
			return "manage_unless/security/authentication";
		}else{
			//未绑定，跳转到账号管理页面
			try {
				CommodityUtilNew.getYgDgAlertThenRedirectScript(response, "请先绑定手机号码！", "/merchants/security/accountSecurity.sc");
			} catch (IOException e) {
				logger.error("获取弹框并关闭页面脚本，跳转其他路径报错！",e);
			}
			return null;
		}
	}
	
	/**
	 * checkPwdStrength:检查当前账号的密码强度 
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/checkPwdStrength")
	public String checkPwdStrength(HttpServletRequest request){
		JSONObject jsObject = new JSONObject();
		String strength = SessionUtil.getSessionProperty(request, "strength");
		String yougouAdmin = SessionUtil.getSessionProperty(request, "is_yougou_admin");
		jsObject.put("strength", strength);
		jsObject.put("yougou", yougouAdmin);
		return jsObject.toString();
	}
	
	/**
	 * deleteRedisCacheOfMobile:方便测试，删除与手机验证码相关的缓存
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6
	 */
	/*
	 * 上线之后删除该代码
	 */
	/*
	@ResponseBody
	@RequestMapping("/{mobileCode}/delCache")
	public String deleteRedisCacheOfMobile(@PathVariable("mobileCode") String mobileCode){
		String loginName = YmcThreadLocalHolder.getOperater();
		//删除短信验证码的缓存，避免每个手机每天只能发短信5次
		redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName +":"+mobileCode);
		//删除每个账号每天只能发短信25条的限制
		redisTemplate.delete(CacheConstant.C_USER_MOBILE_PHONE_CODE + ":" +loginName);
		return "success";
	}*/
	
}
