package com.yougou.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Servlet 变量
 * 
 * @author 杨梦清
 * 
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.METHOD })
@Inherited
@Documented
public @interface ServletVariable {

	Named name();

	public static enum Named {
		HTTP_BASE_PATH,
		HTTP_CONTEXT_PATH,
		HTTP_LOCALE,
		HTTP_REQUEST, 
		HTTP_REQUEST_PARAMETERS,
		HTTP_REQUEST_IP,
		HTTP_RESPONSE, 
	}
}
