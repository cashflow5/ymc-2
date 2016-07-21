package com.yougou.kaidian.commodity.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.yougou.kaidian.commodity.model.vo.RedisFilePublishVo;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.ordercenter.common.DateUtil;


/**
 * 
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION : 页面发布 copy from cms
 * @AUTHOR : liang.yx@yougou.com
 * @DATE :2013-5-14
 *       </p>
 **************************************************************** 
 */
@Component
public class FSSFilePublisher {
	
	@Value("#{configProperties['static.server.ips']}") 
    private String STATIC_SERVER_IPS;

	/**
	 * slf4j def
	 */
	private static final Logger logger = LoggerFactory.getLogger(FSSFilePublisher.class);
	
 
	@Autowired
    @Qualifier("redisHtmlTemplate")
	private RedisTemplate<String, String> redisTemplate ;

	/**
	 * 发布文件
	 * 
	 * @author tanfeng
	 * @param filePath
	 *            路径名，相对于主目录
	 * @param content
	 *            文件内容
	 * @param ips
	 *            分配到的IP列表
	 */
	public void publishSingleFileByJson(String filePath, String content, List<String> ips) {
		logger.info("Publish file:{}", filePath); // debug

		RedisFilePublishVo rtv = new RedisFilePublishVo();
		rtv.setIps(ips);
		rtv.setContent(content);
		rtv.setFlag(RedisFilePublishVo.FLAG_CONTENT);
		rtv.setFilePath(filePath);

		publishByJson(rtv);

	}

	/**
	 * 发布文件
	 * 
	 * @author tanfeng
	 * @param rtv
	 */
	public void publishByJson(RedisFilePublishVo rtv) {
		ObjectMapper om = new ObjectMapper();
		try {
			String publishContent = om.writeValueAsString(rtv);
			redisTemplate.convertAndSend(Constant.REDIS_CHANNEL_NAME_OF_PUBLISH_FILE, publishContent);
		} catch (IOException e) {
			logger.error("Publish file:{} error", rtv, e); // error
		} finally {
			om = null;
		}
	}
	
	/**
	 * 将版式内容生成静态页
	 * @param htmlContent
	 * @param filePath
	 * @throws IOException
	 */
	public void setStaticHtml(String htmlContent,String filePath) {
		try{
			filePath = Constant.FILE_NAME_URL_PATH + filePath;
			List<String> ips = parseIPs(this.STATIC_SERVER_IPS);
			//byte[] bytes = (htmlContent == null || "".equals(htmlContent)) ? (" ".getBytes()) : htmlContent.getBytes("UTF-8"); 
			//String newhtmlContent = new String(bytes);
			if(htmlContent == null){
				htmlContent = "";
			}
			String createStr = "<!--关联销售版式设置模板生成Start时间："+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" -->";
			String endStr = "<!--关联销售版式设置模板生成End："+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" -->";
			htmlContent = createStr + htmlContent + endStr;
		
			publishSingleFileByJson(filePath, htmlContent, ips);
		}catch (Exception e) {
			logger.error("FSSFilePublisher setStaticHtml设置生成版式静态页异常,静态页文件{}",filePath, e);
		}
	}
	
	private List<String> parseIPs(String ipsString) {
		String ipArray[] = ipsString.split(",");
		List<String> ipList = new ArrayList<String>();
		if (ipArray != null && ipArray.length > 0) {
			for (String ip : ipArray) {
				if (StringUtils.isNotBlank(ip) && isValidateIp(ip)) {
					ipList.add(ip.trim());
				}
			}
		}
		return ipList;
	}
	
	/**
	 * 校验ip是否合法
	 * 
	 * @param ip
	 * @return
	 */
	private boolean isValidateIp(String ip) {
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ip.trim());
		return matcher.matches();
	}
}
