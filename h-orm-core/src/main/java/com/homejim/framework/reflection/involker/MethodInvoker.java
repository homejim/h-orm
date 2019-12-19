package com.homejim.framework.reflection.involker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Clinton Begin
 * 方法的 Invoker: 将方法包装起来， 直接进行调用
 */
public class MethodInvoker implements Invoker {

  private final Class<?> type;
  private final Method method;

  // 有且仅有一个参数type就等于第一个参数(setter)， 没有就等于返回值（getter）
  public MethodInvoker(Method method) {
    this.method = method;

    if (method.getParameterTypes().length == 1) {
      type = method.getParameterTypes()[0];
    } else {
      type = method.getReturnType();
    }
  }

  // 调用 Method.invoke
  @Override
  public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
    return method.invoke(target, args);
  }

  @Override
  public Class<?> getType() {
    return type;
  }
}