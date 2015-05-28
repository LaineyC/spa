package com.lite.app.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Logger {
	
	/**
	 * 是否需要打印日志
	 */
	boolean print() default true;
	
	/**
	 * 日志信息。<p>
	 */
	String content() default "";

}
