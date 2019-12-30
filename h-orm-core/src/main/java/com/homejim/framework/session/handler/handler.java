package com.homejim.framework.session.handler;

import com.homejim.framework.sql.mapping.StatementContext;

import java.sql.ResultSet;

/**
 * 处理器接口
 *
 * @author homejim
 * @since 2019-12-30 12:33
 */
public interface handler {
    ResultSet handle(StatementContext context);
}
