package com.homejim.framework.session;

import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlPool;
import com.homejim.framework.sql.SqlTypeEnum;
import com.homejim.framework.sql.mapping.MappedStatement;
import com.homejim.framework.sql.mapping.SqlSegment;
import com.homejim.framework.sql.mapping.StatementContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author homejim
 * @description 全局访问层
 * @create: 2019-12-19 00:07
 */
public class HDao {

    public <T> T selectById(Class<T> tClass, Object id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Id is required!");
        }
        String selectSqlKey = SqlPool.sqlKey(tClass.getName(), "mysql", SqlTypeEnum.SELECT);
        MappedStatement mappedStatement = SqlPool.getSql(selectSqlKey);
        Map<String, Object> params = new HashMap<>();
        SqlEntity sqlEntity = mappedStatement.getSqlEntity();
        MappingProperty primaryKey = sqlEntity.getPrimaryKey();
        params.put(primaryKey.getField(), id);
        return queryEntity(tClass, mappedStatement, params);
    }

    public <T> T queryEntity(Class<T> tClass, MappedStatement mappedStatement, Map<String, Object> params) {
        StatementContext statementContext = new StatementContext();
        List<SqlSegment> segments = mappedStatement.getSegments();
        List<Object> finalParams = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        for (SqlSegment segment : segments) {
            if (segment.isCheckIfExist()) {
                Object param = params.get(segment.getParam());
                if (param != null) {
                    sql.append(" ").append(segment.getParsedSql());
                    finalParams.add(param);
                }
            }else {
                sql.append(" ").append(segment.getParsedSql());
            }
        }
        statementContext.setParams(finalParams);
        statementContext.setPreparedSql(sql.toString());

        return null;

    }
}
