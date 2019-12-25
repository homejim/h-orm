package com.homejim.framework.sql.generator;

import com.google.common.base.Joiner;
import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql 生成器
 *
 * @author homejim
 * @since 2019-12-25
 */
public interface SqlGenerator {
    Joiner andJoiner = Joiner.on(" and ");

    /**
     * 生成 SQL
     *
     * @param sqlEntity
     * @return
     */
    String generate(SqlEntity sqlEntity);

    default String where(List<MappingProperty> columns) {
        List<String> whereCaluses = new ArrayList<>();
        columns.forEach(item -> {
            String whereCaluse = String.format("{? %s = #%s# }", item.getColumn(), item.getField());
            whereCaluses.add(whereCaluse);
        });
        return andJoiner.join(whereCaluses);
    }
}
