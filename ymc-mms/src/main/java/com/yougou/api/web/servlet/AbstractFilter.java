package com.yougou.api.web.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 过滤器基类
 * 
 * @author yang.mq
 *
 */
public abstract class AbstractFilter extends FilterSupport implements Filter {
	
	protected static final Logger LOGGER = Logger.getLogger(AbstractFilter.class);
	
	protected static final String MONGODB_COLLECTION_NAME = "tbl_merchant_api_filter_variable";
	
	protected static final String MONGODB_DBOBJECT_IDENTIFIER = "identifier";
	
	protected static final String MONGODB_DBOBJECT_VARIABLE = "variable";
	
	protected static final String MONGODB_DBOBJECT_VARIABLE_1 = "lastAccessTimeMillis";
	
	protected static final String MONGODB_DBOBJECT_VARIABLE_2 = "lastAccessCount";
	
	protected FilterConfig filterConfig;
	
	protected ApplicationContext applicationContext;
	
	protected AutowireCapableBeanFactory beanFactory;
	
	@Override
	public final void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.applicationContext = findWebApplicationContext();
		this.beanFactory = applicationContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
		try {
			onInitFilterSet();
		} catch (Exception ex) {
			if (ex instanceof ServletException) {
				throw (ServletException) ex;
			} else {
				throw new ServletException(ex);
			}
		} finally {
			LOGGER.info(getClass().getSimpleName() + " init at " + System.currentTimeMillis());
		}
	}

	@Override
	public void destroy() {
		LOGGER.info(getClass().getSimpleName() + " destroy at " + System.currentTimeMillis());
	}
	
	/**
	 * 初始化过滤器
	 * 
	 * @throws Exception
	 */
	public void onInitFilterSet() throws Exception {
		
	}
	
	/**
	 * 查找 Spring Web 应用上下文
	 * 
	 * @return WebApplicationContext
	 */
	private WebApplicationContext findWebApplicationContext() {
		String attrName = filterConfig.getInitParameter("contextAttribute");
		if (attrName == null) {
			return WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		} else {
			return WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext(), attrName);
		}
	}
}
