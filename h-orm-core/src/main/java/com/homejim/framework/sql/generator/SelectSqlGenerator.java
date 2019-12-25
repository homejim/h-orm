package com.homejim.framework.sql.generator;

import com.google.common.base.Joiner;
import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sql 生成器
 *
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-12-06 23:05
 */
public class SelectSqlGenerator implements SqlGenerator {

    public final static SelectSqlGenerator INSTANCE = new SelectSqlGenerator();

    private static final Joiner andJoiner = Joiner.on(" and ");

    public static final String SELECT_TEMPLATE = "select %s from %s where %s limit 1";

    public String where(List<MappingProperty> columns) {
        List<String> whereCaluses = new ArrayList<>();
        columns.forEach(item -> {
            String whereCaluse = String.format("{? %s = #%s# }", item.getColumn(), item.getField());
            whereCaluses.add(whereCaluse);
        });
        return andJoiner.join(whereCaluses);
    }

    @Override
    public String generate(SqlEntity sqlEntity) {
        String selectDBColumns = sqlEntity.getSelectDBColumns();
        String where = where(Collections.singletonList(sqlEntity.getPrimaryKey()));
        return String.format(SELECT_TEMPLATE, selectDBColumns, sqlEntity.getTable(), where);
    }
}