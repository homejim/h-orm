package com.homejim.framework.datasource.unpooled;

import com.homejim.framework.datasource.DataSourceFactory;
import com.homejim.framework.exception.HormDataSourceException;
import com.homejim.framework.reflection.MetaObject;
import com.homejim.framework.reflection.SystemMetaObject;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Clinton Begin
 * 具体工厂类
 */
public class UnpooledDataSourceFactory implements DataSourceFactory {

  private static final String DRIVER_PROPERTY_PREFIX = "driver.";
  private static final int DRIVER_PROPERTY_PREFIX_LENGTH = DRIVER_PROPERTY_PREFIX.length();

  // DataSource
  protected DataSource dataSource;

  // 创建 UnpooledDataSource 对象， 赋值给 dataSource
  public UnpooledDataSourceFactory() {
    this.dataSource = new UnpooledDataSource();
  }

  @Override
  public void setProperties(Properties properties) {
    Properties driverProperties = new Properties();
    // 创建 dataSource 所对应的 MetaObject
    MetaObject metaDataSource = SystemMetaObject.forObject(dataSource);
    // 遍历传入的 Properties
    for (Object key : properties.keySet()) {
      String propertyName = (String) key;
      // 以 "driver." 开头的属性名
      if (propertyName.startsWith(DRIVER_PROPERTY_PREFIX)) {
        // 获取值， 以去除"driver."的 propertyName 为key， 设置属性
        String value = properties.getProperty(propertyName);
        driverProperties.setProperty(propertyName.substring(DRIVER_PROPERTY_PREFIX_LENGTH), value);
      } else if (metaDataSource.hasSetter(propertyName)) {// 如果含有 Setter 方法
        String value = (String) properties.get(propertyName);
        // 根据属性的类型进行类型转换
        Object convertedValue = convertValue(metaDataSource, propertyName, value);
        // 设置属性
        metaDataSource.setValue(propertyName, convertedValue);
      } else {
        throw new HormDataSourceException("Unknown DataSource property: " + propertyName);
      }
    }
    // 设置属性
    if (driverProperties.size() > 0) {
      metaDataSource.setValue("driverProperties", driverProperties);
    }
  }

  @Override
  public DataSource getDataSource() {
    return dataSource;
  }

  /**
   * 类型转换：将属性按类型进行转换
   */
  private Object convertValue(MetaObject metaDataSource, String propertyName, String value) {
    Object convertedValue = value;
    // 获取对应属性的 setter 类型
    Class<?> targetType = metaDataSource.getSetterType(propertyName);
    if (targetType == Integer.class || targetType == int.class) {
      convertedValue = Integer.valueOf(value);
    } else if (targetType == Long.class || targetType == long.class) {
      convertedValue = Long.valueOf(value);
    } else if (targetType == Boolean.class || targetType == boolean.class) {
      convertedValue = Boolean.valueOf(value);
    }
    return convertedValue;
  }

}
