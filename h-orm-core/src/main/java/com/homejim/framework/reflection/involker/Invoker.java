package com.homejim.framework.reflection.involker;

import java.lang.reflect.InvocationTargetException;

/**
 * Invoker: 与方法的 invoke 相关
 *
 * @author Clinton Begin
 */
public interface Invoker {
    // 调用方法
    Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException;

    // 获取类型
    Class<?> getType();
}
