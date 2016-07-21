package com.yougou.kaidian.user.service.impl;  

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.yougou.component.sms.constant.ModelType;
import com.yougou.component.sms.model.SmsVo;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.order.util.GetAddressByIpUtil;
import com.yougou.kaidian.user.dao.MerchantCenterTrustIpMapper;
import com.yougou.kaidian.user.dao.MessageMapper;
import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;
import com.yougou.kaidian.user.model.pojo.MerchantCenterTrustIp;
import com.yougou.kaidian.user.model.pojo.Message;
import com.yougou.kaidian.user.service.IMerchantCenterTrustIpService;

@Service
public class MerchantCenterTrustIpServiceImpl implements IMerchantCenterTrustIpService {
	
	@Resource
	private MerchantCenterTrustIpMapper merchantCenterTrustIpMapper;
	private Logger logger = LoggerFactory.getLogger(MerchantCenterTrustIpServiceImpl.class);
	@Resource
	private MessageMapper messageMapper;
	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public boolean checkTrustIp(String ip, String loginName) {
		int count = merchantCenterTrustIpMapper.checkTrustIp(ip,loginName);
		if(count>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean insert(MerchantCenterTrustIp trustIp) {
		boolean flag = false;
		try{
			merchantCenterTrustIpMapper.insert(trustIp);
			flag = true;
			logger.warn("添加信任IP:{}",new Object[]{ToStringBuilder.reflectionToString(trustIp, ToStringStyle.SHORT_PREFIX_STYLE)});
		}catch(Exception e){
			logger.error("信任ip插入数据库失败：",e);
		}
		return flag;
	}
	
	@Override
	public boolean checkTrustAddrByAddr(MerchantCenterOperationLog operationLog, String loginName,
			String mobileCode) {
		//查看是否有登录的记录，首次登录不做提示，排除当前登录的次数
		int loginCount = merchantCenterTrustIpMapper.findLoginLogCount(loginName,operationLog.getOperated());
		String loginAddress = operationLog.getLoginAddress();
		//如果为IPError，再次请求获取归属地
		if(loginAddress.equals("IPError")){
			loginAddress = GetAddressByIpUtil.GetAddressByIpWithSina(operationLog.getLoginHost(), "json");
		}
		if(loginCount<=0){
			//商家账号第一次登录
			return true;
		}else{
			//查询该账号一个月内所有登录的归属地，作为可信任的归属地，排除当前登录的归属地
			List<String> addressList = merchantCenterTrustIpMapper.findTrustAddrByName(loginName,operationLog.getOperated());
			if(loginAddress.equals("未分配或者内网IP") || loginAddress.equals("IPError") ||
					addressList.contains(loginAddress)){
				//在可信任归属地里面
				return true;
			}
		}
		//先判断是否绑定手机，没有绑定手机不验证身份，绑定了手机，并且今天未发过短信
		Boolean flag = (Boolean)redisTemplate.opsForValue().get(
				CacheConstant.C_MERCHANT_LOGIN_NAME_SECURITY+":"+loginName+":"+mobileCode+":"+loginAddress);
		if(StringUtils.isNotBlank(mobileCode) && (flag==null)){
			//发送短信提醒，并且记录日志
			String addr = loginAddress;
			SmsVo smsVo = new SmsVo();
			smsVo.setPhone(new String[] { mobileCode });
			String[] citys = new String[]{"北京","天津","上海","重庆"};
			String cityStr = "北京北京,天津天津,上海上海,重庆重庆";
			if(cityStr.contains(loginAddress)){
				for(String city : citys){
					if(loginAddress.contains(city)){
						addr = loginAddress.replaceFirst(city, "");
						break;
					}
				}
			}
			smsVo.setContent("优购商家中心账号提醒：您的账号"+loginName+
					"于"+DateUtil.getFormatByDate(new Date())+"，在"+addr+"，ip："+operationLog.getLoginHost()+"登录。如有异常，请及时修改登录密码！【优购时尚商城】");
			smsVo.setModelType(ModelType.MODEL_TYPE_MERCHANT);
			//暂时先内部观察
			//smsApi.sendNowByYouGou(smsVo);
			redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_LOGIN_NAME_SECURITY+":"+
					loginName+":"+mobileCode+":"+loginAddress, true);
			//设置生存期为当天23:59:59
			redisTemplate.expireAt(CacheConstant.C_MERCHANT_LOGIN_NAME_SECURITY +":"+loginName+":"+mobileCode+":"+loginAddress, 
					com.yougou.tools.common.utils.DateUtil.parseDate(
							com.yougou.tools.common.utils.DateUtil.format(new Date(), "yyyy-MM-dd")+" 23:59:59", 
							com.yougou.tools.common.utils.DateUtil.LONG_DATE_TIME_PATTERN));
			//短信记录
			Message message = new Message();
			message.setId(UUIDGenerator.getUUID());
			message.setOperated(new Date());
			message.setLoginName(loginName);
			message.setTo(mobileCode);
			//信息的类型   0短信  1邮件
			message.setType("0");
			message.setContent(smsVo.getContent());
			message.setComment("登录异常");
			messageMapper.saveMessage(message);
		}else{
			return true;
		}
		return false;
	}
}
