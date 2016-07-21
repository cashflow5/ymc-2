package com.belle.memcached.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 读取缓存注解
 *
 * @author 罗正加
 * @date 2011-7-20
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReadCacheAnnotation {
	/**
	 * 命名空间
	 */
	String namespace() default "YOUGOU_";

	/**
	 * 缓存服务器客户端名称
	 */
	CacheClientEnum clientName();

	/**
	 *	緩存key
	 */
	String assignCacheKey();

	/**
	 * 是否开启本地缓存
	 */
	boolean localCache() default false;

	/**
	 * 本地缓存的时间(localCache为true时该参数才有效)
	 */
	int expiration() default 0;
}
