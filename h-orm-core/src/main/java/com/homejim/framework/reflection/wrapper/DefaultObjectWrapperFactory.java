package com.homejim.framework.reflection.wrapper;

import com.homejim.framework.exception.HormReflectionException;
import com.homejim.framework.reflection.MetaObject;

import javax.management.ReflectionException;

/**
 * @author Clinton Begin
 * mybatis 提供的 ObjectWrapperFactory 的默认实现类（不可用）
 */
public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

  // 始终返回 false
  @Override
  public boolean hasWrapperFor(Object object) {
    return false;
  }

  // 抛出异常
  @Override
  public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
    throw new HormReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
  }

}
