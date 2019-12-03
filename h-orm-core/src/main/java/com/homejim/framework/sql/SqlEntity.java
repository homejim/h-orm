package com.homejim.framework.sql;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Sql实体类
 *
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-12-03 19:22
 */
@Setter
@Getter
public class SqlEntity {
    /**
     * 映射字段
     */
    private List<MappingProperty> properties;

    /**
     * 主键
     */
    private MappingProperty primaryKey;

    /**
     * 表名
     */
    private String table;

    /**
     * 类全限定名
     */
    private String classFullName;
}