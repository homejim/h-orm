package com.homejim.framework.annotation;

import java.lang.annotation.*;

/**
 * 数据库表标识
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    String value();
}
