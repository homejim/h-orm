package com.homejim.framework.sql.generator;

import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlTypeEnum;

import java.util.Collections;

/**
 * Sql 生成器
 *
 * @author homejim
 * @since 2019-12-06 23:05
 */
public class DeleteSqlGenerator implements SqlGenerator {

    public final static DeleteSqlGenerator INSTANCE = new DeleteSqlGenerator();

    private final String DELETE_TEMPLATE = "delete from %s where %s ";

    @Override
    public String generate(SqlEntity sqlEntity) {
        String where = where(Collections.singletonList(sqlEntity.getPrimaryKey()));
        return String.format(DELETE_TEMPLATE, sqlEntity.getTable(), where);
    }

    @Override
    public SqlTypeEnum getSqlType() {
        return SqlTypeEnum.DELETE;
    }

}