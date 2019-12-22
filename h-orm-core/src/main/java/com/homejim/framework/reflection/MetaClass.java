package com.homejim.framework.reflection;

import com.homejim.framework.reflection.involker.GetFieldInvoker;
import com.homejim.framework.reflection.involker.Invoker;
import com.homejim.framework.reflection.involker.MethodInvoker;
import com.homejim.framework.reflection.property.PropertyTokenizer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author Clinton Begin
 * 对反射类的元信息的封装和处理
 */
public class MetaClass {

  private final ReflectorFactory reflectorFactory;
  private final Reflector reflector;

  /**
   * MetaClass 构造函数
   */
  private MetaClass(Class<?> type, ReflectorFactory reflectorFactory) {
    this.reflectorFactory = reflectorFactory;
    this.reflector = reflectorFactory.findForClass(type);
  }

  /**
   * 跟上面的是一样的
   */
  public static MetaClass forClass(Class<?> type, ReflectorFactory reflectorFactory) {
    return new MetaClass(type, reflectorFactory);
  }

  /**
   * 通过属性名称， 获取属性的 MetaClass
   */
  public MetaClass metaClassForProperty(String name) {
    Class<?> propType = reflector.getGetterType(name);
    return MetaClass.forClass(propType, reflectorFactory);
  }

  /**
   * 查找 Property
   */
  public String findProperty(String name) {
    // 通过 buildProperty 方法
    StringBuilder prop = buildProperty(name, new StringBuilder());
    return prop.length() > 0 ? prop.toString() : null;
  }
  /**
   * 查找 Property
   */
  public String findProperty(String name, boolean useCamelCaseMapping) {
    if (useCamelCaseMapping) {
      name = name.replace("_", "");
    }
    return findProperty(name);
  }

  /**
   * 获取可读属性的集合
   */
  public String[] getGetterNames() {
    return reflector.getGetablePropertyNames();
  }
  /**
   * 获取可写属性的集合
   */
  public String[] getSetterNames() {
    return reflector.getSetablePropertyNames();
  }

  /**
   * 获取 setter 类型：递归
   * @param name 表达式
   */
  public Class<?> getSetterType(String name) {
    PropertyTokenizer prop = new PropertyTokenizer(name);
    if (prop.hasNext()) {
      MetaClass metaProp = metaClassForProperty(prop.getName());
      return metaProp.getSetterType(prop.getChildren());
    } else {
      return reflector.getSetterType(prop.getName());
    }
  }
  /**
   * 获取 getter 类型：递归
   * @param name 表达式
   */
  public Class<?> getGetterType(String name) {
    PropertyTokenizer prop = new PropertyTokenizer(name);
    if (prop.hasNext()) {
      MetaClass metaProp = metaClassForProperty(prop);
      return metaProp.getGetterType(prop.getChildren());
    }
    // issue #506. Resolve the type inside a Collection Object
    return getGetterType(prop);
  }

  /**
   * 为属性创建 MetaClass 对象
   *
   * @param prop 属性名
   * @return
   */
  private MetaClass metaClassForProperty(PropertyTokenizer prop) {
    // 查找指定属性对应的 Class
    Class<?> propType = getGetterType(prop);
    // 创建对应的 MetaClass 对象
    return MetaClass.forClass(propType, reflectorFactory);
  }

    /**
     * 从表达式中获取getter Class类型
     * @param prop
     * @return
     */
  private Class<?> getGetterType(PropertyTokenizer prop) {
    Class<?> type = reflector.getGetterType(prop.getName());
    if (prop.getIndex() != null && Collection.class.isAssignableFrom(type)) {
      Type returnType = getGenericGetterType(prop.getName());
      if (returnType instanceof ParameterizedType) {
        Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
        if (actualTypeArguments != null && actualTypeArguments.length == 1) {
          returnType = actualTypeArguments[0];
          if (returnType instanceof Class) {
            type = (Class<?>) returnType;
          } else if (returnType instanceof ParameterizedType) {
            type = (Class<?>) ((ParameterizedType) returnType).getRawType();
          }
        }
      }
    }
    return type;
  }

    /**
     * 获取属性的泛型 getter
     * @param propertyName
     * @return
     */
  private Type getGenericGetterType(String propertyName) {
    try {
      Invoker invoker = reflector.getGetInvoker(propertyName);
      // 如果是方法， 返回其返回值类型
      if (invoker instanceof MethodInvoker) {
        Field _method = MethodInvoker.class.getDeclaredField("method");
        _method.setAccessible(true);
        Method method = (Method) _method.get(invoker);
        return TypeParameterResolver.resolveReturnType(method, reflector.getType());
      } else if (invoker instanceof GetFieldInvoker) {
          // 如果是 GetFieldInvoker， 返回其属性类型
          Field _field = GetFieldInvoker.class.getDeclaredField("field");
          _field.setAccessible(true);
          Field field = (Field) _field.get(invoker);
          return TypeParameterResolver.resolveFieldType(field, reflector.getType());
      }
    } catch (NoSuchFieldException | IllegalAccessException ignored) {
    }
    return null;
  }

    /**
     * 判断是否有 setter， 最终由 reflector 来判断
     * @param name
     * @return
     */
  public boolean hasSetter(String name) {
    PropertyTokenizer prop = new PropertyTokenizer(name);
    if (prop.hasNext()) {
      if (reflector.hasSetter(prop.getName())) {
        MetaClass metaProp = metaClassForProperty(prop.getName());
        return metaProp.hasSetter(prop.getChildren());
      } else {
        return false;
      }
    } else {
      return reflector.hasSetter(prop.getName());
    }
  }

  /**
   * 查找属性是否有对应的getter
   *
   * @param name
   * @return
   */
  public boolean hasGetter(String name) {
    // 解析属性表达式
    PropertyTokenizer prop = new PropertyTokenizer(name);
    // 存在待处理的子表达式
    if (prop.hasNext()) {
      if (reflector.hasGetter(prop.getName())) {
        MetaClass metaProp = metaClassForProperty(prop);
        return metaProp.hasGetter(prop.getChildren());
      } else {
        return false;
      }
    } else {
      return reflector.hasGetter(prop.getName());
    }
  }

    /**
     * 获取 GetInvoker
     * @param name
     * @return
     */
  public Invoker getGetInvoker(String name) {
    return reflector.getGetInvoker(name);
  }

    /**
     * 获取 SetInvoker
     * @param name
     * @return
     */
  public Invoker getSetInvoker(String name) {
    return reflector.getSetInvoker(name);
  }

    /**
     * 解析属性表达式
     * 会去寻找reflector中是否有对应的的属性
     * @param name
     * @param builder
     * @return
     */
  private StringBuilder buildProperty(String name, StringBuilder builder) {
    // 解析属性表达式
    PropertyTokenizer prop = new PropertyTokenizer(name);
    // 是否有子表达式
    if (prop.hasNext()) {
      // 查找对应的属性
      String propertyName = reflector.findPropertyName(prop.getName());
      if (propertyName != null) {
        // 追加属性名
        builder.append(propertyName);
        builder.append(".");
        // 创建对应的 MetaClass 对象
        MetaClass metaProp = metaClassForProperty(propertyName);
        // 解析子表达式, 递归
        metaProp.buildProperty(prop.getChildren(), builder);
      }
    } else {
      // 根据名称查找属性
      String propertyName = reflector.findPropertyName(name);
      if (propertyName != null) {
        builder.append(propertyName);
      }
    }
    return builder;
  }

    /**
     * 是否有默认构造函数
     * @return
     */
  public boolean hasDefaultConstructor() {
    return reflector.hasDefaultConstructor();
  }

}