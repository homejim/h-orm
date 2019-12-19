package com.homejim.framework.reflection;

/**
 * 主要负责 Reflector 对象的创建和缓存
 */
public interface ReflectorFactory {

  /**
   * 检测该 ReflectorFactory 对象是否会缓存 Reflector 对象
   */
  boolean isClassCacheEnabled();

  /**
   * 设置是否缓存
   */
  void setClassCacheEnabled(boolean classCacheEnabled);

  /**
   * 缓存中查找 Class 对应的 Reflector 对象， 找不到则创建
   * @param type
   * @return
   */
  Reflector findForClass(Class<?> type);
}