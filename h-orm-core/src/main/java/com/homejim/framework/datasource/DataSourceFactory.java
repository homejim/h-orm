package com.homejim.framework.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 数据源工厂
 */
public interface DataSourceFactory {

  // 设置 DataSource 的属性
  void setProperties(Properties props);

  // 获取 DataSource
  DataSource getDataSource();

}