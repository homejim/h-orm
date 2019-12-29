package com.homejim.framework.sql.generator;

import com.google.common.base.Joiner;
import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlTypeEnum;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 更新实体的 sql 生成器
 *
 * @author homejim
 * @since 2019-12-25
 */
public class UpdateSqlGenerator implements SqlGenerator {

    private static Joiner sqlJoiner = Joiner.on("");

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
        List<String> noUpdateList = sqlEntity.getNotUpdateProperties().stream().map(MappingProperty::getField).collect(Collectors.toList());
        MappingProperty primaryMappingProperty = sqlEntity.getPrimaryKey();
        String first = String.format("{?? %s=#%s#}", primaryMappingProperty.getColumn(), primaryMappingProperty.getField());

        List<String> selectColumns = sqlEntity.getProperties().stream().filter(MappingProperty::getSelect).map(
                item -> {
                    if (!noUpdateList.contains(item.getField())) {
                        if (!primaryMappingProperty.getField().equals(item.getField())) {
                            return String.format("{? ,%s=#%s#}", item.getColumn(), item.getField());
                        }
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
        String join = sqlJoiner.join(selectColumns);
        return first + join;
    }
}