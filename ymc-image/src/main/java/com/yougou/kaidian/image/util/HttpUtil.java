/**
 * 
 */
package com.yougou.kaidian.image.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http操作工具类
 * 
 * @author li.m1
 *
 */
public class HttpUtil {
	
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	/**
	 * 判断url是否有效
	 * 
	 * @param url  ：http://i2.ygimg.cn/pics/zhuanti/20130718/dpxlfq/xl_01.jpg
	 * @return true|false
	 */
	public static boolean isValidURL(String url) {
		if (StringUtils.isBlank(url)) {
			return false;
		}
		int status = -1;
		for (int i = 0, count = 3; i < count; i++) {
			try {
				URL _url = new URL(url);
				HttpURLConnection con = (HttpURLConnection) _url.openConnection();
				status = con.getResponseCode();
				if (200 == status) {
					return true;
				}
			} catch (Exception e) {
				log.error(MessageFormat.format("URL:{0}不可用, 尝试连接{1}次", new Object[]{url, i + 1}));
			}
		}
		
		return false;
	}
	
	/**
	 * 下载URL文件
	 * 
	 * @param url ： http://i2.ygimg.cn/pics/zhuanti/20130718/dpxlfq/xl_01.jpg
	 * @return stream
	 */
	public static InputStream getInputStreamByURL(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
	    InputStream in = null;
	    try {
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (200 == response.getStatusLine().getStatusCode()) {
				InputStream _in = entity.getContent();
				in = new ByteArrayInputStream(IOUtils.toByteArray(_in));
				IOUtils.closeQuietly(_in);
			}else{
				log.error(MessageFormat.format("getInputStreamByURL下载URL失败:[{0}],Status:{1}.", url,response.getStatusLine().getStatusCode()));
			}
		} catch (ClientProtocolException e) {
			log.error(MessageFormat.format("下载URL:[{0}]HTTP异常.", url), e);
		} catch (IOException e) {
			log.error(MessageFormat.format("下载URL:[{0}]IO异常.", url), e);
		} catch (Exception e) {
			log.error(MessageFormat.format("下载URL:[{0}]IO异常.", url), e);
		} finally{  
            client.getConnectionManager().shutdown();
        }
	    return in;
	}
	
	public static InputStream executeMethod(HttpRequestBase httpMethod) {
		DefaultHttpClient client = new DefaultHttpClient();
		InputStream in = null;
		try {
			HttpResponse response = client.execute(httpMethod);
			HttpEntity entity = response.getEntity();
			if (200 == response.getStatusLine().getStatusCode()) {
				InputStream _in = entity.getContent();
				in = new ByteArrayInputStream(IOUtils.toByteArray(_in));
				IOUtils.closeQuietly(_in);
			}else{
				log.error(MessageFormat.format("executeMethod下载URL失败:[{0}],Status:{1}.", httpMethod.getURI().toString(),response.getStatusLine().getStatusCode()));
			}
		} catch (ClientProtocolException e) {
			log.error(MessageFormat.format("下载URL:[{0}]HTTP异常.", httpMethod.getURI().toString()), e);
		} catch (IOException e) {
			log.error(MessageFormat.format("下载URL:[{0}]IO异常.", httpMethod.getURI().toString()), e);
		} catch (Exception e) {
			log.error(MessageFormat.format("下载URL:[{0}]IO异常.", httpMethod.getURI().toString()), e);
		}finally{  
            client.getConnectionManager().shutdown();
        }
	    return in;
	}
}
