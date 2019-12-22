package com.homejim.framework.reflection.wrapper;

import com.homejim.framework.reflection.MetaObject;
import com.homejim.framework.reflection.factory.ObjectFactory;
import com.homejim.framework.reflection.property.PropertyTokenizer;

import java.util.List;

/**
 * @author Clinton Begin
 * 对象的包装类
 */
public interface ObjectWrapper {

  // 如果封装的是普通的 JavaBean 对象， 则调用相应属性的 getter 方法
  // 如果封装的是集合类， 则获取制定 key 或 下标对应的 value
  Object get(PropertyTokenizer prop);

  // 如果封装的是普通的 JavaBean 对象， 则调用相应属性的 getter 方法
  // 如果封装的是集合类， 则获取制定 key 或 下标对应的 value
  void set(PropertyTokenizer prop, Object value);

  // 查找属性表达式指定的属性， 第二个参数表示是否忽略属性表达式中的下划线
  String findProperty(String name, boolean useCamelCaseMapping);

  // 获取可写属性的属性集合
  String[] getGetterNames();

  // 获取可读属性的集合
  String[] getSetterNames();

  // 解析属性表达式指定属性的 setter 方法的参数类型
  Class<?> getSetterType(String name);

  // 解析属性表达式指定属性的 getter 方法的参数类型
  Class<?> getGetterType(String name);

  // 判断指定表达式是否有 setter 方法
  boolean hasSetter(String name);

  // 判断指定表达式是否有 getter 方法
  boolean hasGetter(String name);

  // 为属性表达式指定的属性创建对应的 MetaObject 对象
  MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);

  // 判断是否为集合
  boolean isCollection();

  // Collection 对象的 add 方法
  void add(Object element);

  // Collection 对象的 addAll 方法
  <E> void addAll(List<E> element);

}
