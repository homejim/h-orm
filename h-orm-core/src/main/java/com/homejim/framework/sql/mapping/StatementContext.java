package com.homejim.framework.sql.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

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
     * 实际的参数. 使用 Map 以便进行匹配
     */
    private Map<String, Object> params;

    /**
     * sql. 可以是 key 的形式或者真正的 sql.
     */
    private String sql;

}