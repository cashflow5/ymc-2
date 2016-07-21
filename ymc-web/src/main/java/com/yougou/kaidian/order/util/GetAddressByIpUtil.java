package com.yougou.kaidian.order.util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yougou.kaidian.framework.constant.Constant;
public class GetAddressByIpUtil {
	
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GetAddressByIpUtil.class);
    
    
	 /**
     * 根据IP 获取IP所在地信息，调用淘宝第三方接口
     * 返回数据格式：{"code":0,"data":{"ip":"210.75.225.254","country":"\u4e2d\u56fd","area":"\u534e\u5317",
		"region":"\u5317\u4eac\u5e02","city":"\u5317\u4eac\u5e02","county":"","isp":"\u7535\u4fe1",
		"country_id":"86","area_id":"100000","region_id":"110000","city_id":"110000",
		"county_id":"-1","isp_id":"100017"}}
		其中code的值的含义为，0：成功，1：失败。 
     * @param IP
     * @return
     */
    public static String GetAddressByIp(String IP){
        String address = "";
        try{
	         String str = getJsonContent(Constant.GET_ADDRESS_BYID_URL+IP);
	         // logger.info(str);
	         JSONObject obj = JSONObject.fromObject(str);
	         JSONObject obj2 ;
	         Integer code ;
	         if( null!=obj && (obj.get("data") ) instanceof JSONObject ){
	        	 obj2 =  (JSONObject) obj.get("data");
	        	 code = (Integer) obj.get("code");
	         
		         if(null!= code && code == 0 ){        
		        	 //address =  obj2.get("country")+"--" +obj2.get("area")+"--"+obj2.get("region")+"--" +obj2.get("city")+"--" +obj2.get("isp");
		        	 // 省份
		        	 String region = (String) obj2.get("region");
		        	 // 城市
		        	 String city = (String) obj2.get("city");
		        	 // 返回省市信息
		        	 address = region+city;
		        	 if(StringUtils.isBlank(address)){
		        		 address =  (String) obj2.get("country");
		        	 }
		         }else{
		        	 address =  "IPError";
		         }
	         }
        }catch(Exception e){
        	logger.error("获取IP所在地异常：ip="+IP,e);
            address = "IPError";
        }
        return address;
          
    }
    
    /**
     * GetAddressByIpWithSina:通过新浪的ip查询接口查询 ，返回json格式
     * @author li.n1 
     * @param IP ip地址
     * @param format 返回的格式 有js/json/xml等格式
     * @return 归属地
     * @since JDK 1.6 
     * @date 2015-10-10 下午1:49:10
     */
    public static String GetAddressByIpWithSina(String IP,String format){
        String address = "";
        try{
	         String str = getJsonContent(MessageFormat.format(Constant.GET_ADDRESS_BYID_URL_USE_SINA,format,IP));
	         // logger.info(str);
	         if("-2".equals(str) || "-3".equals(str)){
	        	 return "未分配或者内网IP";
	         }
	         JSONObject obj = JSONObject.fromObject(str);
	         //JSONObject obj2 =  (JSONObject) obj.get("data");
	         if( obj !=null && null!=( obj.get("ret")) && ( (Integer) obj.get("ret") )==1 ){
	        	 // 省份
	        	 String region = (String) obj.get("province");
	        	 // 城市
	        	 String city = (String) obj.get("city");
	        	 // 返回省市信息
	        	 address = region+city;
	        	 if(StringUtils.isBlank(address)){
	        		 address =  (String) obj.get("country");
	        	 }
	         }else{
	        	 address =  "未分配或者内网IP";
	         }
        }catch(Exception e){
        	logger.error("获取IP所在地异常：ip="+IP,e);
            address = "IPError";
        }
        return address;
          
    }
    
    /**
     * 传入URL，建立HTTP链接调用淘宝接口并获取返回的输入流
     * @param urlStr
     * @return
     */
    public static String getJsonContent(String urlStr)
    {
        try
        {// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url
                    .openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(3000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            // 获取相应码
            int respCode = httpConn.getResponseCode();
            if (respCode == 200)
            {
                return ConvertStream2Json(httpConn.getInputStream());
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        return result.toString();
    }
 
    /**
     * 将淘宝接口返回的输入流转为json 字符串
	 *
     * @param inputStream
     * @return
     */
    private static String ConvertStream2Json(InputStream inputStream)
    {
    	JSONObject result = new JSONObject();
        String jsonStr = result.toString();
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try
        {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }
    
    /**
     * @Description java获取客户端访问的真实IP地址
     * @author temdy
     * @Date 2014-11-07
     * @param request 请求对象
     * @return
     */
    public static String getIPAddress(HttpServletRequest request){
    	String ip = null;
    	//获取X-Forwarded-For
    	if( null!=request ){
    	
	    	ip = request.getHeader("x-forwarded-for");  
	    	
	    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	    		//获取Proxy-Client-IP
	    		ip = request.getHeader("Proxy-Client-IP");  
	    	}  
	    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	    		//WL-Proxy-Client-IP
	    		ip = request.getHeader("WL-Proxy-Client-IP");  
	    	}  
	    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	    		//获取的IP实际上是代理服务器的地址，并不是客户端的IP地址
	    		ip = request.getRemoteAddr();  
	    	}		
	    	/*
	    	 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	    	 * X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100  {"code":1,"data":"invaild ip."}=========
	    	 * 用户真实IP为： 192.168.1.110
	    	 */
	    	if (ip.contains(",")){
	    		ip = ip.split(",")[0];
	    	}
    	}else{
    		ip = "unknown";
    	}
    	return ip;
    }
    
//    public static void main(String[] args) {
//    	JSONObject result = new JSONObject();
//    	String address = GetAddressByIpUtil.GetAddressByIpWithSina("180.97.33.107","json");//
//    	System.out.println("address:"+address);
//	}
}
