package com.homejim.framework.sql.generator;

import com.google.common.base.Joiner;
import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlTypeEnum;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 更新sql 的生成器
 *
 * @author homejim
 * @since 2019-12-25
 */
public class UpdateSqlGenerator implements SqlGenerator {

    private static Joiner sqlJoiner = Joiner.on(",");

    public final static UpdateSqlGenerator INSTANCE = new UpdateSqlGenerator();
    /**
     * update
     */
    private final String UPDATE_TEMPLATE = "UPDATE %s SET %s WHERE %s";

    @Override
    public String generate(SqlEntity sqlEntity) {
        String updateCaules = getUpdateCaules(sqlEntity);
        String where = where(Collections.singletonList(sqlEntity.getPrimaryKey()));
        return String.format(UPDATE_TEMPLATE, sqlEntity.getTable(), updateCaules, where);
    }

    @Override
    public SqlTypeEnum getSqlType() {
        return SqlTypeEnum.UPDATE;
    }

    public String getUpdateCaules(SqlEntity sqlEntity) {
        List<String> selectColumns = sqlEntity.getProperties().stream().filter(MappingProperty::getSelect).map(item -> "set " + item.getColumn() + "=?").collect(Collectors.toList());
        return sqlJoiner.join(selectColumns);
    }
}