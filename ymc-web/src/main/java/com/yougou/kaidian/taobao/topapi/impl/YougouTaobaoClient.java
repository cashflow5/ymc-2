package com.yougou.kaidian.taobao.topapi.impl;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;


public class YougouTaobaoClient {

	public static TaobaoClient getYougouTaobaoClient(){
		final String appKey = "12575262";
		final String appSecret = "0c6095945537a6b7ec3404bb7f0398df";
		return getYougouTaobaoClient(appKey, appSecret);
	}

	public static TaobaoClient getYougouTaobaoClient(String appKey, String appSecret){
		//final String serverUrl = "http://121.196.131.76/router/rest";
		final String serverUrl = "http://gw.api.taobao.com/router/rest";
		return new DefaultTaobaoClient(serverUrl, appKey, appSecret);
	}
}
