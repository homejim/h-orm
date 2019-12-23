package com.homejim.framework.reflection.factory;

import java.util.List;
import java.util.Properties;

/**
 * MyBatis uses an ObjectFactory to create all needed new Objects.
 * 使用该类来创建所需的新对象
 *
 * @author Clinton Begin
 */
public interface ObjectFactory {

    /**
     * Sets configuration properties.
     * 设置配置信息
     *
     * @param properties configuration properties
     */
    void setProperties(Properties properties);

    /**
     * Creates a new object with default constructor.
     * 使用默认构造函数创建对象
     *
     * @param type Object type
     * @return
     */
    <T> T create(Class<T> type);

    /**
     * Creates a new object with the specified constructor and params.
     * 通过指定的构造函数和参数， 创建一个对象
     *
     * @param type                Object type
     * @param constructorArgTypes Constructor argument types
     * @param constructorArgs     Constructor argument values
     * @return
     */
    <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);

    /**
     * 检测制定类型是否为集合类型
     * Returns true if this object can have a set of other objects.
     * It's main purpose is to support non-java.util.Collection objects like Scala collections.
     *
     * @param type Object type
     * @return whether it is a collection or not
     * @since 3.1.0
     */
    <T> boolean isCollection(Class<T> type);

}
