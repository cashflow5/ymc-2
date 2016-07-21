package com.yougou.api.util;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/**
 * 
 * @author 杨梦清
 * 
 */
public class HibernateUtils {
	
	/**
	 * 获取非Hibernate包装对象实例
	 * 
	 * @param proxy
	 * @return T
	 */
	public static <T> T unwarp(T proxy) {
		Hibernate.initialize(proxy);
		if (HibernateProxy.class.isInstance(proxy)) {
			proxy = (T) ((HibernateProxy) proxy).getHibernateLazyInitializer().getImplementation();
		}
		return proxy;
	}
}

