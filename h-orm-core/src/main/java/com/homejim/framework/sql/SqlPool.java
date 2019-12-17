package com.homejim.framework.sql;

import com.homejim.framework.sql.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * Sql 工厂， 通过其产生对应的 Sql
 *
 * @author homejim
 * @since 2019-12-04 12:49
 */
public class SqlPool {

    private static Map<String, MappedStatement> mappedStatementPool = new HashMap<>();

    public static void addSql(String sqlKey, MappedStatement statement) {
        if (mappedStatementPool.get(sqlKey) != null) {
            throw new RuntimeException(String.format("The mappedStatementPool has contained key [%s], please checked", sqlKey));
        }
        mappedStatementPool.put(sqlKey, statement);
    }

    public static MappedStatement getSql(String sqlKey) {
        return mappedStatementPool.get(sqlKey);
    }

    public static String sqlKey(String className, String db, SqlTypeEnum sqlTypeEnum) {
        return String.format("%s$%s$%s", db, className, sqlTypeEnum.getCode()).toLowerCase();
    }

}