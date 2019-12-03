package com.homejim.framework.annotation;

/**
 * Id， 一个类里面只能有一个。 不支持复合主键
 */

/**
 * 列注解
 */

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
}
