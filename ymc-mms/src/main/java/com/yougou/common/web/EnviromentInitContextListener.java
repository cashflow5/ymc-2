package com.yougou.common.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * application的设置路径
 * @author liuwenjun
 * create time 2012-8-13
 */
public class EnviromentInitContextListener implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(EnviromentInitContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String basePath = event.getServletContext().getContextPath();
		
		//set dynamic WMS Dubbo Hessian service base path
		String wmsDubboBasePath = "";
		if("/".equals(basePath)) {
			basePath = "";
		} else {
			if(StringUtils.isNotBlank(basePath)){
				wmsDubboBasePath = basePath.substring(1) + "/";
		
			}else{
				 wmsDubboBasePath =  "";
			}
		}
		System.setProperty("yougou.BasePath", basePath);
		System.setProperty("yougou.wms.dubbo.BasePath", wmsDubboBasePath);
		
		logger.info("init BasePath="+ basePath);
		logger.info("init WMS Dubbo hessian service BasePath="+ wmsDubboBasePath);
	}

}
