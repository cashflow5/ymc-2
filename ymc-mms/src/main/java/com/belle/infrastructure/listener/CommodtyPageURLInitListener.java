package com.belle.infrastructure.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.belle.infrastructure.html.CommodtyPageURLMethod;

/**
 * 系统初始化监听器
 * @author liu.wj
 *
 */
public class CommodtyPageURLInitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		servletContext.setAttribute("commodtyPageURLMethod", new CommodtyPageURLMethod());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}
	
	
}
