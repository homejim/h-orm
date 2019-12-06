package com.homejim.framework.sql;

import com.homejim.framework.sql.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * Sql 工厂， 通过其产生对应的 Sql
 *
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-12-04 12:49
 */
public class SqlFactory {
    private static Map<String, MappedStatement> sqlPools = new HashMap<>();

    public void addSql(String sqlKey, MappedStatement statement) {
        if (sqlPools.get(sqlKey) != null) {
            throw new RuntimeException(String.format("The sqlPools has contained key [%s], please checked", sqlKey));
        }
    }
}