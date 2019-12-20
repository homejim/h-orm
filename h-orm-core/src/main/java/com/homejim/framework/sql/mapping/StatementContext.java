package com.homejim.framework.sql.mapping;

import com.homejim.framework.reflection.Reflector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 表示一个 SQL节点
 *
 * @author homejim
 * @since 2019-12-04 12:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StatementContext {

    /**
     * 实际的参数.
     */
    private List<Object> params;

    /**
     * sql. 可以是 key 的形式或者真正的 sql.
     */
    private String preparedSql;

    private Reflector reflector;
}