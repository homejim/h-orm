package com.homejim.framework.sql.generator;

import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlTypeEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 更新实体的 sql 生成器
 *
 * @author homejim
 * @since 2019-12-25
 */
public class InsertSqlGenerator implements SqlGenerator {

    public final static InsertSqlGenerator INSTANCE = new InsertSqlGenerator();
    /**
     * update
     */
    private final String INSERT_TEMPLATE = "INSERT INTO %s%s";

    @Override
    public String generate(SqlEntity sqlEntity) {
        String insertCaules = getInsertCaules(sqlEntity);
        return String.format(INSERT_TEMPLATE, sqlEntity.getTable(), insertCaules);
    }

    @Override
    public SqlTypeEnum getSqlType() {
        return SqlTypeEnum.INSERT;
    }

    public String getInsertCaules(SqlEntity sqlEntity) {
        List<String> noInsertList = sqlEntity.getNotInsertProperties().stream().map(MappingProperty::getField).collect(Collectors.toList());

        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("VALUES(");

        boolean first = false;
        List<MappingProperty> properties = sqlEntity.getProperties();
        for (MappingProperty item : properties) {
            if (!noInsertList.contains(item.getField())) {
                if (!first) {
                    columns.append("{? ").append(item.getColumn()).append("@").append(item.getField()).append("@}");
                    values.append("{? ").append("#").append(item.getField()).append("#}");
                    first = true;
                } else {
                    columns.append("{?,").append(item.getColumn()).append("@").append(item.getField()).append("@}");
                    values.append("{?,").append("#").append(item.getField()).append("#}");
                }
            }
        }
        columns.append(")");
        values.append(")");
        return columns.toString() + " " + values.toString();
    }

}