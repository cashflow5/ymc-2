/**
 * 
 */
package com.belle.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *<P>
 * 2011-5-25下午07:42:29
 *<P>
 * @author liudi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
	String name();
}
