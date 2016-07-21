package com.belle.infrastructure.log.factory;

import com.belle.infrastructure.log.model.vo.AppLog;



/**
 * 
 *@author yhb
 * 
 * @version 创建时间：2011-4-12 下午04:56:54
 */
public class AppLogFactory {
	
	public static AppLog getLog(Class clazz){
		AppLog appLog = new AppLog(clazz.getName());
		return appLog;
	}

}
