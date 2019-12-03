package com.homejim.framework.annotation;

import java.lang.annotation.*;

/**
 * 列注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String value();
}
