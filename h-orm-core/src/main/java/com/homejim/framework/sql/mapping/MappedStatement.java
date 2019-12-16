package com.homejim.framework.sql.mapping;

import com.homejim.framework.sql.SqlTypeEnum;
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
public final class MappedStatement {

    /**
     * sql 的类型
     */
    private SqlTypeEnum sqlTypeEnum;

    /**
     * 是否缓存
     */
    private Boolean cache;

    /**
     * sql 片段
     */
    List<SqlSegment> segments;
}