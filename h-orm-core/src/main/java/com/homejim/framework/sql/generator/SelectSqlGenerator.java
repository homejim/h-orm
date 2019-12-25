package com.homejim.framework.sql.generator;

import com.google.common.base.Joiner;
import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlTypeEnum;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sql 生成器
 *
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-12-06 23:05
 */
public class SelectSqlGenerator implements SqlGenerator {

    private static Joiner sqlJoiner = Joiner.on(",");
    public final static SelectSqlGenerator INSTANCE = new SelectSqlGenerator();

    private final String SELECT_TEMPLATE = "select %s from %s where %s limit 1";

    @Override
    public String generate(SqlEntity sqlEntity) {
        String selectCaules = getSelectCaules(sqlEntity);
        String where = where(Collections.singletonList(sqlEntity.getPrimaryKey()));
        return String.format(SELECT_TEMPLATE, selectCaules, sqlEntity.getTable(), where);
    }

    @Override
    public SqlTypeEnum getSqlType() {
        return SqlTypeEnum.SELECT;
    }

    public String getSelectCaules(SqlEntity sqlEntity) {
        List<String> selectColumns = sqlEntity.getProperties().stream().filter(MappingProperty::getSelect).map(MappingProperty::getColumn).collect(Collectors.toList());
        return sqlJoiner.join(selectColumns);
    }
}