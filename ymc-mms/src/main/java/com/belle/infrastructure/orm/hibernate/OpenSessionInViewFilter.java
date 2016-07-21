/*
 * 作者:Mrvoce
 **/
package com.belle.infrastructure.orm.hibernate;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * 缩窄OpenSessionInViewFilter的过滤范围,请求css、js、图片等静态内容时不创建/关闭Hibernate Session连接.
 * 
 * 在web.xml中可配置excludeSuffixs参数,多个过滤文件后缀名以','分割.
 * 
 * @author calvin
 */
public class OpenSessionInViewFilter extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {

	public static final String EXCLUDE_SUFFIXS_NAME = "excludeSuffixs";

	public static final String INCLUDE_SUFFIXS_NAME = "includeSuffixs";

	private static final String[] DEFAULT_EXCLUDE_SUFFIXS = { ".js", ".css", ".jpg", ".gif" };

	private static final String[] DEFAULT_INCLUDE_SUFFIXS = { ".action" };

	private String[] excludeSuffixs = DEFAULT_EXCLUDE_SUFFIXS;

	private String[] includeSuffixs = DEFAULT_INCLUDE_SUFFIXS;

	/**
	 * 重载过滤控制函数,忽略特定后缀名的请求.
	 */
	@Override
	protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
		String fullPath = request.getServletPath();
		String path = StringUtils.substringBefore(fullPath, "?");

		for (String suffix : includeSuffixs) {
			if (path.endsWith(suffix))
				return false;
		}

		for (String suffix : excludeSuffixs) {
			if (path.endsWith(suffix))
				return true;
		}

		return false;
	}

	/**
	 * 初始化excludeSuffixs参数.
	 */
	@Override
	protected void initFilterBean() throws ServletException {

		String includeSuffixStr = getFilterConfig().getInitParameter(INCLUDE_SUFFIXS_NAME);

		if (StringUtils.isNotBlank(includeSuffixStr)) {
			includeSuffixs = includeSuffixStr.split(",");
			//为匹配字符串加上"."使匹配更精确，如.jpg
			for (int i = 0; i < includeSuffixs.length; i++) {
				includeSuffixs[i] = "." + includeSuffixs[i];
			}
		}

		String excludeSuffixStr = getFilterConfig().getInitParameter(EXCLUDE_SUFFIXS_NAME);

		if (StringUtils.isNotBlank(excludeSuffixStr)) {
			excludeSuffixs = excludeSuffixStr.split(",");
			for (int i = 0; i < excludeSuffixs.length; i++) {
				excludeSuffixs[i] = "." + excludeSuffixs[i];
			}
		}
	}
	
	
	protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        session.setFlushMode(FlushMode.COMMIT);
        return session;
}

	protected void closeSession(Session session, SessionFactory factory) {
        session.flush();
        super.closeSession(session, factory);
}
}

