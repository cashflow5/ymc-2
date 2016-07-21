package com.yougou;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class test1 {
	public static void main(String[] args) throws Exception {
		/*HttpClient client = new DefaultHttpClient();
		HttpPost method = new HttpPost("http://www.shuhuatai.com/mocai/vote.php");
		List <NameValuePair> params = new ArrayList<NameValuePair>();  
        params.add(new BasicNameValuePair("num", "12190025"));
        params.add(new BasicNameValuePair("dowhat", "post")); 
        params.add(new BasicNameValuePair("username", "niange")); 
        for(int i=0;i<10;i++){
        	 params.add(new BasicNameValuePair("userid", "110"+i)); 
        	method.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
        	HttpResponse response = client.execute(method);
    		HttpEntity entity = response.getEntity();
    		int status = 0;
    		if(response != null && response.getStatusLine() != null){
    			status = response.getStatusLine().getStatusCode();
    			if(status==200){
    				if("success".equals(EntityUtils.toString(entity))){
    					System.out.println("投票成功！！=======");
    				}
    			}
    			System.out.println("投票不成功！==========");
    		}
        }*/
		
		UID uid = new UID();
		System.out.println(uid.toString());
	}
	
}
