package com.yougou.kaidian.image.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil;
import com.yougou.kaidian.common.vo.Image4BatchUploadMessage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.image.constant.ImageSetting;
import com.yougou.kaidian.image.service.ITaskJobService;

@Controller
@RequestMapping("/httptaskjob")
public class HttpTaskJobControll {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpTaskJobControll.class);
	
	@Resource
	private ITaskJobService taskJobService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private ImageSetting imageSetting;
	private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 删除本服务器临时目录过期图片
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET,value="/deltemppicfile")
	public void deleteTemporaryImg(HttpServletRequest request){
		String clientIP=this.getHttpRequestClientIp(request);
		boolean ipcheck=clientIP!=null&&(clientIP.indexOf("10.10.10")>-1||clientIP.indexOf("10.0.20")>-1);
		if(ipcheck){
			logger.warn(dateFormat.format(new Date())+":接收到执行删除临时图片任务");
			boolean isdeleted=taskJobService.deleteFiles(imageSetting.commodityTemporaryPicdir, null, 7, false);
			if(!isdeleted) {
				logger.error(dateFormat.format(new Date())+":执行删除临时图片任务失败");
			}
			
			try {
				//清理图片处理缓存
				List<Object> masterMessages = this.redisTemplate.opsForHash().values(CacheConstant.C_IMAGE_MASTER_JMS_KEY);
				List<Object> batchMessages = this.redisTemplate.opsForHash().values(CacheConstant.C_IMAGE_BATCH_JMS_KEY);
				
				Date curentDate = new Date();
				if (CollectionUtils.isNotEmpty(masterMessages)) {
					for (Object object : batchMessages) {
						Image4SingleCommodityMessage _message = (Image4SingleCommodityMessage) object;
						if (null == _message.getCreateTime() || DateUtil.addDay2Date(7, _message.getCreateTime()).getTime() > curentDate.getTime()) {
							this.redisTemplate.opsForHash().delete(CacheConstant.C_IMAGE_MASTER_JMS_KEY, _message.getCommodityNo());
						}
					}
				}
				
				if (CollectionUtils.isNotEmpty(batchMessages)) {
					for (Object object : batchMessages) {
						Image4BatchUploadMessage _message = (Image4BatchUploadMessage) object;
						if (null == _message.getCreateTime() || DateUtil.addDay2Date(7, _message.getCreateTime()).getTime() > curentDate.getTime()) {
							this.redisTemplate.opsForHash().delete(CacheConstant.C_IMAGE_BATCH_JMS_KEY, _message.getCommodityNo());
						}
					}
				}
				logger.warn("清理图片缓存成功!");
			} catch (Exception e) {
				logger.error("清理图片处理缓存异常。", e);
			}
		}
	}
	
	/**
	 * 获取请求客户端IP
	 * 
	 * @return String
	 */
	protected String getHttpRequestClientIp(HttpServletRequest request) {
			String clientIp = request.getHeader("X-Forwarded-For");
			if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
				clientIp = request.getHeader("Proxy-Client-Ip");
			}
			if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
				clientIp = request.getHeader("WL-Proxy-Client-Ip");
			}
			if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
				clientIp = request.getRemoteAddr();
			}
			if (StringUtils.isNotBlank(clientIp) && !"unknown".equalsIgnoreCase(clientIp)) {
				String[] clientIps = StringUtils.split(clientIp, ",");
				clientIp = clientIps.length > 0 ? clientIps[0] : clientIp;
			}
		return StringUtils.trimToNull(clientIp);
	}
}
