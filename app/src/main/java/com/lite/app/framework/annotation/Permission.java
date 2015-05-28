package com.lite.app.framework.annotation;

import com.lite.app.framework.enums.Modifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限验证的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Permission {

    /**
     * 权限可访问的级别
     */
    Modifier level() default Modifier.PROTECTED;

}
