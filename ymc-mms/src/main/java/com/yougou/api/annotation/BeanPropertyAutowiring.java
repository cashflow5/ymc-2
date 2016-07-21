package com.yougou.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Bean 属性自动装配接口<br/>
 * 通过 Spring 完成 Bean 的属性自动装配工作<br/>
 * 
 * @author 杨梦清
 * 
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Inherited
@Documented
public @interface BeanPropertyAutowiring {
	
	int value() default AutowireCapableBeanFactory.AUTOWIRE_NO;
}
