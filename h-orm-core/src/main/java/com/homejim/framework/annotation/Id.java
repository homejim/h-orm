package com.homejim.framework.annotation;

/**
 * Id， 一个类里面只能有一个。 不支持复合主键
 * <p>
 * 列注解
 * <p>
 * 列注解
 */

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
}
