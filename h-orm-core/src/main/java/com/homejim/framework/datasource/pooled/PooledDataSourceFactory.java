package com.homejim.framework.datasource.pooled;

import com.homejim.framework.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * @author Clinton Begin
 * 又一个具体类
 */
public class PooledDataSourceFactory extends UnpooledDataSourceFactory {

  // 相比于 UnpooledDataSourceFactory， 其 dataSource 不一样而已
  public PooledDataSourceFactory() {
    this.dataSource = new PooledDataSource();
  }

}
