package com.homejim.framework.session.handler;

import com.homejim.framework.sql.mapping.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 查询接口
 *
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-12-30 12:44
 */
public class QueryHandler implements handler {
    @Override
    public ResultSet handle(StatementContext statementContext) {
        ResultSet resultSet = null;
        try {
            resultSet = statementContext.getStatement().executeQuery(statementContext.getPreparedSql());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}