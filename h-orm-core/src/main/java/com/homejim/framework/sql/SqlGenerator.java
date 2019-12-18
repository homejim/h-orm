package com.homejim.framework.sql;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sql 生成器
 *
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-12-06 23:05
 */
public class SqlGenerator {

    private static final Joiner andJoiner = Joiner.on(" and ");

    public static final String SELECT_TEMPLATE = "select %s from %s where %s limit 1";

    public static String selectOne(SqlEntity sqlEntity) {
        String selectDBColumns = sqlEntity.getSelectDBColumns();
        String where = where(Collections.singletonList(sqlEntity.getPrimaryKey()));
        return String.format(SELECT_TEMPLATE, selectDBColumns, sqlEntity.getTable(), where);
    }

    public static String where(List<MappingProperty> columns) {
        List<String> whereCaluses = new ArrayList<>();
        columns.forEach(item -> {
            String whereCaluse = String.format("{? %s = #%s# }", item.getColumn(), item.getField());
            whereCaluses.add(whereCaluse);
        });
        return andJoiner.join(whereCaluses);
    }
}