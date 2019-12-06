package com.homejim.framework.sql.mapping;

import com.homejim.framework.sql.SqlEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 表示一个 SQL节点
 *
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-12-04 12:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public final class MappedStatement {

    private SqlEntity sqlEntity;

    private Boolean cache;
}