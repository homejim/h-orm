package com.homejim.framework.reflection.wrapper;

import com.homejim.framework.reflection.MetaObject;

/**
 * @author Clinton Begin
 * 负责 ObjectWrapper 对象的创建
 */
public interface ObjectWrapperFactory {

  // 判断是否有对应的 ObjectWrapper 对象
  boolean hasWrapperFor(Object object);

  // 获取指定的 ObjectWrapper 对象
  ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}
