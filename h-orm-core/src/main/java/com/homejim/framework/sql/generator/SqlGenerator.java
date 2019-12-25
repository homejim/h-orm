package com.homejim.framework.sql.generator;

import com.homejim.framework.sql.SqlEntity;

/**
 * Sql 生成器
 *
 * @author homejim
 * @since 2019-12-25
 */
public interface SqlGenerator {

    /**
     * 生成 SQL
     *
     * @param sqlEntity
     * @return
     */
    String generate(SqlEntity sqlEntity);
}
